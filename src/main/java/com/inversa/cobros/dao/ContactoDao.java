/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.dao;

import java.util.List;
import com.inversa.cobros.model.TblContacto;

/**
 *
 * @author Z420WK
 */
public interface ContactoDao {

    public List<TblContacto> findAll();

    public TblContacto findById(TblContacto obj);

    public TblContacto findByNombre(TblContacto obj);

    public TblContacto findByCedula(TblContacto obj);

    public List<TblContacto> findByUsuarioingreso(TblContacto obj);

    public List<TblContacto> findByFechaingreso(TblContacto obj);

    public List<TblContacto> findByUsuariomodifico(TblContacto obj);

    public List<TblContacto> findByFechamodifico(TblContacto obj);

    public void insert(TblContacto obj);

    public void update(TblContacto obj);

    public void delete(TblContacto obj);
}
