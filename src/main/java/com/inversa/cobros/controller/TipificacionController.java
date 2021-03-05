/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.controller;

import com.inversa.cobros.ejb.TipificacionService;
import com.inversa.cobros.model.Subtipificacion;
import com.inversa.cobros.model.TblLlamada;
import com.inversa.cobros.model.TblResultadogestion;
import com.inversa.cobros.model.TblResultadotercero;
import com.inversa.cobros.model.Tipificacion;
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
public class TipificacionController implements Serializable {

    @Inject
    private TipificacionService ejbLocal;

    private List<Tipificacion> tipificacionList;

    private List<Subtipificacion> subtipificacionList;

    private List<TblResultadogestion> resultadogestionList;

    private List<TblResultadotercero> resultadoterceroList;

    private boolean isDisabledPromesa;

    @Inject
    private CarteraGestionController carteraGestionController;

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

    /**
     *
     * @param selectedTipificacion
     */
    public void onTipificacionChange(TblLlamada selectedLlamada) {
        if (selectedLlamada != null) {
            Tipificacion selectedTipificacion = selectedLlamada.getIdTipificacion();
            if (selectedTipificacion != null) {
                this.subtipificacionList = selectedTipificacion.getSubtipificacionList();
                this.resultadogestionList = selectedTipificacion.getTblResultadogestionList();
                this.resultadoterceroList = selectedTipificacion.getTblResultadoterceroList();

                if (selectedTipificacion.getIdTipificacion() == 1) {
                    this.setIsDisabledPromesa(false);
                } else {
                    this.setIsDisabledPromesa(true);
                }

            } else {
                this.subtipificacionList = new ArrayList<Subtipificacion>();
                this.setIsDisabledPromesa(true);
            }

            this.carteraGestionController.setSubtipificacionNullonLlamada(selectedLlamada);
            this.carteraGestionController.setResultadoGestionNullonLlamada(selectedLlamada);
            this.carteraGestionController.setResultadoTerceroNullonLlamada(selectedLlamada);
        }

    }

    /**
     *
     * @param selectedResultadogestion
     */
    public void onResultadogestionChange(TblLlamada selectedLlamada) {
        if (selectedLlamada != null) {
            TblResultadogestion selectedResultadogestion = selectedLlamada.getIdResultadogestion();
            if (selectedResultadogestion != null) {
                this.resultadoterceroList = selectedResultadogestion.getTblResultadoterceroList();

            } else {
                this.resultadoterceroList = new ArrayList<TblResultadotercero>();
            }
            
            this.carteraGestionController.setResultadoTerceroNullonLlamada(selectedLlamada);
        }
    }

    public boolean isIsDisabledPromesa() {
        return isDisabledPromesa;
    }

    public void setIsDisabledPromesa(boolean isDisabledPromesa) {
        this.isDisabledPromesa = isDisabledPromesa;
    }

    public List<TblResultadogestion> getResultadogestionList() {
        return resultadogestionList;
    }

    public void setResultadogestionList(List<TblResultadogestion> resultadogestionList) {
        this.resultadogestionList = resultadogestionList;
    }

    public List<TblResultadotercero> getResultadoterceroList() {
        return resultadoterceroList;
    }

    public void setResultadoterceroList(List<TblResultadotercero> resultadoterceroList) {
        this.resultadoterceroList = resultadoterceroList;
    }

}
