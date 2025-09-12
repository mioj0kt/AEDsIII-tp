package Arquivo;

import Entidades.Usuario;
import Hash.HashExtensivel;
import Pares.ParEmailID;

public class ArquivoUsuario extends Arquivo<Usuario> {

    HashExtensivel<ParEmailID> indiceIndiretoEmail;

    public ArquivoUsuario() throws Exception {
        super("usuarios", Usuario.class.getConstructor());
        indiceIndiretoEmail = new HashExtensivel<>(ParEmailID.class.getConstructor(), 4,
                ".\\dados\\usuarios\\indiceEmail.d.db",
                ".\\dados\\usuarios\\indiceEmail.c.db");
    }

    
    //Cria um novo usuário no arquivo de dados e atualiza o índice de emails.
    @Override
    public int create(Usuario u) throws Exception {
        int id = super.create(u);
        u.setId(id);
        indiceIndiretoEmail.create(new ParEmailID(u.getEmail(), u.getId()));
        return id;
    }

    
    //Lê um usuário do arquivo buscando pelo email.
    public Usuario read(String email) throws Exception {
        ParEmailID p = new ParEmailID(email, -1);
        p = indiceIndiretoEmail.read(p.hashCode());
        
        if (p == null)
            return null;
        else
            return super.read(p.getID());
    }

    
    //Deleta um usuário do arquivo buscando pelo email.
    public boolean delete(String email) throws Exception {
        ParEmailID p = new ParEmailID(email, -1);
        p = indiceIndiretoEmail.read(p.hashCode());

        if (p != null) {
            if (super.delete(p.getID())) {
                return indiceIndiretoEmail.delete(p.hashCode());
            }
        }
        return false;
    }

    @Override
    public boolean update(Usuario novoUsuario) throws Exception {
        // Primeiro, lemos o usuário antigo pelo ID para obter o email antigo
        Usuario velhoUsuario = super.read(novoUsuario.getId());
        if (velhoUsuario == null) {
            return false;
        }

        // Realiza o update no arquivo principal
        if (super.update(novoUsuario)) {
            // Se o email foi alterado, precisamos atualizar o índice
            if (!novoUsuario.getEmail().equals(velhoUsuario.getEmail())) {
                // Deleta o índice do email antigo
                indiceIndiretoEmail.delete(new ParEmailID(velhoUsuario.getEmail(), -1).hashCode());
                // Cria o índice para o novo email
                indiceIndiretoEmail.create(new ParEmailID(novoUsuario.getEmail(), novoUsuario.getId()));
            }
            return true;
        }
        return false;
    }
}