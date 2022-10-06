package config;
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
import Model.Trecho;

public class Acoes {

    private static List<Trecho> listaTrechos = new ArrayList<>();
    private static List<String> listaComIdsEscolhidos = new ArrayList<>();

    private static String trechosNoServidor;
    private static String ano = "2022";
    private static Path pathBaseRede = Paths.get("\\\\10.100.10.219", "Videos", "Recebidos");
    private static Path pathSistDown = Paths.get("D:", "sist-down");
    private static Path pathSistDownConfigs = Paths.get(pathSistDown.toString(), "configs");
    private static Path pathDownloadSistDown = Paths.get(pathSistDown.toString(), "Videos", 
                                                                  "Recebidos", ano);
    private static Path pathCSVComTrechosDisponiveis = Paths.get(pathBaseRede.toString(), 
                                                                  "sistdown-config",
                                                                  "caminhos.csv");
    


    public static void printaInicio() throws Exception {
        getConfigs();
        String message = """
              ==================================================================  
                  __  _                             
                /  _|| |_   _   _ __   _____      __
                | |_ | | | | | | '_ \\ / _ \\ \\ /\\ / /
                |  _|| | |_| | | | | | (_) \\ V  V / 
                |_|  |_|\\__, | |_| |_|\\___/ \\_/\\_/  
                        |___/                                     SIST-DOWN v0.1
              ==================================================================               
                """;
        System.out.print(message);
        printaTrechos();
        System.out.println("* Digite os Ids dos trechos que quer baixar separados por virgula.");
        System.out.println("* Para limpar a pasta, coloque a palavra limpa nos parametros. Ex:");
        System.out.println("    limpa, 1210, 1211, 1212");
        System.out.println("==================================================================");
    }



    private static void getConfigs() throws Exception {
        trechosNoServidor = Files.readString(Paths.get(pathSistDownConfigs.toString())).replaceAll(", $", "");
    }

    

    private static void printaTrechos() {
        if (!trechosNoServidor.equals(""))
            System.out.println("* Trechos que estão na pasta:" + "\n" +"* " + trechosNoServidor );
        else
            System.out.println("* 0 trechos baixados.");
    }




    public static void buscaTrechosDisponiveis() throws Exception {
        Scanner scanner = new Scanner(new File(pathCSVComTrechosDisponiveis.toString()));
        while (scanner.hasNext()) {
            String[] row = scanner.nextLine().split(";"); // HD, ID, FINALPATH
            Trecho trecho = new Trecho();
            trecho.setHD(row[0]);
            trecho.setId(row[1]);
            trecho.setLote("Lote" + row[0].charAt(1));
            trecho.setTargetHD(Paths.get(row[2]));
            trecho.setCaminhoRede(Paths.get(pathBaseRede.toString(), ano));
            listaTrechos.add(trecho);
        }
    }



    public static void criaPromptPedindoTrechos() {
        try (Scanner scanner = new Scanner(System.in);) {
            String row = scanner.nextLine();
            if (!row.contains(",")) {
                listaComIdsEscolhidos.add(row);
            } else {
                String[] rows = row.split(",");
                for (int i = 0; i < rows.length; i++)
                    listaComIdsEscolhidos.add(rows[i].replaceAll("\\s+", ""));
            }
        }

    }

    public static void verificaSeLimpaPasta() throws Exception {
        for (int i=0; i<listaComIdsEscolhidos.size(); i++) {
            String param = listaComIdsEscolhidos.get(i);
            if (param.toLowerCase().contains("clea") ||param.toLowerCase().contains("limp")) {
                System.out.println("..Limpando pasta");
                trechosNoServidor = "";
                listaComIdsEscolhidos.remove(i);
                deleteFolder(pathDownloadSistDown.toFile());
                FileWriter f = new FileWriter(pathSistDownConfigs.toFile(), false);
                f.close();
                break;
            }
        }


    }



    public static void downloadFolders() throws Exception {
        if (listaComIdsEscolhidos.size() > 0 ) {
            System.out.println("\n....(0/" + listaComIdsEscolhidos.size() + ")Iniciando download dos trechos");
        }
        for (int i = 0; i < listaComIdsEscolhidos.size(); i++) { 
            boolean encontrou = false;
            for (int j = 0; j < listaTrechos.size(); j++) {
                if (listaComIdsEscolhidos.get(i).equals(listaTrechos.get(j).getId())) {
                    Trecho t = listaTrechos.get(j);
                    Path targetPath = Paths.get(pathDownloadSistDown.toString(), t.getLote(),
                            t.getHD(), t.getTargetHD().toString());
                    copyFolder(t.getCaminhoRede(), targetPath, StandardCopyOption.REPLACE_EXISTING);
                    System.out.println("....(" + i + "/" + listaComIdsEscolhidos.size() + 
                                       ") Baixado trecho de id: " + listaComIdsEscolhidos.get(i));
                    Files.write(pathSistDownConfigs, (listaComIdsEscolhidos.get(i) + ", ").getBytes(), StandardOpenOption.APPEND);
                    encontrou = true;
                    break;
                }
            }
            if (!encontrou) {
                System.out.println("....Não foi encontrado o trecho de id: " + listaComIdsEscolhidos.get(i));
            }
        }
    }

    public static void printaFim() throws Exception {
        getConfigs();
        System.out.println("\n");
        System.out.println("==================================================================");
        System.out.println("* Finalizado os downloads..");
        printaTrechos();
        System.out.println("==================================================================");
    }

    private static void copyFolder(Path source, Path target, CopyOption... options) throws IOException {
        Files.walkFileTree(source, new SimpleFileVisitor<Path>() {

            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
                    throws IOException {
                Files.createDirectories(target.resolve(source.relativize(dir)));
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
                    throws IOException {
                Files.copy(file, target.resolve(source.relativize(file)), options);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    private static void deleteFolder(File folder) {
        File[] files = folder.listFiles();
        if (files != null) { // some JVMs return null for empty dirs
            for (File f : files) {
                if (f.isDirectory()) {
                    deleteFolder(f);
                } else {
                    f.delete();
                }
            }
        }
        folder.delete();
    }

}
