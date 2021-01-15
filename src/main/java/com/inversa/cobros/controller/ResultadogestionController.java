/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.controller;

import com.inversa.cobros.ejb.ResultadogestionService;
import com.inversa.cobros.model.TblResultadogestion;
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
public class ResultadogestionController implements Serializable {

    @Inject
    private ResultadogestionService ejbLocal;

    private List<TblResultadogestion> resultadogestionList;

    @PostConstruct
    public void init() {
        this.resultadogestionList = this.ejbLocal.findAll();
    }

    public List<TblResultadogestion> getResultadogestionList() {
        return resultadogestionList;
    }

    public void setResultadogestionList(List<TblResultadogestion> resultadogestionList) {
        this.resultadogestionList = resultadogestionList;
    }

    /**
     * 
     * @param id
     * @return 
     */
    public TblResultadogestion getResultadogestion(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("no id provided");
        }
        for (TblResultadogestion obj : this.resultadogestionList) {
            if (id.equals(obj.getIdResultadogestion())) {
                return obj;
            }
        }
        return null;
    }

}
