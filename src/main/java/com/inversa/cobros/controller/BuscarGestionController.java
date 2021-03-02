/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.controller;

import com.inversa.cobros.ejb.GestionService;
import com.inversa.cobros.ejb.PromesaService;
import com.inversa.cobros.model.Cartera;
import com.inversa.cobros.model.TblClienteUsuario;
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
public class BuscarGestionController implements Serializable {

    @Inject
    private GestionService ejbLocal;

    @Inject
    private PromesaService ejbPromesaServiceLocal;

    private List<TblGestion> gestionList;
    private TblGestion gestion;
    private List<TblLlamada> llamadaList;
    //private String codigoCartera;
    //private List<Cartera> codigoCarteraList;
    
    private TblUsuario usuario;

    @PostConstruct
    public void init() {
        //this.codigoCarteraList = ejbLocal.findCarteraByDistinc();
        this.llamadaList = new ArrayList<TblLlamada>();
        this.gestion = new TblGestion();        
        this.usuario = (TblUsuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
    }    

    /**
     * 
     */
    public void buscarGestion() {
        
        if(this.llamadaList != null){
            this.llamadaList.clear();
        }
        
        String codigoCartera = null;
        List<TblClienteUsuario> clienteUsuarioList = this.usuario.getTblClienteUsuarioList();
        if(clienteUsuarioList != null && !clienteUsuarioList.isEmpty() && clienteUsuarioList.size()>0){
            codigoCartera = clienteUsuarioList.get(0).getCodigo_cartera();
        }
        
        this.gestion.setCodigoCartera(codigoCartera);

        this.gestionList = this.ejbLocal.findByIdentificacionANDCodigoCartera(this.gestion);//findByIdentificacion(this.gestion);

        if (this.gestionList != null && !this.gestionList.isEmpty() && this.gestionList.size() > 0) {
            this.gestion = this.gestionList.get(0);
            for (int index = 0; index < this.gestionList.size(); index++) {
                List<TblLlamada> llamadas = this.gestionList.get(index).getTblLlamadaList();
                if (llamadas != null && !llamadas.isEmpty() && llamadas.size() > 0) {
                    for (int i = 0; i < llamadas.size(); i++) {
                        TblPromesa promesaUltimoPago = this.ejbPromesaServiceLocal.findPromesaUltimoPago(this.gestionList.get(index).getIdGestion(), llamadas.get(i).getIdLlamada());
                        llamadas.get(i).setUltimaPromesa(promesaUltimoPago);
                        this.llamadaList.add(llamadas.get(i));
                    }
                }
            }
        }
    }
    

    public List<TblGestion> getGestionList() {
        return gestionList;
    }

    public void setGestionList(List<TblGestion> gestionList) {
        this.gestionList = gestionList;
    }

    public TblGestion getGestion() {
        return gestion;
    }

    public void setGestion(TblGestion gestion) {
        this.gestion = gestion;
    }

    public List<TblLlamada> getLlamadaList() {
        return llamadaList;
    }

    public void setLlamadaList(List<TblLlamada> llamadaList) {
        this.llamadaList = llamadaList;
    }

    /*
    public String getCodigoCartera() {
        return codigoCartera;
    }

    public void setCodigoCartera(String codigoCartera) {
        this.codigoCartera = codigoCartera;
    }

    public List<Cartera> getCodigoCarteraList() {
        return codigoCarteraList;
    }

    public void setCodigoCarteraList(List<Cartera> codigoCarteraList) {
        this.codigoCarteraList = codigoCarteraList;
    }
    */

}
