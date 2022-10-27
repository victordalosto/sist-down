package action;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.Tags;
import model.Trechos;
import mslinks.ShellLink;

public class Acoes {

    private static List<String> listaComIdsEscolhidos;
    private static String contexto;


    public static void inicializacao() throws Exception {
        Update.v2();
        Caminhos.criarPastas();
        Caminhos.criarArquivos();
    }




    public static void carregaTrechosDisponiveis() throws Exception {
        try(Scanner scanner = new Scanner(Caminhos.pathCSVComTrechosDisponiveis)) {
            while (scanner.hasNext()) {
                String[] row = scanner.nextLine().split(";");
                Trechos.addTrecho(row[0], row[1]);
            }
        }
    }
    



    public static void printaInicio() throws Exception {
        if (contexto == null) {
            System.out.println(" ================================================================================");
            String message = """
                     *      __  _
                     *    /  _|| |_   _   _ __   _____      __
                     *    | |_ | | | | | | '_ \\ / _ \\ \\ /\\ / /
                     *    |  _|| | |_| | | | | | (_) \\ V  V /
                     *    |_|  |_|\\__, | |_| |_|\\___/ \\_/\\_/
                     *            |___/                                              SIST-DOWN v1.9.3
                    """;
            System.out.print(message);
        } else {
            System.out.println(" * SISTDOWN Reiniciado");
        }
        contexto = Files.readAllLines(Caminhos.SISTDOWN_CONFIG_CONTEXTO.toPath()).get(0);
        System.out.println(" ================================================================================");
        System.out.println(" * MODO: " + contexto);
        Util.printaTrechosNaMaquinaLocal(contexto);
        System.out.println(" ================================================================================");
        if (contexto.equalsIgnoreCase("local")) {
            System.out.println(" * Para 'baixar' trechos, digite os ids separados por virgula.  Ex: 100, 101, 102");
            System.out.println(" * Para 'limpar' e também baixar acrescente o parametro limpa.  Ex: limpa, 85, 86");    
        }
        System.out.println(" * Para mudar o contexto do Sistlev, digite 'rede' ou 'local'");
        System.out.println(" ================================================================================");
        System.out.print(" FLY-now> ");
    }








    public static void criaPromptPedindoInputs() {
        listaComIdsEscolhidos = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        String row = scanner.nextLine();
        row = row.replaceAll("\\s+", "").replaceAll("[;]", ",").replaceAll("[/]", ",").replaceAll("[.]", ",");
        if (!row.contains(",")) {
            if (Util.isValid(row))
                addListEscolhidos(row);
        } else {
            String[] rows = row.split(",");
            for (int i = 0; i < rows.length; i++) {
                String trecho = rows[i];
                if (Util.isValid(trecho))
                    addListEscolhidos(trecho);
            }
        }
        System.out.println();
    }


    private static void addListEscolhidos(String trecho) {
        if (Tags.isTag(trecho)) {
                listaComIdsEscolhidos.add(trecho.toUpperCase());
        } else {
            trecho = trecho.replaceAll("[^\\d.]", "");
            if (Util.isValid(trecho))
                listaComIdsEscolhidos.add(trecho);
        }
    }




    public static void verificaSeMudaContexto() throws Exception {
        for (int i=0; i<listaComIdsEscolhidos.size(); i++) {
            String param = listaComIdsEscolhidos.get(i);
            if (param.equalsIgnoreCase("local") && !contexto.equalsIgnoreCase("local")) {
                mudandoContexto("local", i);
                Caminhos.SISTDOWN_SHORTCUT_REDE.delete();
                Caminhos.SISTDOWN_DOWNLOADS_TEMPORARY.renameTo(Caminhos.SISTDOWN_DOWNLOADS);
            } else if (param.toLowerCase().equalsIgnoreCase("rede") && !contexto.equalsIgnoreCase("rede")) {
                mudandoContexto("rede", i);
                if (Caminhos.SISTDOWN_DOWNLOADS.isDirectory())
                    Caminhos.SISTDOWN_DOWNLOADS.renameTo(Caminhos.SISTDOWN_DOWNLOADS_TEMPORARY);
                ShellLink.createLink(Caminhos.REDE_VIDEOS_FOLDER.toString(), Caminhos.SISTDOWN_SHORTCUT_REDE.toString());
            }
        }
    }


    private static void mudandoContexto(String novoContexto, int id) throws Exception {
        System.out.println(" * Mudando o contexto para " + novoContexto);
        contexto = novoContexto;
        Files.write(Caminhos.SISTDOWN_CONFIG_CONTEXTO.toPath(), (contexto).getBytes(), StandardOpenOption.TRUNCATE_EXISTING);
        listaComIdsEscolhidos.remove(id);
    }




    public static void verificaSeLimpaPasta() throws Exception {
        for (int i=0; i<listaComIdsEscolhidos.size(); i++) {
            String param = listaComIdsEscolhidos.get(i);
            if (param.equalsIgnoreCase("limpa") || param.equalsIgnoreCase("limpar")) {
                listaComIdsEscolhidos.remove(i);
                Util.deleteFolder(Caminhos.SISTDOWN_DOWNLOADS);
                Util.deleteFolder(Caminhos.SISTDOWN_DOWNLOADS_TEMPORARY);
                FileWriter f = new FileWriter(Caminhos.SISTDOWN_CONFIG_INFODOWNLOADS, false);
                f.close();
                System.out.println(" * ...Pasta Limpa");
        }}
    }




    public static void downloadFolders() throws Exception {
        if (listaComIdsEscolhidos.size() > 0 && contexto.equalsIgnoreCase("local")) {
            System.out.println(" * ...Iniciando o download dos trechos");
            for (int i = 0; i < listaComIdsEscolhidos.size(); i++) { 
                String id = listaComIdsEscolhidos.get(i);
                String caminho = Trechos.getCaminho(id);
                if (caminho != null) {
                    Path caminhoRede = Paths.get(Caminhos.REDE_VIDEOS_FOLDER.toString(), caminho);
                    Path caminhoSistdown = Paths.get(Caminhos.SISTDOWN_DOWNLOADS.toString(), caminho);
                    Util.copyFolder(caminhoRede, caminhoSistdown, StandardCopyOption.REPLACE_EXISTING);
                    String nomeTrecho = id + "-" + caminhoSistdown.toString().replaceAll(".+_", "").substring(0, 5);
                    System.out.println(" *Baixado " + nomeTrecho);
                    Files.write(Caminhos.SISTDOWN_CONFIG_INFODOWNLOADS.toPath(), (nomeTrecho + ",  ").getBytes(), StandardOpenOption.APPEND);
                } else {
                    System.out.println(" * ...Não foi encontrado o trecho de id: " + listaComIdsEscolhidos.get(i) + ".");
                }
            }
        } else {
            System.out.println(" * Mude o contexto para local para começar a baixar trechos");
        }
    }




    public static void printaFim() throws Exception {
        System.out.println("\n\n\n\n\n\n\n");
    }


}
