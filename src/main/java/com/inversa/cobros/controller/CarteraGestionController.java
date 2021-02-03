/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.controller;

import com.inversa.cobros.ejb.CarteraService;
import com.inversa.cobros.ejb.ContactoService;
import com.inversa.cobros.ejb.TelefonoService;
import com.inversa.cobros.model.Razonmora;
import com.inversa.cobros.model.Subtipificacion;
import com.inversa.cobros.model.TblCartera;
import com.inversa.cobros.model.TblContacto;
import com.inversa.cobros.model.TblGestion;
import com.inversa.cobros.model.TblLlamada;
import com.inversa.cobros.model.TblPromesa;
import com.inversa.cobros.model.TblTelefono;
import com.inversa.cobros.model.TblUsuario;
import com.inversa.cobros.model.Tipificacion;
import com.inversa.cobros.model.Tipotelefono;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

/**
 *
 * @author Z420WK
 */
@Named
@ViewScoped
public class CarteraGestionController implements Serializable {

    @Inject
    private CarteraService ejbCarteraLocal;

    @Inject
    private ContactoService ejbContactoLocal;

    @Inject
    private TelefonoService ejbTelefonoLocal;

    @Inject
    private CarteraController carteraController;

    @Inject
    private TipificacionController tipificacionController;

    private TblGestion gestion;
    private List<TblLlamada> llamadaList;
    private TblLlamada selectedLlamada;
    private List<TblPromesa> promesaList;
    private List<TblTelefono> telefonos;

    private List<TblCartera> carteraList;
    private TblCartera selectedCartera;

    private Calendar fechaHoy;
    private TblUsuario usuario;

    @PostConstruct
    public void init() {

        this.llamadaList = new ArrayList<TblLlamada>();
        this.usuario = (TblUsuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        this.fechaHoy = Calendar.getInstance();
        this.gestion = new TblGestion();

        this.selectedCartera = this.carteraController.getCartera();
        this.setCarteraTOGestion(this.selectedCartera);        

        this.promesaList = new ArrayList<TblPromesa>();
        this.telefono = new TblTelefono();
        this.tipificacionController.setIsDisabledPromesa(true);

        /*
        this.mtoSaldoOperacion = new BigDecimal(BigInteger.ZERO);
        this.mtoDescuentoPromesa = new BigDecimal(BigInteger.ZERO);
        this.mtoSaldoPromesa = new BigDecimal(BigInteger.ZERO);
         */
    }

    public TblGestion getGestion() {
        return gestion;
    }

    public void setGestion(TblGestion gestion) {
        this.gestion = gestion;
    }

    public List<TblLlamada> getLlamadaList() {
        return llamadaList;
    }

    public void setLlamadaList(List<TblLlamada> llamadaList) {
        this.llamadaList = llamadaList;
    }

    public TblLlamada getSelectedLlamada() {
        return selectedLlamada;
    }

    public void setSelectedLlamada(TblLlamada selectedLlamada) {
        this.selectedLlamada = selectedLlamada;
    }

    public List<TblPromesa> getPromesaList() {
        return promesaList;
    }

    public void setPromesaList(List<TblPromesa> promesaList) {
        this.promesaList = promesaList;
    }

    public List<TblTelefono> getTelefonos() {
        return telefonos;
    }

    public void setTelefonos(List<TblTelefono> telefonos) {
        this.telefonos = telefonos;
    }

    public List<TblCartera> getCarteraList() {
        return carteraList;
    }

    public void setCarteraList(List<TblCartera> carteraList) {
        this.carteraList = carteraList;
    }

    public TblCartera getSelectedCartera() {
        return selectedCartera;
    }

    public void setSelectedCartera(TblCartera selectedCartera) {
        this.selectedCartera = selectedCartera;
    }

    /*
    ***************************************************************************
     */
    private TblContacto contacto;
    private TblTelefono telefono;
    private Tipotelefono tipo;
    private List<Tipotelefono> tipoList;

    public void contactoABuscar(String documento) {
        if (documento != null && !documento.trim().equals("")) {
            this.contacto = new TblContacto();
            this.contacto.setCedula(documento);
            this.contacto = this.ejbContactoLocal.findByCedula(this.contacto);
            List<TblTelefono> telefonoList = this.ejbTelefonoLocal.findByContactoEstado(this.contacto.getIdContacto(), "ACT");
            this.contacto.setTblTelefonoList(telefonoList);
            this.telefonos = this.contacto.getTblTelefonoList();

            this.addTelefonoLlamada();
        }
    }

    public void addTelefonoLlamada() {
        if (this.telefonos != null && !this.telefonos.isEmpty()) {
            for (int index = 0; index < this.telefonos.size(); index++) {
                String telefono = this.telefonos.get(index).getTelefono();

                boolean existe = false;// no existe.

                for (int i = 0; i < this.llamadaList.size(); i++) {
                    if (this.llamadaList.get(i).getCallToNumber().equals(telefono)) {
                        existe = true;// existe.
                    }
                }

                if (!existe) {// si no existe lo agrega...
                    TblLlamada llamada = new TblLlamada();
                    llamada.setCallToNumber(telefono);

                    Tipotelefono tt = this.telefonos.get(index).getIdTipotelefono();

                    Tipificacion tipF = new Tipificacion();
                    Subtipificacion subTP = new Subtipificacion();
                    Razonmora rm = new Razonmora();

                    llamada.setIdTipotelefono(tt);

                    subTP.setIdTipificacion(tipF);
                    llamada.setIdTipificacion(tipF);
                    llamada.setIdSubtipificacion(subTP);
                    llamada.setIdrazonmora(rm);

                    this.llamadaList.add(llamada);
                }//existe
            }
        }
    }

    /*
    Seleccciona un registro de la cartera.
    selecciona una operacion.
     */

    public void onRowSelect(SelectEvent<TblCartera> event) {
        TblCartera obj = event.getObject();        
        this.setCarteraTOGestion(obj);
        
        FacesMessage msg = new FacesMessage("Operación seleccionada!", String.valueOf(obj.getNumeroCuenta()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowUnselect(UnselectEvent<TblCartera> event) {
        FacesMessage msg = new FacesMessage("Operación deseleccionada!", String.valueOf(event.getObject().getNumeroCuenta()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    /**
     * 
     * @param objCartera 
     */
    public void setCarteraTOGestion(TblCartera objCartera){
        if (objCartera != null) {
            String codigoCartera = objCartera.getCodigoCartera();
            String codigoGestor = usuario.getCodigoGestor();
            String identificacion = objCartera.getIdentificacion();
            this.contactoABuscar(identificacion);

            // info de la gestion *******************
            this.gestion.setCodigoCartera(codigoCartera);
            this.gestion.setNombre_cartera(objCartera.getIdCliente().getNombre());
            this.gestion.setIdentificacion(identificacion);
            this.gestion.setNombreCliente(objCartera.getNombreCliente());
            this.gestion.setOperacion(objCartera.getNumeroCuenta());

            // Saldo e interese en colones y dolares...
            this.gestion.setCodigoGestor(codigoGestor);
            this.gestion.setFechaGestion(this.fechaHoy.getTime());// fecha Hoy...
            this.gestion.setUsuarioingreso(this.usuario.getUsuario());
            this.gestion.setFechaingreso(this.fechaHoy.getTime());// fecha Hoy...

            //***************************************
            TblCartera obj = new TblCartera();
            obj.setCodigoCartera(codigoCartera);
            obj.setCodigoGestor(codigoGestor);
            obj.setIdentificacion(identificacion);
            this.carteraList = this.ejbCarteraLocal.findByCarteraGestorIdentificacion(obj);
        }
    }

}
