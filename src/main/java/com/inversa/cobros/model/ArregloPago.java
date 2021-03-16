/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.model;

import java.io.Serializable;

/**
 *
 * @author Z420WK
 */

public class ArregloPago implements Serializable{
 
    private String codigo;
    private String nombre;
    private String codigo_cliente;

    public ArregloPago(String codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }

    public ArregloPago(String codigo, String nombre, String codigo_cliente) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.codigo_cliente = codigo_cliente;
    }    

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigo_cliente() {
        return codigo_cliente;
    }

    public void setCodigo_cliente(String codigo_cliente) {
        this.codigo_cliente = codigo_cliente;
    }
    
}
