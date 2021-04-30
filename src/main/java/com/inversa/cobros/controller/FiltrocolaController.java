/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.controller;

import com.inversa.cobros.ejb.TipificacionService;
import com.inversa.cobros.model.Razonmora;
import com.inversa.cobros.model.Subtipificacion;
import com.inversa.cobros.model.Tipificacion;
import java.io.Serializable;
import java.math.BigDecimal;
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
public class FiltrocolaController implements Serializable {
    
    @Inject    
    private TipificacionService ejbTipificacionLocal;

    private List<Tipificacion> tipificacionList;
    
    private Tipificacion tipificacion;

    private List<Subtipificacion> subtipificacionList;

    private Subtipificacion subTipificacion;

    private Razonmora razonmora;

    private int poseeTrabajo;
    
    private int diasultimopagopromesa;
    
    private int rangobalance_tiposalario;
    
    private String rangobalance;
    private String tiposalario;
    
    private BigDecimal cantidad;
    
    private int fechasparaaplicar;

    @PostConstruct
    public void init() {        
        this.tipificacionList = ejbTipificacionLocal.findAll();
    }

    public Tipificacion getTipificacion() {
        return tipificacion;
    }

    public void setTipificacion(Tipificacion tipificacion) {
        this.tipificacion = tipificacion;
    }

    public Subtipificacion getSubTipificacion() {
        return subTipificacion;
    }

    public void setSubTipificacion(Subtipificacion subTipificacion) {
        this.subTipificacion = subTipificacion;
    }

    public Razonmora getRazonmora() {
        return razonmora;
    }

    public void setRazonmora(Razonmora razonmora) {
        this.razonmora = razonmora;
    }

    public int getPoseeTrabajo() {
        return poseeTrabajo;
    }

    public void setPoseeTrabajo(int poseeTrabajo) {
        this.poseeTrabajo = poseeTrabajo;
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

    public int getDiasultimopagopromesa() {
        return diasultimopagopromesa;
    }

    public void setDiasultimopagopromesa(int diasultimopagopromesa) {
        this.diasultimopagopromesa = diasultimopagopromesa;
    }

    public int getRangobalance_tiposalario() {
        return rangobalance_tiposalario;
    }

    public void setRangobalance_tiposalario(int rangobalance_tiposalario) {
        this.rangobalance_tiposalario = rangobalance_tiposalario;
    }

    public String getRangobalance() {
        return rangobalance;
    }

    public void setRangobalance(String rangobalance) {
        this.rangobalance = rangobalance;
    }

    public String getTiposalario() {
        return tiposalario;
    }

    public void setTiposalario(String tiposalario) {
        this.tiposalario = tiposalario;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public int getFechasparaaplicar() {
        return fechasparaaplicar;
    }

    public void setFechasparaaplicar(int fechasparaaplicar) {
        this.fechasparaaplicar = fechasparaaplicar;
    }    
    

    /**
     * 
     */
    public void onTipificacionChange() {        
        if (this.tipificacion != null) {
            this.subtipificacionList = this.tipificacion.getSubtipificacionList();
        }
    }

}
