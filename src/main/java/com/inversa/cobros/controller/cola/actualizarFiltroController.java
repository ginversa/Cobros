/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.controller.cola;

import com.inversa.cobros.ejb.FiltrocolaService;
import com.inversa.cobros.ejb.TipificacionService;
import com.inversa.cobros.model.Subtipificacion;
import com.inversa.cobros.model.TblFiltrocola;
import com.inversa.cobros.model.TblUsuario;
import com.inversa.cobros.model.Tipificacion;
import java.io.IOException;
import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.PrimeFaces;

/**
 *
 * @author Z420WK
 */
@Named
@ViewScoped
public class actualizarFiltroController implements Serializable {

    @Inject
    private ListarFiltroController listarColaController;

    @Inject
    private FiltrocolaService ejbFiltrocolaLocal;

    private TblFiltrocola filtrocola;

    @Inject
    private TipificacionService ejbTipificacionLocal;

    private List<Tipificacion> tipificacionList;
    private List<Subtipificacion> subtipificacionList;

    private Calendar fechaHoy;
    private TblUsuario usuario;

    @PostConstruct
    public void init() {
        // Usuario de session...
        this.usuario = (TblUsuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        this.fechaHoy = Calendar.getInstance();

        this.filtrocola = this.listarColaController.getSelectedFiltrocola();
        this.tipificacionList = ejbTipificacionLocal.findAll();        
        onTipificacionChange();
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

    public TblFiltrocola getFiltrocola() {
        return filtrocola;
    }

    public void setFiltrocola(TblFiltrocola filtrocola) {
        this.filtrocola = filtrocola;
    }

    /**
     *
     */
    public void onTipificacionChange() {
        if (this.filtrocola.getIdTipificacion() != null) {
            this.subtipificacionList = this.filtrocola.getIdTipificacion().getSubtipificacionList();
        }
    }

    /**
     *
     */
    public void guardar() {
        try {
            String codigoCartera = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("codigo_cartera");

            if (this.filtrocola.getNombre() == null || this.filtrocola.getNombre().trim().equals("")) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso!", "Nombre requerido!");
                FacesContext.getCurrentInstance().addMessage(null, msg);

            } else {

                this.filtrocola.setEstado("ACT");
                this.filtrocola.setFechamodifico(this.fechaHoy.getTime());
                this.filtrocola.setUsuariomodifico(this.usuario.getUsuario());
                this.filtrocola.setCodigoCartera(codigoCartera);
                this.ejbFiltrocolaLocal.update(this.filtrocola);
                PrimeFaces.current().ajax().update("formCola");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso!", "Filtros actualizado. Correcto!"));
                FacesContext.getCurrentInstance().getExternalContext().redirect("listarFiltro.xhtml");

            }

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error!", "Error registrando filtros. Error!"));
            System.out.println(e.getMessage());

        }

    }

    /**
     *
     */
    public void listarCola() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("listarFiltro.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(FiltrocolaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
        /**
     *
     */
    public void generarCola() {
        try {
            if (this.filtrocola != null && this.filtrocola.getIdFiltrocola() != null) {
                this.ejbFiltrocolaLocal.generarCola(this.filtrocola,this.usuario.getUsuarioingreso(),this.fechaHoy.getTime());
                PrimeFaces.current().ajax().update("formCola");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso!", "Cola Generada. Correcto!"));
                FacesContext.getCurrentInstance().getExternalContext().redirect("listarCola.xhtml");
            }

        } catch (IOException ex) {
            Logger.getLogger(FiltrocolaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
