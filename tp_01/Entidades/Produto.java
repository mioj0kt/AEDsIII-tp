package Entidades;

import java.io.*;
import Registros.Registro;

public class Produto implements Registro{
    
    private int id;
    private String gtin13;
    private String nome;
    private String descricao;

    public Produto(){
        this(-1, "", "", "");
    }

    public Produto(int id, String gtin13, String nome, String descricao){
        this.id = id;
        this.gtin13 = gtin13;
        this.nome = nome;
        this.descricao = descricao;
    }

    // getters / setters
    @Override
    public int getId() { return id; }
    @Override
    public void setId(int id) { this.id = id; }

    public String getGtin13() { return gtin13; }
    public void setGtin13(String gtin13) { this.gtin13 = gtin13; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    // serialização para o CRUD (toByteArray / fromByteArray)
    @Override
    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        dos.writeInt(this.id);
        dos.writeUTF(this.gtin13 != null ? this.gtin13 : "");
        dos.writeUTF(this.nome != null ? this.nome : "");
        dos.writeUTF(this.descricao != null ? this.descricao : "");

        return baos.toByteArray();
    }

    @Override
    public void fromByteArray(byte[] ba) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(ba);
        DataInputStream dis = new DataInputStream(bais);

        this.id = dis.readInt();
        this.gtin13 = dis.readUTF();
        this.nome = dis.readUTF();
        this.descricao = dis.readUTF();
    }

    @Override
    public String toString(){
        return "ID: " + id + " | GTIN-13: " + gtin13 + " | Nome: " + nome + " | Descricao: " + descricao;
    }
}
