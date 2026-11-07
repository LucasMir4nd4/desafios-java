package br.com.lucas.logs;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        System.out.println("Analisador de Logs de Servidor - em construção");

        LeitorLog leitorLog = new LeitorLog();
        ConstrutorResumo construtorResumo = new ConstrutorResumo();

        String[] arquivos = {
                "C:\\Users\\LUMI\\IdeaProjects\\desafios-java\\desafios-java\\02-analisador-de-logs\\src\\main\\resources\\arquivos\\servidor-app.log",
                "C:\\Users\\LUMI\\IdeaProjects\\desafios-java\\desafios-java\\02-analisador-de-logs\\src\\main\\resources\\arquivos\\servidor-banco.log",
                "C:\\Users\\LUMI\\IdeaProjects\\desafios-java\\desafios-java\\02-analisador-de-logs\\src\\main\\resources\\arquivos\\servidor-email.log"
        };

        ResultadoLeitura resultado = null;
        for (String arquivo : arquivos) {
            resultado = leitorLog.lerLogs(arquivo);
            construtorResumo.construirResumo(resultado);
        }

    }
}
