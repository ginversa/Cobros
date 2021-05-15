/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.controller;

import com.inversa.cobros.constante.comun.ConstanteComun;
import com.inversa.cobros.ejb.LlamadaService;
import com.inversa.cobros.model.TblLlamada;
import com.inversa.cobros.model.TblUsuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Z420WK
 */
@Named
@SessionScoped
public class ListarLlamadaController implements Serializable {

    @Inject
    private LlamadaService ejbLocal;
    private List<TblLlamada> llamadaList;
    
    private TblUsuario usuario;

    @PostConstruct
    public void init() {

        this.llamadaList = new ArrayList<>();

        // Usuario de session...
        this.usuario = (TblUsuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(ConstanteComun.USUARIO);
        
        String codigoGestor = usuario.getCodigoGestor();
        String codigoCartera = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(ConstanteComun.COD_CARTERA);        
        this.llamadaList = this.ejbLocal.findByCarteraANDSupervisor(codigoCartera,codigoGestor);

    }    

    public List<TblLlamada> getLlamadaList() {
        return llamadaList;
    }

    public void setLlamadaList(List<TblLlamada> llamadaList) {
        this.llamadaList = llamadaList;
    }

    /**
     * call_length; es el tiempo total de la llamada. Ese tiemoo es desde que
     * entra al sistema y hace ring
     *
     * @param date_ini
     * @param date_end
     * @return call_length
     */
    public Date restarFechar(Date date_ini, Date date_end) {

        Long call_length = Long.valueOf(0);

        boolean isTrue_date_ini = date_ini != null ? true : false;
        boolean isTrue_date_end = date_end != null ? true : false;

        if (isTrue_date_ini && isTrue_date_end) {
            call_length = date_end.getTime() - date_ini.getTime();
        }

        return new Date(call_length);
    }

}
