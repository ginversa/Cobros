/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.reporte.controller;

import com.inversa.cobros.model.TblUsuario;
import com.inversa.reporte.model.GestionProductividadPorHora;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import com.inversa.reporte.ejb.GestionProductividadService;

/**
 *
 * @author Z420WK
 */

@Named
@ViewScoped
public class GestionProductividadPorHoraController implements Serializable{
 
    
    @Inject
    private GestionProductividadService ejbLocal;
    
    private Date fecha;
    private String codigoCartera;
    
    private List<GestionProductividadPorHora> gestionList;
    
    private Calendar fechaHoy;
    private TblUsuario usuario;
    
    @PostConstruct
    public void init(){
        this.fechaHoy = Calendar.getInstance();
        this.usuario = (TblUsuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        this.fecha = this.fechaHoy.getTime();
        
    }
    
    /**
     * 
     */
    public void buscar(){
    
        this.codigoCartera = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("codigo_cartera"); 
        
        if (this.fecha == null) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso!", "Fecha requerida!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            
        } else if(this.codigoCartera == null || this.codigoCartera.trim().equals("")){
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso!", "Código cartera requerida!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            
        }else{
            this.gestionList = this.ejbLocal.getProductividadPorHora(this.fecha, this.codigoCartera);
        }        
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getCodigoCartera() {
        return codigoCartera;
    }

    public void setCodigoCartera(String codigoCartera) {
        this.codigoCartera = codigoCartera;
    }

    public List<GestionProductividadPorHora> getGestionList() {
        return gestionList;
    }

    public void setGestionList(List<GestionProductividadPorHora> gestionList) {
        this.gestionList = gestionList;
    }

}
