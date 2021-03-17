/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.dao;

import com.inversa.cobros.model.TblCartera;
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
public class CarteraDaoImpl implements CarteraDao {

    @PersistenceContext(unitName = "cobrosPU")
    EntityManager em;

    @Override
    public List<TblCartera> findAll() {
        TypedQuery<TblCartera> query = em.createNamedQuery("TblCartera.findAll", TblCartera.class);
        List<TblCartera> results = query.getResultList();
        return results;
    }

    @Override
    public TblCartera findById(TblCartera obj) {
        return em.find(TblCartera.class, obj.getId());
    }

    @Override
    public List<TblCartera> findByCodigoCartera(TblCartera obj) {
        TypedQuery<TblCartera> query = em.createNamedQuery("TblCartera.findByCodigoCartera", TblCartera.class);
        query.setParameter("codigoCartera", obj.getCodigoCartera());
        List<TblCartera> results = query.getResultList();
        return results;
    }

    @Override
    public List<TblCartera> findByCodigoGestor(TblCartera obj) {
        TypedQuery<TblCartera> query = em.createNamedQuery("TblCartera.findByCodigoGestor", TblCartera.class);
        query.setParameter("codigoGestor", obj.getCodigoGestor());
        List<TblCartera> results = query.getResultList();
        return results;
    }

    @Override
    public List<TblCartera> findByNumeroClienteCif(TblCartera obj) {
        TypedQuery<TblCartera> query = em.createNamedQuery("TblCartera.findByNumeroClienteCif", TblCartera.class);
        query.setParameter("numeroClienteCif", obj.getNumeroClienteCif());
        List<TblCartera> results = query.getResultList();
        return results;
    }

    @Override
    public List<TblCartera> findByIdentificacion(TblCartera obj) {
        TypedQuery<TblCartera> query = em.createNamedQuery("TblCartera.findByIdentificacion", TblCartera.class);
        query.setParameter("identificacion", obj.getIdentificacion());
        List<TblCartera> results = query.getResultList();
        return results;
    }

    @Override
    public List<TblCartera> findByNumeroCuenta(TblCartera obj) {
        TypedQuery<TblCartera> query = em.createNamedQuery("TblCartera.findByNumeroCuenta", TblCartera.class);
        query.setParameter("numeroCuenta", obj.getNumeroCuenta());
        List<TblCartera> results = query.getResultList();
        return results;
    }

    @Override
    public List<TblCartera> findByNumeroTarjeta(TblCartera obj) {
        TypedQuery<TblCartera> query = em.createNamedQuery("TblCartera.findByNumeroTarjeta", TblCartera.class);
        query.setParameter("numeroTarjeta", obj.getNumeroTarjeta());
        List<TblCartera> results = query.getResultList();
        return results;
    }

    @Override
    public List<TblCartera> findByTipoProducto(TblCartera obj) {
        TypedQuery<TblCartera> query = em.createNamedQuery("TblCartera.findByTipoProducto", TblCartera.class);
        query.setParameter("tipoProducto", obj.getTipoProducto());
        List<TblCartera> results = query.getResultList();
        return results;
    }

    @Override
    public List<TblCartera> findByBucket(TblCartera obj) {
        TypedQuery<TblCartera> query = em.createNamedQuery("TblCartera.findByBucket", TblCartera.class);
        query.setParameter("bucket", obj.getBucket());
        List<TblCartera> results = query.getResultList();
        return results;
    }

    @Override
    public List<TblCartera> findByDiasMora(TblCartera obj) {
        TypedQuery<TblCartera> query = em.createNamedQuery("TblCartera.findByDiasMora", TblCartera.class);
        query.setParameter("diasMora", obj.getDiasMora());
        List<TblCartera> results = query.getResultList();
        return results;
    }

    @Override
    public List<TblCartera> findByPlacement(TblCartera obj) {
        TypedQuery<TblCartera> query = em.createNamedQuery("TblCartera.findByPlacement", TblCartera.class);
        query.setParameter("placement", obj.getPlacement());
        List<TblCartera> results = query.getResultList();
        return results;
    }

    @Override
    public List<TblCartera> findByFechaAsignacion(TblCartera obj) {
        TypedQuery<TblCartera> query = em.createNamedQuery("TblCartera.findByFechaAsignacion", TblCartera.class);
        query.setParameter("fechaAsignacion", obj.getFechaAsignacion());
        List<TblCartera> results = query.getResultList();
        return results;
    }

    @Override
    public List<TblCartera> findByEstado(TblCartera obj) {
        TypedQuery<TblCartera> query = em.createNamedQuery("TblCartera.findByEstado", TblCartera.class);
        query.setParameter("estado", obj.getEstado());
        List<TblCartera> results = query.getResultList();
        return results;
    }

    @Override
    public List<TblCartera> findByUsuarioIngreso(TblCartera obj) {
        TypedQuery<TblCartera> query = em.createNamedQuery("TblCartera.findByUsuarioIngreso", TblCartera.class);
        query.setParameter("usuarioIngreso", obj.getUsuarioIngreso());
        List<TblCartera> results = query.getResultList();
        return results;
    }

    @Override
    public void insert(TblCartera obj) {
        em.persist(obj);
    }

    @Override
    public void update(TblCartera obj) {
        em.merge(obj);
    }

    @Override
    public void delete(TblCartera obj) {
        em.merge(obj);
        em.remove(obj);
    }

    @Override
    public List<TblCartera> findByCarteraGestorIdentificacion(TblCartera obj) {
        TypedQuery<TblCartera> query = em.createNamedQuery("TblCartera.findByCarteraGestorIdentificacion", TblCartera.class);
        query.setParameter("codigoCartera", obj.getCodigoCartera());
        query.setParameter("codigoGestor", obj.getCodigoGestor());
        query.setParameter("identificacion", obj.getIdentificacion());
        List<TblCartera> results = query.getResultList();
        return results;
    }

    @Override
    public List<TblCartera> findByCodigoGestorANDCodigoCartera(TblCartera objCartera) {
        Query query = em.createNativeQuery("select cartera.* from tbl_cartera cartera where cartera.codigo_cartera = ?1 and cartera.codigo_gestor = ?2 and not exists (select tg.id_gestion from tbl_gestion tg where tg.operacion = cartera.numero_cuenta)", TblCartera.class);
        query.setParameter(1, objCartera.getCodigoCartera());
        query.setParameter(2, objCartera.getCodigoGestor());
        List<TblCartera> results = query.getResultList();
        return results;
    }

    /*
    public List<TblCartera> findByCodigoGestorANDCodigoCartera(TblCartera objCartera) {
        TypedQuery<TblCartera> query = em.createNamedQuery("TblCartera.findByCodigoGestorANDCodigoCartera", TblCartera.class);
        query.setParameter("codigoGestor", objCartera.getCodigoGestor());
        query.setParameter("codigoCartera", objCartera.getCodigoCartera());
        List<TblCartera> results = query.getResultList();
        return results;
    }
     */
}
