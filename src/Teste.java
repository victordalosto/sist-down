
public class Teste {
    public static void main(String[] args) {
        
        String trecho = "\\\\10.100.10.219\\Videos\\Recebidos\\Lote3\\3303\\19_08_2022\\365_020MA5892";
        trecho = trecho.replaceAll(".+_", "").substring(0, 5);
        System.out.println(trecho);

    }
}
