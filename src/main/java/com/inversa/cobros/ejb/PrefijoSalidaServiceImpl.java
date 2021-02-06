/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.ejb;

import com.inversa.cobros.dao.PrefijoSalidaDao;
import com.inversa.cobros.model.TblPrefijoSalida;
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
public class PrefijoSalidaServiceImpl implements PrefijoSalidaService, PrefijoSalidaServiceRemote {

    @Resource
    private SessionContext contexto;

    @Inject
    private PrefijoSalidaDao dao;

    @Override
    public List<TblPrefijoSalida> findAll() {
        return dao.findAll();
    }

    @Override
    public TblPrefijoSalida findById(TblPrefijoSalida obj) {
        return dao.findById(obj);
    }

}
