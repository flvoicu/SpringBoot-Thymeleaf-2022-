package com.flavius.ahorcado.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class Letra {

    @NotNull(message = "La Letra no puede ser nula")
    @NotBlank(message = "La Letra no puede estar vacia")
    @Pattern(regexp = "^[A-Za-zÀ-ÿ]{1}$", message = "La Letra no tiene el formato adecuado")
    private String letra;

    public Letra() {
    }

    public Letra(String letra) {
        this.letra = letra;
    }

    public String getLetra() {
        return letra;
    }

    public void setLetra(String letra) {
        this.letra = letra;
    }
}
