package com.CRUD.integrador.modelo;

public class Consulta {

    private String campo;
    private String valor;

    public Consulta(String campo, String valor) {
        this.campo = campo;
        this.valor = valor;
    }

    public Consulta() {
    }

    public String getCampo() {
        return campo;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public void setCampo(String campo) {
        this.campo = campo;
    }
}
