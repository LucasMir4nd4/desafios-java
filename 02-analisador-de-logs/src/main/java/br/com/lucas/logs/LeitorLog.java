package br.com.lucas.logs;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import br.com.lucas.logs.model.Log;

public class LeitorLog {


    public ResultadoLeitura lerLogs(String caminhoArquivo) {

        String nomeArquivo = caminhoArquivo.substring(caminhoArquivo.lastIndexOf("/") + 1);

        List<Log> logs = new ArrayList<>();
        List<Log> linhasInvalidas = new ArrayList<>();
        Integer contador;


        InputStream is = getClass().getResourceAsStream(caminhoArquivo);
        if (is == null) {
            System.out.println("Arquivo não encontrado: " + caminhoArquivo);
            throw new RuntimeException(new FileNotFoundException(caminhoArquivo));
        }
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            String linha = br.readLine();
            contador = 0;

            while (linha != null) {

                contador++;
                Log linhaTratada = tratarLinha(linha);


                if (linhaTratada.isValido()) {

                    linhaTratada.setNumeroLinha(contador);
                    logs.add(linhaTratada);

                } else {

                    linhaTratada.setNumeroLinha(contador);
                    linhasInvalidas.add(linhaTratada);

                }

                linha = br.readLine();
            }

        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + caminhoArquivo);
            throw new RuntimeException(e);
        }

        return new ResultadoLeitura(nomeArquivo, logs, linhasInvalidas, contador);
    }

    public Log tratarLinha(String linha){

        LocalDateTime data;
        Nivel nivel;
        String mensagem;
        try {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            data = LocalDateTime.parse(linha.substring(0, linha.indexOf("[" )- 1) , dtf);

            nivel = Nivel.valueOf(linha.substring(linha.indexOf(" [") + 2, linha.indexOf("]")));

            mensagem = linha.substring(linha.indexOf("]") + 2);

        } catch (DateTimeException | StringIndexOutOfBoundsException | IllegalArgumentException e) {
            return new Log(linha);
        }

        return new Log(data, nivel, mensagem);
    }

}
