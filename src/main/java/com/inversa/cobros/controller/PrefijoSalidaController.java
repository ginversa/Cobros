/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.controller;

import com.inversa.cobros.ejb.PrefijoSalidaService;
import com.inversa.cobros.model.TblPrefijoSalida;
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
public class PrefijoSalidaController implements Serializable {

    @Inject
    private PrefijoSalidaService ejbLocal;

    private List<TblPrefijoSalida> prefijoSalidaList;

    @PostConstruct
    public void init() {
        this.prefijoSalidaList = this.ejbLocal.findAll();
    }

    public List<TblPrefijoSalida> getPrefijoSalidaList() {
        return prefijoSalidaList;
    }

    public void setPrefijoSalidaList(List<TblPrefijoSalida> prefijoSalidaList) {
        this.prefijoSalidaList = prefijoSalidaList;
    }

    
    /**
     *
     * @param id
     * @return
     */
    public TblPrefijoSalida getPrefijoSalida(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("no id provided");
        }
        for (TblPrefijoSalida obj : this.prefijoSalidaList) {
            if (id.equals(obj.getId())) {
                return obj;
            }
        }
        return null;
    }

}
