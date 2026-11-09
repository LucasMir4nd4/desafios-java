package br.com.lucas.logs;

import java.util.List;

public class FrequenciaDeErros {
   private String descricaoErro;
   private int quantidadeErro = 0;

    public FrequenciaDeErros(String descricaoErro, int quantidadeErro) {
        this.descricaoErro = descricaoErro;
        this.quantidadeErro = quantidadeErro;
    }

    public FrequenciaDeErros() {
    }

    public void adicionaErro() {
        this.quantidadeErro++;
    }



    public String getDescricaoErro() {
        return descricaoErro;
    }

    public void setDescricaoErro(String descricaoErro) {
        this.descricaoErro = descricaoErro;
    }

    public int getQuantidadeErro() {
        return quantidadeErro;
    }

    public void setQuantidadeErro(int quantidadeErro) {
        this.quantidadeErro = quantidadeErro;
    }
}
