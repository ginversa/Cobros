/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.controller;

import com.inversa.cobros.ejb.GestionService;
import com.inversa.cobros.model.TblClienteUsuario;
import com.inversa.cobros.model.TblGestion;
import com.inversa.cobros.model.TblUsuario;
import java.io.Serializable;
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
public class ListarGestionController implements Serializable{
    
    @Inject
    private GestionService ejbLocal;
    
    private List<TblGestion> gestionList;
    
    @PostConstruct
    public void init(){
        
        // Usuario de session...
        TblUsuario usuario = (TblUsuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");        
        String codigoCartera = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("codigo_cartera");
        
        TblGestion gestion = new TblGestion();
        gestion.setCodigoGestor(usuario.getCodigoGestor());
        gestion.setCodigoCartera(codigoCartera);
        
        if(codigoCartera != null && !codigoCartera.trim().equals("")){
            this.gestionList = this.ejbLocal.findByCodigoGestorANDCodigoCartera(gestion);
        }else{
            this.gestionList = this.ejbLocal.findByCodigoGestor(gestion);
        }
        
    }

    public List<TblGestion> getGestionList() {
        return gestionList;
    }

    public void setGestionList(List<TblGestion> gestionList) {
        this.gestionList = gestionList;
    }
    
    
}
