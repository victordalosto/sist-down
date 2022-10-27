package action;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import model.Trechos;
import mslinks.ShellLink;

public class Acoes {

    private static List<String> listaComIdsEscolhidos;

    private static String trechosNaLocal;
    private static String contexto;




    public static void inicializacao() throws Exception {
        System.out.println(" ================================================================================");
        String message = """
                      __  _
                    /  _|| |_   _   _ __   _____      __
                    | |_ | | | | | | '_ \\ / _ \\ \\ /\\ / /
                    |  _|| | |_| | | | | | (_) \\ V  V /
                    |_|  |_|\\__, | |_| |_|\\___/ \\_/\\_/
                            |___/                                                SIST-DOWN v1.9.2
                """;
        System.out.print(message);
        Update.v2();
        Caminhos.criarPastas();
        Caminhos.criarArquivos();
    }
    



    public static void printaInicio() throws Exception {
        contexto = Files.readAllLines(Caminhos.SISTDOWN_CONFIG_CONTEXTO.toPath()).get(0);
        System.out.println(" ================================================================================");
        System.out.println(" MODO: " + contexto);
        printaTrechos();
        System.out.println(" ================================================================================");
        if (contexto.equals("local")) {
            System.out.println(" * Para 'baixar' trechos, digite os ids separados por virgula.  Ex: 100, 101, 102");
            System.out.println(" * Para 'limpar' e também baixar acrescente o parametro limpa.  Ex: limpa, 85, 86");    
        }
        System.out.println(" * Para mudar o contexto do Sistlev, digite 'rede' ou 'local'");
        System.out.println(" ================================================================================");
    }




    private static void printaTrechos() throws Exception {
        trechosNaLocal = Files.readString(Paths.get(Caminhos.SISTDOWN_CONFIG_INFODOWNLOADS.toString())).replaceAll(", $", "");
        if (contexto.equals("local")) {
            if (trechosNaLocal.equals("")) {
                System.out.println(" * 0 trechos baixados.");
            } else {
                System.out.println(" * Trechos que estão na pasta:");
                System.out.println(" * " + trechosNaLocal);
            }
        }
    }




    public static void criaPromptPedindoInputs() {
        listaComIdsEscolhidos = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        String row = scanner.nextLine();
        row = row.replaceAll("\\s+", "").replaceAll("[;]", ",").replaceAll("[/]", ",").replaceAll("[.]", ",");
        if (!row.contains(",")) {
            if (row != null && !row.equals("") && !row.equals(" ")) {
                listaComIdsEscolhidos.add(row);
            }
        } else {
            String[] rows = row.split(",");
            for (int i = 0; i < rows.length; i++) {
                String trecho = rows[i];
                if (trecho != null && !trecho.equals("") && !trecho.equals(" ")) {
                    listaComIdsEscolhidos.add(trecho);
                }
            }
        }
        System.out.println();
    }




    public static void verificaSeMudaContexto() throws Exception {
        for (int i=0; i<listaComIdsEscolhidos.size(); i++) {
            String param = listaComIdsEscolhidos.get(i);
            if (param.toLowerCase().contains("local") && !contexto.equals("local")) {
                System.out.println(" Mudando o contexto para local");
                contexto = "local";
                Files.write(Caminhos.SISTDOWN_CONFIG_CONTEXTO.toPath(), (contexto).getBytes(), StandardOpenOption.TRUNCATE_EXISTING);
                listaComIdsEscolhidos.remove(i);
                Caminhos.SISTDOWN_SHORTCUT_REDE.delete();
                Caminhos.SISTDOWN_DOWNLOADS_TEMPORARY.renameTo(Caminhos.SISTDOWN_DOWNLOADS);
            } else if (param.toLowerCase().contains("rede") && !contexto.equals("rede")) {
                System.out.println(" Mudando o contexto para rede");
                contexto = "rede";
                Files.write(Caminhos.SISTDOWN_CONFIG_CONTEXTO.toPath(), (contexto).getBytes(), StandardOpenOption.TRUNCATE_EXISTING);
                listaComIdsEscolhidos.remove(i);
                if (Caminhos.SISTDOWN_DOWNLOADS.isDirectory())
                    Caminhos.SISTDOWN_DOWNLOADS.renameTo(Caminhos.SISTDOWN_DOWNLOADS_TEMPORARY);
                ShellLink.createLink(Caminhos.REDE_VIDEOS_FOLDER.toString(), Caminhos.SISTDOWN_SHORTCUT_REDE.toString());
            }
        }

    }




    public static void verificaSeLimpaPasta() throws Exception {
        for (int i=0; i<listaComIdsEscolhidos.size(); i++) {
            String param = listaComIdsEscolhidos.get(i);
            if (param.toLowerCase().contains("clea") || param.toLowerCase().contains("limp")) {
                trechosNaLocal = "";
                listaComIdsEscolhidos.remove(i);
                deleteFolder(Caminhos.SISTDOWN_DOWNLOADS);
                FileWriter f = new FileWriter(Caminhos.SISTDOWN_CONFIG_INFODOWNLOADS, false);
                f.close();
                System.out.println(" ...Pasta Limpa");
        }}
    }




    public static void 
    buscaTrechosDisponiveis() throws Exception {
        try(Scanner scanner = new Scanner(Caminhos.pathCSVComTrechosDisponiveis)) {
            while (scanner.hasNext()) {
                String[] row = scanner.nextLine().split(";"); // HD, ID, FINALPATH
                Trechos.addTrecho(row[0], row[1]);
            }
        }
    }




    public static void downloadFolders() throws Exception {
        if (listaComIdsEscolhidos.size() > 0 && contexto.equals("local")) {
            System.out.println(" ...Iniciando o download dos trechos");
            for (int i = 0; i < listaComIdsEscolhidos.size(); i++) { 
                String id = listaComIdsEscolhidos.get(i);
                String caminho = Trechos.getCaminho(id);
                if (caminho != null) {
                    Path caminhoRede = Paths.get(Caminhos.REDE_VIDEOS_FOLDER.toString(), caminho);
                    Path caminhoSistdown = Paths.get(Caminhos.SISTDOWN_DOWNLOADS.toString(), caminho);
                    copyFolder(caminhoRede, caminhoSistdown, StandardCopyOption.REPLACE_EXISTING);
                    System.out.println("Baixado " + id +  " - " +  caminhoSistdown.toString());
                    Files.write(Caminhos.SISTDOWN_CONFIG_INFODOWNLOADS.toPath(), (id + ", ").getBytes(), StandardOpenOption.APPEND);
                } else {
                    System.out.println(" ...Não foi encontrado o trecho de id: " + listaComIdsEscolhidos.get(i) + ".");
                }
            }
        } else {
            System.out.println(" Mude o contexto para local para começar a baixar trechos");
        }
    }




    public static void printaFim() throws Exception {
        System.out.println("\n\n\n\n\n");
    }




    private static void copyFolder(Path source, Path target, CopyOption... options) throws Exception {
        Files.walkFileTree(source, new SimpleFileVisitor<Path>() {

            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                Files.createDirectories(target.resolve(source.relativize(dir)));
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.copy(file, target.resolve(source.relativize(file)), options);
                return FileVisitResult.CONTINUE;
            }
        });
    }




    public static void deleteFolder(File folder) {
        File[] files = folder.listFiles();
        if (files != null) { // some JVMs return null for empty dirs
            for (File f : files) {
                if (f.isDirectory())
                    deleteFolder(f);
                else
                    f.delete();
            }
        }
        folder.delete();
    }

}
