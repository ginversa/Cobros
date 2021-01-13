/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.ejb;

import com.inversa.cobros.model.Subtipificacion;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Z420WK
 */
@Remote
public interface SubtipificacionServiceRemote {

    public List<Subtipificacion> findAll();

    public Subtipificacion findById(Subtipificacion obj);
    
    public List<Subtipificacion> findByIdTipificacion(Subtipificacion obj);

    public List<Subtipificacion> findByDescripcion(Subtipificacion obj);

    public List<Subtipificacion> findByCodigoCartera(Subtipificacion obj);

    public List<Subtipificacion> findByEstado(Subtipificacion obj);

    public List<Subtipificacion> findByUsuarioingreso(Subtipificacion obj);

    public List<Subtipificacion> findByFechaingreso(Subtipificacion obj);

    public List<Subtipificacion> findByUsuariomodifico(Subtipificacion obj);

    public List<Subtipificacion> findByFechamodifico(Subtipificacion obj);

    public void insert(Subtipificacion obj);

    public void update(Subtipificacion obj);

    public void delete(Subtipificacion obj);

}
