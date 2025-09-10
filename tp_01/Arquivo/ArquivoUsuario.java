package Arquivo;

import Entidades.Usuario;
import Hash.HashExtensivel;
import Pares.ParIDEmail;

public class ArquivoUsuario extends Arquivo<Usuario> {

    HashExtensivel<ParIDEmail> indiceIndiretoEmail;

    public ArquivoUsuario() throws Exception {
        super("usuarios", Usuario.class.getConstructor());
        indiceIndiretoEmail = new HashExtensivel<>(ParIDEmail.class.getConstructor(), 4,
                "tp_01//Dados/Usuarios/indiceEmail.d.db",
                "tp_01//Dados/Usuarios/indiceEmail.c.db");
    }

    public void printIndiceEmail() {
        try {
            System.out.println("\n--- Índice de Emails ---");
            indiceIndiretoEmail.print(); // usa o print já existente no HashExtensivel
            System.out.println("------------------------\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    

    @Override
    public int create(Usuario u) throws Exception {
        int id = super.create(u);
        indiceIndiretoEmail.create(new ParIDEmail(u.getEmail(), id));
        return id;
    }

    public Usuario read(String email) throws Exception{
        int h = ParIDEmail.hash(email);
        ParIDEmail pie = indiceIndiretoEmail.read(h); 
        if (pie == null) 
            return null;

        Usuario u = read(pie.getId());
        if(u.getEmail().equalsIgnoreCase(email))
            return u;
        else
            return null; 
    }

    public boolean delete(String email) throws Exception {
        ParIDEmail pie = indiceIndiretoEmail.read(ParIDEmail.hash(email));
        if (pie != null) {
            if (super.delete(pie.getId())) {
                return indiceIndiretoEmail.delete(ParIDEmail.hash(email));
            }
        }
        return false;
    }

    @Override
    public boolean update(Usuario novoUsuario) throws Exception {
        Usuario velhoUsuario = read(novoUsuario.getEmail());
        if (super.update(novoUsuario)) {
            if (!novoUsuario.getEmail().equals(velhoUsuario.getEmail())) {
                indiceIndiretoEmail.delete(ParIDEmail.hash(velhoUsuario.getEmail()));
                indiceIndiretoEmail.create(new ParIDEmail(novoUsuario.getEmail(), novoUsuario.getId()));
            }
            return true;
        }
        return false;
    }
}