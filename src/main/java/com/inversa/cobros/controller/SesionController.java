/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.controller;


import com.inversa.cobros.model.TblUsuario;
import java.io.IOException;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 *
 * @author Z420WK
 */
@Named
@SessionScoped
public class SesionController implements Serializable {

    private TblUsuario usuario;

    public void init() {
        this.usuario = new TblUsuario();
        this.buscarUsaurio();
    }

    /**
     *
     * @return
     */
    public void buscarUsaurio() {
        FacesContext context = FacesContext.getCurrentInstance();
        TblUsuario obj = (TblUsuario) context.getExternalContext().getSessionMap().get("usuario");
        if (obj != null) {
            usuario = obj;
        }
    }

    /**
     *
     * @return
     */
    public String usuarioSesion() {
        String nombre = "";
        FacesContext context = FacesContext.getCurrentInstance();
        TblUsuario obj = (TblUsuario) context.getExternalContext().getSessionMap().get("usuario");
        if (obj != null) {
            nombre = obj.getTblPersona().getNombre()+" - "+obj.getCodigoGestor();
        }

        return nombre;
    }

    /**
     *
     */
    public void cerrarSesion() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
    }

    /**
     * 
     */
    public void verificarSesion() {
        try {

            FacesContext context = FacesContext.getCurrentInstance();
            TblUsuario obj = (TblUsuario) context.getExternalContext().getSessionMap().get("usuario");

            if (obj == null) {
                context.getExternalContext().redirect("../login.xhtml");

            }

        } catch (IOException e) {
            e.getStackTrace();
        }
    }

    
    public TblUsuario getUsuario() {
        return usuario;
    }

    public void setUsuario(TblUsuario usuario) {
        this.usuario = usuario;
    }

}
