/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.ejb;

import com.inversa.cobros.dao.ClienteCarteraDao;
import com.inversa.cobros.model.TblClienteCartera;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Z420WK
 */
@Stateless
public class ClienteCarteraServiceImpl implements ClienteCarteraService, ClienteCarteraServiceRemote {

    @Resource
    private SessionContext contexto;

    @Inject
    private ClienteCarteraDao dao;

    @Override
    public List<TblClienteCartera> findAll() {
        return dao.findAll();
    }

    @Override
    public TblClienteCartera findByIdClienteCartera(TblClienteCartera obj) {
        return dao.findByIdClienteCartera(obj);
    }

    @Override
    public List<TblClienteCartera> findByCodigoCartera(TblClienteCartera obj) {
        return dao.findByCodigoCartera(obj);
    }

    @Override
    public List<TblClienteCartera> findByCodigoGestor(String codigoGestor) {
        return dao.findByCodigoGestor(codigoGestor);
    }

}
