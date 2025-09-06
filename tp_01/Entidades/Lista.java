package Entidades;

import Registro.Registro;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.time.LocalDate;

public class Lista implements Registro {

    public int id;
    public int idUsuario;
    public String nome;
    public String descricao;
    public LocalDate dataCriacao;
    public LocalDate dataLimite;
    public String codigoCompartilhavel;

    // Construtor completo
    public Lista(int id, int idUsuario, String nome, String descricao, LocalDate dataCriacao,
                 LocalDate dataLimite, String codigoCompartilhavel) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.nome = nome;
        this.descricao = descricao;
        this.dataCriacao = dataCriacao;
        this.dataLimite = dataLimite;
        this.codigoCompartilhavel = codigoCompartilhavel;
    }

    // Construtor vazio
    public Lista() {
        this(-1, -1, "", "", LocalDate.now(), null, "");
    }

    // Construtor sem ID (para novos registros)
    public Lista(int idUsuario, String nome, String descricao, LocalDate dataLimite, String codigoCompartilhavel) {
        this(-1, idUsuario, nome, descricao, LocalDate.now(), dataLimite, codigoCompartilhavel);
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Override
    public String toString() {
        return "\nID.................: " + this.id +
               "\nID Usuário........: " + this.idUsuario +
               "\nNome..............: " + this.nome +
               "\nDescrição.........: " + this.descricao +
               "\nData Criação......: " + this.dataCriacao +
               "\nData Limite.......: " + (this.dataLimite != null ? this.dataLimite : "Não definida") +
               "\nCódigo Compart....: " + this.codigoCompartilhavel;
    }

    @Override
    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        dos.writeInt(this.id);
        dos.writeInt(this.idUsuario);
        dos.writeUTF(this.nome);
        dos.writeUTF(this.descricao);
        dos.writeInt((int) this.dataCriacao.toEpochDay());
        dos.writeBoolean(this.dataLimite != null);
        if (this.dataLimite != null) {
            dos.writeInt((int) this.dataLimite.toEpochDay());
        }
        dos.writeUTF(this.codigoCompartilhavel);

        return baos.toByteArray();
    }

    @Override
    public void fromByteArray(byte[] ba) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(ba);
        DataInputStream dis = new DataInputStream(bais);

        this.id = dis.readInt();
        this.idUsuario = dis.readInt();
        this.nome = dis.readUTF();
        this.descricao = dis.readUTF();
        this.dataCriacao = LocalDate.ofEpochDay(dis.readInt());

        boolean temDataLimite = dis.readBoolean();
        if (temDataLimite) {
            this.dataLimite = LocalDate.ofEpochDay(dis.readInt());
        } else {
            this.dataLimite = null;
        }

        this.codigoCompartilhavel = dis.readUTF();
    }
}
