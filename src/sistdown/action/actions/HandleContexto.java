package action.actions;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

import model.Inputs;
import service.Caminhos;
import service.Util;

public class HandleContexto implements Acao {

    /**
     * Faz o tratamento caso tenha sido inseridos inputs de Configuração.
     * Altera de contexto entre a maquina local e a rede.
     */
    public void executa() throws Exception {
        for (int i=0; i<Inputs.size(); i++) {
            String param = Inputs.listaComInputs.get(i);
            if (param.equalsIgnoreCase("LOCAL"))  {
                Inputs.listaComInputs.remove(i);
                if (!Util.contexto.equalsIgnoreCase("LOCAL")) {
                    boolean trocou = toggleContexto(Caminhos.SISTDOWN_CURRENT, Caminhos.SISTDOWN_SHORTCUT_REDE, 
                                                    Caminhos.SISTDOWN_DOWNLOADS_LOCAL, Caminhos.SISTDOWN_CURRENT);
                    mudandoContexto("LOCAL", i, trocou);
                }
            } else if (param.toLowerCase().equalsIgnoreCase("rede")) {
                if (!Util.contexto.equalsIgnoreCase("rede")) {
                    Inputs.listaComInputs.remove(i);
                    boolean trocou = toggleContexto(Caminhos.SISTDOWN_CURRENT, Caminhos.SISTDOWN_DOWNLOADS_LOCAL, 
                                                    Caminhos.SISTDOWN_SHORTCUT_REDE, Caminhos.SISTDOWN_CURRENT);
                    mudandoContexto("rede", i, trocou);
                }
            } 
        }
    }



    /**
     * Faz o toggle do contexto dos arquivos, trocando a pasta intermedia Current que guarda os Vídeos,
     * migra entre a pasta apontar para a rede ou para o repositorio local com os Downloads.
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
     * Rotina executada para mudar de contexto entre a local e a rede.
     */
    private static void mudandoContexto(String novoContexto, int id, boolean trocou) throws Exception {
        if (trocou) {
            System.out.println(" * Mudando o contexto para " + novoContexto);
            Util.contexto = novoContexto.toUpperCase();
            Files.write(Caminhos.SISTDOWN_CONFIG_CONTEXTO.toPath(), Util.contexto.getBytes(), 
                        StandardOpenOption.TRUNCATE_EXISTING);
        } else { 
            System.out.println(" * Não foi possível trocar de contexto");
        }
    }
    
}
