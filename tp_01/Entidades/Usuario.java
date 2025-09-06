package Entidades;

import Registro.Registro;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Usuario implements Registro {

    public int id;
    public String nome;
    public String email;
    public String hashSenha;
    public String perguntaSecreta;
    public String respostaSecreta;

    public Usuario() {
        this(-1, "", "", "", "", "");
    }

    public Usuario(String nome, String email, String hashSenha, String perguntaSecreta, String respostaSecreta) {
        this(-1, nome, email, hashSenha, perguntaSecreta, respostaSecreta);
    }

    public Usuario(int id, String nome, String email, String hashSenha, String perguntaSecreta, String respostaSecreta) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.hashSenha = hashSenha;
        this.perguntaSecreta = perguntaSecreta;
        this.respostaSecreta = respostaSecreta;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String toString() {
        return "\nID...............: " + this.id +
               "\nNome.............: " + this.nome +
               "\nEmail............: " + this.email +
               "\nHash da Senha....: " + this.hashSenha +
               "\nPergunta Secreta.: " + this.perguntaSecreta +
               "\nResposta Secreta.: " + this.respostaSecreta;
    }

    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        dos.writeInt(this.id);
        dos.writeUTF(this.nome);
        dos.writeUTF(this.email);
        dos.writeUTF(this.hashSenha);
        dos.writeUTF(this.perguntaSecreta);
        dos.writeUTF(this.respostaSecreta);

        return baos.toByteArray();
    }

    public void fromByteArray(byte[] b) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(b);
        DataInputStream dis = new DataInputStream(bais);

        this.id = dis.readInt();
        this.nome = dis.readUTF();
        this.email = dis.readUTF();
        this.hashSenha = dis.readUTF();
        this.perguntaSecreta = dis.readUTF();
        this.respostaSecreta = dis.readUTF();
    }
}
