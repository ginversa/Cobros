/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.dao;

import com.inversa.cobros.model.TblGestion;
import java.util.List;

/**
 *
 * @author Z420WK
 */
public interface GestionDao {

    public List<TblGestion> findAll();

    public TblGestion findById(TblGestion obj);

    public List<TblGestion> findByCodigoCartera(TblGestion obj);

    public List<TblGestion> findByNombreCliente(TblGestion obj);

    public List<TblGestion> findByDocumento(TblGestion obj);

    public List<TblGestion> findByCodigoGestor(TblGestion obj);    

    public List<TblGestion> findByMoneda(TblGestion obj);

    public List<TblGestion> findByFechaGestion(TblGestion obj);

    public List<TblGestion> findByEstado(TblGestion obj);

    public List<TblGestion> findByUsuarioingreso(TblGestion obj);

    public List<TblGestion> findByFechaingreso(TblGestion obj);

    public List<TblGestion> findByUsuariomodifico(TblGestion obj);

    public List<TblGestion> findByFechamodifico(TblGestion obj);

    public void insert(TblGestion obj);

    public void update(TblGestion obj);

    public void delete(TblGestion obj);

}
