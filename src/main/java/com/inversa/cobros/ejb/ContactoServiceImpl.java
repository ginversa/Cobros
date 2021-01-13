/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.ejb;

import com.inversa.cobros.dao.ContactoDao;
import com.inversa.cobros.model.TblContacto;
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
public class ContactoServiceImpl implements ContactoService, ContactoServiceRemote {

    @Resource
    private SessionContext contexto;

    @Inject
    private ContactoDao dao;

    @Override
    public List<TblContacto> findAll() {
        return dao.findAll();
    }

    @Override
    public TblContacto findById(TblContacto obj) {
        return dao.findById(obj);
    }

    @Override
    public TblContacto findByNombre(TblContacto obj) {
        return dao.findByNombre(obj);
    }

    @Override
    public TblContacto findByCedula(TblContacto obj) {
        return dao.findByCedula(obj);        
    }

    @Override
    public List<TblContacto> findByUsuarioingreso(TblContacto obj) {
        return dao.findByUsuarioingreso(obj);
    }

    @Override
    public List<TblContacto> findByFechaingreso(TblContacto obj) {
        return dao.findByFechaingreso(obj);
    }

    @Override
    public List<TblContacto> findByUsuariomodifico(TblContacto obj) {
        return dao.findByUsuariomodifico(obj);
    }

    @Override
    public List<TblContacto> findByFechamodifico(TblContacto obj) {
        return dao.findByFechamodifico(obj);
    }

    @Override
    public void insert(TblContacto obj) {
        try {
            dao.insert(obj);

        } catch (Throwable t) {
            contexto.setRollbackOnly();// hace rollback...
            t.printStackTrace(System.out);// imprime en consola el error
        }
    }

    @Override
    public void update(TblContacto obj) {
        try {
            dao.update(obj);

        } catch (Throwable t) {
            contexto.setRollbackOnly();// hace rollback...
            t.printStackTrace(System.out);// imprime en consola el error
        }
    }

    @Override
    public void delete(TblContacto obj) {
        try {
            dao.delete(obj);

        } catch (Throwable t) {
            contexto.setRollbackOnly();// hace rollback...
            t.printStackTrace(System.out);// imprime en consola el error
        }
    }

}
