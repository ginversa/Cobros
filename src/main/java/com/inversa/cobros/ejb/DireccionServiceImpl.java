/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.ejb;

import com.inversa.cobros.dao.DireccionDao;
import com.inversa.cobros.model.TblDireccion;
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
public class DireccionServiceImpl implements DireccionService, DireccionServiceRemote {

    @Resource
    private SessionContext contexto;

    @Inject
    private DireccionDao dao;

    @Override
    public List<TblDireccion> findAll() {
        return dao.findAll();
    }

    @Override
    public TblDireccion findById(TblDireccion obj) {
        return dao.findById(obj);
    }

    @Override
    public TblDireccion findByDireccion(TblDireccion obj) {
        return dao.findByDireccion(obj);
    }

    @Override
    public List<TblDireccion> findByRanking(TblDireccion obj) {
        return dao.findByRanking(obj);
    }

    @Override
    public List<TblDireccion> findByUsuarioingreso(TblDireccion obj) {
        return dao.findByUsuarioingreso(obj);
    }

    @Override
    public List<TblDireccion> findByFechaingreso(TblDireccion obj) {
        return dao.findByFechaingreso(obj);
    }

    @Override
    public List<TblDireccion> findByUsuariomodifico(TblDireccion obj) {
        return dao.findByUsuariomodifico(obj);
    }

    @Override
    public List<TblDireccion> findByFechamodifico(TblDireccion obj) {
        return dao.findByFechamodifico(obj);
    }
    
     @Override
    public void insert(TblDireccion obj) {
        try {
            dao.insert(obj);

        } catch (Throwable t) {
            contexto.setRollbackOnly();// hace rollback...
            t.printStackTrace(System.out);// imprime en consola el error
        }
    }

    @Override
    public void update(TblDireccion obj) {
        try {
            dao.update(obj);

        } catch (Throwable t) {
            contexto.setRollbackOnly();// hace rollback...
            t.printStackTrace(System.out);// imprime en consola el error
        }
    }

    @Override
    public void delete(TblDireccion obj) {
        try {
            dao.delete(obj);

        } catch (Throwable t) {
            contexto.setRollbackOnly();// hace rollback...
            t.printStackTrace(System.out);// imprime en consola el error
        }
    }

}
