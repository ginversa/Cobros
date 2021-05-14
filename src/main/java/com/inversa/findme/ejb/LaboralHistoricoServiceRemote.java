/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.findme.ejb;

import com.inversa.findme.model.LaboralHistorico;
import java.math.BigDecimal;
import javax.ejb.Remote;

/**
 *
 * @author Z420WK
 */
@Remote
public interface LaboralHistoricoServiceRemote {

    public LaboralHistorico getPoseeTrabajo(String identificacion);

    public LaboralHistorico getRangoBalance(String identificacion, BigDecimal cantidad, int condicion);

}
