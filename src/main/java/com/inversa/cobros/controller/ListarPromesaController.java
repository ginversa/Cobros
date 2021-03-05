/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.controller;

import com.inversa.cobros.ejb.GestionService;
import com.inversa.cobros.ejb.PromesaService;
import com.inversa.cobros.model.TblClienteUsuario;
import com.inversa.cobros.model.TblGestion;
import com.inversa.cobros.model.TblLlamada;
import com.inversa.cobros.model.TblPromesa;
import com.inversa.cobros.model.TblUsuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
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
public class ListarPromesaController implements Serializable {

    @Inject
    private PromesaService ejbLocal;

    @Inject
    private GestionService ejbGestionLocal;

    private List<TblGestion> gestionList;

    private List<TblPromesa> promesasFechaHoyList;
    private List<TblPromesa> promesasFechaAyerList;

    private TblUsuario usuario;
    private Calendar fechaHoy;
    private Calendar fechaManana;
    private int dias = 1;// dias a sumar...

    @PostConstruct
    public void init() {

        this.promesasFechaHoyList = new ArrayList<TblPromesa>();
        this.promesasFechaAyerList = new ArrayList<TblPromesa>();

        // Usuario de session...
        FacesContext contexto = FacesContext.getCurrentInstance();
        this.usuario = (TblUsuario) contexto.getExternalContext().getSessionMap().get("usuario");
        String codigoCartera = (String) contexto.getExternalContext().getSessionMap().get("codigo_cartera");
        String codigoGestor = this.usuario.getCodigoGestor();        

        this.fechaHoy = Calendar.getInstance();
        this.fechaManana = Calendar.getInstance();
        this.fechaManana.add(Calendar.DAY_OF_YEAR, dias);// sumar 

        TblPromesa promesaHoy = new TblPromesa();
        promesaHoy.setFechaPago(this.fechaHoy.getTime());
        promesaHoy.setUsuarioingreso(this.usuario.getUsuario());
        this.promesasFechaHoyList = this.ejbLocal.findByFechaPagoAndUsuarioIngreso(promesaHoy,codigoGestor,codigoCartera);

        TblPromesa promesaAyer = new TblPromesa();
        promesaAyer.setFechaPago(this.fechaManana.getTime());
        promesaAyer.setUsuarioingreso(this.usuario.getUsuario());
        this.promesasFechaAyerList = this.ejbLocal.findByFechaPagoAndUsuarioIngreso(promesaAyer,codigoGestor,codigoCartera);

    }

    public List<TblPromesa> getPromesasFechaHoyList() {
        return promesasFechaHoyList;
    }

    public void setPromesasFechaHoyList(List<TblPromesa> promesasFechaHoyList) {
        this.promesasFechaHoyList = promesasFechaHoyList;
    }

    public List<TblPromesa> getPromesasFechaAyerList() {
        return promesasFechaAyerList;
    }

    public void setPromesasFechaAyerList(List<TblPromesa> promesasFechaAyerList) {
        this.promesasFechaAyerList = promesasFechaAyerList;
    }

}
