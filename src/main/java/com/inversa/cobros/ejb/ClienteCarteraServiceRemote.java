/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.ejb;

import com.inversa.cobros.model.TblClienteCartera;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Z420WK
 */
@Remote
public interface ClienteCarteraServiceRemote {

    public List<TblClienteCartera> findAll();

    public TblClienteCartera findByIdClienteCartera(TblClienteCartera obj);

    public List<TblClienteCartera> findByCodigoCartera(TblClienteCartera obj);

    public List<TblClienteCartera> findByCodigoGestor(String codigoGestor);
    
}
