/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.controller;

import com.inversa.cobros.ejb.GestionService;
import com.inversa.cobros.ejb.LlamadaService;
import com.inversa.cobros.ejb.PromesaService;
import com.inversa.cobros.model.TblGestion;
import com.inversa.cobros.model.TblLlamada;
import com.inversa.cobros.model.TblPromesa;
import java.io.Serializable;
import java.util.ArrayList;
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
public class GestionController implements Serializable {

    @Inject
    private GestionService ejbLocal;
    
    @Inject
    private LlamadaService ejbLlamadaLocal;

    @Inject
    private PromesaService ejbPromesaServiceLocal;

    private List<TblGestion> gestionList;
    private TblGestion gestion;

    private List<TblLlamada> llamadaList;

    @PostConstruct
    public void init() {
        this.llamadaList = new ArrayList<TblLlamada>();
    }

    public List<TblGestion> getGestionList() {
        return gestionList;
    }

    public void setGestionList(List<TblGestion> gestionList) {
        this.gestionList = gestionList;
    }

    public TblGestion getGestion() {
        return gestion;
    }

    public void setGestion(TblGestion gestion) {
        this.gestion = gestion;
    }

    public List<TblLlamada> getLlamadaList() {
        return llamadaList;
    }

    public void setLlamadaList(List<TblLlamada> llamadaList) {
        this.llamadaList = llamadaList;
    }

    /**
     *
     * @param cartera
     * @param identificacion
     */
    public void cargarGestiones(String cartera, String identificacion) {
        if(this.llamadaList != null && !this.llamadaList.isEmpty() && this.llamadaList.size()>0){
            this.llamadaList.clear();
        }
        
        this.llamadaList = this.ejbLlamadaLocal.findByIdentificacionCartera(identificacion, cartera);
    }

}
