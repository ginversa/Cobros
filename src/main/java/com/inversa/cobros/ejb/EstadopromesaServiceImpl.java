/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.ejb;

import com.inversa.cobros.dao.EstadopromesaDao;
import com.inversa.cobros.model.Estadopromesa;
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
public class EstadopromesaServiceImpl implements EstadopromesaService, EstadopromesaServiceRemote {

    @Resource
    private SessionContext contexto;

    @Inject
    private EstadopromesaDao dao;

    @Override
    public List<Estadopromesa> findAll() {
        return dao.findAll();
    }

    @Override
    public Estadopromesa findByIdestadopromesa(Estadopromesa obj) {
        return dao.findByIdestadopromesa(obj);
    }

    @Override
    public Estadopromesa findByCodigo(Estadopromesa obj) {
        return dao.findByCodigo(obj);
    }

}
