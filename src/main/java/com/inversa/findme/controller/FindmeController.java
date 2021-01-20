/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.findme.controller;

import com.inversa.cobros.controller.DeudorController;
import com.inversa.cobros.model.TblDeudor;
import com.inversa.findme.ejb.FindmeService;
import com.inversa.findme.model.ArbolFamiliar;
import com.inversa.findme.model.HistorialCorreo;
import com.inversa.findme.model.HistorialDireccion;
import com.inversa.findme.model.HistorialJudicial;
import com.inversa.findme.model.HistorialLaboral;
import com.inversa.findme.model.HistorialTelefono;
import com.inversa.findme.model.PropiedadRegistrada;
import com.inversa.findme.model.SociedadAnonima;
import com.inversa.findme.model.Vehiculo;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
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
@ViewScoped
public class FindmeController implements Serializable {

    @Inject
    private FindmeService ejbLocal;

    private List<HistorialLaboral> historialLaboralList;

    private List<HistorialCorreo> historialCorreoList;

    private List<HistorialTelefono> historialTelefonoList;

    private List<Vehiculo> vehiculoList;

    private List<PropiedadRegistrada> propiedadRegistradaList;

    private List<HistorialDireccion> historialDireccionList;

    private List<HistorialJudicial> historialJudicialList;

    private List<SociedadAnonima> sociedadAnonimaList;

    private List<ArbolFamiliar> arbolFamiliarList;

    @Inject
    private DeudorController deudorController;
    private TblDeudor deudor;
    private String cedula;

    @PostConstruct
    public void init() {

        this.deudor = deudorController.getDeudor();
        if (this.deudor != null) {
            cedula = this.deudor.getDocumento();
            cedula = "108020318";
            cedula.trim();
        }

    }

    public List<HistorialLaboral> getHistorialLaboralList() {
        return historialLaboralList;
    }

    public void setHistorialLaboralList(List<HistorialLaboral> historialLaboralList) {
        this.historialLaboralList = historialLaboralList;
    }

    public List<HistorialCorreo> getHistorialCorreoList() {
        return historialCorreoList;
    }

    public void setHistorialCorreoList(List<HistorialCorreo> historialCorreoList) {
        this.historialCorreoList = historialCorreoList;
    }

    public List<HistorialTelefono> getHistorialTelefonoList() {
        return historialTelefonoList;
    }

    public void setHistorialTelefonoList(List<HistorialTelefono> historialTelefonoList) {
        this.historialTelefonoList = historialTelefonoList;
    }

    public List<Vehiculo> getVehiculoList() {
        return vehiculoList;
    }

    public void setVehiculoList(List<Vehiculo> vehiculoList) {
        this.vehiculoList = vehiculoList;
    }

    public List<PropiedadRegistrada> getPropiedadRegistradaList() {
        return propiedadRegistradaList;
    }

    public void setPropiedadRegistradaList(List<PropiedadRegistrada> propiedadRegistradaList) {
        this.propiedadRegistradaList = propiedadRegistradaList;
    }

    public List<HistorialDireccion> getHistorialDireccionList() {
        return historialDireccionList;
    }

    public void setHistorialDireccionList(List<HistorialDireccion> historialDireccionList) {
        this.historialDireccionList = historialDireccionList;
    }

    public List<HistorialJudicial> getHistorialJudicialList() {
        return historialJudicialList;
    }

    public void setHistorialJudicialList(List<HistorialJudicial> historialJudicialList) {
        this.historialJudicialList = historialJudicialList;
    }

    public List<SociedadAnonima> getSociedadAnonimaList() {
        return sociedadAnonimaList;
    }

    public void setSociedadAnonimaList(List<SociedadAnonima> sociedadAnonimaList) {
        this.sociedadAnonimaList = sociedadAnonimaList;
    }

    public List<ArbolFamiliar> getArbolFamiliarList() {
        return arbolFamiliarList;
    }

    public void setArbolFamiliarList(List<ArbolFamiliar> arbolFamiliarList) {
        this.arbolFamiliarList = arbolFamiliarList;
    }

    private Integer activeTabIndex = 0;

    public Integer getActiveTabIndex() {
        return activeTabIndex;
    }

    public void setActiveTabIndex(Integer activeTabIndex) {
        this.activeTabIndex = activeTabIndex;
    }

    public void onTabViewChange(TabChangeEvent event) {
        TabView tv = (TabView) event.getComponent();
        this.activeTabIndex = tv.getActiveIndex();
        System.out.println("ActiveIndex                             : " + tv.getActiveIndex());
        System.out.println("event.getTab().getId()                  : " + event.getTab().getId());
        System.out.println("tv.getChildren().indexOf(event.getTab()): " + tv.getChildren().indexOf(event.getTab()));
        int index = tv.getChildren().indexOf(event.getTab());
        if (index == 1) {
            cargarFindme();
        }
    }

    /**
     * 
     */
    private void cargarFindme() {
        if (cedula != null && !cedula.trim().equals("")) {
            this.historialTelefonoList = this.ejbLocal.historial_telefonos(cedula);
            this.historialCorreoList = this.ejbLocal.historial_correos(cedula);
            this.historialDireccionList = this.ejbLocal.historial_direcciones(cedula);
            this.arbolFamiliarList = this.ejbLocal.arbol_familiar(cedula);
            this.historialLaboralList = this.ejbLocal.analisis_laboral(cedula);
            this.sociedadAnonimaList = this.ejbLocal.sociedades_anonimas(cedula);
            this.propiedadRegistradaList = this.ejbLocal.propiedades_registradas(cedula);
            this.vehiculoList = this.ejbLocal.consulta_vehiculos(cedula);
            this.historialJudicialList = this.ejbLocal.historial_judicial(cedula);
        }
        
        PrimeFaces.current().ajax().update("formGestion:idTabView:idHistorialTelefonos","formGestion:idTabView:idHistorialDireccion","formGestion:idTabView:idArbolFamiliar","formGestion:idTabView:idAnalisisLaboral","formGestion:idTabView:idSociedadAnonima","formGestion:idTabView:idPropiedadRegistrada","formGestion:idTabView:idVehiculo","formGestion:idTabView:idHistorialJudicial");
    }

}
