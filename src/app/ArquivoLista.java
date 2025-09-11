package app;

import aed3.*;
import java.util.ArrayList;
import java.util.List;

public class ArquivoLista extends Arquivo<Lista> {

    private ArvoreBMais<ParUsuarioLista> indiceUsuarioLista;

    public ArquivoLista() throws Exception {
        super("listas", Lista.class.getConstructor());
        indiceUsuarioLista = new ArvoreBMais<>(ParUsuarioLista.class.getConstructor(), 5, "dados/listas/usuarioLista.idx");
    }

    public List<Lista> readAll(int idUsuario) throws Exception {
        List<Lista> listasDoUsuario = new ArrayList<>();
        ArrayList<ParUsuarioLista> pares = indiceUsuarioLista.read(new ParUsuarioLista(idUsuario, -1));
        
        for (ParUsuarioLista par : pares) {
            Lista lista = super.read(par.getIdLista());
            if (lista != null) {
                listasDoUsuario.add(lista);
            }
        }
        
        return listasDoUsuario;
    }
    
    @Override
    public int create(Lista obj) throws Exception {
        obj.setCodigoCompartilhavel(NanoID.generate());
        int idLista = super.create(obj);
        obj.setId(idLista);
        
        // Aqui verificamos se a inserção no índice foi bem-sucedida.
        boolean inseridoNoIndice = indiceUsuarioLista.create(new ParUsuarioLista(obj.getIdUsuario(), idLista));
        
        if (!inseridoNoIndice) {
            // Se a inserção no índice falhar, desfaz a criação no arquivo principal
            // para manter a consistência dos dados.
            super.delete(idLista);
            throw new Exception("Não foi possível inserir a lista no índice da Árvore B+. A operação foi cancelada.");
        }
        
        return idLista;
    }

    @Override
    public boolean delete(int idLista) throws Exception {
        Lista lista = super.read(idLista);
        if (lista == null) {
            return false;
        }

        if (super.delete(idLista)) {
            return indiceUsuarioLista.delete(new ParUsuarioLista(lista.getIdUsuario(), idLista));
        }
        
        return false;
    }
}