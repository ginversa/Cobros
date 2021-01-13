/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.controller;

import com.inversa.cobros.ejb.TipoTelefonoService;
import com.inversa.cobros.model.Tipotelefono;
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
public class TipotelefonoController implements Serializable {

    @Inject
    private TipoTelefonoService ejbLocal;

    private List<Tipotelefono> tipotelefonoList;
    private Tipotelefono tipotelefonoSelected;

    @PostConstruct
    public void init() {
        this.tipotelefonoList = this.findAll();
    }

    public List<Tipotelefono> findAll() {
        return this.ejbLocal.findAll();
    }

    public Tipotelefono findById(Tipotelefono obj) {
        return this.ejbLocal.findById(obj);
    }

    public List<Tipotelefono> getTipotelefonoList() {
        return tipotelefonoList;
    }

    public void setTipotelefonoList(List<Tipotelefono> tipotelefonoList) {
        this.tipotelefonoList = tipotelefonoList;
    }

    public Tipotelefono getTipotelefonoSelected() {
        return tipotelefonoSelected;
    }

    public void setTipotelefonoSelected(Tipotelefono tipotelefonoSelected) {
        this.tipotelefonoSelected = tipotelefonoSelected;
    }

    /**
     * 
     * @param id
     * @return 
     */
    public Tipotelefono getTipotelefono(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("no id provided");
        }
        for (Tipotelefono obj : tipotelefonoList) {
            if (id.equals(obj.getIdTipotelefono())) {
                return obj;
            }
        }
        return null;
    }
}
