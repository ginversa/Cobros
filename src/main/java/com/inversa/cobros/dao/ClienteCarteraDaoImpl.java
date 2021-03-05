/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.dao;

import com.inversa.cobros.model.TblClienteCartera;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author Z420WK
 */
@Stateless
public class ClienteCarteraDaoImpl implements ClienteCarteraDao{
    
    @PersistenceContext(unitName = "cobrosPU")
    EntityManager em;

    @Override
    public List<TblClienteCartera> findAll() {
        TypedQuery<TblClienteCartera> query = em.createNamedQuery("TblClienteCartera.findAll", TblClienteCartera.class);
        List<TblClienteCartera> results = query.getResultList();
        return results;
    }

    @Override
    public TblClienteCartera findByIdClienteCartera(TblClienteCartera obj) {
        return em.find(TblClienteCartera.class, obj.getIdClienteCartera());
    }

    @Override
    public List<TblClienteCartera> findByCodigoCartera(TblClienteCartera obj) {
        TypedQuery<TblClienteCartera> query = em.createNamedQuery("TblClienteCartera.findByCodigoCartera", TblClienteCartera.class);
        query.setParameter("codigoCartera", obj.getCodigoCartera());
        List<TblClienteCartera> results = query.getResultList();
        return results;
    }

    @Override
    public List<TblClienteCartera> findByCodigoGestor(String codigoGestor) {
        Query query = em.createNativeQuery("select tcc.* from tbl_cliente_cartera tcc inner join tbl_cliente_usuario tcu on tcu.id_cliente_cartera = tcc.id_cliente_cartera inner join tbl_usuario tu on tu.id_persona = tcu.id_persona where tu.codigo_gestor = ?1", TblClienteCartera.class);
        query.setParameter(1, codigoGestor);
        List<TblClienteCartera> results = query.getResultList();
        return results;
    }
    
}
