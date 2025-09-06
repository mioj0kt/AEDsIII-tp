package Entidades;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.time.LocalDate;

public class Lista{

    protected int ID;
    protected int IDUsuario;
    protected String Nome;
    protected String Descricao;
    protected LocalDate DataCriacao;
    protected LocalDate DataLimite;
    protected String CodigoCompartilhavel;

    // Construtor Completo
    public Lista(int id, int idUsuario, String nome, String descricao, LocalDate dataCriacao,
                 LocalDate dataLimite, String codigoCompartilhavel){
        ID = id;
        IDUsuario = idUsuario;
        Nome = nome;
        Descricao = descricao;
        DataCriacao = dataCriacao;
        DataLimite = dataLimite;
        CodigoCompartilhavel = codigoCompartilhavel;
    }

    // Construtor Vazio
    public Lista(){
        ID = -1;
        IDUsuario = -1;
        Nome = "";
        Descricao = "";
        DataCriacao = LocalDate.now();
        DataLimite = null;
        CodigoCompartilhavel = "";
    }

    // Construtor sem ID (para novos registros)
    public Lista(int idUsuario, String nome, String descricao, LocalDate dataLimite, String codigoCompartilhavel){
        ID = -1;
        IDUsuario = idUsuario;
        Nome = nome;
        Descricao = descricao;
        DataCriacao = LocalDate.now();
        DataLimite = dataLimite;
        CodigoCompartilhavel = codigoCompartilhavel;
    }

    // Getters e Setters
    public int getId(){
        return ID;
    }

    public void setId(int id){
        ID = id;
    }

    public int getIdUsuario(){
        return IDUsuario;
    }

    public void setIdUsuario(int idUsuario){
        IDUsuario = idUsuario;
    }

    public String getNome(){
        return Nome;
    }

    public void setNome(String nome){
        Nome = nome;
    }

    public String getDescricao(){
        return Descricao;
    }

    public void setDescricao(String descricao){
        Descricao = descricao;
    }

    public LocalDate getDataCriacao(){
        return DataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao){
        DataCriacao = dataCriacao;
    }

    public LocalDate getDataLimite(){
        return DataLimite;
    }

    public void setDataLimite(LocalDate dataLimite){
        DataLimite = dataLimite;
    }

    public String getCodigoCompartilhavel(){
        return CodigoCompartilhavel;
    }

    public void setCodigoCompartilhavel(String codigoCompartilhavel){ 
        CodigoCompartilhavel = codigoCompartilhavel;
    }

    @Override
    public String toString(){
        return "\nID: " + ID +
               "\nID Usuário: " + IDUsuario +
               "\nNome: " + Nome +
               "\nDescrição: " + Descricao +
               "\nData Criação: " + DataCriacao +
               "\nData Limite: " + (DataLimite != null ? DataLimite : "Não definida") +
               "\nCódigo Compartilhável: " + CodigoCompartilhavel;
    }

    public byte[] toByteArray() throws IOException{
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        dos.writeInt(ID);
        dos.writeInt(IDUsuario);
        dos.writeUTF(Nome);
        dos.writeUTF(Descricao);
        dos.writeInt((int) DataCriacao.toEpochDay());
        dos.writeBoolean(DataLimite != null);
        if (DataLimite != null) {
            dos.writeInt((int) DataLimite.toEpochDay());
        }
        dos.writeUTF(CodigoCompartilhavel);

        return baos.toByteArray();
    }

    public void fromByteArray(byte[] ba) throws IOException{
        ByteArrayInputStream bais = new ByteArrayInputStream(ba);
        DataInputStream dis = new DataInputStream(bais);

        ID = dis.readInt();
        IDUsuario = dis.readInt();
        Nome = dis.readUTF();
        Descricao = dis.readUTF();
        DataCriacao = LocalDate.ofEpochDay(dis.readInt());
        boolean temDataLimite = dis.readBoolean();
        if (temDataLimite){
            DataLimite = LocalDate.ofEpochDay(dis.readInt());
        } else{
            DataLimite = null;
        }
        CodigoCompartilhavel = dis.readUTF();
    }
}
