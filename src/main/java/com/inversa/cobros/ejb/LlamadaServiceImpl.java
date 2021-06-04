/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.ejb;

import com.inversa.cobros.dao.LlamadaDao;
import com.inversa.cobros.model.TblLlamada;
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
public class LlamadaServiceImpl implements LlamadaService, LlamadaServiceRemote {

    @Resource
    private SessionContext contexto;

    @Inject
    private LlamadaDao dao;

    @Inject
    private PromesaService ejbPromesaServiceLocal;

    @Override
    public List<TblLlamada> findAll() {
        return dao.findAll();
    }

    @Override
    public TblLlamada findById(TblLlamada obj) {
        return dao.findById(obj);
    }

    @Override
    public TblLlamada findByCallLogId(TblLlamada obj) {
        return dao.findByCallLogId(obj);
    }

    @Override
    public List<TblLlamada> findByDateIni(TblLlamada obj) {
        return dao.findByDateIni(obj);
    }

    @Override
    public List<TblLlamada> findByDateEnd(TblLlamada obj) {
        return dao.findByDateEnd(obj);
    }

    @Override
    public List<TblLlamada> findByCallFromNumber(TblLlamada obj) {
        return dao.findByCallFromNumber(obj);
    }

    @Override
    public List<TblLlamada> findByCallToNumber(TblLlamada obj) {
        return dao.findByCallToNumber(obj);
    }

    @Override
    public List<TblLlamada> findByDialstatus(TblLlamada obj) {
        return dao.findByDialstatus(obj);
    }

    @Override
    public List<TblLlamada> findByEstado(TblLlamada obj) {
        return dao.findByEstado(obj);
    }

    @Override
    public List<TblLlamada> findByUsuarioingreso(TblLlamada obj) {
        return dao.findByUsuarioingreso(obj);
    }

    @Override
    public List<TblLlamada> findByFechaingreso(TblLlamada obj) {
        return dao.findByFechaingreso(obj);
    }

    @Override
    public List<TblLlamada> findByUsuariomodifico(TblLlamada obj) {
        return dao.findByUsuariomodifico(obj);
    }

    @Override
    public List<TblLlamada> findByFechamodifico(TblLlamada obj) {
        return dao.findByFechamodifico(obj);
    }

    @Override
    public Long insert(TblLlamada obj) {
        Long id = null;
        try {
            
            id = dao.insert(obj);

        } catch (Throwable t) {
            contexto.setRollbackOnly();// hace rollback...
            t.printStackTrace(System.out);// imprime en consola el error
        }
        
        return id;
    }

    @Override
    public void update(TblLlamada obj) {
        try {
            dao.update(obj);

        } catch (Throwable t) {
            contexto.setRollbackOnly();// hace rollback...
            t.printStackTrace(System.out);// imprime en consola el error
        }
    }

    @Override
    public void delete(TblLlamada obj) {
        try {
            dao.delete(obj);

        } catch (Throwable t) {
            contexto.setRollbackOnly();// hace rollback...
            t.printStackTrace(System.out);// imprime en consola el error
        }
    }

    @Override
    public TblLlamada findUltimaLlamada(Long idGestion) {
        return dao.findUltimaLlamada(idGestion);
    }

    @Override
    public List<TblLlamada> findByIdentificacionCartera(String identificacion, String codigoCartera) {
        List<TblLlamada> llamadas = dao.findByIdentificacionCartera(identificacion, codigoCartera);
        if (llamadas != null && !llamadas.isEmpty()) {
            for (int i = 0; i < llamadas.size(); i++) {
                Long idGestion = llamadas.get(i).getIdGestion().getIdGestion();
                String operacion = llamadas.get(i).getOperacion();
                
                TblPromesa promesaUltimoPago = this.ejbPromesaServiceLocal.findPromesaUltimoPago(idGestion,operacion);
                llamadas.get(i).setUltimaPromesa(promesaUltimoPago);
            }
        }
        
        return llamadas;
    }

    @Override
    public List<TblLlamada> buscarPorTelefono(String telefono, String codigoCartera) {
        List<TblLlamada> llamadas = dao.buscarPorTelefono(telefono, codigoCartera);

        for (int i = 0; i < llamadas.size(); i++) {
            TblPromesa promesaUltimoPago = this.ejbPromesaServiceLocal.findPromesaUltimoPago(llamadas.get(i).getIdGestion().getIdGestion(), llamadas.get(i).getOperacion());
            llamadas.get(i).setUltimaPromesa(promesaUltimoPago);
        }

        return llamadas;
    }

    @Override
    public List<TblLlamada> buscarPorGestorCartera(String codigoGestor, String codigoCartera) {
        return dao.buscarPorGestorCartera(codigoGestor, codigoCartera);
    }

    @Override
    public TblLlamada findUltimaLlamada(String codigoCartera, String identificacion, String operacion) {
        return dao.findUltimaLlamada(codigoCartera, identificacion, operacion);
    }

    @Override
    public List<TblLlamada> findLlamadasByOperacion(String codigoCartera, String identificacion, String numeroCuenta) {
        return dao.findLlamadasByOperacion(codigoCartera, identificacion, numeroCuenta);
    }

    @Override
    public TblLlamada findUltimaLlamada(String codigoCartera, String identificacion) {
        return dao.findUltimaLlamada(codigoCartera, identificacion);
    }

    @Override
    public List<TblLlamada> findByGestion(Long idGestion) {
        return dao.findByGestion(idGestion);
    }

    @Override
    public List<TblLlamada> findByCarteraANDSupervisor(String codigoCartera, String codigoGestor) {
        return dao.findByCarteraANDSupervisor(codigoCartera, codigoGestor);
    }

}
