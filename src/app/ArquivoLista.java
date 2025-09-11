import aed3.*;
import java.util.ArrayList;
import java.util.List;

public class ArquivoLista extends Arquivo<Lista> {

    // private ArvoreBMais<ParNomeID> indiceNome; // Descomente quando tiver a Árvore B+

    public ArquivoLista() throws Exception {
        super("listas", Lista.class.getConstructor());
        // indiceNome = new ArvoreBMais<>(ParNomeID.class.getConstructor(), 5, "listas.nome.idx");
    }

    /**
     * Retorna todas as listas que pertencem a um usuário específico.
     * ATENÇÃO: Esta é uma implementação temporária que lê o arquivo inteiro.
     * A versão final deve usar um índice (Hash ou Árvore B+) no ID do usuário 
     * para otimizar essa busca.
     * @param idUsuario O ID do usuário logado.
     * @return Uma lista de Listas.
     */
    public List<Lista> readAll(int idUsuario) throws Exception {
        List<Lista> listasDoUsuario = new ArrayList<>();
        arquivo.seek(TAM_CABECALHO); // Pula o cabeçalho do arquivo
        
        while (arquivo.getFilePointer() < arquivo.length()) {
            byte lapide = arquivo.readByte();
            short tam = arquivo.readShort();
            byte[] dados = new byte[tam];
            arquivo.read(dados);

            if (lapide == ' ') {
                Lista l = construtor.newInstance();
                l.fromByteArray(dados);
                if (l.getIdUsuario() == idUsuario) {
                    listasDoUsuario.add(l);
                }
            }
        }
        return listasDoUsuario;
    }
    
    // O método create precisará ser ajustado para atualizar o índice da Árvore B+
    @Override
    public int create(Lista obj) throws Exception {
        // Gera o código único antes de salvar
        obj.setCodigoCompartilhavel(NanoID.generate());
        int id = super.create(obj);
        // indiceNome.create(new ParNomeID(obj.getNome(), id)); // Descomente quando tiver a Árvore B+
        return id;
    }

    // Os métodos delete e update também precisarão atualizar o índice da Árvore B+
}