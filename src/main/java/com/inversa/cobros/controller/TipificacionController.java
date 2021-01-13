/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.controller;

import com.inversa.cobros.ejb.TipificacionService;
import com.inversa.cobros.model.Subtipificacion;
import com.inversa.cobros.model.Tipificacion;
import java.io.Serializable;
import java.util.ArrayList;
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
public class TipificacionController implements Serializable {

    @Inject
    private TipificacionService ejbLocal;

    private List<Tipificacion> tipificacionList;

    private List<Subtipificacion> subtipificacionList;
    
    private boolean isDisabledPromesa;

    @PostConstruct
    public void init() {
        
        this.tipificacionList = ejbLocal.findAll();
        
        this.isDisabledPromesa = true;// desabilita el boton de promesas...
    }

    /**
     *
     * @param id
     * @return
     */
    public Tipificacion getTipificacion(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("no id provided");
        }
        for (Tipificacion obj : tipificacionList) {
            if (id.equals(obj.getIdTipificacion())) {
                return obj;
            }
        }
        return null;
    }

    /**
     *
     * @param id
     * @return
     */
    public List<Subtipificacion> getSubtipificacionList(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("no id provided");
        }
        for (Tipificacion obj : tipificacionList) {
            if (id.equals(obj.getIdTipificacion())) {
                return obj.getSubtipificacionList();
            }
        }
        return null;
    }

    public List<Tipificacion> getTipificacionList() {
        return tipificacionList;
    }

    public void setTipificacionList(List<Tipificacion> tipificacionList) {
        this.tipificacionList = tipificacionList;
    }

    public List<Subtipificacion> getSubtipificacionList() {
        return subtipificacionList;
    }

    public void setSubtipificacionList(List<Subtipificacion> subtipificacionList) {
        this.subtipificacionList = subtipificacionList;
    }

    @Inject
    private GestionController gestionController;
    
    /**
     *
     * @param selectedTipificacion
     */
    public void onTipificacionChange(Tipificacion selectedTipificacion) {
        if (selectedTipificacion != null) {
            this.subtipificacionList = selectedTipificacion.getSubtipificacionList();
            
            if(selectedTipificacion.getIdTipificacion() == 1){
                this.setIsDisabledPromesa(false);
            }else{
                this.setIsDisabledPromesa(true);
            }            
            
        } else {
            this.subtipificacionList = new ArrayList<Subtipificacion>();
            this.setIsDisabledPromesa(true);
        }
    }

    public boolean isIsDisabledPromesa() {
        return isDisabledPromesa;
    }

    public void setIsDisabledPromesa(boolean isDisabledPromesa) {
        this.isDisabledPromesa = isDisabledPromesa;
    }

}
