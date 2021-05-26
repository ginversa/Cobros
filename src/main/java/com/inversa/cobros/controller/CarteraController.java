/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.controller;

import com.inversa.cobros.constante.comun.ConstanteComun;
import com.inversa.cobros.ejb.CarteraService;
import com.inversa.cobros.ejb.LlamadaService;
import com.inversa.cobros.ejb.PromesaService;
import com.inversa.cobros.model.TblCartera;
import com.inversa.cobros.model.TblLlamada;
import com.inversa.cobros.model.TblPromesa;
import com.inversa.cobros.model.TblUsuario;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.PrimeFaces;
import org.primefaces.component.tabview.TabView;
import org.primefaces.event.TabChangeEvent;

/**
 *
 * @author Z420WK
 */
@Named
@SessionScoped
public class CarteraController implements Serializable {

    @Inject
    private CarteraService ejbLocal;

    @Inject
    private LlamadaService ejbLlamadaLocal;

    @Inject
    private PromesaService ejbPromesaLocal;

    @Inject
    private ListarPromesaController listarPromesaController;

    private TblCartera cartera;
    private String observaciones;

    private List<TblCartera> carteraList;
    private TblUsuario usuario;

    private String codigoGestor;
    private String codigoCartera;

    @PostConstruct
    public void init() {
        this.usuario = (TblUsuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(ConstanteComun.USUARIO);
        this.codigoGestor = this.usuario.getCodigoGestor();
        this.codigoCartera = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(ConstanteComun.COD_CARTERA);
        this.cargarCartera();

    }

    public TblCartera getCartera() {
        return cartera;
    }

    public void setCartera(TblCartera cartera) {
        this.cartera = cartera;
    }

    public List<TblCartera> getCarteraList() {
        return this.carteraList;
    }

    public void setCarteraList(List<TblCartera> carteraList) {
        this.carteraList = carteraList;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    /**
     *
     */
    private void buscarGestion() {

        if (this.carteraList != null && !this.carteraList.isEmpty()) {
            for (int index = 0; index < this.carteraList.size(); index++) {
                String cod_cartera = this.carteraList.get(index).getCodigoCartera();
                String identificacion = this.carteraList.get(index).getIdentificacion();
                String operacion = this.carteraList.get(index).getNumeroCuenta();

                TblLlamada ultimaLlamada = this.ejbLlamadaLocal.findUltimaLlamada(cod_cartera, identificacion, operacion);
                TblPromesa ultimaPromesa = this.ejbPromesaLocal.findUltimaPromesa(cod_cartera, identificacion, operacion);

                if (ultimaLlamada != null) {
                    this.carteraList.get(index).setUltimaLlamada(ultimaLlamada);
                }

                if (ultimaPromesa != null) {
                    this.carteraList.get(index).setUltimaPromesa(ultimaPromesa);
                }
            }
        }
    }//buscarGestion

    /*
    ***************************************************************************
    ***************************************************************************
    ***************************************************************************
     */
    /**
     *
     */
    public void cargarCartera() {
        if (this.codigoGestor != null) {
            this.cartera = new TblCartera();
            this.cartera.setCodigoGestor(this.codigoGestor);
            if (this.codigoCartera != null && !this.codigoCartera.trim().equals("")) {
                this.cartera.setCodigoCartera(this.codigoCartera);
                this.carteraList = this.ejbLocal.findByCodigoGestorANDCodigoCartera(this.cartera);
                
            } else {
                this.carteraList = this.ejbLocal.findByCodigoGestor(this.cartera);
            }

            this.buscarGestion();
        }
    }

    private Integer activeTabIndex = 0;

    public Integer getActiveTabIndex() {
        return activeTabIndex;
    }

    public void setActiveTabIndex(Integer activeTabIndex) {
        this.activeTabIndex = activeTabIndex;
    }

    /**
     *
     * @param event
     */
    public void onTabViewChange(TabChangeEvent event) {
        TabView tv = (TabView) event.getComponent();
        this.activeTabIndex = tv.getActiveIndex();

        int index = tv.getChildren().indexOf(event.getTab());
        switch (index) {
            case 1:
                this.listarPromesaController.cargarPromesas();
                PrimeFaces.current().ajax().update("formCartera:idTabViewCartera:idDTPromesas");
                break;
            case 2:
                this.listarPromesaController.cargarRecordatorio();
                PrimeFaces.current().ajax().update("formCartera:idTabViewCartera:idDTRecordatorio");
                break;
            case 3:
                this.cargarCartera();
                PrimeFaces.current().ajax().update("formCartera:idTabViewCartera:idDTCartera");
                break;
            default:
                break;
        }
    }

    /**
     *
     * @param operacion
     * @return
     */
    public TblCartera searchCarteraByOperacion(String operacion) {

        TblCartera operacion_cartera = new TblCartera();
        operacion_cartera.setNumeroCuenta(operacion);
        operacion_cartera = this.ejbLocal.findByNumeroCuenta(operacion_cartera);
        if (operacion_cartera != null) {
            return operacion_cartera;
        } else {
            return null;
        }
    }

    /**
     * 
     * @param identificacion
     * @return 
     */
    public List<TblCartera> searchCarteraByIdentificacion(String identificacion) {
        TblCartera tblCartera = new TblCartera();
        tblCartera.setIdentificacion(identificacion);
        List<TblCartera> operacionList = this.ejbLocal.findByIdentificacion(tblCartera);
        if (operacionList != null && !operacionList.isEmpty()) {
            return operacionList;
        } else {
            return null;
        }
    }

}
