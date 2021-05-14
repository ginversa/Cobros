/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.controller.cola;

import com.inversa.cobros.constante.comun.ConstanteComun;
import com.inversa.cobros.controller.CarteraController;
import com.inversa.cobros.ejb.ColaService;
import com.inversa.cobros.ejb.FiltrocolaService;
import com.inversa.cobros.model.TblCartera;
import com.inversa.cobros.model.TblCola;
import com.inversa.cobros.model.TblFiltrocola;
import com.inversa.cobros.model.TblUsuario;
import java.io.Serializable;
import java.util.Calendar;
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
public class ListarColaGestorController implements Serializable {

    @Inject
    private FiltrocolaService ejbFiltrocolaLocal;

    @Inject
    private ColaService ejbColaLocal;
    
    @Inject
    private CarteraController carteraController;
    
    @Inject
    private ColaGestionController colaGestionController;

    private List<TblFiltrocola> filtrocolaList;

    private List<TblCola> colaList;

    private TblFiltrocola filtroSelected;
    
    private TblCola colaSelected;
    
    private Calendar fechaHoy;
    private TblUsuario usuario;

    @PostConstruct
    public void init() {
        this.usuario = (TblUsuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        this.fechaHoy = Calendar.getInstance();
        
        String codigoCartera = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(ConstanteComun.COD_CARTERA);
        TblFiltrocola filtro = new TblFiltrocola();
        filtro.setCodigoCartera(codigoCartera);
        this.filtrocolaList = this.ejbFiltrocolaLocal.findByCodigoCartera(filtro);
        if(this.filtrocolaList != null && !this.filtrocolaList.isEmpty() && this.filtrocolaList.size()>0){
            this.filtroSelected = this.filtrocolaList.get(0);
            this.onFiltroChange();
        }
    }

    /**
     *
     */
    public void onFiltroChange() {
        if (this.filtroSelected != null) {
            TblCola obj = new TblCola();
            obj.setIdFiltrocola(this.filtroSelected);
            obj.setCodigoCartera(this.filtroSelected.getCodigoCartera());
            this.colaList = this.ejbColaLocal.findByCodigoGestorAndIdFiltroLimit_ONE(obj);
        }
    }

    public List<TblCola> getColaList() {
        return colaList;
    }

    public void setColaList(List<TblCola> colaList) {
        this.colaList = colaList;
    }

    public TblFiltrocola getFiltroSelected() {
        return filtroSelected;
    }

    public void setFiltroSelected(TblFiltrocola filtroSelected) {
        this.filtroSelected = filtroSelected;
    }

    public List<TblFiltrocola> getFiltrocolaList() {
        return filtrocolaList;
    }

    public void setFiltrocolaList(List<TblFiltrocola> filtrocolaList) {
        this.filtrocolaList = filtrocolaList;
    }

    public TblCola getColaSelected() {
        return colaSelected;
    }

    public void setColaSelected(TblCola colaSelected) {
        this.colaSelected = colaSelected;
    }   
    
    /**
     * 
     * @param cola 
     */
    public void setColaTOGestion(TblCola cola) {
        if (cola != null) {            
            String identificacion = cola.getIdentificacion();
            this.setColaSelected(cola);
            List<TblCartera> operacionList = this.carteraController.searchCarteraByIdentificacion(identificacion);
            if(operacionList != null && !operacionList.isEmpty() && operacionList.size()>0){                
                this.colaSelected.setEstado(ConstanteComun.EN_GESTION);// ENG = En Gestion
                this.colaSelected.setCodigoGestor(this.usuario.getCodigoGestor());
                this.colaSelected.setUsuariomodifico(this.usuario.getUsuario());
                this.colaSelected.setFechamodifico(this.fechaHoy.getTime());
                this.ejbColaLocal.update(this.colaSelected);
                this.setColaSelected(this.colaSelected);
                TblCartera operacion_cartera = operacionList.get(0);
                this.carteraController.setCartera(operacion_cartera);
                this.colaGestionController.setColaSelected(this.colaSelected);
            }            
        }
    }

}
