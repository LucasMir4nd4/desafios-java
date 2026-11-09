package br.com.lucas.logs;

import java.io.*;
import java.nio.file.Path;
import java.text.ParseException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class LeitorDeLog {

    private static final DateTimeFormatter FORMATO_HORA = DateTimeFormatter.ofPattern("HH:mm:ss");
    private LocalTime hora;

    public ResultadoLeitura lerLogs(String arquivo) throws FileNotFoundException {

        List<Log> logs = new ArrayList<>();
        List<String> linhas = new ArrayList<>();
        LogResumo logResumo = new LogResumo();
        List<FrequenciaDeErros> frequenciaDeErros = new ArrayList<>();
        Integer numeroDaLinha = 0;

        logResumo.setArquivo(arquivo);

        try(BufferedReader br = new BufferedReader(new FileReader(arquivo))) {

            String linha = br.readLine();

            while (linha != null) {
                numeroDaLinha++;
                Log log = tratarLinha(linha);

                if (log != null) {
                    logs.add(log);
                    switch (logs.getLast().getNivel()){
                        case "ERROR":
                            hora = logs.getLast().getData().toLocalTime();
                            if (logResumo.getQuantidadeError() == 0 ) {
                                logResumo.setTimePrimeiroErro(hora);
                            }
                            logResumo.setTimeFimErro(hora);
                            logResumo.somaError();

                            Boolean encontrou = false;
                            for (FrequenciaDeErros frequencia : frequenciaDeErros){
                                if (frequencia.getDescricaoErro().equals(logs.getLast().getMensagem())) {
                                    frequencia.adicionaErro();
                                    encontrou = true;
                                }
                            }
                            if (!encontrou) {
                                FrequenciaDeErros f = new FrequenciaDeErros(logs.getLast().getMensagem(),1);
                                frequenciaDeErros.add(f);
                            }

                            break;
                        case "WARN":
                            logResumo.somaWarn();
                            break;
                        case "INFO":
                            logResumo.somaInfo();
                            break;
                    }

//                    System.out.println(linha);
                } else {
                    logResumo.adicionarLinhaComErro(numeroDaLinha);
                    linhas.add(linha);
                }
                linha = br.readLine();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        frequenciaDeErros.sort(Comparator.comparingInt(FrequenciaDeErros::getQuantidadeErro).reversed());

        List<String> errosMaisFrequentes = frequenciaDeErros.stream()
                .limit(3)
                .map(f -> f.getQuantidadeErro() + "x " + f.getDescricaoErro())
                .toList();

        logResumo.setErroFrequente(errosMaisFrequentes);

        return new ResultadoLeitura(logs, linhas, logResumo);
    }

    private static final DateTimeFormatter FORMATO_DATA =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public Log  tratarLinha(String linha) {

        LocalDateTime dataHora;
        String nivel;
        String mensagem;

        Log log = new Log();

        try {

            dataHora = LocalDateTime.parse(linha.substring(0, linha.indexOf("[") - 1), FORMATO_DATA);
            nivel = linha.substring(linha.indexOf("[") + 1, linha.lastIndexOf("]"));
            mensagem = linha.substring(linha.lastIndexOf("]") + 1);

        } catch (DateTimeException | StringIndexOutOfBoundsException e) {
            return null;
        }

        return new Log(dataHora, nivel, mensagem);
    }
}
