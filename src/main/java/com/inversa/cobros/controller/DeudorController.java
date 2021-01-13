/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.controller;

import com.inversa.cobros.model.TblDeudor;
import com.inversa.cobros.ejb.DeudorService;
import com.inversa.cobros.model.TblUsuario;
import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Z420WK
 */
@Named
@RequestScoped
public class DeudorController implements Serializable {

    @Inject
    private DeudorService ejbLocal;

    private TblDeudor deudor;

    private List<TblDeudor> deudorList;
    
    private Calendar fechaHoy;
    
    private TblUsuario usuario;

    @PostConstruct
    public void init() {
        this.fechaHoy = Calendar.getInstance();
        // Usuario de session...
        this.usuario = (TblUsuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        String codigoGestor = this.usuario.getCodigoGestor();
        if(codigoGestor!= null){
            this.deudor = new TblDeudor();
            this.deudor.setCodigoGestor(codigoGestor);
            
            
            this.fechaHoy.set(Calendar.YEAR, 2020);
            this.fechaHoy.set(Calendar.MONTH, 11);
            this.fechaHoy.set(Calendar.DAY_OF_MONTH, 30);
            this.deudor.setFechaIngreso(this.fechaHoy.getTime());
            
            this.deudorList = ejbLocal.findByGestorIfNotExistsGestion(this.deudor);
            
        }        
    }

    public TblDeudor getDeudor() {
        return deudor;
    }

    public void setDeudor(TblDeudor deudor) {
        this.deudor = deudor;
    }

    public List<TblDeudor> getDeudorList() {
        return deudorList;
    }

    public void setDeudorList(List<TblDeudor> deudorList) {
        this.deudorList = deudorList;
    }
    
}
