/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.ejb;

import com.inversa.cobros.model.TblLlamada;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Z420WK
 */
@Local
public interface LlamadaService {

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

    public void insert(TblLlamada obj);

    public void update(TblLlamada obj);

    public void delete(TblLlamada obj);
    
    public List<TblLlamada> buscarLlamada(String identificacion, String codigoCartera);

}
