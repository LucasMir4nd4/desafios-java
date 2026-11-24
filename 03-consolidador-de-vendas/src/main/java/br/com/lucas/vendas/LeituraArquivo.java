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

        LocalDateTime data = null;
        String nome = null;
        int quantidade = 0;
        double valor = 0.0;

        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            
            String linha = br.readLine();
            String[]vetorLinha = linha.split(";");

            
            while (linha != null) {
                for (int i = 0; i < vetorLinha.length; i++) {
                    data = LocalDateTime.parse(vetorLinha[0]);
                    nome = vetorLinha[1];
                    quantidade = Integer.parseInt(vetorLinha[2]);
                    valor = Double.parseDouble(vetorLinha[3]);
                }
                linha = br.readLine();
                vendas.add(new Venda(data,nome,quantidade,valor));
            }
        } catch (IOException e) {
            vendas.add(new Venda(nome));
            return null;
        }

        return vendas;
    }

}
