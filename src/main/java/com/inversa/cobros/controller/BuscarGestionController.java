/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.controller;

import com.inversa.cobros.ejb.GestionService;
import com.inversa.cobros.ejb.PromesaService;
import com.inversa.cobros.model.TblGestion;
import com.inversa.cobros.model.TblLlamada;
import com.inversa.cobros.model.TblPromesa;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Z420WK
 */
@Named
@SessionScoped

public class BuscarGestionController implements Serializable {

    @Inject
    private GestionService ejbLocal;

    @Inject
    private PromesaService ejbPromesaServiceLocal;

    private List<TblGestion> gestionList;
    private TblGestion searchGestion;
    private TblGestion selectedGestion;
    
    private List<TblLlamada> llamadaList;    

    @PostConstruct
    public void init() {        
        this.llamadaList = new ArrayList<TblLlamada>();        
        this.searchGestion = new TblGestion();
        this.selectedGestion = new TblGestion();
    }    

    /**
     * 
     */
    public void buscarGestion() {
        
        if(this.llamadaList != null){
            this.llamadaList.clear();
        }
        
        String codigoCartera = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("codigo_cartera");        
        this.searchGestion.setCodigoCartera(codigoCartera);

        this.gestionList = this.ejbLocal.findByIdentificacionANDCodigoCartera(this.searchGestion);

        if (this.gestionList != null && !this.gestionList.isEmpty() && this.gestionList.size() > 0) {
            this.searchGestion = this.gestionList.get(0);
            for (int index = 0; index < this.gestionList.size(); index++) {
                List<TblLlamada> llamadas = this.gestionList.get(index).getTblLlamadaList();
                if (llamadas != null && !llamadas.isEmpty() && llamadas.size() > 0) {
                    for (int i = 0; i < llamadas.size(); i++) {
                        TblPromesa promesaUltimoPago = this.ejbPromesaServiceLocal.findPromesaUltimoPago(this.gestionList.get(index).getIdGestion(), llamadas.get(i).getIdLlamada());
                        llamadas.get(i).setUltimaPromesa(promesaUltimoPago);
                        this.llamadaList.add(llamadas.get(i));
                    }
                }
            }
        }
    }
    

    public List<TblGestion> getGestionList() {
        return gestionList;
    }

    public void setGestionList(List<TblGestion> gestionList) {
        this.gestionList = gestionList;
    }

    public List<TblLlamada> getLlamadaList() {
        return llamadaList;
    }

    public void setLlamadaList(List<TblLlamada> llamadaList) {
        this.llamadaList = llamadaList;
    }

    public TblGestion getSearchGestion() {
        return searchGestion;
    }

    public void setSearchGestion(TblGestion searchGestion) {
        this.searchGestion = searchGestion;
    }

    public TblGestion getSelectedGestion() {
        return selectedGestion;
    }

    public void setSelectedGestion(TblGestion selectedGestion) {
        this.selectedGestion = selectedGestion;
    }    

}
