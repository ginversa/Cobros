/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.ejb;

import com.inversa.cobros.dao.PromesaDao;
import com.inversa.cobros.model.TblPromesa;
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
public class PromesaServiceImpl implements PromesaService, PromesaServiceRemote {

    @Resource
    private SessionContext contexto;

    @Inject
    private PromesaDao dao;

    @Override
    public List<TblPromesa> findAll() {
        return dao.findAll();                
    }

    @Override
    public TblPromesa findById(TblPromesa obj) {
        return dao.findById(obj);
    }

    @Override
    public List<TblPromesa> findByOperacion(TblPromesa obj) {
        return dao.findByOperacion(obj);
    }

    @Override
    public List<TblPromesa> findByTelefono(TblPromesa obj) {
        return dao.findByTelefono(obj);
    }

    @Override
    public List<TblPromesa> findByFechaPago(TblPromesa obj) {
        return dao.findByFechaPago(obj);
    }

    @Override
    public List<TblPromesa> findByUsuarioingreso(TblPromesa obj) {
        return dao.findByUsuarioingreso(obj);
    }

    @Override
    public List<TblPromesa> findByFechaingreso(TblPromesa obj) {
        return dao.findByFechaingreso(obj);
    }

    @Override
    public List<TblPromesa> findByUsuariomodifico(TblPromesa obj) {
        return dao.findByUsuariomodifico(obj);
    }

    @Override
    public List<TblPromesa> findByFechamodifico(TblPromesa obj) {
        return dao.findByFechamodifico(obj);
    }

    @Override
    public void insert(TblPromesa obj) {
        try {
            dao.insert(obj);

        } catch (Throwable t) {
            contexto.setRollbackOnly();// hace rollback...
            t.printStackTrace(System.out);// imprime en consola el error
        }
    }

    @Override
    public void update(TblPromesa obj) {
        try {
            dao.update(obj);

        } catch (Throwable t) {
            contexto.setRollbackOnly();// hace rollback...
            t.printStackTrace(System.out);// imprime en consola el error
        }
    }

    @Override
    public void delete(TblPromesa obj) {
        try {
            dao.delete(obj);

        } catch (Throwable t) {
            contexto.setRollbackOnly();// hace rollback...
            t.printStackTrace(System.out);// imprime en consola el error
        }
    }

    @Override
    public List<TblPromesa> findByFechaPagoAndUsuarioIngreso(TblPromesa obj) {
        return dao.findByFechaPagoAndUsuarioIngreso(obj);
    }

    @Override
    public TblPromesa findPromesaUltimoPago(Long idGestion, Long idLlamada) {
        return dao.findPromesaUltimoPago(idGestion, idLlamada);
    }

    @Override
    public TblPromesa findPromesaUltimoPago(Long idGestion) {
        return dao.findPromesaUltimoPago(idGestion);
    }

    @Override
    public List<TblPromesa> findByFechaPagoAndUsuarioIngreso(TblPromesa obj, String codigo_gestor, String codigo_cartera) {
        return dao.findByFechaPagoAndUsuarioIngreso(obj, codigo_gestor, codigo_cartera);
    }

    @Override
    public int updateEstadoPromesa(Long idGestion, String estado) {
        return dao.updateEstadoPromesa(idGestion, estado);
    }

}
