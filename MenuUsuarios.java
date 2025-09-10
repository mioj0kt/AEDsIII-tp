import java.util.Scanner;
import java.security.MessageDigest;

public class MenuUsuarios {

    ArquivoUsuario arqUsuarios;
    private Usuario usuarioAtivo;
    private static Scanner console = new Scanner(System.in);

    public MenuUsuarios() throws Exception {
        arqUsuarios = new ArquivoUsuario();
    }

    public void incluirUsuario() {
        System.out.println("\nInclusão de usuário");
        try {
            String nome;
            do {
                System.out.print("Nome (min 4 chars, vazio p/ cancelar): ");
                nome = console.nextLine();
                if (nome.length() == 0) return;
                if (nome.length() < 4) System.err.println("Nome muito curto.");
            } while(nome.length() < 4);

            String email;
            do {
                System.out.print("Email (vazio p/ cancelar): ");
                email = console.nextLine().trim();
                if (email.isEmpty()) return;
                if (!email.contains("@")) System.err.println("Email inválido.");
            } while(!email.contains("@"));

            // verifica existência
            if (arqUsuarios.read(email) != null) {
                System.out.println("Email já cadastrado.");
                return;
            }

            System.out.print("Senha: ");
            String senha = console.nextLine();

            System.out.print("Pergunta secreta (para recuperação): ");
            String pergunta = console.nextLine();

            System.out.print("Resposta secreta: ");
            String resposta = console.nextLine();

            String hash = hashSenha(senha);

            Usuario u = new Usuario(-1, nome, email, hash, pergunta, resposta);
            int id = arqUsuarios.create(u);
            System.out.println("Usuário cadastrado com sucesso. ID interno: " + id);

        } catch(Exception e) {
            System.out.println("Erro ao incluir usuário: " + e.getMessage());
        }
    }

    public Usuario getUsuarioAtivo() {
        return usuarioAtivo;
    }

    public void login() {
        System.out.println("\nLogin");
        System.out.print("Email: ");
        String email = console.nextLine().trim();
        if (email.isEmpty()) return;
    
        System.out.print("Senha: ");
        String senha = console.nextLine();
    
        try {
            Usuario u = arqUsuarios.read(email);
            if (u == null) {
                System.out.println("Usuário não encontrado.");
                return;
            }
    
            String hash = hashSenha(senha);
            if (hash.equals(u.getHashSenha())) {
                usuarioAtivo = u; // define usuário ativo
                System.out.println("Login bem-sucedido. Bem-vindo, " + u.getNome() + "!");
    
                // Exibe o menu principal do usuário
                menuPrincipalUsuario();
    
            } else {
                System.out.println("Senha incorreta.");
            }
        } catch(Exception e) {
            System.out.println("Erro no login: " + e.getMessage());
        }
    }
    
    private void menuPrincipalUsuario() {
        char opcao;
        do {
            System.out.println("\nPresenteFácil 1.0");
            System.out.println("-----------------");
            System.out.println("> Início");
            System.out.println("\n(1) Meus dados");
            System.out.println("(2) Minhas listas");
            System.out.println("(3) Produtos");
            System.out.println("(4) Buscar lista");
            System.out.println("(S) Sair");
    
            System.out.print("\nOpção: ");
            String entrada = console.nextLine().trim().toUpperCase();
            opcao = entrada.isEmpty() ? ' ' : entrada.charAt(0);
    
            switch (opcao) {
                case '1': mostraUsuario(usuarioAtivo); 
                    break;
                case '2': 
                    
                    break;
                case '3': 
                    System.out.println("Funcionalidade Produtos ainda não implementada."); 
                    break;
                case '4': 
                    System.out.println("Funcionalidade Buscar lista ainda não implementada."); 
                    break;
                case 'S': 
                    System.out.println("Saindo..."); 
                    usuarioAtivo = null;
                    break;
                default: System.out.println("Opção inválida!"); 
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

    // SHA-256 hex
    private static String hashSenha(String senha) {
        try {
            senha = senha.trim(); // remove espaços extras
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] bytes = md.digest(senha.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) sb.append(String.format("%02x", b));
            return sb.toString();
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
}
