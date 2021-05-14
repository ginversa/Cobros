/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.ejb;

import com.inversa.cobros.model.TblCola;
import com.inversa.cobros.model.TblFiltrocola;
import java.util.Date;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Z420WK
 */
@Remote
public interface FiltrocolaServiceRemote {

    public List<TblFiltrocola> findAll();

    public TblFiltrocola findByIdFiltrocola(TblFiltrocola obj);

    public void insert(TblFiltrocola obj);

    public void update(TblFiltrocola obj);

    public void delete(TblFiltrocola obj);

    public List<TblFiltrocola> findByCodigoCartera(TblFiltrocola obj);
    
    public void generarCola(TblFiltrocola obj, String usuarioIngreso, Date fechaIngreso);
    
    public void deleteCola(TblFiltrocola filtrocola);
    
    public List<TblCola> findByCodigoGestorANDCodigoCarteraANDFiltro(TblFiltrocola obj);
    
    public List<TblCola> filtrarCarteraSegunFindme(TblFiltrocola filtrocola, List<TblCola> carteraFiltrada);
}
