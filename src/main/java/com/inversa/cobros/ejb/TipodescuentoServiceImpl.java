/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.ejb;

import com.inversa.cobros.dao.TipodescuentoDao;
import com.inversa.cobros.model.Tipodescuento;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Z420WK
 */
@Stateless
public class TipodescuentoServiceImpl implements TipodescuentoService, TipodescuentoServiceRemote {

    @Resource
    private SessionContext contexto;

    @Inject
    private TipodescuentoDao dao;

    @Override
    public List<Tipodescuento> findAll() {
        return dao.findAll();
    }

    @Override
    public Tipodescuento findByIdtipodescuento(Tipodescuento obj) {
        return dao.findByIdtipodescuento(obj);
    }

    @Override
    public Tipodescuento findByCodigo(Tipodescuento obj) {
        return dao.findByCodigo(obj);
    }

}
