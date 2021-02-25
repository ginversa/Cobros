/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.ejb;

import com.inversa.cobros.dao.PagosHistorialDao;
import com.inversa.cobros.model.TblPagosHistorial;
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
public class PagosHistorialServiceImpl implements PagosHistorialService, PagosHistorialServiceRemote {

    @Resource
    private SessionContext contexto;

    @Inject
    private PagosHistorialDao dao;

    @Override
    public List<TblPagosHistorial> findAll() {
        return dao.findAll();
    }

    @Override
    public TblPagosHistorial findById(TblPagosHistorial entity) {
        return dao.findById(entity);
    }

    @Override
    public List<TblPagosHistorial> findByCodigoCartera(TblPagosHistorial entity) {
        return dao.findByCodigoCartera(entity);
    }

    @Override
    public List<TblPagosHistorial> findByNumeroCuenta(TblPagosHistorial entity) {
        return dao.findByNumeroCuenta(entity);
    }

    @Override
    public List<TblPagosHistorial> findByFechaPago(TblPagosHistorial entity) {
        return dao.findByFechaPago(entity);
    }

    @Override
    public List<TblPagosHistorial> findByCodigoGestor(TblPagosHistorial entity) {
        return dao.findByCodigoGestor(entity);
    }

    @Override
    public List<TblPagosHistorial> findByTipoPago(TblPagosHistorial entity) {
        return dao.findByTipoPago(entity);
    }

}
