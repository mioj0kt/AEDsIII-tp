package Controles;

import java.util.Comparator;
import java.util.List;
import Arquivo.ArquivoProduto;
import Entidades.Produto;
import Views.VisaoProduto;
import Menus.ConsoleUtils;

public class ControleProduto {

    private ArquivoProduto arqProdutos;
    private VisaoProduto visao;

    public ControleProduto() throws Exception {
        this.arqProdutos = new ArquivoProduto();
        this.visao = new VisaoProduto();
    }

    public void executa() {
        char opcao;
        do {
            opcao = visao.mostraMenuProdutos();
            switch (opcao) {
                case '1':
                    buscarPorGtin();
                    break;
                case '2':
                    listarTodos();
                    break;
                case '3':
                    cadastrarProduto();
                    break;
                case 'R':
                    break;
                default:
                    visao.exibeMensagem("Opção inválida!");
                    break;
            }
        } while (opcao != 'R');
    }

    private void cadastrarProduto() {
        Produto p = visao.leProduto();

        // Se p for null, o usuário cancelou a operação
        if (p == null) {
            visao.exibeMensagem("Cadastro de produto cancelado.");
            return;
        }

        try {
            arqProdutos.create(p);
            visao.exibeMensagem("Produto cadastrado com sucesso!");
        } catch (Exception e) {
            visao.exibeMensagem("Erro ao cadastrar produto: " + e.getMessage());
        }
    }

    private void buscarPorGtin() {
        String gtin = visao.leGtin();
        try {
            Produto p = arqProdutos.read(gtin);
            if (p == null) {
                visao.exibeMensagem("Produto não encontrado.");
                return;
            }

            char opcao;
            do {
                visao.mostraProduto(p);
                opcao = visao.mostraMenuDetalheProduto(p);
                switch (opcao) {
                    case '1':
                        alterarProduto(p); // <-- MUDANÇA AQUI
                        break;
                    case '2':
                        if (p.isAtivo()) {
                            // A verificação se o produto pode ser inativado (se está em listas)
                            // deveria ser feita aqui. Por simplicidade, vamos apenas inativar.
                            arqProdutos.delete(p.getId());
                            visao.exibeMensagem("Produto inativado com sucesso.");
                        } else {
                            arqProdutos.reativar(p.getId());
                            visao.exibeMensagem("Produto reativado com sucesso.");
                        }
                        p = arqProdutos.read(gtin);
                        break;
                    case 'R':
                        break;
                    default:
                        visao.exibeMensagem("Opção inválida!");
                        break;
                }
            } while (opcao != 'R');

        } catch (Exception e) {
            visao.exibeMensagem("Erro ao buscar produto: " + e.getMessage());
        }
    }

    private void alterarProduto(Produto p) {
        visao.leAlteracaoProduto(p);
        try {
            arqProdutos.update(p);
            visao.exibeMensagem("Produto alterado com sucesso!");
        } catch (Exception e) {
            visao.exibeMensagem("Erro ao alterar produto: " + e.getMessage());
        }
    }

    private void listarTodos() {
        try {
            List<Produto> produtos = arqProdutos.readAll();
            // Ordena primeiro por status, ativos primeiro, e depois por ID
            produtos.sort(Comparator.comparing(Produto::isAtivo).reversed()
                    .thenComparing(Produto::getId));

            System.out.println("\n-- Todos os Produtos --");
            for (Produto p : produtos) {
                System.out.printf("ID %d | %s | %s [%s]\n", p.getId(), p.getGtin13(), p.getNome(),
                        (p.isAtivo() ? "Ativo" : "Inativo"));
            }
            ConsoleUtils.pausar();
        } catch (Exception e) {
            visao.exibeMensagem("Erro ao listar produtos: " + e.getMessage());
        }
    }
}