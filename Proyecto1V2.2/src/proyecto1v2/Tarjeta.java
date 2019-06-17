/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package proyecto1v2;

import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

/**
 *
 * @author User
 */
public class Tarjeta {
    
    private int numero;
    private int idUsuario;
    private String nombreUsuario;
    private String direccionUsuario;
    private String fechaCompra;
    private int saldo;
    private Vector fechasRecargas;
    private Vector fechasExtracciones;
   
    
    public Tarjeta()
    
    {
    
        fechasRecargas = new Vector();
        fechasExtracciones=new Vector();
        /*numero=numTar;
        idUsuario=idPro;
        nombreUsuario=nombrePro;
        direccionUsuario=direccPro;
        fechaCompra=fecha;
        saldo=saldoTar;*/
        
    }

    /**
     * @return the numero
     */
    public int getNumero() {
        return numero;
    }

    /**
     * @param numero the numero to set
     */
    public void setNumero(int numero) {
        this.numero = numero;
    }

    /**
     * @return the idUsuario
     */
    public int getIdUsuario() {
        return idUsuario;
    }

    /**
     * @param idUsuario the idUsuario to set
     */
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    /**
     * @return the nombreUsuario
     */
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    /**
     * @param nombreUsuario the nombreUsuario to set
     */
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    /**
     * @return the direccionUsuario
     */
    public String getDireccionUsuario() {
        return direccionUsuario;
    }

    /**
     * @param direccionUsuario the direccionUsuario to set
     */
    public void setDireccionUsuario(String direccionUsuario) {
        this.direccionUsuario = direccionUsuario;
    }

    /**
     * @return the fechaCompra
     */
    public String getFechaCompra() {
        return fechaCompra;
    }

    /**
     * @param fechaCompra the fechaCompra to set
     */
    public void setFechaCompra(String fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    /**
     * @return the saldo
     */
    public int getSaldo() {
        return saldo;
    }

    /**
     * @param saldo the saldo to set
     */
    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }

    /**
     * @return the fechasRecargas
     */
    public Vector getFechasRecargas() {
        return fechasRecargas;
    }

    /**
     * @param fechasRecargas the fechasRecargas to set
     */
    public void setFechasRecargas(String fechaRecarga) {
        this.fechasRecargas.add(fechasRecargas);
    }
    /**
     * @return the fechasExtracciones
     */
    public Vector getFechasExtracciones() {
        return fechasExtracciones;
    }

    /**
     * @param fechasExtracciones the fechasExtracciones to set
     */
    public void setFechasExtracciones(String fechaExtraccion) {
        this.fechasExtracciones.add(fechaExtraccion);
    }
    
    
    
    
}
