/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.ejb;

import com.inversa.cobros.dao.ArreglopagoDao;
import com.inversa.cobros.model.Arreglopago;
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
public class ArreglopagoServiceImpl implements ArreglopagoService, ArreglopagoServiceRemote {

    @Resource
    private SessionContext contexto;

    @Inject
    private ArreglopagoDao dao;

    @Override
    public List<Arreglopago> findAll() {
        return dao.findAll();
    }

    @Override
    public Arreglopago findByIdarreglopago(Arreglopago obj) {
        return dao.findByIdarreglopago(obj);
    }

    @Override
    public Arreglopago findByCodigo(Arreglopago obj) {
        return dao.findByCodigo(obj);
    }

}
