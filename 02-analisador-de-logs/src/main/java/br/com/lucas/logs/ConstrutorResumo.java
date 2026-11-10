package br.com.lucas.logs;

import java.util.List;

import br.com.lucas.logs.model.Log;

public class ConstrutorResumo {

    private static final String CABECALHO = "BEM VINDO AO PROGRAMA:";
    private static final String LINHA_DUPLA= "=".repeat(50);
    private static final String LINHA_SIMPLES= "-".repeat(50);



    public void construirResumo(ResultadoLeitura resultado) {


        System.out.println(LINHA_DUPLA);
        System.out.println(CABECALHO);
        System.out.println(LINHA_DUPLA);

        System.out.println("iniciando a leitura do arquivo: "+ resultado.getNomeArquivo());
        List<Log> logs = resultado.getLogs();
        List<Log> linhasInvalidas = resultado.getLinhasInvalidas();

        System.out.println("Nível | QUANTIDADE ");

        System.out.println("INFO | " + logs.stream().filter(l -> l.getNivel().toString().equals("INFO")).count());
        System.out.println("WARN | " + logs.stream().filter(l -> l.getNivel().toString().equals("WARN")).count());
        System.out.println("ERROR | " + logs.stream().filter(l -> l.getNivel().toString().equals("ERROR")).count());

        System.out.println(LINHA_DUPLA);


        System.out.println("QUANTIDADE DE LINHAS INVÁLIDAS: " + linhasInvalidas.size());
        System.out.println("NUMERO DAS LINHAS INVÁLIDAS: " + linhasInvalidas.stream().map(Log::getNumeroLinha).toList());


        System.out.println("Erros mais frequentes: ");

        List<Log> logsErro = logs.stream().filter(l -> l.getNivel().toString().equals("ERROR")).toList();

        logsErro.stream().map(Log::getConteudo).distinct().forEach(erro -> {
            long count = logsErro.stream().filter(l -> l.getConteudo().equals(erro)).count();
            System.out.println(" " + count + "x " + erro);
        });



        System.out.println("leitura do arquivo finalizada com sucesso!");
        System.out.println(LINHA_SIMPLES.repeat(2));



    }



}
