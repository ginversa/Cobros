/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.ejb;

import com.inversa.cobros.dao.RazonmoraDao;
import com.inversa.cobros.model.Razonmora;
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
public class RazonMoraServiceImpl implements RazonMoraService, RazonMoraServiceRemote {

    @Resource
    private SessionContext contexto;

    @Inject
    private RazonmoraDao dao;

    @Override
    public List<Razonmora> findAll() {
        return dao.findAll();
    }

    @Override
    public Razonmora findById(Razonmora obj) {
        return dao.findById(obj);
    }

    @Override
    public List<Razonmora> findByCodigoCartera(Razonmora obj) {
        return dao.findByCodigoCartera(obj);
    }

    @Override
    public List<Razonmora> findByEstado(Razonmora obj) {
        return dao.findByEstado(obj);
    }

    @Override
    public List<Razonmora> findByUsuarioingreso(Razonmora obj) {
        return dao.findByUsuarioingreso(obj);
    }

    @Override
    public List<Razonmora> findByFechaingreso(Razonmora obj) {
        return dao.findByFechaingreso(obj);
    }

    @Override
    public List<Razonmora> findByUsuariomodifico(Razonmora obj) {
        return dao.findByUsuariomodifico(obj);
    }

    @Override
    public List<Razonmora> findByFechamodifico(Razonmora obj) {
        return dao.findByFechamodifico(obj);
    }

    @Override
    public void insert(Razonmora obj) {
        try {
            dao.insert(obj);

        } catch (Throwable t) {
            contexto.setRollbackOnly();// hace rollback...
            t.printStackTrace(System.out);// imprime en consola el error
        }
    }

    @Override
    public void update(Razonmora obj) {
        try {
            dao.update(obj);

        } catch (Throwable t) {
            contexto.setRollbackOnly();// hace rollback...
            t.printStackTrace(System.out);// imprime en consola el error
        }
    }

    @Override
    public void delete(Razonmora obj) {
        try {
            dao.delete(obj);

        } catch (Throwable t) {
            contexto.setRollbackOnly();// hace rollback...
            t.printStackTrace(System.out);// imprime en consola el error
        }
    }

}
