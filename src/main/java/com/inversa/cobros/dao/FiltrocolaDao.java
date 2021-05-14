/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.dao;

import com.inversa.cobros.model.TblCola;
import com.inversa.cobros.model.TblFiltrocola;
import java.util.List;

/**
 *
 * @author Z420WK
 */
public interface FiltrocolaDao {

    public List<TblFiltrocola> findAll();

    public TblFiltrocola findByIdFiltrocola(TblFiltrocola obj);

    public void insert(TblFiltrocola obj);

    public void update(TblFiltrocola obj);

    public void delete(TblFiltrocola obj);
    
    public List<TblFiltrocola> findByCodigoCartera(TblFiltrocola obj);
    
    public List<TblCola> findByCodigoGestorANDCodigoCarteraANDFiltro(Long IdFiltrocola, String codigo_cartera, String codigo_gestor, String codigoTipificacion, String codigoSubTipificacion, String codigoRazonMora, Long uno, Long dos, Long mesUP, Long mesPP);

}
