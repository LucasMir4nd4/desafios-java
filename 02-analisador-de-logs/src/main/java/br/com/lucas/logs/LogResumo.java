package br.com.lucas.logs;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class LogResumo {

    private String arquivo;
    private int quantidadeInfo = 0;
    private int quantidadeError = 0;
    private int quantidadeWarn = 0;
    private List<Integer> linhaComErro = new ArrayList<>();
    private LocalTime timePrimeiroErro;
    private LocalTime timeFimErro;
    private List<String> erroFrequente = new ArrayList<>();




    public LogResumo(String arquivo, int quantidadeInfo, int quantidadeError, int quantidadeWarn, List<Integer> linhaComErro, LocalTime timePrimeiroErro, LocalTime timeFimErro, List<String> erroFrequente) {
        this.arquivo = arquivo;
        this.quantidadeInfo = quantidadeInfo;
        this.quantidadeError = quantidadeError;
        this.quantidadeWarn = quantidadeWarn;
        this.linhaComErro = linhaComErro;
        this.timePrimeiroErro = timePrimeiroErro;
        this.timeFimErro = timeFimErro;
        this.erroFrequente = erroFrequente;
    }

    public LogResumo() {
    }

    public String getArquivo() {
        return arquivo;
    }

    public void setArquivo(String arquivo) {
        this.arquivo = arquivo;
    }

    public int getQuantidadeInfo() {
        return quantidadeInfo;
    }

    public void setQuantidadeInfo(int quantidadeInfo) {
        this.quantidadeInfo = quantidadeInfo;
    }

    public int getQuantidadeError() {
        return quantidadeError;
    }

    public void setQuantidadeError(int quantidadeError) {
        this.quantidadeError = quantidadeError;
    }

    public int getQuantidadeWarn() {
        return quantidadeWarn;
    }

    public void setQuantidadeWarn(int quantidadeWarn) {
        this.quantidadeWarn = quantidadeWarn;
    }

    public List<Integer> getLinhaComErro() {
        return linhaComErro;
    }

    public void adicionarLinhaComErro(Integer linhaComErro) {
        this.linhaComErro.add(linhaComErro);
    }

    public LocalTime getTimePrimeiroErro() {
        return timePrimeiroErro;
    }

    public void setTimePrimeiroErro(LocalTime timePrimeiroErro) {
        this.timePrimeiroErro = timePrimeiroErro;
    }

    public LocalTime getTimeFimErro() {
        return timeFimErro;
    }

    public void setTimeFimErro(LocalTime timeFimErro) {
        this.timeFimErro = timeFimErro;
    }

    public void somaError(){
        this.quantidadeError++;
    }
    public void somaWarn(){
        this.quantidadeWarn++;
    }
    public void somaInfo(){
        this.quantidadeInfo++;
    }

    public void setErroFrequente(List<String> erroFrequente) {
        this.erroFrequente = erroFrequente;
    }
}
