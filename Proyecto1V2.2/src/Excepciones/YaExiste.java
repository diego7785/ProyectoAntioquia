/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Excepciones;

import java.util.ArrayList;

/**
 *
 * @author MELI
 */
public class YaExiste extends Exception{
    public YaExiste(){
        
    }
    
    public static boolean existe(ArrayList array, String placa){
        boolean resultado=false;
        for(int i=0; i<array.size(); i++){
            if(array.get(i).equals(placa)){
                resultado=true;
            }
        }
        return resultado;
    }
}
