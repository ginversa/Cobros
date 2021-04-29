/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.ejb;

import com.inversa.cobros.dao.SubtipificacionDao;
import com.inversa.cobros.model.Subtipificacion;
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
public class SubtipificacionServiceImpl implements SubtipificacionService, SubtipificacionServiceRemote {

    @Resource
    private SessionContext contexto;

    @Inject
    private SubtipificacionDao dao;

    @Override
    public List<Subtipificacion> findAll() {
        return dao.findAll();
    }

    @Override
    public Subtipificacion findById(Subtipificacion obj) {
        return dao.findById(obj);
    }
    
    @Override
    public List<Subtipificacion> findByIdTipificacion(Subtipificacion obj){
        return dao.findByIdTipificacion(obj);
    }

    @Override
    public List<Subtipificacion> findByDescripcion(Subtipificacion obj) {
        return dao.findByDescripcion(obj);
    }

    @Override
    public List<Subtipificacion> findByCodigoCartera(Subtipificacion obj) {
        return dao.findByCodigoCartera(obj);
    }

    @Override
    public List<Subtipificacion> findByEstado(Subtipificacion obj) {
        return dao.findByEstado(obj);
    }

    @Override
    public List<Subtipificacion> findByUsuarioingreso(Subtipificacion obj) {
        return dao.findByUsuarioingreso(obj);
    }

    @Override
    public List<Subtipificacion> findByFechaingreso(Subtipificacion obj) {
        return dao.findByFechaingreso(obj);
    }

    @Override
    public List<Subtipificacion> findByUsuariomodifico(Subtipificacion obj) {
        return dao.findByUsuariomodifico(obj);
    }

    @Override
    public List<Subtipificacion> findByFechamodifico(Subtipificacion obj) {
        return dao.findByFechamodifico(obj);
    }

    @Override
    public void insert(Subtipificacion obj) {
         try {
            dao.insert(obj);

        } catch (Throwable t) {
            contexto.setRollbackOnly();// hace rollback...
            t.printStackTrace(System.out);// imprime en consola el error
        }
    }

    @Override
    public void update(Subtipificacion obj) {
         try {
            dao.update(obj);

        } catch (Throwable t) {
            contexto.setRollbackOnly();// hace rollback...
            t.printStackTrace(System.out);// imprime en consola el error
        }
    }

    @Override
    public void delete(Subtipificacion obj) {
         try {
            dao.delete(obj);

        } catch (Throwable t) {
            contexto.setRollbackOnly();// hace rollback...
            t.printStackTrace(System.out);// imprime en consola el error
        }
    }

    @Override
    public Subtipificacion findByCodigo(String codigo) {
        return dao.findByCodigo(codigo);
    }
    
}
