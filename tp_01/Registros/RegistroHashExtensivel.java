<<<<<<<< HEAD:tp_01/Registro/RegistroHashExtensivel.java
package Registro;
========
package Registros;
>>>>>>>> main:tp_01/Registros/RegistroHashExtensivel.java

import java.io.IOException;

public interface RegistroHashExtensivel<T> {

  public int hashCode(); // chave numérica para ser usada no diretório

  public short size(); // tamanho FIXO do registro

  public byte[] toByteArray() throws IOException; // representação do elemento em um vetor de bytes

  public void fromByteArray(byte[] ba) throws IOException; // vetor de bytes a ser usado na construção do elemento

}