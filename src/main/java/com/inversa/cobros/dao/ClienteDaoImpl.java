/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.dao;

import com.inversa.cobros.model.TblCliente;
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
public class ClienteDaoImpl implements ClienteDao {

    @PersistenceContext(unitName = "cobrosPU")
    EntityManager em;

    @Override
    public List<TblCliente> findAll() {
        TypedQuery<TblCliente> query = em.createNamedQuery("TblCliente.findAll", TblCliente.class);
        List<TblCliente> results = query.getResultList();
        return results;
    }

    @Override
    public TblCliente findByIdCliente(TblCliente obj) {
        return em.find(TblCliente.class, obj.getIdCliente());
    }

    @Override
    public List<TblCliente> findByNombre(TblCliente obj) {
        TypedQuery<TblCliente> query = em.createNamedQuery("TblCliente.findByNombre", TblCliente.class);
        query.setParameter("nombre", obj.getNombre());
        List<TblCliente> results = query.getResultList();
        return results;
    }

    @Override
    public TblCliente findByCodigo(TblCliente obj) {
        TypedQuery<TblCliente> query = em.createNamedQuery("TblCliente.findByCodigo", TblCliente.class);
        query.setParameter("codigo", obj.getCodigo());
        TblCliente results = query.getSingleResult();
        return results;
    }

    @Override
    public List<TblCliente> findByEstado(TblCliente obj) {
        TypedQuery<TblCliente> query = em.createNamedQuery("TblCliente.findByEstado", TblCliente.class);
        query.setParameter("estado", obj.getEstado());
        List<TblCliente> results = query.getResultList();
        return results;
    }

    @Override
    public List<TblCliente> findByUsuarioingreso(TblCliente obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<TblCliente> findByFechaingreso(TblCliente obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<TblCliente> findByUsuariomodifico(TblCliente obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<TblCliente> findByFechamodifico(TblCliente obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
