package sistdown.action.actions;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import sistdown.model.InputsPrompt;
import sistdown.service.Caminho;
import sistdown.service.Util;


/**
 * Funcionalidade - Contexto <p>
 * Classe que permite o Sistdown migrar entre contextos para trabalhar na Rede ou na máquina local.       <p>
 * <code>LOCAL</code>: Força o Sistdown a buscar apenas os vídeos que estão armazenados na máquina local. <p>
 * <code>REDE</code>: Força o Sistdown a buscar os vídeos na rede do DNIT.
 */
public class HandleContexto implements Acao {


    public void executa() throws Exception {
        for (int i=0; i<InputsPrompt.sizeList(); i++) {
            String param = InputsPrompt.listaComInputs.get(i);
            if (param.equalsIgnoreCase("LOCAL"))  {
                InputsPrompt.listaComInputs.remove(i);
                if (!Util.contexto.equalsIgnoreCase("LOCAL")) {
                    boolean trocou = toggleContexto(Caminho.SISTDOWN_CURRENT, Caminho.SISTDOWN_SHORTCUT_REDE, 
                                                    Caminho.SISTDOWN_DOWNLOADS_LOCAL, Caminho.SISTDOWN_CURRENT);
                    mudandoContexto("LOCAL", i, trocou);
                }
            } else if (param.toLowerCase().equalsIgnoreCase("REDE")) {
                if (!Util.contexto.equalsIgnoreCase("REDE")) {
                    InputsPrompt.listaComInputs.remove(i);
                    boolean trocou = toggleContexto(Caminho.SISTDOWN_CURRENT, Caminho.SISTDOWN_DOWNLOADS_LOCAL, 
                                                    Caminho.SISTDOWN_SHORTCUT_REDE, Caminho.SISTDOWN_CURRENT);
                    mudandoContexto("REDE", i, trocou);
                }
            } 
        }
    }



    /**
     * Faz o toggle do contexto dos arquivos, trocando a pasta intermediria Current que guarda os Vídeos,
     * migra entre a pasta para apontar para a REDE ou para o repositorio LOCAL com os Downloads.
     */
    private static boolean toggleContexto(File file1, File target1, File file2, File target2) {
        boolean trocouPrimeiroArquivo = file1.renameTo(target1);
        if (trocouPrimeiroArquivo == false)
            return false;
        boolean trocouSegundoArquivo = file2.renameTo(target2);
        if (trocouSegundoArquivo == true)
            return true;
        target1.renameTo(file1);
        return false;
    }



    /**
     * Rotina executada para mudar de contexto entre a LOCAL e a REDE.
     */
    private static void mudandoContexto(String novoContexto, int id, boolean trocou) throws Exception {
        if (trocou) {
            System.out.println(" * Mudando o contexto para " + novoContexto);
            Util.contexto = novoContexto.toUpperCase();
            Files.write(Caminho.SISTDOWN_CONFIG_CONTEXTO.toPath(), Util.contexto.getBytes(), 
                        StandardOpenOption.TRUNCATE_EXISTING);
        } else { 
            System.out.println(" * Não foi possível trocar de contexto");
        }
    }
    
}
