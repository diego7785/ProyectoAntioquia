/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ManejoDeArchivos;

import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 *
 * @author MELI
 */
public class LeerArchivo {

    private ArrayList<String> ruta = new ArrayList<String>();
    private ArrayList<String> bus = new ArrayList<String>();
    private ArrayList<String> tarjeta = new ArrayList<String>();
    private ArrayList<String> fechasRecargas = new ArrayList<String>();
    private ArrayList<String> fechasDescargas = new ArrayList<String>();
    

    public LeerArchivo() {

    }

    public void readFile(String direccion, int tipo) {//1. Ruta , 2. Buses , 3. Tarjetas, 4.fechas recargas , 5. fechas descargas

        String acumulador = "";
        try {
            File archivo = new File(direccion);

            FileReader fr = new FileReader(archivo);
            BufferedReader br = new BufferedReader(fr);

            String linea;
            StringTokenizer tokens;

            while ((linea = br.readLine()) != null) {
                acumulador = linea;
                tokens = new StringTokenizer(acumulador, ",");
                if (tipo == 1) {
                    while (tokens.hasMoreTokens()) {
                        ruta.add(tokens.nextToken());
                    }

                } else if (tipo == 2) {
                    while (tokens.hasMoreTokens()) {
                        bus.add(tokens.nextToken());
                    }
                } else if (tipo == 3) {
                   while (tokens.hasMoreTokens()) {
                        tarjeta.add(tokens.nextToken());
                    }
                }else if (tipo == 4) {
                   while (tokens.hasMoreTokens()) {
                        fechasRecargas.add(tokens.nextToken());
                    }
                } else if (tipo == 5) {
                   while (tokens.hasMoreTokens()) {
                        fechasDescargas.add(tokens.nextToken());
                    }
                }
            }

        } catch (FileNotFoundException ep) {
         
        } catch (IOException ep) {

        }
    }

    public ArrayList getBus() {
        return bus;
    }

    public ArrayList getRuta() {
        return ruta;
    }

    public ArrayList getTarjeta() {
        return tarjeta;
    }
    
    public ArrayList<String> getFechasRecargas() {
        return fechasRecargas;
    }

    public void setFechasRecargas(ArrayList<String> fechasRecargas) {
        this.fechasRecargas = fechasRecargas;
    }

    public ArrayList<String> getFechasDescargas() {
        return fechasDescargas;
    }

    public void setFechasDescargas(ArrayList<String> fechasDescargas) {
        this.fechasDescargas = fechasDescargas;
    }
    
    
    
}
