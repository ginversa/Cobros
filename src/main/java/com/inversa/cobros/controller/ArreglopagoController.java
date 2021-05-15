/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.controller;

import com.inversa.cobros.ejb.ArreglopagoService;
import com.inversa.cobros.model.Arreglopago;
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
public class ArreglopagoController implements Serializable {

    @Inject
    private ArreglopagoService ejbLocal;

    private List<Arreglopago> arreglopagoList;

    @PostConstruct
    public void init() {
        this.arreglopagoList = this.ejbLocal.findAll();
    }

    public List<Arreglopago> getArreglopagoList() {
        return arreglopagoList;
    }

    public void setArreglopagoList(List<Arreglopago> arreglopagoList) {
        this.arreglopagoList = arreglopagoList;
    }

    /**
     * 
     * @param id
     * @return 
     */
    public Arreglopago getArreglopago(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("no se proporcionó identificación");
        }
        for (Arreglopago obj : arreglopagoList) {
            if (id.equals(obj.getIdarreglopago())) {
                return obj;
            }
        }
        return null;
    }

}
