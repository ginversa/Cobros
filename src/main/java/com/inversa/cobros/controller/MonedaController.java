/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.controller;

import com.inversa.cobros.ejb.MonedaService;
import com.inversa.cobros.model.Moneda;
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
public class MonedaController implements Serializable {

    @Inject
    private MonedaService ejbLocal;

    private List<Moneda> monedaList;

    @PostConstruct
    public void init() {
        this.monedaList = this.ejbLocal.findAll();
    }

    public List<Moneda> getMonedaList() {
        return monedaList;
    }

    public void setMonedaList(List<Moneda> monedaList) {
        this.monedaList = monedaList;
    }

    /**
     * 
     * @param id
     * @return 
     */
    public Moneda getMoneda(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("no se proporcionó identificación");
        }
        for (Moneda obj : monedaList) {
            if (id.equals(obj.getIdMoneda())) {
                return obj;
            }
        }
        return null;
    }

}
