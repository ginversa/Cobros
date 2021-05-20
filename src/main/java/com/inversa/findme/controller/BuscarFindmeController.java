/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.findme.controller;

import com.inversa.cobros.constante.comun.ConstanteComun;
import com.inversa.cobros.controller.LlamarController;
import com.inversa.cobros.model.TblLlamada;
import com.inversa.cobros.model.TblUsuario;
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
import java.util.Calendar;
import java.util.List;
import javax.annotation.PostConstruct;
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
public class BuscarFindmeController implements Serializable {

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

    private TblUsuario usuario;
    private Calendar fechaHoy;
    
    private String identificacion;
    private String nombre;

    @PostConstruct
    public void init() {
        this.usuario = (TblUsuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(ConstanteComun.USUARIO);
        this.fechaHoy = Calendar.getInstance();
    }

    /**
     * 
     */
    public void buscar() {
        if (this.identificacion != null && !this.identificacion.trim().equals("")) {
            this.historialTelefonoList = this.ejbLocal.historial_telefonos(this.identificacion);
            this.historialCorreoList = this.ejbLocal.historial_correos(this.identificacion);
            this.historialDireccionList = this.ejbLocal.historial_direcciones(this.identificacion);
            this.arbolFamiliarList = this.ejbLocal.arbol_familiar(this.identificacion);
            this.historialLaboralList = this.ejbLocal.analisis_laboral(this.identificacion);
            this.sociedadAnonimaList = this.ejbLocal.sociedades_anonimas(this.identificacion);
            this.propiedadRegistradaList = this.ejbLocal.propiedades_registradas(this.identificacion);
            this.vehiculoList = this.ejbLocal.consulta_vehiculos(this.identificacion);
            this.historialJudicialList = this.ejbLocal.historial_judicial(this.identificacion);
            
            if(this.arbolFamiliarList != null && !this.arbolFamiliarList.isEmpty() && this.arbolFamiliarList.size()>0){
                this.setNombre(this.arbolFamiliarList.get(0).getNombre());
            }
        }

        PrimeFaces.current().ajax().update("formFindme:idHistorialTelefonos", "formFindme:idHistorialDireccion", "formFindme:idArbolFamiliar", "formFindme:idAnalisisLaboral", "formFindme:idSociedadAnonima", "formFindme:idPropiedadRegistrada", "formFindme:idVehiculo", "formFindme:idHistorialJudicial");
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

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
