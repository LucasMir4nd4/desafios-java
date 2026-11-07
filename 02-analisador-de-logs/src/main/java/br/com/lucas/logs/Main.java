package br.com.lucas.logs;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {

//        String caminhoDoArquivo = "C:\\Users\\leand\\eclipse-workspace\\desafios-java\\02-analisador-de-logs\\src\\main\\resources\\arquivos\\servidor-app.log";

        Path pasta = Path.of("src/main/resources/arquivos");
        List<String> arquivos = List.of("servidor-app.log", "servidor-banco.log", "servidor-email.log");
        List<ResultadoLeitura> resultadoLeituras = new ArrayList<>();
        LeitorDeLog leitor = new LeitorDeLog();

        for (String arquivo : arquivos) {
            System.out.println("============================================");
            System.out.println("Arquivo: " + arquivo);
            try {
                resultadoLeituras.add(leitor.lerLogs(pasta.resolve(arquivo).toString()));
            } catch (FileNotFoundException e) {
                System.out.println("Não foi possível ler o arquivo "+arquivos.getFirst()+": "+e.getMessage());
            }
        }

        System.out.println("========================================");
        System.out.println("        ANALISADOR DE LOGS");
        System.out.println("========================================");

        for (ResultadoLeitura resultado : resultadoLeituras) {

            System.out.println("["+resultado.logResumo.getArquivo()+"]");
            System.out.println("INFO: "+resultado.logResumo.getQuantidadeInfo()+
                               "| WARN: "+resultado.logResumo.getQuantidadeWarn()+
                               "| ERROR: "+resultado.logResumo.getQuantidadeError());
            System.out.println("Linhas fora do padrão: "+resultado.logResumo.getLinhaComErro().size());
            System.out.println("Primeiro erro: "+resultado.logResumo.getTimePrimeiroErro()+
                               " | Último erro: "+resultado.logResumo.getTimeFimErro());
            System.out.println();
        }

    }
}
