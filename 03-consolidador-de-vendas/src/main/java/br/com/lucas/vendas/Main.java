package br.com.lucas.vendas;

public class Main {

    public static void main(String[] args) {
        System.out.println("BEM VINDO AO PROGRAMA - CONSOLIDADOR DE VENDAS");

        LeituraArquivo leituraArquivo = new LeituraArquivo();

        String[] arquivos = {
                "/arquivos/vendas-barueri.csv",
                "/arquivos/vendas-centro.csv",
                "/arquivos/vendas-osasco.csv"
        };

        String caminhoArquivo = arquivos[0];

        System.out.println(leituraArquivo.lerArquivo(caminhoArquivo));



    }
}
