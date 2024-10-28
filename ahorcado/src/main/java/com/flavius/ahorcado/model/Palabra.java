package com.flavius.ahorcado.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class Palabra {

    @NotNull(message = "El campo Palabra no puede ser nulo")
    @NotBlank(message = "El campo Palabra no puede estar en blanco")
    @Pattern(regexp = "^[A-Za-z]*$", message = "La Palabra no tiene el formato adecuado")
    private String palabra;

    private char[] arr;

    public Palabra() {
    }

    public Palabra(String palabra) {
        this.palabra = palabra;
    }

    public String getPalabra() {
        return palabra;
    }

    public void setPalabra(String palabra) {
        this.palabra = palabra;
    }

    public char[] getArr() {
        return arr;
    }

    public void setArr(char[] arr) {
        this.arr = arr;
    }


    @Override
    public String toString() {
        return "Palabra{" +
                "palabra='" + palabra + '\'' +
                '}';
    }
}
