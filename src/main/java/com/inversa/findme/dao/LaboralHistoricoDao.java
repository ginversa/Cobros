/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.findme.dao;

import com.inversa.findme.model.LaboralHistorico;
import java.math.BigDecimal;

/**
 *
 * @author Z420WK
 */
public interface LaboralHistoricoDao {
    
    public LaboralHistorico getPoseeTrabajo(String identificacion);
    
    public LaboralHistorico getRangoBalance(String identificacion, BigDecimal cantidad, int condicion);
}
