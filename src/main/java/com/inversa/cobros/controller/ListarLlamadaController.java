/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.controller;

import com.inversa.cobros.ejb.GestionService;
import com.inversa.cobros.model.TblGestion;
import com.inversa.cobros.model.TblLlamada;
import com.inversa.cobros.model.TblUsuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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
public class ListarLlamadaController implements Serializable {

    @Inject
    private GestionService ejbLocal;

    private List<TblGestion> gestionList;

    private List<TblLlamada> llamadaList;

    @PostConstruct
    public void init() {

        this.llamadaList = new ArrayList<TblLlamada>();

        // Usuario de session...
        TblUsuario usuario = (TblUsuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        String codigoCartera = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("codigo_cartera");

        TblGestion gestion = new TblGestion();
        gestion.setCodigoGestor(usuario.getCodigoGestor());
        gestion.setCodigoCartera(codigoCartera);
        this.gestionList = this.ejbLocal.findByCodigoGestorANDCodigoCartera(gestion);

        for (int index = 0; index < this.gestionList.size(); index++) {
            List<TblLlamada> llamadas = this.gestionList.get(index).getTblLlamadaList();
            if (llamadas != null && !llamadas.isEmpty() && llamadas.size() > 0) {
                llamadaList.addAll(llamadas);
            }
        }
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
