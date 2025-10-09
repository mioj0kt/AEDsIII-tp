package Pares;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import Registros.RegistroArvoreBMais;

public class ParListaProduto implements RegistroArvoreBMais<ParListaProduto>{
    
    private int idLista;
    private int idProduto;
    private final short TAMANHO = 8; // Dois inteiros = 4 + 4 bytes

    public ParListaProduto(){
        this(-1, -1);
    }

    public ParListaProduto(int idLista, int idProduto){
        this.idLista = idLista;
        this.idProduto = idProduto;
    }
    
    public int getIdLista(){
        return this.idLista;
    }

    public int getIdProduto(){
        return this.idProduto;
    }

    @Override
    public short size(){
        return this.TAMANHO;
    }

    @Override
    public ParListaProduto clone(){
        return new ParListaProduto(this.idLista, this.idProduto);
    }

    @Override
    public int compareTo(ParListaProduto x){
        if(this.idLista != x.idLista){
            return this.idLista - x.idLista;
        }
        return (this.idProduto == -1) ? 0 : this.idProduto - x.idProduto;
    }

    @Override
    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeInt(this.idLista);
        dos.writeInt(this.idProduto);
        return baos.toByteArray();
    }

    @Override
    public void fromByteArray(byte[] ba) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(ba);
        DataInputStream dis = new DataInputStream(bais);
        this.idLista = dis.readInt();
        this.idProduto = dis.readInt();
    }
}
