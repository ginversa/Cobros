/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.controller;

import com.inversa.cobros.ejb.EstadopromesaService;
import com.inversa.cobros.model.Estadopromesa;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Z420WK
 */
@Named
@ViewScoped
public class EstadopromesaController implements Serializable {

    @Inject
    private EstadopromesaService ejbLocal;

    private List<Estadopromesa> estadopromesaList;


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
        this.estadopromesaList = this.ejbLocal.findAll();
    }

    public List<Estadopromesa> getEstadopromesaList() {
        return estadopromesaList;
    }

    public void setEstadopromesaList(List<Estadopromesa> estadopromesaList) {
        this.estadopromesaList = estadopromesaList;
    }

    /**
     * 
     * @param id
     * @return 
     */
    public Estadopromesa getEstadopromesa(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("no se proporcionó identificación");
        }
        for (Estadopromesa obj : estadopromesaList) {
            if (id.equals(obj.getIdestadopromesa())) {
                return obj;
            }
        }
        return null;
    }

}
