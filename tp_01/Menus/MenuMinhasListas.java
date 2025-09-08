package Menus;

import java.util.Scanner;

public class MenuMinhasListas{
    public void menu(Scanner sc){
        String tmp;
        int x;

        do{ 
            System.out.println("PresenteFácil 1.0\n" + 
                               "------------------\n" +
                               "> Início > Minhas listas\n" +
                               "\nLISTAS\n" +
                               "(1) Aniversario\n" +  // Exemplo
                               "\n(N) Nova lista\n" +
                               "(R) Retornar ao menu anterior\n");

            System.out.print("\nOpcao: ");
            tmp = sc.nextLine();

            if(tmp.equalsIgnoreCase("N")){
                System.out.println("Criando nova lista...\n");
                continue;
            }

            if(tmp.equalsIgnoreCase("R")){
                System.out.println("Retornando ao menu anterior...\n");
                break;
            }

            try{
                x = Integer.valueOf(tmp);
            } catch(NumberFormatException e){
                x = -1;
            }

            switch(x){
                case 1: new MenuLista().menu(sc); break;
                case 2: ; break;
                default:
                    System.out.println("Opcao Invalida\n");
                    break;
            }
        } while(true);
    }
}
