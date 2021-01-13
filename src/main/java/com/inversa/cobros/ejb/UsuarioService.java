/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.ejb;

import com.inversa.cobros.model.TblUsuario;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Z420WK
 */
@Local
public interface UsuarioService {

    public List<TblUsuario> findAll();

    public TblUsuario findById(TblUsuario obj);

    public TblUsuario findByUsuario(TblUsuario obj);

    public TblUsuario findByClave(TblUsuario obj);

    public TblUsuario findByCodigoGestor(TblUsuario obj);

    public List<TblUsuario> findByEstado(TblUsuario obj);

    public List<TblUsuario> findByUsuarioingreso(TblUsuario obj);

    public List<TblUsuario> findByFechaingreso(TblUsuario obj);

    public List<TblUsuario> findByUsuariomodifico(TblUsuario obj);

    public List<TblUsuario> findByFechamodifico(TblUsuario obj);

    public void insert(TblUsuario obj);

    public void update(TblUsuario obj);

    public void delete(TblUsuario obj);

    public TblUsuario findByUsuarioAndClave(TblUsuario obj);

}
