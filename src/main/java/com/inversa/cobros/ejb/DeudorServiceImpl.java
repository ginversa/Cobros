/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.ejb;

import com.inversa.cobros.dao.DeudorDao;
import com.inversa.cobros.model.TblDeudor;
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
public class DeudorServiceImpl implements DeudorService, DeudorServiceRemote {

    @Resource
    private SessionContext contexto;

    @Inject
    private DeudorDao dao;

    @Override
    public List<TblDeudor> findAll() {
        return dao.findAll();
    }

    @Override
    public TblDeudor findById(TblDeudor obj) {
        return dao.findById(obj);
    }

    @Override
    public List<TblDeudor> findByCodigoGestor(TblDeudor obj) {
        return dao.findByCodigoGestor(obj);
    }

    @Override
    public void insert(TblDeudor obj) {
         try {
            dao.insert(obj);

        } catch (Throwable t) {
            contexto.setRollbackOnly();// hace rollback...
            t.printStackTrace(System.out);// imprime en consola el error
        }
    }

    @Override
    public void update(TblDeudor obj) {
        try {
            dao.update(obj);

        } catch (Throwable t) {
            contexto.setRollbackOnly();// hace rollback...
            t.printStackTrace(System.out);// imprime en consola el error
        }
    }

    @Override
    public void delete(TblDeudor obj) {
         try {
            dao.delete(obj);

        } catch (Throwable t) {
            contexto.setRollbackOnly();// hace rollback...
            t.printStackTrace(System.out);// imprime en consola el error
        }
    }

    @Override
    public List<TblDeudor> findByCarteraGestorDocumento(TblDeudor obj) {
        return dao.findByCarteraGestorDocumento(obj);
    }

    @Override
    public List<TblDeudor> findByGestorIfNotExistsGestion(TblDeudor obj) {
        return dao.findByGestorIfNotExistsGestion(obj);
    }

}
