/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.controller;

import com.inversa.cobros.ejb.SubtipificacionService;
import com.inversa.cobros.model.Subtipificacion;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Z420WK
 */
@Named
@SessionScoped
public class SubtipificacionController implements Serializable {

    @Inject
    private SubtipificacionService ejbLocal;

    private List<Subtipificacion> subtipificacionList;
    private Subtipificacion subtipificacionSelected;

    @PostConstruct
    public void init() {
        this.subtipificacionList = ejbLocal.findAll();
    }

    /**
     *
     * @param id
     * @return
     */
    public Subtipificacion getSubtipificacion(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("no id provided");
        }
        for (Subtipificacion obj : subtipificacionList) {
            if (id.equals(obj.getIdSubtipificacion())) {
                return obj;
            }
        }
        return null;
    }

    public List<Subtipificacion> getSubtipificacionList() {
        return subtipificacionList;
    }

    public void setSubtipificacionList(List<Subtipificacion> subtipificacionList) {
        this.subtipificacionList = subtipificacionList;
    }

    public Subtipificacion getSubtipificacionSelected() {
        return subtipificacionSelected;
    }

    public void setSubtipificacionSelected(Subtipificacion subtipificacionSelected) {
        this.subtipificacionSelected = subtipificacionSelected;
    }

}
