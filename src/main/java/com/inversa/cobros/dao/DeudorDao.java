/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.dao;

import com.inversa.cobros.model.TblDeudor;
import java.util.List;

/**
 *
 * @author Z420WK
 */
public interface DeudorDao {

    public List<TblDeudor> findAll();

    public TblDeudor findById(TblDeudor obj);

    public List<TblDeudor> findByCodigoGestor(TblDeudor obj);
        
    public List<TblDeudor> findByCarteraGestorDocumento(TblDeudor obj);
    
    public List<TblDeudor> findByGestorIfNotExistsGestion(TblDeudor obj);

    public void insert(TblDeudor obj);

    public void update(TblDeudor obj);

    public void delete(TblDeudor obj);

}
