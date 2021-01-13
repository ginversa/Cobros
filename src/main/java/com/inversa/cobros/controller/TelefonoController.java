/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.controller;

import com.inversa.cobros.ejb.ContactoService;
import com.inversa.cobros.ejb.TelefonoService;
import com.inversa.cobros.ejb.TipoTelefonoService;
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
public class TelefonoController implements Serializable {

    @Inject
    private TelefonoService ejbLocal;

    @Inject
    private ContactoService ejbContactoLocal;

    private TblContacto contacto;

    private TblTelefono telefono;
    private List<TblTelefono> telefonoList;

    private TblUsuario usuario;
    private Calendar fechaHoy;

    @PostConstruct
    public void init() {
        // Usuario de session...
        this.usuario = (TblUsuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        this.fechaHoy = Calendar.getInstance();

        this.contacto = new TblContacto();
        this.telefono = new TblTelefono();
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

}
