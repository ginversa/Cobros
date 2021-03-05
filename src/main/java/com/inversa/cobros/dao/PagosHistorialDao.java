/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.dao;

import com.inversa.cobros.model.TblPagosHistorial;
import java.util.List;

/**
 *
 * @author Z420WK
 */
public interface PagosHistorialDao {

    public List<TblPagosHistorial> findAll();

    public TblPagosHistorial findById(TblPagosHistorial entity);

    public List<TblPagosHistorial> findByCodigoCartera(TblPagosHistorial entity);

    public List<TblPagosHistorial> findByNumeroCuenta(TblPagosHistorial entity);

    public List<TblPagosHistorial> findByFechaPago(TblPagosHistorial entity);

    public List<TblPagosHistorial> findByCodigoGestor(TblPagosHistorial entity);

    public List<TblPagosHistorial> findByTipoPago(TblPagosHistorial entity);

    public List<TblPagosHistorial> findByUsuarioIngreso(TblPagosHistorial entity);

    public List<TblPagosHistorial> findByFechaIngreso(TblPagosHistorial entity);

    public List<TblPagosHistorial> findByUsuarioModifico(TblPagosHistorial entity);

    public List<TblPagosHistorial> findByFechaModifico(TblPagosHistorial entity);
    
    public List<TblPagosHistorial> findByNumeroCuentaANDCodigoCartera(TblPagosHistorial entity);

}
