/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.controller;

import com.inversa.cobros.ejb.ClienteCarteraService;
import com.inversa.cobros.model.TblClienteCartera;
import com.inversa.cobros.model.TblUsuario;
import java.io.Serializable;
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
public class ClienteCarteraController implements Serializable {

    @Inject
    private ClienteCarteraService ejbLocal;

    private List<TblClienteCartera> clienteCarteraList;

    private TblClienteCartera clienteCartera;

    private String codigoCarteraSelected;

    @PostConstruct
    public void init() {
        TblUsuario usuario = (TblUsuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        String codigoGestor = usuario.getCodigoGestor();

        this.clienteCarteraList = this.ejbLocal.findByCodigoGestor(codigoGestor);

        if (this.clienteCarteraList != null && !this.clienteCarteraList.isEmpty() && this.clienteCarteraList.size() > 0) {
            this.codigoCarteraSelected = this.clienteCarteraList.get(0).getCodigoCartera();
            System.out.println("Cartera cambio: " + this.codigoCarteraSelected);
            if (this.codigoCarteraSelected != null && !this.codigoCarteraSelected.trim().equals("")) {
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("codigo_cartera", this.codigoCarteraSelected);
            }
        }

    }

    public List<TblClienteCartera> getClienteCarteraList() {
        return clienteCarteraList;
    }

    public void setClienteCarteraList(List<TblClienteCartera> clienteCarteraList) {
        this.clienteCarteraList = clienteCarteraList;
    }

    public TblClienteCartera getClienteCartera() {
        return clienteCartera;
    }

    public void setClienteCartera(TblClienteCartera clienteCartera) {
        this.clienteCartera = clienteCartera;
    }

    public String getCodigoCarteraSelected() {
        return codigoCarteraSelected;
    }

    public void setCodigoCarteraSelected(String codigoCarteraSelected) {
        this.codigoCarteraSelected = codigoCarteraSelected;
    }

    /**
     *
     */
    public void onCarteraChange() {
        System.out.println("Cartera cambio: " + this.codigoCarteraSelected);
        if (this.codigoCarteraSelected != null && !this.codigoCarteraSelected.trim().equals("")) {
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("codigo_cartera", this.codigoCarteraSelected);
        }
    }

}
