/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.findme.ejb;

import com.inversa.findme.dao.LaboralHistoricoDao;
import com.inversa.findme.model.LaboralHistorico;
import java.math.BigDecimal;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Z420WK
 */
@Stateless
public class LaboralHistoricoServiceImpl implements LaboralHistoricoService, LaboralHistoricoServiceRemote {

    @Resource
    private SessionContext contexto;

    @Inject
    private LaboralHistoricoDao dao;

    @Override
    public LaboralHistorico getPoseeTrabajo(String identificacion) {
        return dao.getPoseeTrabajo(identificacion);
    }

    @Override
    public LaboralHistorico getRangoBalance(String identificacion, BigDecimal cantidad, int condicion) {
        return dao.getRangoBalance(identificacion, cantidad, condicion);
    }

}
