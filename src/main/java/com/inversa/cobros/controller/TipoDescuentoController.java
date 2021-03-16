/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.controller;

import com.inversa.cobros.model.TipoDescuento;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author Z420WK
 */
@Named
@ViewScoped
public class TipoDescuentoController implements Serializable {

    private List<TipoDescuento> tipoDescuentoList;

    private TipoDescuento tipoDescuento;

    @PostConstruct
    public void init() {
        this.tipoDescuentoList = new ArrayList<>();        
        TipoDescuento tdFij = new TipoDescuento("FIJ", "Monto Fijo");
        TipoDescuento tdPor = new TipoDescuento("POR", "Porcentaje");
        this.tipoDescuentoList.add(tdFij);
        this.tipoDescuentoList.add(tdPor);
    }

    public List<TipoDescuento> getTipoDescuentoList() {
        return tipoDescuentoList;
    }

    public void setTipoDescuentoList(List<TipoDescuento> tipoDescuentoList) {
        this.tipoDescuentoList = tipoDescuentoList;
    }

    public TipoDescuento getTipoDescuento() {
        return tipoDescuento;
    }

    public void setTipoDescuento(TipoDescuento tipoDescuento) {
        this.tipoDescuento = tipoDescuento;
    }

}
