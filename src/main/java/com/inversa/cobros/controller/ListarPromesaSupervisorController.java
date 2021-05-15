/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.controller;

import com.inversa.cobros.constante.comun.ConstanteComun;
import com.inversa.cobros.ejb.PromesaService;
import com.inversa.cobros.model.TblPromesa;
import com.inversa.cobros.model.TblUsuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Z420WK
 */
@Named
@ViewScoped
public class ListarPromesaSupervisorController implements Serializable {

    @Inject
    private PromesaService ejbLocal;
    
    private List<TblPromesa> promesaList;
    
    private TblUsuario usuario;

    @PostConstruct
    public void init() {
        
        this.promesaList = new ArrayList<>();        
        this.usuario = (TblUsuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(ConstanteComun.USUARIO);
        
        String codigoGestor = usuario.getCodigoGestor();
        String codigoCartera = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(ConstanteComun.COD_CARTERA);        
        
        this.promesaList = this.ejbLocal.findByCarteraANDSupervisor(codigoCartera,codigoGestor);
    }

    public List<TblPromesa> getPromesaList() {
        return promesaList;
    }

    public void setPromesaList(List<TblPromesa> promesaList) {
        this.promesaList = promesaList;
    }

}
