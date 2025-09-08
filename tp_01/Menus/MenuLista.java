package Menus;

import java.util.Scanner;

public class MenuLista{
    public void menu(Scanner sc){
        String opcao;
        int x;

        do{
            System.out.println("PresenteFácil 1.0\n" + 
                            "------------------\n" +
                            "> Início > Minhas listas > Aniversario\n" + 

                            // Ajustar para exibir os detalhes da lista selecionada
                            "\nCÓDIGO: " + "tdfd9as8bp" + 
                            "\nNOME: " + "Aniversário 2025  " +
                            "\nDESCRIÇAO: " + "Sugestões de presentes para o meu aniversário, que será comemorado na cervejaria." +
                            "\nDATA CRIAÇ˜AO: " + "30/07/2025" +
                            "\nDATA LIMITE: " + "31/10/2025" + "\n" +

                            "\n(1) Gerenciar produtos da lista\n" +
                            "(2) Alterar dados da lista\n" +
                            "(3) Excluir lista\n" + 
                            "\n(R) Retornar ao menu anterior\n");

            System.out.print("\nOpcao: ");
            opcao = sc.nextLine().trim();

            if(opcao.equalsIgnoreCase("R")){
                System.out.println("Retornando ao menu anterior...\n");
                return;
            }

            try{
                x = Integer.parseInt(opcao);
            } catch(NumberFormatException e){
                x = -1;
            }

            switch (x){
                case 1: System.out.println("Ainda não implementado"); break;
                case 2: System.out.println("Alterar dados da lista"); break;
                case 3: System.out.println("Excluir a lista"); break;
                default: System.out.println("Opcao Invalida\n"); break;
            }
        } while(true);
    }
}
