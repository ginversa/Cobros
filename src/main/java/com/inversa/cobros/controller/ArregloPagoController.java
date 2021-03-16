/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.controller;

import com.inversa.cobros.model.ArregloPago;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author Z420WK
 */

@Named
@ViewScoped
public class ArregloPagoController implements Serializable{
    
    private List<ArregloPago> arregloPagoList;
    
    private ArregloPago arregloPago;
    
    @PostConstruct
    public void init(){
    
    }

    public List<ArregloPago> getArregloPagoList() {
        return arregloPagoList;
    }

    public void setArregloPagoList(List<ArregloPago> arregloPagoList) {
        this.arregloPagoList = arregloPagoList;
    }

    public ArregloPago getArregloPago() {
        return arregloPago;
    }

    public void setArregloPago(ArregloPago arregloPago) {
        this.arregloPago = arregloPago;
    }    
    
}
