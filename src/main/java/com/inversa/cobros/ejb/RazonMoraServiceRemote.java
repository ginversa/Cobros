/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.ejb;

import com.inversa.cobros.model.Razonmora;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Z420WK
 */
@Remote
public interface RazonMoraServiceRemote {

    public List<Razonmora> findAll();

    public Razonmora findById(Razonmora obj);

    public List<Razonmora> findByCodigoCartera(Razonmora obj);

    public List<Razonmora> findByEstado(Razonmora obj);

    public List<Razonmora> findByUsuarioingreso(Razonmora obj);

    public List<Razonmora> findByFechaingreso(Razonmora obj);

    public List<Razonmora> findByUsuariomodifico(Razonmora obj);

    public List<Razonmora> findByFechamodifico(Razonmora obj);

    public void insert(Razonmora obj);

    public void update(Razonmora obj);

    public void delete(Razonmora obj);
}
