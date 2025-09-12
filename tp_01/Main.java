import java.util.*;
import Menus.*;

public class Main{
    public static void main(String[] args){
        Scanner sc;

        try{
            sc = new Scanner(System.in);
            MenuUsuarios menu = new MenuUsuarios();

            // Primeiro: login ou criação de usuário
            boolean usuarioLogado = menu.menu();
            if(!usuarioLogado){
                System.out.println("Programa encerrado. Nenhum usuário logado.");
                return;
            }

            int x = -1;
            String tmp;
            do{ 
                System.out.println("PresenteFácil 1.0 \n" + 
                                   "------------------\n" +
                                   "> Início\n" +
                                   "\n(1) Meus dados\n" +
                                   "(2) Minhas listas\n" +
                                   "(3) Produtos\n" +
                                   "(4) Buscar listas\n" +
                                   "\n(S) Sair\n");

                System.out.print("Opcao: ");
                tmp = sc.nextLine();

                if(tmp.equalsIgnoreCase("S")){
                    System.out.println("Finalizado.");
                    break;
                }

                try{
                    x = Integer.valueOf(tmp);
                } catch(NumberFormatException e){
                    x = -1; 
                }
                switch(x){
                    case 1: menu.mostraUsuario(menu.getUsuarioAtivo()); break;
                    case 2: gerenciarListas(menu.getUsuarioAtivo().getId());; break;
                    case 3: System.out.println("Não implementado.\n"); break;
                    case 4: System.out.println("Buscar listas.\n"); break;
                    default: System.out.println("Opcao Invalida\n"); break;
                }
            } while(true);

        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
