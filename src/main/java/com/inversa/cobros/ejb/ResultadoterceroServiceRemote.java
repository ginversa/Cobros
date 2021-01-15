/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.ejb;

import com.inversa.cobros.model.TblResultadotercero;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Z420WK
 */
@Remote
public interface ResultadoterceroServiceRemote {

    public List<TblResultadotercero> findAll();

    public TblResultadotercero findById(TblResultadotercero obj);

    public TblResultadotercero findByDescripcion(TblResultadotercero obj);

    public TblResultadotercero findByCodigo(TblResultadotercero obj);

    public List<TblResultadotercero> findByUsuarioingreso(TblResultadotercero obj);

    public List<TblResultadotercero> findByFechaingreso(TblResultadotercero obj);

    public List<TblResultadotercero> findByUsuariomodifico(TblResultadotercero obj);

    public List<TblResultadotercero> findByFechamodifico(TblResultadotercero obj);

    public void insert(TblResultadotercero obj);

    public void update(TblResultadotercero obj);

    public void delete(TblResultadotercero obj);

}
