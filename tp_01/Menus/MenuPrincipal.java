package Menus;

import java.util.Scanner;
import Controles.ControleLista;
import Entidades.Usuario;

public class MenuPrincipal{

    private Usuario usuarioAtivo;
    private static Scanner console = new Scanner(System.in);

    public MenuPrincipal(Usuario usuarioAtivo) {
        this.usuarioAtivo = usuarioAtivo;
    }

    public void executa() {
        char opcao;
        do {
            System.out.println("\n\nPresenteFácil 1.0");
            System.out.println("-----------------");
            System.out.println("> Início");
            System.out.println("\n(1) Meus dados");
            System.out.println("(2) Minhas listas");
            System.out.println("(3) Produtos");
            System.out.println("(4) Buscar lista");
            System.out.println("\n(S) Sair");
    
            System.out.print("\nOpção: ");
            String entrada = console.nextLine().trim().toUpperCase();
            opcao = entrada.isEmpty() ? ' ' : entrada.charAt(0);
    
            switch (opcao) {
                case '1':
                    mostraUsuario(usuarioAtivo); 
                    break;
                case '2': 
                    ControleLista controleLista = new ControleLista(usuarioAtivo);
                    controleLista.executa();
                    break;
                case '3': 
                    System.out.println("\nFuncionalidade Produtos (TP2) ainda não implementada."); 
                    break;
                case '4': 
                    System.out.println("\nFuncionalidade Buscar lista ainda não implementada."); 
                    break;
                case 'S': 
                    System.out.println("\nSaindo da sua conta..."); 
                    usuarioAtivo = null;
                    break;
                default: 
                    System.out.println("\nOpção inválida!"); 
                    break;
            }
    
        } while (opcao != 'S');
    }

    public void mostraUsuario(Usuario u) {
        if (u == null) return;
        System.out.println("\n--- Dados do usuário ---");
        System.out.println("ID: " + u.getId());
        System.out.println("Nome: " + u.getNome());
        System.out.println("Email: " + u.getEmail());
        System.out.println("Pergunta secreta: " + u.getPerguntaSecreta());
        System.out.println("-------------------------");
    }
}
