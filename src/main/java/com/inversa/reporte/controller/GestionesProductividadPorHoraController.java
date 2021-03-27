/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.reporte.controller;

import com.inversa.cobros.model.TblUsuario;
import com.inversa.reporte.ejb.GestionesProductividadPorHoraService;
import com.inversa.reporte.model.GestionesProductividadPorHora;
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

/**
 *
 * @author Z420WK
 */

@Named
@ViewScoped
public class GestionesProductividadPorHoraController implements Serializable{
 
    
    @Inject
    private GestionesProductividadPorHoraService ejbLocal;
    
    private Date fecha;
    private String codigoCartera;
    
    private List<GestionesProductividadPorHora> gestionList;
    
    private Calendar fechaHoy;
    private TblUsuario usuario;
    
    @PostConstruct
    public void init(){
        this.fechaHoy = Calendar.getInstance();
        this.usuario = (TblUsuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        //this.codigoGestor = this.usuario.getCodigoGestor();
        
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
            this.gestionList = this.ejbLocal.getProductividadPorHora(fecha, codigoCartera);
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

    public List<GestionesProductividadPorHora> getGestionList() {
        return gestionList;
    }

    public void setGestionList(List<GestionesProductividadPorHora> gestionList) {
        this.gestionList = gestionList;
    }

}
