/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.findme.ejb;

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
import javax.ejb.Remote;

/**
 *
 * @author Z420WK
 */
@Remote
public interface FindmeServiceRemote {

    public List<HistorialLaboral> analisis_laboral(String cedula);

    public List<HistorialCorreo> historial_correos(String cedula);

    public List<HistorialTelefono> historial_telefonos(String cedula);

    public List<Vehiculo> consulta_vehiculos(String cedula);

    public List<PropiedadRegistrada> propiedades_registradas(String cedula);

    public List<HistorialDireccion> historial_direcciones(String cedula);

    public List<HistorialJudicial> historial_judicial(String cedula);

    public List<SociedadAnonima> sociedades_anonimas(String cedula);

    public List<ArbolFamiliar> arbol_familiar(String cedula);

}
