/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.ejb;

import com.inversa.cobros.dao.ResultadoterceroDao;
import com.inversa.cobros.model.TblResultadotercero;
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
public class ResultadoterceroServiceImpl implements ResultadoterceroService, ResultadoterceroServiceRemote {

    @Resource
    private SessionContext contexto;

    @Inject
    private ResultadoterceroDao dao;

    @Override
    public List<TblResultadotercero> findAll() {
        return dao.findAll();
    }

    @Override
    public TblResultadotercero findById(TblResultadotercero obj) {
        return dao.findById(obj);
    }

    @Override
    public TblResultadotercero findByDescripcion(TblResultadotercero obj) {
        return dao.findByDescripcion(obj);
    }

    @Override
    public TblResultadotercero findByCodigo(TblResultadotercero obj) {
        return dao.findByCodigo(obj);
    }

    @Override
    public List<TblResultadotercero> findByUsuarioingreso(TblResultadotercero obj) {
        return dao.findByUsuarioingreso(obj);
    }

    @Override
    public List<TblResultadotercero> findByFechaingreso(TblResultadotercero obj) {
        return dao.findByFechaingreso(obj);
    }

    @Override
    public List<TblResultadotercero> findByUsuariomodifico(TblResultadotercero obj) {
        return dao.findByUsuariomodifico(obj);
    }

    @Override
    public List<TblResultadotercero> findByFechamodifico(TblResultadotercero obj) {
        return dao.findByFechamodifico(obj);
    }

    @Override
    public void insert(TblResultadotercero obj) {
        try {
            dao.insert(obj);

        } catch (Throwable t) {
            contexto.setRollbackOnly();// hace rollback...
            t.printStackTrace(System.out);// imprime en consola el error
        }
    }

    @Override
    public void update(TblResultadotercero obj) {
        try {
            dao.update(obj);

        } catch (Throwable t) {
            contexto.setRollbackOnly();// hace rollback...
            t.printStackTrace(System.out);// imprime en consola el error
        }
    }

    @Override
    public void delete(TblResultadotercero obj) {
        try {
            dao.delete(obj);

        } catch (Throwable t) {
            contexto.setRollbackOnly();// hace rollback...
            t.printStackTrace(System.out);// imprime en consola el error
        }
    }

}
