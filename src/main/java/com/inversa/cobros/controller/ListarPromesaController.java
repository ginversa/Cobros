/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.controller;

import com.inversa.cobros.ejb.GestionService;
import com.inversa.cobros.model.TblGestion;
import com.inversa.cobros.model.TblLlamada;
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
public class ListarPromesaController implements Serializable{
    
     @Inject
    private GestionService ejbLocal;
    
    private List<TblGestion> gestionList;
        
    private List<TblPromesa> promesasList;
    
    @PostConstruct
    public void init(){
        
        this.promesasList = new ArrayList<TblPromesa>();
        
         // Usuario de session...
        TblUsuario usuario = (TblUsuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        
        TblGestion gestion = new TblGestion();
        gestion.setCodigoGestor(usuario.getCodigoGestor());
        this.gestionList = this.ejbLocal.findByCodigoGestor(gestion);
        
        for(int index=0; index< this.gestionList.size();index++){
            List<TblLlamada> llamadas = this.gestionList.get(index).getTblLlamadaList();
            if(llamadas != null && !llamadas.isEmpty() && llamadas.size()>0){
                for(int i =0;i<llamadas.size();i++){
                    TblLlamada llamada = llamadas.get(i);
                    promesasList.addAll(llamada.getTblPromesaList());
                }                
            }            
        }
    }

    public List<TblPromesa> getPromesasList() {
        return promesasList;
    }

    public void setPromesasList(List<TblPromesa> promesasList) {
        this.promesasList = promesasList;
    }

}
