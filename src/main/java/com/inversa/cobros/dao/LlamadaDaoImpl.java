/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.dao;

import com.inversa.cobros.model.TblLlamada;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.validation.ConstraintViolationException;

/**
 *
 * @author Z420WK
 */
@Stateless
public class LlamadaDaoImpl implements LlamadaDao {

    @PersistenceContext(unitName = "cobrosPU")
    EntityManager em;

    @Override
    public List<TblLlamada> findAll() {
        TypedQuery<TblLlamada> query = em.createNamedQuery("TblLlamada.findAll", TblLlamada.class);
        List<TblLlamada> results = query.getResultList();
        return results;
    }

    @Override
    public TblLlamada findById(TblLlamada obj) {
        return em.find(TblLlamada.class, obj.getIdLlamada());
    }

    @Override
    public TblLlamada findByCallLogId(TblLlamada obj) {
        TypedQuery<TblLlamada> query = em.createNamedQuery("TblLlamada.findByCallLogId", TblLlamada.class);
        query.setParameter("callLogId", obj.getCallLogId());
        TblLlamada results = query.getSingleResult();
        return results;
    }

    @Override
    public List<TblLlamada> findByDateIni(TblLlamada obj) {
        TypedQuery<TblLlamada> query = em.createNamedQuery("TblLlamada.findByDateIni", TblLlamada.class);
        query.setParameter("dateIni", obj.getDateIni());
        List<TblLlamada> results = query.getResultList();
        return results;
    }

    @Override
    public List<TblLlamada> findByDateEnd(TblLlamada obj) {
        TypedQuery<TblLlamada> query = em.createNamedQuery("TblLlamada.findByDateEnd", TblLlamada.class);
        query.setParameter("dateEnd", obj.getDateEnd());
        List<TblLlamada> results = query.getResultList();
        return results;
    }

    @Override
    public List<TblLlamada> findByCallFromNumber(TblLlamada obj) {
        TypedQuery<TblLlamada> query = em.createNamedQuery("TblLlamada.findByCallFromNumber", TblLlamada.class);
        query.setParameter("callFromNumber", obj.getCallFromNumber());
        List<TblLlamada> results = query.getResultList();
        return results;
    }

    @Override
    public List<TblLlamada> findByCallToNumber(TblLlamada obj) {
        TypedQuery<TblLlamada> query = em.createNamedQuery("TblLlamada.findByCallToNumber", TblLlamada.class);
        query.setParameter("callToNumber", obj.getCallToNumber());
        List<TblLlamada> results = query.getResultList();
        return results;
    }

    @Override
    public List<TblLlamada> findByDialstatus(TblLlamada obj) {
        TypedQuery<TblLlamada> query = em.createNamedQuery("TblLlamada.findByDialstatus", TblLlamada.class);
        query.setParameter("dialstatus", obj.getDialstatus());
        List<TblLlamada> results = query.getResultList();
        return results;
    }

    @Override
    public List<TblLlamada> findByEstado(TblLlamada obj) {
        TypedQuery<TblLlamada> query = em.createNamedQuery("TblLlamada.findByEstado", TblLlamada.class);
        query.setParameter("estado", obj.getEstado());
        List<TblLlamada> results = query.getResultList();
        return results;
    }

    @Override
    public List<TblLlamada> findByUsuarioingreso(TblLlamada obj) {
        TypedQuery<TblLlamada> query = em.createNamedQuery("TblLlamada.findByUsuarioingreso", TblLlamada.class);
        query.setParameter("usuarioingreso", obj.getUsuarioingreso());
        List<TblLlamada> results = query.getResultList();
        return results;
    }

    @Override
    public List<TblLlamada> findByFechaingreso(TblLlamada obj) {
        TypedQuery<TblLlamada> query = em.createNamedQuery("TblLlamada.findByFechaingreso", TblLlamada.class);
        query.setParameter("fechaingreso", obj.getFechaingreso());
        List<TblLlamada> results = query.getResultList();
        return results;
    }

    @Override
    public List<TblLlamada> findByUsuariomodifico(TblLlamada obj) {
        TypedQuery<TblLlamada> query = em.createNamedQuery("TblLlamada.findByUsuariomodifico", TblLlamada.class);
        query.setParameter("usuariomodifico", obj.getUsuariomodifico());
        List<TblLlamada> results = query.getResultList();
        return results;
    }

    @Override
    public List<TblLlamada> findByFechamodifico(TblLlamada obj) {
        TypedQuery<TblLlamada> query = em.createNamedQuery("TblLlamada.findByFechamodifico", TblLlamada.class);
        query.setParameter("fechamodifico", obj.getFechamodifico());
        List<TblLlamada> results = query.getResultList();
        return results;
    }

    @Override
    public Long insert(TblLlamada obj) {
        Long id = null;
        try {
            em.persist(obj);
            em.flush();
            em.refresh(obj);
            System.out.println("Llamada ID: " + obj.getIdGestion());
            id = obj.getIdLlamada();
        } catch (ConstraintViolationException e) {
            System.out.println("insert Llamada : " + e.getMessage());
        }

        return id;
    }

    @Override
    public void update(TblLlamada obj) {
        em.merge(obj);
    }

    @Override
    public void delete(TblLlamada obj) {
        em.merge(obj);
        em.remove(obj);
    }

    /**
     *
     * @param identificacion
     * @param codigoCartera
     * @return
     */
    @Override
    public List<TblLlamada> findByIdentificacionCartera(String identificacion, String codigoCartera) {
        try {
            Query query = em.createNativeQuery("select tl.* from tbl_llamada tl inner join tbl_gestion tg on tg.id_gestion = tl.id_gestion where tg.identificacion = ?1 and tg.codigo_cartera = ?2 order by tl.id_llamada desc", TblLlamada.class);
            query.setParameter(1, identificacion);
            query.setParameter(2, codigoCartera);
            List<TblLlamada> found = query.getResultList();
            if (found.isEmpty()) {
                return null; //or throw checked exception data not found
            } else {
                return found;
            }

        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     *
     * @param telefono
     * @param codigoCartera
     * @return
     */
    public List<TblLlamada> buscarPorTelefono(String telefono, String codigoCartera) {
        try {
            Query query = em.createNativeQuery("select tl.* from tbl_llamada tl inner join tbl_gestion tg on tg.id_gestion = tl.id_gestion where tl.call_to_number = ?1 and tg.codigo_cartera = ?2 order by tl.id_llamada desc", TblLlamada.class);
            query.setParameter(1, telefono);
            query.setParameter(2, codigoCartera);
            List<TblLlamada> found = query.getResultList();
            if (found.isEmpty()) {
                return null; //or throw checked exception data not found
            } else {
                return found;
            }

        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     *
     * @param codigoGestor
     * @param codigoCartera
     * @return
     */
    @Override
    public List<TblLlamada> buscarPorGestorCartera(String codigoGestor, String codigoCartera) {
        try {
            Query query = em.createNativeQuery("select tl.* from tbl_llamada tl inner join tbl_gestion tg on tg.id_gestion = tl.id_gestion where tg.codigo_gestor = ?1 and tg.codigo_cartera = ?2 order by tl.fechaingreso desc", TblLlamada.class);
            query.setParameter(1, codigoGestor);
            query.setParameter(2, codigoCartera);
            List<TblLlamada> found = query.getResultList();
            if (found.isEmpty()) {
                return null; //or throw checked exception data not found
            } else {
                return found;
            }

        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<TblLlamada> findLlamadasByOperacion(String codigoCartera, String identificacion, String operacion) {
        try {
            Query query = em.createNativeQuery("select tl.* from tbl_llamada tl inner join tbl_gestion tg on tg.id_gestion = tl.id_gestion where tl.operacion = ?1 and tg.identificacion = ?2 and tg.codigo_cartera = ?3 order by (case when tl.fechamodifico is not null then tl.fechamodifico else tl.fechaingreso end)", TblLlamada.class);
            query.setParameter(1, operacion);
            query.setParameter(2, identificacion);
            query.setParameter(3, codigoCartera);
            List<TblLlamada> found = query.getResultList();
            if (found.isEmpty()) {
                return null; //or throw checked exception data not found
            } else {
                return found;
            }

        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public TblLlamada findUltimaLlamada(Long idGestion) {
        try {
            Query query = em.createNativeQuery("select tl.* from tbl_llamada tl where tl.id_gestion = ?1 order by tl.id_llamada desc limit 1", TblLlamada.class);
            query.setParameter(1, idGestion);
            List<TblLlamada> found = query.getResultList();
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
    public TblLlamada findUltimaLlamada(String codigoCartera, String identificacion, String operacion) {
        try {
            Query query = em.createNativeQuery("select tl.* from tbl_llamada tl inner join tbl_gestion tg on tg.id_gestion = tl.id_gestion where tl.operacion = ?1 and tg.identificacion = ?2 and tg.codigo_cartera = ?3 order by (case when tl.fechamodifico is not null then tl.fechamodifico else tl.fechaingreso end) desc limit 1", TblLlamada.class);
            query.setParameter(1, operacion);
            query.setParameter(2, identificacion);
            query.setParameter(3, codigoCartera);
            List<TblLlamada> found = query.getResultList();
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
    public TblLlamada findUltimaLlamada(String codigoCartera, String identificacion) {
        try {
            Query query = em.createNativeQuery("select tl.* from tbl_llamada tl inner join tbl_gestion tg on tg.id_gestion = tl.id_gestion where tg.identificacion = ?1 and tg.codigo_cartera = ?2 order by tl.id_llamada desc limit 1", TblLlamada.class);
            query.setParameter(1, identificacion);
            query.setParameter(2, codigoCartera);
            List<TblLlamada> found = query.getResultList();
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
    public List<TblLlamada> findByGestion(Long idGestion) {
        try {
            Query query = em.createNativeQuery("select tl.* from tbl_llamada tl where tl.id_gestion = ?1 order by tl.id_llamada desc", TblLlamada.class);
            query.setParameter(1, idGestion);
            List<TblLlamada> found = query.getResultList();
            if (found.isEmpty()) {
                return null; //or throw checked exception data not found
            } else {
                return found;
            }

        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<TblLlamada> findByCarteraANDSupervisor(String codigoCartera, String codigoGestor) {
        Query query = em.createNativeQuery("select tl.*\n"
                + "  from tbl_gestion tg inner join tbl_llamada tl on tl.id_gestion = tg.id_gestion\n"
                + " where tg.codigo_cartera = ?1\n"
                + "   and tg.codigo_gestor in(select usuario.codigo_gestor from tbl_usuario usuario where usuario.id_usuariosupervisor = (select supervisor.id_persona from tbl_usuario supervisor where supervisor.codigo_gestor = ?2 and supervisor.id_rolusuario = 2))\n"
                + "order by tg.id_gestion, tl.id_llamada desc", TblLlamada.class);
        query.setParameter(1, codigoCartera);
        query.setParameter(2, codigoGestor);
        List<TblLlamada> found = query.getResultList();

        if (found.isEmpty()) {
            return null; //or throw checked exception data not found
        } else {
            return found;
        }
    }

}
