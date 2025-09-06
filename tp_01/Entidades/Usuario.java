package Entidades;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Usuario{

    protected int ID;
    protected String Nome;
    protected String Email;
    protected String HashSenha;
    protected String PerguntaSecreta;
    protected String RespostaSecreta;

    // Construtor Completo
    public Usuario(int id, String nome, String email, String hashSenha, String perguntaSecreta, String respostaSecreta){
        ID = id;
        Nome = nome;
        Email = email;
        HashSenha = hashSenha;
        PerguntaSecreta = perguntaSecreta;
        RespostaSecreta = respostaSecreta;
    }

    // Construtor Vazio
    public Usuario(){
        ID = -1;
        Nome = "";
        Email = "";
        HashSenha = "";
        PerguntaSecreta = "";
        RespostaSecreta = "";
    }

    // Construtor sem ID
    public Usuario(String nome, String email, String hashSenha, String perguntaSecreta, String respostaSecreta){
        ID = -1;
        Nome = nome;
        Email = email;
        HashSenha = hashSenha;
        PerguntaSecreta = perguntaSecreta;
        RespostaSecreta = respostaSecreta;
    }

    // Getters e Setters
    public int getId(){
        return ID;
    }

    public void setId(int id){
        ID = id;
    }

    public String getNome(){
        return Nome;
    }

    public void setNome(String nome){
        Nome = nome;
    }

    public String getEmail(){
        return Email;
    }

    public void setEmail(String email){
        Email = email;
    }

    public String getHashSenha(){
        return HashSenha;
    }

    public void setHashSenha(String hashSenha){ 
        HashSenha = hashSenha;
    }

    public String getPerguntaSecreta(){
        return PerguntaSecreta;
    }

    public void setPerguntaSecreta(String perguntaSecreta){
        PerguntaSecreta = perguntaSecreta;
    }

    public String getRespostaSecreta(){
        return RespostaSecreta;
    }

    public void setRespostaSecreta(String respostaSecreta){
        RespostaSecreta = respostaSecreta;
    }

    public String toString(){
        return "\nID: " + ID +
               "\nNome: " + Nome +
               "\nEmail: " + Email +
               "\nHash da Senha: " + HashSenha +
               "\nPergunta Secreta: " + PerguntaSecreta +
               "\nResposta Secreta: " + RespostaSecreta;
    }

    public byte[] toByteArray() throws IOException{
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        dos.writeInt(ID);
        dos.writeUTF(Nome);
        dos.writeUTF(Email);
        dos.writeUTF(HashSenha);
        dos.writeUTF(PerguntaSecreta);
        dos.writeUTF(RespostaSecreta);

        return baos.toByteArray();
    }

    public void fromByteArray(byte[] ba) throws IOException{
        ByteArrayInputStream bais = new ByteArrayInputStream(ba);
        DataInputStream dis = new DataInputStream(bais);

        ID = dis.readInt();
        Nome = dis.readUTF();
        Email = dis.readUTF();
        HashSenha = dis.readUTF();
        PerguntaSecreta = dis.readUTF();
        RespostaSecreta = dis.readUTF();
    }
}
