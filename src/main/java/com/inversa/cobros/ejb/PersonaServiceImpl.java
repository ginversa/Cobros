/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.ejb;

import com.inversa.cobros.dao.PersonaDao;
import com.inversa.cobros.model.TblPersona;
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
public class PersonaServiceImpl implements PersonaService, PersonaServiceRemote {

    @Resource
    private SessionContext contexto;

    @Inject
    private PersonaDao dao;

    @Override
    public List<TblPersona> findAll() {
        return dao.findAll();
    }

    @Override
    public TblPersona findById(TblPersona obj) {
        return dao.findById(obj);
    }

    @Override
    public TblPersona findByNombre(TblPersona obj) {
        return dao.findByNombre(obj);
    }

    @Override
    public TblPersona findByCedula(TblPersona obj) {
        return dao.findByCedula(obj);
    }

    @Override
    public List<TblPersona> findByUsuarioingreso(TblPersona obj) {
        return dao.findByUsuarioingreso(obj);
    }

    @Override
    public List<TblPersona> findByFechaingreso(TblPersona obj) {
        return dao.findByFechaingreso(obj);
    }

    @Override
    public List<TblPersona> findByUsuariomodifico(TblPersona obj) {
        return dao.findByUsuariomodifico(obj);
    }

    @Override
    public List<TblPersona> findByFechamodifico(TblPersona obj) {
        return dao.findByFechamodifico(obj);
    }

    @Override
    public void insert(TblPersona obj) {
        try {
            dao.insert(obj);

        } catch (Throwable t) {
            contexto.setRollbackOnly();// hace rollback...
            t.printStackTrace(System.out);// imprime en consola el error
        }
    }

    @Override
    public void update(TblPersona obj) {
        try {
            dao.update(obj);

        } catch (Throwable t) {
            contexto.setRollbackOnly();// hace rollback...
            t.printStackTrace(System.out);// imprime en consola el error
        }
    }

    @Override
    public void delete(TblPersona obj) {
        try {
            dao.delete(obj);

        } catch (Throwable t) {
            contexto.setRollbackOnly();// hace rollback...
            t.printStackTrace(System.out);// imprime en consola el error
        }
    }

}
