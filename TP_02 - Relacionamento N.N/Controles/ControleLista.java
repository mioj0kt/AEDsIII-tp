package Controles;

import java.util.Comparator;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Arquivo.ArquivoLista;
import Arquivo.ArquivoListaProduto;
import Arquivo.ArquivoProduto;
import Entidades.Lista;
import Entidades.ListaProduto;
import Entidades.Produto;
import Entidades.Usuario;
import Menus.ConsoleUtils;
import Views.VisaoLista;

public class ControleLista {

    private ArquivoLista arqListas;
    private VisaoLista visao;
    private Usuario usuarioLogado;
    private ArquivoProduto arqProdutos;
    private ArquivoListaProduto arqListaProduto;

    public ControleLista(Usuario usuarioLogado) {
        try {
            this.arqListas = new ArquivoLista();
            this.arqProdutos = new ArquivoProduto(); // NOVO
            this.arqListaProduto = new ArquivoListaProduto(); // NOVO
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.visao = new VisaoLista();
        this.usuarioLogado = usuarioLogado;
    }

    // Inicia a tela principal de "Minhas Listas"
    public void executa() {
        String opcao;
        do {
            // 1. Pega as listas do usuário
            List<Lista> listas = null;
            try {
                listas = arqListas.readAll(usuarioLogado.getId());
            } catch (Exception e) {
                visao.exibeMensagem("Erro ao ler as listas: " + e.getMessage());
                listas = new java.util.ArrayList<>();
            }

            // 2. Ordena as listas por nome
            listas.sort(Comparator.comparing(Lista::getNome));

            // 3. Mostra o menu e lê a opção
            opcao = visao.mostraMenuPrincipalListas(listas);

            switch (opcao) {
                case "N":
                    incluirLista();
                    break;
                case "R":
                    // Apenas sai do loop
                    break;
                default:
                    try {
                        int numLista = Integer.parseInt(opcao) - 1;
                        if (numLista >= 0 && numLista < listas.size()) {
                            gerenciarLista(listas.get(numLista));
                        } else {
                            visao.exibeMensagem("Opção inválida!");
                        }
                    } catch (NumberFormatException e) {
                        visao.exibeMensagem("Opção inválida!");
                    }
                    break;
            }
        } while (!opcao.equals("R"));
    }

    /**
     * Inicia a tela de detalhes para uma lista específica.
     * 
     * @param lista A lista a ser gerenciada.
     */
    private void gerenciarLista(Lista lista) {
        char opcao;
        do {
            opcao = visao.mostraMenuDetalheLista(lista);
            switch (opcao) {
                case '1':
                    gerenciarProdutosDaLista(lista); // CHAMADA PARA O NOVO MÉTODO
                    break;
                case '2':
                    alterarLista(lista);
                    break;
                case '3':
                    if (excluirLista(lista)) {
                        return; // Retorna ao menu anterior se a lista foi excluída
                    }
                    break;
                case '4':
                    try {
                        String codigo = lista.getCodigoCompartilhavel();
                        ConsoleUtils.copiarParaClipboard(codigo);
                        visao.exibeMensagem("Código \"" + codigo + "\" copiado para a área de transferência!");
                    } catch (Exception e) {
                        // Trata o erro caso o sistema não tenha interface gráfica
                        visao.exibeMensagem(
                                "Erro: Não foi possível copiar para a área de transferência neste ambiente.");
                    }
                    break;
                case 'R':
                    break;
                default:
                    visao.exibeMensagem("Opção inválida!");
                    break;
            }
        } while (opcao != 'R');
    }

    private void incluirLista() {
        Lista novaLista = visao.leLista();
        novaLista.setIdUsuario(usuarioLogado.getId());
        try {
            arqListas.create(novaLista);
            visao.exibeMensagem("Lista \"" + novaLista.getNome() + "\" criada com sucesso!");
        } catch (Exception e) {
            visao.exibeMensagem("Erro ao criar lista: " + e.getMessage());
        }
    }

    private void alterarLista(Lista lista) {
        visao.leAlteracaoLista(lista);
        try {
            arqListas.update(lista);
            visao.exibeMensagem("Lista alterada com sucesso!");
        } catch (Exception e) {
            visao.exibeMensagem("Erro ao alterar lista: " + e.getMessage());
        }
    }

    private boolean excluirLista(Lista lista) {
        if (visao.confirmaExclusao()) {
            try {
                arqListas.delete(lista.getId());
                visao.exibeMensagem("Lista excluída com sucesso!");
                return true;
            } catch (Exception e) {
                visao.exibeMensagem("Erro ao excluir lista: " + e.getMessage());
                return false;
            }
        }
        return false;
    }

    private void gerenciarProdutosDaLista(Lista lista) {
        char opcao;
        do {
            List<String> nomesProdutos = new ArrayList<>();
            List<ListaProduto> associacoes = new ArrayList<>();
            try {
                associacoes = arqListaProduto.readByIdLista(lista.getId());
                for (ListaProduto lp : associacoes) {
                    Produto p = arqProdutos.read(lp.getIdProduto());
                    if (p != null) {
                        nomesProdutos.add(p.getNome() + " (x" + lp.getQuantidade() + ")");
                    } else {
                        nomesProdutos.add("Produto não encontrado (ID: " + lp.getIdProduto() + ")");
                    }
                }
            } catch (Exception e) {
                visao.exibeMensagem("Erro ao carregar produtos: " + e.getMessage());
            }

            String entrada = visao.mostraMenuProdutosDaLista(lista, nomesProdutos).trim().toUpperCase();
            opcao = entrada.isEmpty() ? ' ' : entrada.charAt(0);

            switch (opcao) {
                case 'A':
                    acrescentarProdutoNaLista(lista);
                    break;
                case 'R':
                    break;
                default:
                    try {
                        int numProduto = Integer.parseInt(entrada) - 1;
                        if (numProduto >= 0 && numProduto < associacoes.size()) {
                            gerenciarProdutoSelecionado(associacoes.get(numProduto));
                        } else {
                            visao.exibeMensagem("Opção inválida!");
                        }
                    } catch (NumberFormatException e) {
                        visao.exibeMensagem("Opção inválida!");
                    }
                    break;
            }
        } while (opcao != 'R');
    }

    private void acrescentarProdutoNaLista(Lista lista) {
        char opcao;
        do {
            opcao = visao.mostraMenuAcrescentarProduto();
            Produto produtoSelecionado = null;
            try {
                switch (opcao) {
                    case '1':
                        System.out.print("Digite o GTIN-13: ");
                        String gtin = new Scanner(System.in).nextLine();
                        produtoSelecionado = arqProdutos.read(gtin);
                        if (produtoSelecionado == null)
                            visao.exibeMensagem("Produto não encontrado.");
                        break;
                    case '2':
                        produtoSelecionado = selecionarProdutoDeListaGeral();
                        break;
                    case 'R':
                        break;
                    default:
                        visao.exibeMensagem("Opção inválida.");
                        break;
                }

                if (produtoSelecionado != null) {
                    if (!produtoSelecionado.isAtivo()) {
                        visao.exibeMensagem("Este produto está inativo e não pode ser adicionado.");
                    } else if (visao.confirmaAcrescentar(produtoSelecionado.getNome())) {
                        int quantidade = visao.leQuantidade();
                        String obs = visao.leObservacoes();

                        ListaProduto novaAssociacao = new ListaProduto(-1, lista.getId(), produtoSelecionado.getId(),
                                quantidade, obs);
                        arqListaProduto.create(novaAssociacao);
                        visao.exibeMensagem("Produto adicionado!");
                        opcao = 'R'; // Volta para a tela de produtos da lista
                    }
                }
            } catch (Exception e) {
                visao.exibeMensagem("Erro: " + e.getMessage());
            }
        } while (opcao != 'R');
    }

    private Produto selecionarProdutoDeListaGeral() throws Exception {
        List<Produto> produtos = arqProdutos.readAll();
        produtos.removeIf(p -> !p.isAtivo()); // Remove inativos
        produtos.sort(Comparator.comparing(Produto::getNome));

        if (produtos.isEmpty()) {
            visao.exibeMensagem("Nenhum produto ativo cadastrado.");
            return null;
        }

        System.out.println("\n-- Selecione um Produto --");
        for (int i = 0; i < produtos.size(); i++) {
            System.out.printf("(%d) %s\n", i + 1, produtos.get(i).getNome());
        }
        System.out.print("\nOpção (ou 'R' para voltar): ");
        String entrada = new Scanner(System.in).nextLine().trim().toUpperCase();

        if (entrada.equals("R"))
            return null;

        try {
            int indice = Integer.parseInt(entrada) - 1;
            if (indice >= 0 && indice < produtos.size()) {
                return produtos.get(indice);
            }
        } catch (NumberFormatException e) {
            // ignora
        }

        visao.exibeMensagem("Opção inválida.");
        return null;
    }

    private void gerenciarProdutoSelecionado(ListaProduto lp) {
        try {
            Produto p = arqProdutos.read(lp.getIdProduto());
            if (p == null) {
                visao.exibeMensagem("Produto associado não encontrado. Removendo associação corrompida.");
                arqListaProduto.delete(lp.getId());
                return;
            }

            char opcao = visao.mostraMenuGerenciarProduto(p.getNome());
            switch (opcao) {
                case '1': // Alterar
                    visao.leAlteracaoProdutoDaLista(lp);
                    arqListaProduto.update(lp);
                    visao.exibeMensagem("Produto na lista alterado com sucesso!");
                    break;
                case '2': // Remover
                    System.out.print("Tem certeza que deseja remover este produto da lista? (S/N): ");
                    if (new Scanner(System.in).nextLine().trim().equalsIgnoreCase("S")) {
                        arqListaProduto.delete(lp.getId());
                        visao.exibeMensagem("Produto removido da lista.");
                    }
                    break;
                case 'R':
                    break;
                default:
                    visao.exibeMensagem("Opção inválida.");
                    break;
            }

        } catch (Exception e) {
            visao.exibeMensagem("Erro ao gerenciar produto: " + e.getMessage());
        }
    }
}