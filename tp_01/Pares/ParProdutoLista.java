package Pares;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import Registros.RegistroArvoreBMais;

public class ParProdutoLista implements RegistroArvoreBMais<ParProdutoLista>{
    
    private int idProduto;
    private int idLista;
    private final short TAMANHO = 8; // Dois inteiros = 4 + 4 bytes

    public ParProdutoLista(){
        this(-1, -1);
    }

    public ParProdutoLista(int idProduto, int idLista){
        this.idProduto = idProduto;
        this.idLista = idLista;
    }

    public int getIdProduto(){
        return this.idProduto;
    }

    public int getIdLista(){
        return this.idLista;
    }

    @Override
    public short size(){
        return this.TAMANHO;
    }

    @Override
    public ParProdutoLista clone(){
        return new ParProdutoLista(this.idProduto, this.idLista);
    }

    @Override
    public int compareTo(ParProdutoLista x){
        if(this.idProduto != x.idProduto){
            return this.idProduto - x.idProduto;
        }
        return (this.idLista == -1) ? 0 : this.idLista - x.idLista;
    }

    @Override
    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeInt(this.idProduto);
        dos.writeInt(this.idLista);
        return baos.toByteArray();
    }

    @Override
    public void fromByteArray(byte[] ba) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(ba);
        DataInputStream dis = new DataInputStream(bais);
        this.idProduto = dis.readInt();
        this.idLista = dis.readInt();
    }
}
