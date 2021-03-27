/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.ejb;

import com.inversa.cobros.model.TblCorreo;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Z420WK
 */
@Remote
public interface CorreoServiceRemote {

    public List<TblCorreo> findAll();

    public TblCorreo findById(TblCorreo obj);

    public TblCorreo findByCorreo(TblCorreo obj);

    public List<TblCorreo> findByRanking(TblCorreo obj);

    public List<TblCorreo> findByUsuarioingreso(TblCorreo obj);

    public List<TblCorreo> findByFechaingreso(TblCorreo obj);

    public List<TblCorreo> findByUsuariomodifico(TblCorreo obj);

    public List<TblCorreo> findByFechamodifico(TblCorreo obj);

    public void insert(TblCorreo obj);

    public void update(TblCorreo obj);

    public void delete(TblCorreo obj);
    
    public List<TblCorreo> findByContactoEstado(Integer idContacto,String estado);

}
