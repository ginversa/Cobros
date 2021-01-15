/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.ejb;

import com.inversa.cobros.dao.ResultadogestionDao;
import com.inversa.cobros.model.TblResultadogestion;
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
public class ResultadogestionServiceImpl implements ResultadogestionService, ResultadogestionServiceRemote {

    @Resource
    private SessionContext contexto;

    @Inject
    private ResultadogestionDao dao;

    @Override
    public List<TblResultadogestion> findAll() {
        return dao.findAll();
    }

    @Override
    public TblResultadogestion findByIdResultadogestion(TblResultadogestion obj) {
        return dao.findByIdResultadogestion(obj);
    }

    @Override
    public TblResultadogestion findByDescripcion(TblResultadogestion obj) {
        return dao.findByDescripcion(obj);
    }

    @Override
    public TblResultadogestion findByCodigo(TblResultadogestion obj) {
        return dao.findByCodigo(obj);
    }

    @Override
    public List<TblResultadogestion> findByUsuarioingreso(TblResultadogestion obj) {
        return dao.findByUsuarioingreso(obj);
    }

    @Override
    public List<TblResultadogestion> findByFechaingreso(TblResultadogestion obj) {
        return dao.findByFechaingreso(obj);
    }

    @Override
    public List<TblResultadogestion> findByUsuariomodifico(TblResultadogestion obj) {
        return dao.findByUsuariomodifico(obj);
    }

    @Override
    public List<TblResultadogestion> findByFechamodifico(TblResultadogestion obj) {
        return dao.findByFechamodifico(obj);
    }

    @Override
    public void insert(TblResultadogestion obj) {
        try {
            dao.insert(obj);

        } catch (Throwable t) {
            contexto.setRollbackOnly();// hace rollback...
            t.printStackTrace(System.out);// imprime en consola el error
        }
    }

    @Override
    public void update(TblResultadogestion obj) {
        try {
            dao.update(obj);

        } catch (Throwable t) {
            contexto.setRollbackOnly();// hace rollback...
            t.printStackTrace(System.out);// imprime en consola el error
        }
    }

    @Override
    public void delete(TblResultadogestion obj) {
        try {
            dao.delete(obj);

        } catch (Throwable t) {
            contexto.setRollbackOnly();// hace rollback...
            t.printStackTrace(System.out);// imprime en consola el error
        }
    }

}
