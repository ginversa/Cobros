/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.controller;

import com.inversa.cobros.ejb.CarteraService;
import com.inversa.cobros.ejb.GestionService;
import com.inversa.cobros.ejb.PromesaService;
import com.inversa.cobros.model.TblCartera;
import com.inversa.cobros.model.TblGestion;
import com.inversa.cobros.model.TblLlamada;
import com.inversa.cobros.model.TblPromesa;
import com.inversa.cobros.model.TblUsuario;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
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
    private GestionService ejbGestionLocal;

    @Inject
    private PromesaService ejbPromesaLocal;

    @Inject
    private ListarPromesaController listarPromesaController;

    private TblCartera cartera;
    private String observaciones;

    private List<TblCartera> carteraList;
    private TblUsuario usuario;
    private Calendar fechaHoy;

    private String codigoGestor;
    private String codigoCartera;

    @PostConstruct
    public void init() {
        this.fechaHoy = Calendar.getInstance();
        this.usuario = (TblUsuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        this.codigoGestor = this.usuario.getCodigoGestor();
        this.codigoCartera = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("codigo_cartera");        
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
                String codigoCartera = this.carteraList.get(index).getCodigoCartera();
                String identificacion = this.carteraList.get(index).getIdentificacion();
                String operacion = this.carteraList.get(index).getNumeroCuenta();

                TblGestion gestion = new TblGestion();
                gestion.setCodigoCartera(codigoCartera);
                gestion.setIdentificacion(identificacion);

                gestion = this.ejbGestionLocal.findByCodigoCarteraANDIdentificacion(gestion);
                if (gestion != null) {

                    Date fechaUltimaGestion = gestion.getFechaGestion();
                    String razonMora = "";

                    TblPromesa ultimaPromesa = this.ejbPromesaLocal.findPromesaUltimoPago(gestion.getIdGestion());

                    List<TblLlamada> llamadaList = gestion.getTblLlamadaList();
                    if (llamadaList != null && !llamadaList.isEmpty()) {
                        Date fechaingresoMax = null;
                        for (int indexLlamada = 0; indexLlamada < llamadaList.size(); indexLlamada++) {
                            TblLlamada llamada = llamadaList.get(indexLlamada);
                            Date fechaingreso = llamada.getFechaingreso();
                            if (fechaingresoMax == null) {
                                fechaingresoMax = fechaingreso;
                                if (llamada.getIdrazonmora() != null) {
                                    razonMora = llamada.getIdrazonmora().getDescripcion();
                                }

                            } else if (fechaingresoMax.before(fechaingreso)) {// fechaingresoMax is before fechaingreso. fecha es mayor
                                fechaingresoMax = fechaingreso;
                                if (llamada.getIdrazonmora() != null) {
                                    razonMora = llamada.getIdrazonmora().getDescripcion();
                                }
                            }
                        }//for
                    }//if

                    this.carteraList.get(index).setFechaUltimaGestion(fechaUltimaGestion);
                    this.carteraList.get(index).setUltimaPromesa(ultimaPromesa);
                    this.carteraList.get(index).setRazonMora(razonMora);
                }// if Gestion
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
            this.fechaHoy.set(Calendar.YEAR, 2020);
            this.fechaHoy.set(Calendar.MONTH, 11);
            this.fechaHoy.set(Calendar.DAY_OF_MONTH, 30);
            this.cartera.setFechaIngreso(this.fechaHoy.getTime());
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

}
