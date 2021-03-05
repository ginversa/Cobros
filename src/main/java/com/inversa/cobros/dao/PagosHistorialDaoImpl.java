/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.dao;

import com.inversa.cobros.model.TblPagosHistorial;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Z420WK
 */
@Stateless
public class PagosHistorialDaoImpl implements PagosHistorialDao {

    @PersistenceContext(unitName = "cobrosPU")
    EntityManager em;

    @Override
    public List<TblPagosHistorial> findAll() {
        TypedQuery<TblPagosHistorial> query = em.createNamedQuery("TblPagosHistorial.findAll", TblPagosHistorial.class);
        List<TblPagosHistorial> results = query.getResultList();
        return results;
    }

    @Override
    public TblPagosHistorial findById(TblPagosHistorial entity) {
        return em.find(TblPagosHistorial.class, entity.getId());
    }

    @Override
    public List<TblPagosHistorial> findByCodigoCartera(TblPagosHistorial entity) {
        TypedQuery<TblPagosHistorial> query = em.createNamedQuery("TblPagosHistorial.findByCodigoCartera", TblPagosHistorial.class);
        query.setParameter("codigoCartera", entity.getCodigoCartera());
        List<TblPagosHistorial> results = query.getResultList();
        return results;
    }

    @Override
    public List<TblPagosHistorial> findByNumeroCuenta(TblPagosHistorial entity) {
        TypedQuery<TblPagosHistorial> query = em.createNamedQuery("TblPagosHistorial.findByNumeroCuenta", TblPagosHistorial.class);
        query.setParameter("numeroCuenta", entity.getNumeroCuenta());
        List<TblPagosHistorial> results = query.getResultList();
        return results;
    }

    @Override
    public List<TblPagosHistorial> findByFechaPago(TblPagosHistorial entity) {
        TypedQuery<TblPagosHistorial> query = em.createNamedQuery("TblPagosHistorial.findByFechaPago", TblPagosHistorial.class);
        query.setParameter("fechaPago", entity.getFechaPago());
        List<TblPagosHistorial> results = query.getResultList();
        return results;
    }

    @Override
    public List<TblPagosHistorial> findByCodigoGestor(TblPagosHistorial entity) {
        TypedQuery<TblPagosHistorial> query = em.createNamedQuery("TblPagosHistorial.findByCodigoGestor", TblPagosHistorial.class);
        query.setParameter("codigoGestor", entity.getCodigoGestor());
        List<TblPagosHistorial> results = query.getResultList();
        return results;
    }

    @Override
    public List<TblPagosHistorial> findByTipoPago(TblPagosHistorial entity) {
        TypedQuery<TblPagosHistorial> query = em.createNamedQuery("TblPagosHistorial.findByTipoPago", TblPagosHistorial.class);
        query.setParameter("tipoPago", entity.getTipoPago());
        List<TblPagosHistorial> results = query.getResultList();
        return results;
    }

    @Override
    public List<TblPagosHistorial> findByUsuarioIngreso(TblPagosHistorial entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<TblPagosHistorial> findByFechaIngreso(TblPagosHistorial entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<TblPagosHistorial> findByUsuarioModifico(TblPagosHistorial entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<TblPagosHistorial> findByFechaModifico(TblPagosHistorial entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<TblPagosHistorial> findByNumeroCuentaANDCodigoCartera(TblPagosHistorial entity) {
        TypedQuery<TblPagosHistorial> query = em.createNamedQuery("TblPagosHistorial.findByNumeroCuentaANDCodigoCartera", TblPagosHistorial.class);
        query.setParameter("numeroCuenta", entity.getNumeroCuenta());
        query.setParameter("codigoCartera", entity.getCodigoCartera());
        List<TblPagosHistorial> results = query.getResultList();
        return results;
    }

}
