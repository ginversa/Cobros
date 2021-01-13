/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.controller;

import com.inversa.cobros.ejb.ContactoService;
import com.inversa.cobros.model.TblContacto;
import com.inversa.cobros.model.TblDeudor;
import com.inversa.cobros.model.TblTelefono;
import com.inversa.cobros.model.TblUsuario;
import com.inversa.cobros.model.Tipotelefono;
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
public class ContactoController implements Serializable {

    @Inject
    private ContactoService ejbLocal;

    @Inject
    private DeudorController deudorController;
    
     @Inject
    private GestionController gestionController;

    private TblDeudor deudor;
    private TblContacto contacto;    

    private TblUsuario usuario;
    private Calendar fechaHoy;

    private TblTelefono telefono;
    private Tipotelefono tipo;

    @PostConstruct
    public void init() {
        // Usuario de session...
        this.usuario = (TblUsuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        this.fechaHoy = Calendar.getInstance();

        this.contacto = new TblContacto();
        this.telefono = new TblTelefono();

        this.deudor = this.deudorController.getDeudor();

        if (this.deudor != null) {
            String nombre = this.deudor.getNombre();
            String cedula = this.deudor.getDocumento();
            this.contacto.setNombre(nombre);
            this.contacto.setCedula(cedula);
            this.contacto = this.ejbLocal.findByCedula(this.contacto);
        }

    }

    public void addTelefono() {
        try {

            TblTelefono objT = new TblTelefono();
            objT.setTelefono(this.telefono.getTelefono());

            objT.setIdContacto(this.contacto);
            objT.setIdTipotelefono(this.tipo);
            objT.setRanking(Integer.valueOf("0"));
            objT.setUsuarioingreso(this.usuario.getUsuario());
            objT.setFechaingreso(this.fechaHoy.getTime());

            this.contacto.getTblTelefonoList().add(objT);
            this.contacto.setFechamodifico(this.fechaHoy.getTime());
            this.contacto.setUsuariomodifico(this.usuario.getUsuario());
            this.ejbLocal.update(this.contacto);
            
            List<TblTelefono> telefonos = this.contacto.getTblTelefonoList();
            
            this.gestionController.setContacto(this.contacto);
            this.gestionController.setTelefonos(telefonos);
            this.gestionController.addTelefonoLlamada();
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso!", "Teléfono Registrado. Correcto!"));
            PrimeFaces.current().executeScript("PF('manageTelefonoDialog').hide()");
            PrimeFaces.current().ajax().update("form:messages", "form:tblTelefono");

            this.telefono.setTelefono("");
            this.tipo = null;

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error!", "Error registrando Teléfono. Error!"));
            System.out.println(e.getMessage());
        }
    }

    public TblContacto getContacto() {
        return contacto;
    }

    public void setContacto(TblContacto contacto) {
        this.contacto = contacto;
    }

    public TblTelefono getTelefono() {
        return telefono;
    }

    public void setTelefono(TblTelefono telefono) {
        this.telefono = telefono;
    }

    public Tipotelefono getTipo() {
        return tipo;
    }

    public void setTipo(Tipotelefono tipo) {
        this.tipo = tipo;
    }

}
