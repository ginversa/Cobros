/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.ejb;

import com.inversa.cobros.dao.UsuarioDao;
import com.inversa.cobros.model.TblUsuario;
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
public class UsuarioServiceImpl implements UsuarioService, UsuarioServiceRemote {

    @Resource
    private SessionContext contexto;

    @Inject
    private UsuarioDao dao;

    @Override
    public List<TblUsuario> findAll() {
        return dao.findAll();
    }

    @Override
    public TblUsuario findById(TblUsuario obj) {
        return dao.findById(obj);
    }

    @Override
    public TblUsuario findByUsuario(TblUsuario obj) {
        return dao.findByUsuario(obj);
    }

    @Override
    public TblUsuario findByClave(TblUsuario obj) {
        return dao.findByClave(obj);
    }

    @Override
    public TblUsuario findByCodigoGestor(TblUsuario obj) {
        return dao.findByCodigoGestor(obj);
    }

    @Override
    public List<TblUsuario> findByEstado(TblUsuario obj) {
        return dao.findByEstado(obj);
    }

    @Override
    public List<TblUsuario> findByUsuarioingreso(TblUsuario obj) {
        return dao.findByUsuarioingreso(obj);
    }

    @Override
    public List<TblUsuario> findByFechaingreso(TblUsuario obj) {
        return dao.findByFechaingreso(obj);
    }

    @Override
    public List<TblUsuario> findByUsuariomodifico(TblUsuario obj) {
        return dao.findByUsuariomodifico(obj);
    }

    @Override
    public List<TblUsuario> findByFechamodifico(TblUsuario obj) {
        return dao.findByFechamodifico(obj);
    }

    @Override
    public void insert(TblUsuario obj) {
        try {
            dao.insert(obj);

        } catch (Throwable t) {
            contexto.setRollbackOnly();// hace rollback...
            t.printStackTrace(System.out);// imprime en consola el error
        }
    }

    @Override
    public void update(TblUsuario obj) {
        try {
            dao.update(obj);

        } catch (Throwable t) {
            contexto.setRollbackOnly();// hace rollback...
            t.printStackTrace(System.out);// imprime en consola el error
        }
    }

    @Override
    public void delete(TblUsuario obj) {
        try {
            dao.delete(obj);

        } catch (Throwable t) {
            contexto.setRollbackOnly();// hace rollback...
            t.printStackTrace(System.out);// imprime en consola el error
        }
    }

    @Override
    public TblUsuario findByUsuarioAndClave(TblUsuario obj) {
        return dao.findByUsuarioAndClave(obj);
    }

}
