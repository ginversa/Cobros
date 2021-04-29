/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.ejb;

import com.inversa.cobros.model.Tipificacion;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Z420WK
 */
@Remote
public interface TipificacionServiceRemote {

    public List<Tipificacion> findAll();

    public Tipificacion findById(Tipificacion obj);

    public List<Tipificacion> findByDescripcion(Tipificacion obj);

    public List<Tipificacion> findByCodigoCartera(Tipificacion obj);

    public List<Tipificacion> findByEstado(Tipificacion obj);

    public List<Tipificacion> findByUsuarioingreso(Tipificacion obj);

    public List<Tipificacion> findByFechaingreso(Tipificacion obj);

    public List<Tipificacion> findByUsuariomodifico(Tipificacion obj);

    public List<Tipificacion> findByFechamodifico(Tipificacion obj);

    public void insert(Tipificacion obj);

    public void update(Tipificacion obj);

    public void delete(Tipificacion obj);
    
    public Tipificacion findByCodigo(String codigo);

}
