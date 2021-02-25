/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.ejb;

import com.inversa.cobros.dao.GestionDao;
import com.inversa.cobros.model.TblGestion;
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
public class GestionServiceImpl implements GestionService, GestionServiceRemote {

    @Resource
    private SessionContext contexto;

    @Inject
    private GestionDao dao;

    @Override
    public List<TblGestion> findAll() {
        return dao.findAll();
    }

    @Override
    public TblGestion findById(TblGestion obj) {
        return dao.findById(obj);
    }

    @Override
    public List<TblGestion> findByCodigoCartera(TblGestion obj) {
        return dao.findByCodigoCartera(obj);
    }

    @Override
    public List<TblGestion> findByNombreCliente(TblGestion obj) {
        return dao.findByNombreCliente(obj);
    }

    @Override
    public List<TblGestion> findByIdentificacion(TblGestion obj) {
        return dao.findByIdentificacion(obj);
    }

    @Override
    public List<TblGestion> findByCodigoGestor(TblGestion obj) {
        return dao.findByCodigoGestor(obj);
    }


    @Override
    public List<TblGestion> findByFechaGestion(TblGestion obj) {
        return dao.findByFechaGestion(obj);
    }

    @Override
    public List<TblGestion> findByEstado(TblGestion obj) {
        return dao.findByEstado(obj);
    }

    @Override
    public List<TblGestion> findByUsuarioingreso(TblGestion obj) {
        return dao.findByUsuarioingreso(obj);
    }

    @Override
    public List<TblGestion> findByFechaingreso(TblGestion obj) {
        return dao.findByFechaingreso(obj);
    }

    @Override
    public List<TblGestion> findByUsuariomodifico(TblGestion obj) {
        return dao.findByUsuariomodifico(obj);
    }

    @Override
    public List<TblGestion> findByFechamodifico(TblGestion obj) {
        return dao.findByFechamodifico(obj);
    }

    @Override
    public void insert(TblGestion obj) {
        try {
            dao.insert(obj);

        } catch (Throwable t) {
            contexto.setRollbackOnly();// hace rollback...
            t.printStackTrace(System.out);// imprime en consola el error
        }
    }

    @Override
    public void update(TblGestion obj) {
        try {
            dao.update(obj);

        } catch (Throwable t) {
            contexto.setRollbackOnly();// hace rollback...
            t.printStackTrace(System.out);// imprime en consola el error
        }
    }

    @Override
    public void delete(TblGestion obj) {
        try {
            dao.delete(obj);

        } catch (Throwable t) {
            contexto.setRollbackOnly();// hace rollback...
            t.printStackTrace(System.out);// imprime en consola el error
        }
    }

    @Override
    public TblGestion findByCodigoCarteraANDIdentificacion(TblGestion obj) {
        return dao.findByCodigoCarteraANDIdentificacion(obj);
    }

    @Override
    public List<TblGestion> findByIdentificacionANDCodigoCartera(TblGestion obj) {
        return dao.findByIdentificacionANDCodigoCartera(obj);
    }

}
