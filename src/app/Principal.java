package app;

import java.util.Scanner;

public class Principal {

    public static void main(String[] args) {

        Scanner console = new Scanner(System.in);
        MenuUsuarios menuUsuarios;

        try {
            menuUsuarios = new MenuUsuarios();

            char opcao;
            do {

                System.out.println("\n\nPresente Fácil 1.0");
                System.out.println("------------------");
                System.out.println("\n(1) Login");
                System.out.println("(2) Novo usuário");
                System.out.println("(S) Sair");

                System.out.print("\nOpção: ");
                String entrada = console.nextLine().trim().toUpperCase();
                opcao = entrada.isEmpty() ? ' ' : entrada.charAt(0);

                switch (opcao) {
                    case '1':
                        menuUsuarios.login();
                        break;
                    case '2':
                        menuUsuarios.incluirUsuario();
                        break;
                    case 'S':
                        System.out.println("Saindo...");
                        break;
                    default:
                        System.out.println("Opção inválida!");
                        break;
                }

            } while (opcao != 'S');

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            console.close();
        }
    }
}
