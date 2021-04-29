/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.ejb;

import com.inversa.cobros.dao.TipificacionDao;
import com.inversa.cobros.model.Tipificacion;
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
public class TipificacionServiceImpl implements TipificacionService, TipificacionServiceRemote {

    @Resource
    private SessionContext contexto;

    @Inject
    private TipificacionDao dao;


    @Override
    public List<Tipificacion> findAll() {
        return dao.findAll();
    }

    @Override
    public Tipificacion findById(Tipificacion obj) {
        return dao.findById(obj);
    }

    @Override
    public List<Tipificacion> findByDescripcion(Tipificacion obj) {
        return dao.findByDescripcion(obj);
    }

    @Override
    public List<Tipificacion> findByCodigoCartera(Tipificacion obj) {
        return dao.findByCodigoCartera(obj);
    }

    @Override
    public List<Tipificacion> findByEstado(Tipificacion obj) {
        return dao.findByEstado(obj);
    }

    @Override
    public List<Tipificacion> findByUsuarioingreso(Tipificacion obj) {
        return dao.findByUsuarioingreso(obj);
    }

    @Override
    public List<Tipificacion> findByFechaingreso(Tipificacion obj) {
        return dao.findByFechaingreso(obj);
    }

    @Override
    public List<Tipificacion> findByUsuariomodifico(Tipificacion obj) {
        return dao.findByUsuariomodifico(obj);
    }

    @Override
    public List<Tipificacion> findByFechamodifico(Tipificacion obj) {
        return dao.findByFechamodifico(obj);
    }

    @Override
    public void insert(Tipificacion obj) {
        try {
            dao.insert(obj);

        } catch (Throwable t) {
            contexto.setRollbackOnly();// hace rollback...
            t.printStackTrace(System.out);// imprime en consola el error
        }
    }

    @Override
    public void update(Tipificacion obj) {
        try {
            dao.update(obj);

        } catch (Throwable t) {
            contexto.setRollbackOnly();// hace rollback...
            t.printStackTrace(System.out);// imprime en consola el error
        }
    }

    @Override
    public void delete(Tipificacion obj) {
        try {
            dao.delete(obj);

        } catch (Throwable t) {
            contexto.setRollbackOnly();// hace rollback...
            t.printStackTrace(System.out);// imprime en consola el error
        }
    }

    @Override
    public Tipificacion findByCodigo(String codigo) {
        return dao.findByCodigo(codigo);
    }

}
