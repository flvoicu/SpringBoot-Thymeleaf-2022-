package com.flavius.ahorcado.funciones;

import com.flavius.ahorcado.coleccion.Colecciones;
import com.flavius.ahorcado.model.Letra;
import com.flavius.ahorcado.model.Palabra;
import com.flavius.ahorcado.model.UsuarioLoginDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Funciones {

    public static boolean checkUsuario(String usr, String clave){
        boolean confirmacion = false;

        for (int i = 0; i< Colecciones.listaUsuarios.size(); i++){
            UsuarioLoginDTO usu = Colecciones.listaUsuarios.get(i);

            if(usu.getUsuario().equals(usr) && usu.getClave().equals(clave)){
                return true;
            }
        }
        return confirmacion;
    }

    public static void addPalabra(Palabra palabra){
        Colecciones.listaPalabras.add(palabra);
    }

    public static Palabra getPalabraRandom(){
        Random random = new Random();
        Palabra palabra = Colecciones.listaPalabras.get(random.nextInt(Colecciones.listaPalabras.size()));
        palabra.setArr(new char[palabra.getPalabra().length()]);
        char[] arr = palabra.getArr();
        for (int i=0; i<arr.length; i++){
           arr[i] = '_';
        }
        palabra.setArr(arr);
        return palabra;
    }

    public static void addLetra(Letra letra){
        Colecciones.listaLetras.add(letra);

    }

    public static boolean checkRepetirLetra(Letra letra){
        boolean comprobar = false;

        for (int i=0; i<Colecciones.listaLetras.size();i++){
            Letra let = Colecciones.listaLetras.get(i);
            if(let.getLetra().equals(letra.getLetra())){
                return true;
            }
        }
        return comprobar;
    }

    public static boolean checkLetra(Letra letra, Palabra palabra){
        if(palabra.getPalabra().contains(letra.getLetra())){
            char[] arr = palabra.getArr();
            for (int i=0; i<palabra.getPalabra().length(); i++){
                if(letra.getLetra().charAt(0) == palabra.getPalabra().charAt(i)){
                    arr[i] = palabra.getPalabra().charAt(i);
                }
            }
            palabra.setArr(arr);
            return true;
        }else{
            return false;
        }
    }

    public static List<String> listaLetras(){
        List<String> lista = new ArrayList<>();
        for (int i=0; i<Colecciones.listaLetras.size();i++){
            lista.add(Colecciones.listaLetras.get(i).getLetra());
        }
        return lista;
    }

    // cambia de int a string para poder llamar a las clases de css
    public static String intentosString(int intentos){
        switch (intentos){
            case 0:
                return "Cero";
            case 1:
                return "Uno";
            case 2:
                return "Dos";
            case 3:
                return "Tres";
            case 4:
                return "Cuatro";
            case 5:
                return "Cinco";
            case 6:
                return "Seis";
            default:
                return "Nada";
        }
    }

    public static boolean checkGanar(char [] arr, Palabra palabra){
        boolean ganar= false;
        if(String.valueOf(arr).equals(palabra.getPalabra())){
            return true;
        }
        return ganar;
    }
}
