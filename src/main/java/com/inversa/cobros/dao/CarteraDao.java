/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.dao;

import com.inversa.cobros.model.TblCartera;
import java.util.List;

/**
 *
 * @author Z420WK
 */
public interface CarteraDao {

    public List<TblCartera> findAll();

    public TblCartera findById(TblCartera obj);

    public List<TblCartera> findByCodigoCartera(TblCartera obj);

    public List<TblCartera> findByCodigoGestor(TblCartera obj);

    public List<TblCartera> findByNumeroClienteCif(TblCartera obj);

    public List<TblCartera> findByIdentificacion(TblCartera obj);

    public List<TblCartera> findByNumeroCuenta(TblCartera obj);

    public List<TblCartera> findByNumeroTarjeta(TblCartera obj);

    public List<TblCartera> findByTipoProducto(TblCartera obj);

    public List<TblCartera> findByBucket(TblCartera obj);

    public List<TblCartera> findByDiasMora(TblCartera obj);

    public List<TblCartera> findByPlacement(TblCartera obj);

    public List<TblCartera> findByFechaAsignacion(TblCartera obj);

    public List<TblCartera> findByEstado(TblCartera obj);

    public List<TblCartera> findByUsuarioIngreso(TblCartera obj);

    public void insert(TblCartera obj);

    public void update(TblCartera obj);

    public void delete(TblCartera obj);

    public List<TblCartera> findByCarteraGestorIdentificacion(TblCartera objCartera);

}
