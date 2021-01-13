/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.controller;

import com.inversa.cobros.ejb.PromesaService;
import com.inversa.cobros.model.TblPromesa;
import java.io.Serializable;
import java.util.ArrayList;
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
public class PromesaController implements Serializable{
    
    @Inject
    private PromesaService ejbLocal;
    
    private TblPromesa promesa;
    
    private List<TblPromesa> promesaList;
    
    
    @PostConstruct
    public void init(){
        this.promesa = new TblPromesa();
        this.promesaList = new ArrayList<TblPromesa>();        
    }

    public TblPromesa getPromesa() {
        return promesa;
    }

    public void setPromesa(TblPromesa promesa) {
        this.promesa = promesa;
    }

    public List<TblPromesa> getPromesaList() {
        return promesaList;
    }

    public void setPromesaList(List<TblPromesa> promesaList) {
        this.promesaList = promesaList;
    }
    
    
    
}
