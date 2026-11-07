package br.com.lucas.logs;

import br.com.lucas.logs.logs.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class LeitorLog {


    public ResultadoLeitura lerLogs(String caminhoArquivo) {

        String nomeArquivo = caminhoArquivo.substring(caminhoArquivo.lastIndexOf("\\") + 1);

        List<Log> logs = new ArrayList<>();
        List<Log> linhasInvalidas = new ArrayList<>();
        Integer contador;


        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
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

        } catch (FileNotFoundException e) {
            System.out.println("Arquivo não encontrado: " + caminhoArquivo);
            throw new RuntimeException(e);
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
