package br.com.lucas.vendas;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import br.com.lucas.vendas.model.Venda;

public class LeituraArquivo {

    public List<Venda> lerArquivo(String caminhoArquivo){

        List<Venda> vendas = new ArrayList<>();

        LocalDateTime data;
        String nome;
        int quantidade;
        double valor;

        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            
            String linha = br.readLine();
            String[]vetorLinha = linha.split(";");


            
            while (linha != null) {
                for (int i = 0; i < vetorLinha.length; i++) {
                    data = LocalDateTime.parse(vetorLinha[0]);
                    nome = vetorLinha[1];
                    quantidade = Integer.valueOf(vetorLinha[2]);
                    valor = Integer.valueOf(vetorLinha[3]);
                }

                

                // vendas.add(new Venda(data,nome,quantidade,valor));
            }


        } catch (IOException e) {
            //TODO tratar
            return null;
        }

        return vendas;
    }

}
