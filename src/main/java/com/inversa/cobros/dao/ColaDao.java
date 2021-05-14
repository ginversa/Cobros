/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.dao;

import com.inversa.cobros.model.TblCola;
import java.util.List;

/**
 *
 * @author Z420WK
 */
public interface ColaDao {

    public List<TblCola> findAll();

    public TblCola findByIdCola(TblCola obj);

    public List<TblCola> findByCodigoCartera(TblCola obj);

    public List<TblCola> findByCodigoGestor(TblCola obj);

    public List<TblCola> findByIdentificacion(TblCola obj);
    
    public void insert(TblCola obj);

    public void update(TblCola obj);

    public void delete(TblCola obj);
    
    public List<TblCola> findByCodigoGestorAndIdFiltro(TblCola obj);
    
    public List<TblCola> findByIdFiltro(TblCola obj);

    public TblCola buscarSiguienteClienteEnCola(TblCola siguienteEnCola);
    
    public List<TblCola> findByCodigoGestorAndIdFiltroLimit_ONE(TblCola obj);
    
    public void updateIdGestion(Long id_gestion,Long id_filtrocola, Long id_cola, String identificacion);

}
