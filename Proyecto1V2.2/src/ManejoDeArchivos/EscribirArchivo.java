/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ManejoDeArchivos;

import java.io.*;

/**
 *
 * @author MELI
 */
public class EscribirArchivo {

    public EscribirArchivo() {

    }

    public void agregarAlArchivo(String s, int num) {//1. Rutas , 2. Buses , 3. Tarjetas, 4.FechasRecargas, 5.FechasExtracciones
        FileWriter archivo = null;
        PrintWriter pw = null;
        try {
            if (num == 1) {
                archivo = new FileWriter("rutas.txt", true);
                pw = new PrintWriter(archivo);
                pw.println(s);
            } else if (num == 2) {
                archivo = new FileWriter("buses.txt", true);
                pw = new PrintWriter(archivo);
                pw.println(s);
            } else if (num == 3) {
                archivo = new FileWriter("tarjetas.txt",true);
                pw = new PrintWriter(archivo);
                pw.println(s);
            } else if(num==6){
                archivo = new FileWriter("tarjetas.txt");
                pw = new PrintWriter(archivo);
                pw.println(s);
            }
            else if (num == 4) {
                archivo = new FileWriter("fechasRecargas.txt", true);
                pw = new PrintWriter(archivo);
                pw.println(s);
            }
             else if (num == 5) {
                archivo = new FileWriter("fechasExtracciones.txt", true);
                pw = new PrintWriter(archivo);
                pw.println(s);
            }

        } catch (Exception ep) {
            ep.printStackTrace();
        } finally {
            try {
                // Nuevamente aprovechamos el finally para
                // asegurarnos que se cierra el fichero.
                if (null != archivo) {
                    archivo.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

    }
}
