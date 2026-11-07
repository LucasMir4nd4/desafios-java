package br.com.lucas.logs.logs;

import br.com.lucas.logs.Nivel;

import java.time.LocalDateTime;

public class Log {

    private final LocalDateTime data;
    private final Nivel nivel;
    private final String conteudo;
    private Integer numeroLinha;
    private boolean valido;

    public Log(LocalDateTime data, Nivel nivel, String conteudo) {
        this.data = data;
        this.nivel = nivel;
        this.conteudo = conteudo;
        this.valido = true;
    }


    public Log(String conteudo) {
        this.conteudo = conteudo;
        this.valido = false;
        data = null;
        nivel = null;
    }



    public Integer getNumeroLinha() {
        return numeroLinha;
    }

    public void setNumeroLinha(Integer numeroLinha) {
        this.numeroLinha = numeroLinha;
    }

    public boolean isValido() {
        return valido;
    }

    public void setValido(boolean valido) {
        this.valido = valido;
    }

    public LocalDateTime getData() {
        return data;
    }

    public Nivel getNivel() {
        return nivel;
    }

    public String getConteudo() {
        return conteudo;
    }

    @Override
    public String toString() {
        return "Log{" +
                "data=" + data +
                ", nivel=" + nivel +
                ", conteudo='" + conteudo + '\'' +
                ", numeroLinha=" + numeroLinha +
                '}';
    }
}
