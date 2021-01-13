/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.ejb;

import com.inversa.cobros.dao.TipoTelefonoDao;
import com.inversa.cobros.model.Tipotelefono;
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
public class TipoTelefonoServiceImpl implements TipoTelefonoService, TipoTelefonoServiceRemote {

    @Resource
    private SessionContext contexto;

    @Inject
    private TipoTelefonoDao dao;

    @Override
    public List<Tipotelefono> findAll() {
        return dao.findAll();
    }

    @Override
    public Tipotelefono findById(Tipotelefono obj) {
        return dao.findById(obj);
    }

    @Override
    public List<Tipotelefono> findByEstado(Tipotelefono obj) {
        return dao.findByEstado(obj);
    }

    @Override
    public List<Tipotelefono> findByUsuarioingreso(Tipotelefono obj) {
        return dao.findByUsuarioingreso(obj);
    }

    @Override
    public List<Tipotelefono> findByFechaingreso(Tipotelefono obj) {
        return dao.findByFechaingreso(obj);
    }

    @Override
    public List<Tipotelefono> findByUsuariomodifico(Tipotelefono obj) {
        return dao.findByUsuariomodifico(obj);
    }

    @Override
    public List<Tipotelefono> findByFechamodifico(Tipotelefono obj) {
        return dao.findByFechamodifico(obj);
    }

    @Override
    public void insert(Tipotelefono obj) {
        try {
            dao.insert(obj);

        } catch (Throwable t) {
            contexto.setRollbackOnly();// hace rollback...
            t.printStackTrace(System.out);// imprime en consola el error
        }
    }

    @Override
    public void update(Tipotelefono obj) {
        try {
            dao.update(obj);

        } catch (Throwable t) {
            contexto.setRollbackOnly();// hace rollback...
            t.printStackTrace(System.out);// imprime en consola el error
        }
    }

    @Override
    public void delete(Tipotelefono obj) {
        try {
            dao.delete(obj);

        } catch (Throwable t) {
            contexto.setRollbackOnly();// hace rollback...
            t.printStackTrace(System.out);// imprime en consola el error
        }
    }

}
