package br.ufop.ruapplicationpassivemvc.model.entity;

import com.squareup.moshi.Json;

public class ReportDish {
    private int ruim;
    private int baixo;
    private int medio;
    private int alto;
    private int excelente;
    private int total;
    private int rejeicao;

    public int getRuim() {
        return ruim;
    }

    public void setRuim(int ruim) {
        this.ruim = ruim;
    }

    public int getBaixo() {
        return baixo;
    }

    public void setBaixo(int baixo) {
        this.baixo = baixo;
    }

    public int getMedio() {
        return medio;
    }

    public void setMedio(int medio) {
        this.medio = medio;
    }

    public int getAlto() {
        return alto;
    }

    public void setAlto(int alto) {
        this.alto = alto;
    }

    public int getExcelente() {
        return excelente;
    }

    public void setExcelente(int excelente) {
        this.excelente = excelente;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getRejeicao() {
        return rejeicao;
    }

    public void setRejeicao(int rejeicao) {
        this.rejeicao = rejeicao;
    }

    @Override
    public String toString() {
        return "ReportDish{" +
                "ruim=" + ruim +
                ", baixo=" + baixo +
                ", medio=" + medio +
                ", alto=" + alto +
                ", excelente=" + excelente +
                ", total=" + total +
                ", rejeicao=" + rejeicao +
                '}';
    }
}
