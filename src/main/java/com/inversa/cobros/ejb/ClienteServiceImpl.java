/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.ejb;

import com.inversa.cobros.dao.ClienteDao;
import com.inversa.cobros.model.TblCliente;
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
public class ClienteServiceImpl implements ClienteService, ClienteServiceRemote {

    @Resource
    private SessionContext contexto;

    @Inject
    private ClienteDao dao;

    @Override
    public List<TblCliente> findAll() {
        return dao.findAll();
    }

    @Override
    public TblCliente findByIdCliente(TblCliente obj) {
        return dao.findByIdCliente(obj);
    }

    @Override
    public List<TblCliente> findByNombre(TblCliente obj) {
        return dao.findByNombre(obj);
    }

    @Override
    public TblCliente findByCodigo(TblCliente obj) {
        return dao.findByCodigo(obj);
    }

    @Override
    public List<TblCliente> findByEstado(TblCliente obj) {
        return dao.findByEstado(obj);
    }

    @Override
    public List<TblCliente> findByUsuarioingreso(TblCliente obj) {
        return dao.findByUsuarioingreso(obj);
    }

    @Override
    public List<TblCliente> findByFechaingreso(TblCliente obj) {
        return dao.findByFechaingreso(obj);
    }

    @Override
    public List<TblCliente> findByUsuariomodifico(TblCliente obj) {
        return dao.findByUsuarioingreso(obj);
    }

    @Override
    public List<TblCliente> findByFechamodifico(TblCliente obj) {
        return dao.findByFechamodifico(obj);
    }

}
