package Views;

import java.util.List;
import java.util.Scanner;
import Entidades.Produto;
import Menus.ConsoleUtils;

public class VisaoProduto {

    private static Scanner console = new Scanner(System.in);

    public char mostraMenuProdutos() {
        ConsoleUtils.limparTela();
        System.out.println("PresenteFácil 1.0");
        System.out.println("-----------------");
        System.out.println("> Início > Produtos");
        System.out.println("\n(1) Buscar produto por GTIN-13");
        System.out.println("(2) Listar todos os produtos");
        System.out.println("(3) Cadastrar um novo produto");
        System.out.println("\n(R) Retornar ao menu anterior");
        System.out.print("\nOpção: ");
        String entrada = console.nextLine().trim().toUpperCase();
        return entrada.isEmpty() ? ' ' : entrada.charAt(0);
    }

    public String leGtin() {
        System.out.print("\nDigite o GTIN-13 do produto: ");
        return console.nextLine().trim();
    }

    public Produto leProduto() {
        System.out.println("\n-- Novo Produto (digite 'R' a qualquer momento para cancelar) --");
        String gtin;
        do {
            System.out.print("GTIN-13 (apenas números): ");
            gtin = console.nextLine().trim();
            if (gtin.equalsIgnoreCase("R"))
                return null; // Aborta a operação
            if (!gtin.matches("\\d+")) {
                System.err.println("Erro: O GTIN-13 deve conter apenas números.");
            }
        } while (!gtin.matches("\\d+"));

        String nome;
        do {
            System.out.print("Nome (obrigatório): ");
            nome = console.nextLine().trim();
            if (nome.equalsIgnoreCase("R"))
                return null; // Aborta a operação
            if (nome.isEmpty()) {
                System.err.println("Erro: O nome é obrigatório.");
            }
        } while (nome.isEmpty());

        String descricao;
        do {
            System.out.print("Descrição (obrigatório): ");
            descricao = console.nextLine().trim();
            if (descricao.equalsIgnoreCase("R"))
                return null; // Aborta a operação
            if (descricao.isEmpty()) {
                System.err.println("Erro: A descrição é obrigatória.");
            }
        } while (descricao.isEmpty());

        return new Produto(-1, gtin, nome, descricao, true);
    }

    public void mostraProduto(Produto p) {
        if (p == null) {
            exibeMensagem("Produto não encontrado.");
            return;
        }
        System.out.println("\n-- Detalhes do Produto --");
        System.out.println("ID: " + p.getId());
        System.out.println("GTIN-13: " + p.getGtin13());
        System.out.println("Nome: " + p.getNome());
        System.out.println("Descrição: " + p.getDescricao());
        System.out.println("Status: " + (p.isAtivo() ? "Ativo" : "Inativo"));
    }

    public char mostraMenuDetalheProduto(Produto p) {
        System.out.println("\n(1) Alterar dados do produto");
        if (p.isAtivo()) {
            System.out.println("(2) Inativar o produto");
        } else {
            System.out.println("(2) Reativar o produto");
        }
        System.out.println("\n(R) Retornar");
        System.out.print("\nOpção: ");
        String entrada = console.nextLine().trim().toUpperCase();
        return entrada.isEmpty() ? ' ' : entrada.charAt(0);
    }

    public void leAlteracaoProduto(Produto p) {
        System.out.println("\n-- Alterar Produto (deixe em branco para manter o valor atual) --");
        System.out.println("GTIN-13: " + p.getGtin13() + " (não pode ser alterado)");

        System.out.print("Nome [" + p.getNome() + "]: ");
        String nome = console.nextLine();
        if (!nome.trim().isEmpty()) {
            p.setNome(nome);
        }

        System.out.print("Descrição [" + p.getDescricao() + "]: ");
        String descricao = console.nextLine();
        if (!descricao.trim().isEmpty()) {
            p.setDescricao(descricao);
        }
    }

    public void exibeMensagem(String msg) {
        System.out.println("\n" + msg);
        ConsoleUtils.pausar();
    }
}