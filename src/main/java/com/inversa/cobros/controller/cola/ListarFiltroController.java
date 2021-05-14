/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.controller.cola;

import com.inversa.cobros.constante.comun.ConstanteComun;
import com.inversa.cobros.ejb.FiltrocolaService;
import com.inversa.cobros.model.TblFiltrocola;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.PrimeFaces;

/**
 *
 * @author Z420WK
 */
@Named
@SessionScoped
public class ListarFiltroController implements Serializable {

    @Inject
    private FiltrocolaService ejbFiltrocolaLocal;

    /*
    @Inject
    private FiltrocolaController filtrocolaController;
     */
    private List<TblFiltrocola> filtrocolaList;

    private TblFiltrocola selectedFiltrocola;

    /*
    private Calendar fechaHoy;
    private TblUsuario usuario;
     */
    @PostConstruct
    public void init() {
        // Usuario de session...
        //this.usuario = (TblUsuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(ConstanteComun.USUARIO);
        String codigoCartera = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(ConstanteComun.COD_CARTERA);
        //this.fechaHoy = Calendar.getInstance();

        TblFiltrocola obj = new TblFiltrocola();
        obj.setCodigoCartera(codigoCartera);
        this.filtrocolaList = this.ejbFiltrocolaLocal.findByCodigoCartera(obj);
    }

    public List<TblFiltrocola> getFiltrocolaList() {
        return filtrocolaList;
    }

    public void setFiltrocolaList(List<TblFiltrocola> filtrocolaList) {
        this.filtrocolaList = filtrocolaList;
    }

    public TblFiltrocola getSelectedFiltrocola() {
        return selectedFiltrocola;
    }

    public void setSelectedFiltrocola(TblFiltrocola selectedFiltrocola) {
        this.selectedFiltrocola = selectedFiltrocola;
    }

    public void buscar() {
        //this.usuario = (TblUsuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(ConstanteComun.USUARIO);
        String codigoCartera = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(ConstanteComun.COD_CARTERA);
        //this.fechaHoy = Calendar.getInstance();

        TblFiltrocola obj = new TblFiltrocola();
        obj.setCodigoCartera(codigoCartera);
        this.filtrocolaList = this.ejbFiltrocolaLocal.findByCodigoCartera(obj);
        PrimeFaces.current().ajax().update("dtfiltrocolaList", "form:messages");
    }

    /**
     *
     */
    public void eliminar() {
        if (this.selectedFiltrocola != null) {
            if (this.selectedFiltrocola.getIdFiltrocola() != null) {

                TblFiltrocola objRemove = this.ejbFiltrocolaLocal.findByIdFiltrocola(this.selectedFiltrocola);
                if (objRemove != null) {
                    objRemove.setEstado(ConstanteComun.Inactivo);
                    this.ejbFiltrocolaLocal.update(objRemove);
                    this.buscar();
                }
            }
        }
    }

    /**
     *
     */
    public void crearCola() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("crearFiltro.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(FiltrocolaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param obj
     */
    public void setFiltrocola(TblFiltrocola obj) {
        if (obj != null) {
            this.setSelectedFiltrocola(obj);
        }
    }

    /**
     * 
     * @param id
     * @return 
     */
    public TblFiltrocola getFiltrocola(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("no id provided");
        }
        for (TblFiltrocola obj : filtrocolaList) {
            if (id.equals(obj.getIdFiltrocola())) {
                return obj;
            }
        }
        return null;
    }

}
