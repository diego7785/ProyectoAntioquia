/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Excepciones;

/**
 *
 * @author Lenovo
 */
public class NumerosPosi extends Exception {

    /**
     * Creates a new instance of <code>NumerosPosi</code> without detail
     * message.
     */
    public NumerosPosi() {
    }

    /**
     * Constructs an instance of <code>NumerosPosi</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public NumerosPosi(String msg) {
        super(msg);
    }
}
