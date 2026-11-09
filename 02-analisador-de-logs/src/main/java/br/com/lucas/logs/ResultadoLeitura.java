package br.com.lucas.logs;

import java.util.List;

import br.com.lucas.logs.model.Log;

public class ResultadoLeitura {

    private final String nomeArquivo;
    private final List<Log> logs;
    private final List<Log> linhasInvalidas;
    private final Integer numeroLinha;


    public ResultadoLeitura(String nomeArquivo, List<Log> logs, List<Log> linhasInvalidas, Integer numeroLinha) {
        this.nomeArquivo = nomeArquivo;
        this.logs = logs;
        this.linhasInvalidas = linhasInvalidas;
        this.numeroLinha = numeroLinha;
    }

    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public Integer getNumeroLinha() {
        return numeroLinha;
    }

    public List<Log> getLogs() {
        return logs;
    }

    public List<Log> getLinhasInvalidas() {
        return linhasInvalidas;
    }
}
