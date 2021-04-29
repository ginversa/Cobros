/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.dao;

import com.inversa.cobros.model.TblLlamada;
import java.util.List;

/**
 *
 * @author Z420WK
 */
public interface LlamadaDao {

    public List<TblLlamada> findAll();

    public TblLlamada findById(TblLlamada obj);

    public TblLlamada findByCallLogId(TblLlamada obj);

    public List<TblLlamada> findByDateIni(TblLlamada obj);

    public List<TblLlamada> findByDateEnd(TblLlamada obj);

    public List<TblLlamada> findByCallFromNumber(TblLlamada obj);

    public List<TblLlamada> findByCallToNumber(TblLlamada obj);

    public List<TblLlamada> findByDialstatus(TblLlamada obj);
    
    public List<TblLlamada> findByEstado(TblLlamada obj);

    public List<TblLlamada> findByUsuarioingreso(TblLlamada obj);

    public List<TblLlamada> findByFechaingreso(TblLlamada obj);

    public List<TblLlamada> findByUsuariomodifico(TblLlamada obj);

    public List<TblLlamada> findByFechamodifico(TblLlamada obj);
    
    public TblLlamada findUltimaLlamada(Long idGestion);

    public Long insert(TblLlamada obj);

    public void update(TblLlamada obj);

    public void delete(TblLlamada obj);
    
    public List<TblLlamada> findByIdentificacionCartera(String identificacion, String codigoCartera);
    
    public List<TblLlamada> buscarPorTelefono(String telefono, String codigoCartera);
    
    public List<TblLlamada> buscarPorGestorCartera(String codigoGestor, String codigoCartera);

    public TblLlamada findUltimaLlamada(String codigoCartera, String identificacion, String operacion);

    public List<TblLlamada> findLlamadasByOperacion(String codigoCartera, String identificacion, String numeroCuenta);
    
    public TblLlamada findUltimaLlamada(String codigoCartera, String identificacion);
    
    public List<TblLlamada> findByGestion(Long idGestion);

}
