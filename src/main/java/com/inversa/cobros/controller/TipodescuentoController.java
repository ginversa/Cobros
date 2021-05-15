/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.controller;

import com.inversa.cobros.ejb.TipodescuentoService;
import com.inversa.cobros.model.Tipodescuento;
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
public class TipodescuentoController implements Serializable {

    @Inject
    private TipodescuentoService ejbLocal;

    private List<Tipodescuento> tipodescuentoList;

    @PostConstruct
    public void init() {
        this.tipodescuentoList = this.ejbLocal.findAll();
    }

    public List<Tipodescuento> getTipodescuentoList() {
        return tipodescuentoList;
    }

    public void setTipodescuentoList(List<Tipodescuento> tipodescuentoList) {
        this.tipodescuentoList = tipodescuentoList;
    }

    /**
     *
     * @param id
     * @return
     */
    public Tipodescuento getTipodescuento(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("no se proporcionó identificación");
        }
        for (Tipodescuento obj : tipodescuentoList) {
            if (id.equals(obj.getIdtipodescuento())) {
                return obj;
            }
        }
        return null;
    }

}
