/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author invitado
 */

package proyecto1v2;


public class Bus {

    private String placa;
    private int modelo;
    private String marca;
    private String tipo; 
    private int capacidad;
    private Ruta ruta;
    
    public Bus(String placa, int modelo, String marca, String tipo, int capacidad, Ruta ruta){
        this.placa=placa;
        this.modelo=modelo;
        this.marca=marca;
        this.tipo=tipo;
        this.capacidad=capacidad;
        this.ruta=ruta;
    }

    /**
     * @return the placa
     */
    public String getPlaca() {
        return placa;
    }

    /**
     * @param placa the placa to set
     */
    public void setPlaca(String placa) {
        this.placa = placa;
    }

    /**
     * @return the modelo
     */
    public int getModelo() {
        return modelo;
    }

    /**
     * @param modelo the modelo to set
     */
    public void setModelo(int modelo) {
        this.modelo = modelo;
    }

    /**
     * @return the marca
     */
    public String getMarca() {
        return marca;
    }

    /**
     * @param marca the marca to set
     */
    public void setMarca(String marca) {
        this.marca = marca;
    }

    /**
     * @return the tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * @return the capacidad
     */
    public int getCapacidad() {
        return capacidad;
    }

    /**
     * @param capacidad the capacidad to set
     */
    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }
    
    public void setRuta(Ruta ruta){
        this.ruta=ruta;
    }
    
    public Ruta getRuta(){
        return ruta;
    }
    
}
