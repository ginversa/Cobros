/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.controller;

import com.inversa.cobros.llamar.Llamada;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Z420WK
 */
@Named
@ViewScoped
public class LlamarController implements Serializable {

    /*    
    Llamada inicial...
    http://192.168.7.201/PBXPortal/llamar.php?ext=118&numero=987356220

    -- despues de llamar...
    http://192.168.7.201/PBXPortal/consultar.php?call_log_id=1607384698.122685
     */
    //Variables que usaremos 
    private static final String URL_LLAMAR = "http://192.168.7.201/PBXPortal/llamar.php?ext=118&numero=987356220";
    private static final String URL_CONSULTAR = "http://192.168.7.201/PBXPortal/consultar.php?call_log_id=1607384698.122685";
    private static final String URL_ESCUCHAR = "http://192.168.7.201/PBXPortal/llamar.php?ext=118&escuchar=1607384698.122685";
    private static final String URL_BAJAR = "http://192.168.7.201/PBXPortal/consultar.php?call_log_id=1607384698.122685&bajar=1";
    private static Client cliente;
    private static WebTarget webTarget;
    private static Llamada llamada;
    private static Invocation.Builder invocationBuilder;
    private static Response response;
    
    
    @PostConstruct
    public void init(){
        cliente = ClientBuilder.newClient();    
    }

    /**
     *
     */
    public void generarLlamada() {
        //Leer una llamada (metodo get)
        webTarget = cliente.target(URL_LLAMAR);
        // get extracted document as JSON
        String jsonExtract = webTarget.request(MediaType.APPLICATION_JSON).get(String.class);
        System.out.println("Generar una llamada: " + jsonExtract);
        
    }


    /**
     * Consultar datos de llamada: Devuelve... fecha de inicio, fin, dialstatus,
     * ext del usuario, número llamado, duración de la llamada.
     */
    public void consultarDatosLlamada() {
        //Leer una llamada (metodo get)
        webTarget = cliente.target(URL_CONSULTAR);
        // get extracted document as JSON
        llamada = webTarget.request(MediaType.APPLICATION_JSON).get(Llamada.class);
        System.out.println("Consultar datos de llamada: " + llamada);
        
    }
    
    /**
     * Escuchar una llamada
     * Genera una llamada para poder escuchar en la extensión XXX la llamada con id YYY 
     */
    public void escucharLlamada(){
        //Leer una llamada (metodo get)
        webTarget = cliente.target(URL_ESCUCHAR);
        // get extracted document as JSON
        llamada = webTarget.request(MediaType.APPLICATION_JSON).get(Llamada.class);
        System.out.println("Consultar datos de llamada: " + llamada);
    
    }
    
    /**
     * Bajar una llamada
     * Descarga el archivo de la grabación de esa llamada siempre y cuando exista.
     */
    public void bajarLlamada(){
        //Leer una llamada (metodo get)
        webTarget = cliente.target(URL_BAJAR);
        // get extracted document as JSON
        llamada = webTarget.request(MediaType.APPLICATION_JSON).get(Llamada.class);
        System.out.println("Consultar datos de llamada: " + llamada);
    }

}
