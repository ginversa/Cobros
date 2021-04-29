/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.ejb;

import com.inversa.cobros.dao.SaldoDao;
import com.inversa.cobros.model.TblSaldo;
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
public class SaldoServiceImpl implements SaldoService, SaldoServiceRemote {

    @Resource
    private SessionContext contexto;

    @Inject
    private SaldoDao dao;

    @Override
    public List<TblSaldo> findAll() {
        return dao.findAll();
    }

    @Override
    public TblSaldo findByIdSaldo(TblSaldo obj) {
        return dao.findByIdSaldo(obj);
    }

    @Override
    public void insert(TblSaldo obj) {
        try {
            dao.insert(obj);

        } catch (Throwable t) {
            contexto.setRollbackOnly();// hace rollback...
            t.printStackTrace(System.out);// imprime en consola el error
        }
    }

    @Override
    public void update(TblSaldo obj) {
        try {
            dao.update(obj);

        } catch (Throwable t) {
            contexto.setRollbackOnly();// hace rollback...
            t.printStackTrace(System.out);// imprime en consola el error
        }
    }

    @Override
    public void delete(TblSaldo obj) {
        try {
            dao.delete(obj);

        } catch (Throwable t) {
            contexto.setRollbackOnly();// hace rollback...
            t.printStackTrace(System.out);// imprime en consola el error
        }
    }

}
