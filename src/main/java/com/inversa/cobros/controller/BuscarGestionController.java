/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.controller;

import com.inversa.cobros.ejb.GestionService;
import com.inversa.cobros.ejb.LlamadaService;
import com.inversa.cobros.model.TblCartera;
import com.inversa.cobros.model.TblGestion;
import com.inversa.cobros.model.TblLlamada;
import com.inversa.cobros.model.TblPromesa;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
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
    private LlamadaService ejbLlamadaServiceLocal;

    @Inject
    private CarteraController carteraController;

    private List<TblGestion> gestionList;
    private TblGestion searchGestion;
    private TblLlamada selectedLlamada;

    private List<TblLlamada> llamadaList;

    private String identificacion;
    private String telefono;

    @PostConstruct
    public void init() {
        this.llamadaList = new ArrayList<>();
        this.searchGestion = new TblGestion();
        this.selectedLlamada = new TblLlamada();
    }

    /**
     *
     */
    public void buscarPorIdentificacion() {

        if (this.identificacion == null || this.identificacion.trim().equals("")) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso!", "Identificación requerida!");
            FacesContext.getCurrentInstance().addMessage(null, msg);

        } else {
            if (this.llamadaList != null) {
                this.llamadaList.clear();
            }

            String codigoCartera = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("codigo_cartera");
            this.llamadaList = this.ejbLlamadaServiceLocal.findByIdentificacionCartera(this.identificacion, codigoCartera);
            if (this.llamadaList != null && !this.llamadaList.isEmpty() && this.llamadaList.size() > 0) {
                this.searchGestion = this.llamadaList.get(0).getIdGestion();
            }
        }
    }

    /**
     *
     */
    public void buscarPorTelefono() {

        if (this.telefono == null || this.telefono.trim().equals("")) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso!", "Teléfono requerido!");
            FacesContext.getCurrentInstance().addMessage(null, msg);

        } else {
            if (this.llamadaList != null) {
                this.llamadaList.clear();
            }

            String codigoCartera = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("codigo_cartera");
            this.llamadaList = this.ejbLlamadaServiceLocal.buscarPorTelefono(this.telefono, codigoCartera);

            if (this.llamadaList != null && !this.llamadaList.isEmpty() && this.llamadaList.size() > 0) {
                this.searchGestion = this.llamadaList.get(0).getIdGestion();
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

    public TblLlamada getSelectedLlamada() {
        return selectedLlamada;
    }

    public void setSelectedLlamada(TblLlamada selectedLlamada) {
        this.selectedLlamada = selectedLlamada;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     *
     * @param promesa
     */
    public void setPromesaTOGestion(TblPromesa promesa) {
        if (promesa != null) {
            String operacion = promesa.getOperacion();
            TblLlamada llamada = promesa.getIdLlamada();
            if (llamada.getOperacion() == null || llamada.getOperacion().equals("")) {
                llamada.setOperacion(promesa.getOperacion());
            }

            this.setSelectedLlamada(llamada);
            TblCartera operacion_cartera = this.carteraController.searchCarteraByOperacion(operacion);
            this.carteraController.setCartera(operacion_cartera);
        }
    }

    /**
     *
     * @param llamada
     */
    public void setLlamadaTOGestion(TblLlamada llamada) {
        String operacion = llamada.getOperacion();
        this.setSelectedLlamada(llamada);
        TblCartera operacion_cartera = this.carteraController.searchCarteraByOperacion(operacion);
        this.carteraController.setCartera(operacion_cartera);
    }

    /**
     * 
     * @param tblGestion 
     */
    public void setGestionTOGestion(TblGestion tblGestion) {
        if (tblGestion != null) {            
            List<TblCartera> operacionList = this.carteraController.searchCarteraByIdentificacion(tblGestion.getIdentificacion());
            if (operacionList != null && !operacionList.isEmpty() && operacionList.size() > 0) {
                TblCartera operacion_cartera = operacionList.get(0);
                this.carteraController.setCartera(operacion_cartera);
            }
        }
    }

}
