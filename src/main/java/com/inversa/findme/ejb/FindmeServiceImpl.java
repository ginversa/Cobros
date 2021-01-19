/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.findme.ejb;

import com.inversa.findme.dao.FindmeDao;
import com.inversa.findme.model.ArbolFamiliar;
import com.inversa.findme.model.HistorialCorreo;
import com.inversa.findme.model.HistorialDireccion;
import com.inversa.findme.model.HistorialJudicial;
import com.inversa.findme.model.HistorialLaboral;
import com.inversa.findme.model.HistorialTelefono;
import com.inversa.findme.model.PropiedadRegistrada;
import com.inversa.findme.model.SociedadAnonima;
import com.inversa.findme.model.Vehiculo;
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
public class FindmeServiceImpl implements FindmeService, FindmeServiceRemote {

    @Resource
    private SessionContext contexto;

    @Inject
    private FindmeDao dao;

    @Override
    public List<HistorialLaboral> analisis_laboral(String cedula) {
        return dao.analisis_laboral(cedula);
    }

    @Override
    public List<HistorialCorreo> historial_correos(String cedula) {
        return dao.historial_correos(cedula);
    }

    @Override
    public List<HistorialTelefono> historial_telefonos(String cedula) {
        return dao.historial_telefonos(cedula);
    }

    @Override
    public List<Vehiculo> consulta_vehiculos(String cedula) {
        return dao.consulta_vehiculos(cedula);        
    }

    @Override
    public List<PropiedadRegistrada> propiedades_registradas(String cedula) {
        return dao.propiedades_registradas(cedula);
    }

    @Override
    public List<HistorialDireccion> historial_direcciones(String cedula) {
        return dao.historial_direcciones(cedula);
    }

    @Override
    public List<HistorialJudicial> historial_judicial(String cedula) {
        return dao.historial_judicial(cedula);
    }

    @Override
    public List<SociedadAnonima> sociedades_anonimas(String cedula) {
        return dao.sociedades_anonimas(cedula);
    }

    @Override
    public List<ArbolFamiliar> arbol_familiar(String cedula) {
        return dao.arbol_familiar(cedula);
    }

}
