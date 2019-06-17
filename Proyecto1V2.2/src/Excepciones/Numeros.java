/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Excepciones;

/**
 *
 * @author MELI
 */
public class Numeros extends Exception{

    public Numeros() {

    }

    public static boolean isNumeric(String cadena) { //Método para determinar si un String está compuesto por números

        boolean resultado;

        try {
            Integer.parseInt(cadena);
            resultado = true;
        } catch (NumberFormatException excepcion) {
            resultado = false;
        }

        return resultado;
    }

}
