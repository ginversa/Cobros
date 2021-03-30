/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.findme.controller;

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
public class FindmeController implements Serializable {

    @Inject
    private FindmeService ejbLocal;

    @Inject
    private LlamarController llamarController;

    private List<HistorialLaboral> historialLaboralList;

    private List<HistorialCorreo> historialCorreoList;

    private List<HistorialTelefono> historialTelefonoList;
    private HistorialTelefono selectedHistorialTelefono;

    private List<Vehiculo> vehiculoList;

    private List<PropiedadRegistrada> propiedadRegistradaList;

    private List<HistorialDireccion> historialDireccionList;

    private List<HistorialJudicial> historialJudicialList;

    private List<SociedadAnonima> sociedadAnonimaList;

    private List<ArbolFamiliar> arbolFamiliarList;

    private TblLlamada selectedLlamada;

    private TblUsuario usuario;
    private Calendar fechaHoy;

    @PostConstruct
    public void init() {
        this.usuario = (TblUsuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        this.fechaHoy = Calendar.getInstance();
        this.selectedLlamada = new TblLlamada();
        this.selectedHistorialTelefono = new HistorialTelefono();

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

    /*
    public void cargarFindme() {
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

        PrimeFaces.current().ajax().update("formGestion:idTabView:idHistorialTelefonos", "formGestion:idTabView:idHistorialDireccion", "formGestion:idTabView:idArbolFamiliar", "formGestion:idTabView:idAnalisisLaboral", "formGestion:idTabView:idSociedadAnonima", "formGestion:idTabView:idPropiedadRegistrada", "formGestion:idTabView:idVehiculo", "formGestion:idTabView:idHistorialJudicial");
    }
     */
    public void cargarFindme(String identificacion) {
        if (identificacion != null && !identificacion.trim().equals("")) {
            this.historialTelefonoList = this.ejbLocal.historial_telefonos(identificacion);
            this.historialCorreoList = this.ejbLocal.historial_correos(identificacion);
            this.historialDireccionList = this.ejbLocal.historial_direcciones(identificacion);
            this.arbolFamiliarList = this.ejbLocal.arbol_familiar(identificacion);
            this.historialLaboralList = this.ejbLocal.analisis_laboral(identificacion);
            this.sociedadAnonimaList = this.ejbLocal.sociedades_anonimas(identificacion);
            this.propiedadRegistradaList = this.ejbLocal.propiedades_registradas(identificacion);
            this.vehiculoList = this.ejbLocal.consulta_vehiculos(identificacion);
            this.historialJudicialList = this.ejbLocal.historial_judicial(identificacion);
        }

        PrimeFaces.current().ajax().update("formGestion:idTabView:idHistorialTelefonos", "formGestion:idTabView:idHistorialDireccion", "formGestion:idTabView:idArbolFamiliar", "formGestion:idTabView:idAnalisisLaboral", "formGestion:idTabView:idSociedadAnonima", "formGestion:idTabView:idPropiedadRegistrada", "formGestion:idTabView:idVehiculo", "formGestion:idTabView:idHistorialJudicial");
    }

    /*
    ***************************************************************************
    ***************************************************************************
    ***************************************************************************
     */
    /**
     *
     * @param historialTelefono
     */
    public void generarLlamada(HistorialTelefono historialTelefono) {
        if (historialTelefono != null) {
            String telefono = historialTelefono.getTelefono();
            if (telefono != null && !telefono.trim().equals("")) {
                TblLlamada llamada = new TblLlamada();
                llamada.setCallToNumber(telefono);
                this.generarLlamada(llamada);
            }
        }
    }

    public void generarLlamada(TblLlamada llamada) {

        if (llamada != null && llamada.getCallToNumber() != null && !llamada.getCallToNumber().trim().equals("")) {

            this.selectedLlamada = new TblLlamada();
            this.selectedLlamada.setCallToNumber(llamada.getCallToNumber());

            String telefono = this.selectedLlamada.getCallToNumber();

            String jsonExtract = llamarController.hacerLlamada(telefono);
            if (jsonExtract != null) {
                System.out.println("Generar una llamada: " + jsonExtract);

                String errorCentral = null;
                String[] erroresCentralTel = jsonExtract.split("#");
                if (erroresCentralTel != null && erroresCentralTel.length > 1) {
                    errorCentral = erroresCentralTel[1];
                    System.out.println("Error Central: " + errorCentral);
                } else {
                    System.out.println("Error Central: " + erroresCentralTel[0]);
                }

                if (errorCentral != null && !errorCentral.trim().equals("")) {

                    switch (errorCentral.trim()) {
                        case "0":
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error!", "0 - No está configurado el servicio!"));
                            break;
                        case "1":
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error!", "1 - El IP no está autorizado!"));
                            break;
                        case "001":
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error!", "001 - No indica la extensión!"));
                            break;
                        case "002":
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error!", "002 - El número a marcar no es correcto!"));
                            break;
                        case "004":
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error!", "004 - La extensión no es numérica!"));
                            break;
                        case "008":
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error!", "008 - La extensión no existe ni está como activa!"));
                            break;
                        case "016":
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error!", "016 - Falla en generar la llamada local inicial!"));
                            break;
                        case "032":
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error!", "032 - No logra recuperar el ID de la llamada!"));
                            break;
                        case "064":
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error!", "064 - si se envía un ID de llamada que no sea numérico para el caso de escucharla!"));
                            break;
                        case "128":
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error!", "128 - si no se especifica una extensión activa ni existe el contexto: context_qrm!"));
                            break;
                        default:
                            break;
                    }

                } else {
                    this.selectedLlamada.setCallLogId(jsonExtract);
                }
            }

        }// if
    }

    public TblLlamada getSelectedLlamada() {
        return selectedLlamada;
    }

    public void setSelectedLlamada(TblLlamada selectedLlamada) {
        this.selectedLlamada = selectedLlamada;
    }

    public HistorialTelefono getSelectedHistorialTelefono() {
        return selectedHistorialTelefono;
    }

    public void setSelectedHistorialTelefono(HistorialTelefono selectedHistorialTelefono) {
        this.selectedHistorialTelefono = selectedHistorialTelefono;
    }

}
