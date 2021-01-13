/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.ejb;

import com.inversa.cobros.model.Tipotelefono;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Z420WK
 */
@Local
public interface TipoTelefonoService {

    public List<Tipotelefono> findAll();

    public Tipotelefono findById(Tipotelefono obj);

    public List<Tipotelefono> findByEstado(Tipotelefono obj);

    public List<Tipotelefono> findByUsuarioingreso(Tipotelefono obj);

    public List<Tipotelefono> findByFechaingreso(Tipotelefono obj);

    public List<Tipotelefono> findByUsuariomodifico(Tipotelefono obj);

    public List<Tipotelefono> findByFechamodifico(Tipotelefono obj);

    public void insert(Tipotelefono obj);

    public void update(Tipotelefono obj);

    public void delete(Tipotelefono obj);

}
