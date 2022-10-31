package action;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import model.TagsConfiguracao;
import model.Trechos;

public class Acoes {

    private static List<String> listaComIdsEscolhidos;
    private static String contexto;



    /**
     * Configurações de inicializacao:
     * (i)   Da update no Sistdown, 
     * (ii)  Cria pastas de inicialização, e
     * (iii) Carrega os possiveis trechos para download.
     */
    public static void inicializacao() throws Exception {
        Update.V2();
        Caminhos.criarPastas();
        Caminhos.criarArquivos();
        Util.carregaTrechosDisponiveis();
        listaComIdsEscolhidos = Collections.synchronizedList(new ArrayList<>());
    }



    /**
     * Faz o print na tela dos textos de inicialização.
     * Printa a logo de inicio ou a logo de reinicializacao,
     * bem como também printa os trechos que foram baixados.
     */
    public static void printaInicio() throws Exception {
        if (primeiraRun()) {
            System.out.println(" ================================================================================");
            String message = """
                     *      __  _
                     *    /  _|| |_   _   _ __   _____      __
                     *    | |_ | | | | | | '_ \\ / _ \\ \\ /\\ / /
                     *    |  _|| | |_| | | | | | (_) \\ V  V /
                     *    |_|  |_|\\__, | |_| |_|\\___/ \\_/\\_/                     
                     *            |___/                                              SIST-DOWN v2.0.0
                    """;
            System.out.print(message);
        } else {
            System.out.println(" * SISTDOWN Reiniciado");
        }
        contexto = Files.readAllLines(Caminhos.SISTDOWN_CONFIG_CONTEXTO.toPath()).get(0);
        System.out.println(" ================================================================================");
        System.out.println(" * MODO: " + contexto);
        Util.printaTrechosQueEstaoNaMaquinaLocal();
        System.out.println(" ================================================================================");
        if (primeiraRun()) {
            System.out.println(" * Para 'baixar' trechos, digite os ids separados por virgula.  Ex: 100, 101, 102");
            System.out.println(" * Para 'limpar' e também baixar acrescente o parametro limpa.  Ex: limpa, 85, 86");    
        }
        System.out.println(" * Para mudar o contexto do Sistlev, digite 'rede' ou 'local'");
        System.out.println(" ================================================================================");
        System.out.print(" FLY-now> ");
    }



    /**
     * Cria na tela um prompt pedindo inputs de:
     * (i)  Trechos para baixar na maquina local,
     * (ii) Configurações que manipulam o Sistdown.
     */
    public static void criaPromptPedindoInputs() {
        Scanner scanner = new Scanner(System.in);
        String row = scanner.nextLine().replaceAll("\\s+", ",").replaceAll("[;]", ",").replaceAll("[/]", ",").replaceAll("[.]", ",");
        if (!row.contains(",")) {
            adicionaNaLista(row);
        } else {
            String[] rows = row.split(",");
            for (int i = 0; i < rows.length; i++) {
                adicionaNaLista(rows[i]);
            }
        }
        System.out.println();
    }



    /**
     * Adiciona na lista os inputs digitados
     */
    private static void adicionaNaLista(String trecho) {
        if (Util.isValid(trecho)) {
            if (TagsConfiguracao.isTag(trecho)) {
                    listaComIdsEscolhidos.add(trecho.toUpperCase());
            } else {
                trecho = trecho.replaceAll("[^\\d.]", "");
                if (Util.isValid(trecho))
                    listaComIdsEscolhidos.add(trecho);
            }
        }
    }



    /**
     * Faz o tratamento caso tenha sido inseridos inputs de Configuração.
     * (i)  Faz a limpeza dos trechos guardados na maquina local,
     * (ii) Altera de contexto entre a maquina local e a rede.
     */
    public static void inputsDeConfiguracao() throws Exception {
        for (int i=0; i<listaComIdsEscolhidos.size(); i++) {
            String param = listaComIdsEscolhidos.get(i);
            if (param.equalsIgnoreCase("local") && !contexto.equalsIgnoreCase("local")) {
                boolean trocou = toggleContexto(Caminhos.SISTDOWN_CURRENT, Caminhos.SISTDOWN_SHORTCUT_REDE, 
                                               Caminhos.SISTDOWN_DOWNLOADS_LOCAL, Caminhos.SISTDOWN_CURRENT);
                mudandoContexto("local", i, trocou);
            } else if (param.toLowerCase().equalsIgnoreCase("rede") && !contexto.equalsIgnoreCase("rede")) {
                boolean trocou = toggleContexto(Caminhos.SISTDOWN_CURRENT, Caminhos.SISTDOWN_DOWNLOADS_LOCAL, 
                                               Caminhos.SISTDOWN_SHORTCUT_REDE, Caminhos.SISTDOWN_CURRENT);
                mudandoContexto("rede", i, trocou);
            } else if (param.equalsIgnoreCase("limpa") || param.equalsIgnoreCase("limpar")) {
                listaComIdsEscolhidos.remove(i);
                Util.deleteFolder(Caminhos.SISTDOWN_CURRENT);
                Util.deleteFolder(Caminhos.SISTDOWN_DOWNLOADS_LOCAL);
                FileWriter f = new FileWriter(Caminhos.SISTDOWN_CONFIG_INFODOWNLOADS, false);
                f.close();
                System.out.println(" * ...Pasta Limpa");
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
            contexto = novoContexto;
            Files.write(Caminhos.SISTDOWN_CONFIG_CONTEXTO.toPath(), contexto.getBytes(), 
                        StandardOpenOption.TRUNCATE_EXISTING);
        } else { 
            System.out.println(" * Não foi possível trocar de contexto");
        }
        listaComIdsEscolhidos.remove(id);
    }



    /**
     * Faz o download dos trechos na maquina local
     */
    public static void inputsDeDownload() throws Exception {
        if (listaComIdsEscolhidos.size() > 0) {
            System.out.println(" * ...Iniciando o download dos trechos");
        }
        while (listaComIdsEscolhidos.size() > 0) {
            int index = 0;
            String idTrecho = listaComIdsEscolhidos.get(index);
            String caminhoTrecho = Trechos.getCaminho(idTrecho);
            if (caminhoTrecho != null) {
                Path caminhoRede = Paths.get(Caminhos.REDE_VIDEO_FOLDER.toString(), caminhoTrecho);
                Path caminhoSistdown = Paths.get(Caminhos.SISTDOWN_CURRENT.toString(), caminhoTrecho);
                if (contexto.equalsIgnoreCase("local"))
                    caminhoSistdown = Paths.get(Caminhos.SISTDOWN_CURRENT.toString(), caminhoTrecho);
                else 
                    caminhoSistdown = Paths.get(Caminhos.SISTDOWN_DOWNLOADS_LOCAL.toString(), caminhoTrecho);
                Util.deleteFolder(caminhoSistdown.toFile());
                Util.copyFolder(caminhoRede, caminhoSistdown, StandardCopyOption.REPLACE_EXISTING);
                String nomeTrecho = idTrecho + "-" + caminhoSistdown.toString().replaceAll(".+_", "").substring(0, 5);
                Files.write(Caminhos.SISTDOWN_CONFIG_INFODOWNLOADS.toPath(), (nomeTrecho + ",  ").getBytes(), StandardOpenOption.APPEND);
                System.out.println(" * ...>Baixado " + nomeTrecho);
            } else {
                if (!TagsConfiguracao.isTag(idTrecho)) {
                    System.out.println(" * ...Não foi encontrado o trecho de id: " + listaComIdsEscolhidos.get(index) + ".");
                }
            }
            listaComIdsEscolhidos.remove(index);
        } 
    }



    /**
     * Cria alguns separadores de linhas e 
     * informa a reinicialização do Sistdown.
     */
    public static void printaFim(){
        Util.inicializacoes++;
        System.out.println("\n\n\n\n\n\n\n");
    }



    /**
     * Verifica se é a primeira vez rodando 
     * ou se é uma reinicialização do Sistdown.
     */
    public static boolean primeiraRun() {
        if (Util.inicializacoes == 0)
            return true;
        return false;
    }


}
