package Menus;

import java.util.Scanner;

public class MenuMinhasListas{
    public void menu(){
        Scanner sc = new Scanner(System.in);
        String opcao;
        int x;

        do{
            System.out.println("PresenteFácil 1.0\n" + 
                            "------------------\n" +
                            "> Início > Minhas listas\n" +
                            "\nLISTAS\n" +
                            //for(){} +
                            "(1) Aniversario\n" +  //Exemplo
                            "\n(N) Nova listas\n" +
                            "(R) Retornar ao menu anterior\n");

            System.out.print("\nOpcao: ");
            opcao = sc.nextLine().trim();

            if (opcao.equalsIgnoreCase("R")) {
                System.out.println("Retornando ao menu anterior...\n");
                break;
            }

            try{
                x = Integer.valueOf(sc.nextLine());
            }catch(NumberFormatException e){
                x = -1;
            }

            switch(x){
                case 1:
                        
                    break;
                case 2:
                        
                    break;
                case 3:
                        
                    break;
                case 4:
                        
                    break;
                default:
                    break;
            }
            sc.close();
        }while(x != 0);
    }
}
