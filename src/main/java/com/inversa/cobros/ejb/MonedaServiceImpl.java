/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.ejb;

import com.inversa.cobros.dao.MonedaDao;
import com.inversa.cobros.model.Moneda;
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
public class MonedaServiceImpl implements MonedaService, MonedaServiceRemote {

    @Resource
    private SessionContext contexto;

    @Inject
    private MonedaDao dao;

    @Override
    public List<Moneda> findAll() {
        return dao.findAll();
    }

    @Override
    public Moneda findByIdMoneda(Moneda obj) {
        return dao.findByIdMoneda(obj);
    }

    @Override
    public Moneda findByCodigo(Moneda obj) {
        return dao.findByCodigo(obj);
    }

}
