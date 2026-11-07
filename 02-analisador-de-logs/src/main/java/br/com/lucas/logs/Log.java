package br.com.lucas.logs;

import java.time.LocalDateTime;

public class Log {

    private LocalDateTime data;
    private String nivel;
    private String mensagem;


    public Log(LocalDateTime data, String nivel, String mensagem) {
        this.data = data;
        this.nivel = nivel;
        this.mensagem = mensagem;
    }

    public Log() {

    }

    public LocalDateTime getData() {
        return data;
    }

    public String getNivel() {
        return nivel;
    }

    public String getMensagem() {
        return mensagem;
    }

}
