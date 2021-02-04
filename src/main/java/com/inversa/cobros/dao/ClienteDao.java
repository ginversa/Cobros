/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.dao;

import com.inversa.cobros.model.TblCliente;
import java.util.List;

/**
 *
 * @author Z420WK
 */
public interface ClienteDao {

    public List<TblCliente> findAll();

    public TblCliente findByIdCliente(TblCliente obj);

    public List<TblCliente> findByNombre(TblCliente obj);

    public TblCliente findByCodigo(TblCliente obj);

    public List<TblCliente> findByEstado(TblCliente obj);

    public List<TblCliente> findByUsuarioingreso(TblCliente obj);

    public List<TblCliente> findByFechaingreso(TblCliente obj);

    public List<TblCliente> findByUsuariomodifico(TblCliente obj);

    public List<TblCliente> findByFechamodifico(TblCliente obj);

}
