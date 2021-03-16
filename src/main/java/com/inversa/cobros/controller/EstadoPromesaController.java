/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.controller;

import com.inversa.cobros.model.EstadoPromesa;
import java.io.Serializable;
import java.util.ArrayList;
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
public class EstadoPromesaController implements Serializable {

    private List<EstadoPromesa> estadoPromesaList;

    private EstadoPromesa estadoPromesa;

    /*
    -- Estados de una promesa.
    SEG - Seguimiento, antes de recordatorio
    PRO - Promesa, si cuando lo llame recordando, dice que si paga mañana.
    INC - Incumplida, al resivir los pago, se comprueba que no pago.
    EFE - Efectiva, al resivir los pago, se comprueba que si pago.
    DEL - Registro borrado
    REP - Reprogramada
    HIS - Histórico
     */
    @PostConstruct
    public void init() {
        this.estadoPromesaList = new ArrayList<>();
        EstadoPromesa seg = new EstadoPromesa("SEG", "Seguimiento");
        EstadoPromesa pro = new EstadoPromesa("PRO", "Promesa");
        EstadoPromesa rep = new EstadoPromesa("REP", "Reprogramada");
        EstadoPromesa del = new EstadoPromesa("DEL", "Eliminar");
        
        this.estadoPromesaList.add(seg);
        this.estadoPromesaList.add(pro);
        this.estadoPromesaList.add(rep);
        this.estadoPromesaList.add(del);
    }

    public List<EstadoPromesa> getEstadoPromesaList() {
        return estadoPromesaList;
    }

    public void setEstadoPromesaList(List<EstadoPromesa> estadoPromesaList) {
        this.estadoPromesaList = estadoPromesaList;
    }

    public EstadoPromesa getEstadoPromesa() {
        return estadoPromesa;
    }

    public void setEstadoPromesa(EstadoPromesa estadoPromesa) {
        this.estadoPromesa = estadoPromesa;
    }

}
