<<<<<<<< HEAD:tp_01/Registro/Registro.java
package Registro;

import java.io.IOException;

public interface Registro{
    public void setId(int i);
    public int getId();
    public byte[] toByteArray() throws IOException;
    public void fromByteArray(byte[] b) throws IOException;
========
package Registros;

import java.io.IOException;

public interface Registro{
    public void setId(int i);
    public int getId();
    public byte[] toByteArray() throws IOException;
    public void fromByteArray(byte[] b) throws IOException;
>>>>>>>> main:tp_01/Registros/Registro.java
}