/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.dao;

import com.inversa.cobros.model.TblTelefono;
import java.util.List;

/**
 *
 * @author Z420WK
 */
public interface TelefonoDao {

    public List<TblTelefono> findAll();

    public TblTelefono findById(TblTelefono obj);

    public TblTelefono findByTelefono(TblTelefono obj);

    public List<TblTelefono> findByRanking(TblTelefono obj);

    public List<TblTelefono> findByUsuarioingreso(TblTelefono obj);

    public List<TblTelefono> findByFechaingreso(TblTelefono obj);

    public List<TblTelefono> findByUsuariomodifico(TblTelefono obj);

    public List<TblTelefono> findByFechamodifico(TblTelefono obj);

    public void insert(TblTelefono obj);

    public void update(TblTelefono obj);

    public void delete(TblTelefono obj);
    
    public List<TblTelefono> findByContactoEstado(Integer idContacto,String estado);
}
