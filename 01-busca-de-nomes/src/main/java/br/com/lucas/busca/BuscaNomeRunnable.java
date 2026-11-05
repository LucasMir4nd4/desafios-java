package br.com.lucas.busca;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class BuscaNomeRunnable implements Runnable{

    private final String prefixo= "/workspaces/desafios-java/01-busca-de-nomes/src/main/resources/arquivos/";


    private final String nomeArquivo;
    private final String nomeBuscado;

    public BuscaNomeRunnable(String nomeArquivo, String nomeBuscado) {
        this.nomeArquivo = nomeArquivo;
        this.nomeBuscado = nomeBuscado;
    }

    @Override
    public void run() {

        Thread thread = Thread.currentThread();

        System.out.println("Iniciando busca no arquivo: " + nomeArquivo 
            +"/ Virtual Thread: " + thread.isVirtual()
            +"/ Virtual ID: " + thread.threadId()
        );

        int ocorrencias = 0;

        try (BufferedReader br = Files.newBufferedReader(Path.of(prefixo + nomeArquivo))) {

            String conteudo;
            int numeroLinha = 0;

            while ((conteudo = br.readLine()) != null) {
                numeroLinha++;
                if (conteudo.contains(nomeBuscado)) {
                    ocorrencias++;

                    System.out.println("["+nomeArquivo+"] Linha" +numeroLinha+ " : "+conteudo);

                }
            }

            System.out.println("Busca Finalizada! Ocorrencias: "+ocorrencias);
            
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println();
            System.out.println("Erro ao ler o arquivo: "+ nomeArquivo);
        }

    }

}
