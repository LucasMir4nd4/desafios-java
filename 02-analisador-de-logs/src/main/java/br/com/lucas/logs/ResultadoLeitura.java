package br.com.lucas.logs;

import java.util.List;

public class ResultadoLeitura {

    List<Log> logs;
    List<String> linhas;
    LogResumo logResumo;

    public ResultadoLeitura(List<Log> logs, List<String> linhas, LogResumo logResumo) {
        this.logs = logs;
        this.linhas = linhas;
        this.logResumo = logResumo;
    }

}
