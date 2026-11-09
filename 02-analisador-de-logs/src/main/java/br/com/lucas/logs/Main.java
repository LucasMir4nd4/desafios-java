package br.com.lucas.logs;


public class Main {

    public static void main(String[] args) {

        System.out.println("Analisador de Logs de Servidor - em construção");

        LeitorLog leitorLog = new LeitorLog();
        ConstrutorResumo construtorResumo = new ConstrutorResumo();

        String[] arquivos = {
                "/arquivos/servidor-app.log",
                "/arquivos/servidor-banco.log",
                "/arquivos/servidor-email.log"
        };

        ResultadoLeitura resultado = null;
        for (String arquivo : arquivos) {
            resultado = leitorLog.lerLogs(arquivo);
            construtorResumo.construirResumo(resultado);
        }

    }
}
