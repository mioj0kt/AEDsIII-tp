package Pares;

import java.io.*;

<<<<<<< HEAD
import Registro.RegistroArvoreBMais;
=======
import Registros.RegistroArvoreBMais;
>>>>>>> main

public class ParUsuarioLista implements RegistroArvoreBMais<ParUsuarioLista> {

    private int idUsuario;
    private int idLista;
    private final short TAMANHO = 8; // Dois inteiros = 4 + 4 bytes

    public ParUsuarioLista() {
        this(-1, -1);
    }

    public ParUsuarioLista(int idUsuario, int idLista) {
        this.idUsuario = idUsuario;
        this.idLista = idLista;
    }

<<<<<<< HEAD
    public int getIdUsuario() { return this.idUsuario; }
    public int getIdLista() { return this.idLista; }
=======
    public int getIdUsuario() {
        return this.idUsuario;
    }

    public int getIdLista() {
        return this.idLista;
    }
>>>>>>> main

    @Override
    public short size() {
        return this.TAMANHO;
    }
<<<<<<< HEAD
    
=======

>>>>>>> main
    @Override
    public ParUsuarioLista clone() {
        return new ParUsuarioLista(this.idUsuario, this.idLista);
    }

<<<<<<< HEAD
    //Compara dois pares. A comparação primária é pelo idUsuario.
=======
    // Compara dois pares. A comparação primária é pelo idUsuario.
>>>>>>> main
    @Override
    public int compareTo(ParUsuarioLista outro) {
        if (this.idUsuario != outro.idUsuario) {
            return this.idUsuario - outro.idUsuario;
        }
        return (this.idLista == -1) ? 0 : this.idLista - outro.idLista;
    }

    @Override
    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeInt(this.idUsuario);
        dos.writeInt(this.idLista);
        return baos.toByteArray();
    }

    @Override
    public void fromByteArray(byte[] ba) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(ba);
        DataInputStream dis = new DataInputStream(bais);
        this.idUsuario = dis.readInt();
        this.idLista = dis.readInt();
    }
}