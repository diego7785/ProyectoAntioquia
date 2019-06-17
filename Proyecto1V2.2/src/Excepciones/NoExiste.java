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
public class NoExiste extends Exception {

    /**
     * Creates a new instance of <code>NoExiste</code> without detail message.
     */
    public NoExiste() {
    }

    /**
     * Constructs an instance of <code>NoExiste</code> with the specified detail
     * message.
     *
     * @param msg the detail message.
     */
    public NoExiste(String msg) {
        super(msg);
    }
}
