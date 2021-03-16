/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.controller;

import com.inversa.cobros.ejb.UsuarioService;
import com.inversa.cobros.model.TblUsuario;
import java.io.Serializable;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author Z420WK
 */
@Named
@ViewScoped
public class LoginController implements Serializable {

    @EJB
    private UsuarioService ejbLocal;

    private TblUsuario usuario;

    @PostConstruct
    public void init() {
        this.usuario = new TblUsuario();
    }

    public TblUsuario getUsuario() {
        return usuario;
    }

    public void setUsuario(TblUsuario usuario) {
        this.usuario = usuario;
    }    

    /**
     *
     * @return
     */
    public String iniciarSesion() {
        String redireccion = null;

        try {
            TblUsuario obj = this.ejbLocal.findByUsuarioAndClave(usuario);
            if (obj != null) {
                FacesContext facesContext = FacesContext.getCurrentInstance();
                facesContext.getExternalContext().getSessionMap().put("usuario", obj);
                redireccion = "operario/cartera";
                
                System.out.println("====> Usuario             : " + obj.toString());                
                
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Credenciales incorrectas!"));
            }

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Aviso", "Error: " + e.getMessage()));
            e.getStackTrace();
        }

        return redireccion;
    }

}
