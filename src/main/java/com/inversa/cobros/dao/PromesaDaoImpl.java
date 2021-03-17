/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.dao;

import com.inversa.cobros.model.TblPromesa;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

/**
 *
 * @author Z420WK
 */
@Stateless
public class PromesaDaoImpl implements PromesaDao {

    @PersistenceContext(unitName = "cobrosPU")
    EntityManager em;

    @Override
    public List<TblPromesa> findAll() {
        TypedQuery<TblPromesa> query = em.createNamedQuery("TblPromesa.findAll", TblPromesa.class);
        List<TblPromesa> results = query.getResultList();
        return results;
    }

    @Override
    public TblPromesa findById(TblPromesa obj) {
        return em.find(TblPromesa.class, obj.getIdPromesa());
    }

    @Override
    public List<TblPromesa> findByOperacion(TblPromesa obj) {
        TypedQuery<TblPromesa> query = em.createNamedQuery("TblPromesa.findByOperacion", TblPromesa.class);
        query.setParameter("operacion", obj.getOperacion());
        List<TblPromesa> results = query.getResultList();
        return results;
    }

    @Override
    public List<TblPromesa> findByTelefono(TblPromesa obj) {
        TypedQuery<TblPromesa> query = em.createNamedQuery("TblPromesa.findByTelefono", TblPromesa.class);
        query.setParameter("telefono", obj.getTelefono());
        List<TblPromesa> results = query.getResultList();
        return results;
    }

    @Override
    public List<TblPromesa> findByFechaPago(TblPromesa obj) {
        TypedQuery<TblPromesa> query = em.createNamedQuery("TblPromesa.findByFechaPago", TblPromesa.class);
        query.setParameter("fechaPago", obj.getFechaPago());
        List<TblPromesa> results = query.getResultList();
        return results;
    }

    @Override
    public List<TblPromesa> findByUsuarioingreso(TblPromesa obj) {
        TypedQuery<TblPromesa> query = em.createNamedQuery("TblPromesa.findByUsuarioingreso", TblPromesa.class);
        query.setParameter("usuarioingreso", obj.getUsuarioingreso());
        List<TblPromesa> results = query.getResultList();
        return results;
    }

    @Override
    public List<TblPromesa> findByFechaingreso(TblPromesa obj) {
        TypedQuery<TblPromesa> query = em.createNamedQuery("TblPromesa.findByFechaingreso", TblPromesa.class);
        query.setParameter("fechaingreso", obj.getFechaingreso());
        List<TblPromesa> results = query.getResultList();
        return results;
    }

    @Override
    public List<TblPromesa> findByUsuariomodifico(TblPromesa obj) {
        TypedQuery<TblPromesa> query = em.createNamedQuery("TblPromesa.findByUsuariomodifico", TblPromesa.class);
        query.setParameter("usuariomodifico", obj.getUsuariomodifico());
        List<TblPromesa> results = query.getResultList();
        return results;
    }

    @Override
    public List<TblPromesa> findByFechamodifico(TblPromesa obj) {
        TypedQuery<TblPromesa> query = em.createNamedQuery("TblPromesa.findByFechamodifico", TblPromesa.class);
        query.setParameter("fechamodifico", obj.getFechamodifico());
        List<TblPromesa> results = query.getResultList();
        return results;
    }

    @Override
    public void insert(TblPromesa obj) {
        em.persist(obj);
        em.flush();
        em.refresh(obj);
        System.out.println("Promesa ID: " + obj.getIdPromesa());
    }

    @Override
    public void update(TblPromesa obj) {
        em.merge(obj);
    }

    @Override
    public void delete(TblPromesa obj) {
        em.merge(obj);
        em.remove(obj);
    }

    @Override
    public List<TblPromesa> findByFechaPagoAndUsuarioIngreso(TblPromesa obj) {
        TypedQuery<TblPromesa> query = em.createNamedQuery("TblPromesa.findByFechaPagoAndUsuarioIngreso", TblPromesa.class);
        query.setParameter("fechaPago", obj.getFechaPago(), TemporalType.DATE);
        query.setParameter("usuarioingreso", obj.getUsuarioingreso());
        List<TblPromesa> results = query.getResultList();
        return results;
    }

    @Override
    public TblPromesa findPromesaUltimoPago(Long idGestion, Long idLlamada) {

        try {
            Query query = em.createNativeQuery("select tp.* from tbl_promesa tp where tp.id_gestion = ?1 and tp.id_llamada = ?2 and tp.fecha_pago = (select max(tp.fecha_pago) from tbl_promesa tp where tp.id_gestion = ?3 and tp.id_llamada = ?4) order by tp.id_promesa desc", TblPromesa.class);
            query.setParameter(1, idGestion);
            query.setParameter(2, idLlamada);
            query.setParameter(3, idGestion);
            query.setParameter(4, idLlamada);
            List<TblPromesa> found = query.getResultList();

            if (found.isEmpty()) {
                return null; //or throw checked exception data not found
            } else {
                return found.get(0);
            }
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public TblPromesa findPromesaUltimoPago(Long idGestion) {

        try {
            Query query = em.createNativeQuery("select tp.* from tbl_promesa tp where tp.id_gestion = ?1 and tp.fecha_pago = (select max(tp.fecha_pago) from tbl_promesa tp where tp.id_gestion = ?2 and tp.estado != 'DEL') and tp.estado != 'DEL' order by tp.id_promesa desc", TblPromesa.class);
            query.setParameter(1, idGestion);
            query.setParameter(2, idGestion);
            List<TblPromesa> found = query.getResultList();
            if (found.isEmpty()) {
                return null; //or throw checked exception data not found
            } else {
                return found.get(0);
            }

        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<TblPromesa> findByFechaPagoAndUsuarioIngreso(TblPromesa obj, String codigo_gestor, String codigo_cartera) {
        Query query = em.createNativeQuery("select tp.* from tbl_promesa tp where tp.fecha_pago = ?1 and tp.usuarioingreso = ?2 and tp.estado != 'DEL' and EXISTS (select tg.id_gestion from tbl_gestion tg where tg.codigo_gestor = ?3 and tg.codigo_cartera = ?4) order by tp.id_promesa desc", TblPromesa.class);
        query.setParameter(1, obj.getFechaPago(), TemporalType.DATE);
        query.setParameter(2, obj.getUsuarioingreso());
        query.setParameter(3, codigo_gestor);
        query.setParameter(4, codigo_cartera);
        List<TblPromesa> results = query.getResultList();
        return results;
    }

    /**
     *
     * @param idGestion
     * @param estado
     * @return
     */
    @Override
    public int updateEstadoPromesa(Long idGestion, String estado) {
        Query query = em.createQuery("UPDATE TblPromesa SET estado = :estado WHERE idGestion.idGestion = :idGestion");
        query.setParameter("estado", estado);
        query.setParameter("idGestion", idGestion);
        int updateCount = query.executeUpdate();
        return updateCount;
    }

}
