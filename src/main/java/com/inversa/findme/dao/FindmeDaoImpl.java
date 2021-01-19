/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.findme.dao;

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
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Z420WK
 */

@Stateless
public class FindmeDaoImpl implements FindmeDao{
    
    @PersistenceContext(unitName = "findmePU")
    EntityManager em;

    @Override
    public List<HistorialLaboral> analisis_laboral(String cedula) {
        Query sqlQuery = em.createNativeQuery("select id, cedula, estatus, cedula_patrono, nombre, ultimo_salario, ultimo_periodo, meses, promedio, tipo_salario from analisis_laboral(?1)",HistorialLaboral.class);
        List<HistorialLaboral> lista = sqlQuery.setParameter(1, cedula).getResultList();
        return lista;
    }

    @Override
    public List<HistorialCorreo> historial_correos(String cedula) {
        Query sqlQuery = em.createNativeQuery("select id,cedula,correo,fecha_del_dato from historial_correos(?1)",HistorialCorreo.class);
        List<HistorialCorreo> lista = sqlQuery.setParameter(1, cedula).getResultList();
        return lista;
    }

    @Override
    public List<HistorialTelefono> historial_telefonos(String cedula) {
        Query sqlQuery = em.createNativeQuery("select id,cedula,telefono,tipo_telefono,fecha_del_dato from historial_telefonos(?1)",HistorialTelefono.class);
        List<HistorialTelefono> lista = sqlQuery.setParameter(1, cedula).getResultList();
        return lista;
    }

    @Override
    public List<Vehiculo> consulta_vehiculos(String cedula) {
        Query sqlQuery = em.createNativeQuery("select id,cedula,placa,ano_fabricacion,estilo,tipo,valor_fiscal,pais,antiguedad,fecha_del_dato from consulta_vehiculos(?1)",Vehiculo.class);
        List<Vehiculo> lista = sqlQuery.setParameter(1, cedula).getResultList();
        return lista;
    }

    @Override
    public List<PropiedadRegistrada> propiedades_registradas(String cedula) {
        Query sqlQuery = em.createNativeQuery("select id,cedula,provincia,canton,distrito,valor,pais,fecha_del_dato from propiedades_registradas(?1)",PropiedadRegistrada.class);
        List<PropiedadRegistrada> lista = sqlQuery.setParameter(1, cedula).getResultList();
        return lista;
    }

    @Override
    public List<HistorialDireccion> historial_direcciones(String cedula) {
        Query sqlQuery = em.createNativeQuery("select id,cedula,tipo,direccion,fecha_del_dato from historial_direcciones(?1)",HistorialDireccion.class);
        List<HistorialDireccion> lista = sqlQuery.setParameter(1, cedula).getResultList();
        return lista;
    }

    @Override
    public List<HistorialJudicial> historial_judicial(String cedula) {
        Query sqlQuery = em.createNativeQuery("select id,cedula,expediente,asunto,oficina,caso,tipo_parte,fecha_caso,estado,pais,fecha_del_dato from historial_judicial(?1)",HistorialJudicial.class);
        List<HistorialJudicial> lista = sqlQuery.setParameter(1, cedula).getResultList();
        return lista;
    }

    @Override
    public List<SociedadAnonima> sociedades_anonimas(String cedula) {
        Query sqlQuery = em.createNativeQuery("select id,cedula,posicion,nombre_sociedad,cedula_juridica,direccion,telefono,fax,verificado,fecha_del_dato from sociedades_anonimas(?1)",SociedadAnonima.class);
        List<SociedadAnonima> lista = sqlQuery.setParameter(1, cedula).getResultList();
        return lista;
    }

    @Override
    public List<ArbolFamiliar> arbol_familiar(String cedula) {
        Query sqlQuery = em.createNativeQuery("select id,cedula,nombre,parentesco,telefonos,fecha_del_dato from arbol_familiar(?1)",ArbolFamiliar.class);
        List<ArbolFamiliar> lista = sqlQuery.setParameter(1, cedula).getResultList();
        return lista;
    }
    
}
