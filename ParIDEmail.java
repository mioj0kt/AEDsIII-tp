import aed3.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class ParIDEmail implements RegistroHashExtensivel<ParIDEmail> {

    private String email;
    private int id;
    private final short TAMANHO = 72; // 64 bytes para email + 4 para ID + padding

    public ParIDEmail() {
        this.email = "";
        this.id = -1;
    }

    public ParIDEmail(String email, int id) {
        this.email = email;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public int hashCode() {
        return hash(email);
    }

    public short size() {
        return TAMANHO;
    }

    public String toString() {
        return "(" + email + ";" + id + ")";
    }

    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        // cria buffer de email com 64 bytes zerados
        byte[] bufferEmail = new byte[64];
        Arrays.fill(bufferEmail, (byte) 0);

        // copia os bytes do email para dentro do buffer zerado
        byte[] emailBytes = email.getBytes("UTF-8");
        System.arraycopy(emailBytes, 0, bufferEmail, 0, Math.min(emailBytes.length, 64));

        dos.write(bufferEmail);
        dos.writeInt(id);

        return baos.toByteArray();
    }

    
    public void fromByteArray(byte[] ba) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(ba);
        DataInputStream dis = new DataInputStream(bais);
    
        byte[] bufferEmail = new byte[64];
        dis.readFully(bufferEmail);
    
        // remove caracteres nulos (\u0000) e espaços extras
        email = new String(bufferEmail, "UTF-8")
                    .replace("\u0000", "")
                    .trim();
    
        id = dis.readInt();
    }
    
    

    public static int hash(String email) {
        if (email == null || email.isEmpty()) throw new IllegalArgumentException("Email não pode ser nulo ou vazio.");
        email = email.trim().toLowerCase(); // normalização
        long hashValue = 0;
        for (int i = 0; i < email.length(); i++) {
            hashValue = (hashValue * 31 + email.charAt(i)) % 1000000007;
        }
        return (int) Math.abs(hashValue);
    }
    
}
