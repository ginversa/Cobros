/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.controller;

import com.inversa.cobros.constante.comun.ConstanteComun;
import com.inversa.cobros.ejb.UsuarioService;
import com.inversa.cobros.model.TblRolusuario;
import com.inversa.cobros.model.TblUsuario;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 *
 * @author Z420WK
 */
@Named
@SessionScoped
public class LoginController implements Serializable {

    @EJB
    private UsuarioService ejbLocal;

    private TblUsuario usuario;

    private TblRolusuario rolUsuario;

    private boolean esSupervisor;

    private boolean esValidador;

    @PostConstruct
    public void init() {
        this.usuario = new TblUsuario();
        esSupervisor = false;
    }

    public TblUsuario getUsuario() {
        return usuario;
    }

    public void setUsuario(TblUsuario usuario) {
        this.usuario = usuario;
    }

    public TblRolusuario getRolUsuario() {
        return rolUsuario;
    }

    public void setRolUsuario(TblRolusuario rolUsuario) {
        this.rolUsuario = rolUsuario;
    }

    public boolean isEsSupervisor() {
        return esSupervisor;
    }

    public void setEsSupervisor(boolean esSupervisor) {
        this.esSupervisor = esSupervisor;
    }

    public boolean isEsValidador() {
        return esValidador;
    }

    public void setEsValidador(boolean esValidador) {
        this.esValidador = esValidador;
    }

    /**
     *
     * @return
     */
    public String iniciarSesion() {
        String redireccion = null;

        try {
            this.usuario = this.ejbLocal.findByUsuarioAndClave(usuario);
            if (this.usuario != null) {

                this.rolUsuario = this.usuario.getIdRolusuario();

                FacesContext facesContext = FacesContext.getCurrentInstance();
                facesContext.getExternalContext().getSessionMap().put(ConstanteComun.USUARIO, this.usuario);
                redireccion = "operario/cartera";

                if (this.rolUsuario != null) {
                    facesContext.getExternalContext().getSessionMap().put(ConstanteComun.ROL_USUARIO, this.rolUsuario);
                    System.out.println("====> Rol Usuario             : " + this.rolUsuario.toString());

                    if (this.rolUsuario.getCodigo().equals(ConstanteComun.COD_SUPERVISOR)) {
                        this.setEsSupervisor(true);
                    } else {
                        this.setEsSupervisor(false);
                    }

                    if (this.rolUsuario.getCodigo().equals(ConstanteComun.COD_VALIDADOR) || this.rolUsuario.getCodigo().equals(ConstanteComun.COD_SUPERVISOR)) {
                        this.setEsValidador(true);
                    } else {
                        this.setEsValidador(false);
                    }

                }

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
