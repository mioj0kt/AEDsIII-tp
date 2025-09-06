import java.util.*;
import Menus.*;

public class Main{
    public static void main(String[] args){
        Scanner sc;

        try{
            sc = new Scanner(System.in);
            int x;
            String tmp;
            do{ 
                System.out.println("PresenteFácil 1.0\n" + 
                                   "------------------\n" +
                                   "> Início\n" +
                                   "\n(1) Meus dados\n" +
                                   "(2) Minhas listas\n" +
                                   "(3) Produtos\n" +
                                   "(4) Buscar listas\n" +
                                   "\n(S) Sair\n");

                System.out.print("\nOpcao: ");
                tmp = sc.nextLine();

                if(tmp.equalsIgnoreCase("S")){
                    System.out.println("Finalizado.\n");
                    break;
                }
                try{
                    x = Integer.valueOf(tmp);
                } catch(NumberFormatException e){
                    x = -1;
                }
                switch(x){
                    case 1:
                        System.out.println("Meus dados.\n");
                        break;
                    case 2:
                        new MinhasListas().menu();
                        break;
                    case 3:
                        System.out.println("Não implementado.\n");
                        break;
                    case 4:
                        System.out.println("Buscar listas.\n");
                        break;
                    default:
                        System.out.println("Opcao Invalida\n");
                        break;
                }
            } while(x != 0);
            sc.close();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}