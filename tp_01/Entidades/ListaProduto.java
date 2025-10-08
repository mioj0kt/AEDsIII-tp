package Entidades;

import java.io.*;
import Registros.Registro;

public class ListaProduto implements Registro {

    private int id;           // Identificador único do registro (para a interface)
    private int idLista;      // Chave estrangeira para a lista
    private int idProduto;    // Chave estrangeira para o produto
    private int quantidade;
    private String observacoes;

    // Construtores
    public ListaProduto() {
        this(-1, -1, -1, 0, "");
    }

    public ListaProduto(int id, int idLista, int idProduto, int quantidade, String observacoes) {
        this.id = id;
        this.idLista = idLista;
        this.idProduto = idProduto;
        this.quantidade = quantidade;
        this.observacoes = observacoes;
    }

    // Getters / Setters específicos
    @Override
    public int getId() { return this.id; }
    @Override
    public void setId(int id) { this.id = id; }

    public int getIdLista() { return idLista; }
    public void setIdLista(int idLista) { this.idLista = idLista; }

    public int getIdProduto() { return idProduto; }
    public void setIdProduto(int idProduto) { this.idProduto = idProduto; }

    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }

    public String getObservacoes() { return observacoes; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }

    // Serialização para CRUD (toByteArray / fromByteArray)
    @Override
    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        dos.writeInt(this.id);
        dos.writeInt(this.idLista);
        dos.writeInt(this.idProduto);
        dos.writeInt(this.quantidade);
        dos.writeUTF(this.observacoes != null ? this.observacoes : "");

        return baos.toByteArray();
    }

    @Override
    public void fromByteArray(byte[] ba) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(ba);
        DataInputStream dis = new DataInputStream(bais);

        this.id = dis.readInt();
        this.idLista = dis.readInt();
        this.idProduto = dis.readInt();
        this.quantidade = dis.readInt();
        this.observacoes = dis.readUTF();
    }

    @Override
    public String toString() {
        return "ID: " + id + " | ID Lista: " + idLista + " | ID Produto: " + idProduto + " | Quantidade: " + quantidade + " | Observacoes: " + observacoes;
    }
}
