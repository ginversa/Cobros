/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.controller;

import com.inversa.cobros.ejb.ResultadoterceroService;
import com.inversa.cobros.model.TblResultadotercero;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Z420WK
 */
@Named
@ViewScoped
public class ResultadoterceroController implements Serializable {

    @Inject
    private ResultadoterceroService ejbLocal;

    private List<TblResultadotercero> resultadoterceroList;

    @PostConstruct
    public void init() {
        this.resultadoterceroList = this.ejbLocal.findAll();
    }

    public List<TblResultadotercero> getResultadoterceroList() {
        return resultadoterceroList;
    }

    public void setResultadoterceroList(List<TblResultadotercero> resultadoterceroList) {
        this.resultadoterceroList = resultadoterceroList;
    }

    /**
     *
     * @param id
     * @return
     */
    public TblResultadotercero getResultadotercero(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("no id provided");
        }
        for (TblResultadotercero obj : this.resultadoterceroList) {
            if (id.equals(obj.getIdResultadotercero())) {
                return obj;
            }
        }
        return null;
    }

}
