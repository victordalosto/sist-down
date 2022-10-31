package action;
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
import model.Tags;
import model.Trechos;
import mslinks.ShellLink;

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
        if (primeiraVezRodando()) {
            System.out.println(" ================================================================================");
            String message = """
                     *      __  _
                     *    /  _|| |_   _   _ __   _____      __
                     *    | |_ | | | | | | '_ \\ / _ \\ \\ /\\ / /
                     *    |  _|| | |_| | | | | | (_) \\ V  V /
                     *    |_|  |_|\\__, | |_| |_|\\___/ \\_/\\_/                     
                     *            |___/                                              SIST-DOWN v1.9.7
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
        if (primeiraVezRodando()) {
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
            if (Tags.isTag(trecho)) {
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
                listaComIdsEscolhidos.remove(i);
                mudandoContexto("local");
                Caminhos.SISTDOWN_SHORTCUT_REDE.delete();
                Caminhos.SISTDOWN_DOWNLOADS_TEMPORARY.renameTo(Caminhos.SISTDOWN_DOWNLOADS);
            } else if (param.toLowerCase().equalsIgnoreCase("rede") && !contexto.equalsIgnoreCase("rede")) {
                listaComIdsEscolhidos.remove(i);
                mudandoContexto("rede");
                if (Caminhos.SISTDOWN_DOWNLOADS.isDirectory())
                    Caminhos.SISTDOWN_DOWNLOADS.renameTo(Caminhos.SISTDOWN_DOWNLOADS_TEMPORARY);
                ShellLink.createLink(Caminhos.REDE_VIDEOS_FOLDER.toString(), Caminhos.SISTDOWN_SHORTCUT_REDE.toString());
            } else if (param.equalsIgnoreCase("limpa") || param.equalsIgnoreCase("limpar")) {
                listaComIdsEscolhidos.remove(i);
                Util.deleteFolder(Caminhos.SISTDOWN_DOWNLOADS);
                Util.deleteFolder(Caminhos.SISTDOWN_DOWNLOADS_TEMPORARY);
                FileWriter f = new FileWriter(Caminhos.SISTDOWN_CONFIG_INFODOWNLOADS, false);
                f.close();
                System.out.println(" * ...Pasta Limpa");
            }
        }
    }



    /**
     * Passos executados para mudar de contexto entre a local e a rede.
     */
    private static void mudandoContexto(String novoContexto) throws Exception {
        System.out.println(" * Mudando o contexto para " + novoContexto);
        contexto = novoContexto;
        Files.write(Caminhos.SISTDOWN_CONFIG_CONTEXTO.toPath(), (contexto).getBytes(), StandardOpenOption.TRUNCATE_EXISTING);
    }



    /**
     * Faz o download dos trechos na maquina local
     */
    public static void downloadFolders() throws Exception {
        if (listaComIdsEscolhidos.size() > 0) {
            System.out.println(" * ...Iniciando o download dos trechos");
        }
        while (listaComIdsEscolhidos.size() > 0) {
            int index = 0;
            String idTrecho = listaComIdsEscolhidos.get(index);
            String caminhoTrecho = Trechos.getCaminho(idTrecho);
            if (caminhoTrecho != null) {
                Path caminhoRede = Paths.get(Caminhos.REDE_VIDEOS_FOLDER.toString(), caminhoTrecho);
                Path caminhoSistdown = Paths.get(Caminhos.SISTDOWN_DOWNLOADS.toString(), caminhoTrecho);
                if (contexto.equalsIgnoreCase("local"))
                    caminhoSistdown = Paths.get(Caminhos.SISTDOWN_DOWNLOADS.toString(), caminhoTrecho);
                else 
                    caminhoSistdown = Paths.get(Caminhos.SISTDOWN_DOWNLOADS_TEMPORARY.toString(), caminhoTrecho);
                Util.deleteFolder(caminhoSistdown.toFile());
                Util.copyFolder(caminhoRede, caminhoSistdown, StandardCopyOption.REPLACE_EXISTING);
                String nomeTrecho = idTrecho + "-" + caminhoSistdown.toString().replaceAll(".+_", "").substring(0, 5);
                System.out.println(" *Baixado " + nomeTrecho);
                Files.write(Caminhos.SISTDOWN_CONFIG_INFODOWNLOADS.toPath(), (nomeTrecho + ",  ").getBytes(), StandardOpenOption.APPEND);
            } else {
                System.out.println(" * ...Não foi encontrado o trecho de id: " + listaComIdsEscolhidos.get(index) + ".");
            }
            listaComIdsEscolhidos.remove(index);
        } 
    }



    /**
     * Cria alguns separadores de linhas apenas por fim estético para reinicializar o Sistdown
     */
    public static void printaFim(){
        System.out.println("\n\n\n\n\n\n\n");
    }



    /**
     * Verifica se é a primeira vez rodando ou 
     * se é uma reinicialização do Sistdown.
     */
    public static boolean primeiraVezRodando() {
        if (contexto == null)
            return true;
        return false;
    }


}
