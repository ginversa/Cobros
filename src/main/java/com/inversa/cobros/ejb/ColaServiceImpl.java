/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.ejb;

import com.inversa.cobros.dao.ColaDao;
import com.inversa.cobros.model.TblCola;
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
public class ColaServiceImpl implements ColaService, ColaServiceRemote {

    @Resource
    private SessionContext contexto;

    @Inject
    private ColaDao dao;

    @Override
    public List<TblCola> findAll() {
        return dao.findAll();
    }

    @Override
    public TblCola findByIdCola(TblCola obj) {
        return dao.findByIdCola(obj);
    }

    @Override
    public List<TblCola> findByCodigoCartera(TblCola obj) {
        return dao.findByCodigoCartera(obj);
    }

    @Override
    public List<TblCola> findByCodigoGestor(TblCola obj) {
        return dao.findByCodigoGestor(obj);
    }

    @Override
    public List<TblCola> findByIdentificacion(TblCola obj) {
        return dao.findByIdentificacion(obj);
    }

    @Override
    public void insert(TblCola obj) {
        try {
            dao.insert(obj);

        } catch (Throwable t) {
            contexto.setRollbackOnly();// hace rollback...
            t.printStackTrace(System.out);// imprime en consola el error
        }
    }

    @Override
    public void update(TblCola obj) {
        try {
            dao.update(obj);

        } catch (Throwable t) {
            contexto.setRollbackOnly();// hace rollback...
            t.printStackTrace(System.out);// imprime en consola el error
        }
    }

    @Override
    public void delete(TblCola obj) {
        try {
            dao.delete(obj);

        } catch (Throwable t) {
            contexto.setRollbackOnly();// hace rollback...
            t.printStackTrace(System.out);// imprime en consola el error
        }
    }

    @Override
    public List<TblCola> findByCodigoGestorAndIdFiltro(TblCola obj) {
        return dao.findByCodigoGestorAndIdFiltro(obj);
    }
    
    @Override
    public List<TblCola> findByIdFiltro(TblCola obj) {
        return dao.findByIdFiltro(obj);
    }

    @Override
    public TblCola buscarSiguienteClienteEnCola(TblCola siguienteEnCola) {
        return dao.buscarSiguienteClienteEnCola(siguienteEnCola);
    }

    @Override
    public List<TblCola> findByCodigoGestorAndIdFiltroLimit_ONE(TblCola obj) {
        return dao.findByCodigoGestorAndIdFiltroLimit_ONE(obj);
    }

    @Override
    public void updateIdGestion(Long id_gestion, Long id_filtrocola, Long id_cola, String identificacion) {
        dao.updateIdGestion(id_gestion, id_filtrocola, id_cola, identificacion);
    }

}
