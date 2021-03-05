/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.controller;

import com.inversa.cobros.ejb.RazonMoraService;
import com.inversa.cobros.model.Razonmora;
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
public class RazonmoraController implements Serializable {

    @Inject
    private RazonMoraService ejbLocal;

    private List<Razonmora> razonmoraList;
    private Razonmora razonmoraSelected;

    @PostConstruct
    public void init() {
        this.razonmoraList = this.findAll();
    }

    public List<Razonmora> findAll() {
        return this.ejbLocal.findAll();
    }

    public Razonmora findById(Razonmora obj) {
        return this.ejbLocal.findById(obj);
    }

    /**
     * 
     * @param id
     * @return 
     */
    public Razonmora getRazonmora(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("no id provided");
        }
        for (Razonmora obj : razonmoraList) {
            if (id.equals(obj.getIdrazonmora())) {
                return obj;
            }
        }
        return null;
    }

    public List<Razonmora> getRazonmoraList() {
        return razonmoraList;
    }

    public void setRazonmoraList(List<Razonmora> RazonmoraList) {
        this.razonmoraList = RazonmoraList;
    }

    public Razonmora getRazonmoraSelected() {
        return razonmoraSelected;
    }

    public void setRazonmoraSelected(Razonmora RazonmoraSelected) {
        this.razonmoraSelected = RazonmoraSelected;
    }

}
