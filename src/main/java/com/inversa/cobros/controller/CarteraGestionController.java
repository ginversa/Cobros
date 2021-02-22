/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.controller;

import com.inversa.cobros.ejb.CarteraService;
import com.inversa.cobros.ejb.ContactoService;
import com.inversa.cobros.ejb.GestionService;
import com.inversa.cobros.ejb.MonedaService;
import com.inversa.cobros.ejb.PromesaService;
import com.inversa.cobros.ejb.TelefonoService;
import com.inversa.cobros.model.Moneda;
import com.inversa.cobros.model.Razonmora;
import com.inversa.cobros.model.Subtipificacion;
import com.inversa.cobros.model.TblCartera;
import com.inversa.cobros.model.TblCentral;
import com.inversa.cobros.model.TblCliente;
import com.inversa.cobros.model.TblContacto;
import com.inversa.cobros.model.TblCorreo;
import com.inversa.cobros.model.TblGestion;
import com.inversa.cobros.model.TblGestionsaldo;
import com.inversa.cobros.model.TblLlamada;
import com.inversa.cobros.model.TblPrefijoSalida;
import com.inversa.cobros.model.TblPromesa;
import com.inversa.cobros.model.TblTelefono;
import com.inversa.cobros.model.TblUrlLlamada;
import com.inversa.cobros.model.TblUsuario;
import com.inversa.cobros.model.Tipificacion;
import com.inversa.cobros.model.Tipotelefono;
import com.inversa.cobros.util.FechaOperacion;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import org.primefaces.PrimeFaces;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.shaded.json.JSONObject;

/**
 *
 * @author Z420WK
 */
@Named
@ViewScoped
public class CarteraGestionController implements Serializable {

    private static String ip_publica = null; //"192.168.7.201";
    //private static String ip_publica = "190.106.65.237";
    private static String http = null; //"http://";
    //private static String http = "https://";

    // Telefono: prefijos de salida.
    private TblPrefijoSalida prefijoSalidaSelected;
    private List<TblPrefijoSalida> prefijoSalidaList;
    private String directorioCentral;
    private String servicio;
    private String parametro;

    private static String telefonoDefault = "87356220";// 64801981

    private String ext = null;
    private String numeroSalida = null;

    private static Client cliente;
    private static WebTarget webTarget;
    private TblLlamada llamada_En_Proceso;

    @Inject
    private GestionService ejbLocal;

    @Inject
    private CarteraService ejbCarteraLocal;

    @Inject
    private PromesaService ejbPromesaLocal;

    @Inject
    private ContactoService ejbContactoLocal;

    @Inject
    private TelefonoService ejbTelefonoLocal;

    @Inject
    private CarteraController carteraController;

    @Inject
    private TipificacionController tipificacionController;

    @Inject
    private MonedaService ejbMonedaService;

    private TblGestion gestion;
    private List<TblLlamada> llamadaList;
    private TblLlamada selectedLlamada;
    private List<TblPromesa> promesaList;
    private List<TblTelefono> telefonos;

    private List<TblCartera> carteraList;
    private TblCartera selectedCartera;

    private Calendar fechaHoy;
    private TblUsuario usuario;

    // Saldo a cobrar, es requerido si aplica ley de usuara.
    private boolean leyusuraIsRequired;
    private boolean leyusuraDisabled;

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
        this.correoElectronico = new TblCorreo();
        this.tipificacionController.setIsDisabledPromesa(true);

        this.mtoSaldoOperacion = new BigDecimal(BigInteger.ZERO);
        this.mtoDescuentoPromesa = new BigDecimal(BigInteger.ZERO);
        this.mtoSaldoPromesa = new BigDecimal(BigInteger.ZERO);

        this.mtoSaldoOperacionUSD = new BigDecimal(BigInteger.ZERO);
        this.mtoDescuentoPromesaUSD = new BigDecimal(BigInteger.ZERO);
        this.mtoSaldoPromesaUSD = new BigDecimal(BigInteger.ZERO);

        TblCliente cliente = this.selectedCartera.getIdCliente();
        this.prefijoSalidaList = cliente.getTblPrefijoSalidaList();
        if (this.prefijoSalidaList != null && !this.prefijoSalidaList.isEmpty()) {
            //prefijoSalidaSelected = this.prefijoSalidaList.get(0);
        }

        this.leyusuraIsRequired = false;
        this.leyusuraDisabled = true;

    }

    /**
     * informacion para hacer llamada.
     *
     * @return
     */
    private String crearUrlLlamada(String telefono) {

        TblCentral central = this.prefijoSalidaList.get(0).getTblCentral();
        this.http = central.getProtocolo();
        this.ip_publica = central.getIpCentral();
        this.directorioCentral = central.getDirectorio();
        TblUrlLlamada urlLlamar = central.getTblUrlLlamadaList().get(0);// llamar. Buscar servicio para llamar.
        this.servicio = urlLlamar.getServicio();
        this.ext = this.usuario.getExtEnsion();
        this.parametro = urlLlamar.getParametro();
        this.numeroSalida = prefijoSalidaSelected.getPrefijo();
        String URL_LLAMAR = this.http + this.ip_publica + this.directorioCentral + this.servicio + this.ext + this.parametro + this.numeroSalida + telefono;
        System.out.println("URL_LLAMAR: " + URL_LLAMAR);
        return URL_LLAMAR;
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

    public boolean isLeyusuraIsRequired() {
        return leyusuraIsRequired;
    }

    public void setLeyusuraIsRequired(boolean leyusuraIsRequired) {
        this.leyusuraIsRequired = leyusuraIsRequired;
    }

    public boolean isLeyusuraDisabled() {
        return leyusuraDisabled;
    }

    public void setLeyusuraDisabled(boolean leyusuraDisabled) {
        this.leyusuraDisabled = leyusuraDisabled;
    }


    /*
    ***************************************************************************
     */
    private TblContacto contacto;
    private TblTelefono telefono;
    private Tipotelefono tipo;
    private List<Tipotelefono> tipoList;

    private TblCorreo correoElectronico;

    public TblCorreo getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(TblCorreo correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public TblContacto getContacto() {
        return contacto;
    }

    public void setContacto(TblContacto contacto) {
        this.contacto = contacto;
    }

    public TblTelefono getTelefono() {
        return telefono;
    }

    public void setTelefono(TblTelefono telefono) {
        this.telefono = telefono;
    }

    public Tipotelefono getTipo() {
        return tipo;
    }

    public void setTipo(Tipotelefono tipo) {
        this.tipo = tipo;
    }

    /**
     *
     * @param documento
     */
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

    /**
     *
     */
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
    public void setCarteraTOGestion(TblCartera objCartera) {
        if (objCartera != null) {
            String codigoCartera = objCartera.getCodigoCartera();
            String codigoGestor = usuario.getCodigoGestor();
            String identificacion = objCartera.getIdentificacion();
            this.contactoABuscar(identificacion);

            // info de la gestion *******************
            this.gestion.setCodigoCartera(codigoCartera);
            if (objCartera.getIdCliente() != null) {
                this.gestion.setNombre_cartera(objCartera.getIdCliente().getNombre());
            }
            this.gestion.setIdentificacion(identificacion);
            this.gestion.setNombreCliente(objCartera.getNombreCliente());
            this.gestion.setOperacion(objCartera.getNumeroCuenta());

            // Saldo e interese en colones y dolares...
            this.gestion.setCodigoGestor(codigoGestor);
            this.gestion.setFechaGestion(this.fechaHoy.getTime());// fecha Hoy...
            this.gestion.setUsuarioingreso(this.usuario.getUsuario());
            this.gestion.setFechaingreso(this.fechaHoy.getTime());// fecha Hoy...
            this.gestion.setLeyusura(objCartera.getLeyusura());
            this.gestion.setMtosaldocobrar(BigDecimal.ZERO);

            if (this.gestion.getLeyusura() != null && this.gestion.getLeyusura().equals("1")) {
                this.setLeyusuraIsRequired(true);
                this.setLeyusuraDisabled(false);

            } else {
                this.setLeyusuraIsRequired(false);
                this.setLeyusuraDisabled(true);
            }

            //***************************************
            TblCartera obj = new TblCartera();
            obj.setCodigoCartera(codigoCartera);
            obj.setCodigoGestor(codigoGestor);
            obj.setIdentificacion(identificacion);
            this.carteraList = this.ejbCarteraLocal.findByCarteraGestorIdentificacion(obj);
        }
    }

    /**
     * Guarda la informacion de la gestion. Guarda la informacion de las
     * promesas.
     */
    public void finalizarGestion() {
        try {
            String descripcion = this.gestion.getDescripcion();
            System.out.println("descripcion: " + descripcion);
            this.guardarGestion();

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error!", "Error registrando gestión. Error!"));
            System.out.println(e.getMessage());
        }
    }

    /**
     *
     */
    public void guardarGestion() {
        boolean isTrue = guardarGestion(true);
    }

    /**
     *
     * @param isTrue
     * @return
     */
    public boolean guardarGestion(boolean isTrue) {
        List<TblLlamada> llamadaConDatosList = new ArrayList<TblLlamada>();

        try {

            if (this.gestion != null) {

                // validaciones...
                if (this.llamadaList == null) {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso!", "Debe hacer una Llamada!");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                    return false;

                } else {

                    llamadaConDatosList = consultarDatosLlamada();

                    if (llamadaConDatosList == null || llamadaConDatosList.isEmpty() || llamadaConDatosList.size() <= 0) {
                        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso!", "Debe hacer una Llamada!");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                        return false;
                    } else if (llamadaConDatosList != null && !llamadaConDatosList.isEmpty() && llamadaConDatosList.size() > 0) {

                        for (int index = 0; index < llamadaConDatosList.size(); index++) {
                            String telefono = llamadaConDatosList.get(index).getCallToNumber();
                            /*
                            if (llamadaConDatosList.get(index).getIdTipotelefono() == null || llamadaConDatosList.get(index).getIdTipotelefono().getIdTipotelefono() == null) {
                                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso!", "( " + telefono + " ) - " + "Tipo Teléfono requerido!");
                                FacesContext.getCurrentInstance().addMessage(null, msg);
                                return false;
                            }
                             */

                            Tipificacion tipificacion = llamadaConDatosList.get(index).getIdTipificacion();
                            Integer idTipificacion = null;
                            if (tipificacion != null) {
                                idTipificacion = tipificacion.getIdTipificacion();
                            }

                            boolean isNullTipificacion = tipificacion == null ? true : false;
                            boolean isNullIDTipificacion = idTipificacion == null ? true : false;

                            if (isNullTipificacion || isNullIDTipificacion) {
                                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso!", "( " + telefono + " ) - " + "Tipificación requerido!");
                                FacesContext.getCurrentInstance().addMessage(null, msg);
                                return false;
                            }

                            if (llamadaConDatosList.get(index).getIdSubtipificacion() == null || llamadaConDatosList.get(index).getIdSubtipificacion().getIdSubtipificacion() == null) {
                                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso!", "( " + telefono + " ) - " + "Sub-Tipificación requerido!");
                                FacesContext.getCurrentInstance().addMessage(null, msg);
                                return false;
                            }

                            /*
                            Razon de mora.
                            No es requerido: si la tipificacion es;
                            --- Promesa de pago
                            --- Contacto sin promesa
                            --- No Contesta
                            --- Mensaje Familiar
                             */
                            if (!isNullIDTipificacion) {
                                boolean isPP = idTipificacion.equals(1) ? true : false;
                                boolean isCSP = idTipificacion.equals(7) ? true : false;
                                boolean isNCO = idTipificacion.equals(6) ? true : false;
                                boolean isMFA = idTipificacion.equals(4) ? true : false;
                                if (!isPP && !isCSP && !isNCO && !isMFA) {
                                    if (llamadaConDatosList.get(index).getIdrazonmora() == null || llamadaConDatosList.get(index).getIdrazonmora().getIdrazonmora() == null) {
                                        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso!", "( " + telefono + " ) - " + "Razón Mora requerido!");
                                        FacesContext.getCurrentInstance().addMessage(null, msg);
                                        return false;
                                    }
                                }

                                /*
                                Promesas. requerido: si la tipificacion es;
                                --- Promesa de pago
                                 */
                                if (isPP) {
                                    if (this.promesaList == null || this.promesaList.isEmpty() || this.promesaList.size() <= 0) {
                                        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso!", "( " + telefono + " ) - " + "Debe registrar una PROMESA. Requerido!");
                                        FacesContext.getCurrentInstance().addMessage(null, msg);
                                        return false;
                                    }
                                }// Promesa de pago                                
                            }

                        }//for

                        // agregar promesas...
                        for (int index = 0; index < llamadaConDatosList.size(); index++) {
                            if (this.promesaList != null && !this.promesaList.isEmpty() && this.promesaList.size() > 0) {
                                List<TblPromesa> promesas = new ArrayList<TblPromesa>();
                                for (int i = 0; i < this.promesaList.size(); i++) {
                                    if (llamadaConDatosList.get(index).getCallToNumber().equals(this.promesaList.get(i).getTelefono())) {
                                        this.promesaList.get(i).setIdLlamada(llamadaConDatosList.get(index));
                                        this.promesaList.get(i).setIdGestion(this.gestion);

                                        boolean hasOperation = this.promesaList.get(i).getOperacion() != null && !this.promesaList.get(i).getOperacion().trim().equals("") ? true : false;
                                        boolean hasMtopago = this.promesaList.get(i).getMtopago() != null && !this.promesaList.get(i).getMtopago().equals(0) ? true : false;

                                        if (hasOperation && hasMtopago) {
                                            promesas.add(this.promesaList.get(i));
                                        }
                                    }
                                }

                                // agregar las promesas...
                                if (promesas != null && !promesas.isEmpty() && promesas.size() > 0) {
                                    llamadaConDatosList.get(index).setTblPromesaList(promesas);
                                }
                            }
                        }// agregar promesas...

                        // agregar llamadas...
                        this.gestion.setTblLlamadaList(llamadaConDatosList);
                    }
                }

                if (this.gestion.getIdGestion() == null) {

                    // agregar usuario sesion...
                    if (usuario != null) {
                        this.gestion.setUsuarioingreso(usuario.getUsuario());// usuario que esta registrando la gestion
                    }

                    // saldos de la gestion
                    List<TblGestionsaldo> tblGestionsaldoList = this.agregarSaldos();
                    this.gestion.setTblGestionsaldoList(tblGestionsaldoList);

                    this.gestion.setEstado("ING");
                    this.gestion.setFechaingreso(this.fechaHoy.getTime());
                    this.ejbLocal.insert(this.gestion);
                    this.actualizarTelefonoContacto(llamadaConDatosList);
                    this.cargarGestionActual(this.gestion);
                    System.out.println("guardarGestion --> this.gestion.getIdGestion(): " + this.gestion.getIdGestion());
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso!", "Gestión Registrada. Correcto!"));

                } else {// actualizar gestion...

                    if (usuario != null) {// agregar usuario sesion...
                        this.gestion.setUsuariomodifico(usuario.getUsuario());// usuario que esta registrando la gestion
                    }

                    // saldos de la gestion
                    List<TblGestionsaldo> tblGestionsaldoList = this.agregarSaldos();
                    this.gestion.setTblGestionsaldoList(tblGestionsaldoList);

                    this.gestion.setFechamodifico(this.fechaHoy.getTime());
                    this.ejbLocal.update(this.gestion);
                    this.actualizarTelefonoContacto(llamadaConDatosList);
                    this.cargarGestionActual(this.gestion);
                    System.out.println("guardarGestion --> this.gestion.getIdGestion(): " + this.gestion.getIdGestion());
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso!", "Gestión Actulizar. Correcto!"));
                }

            }// this.gestion != null

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error!", "Error registrando gestión. Error!"));
            System.out.println(e.getMessage());
            return false;
        }

        return true;

    }// guardarGestion

    /**
     * Consultar datos de llamada: Devuelve... fecha de inicio, fin, dialstatus,
     * ext del usuario, número llamado, duración de la llamada.
     */
    public List<TblLlamada> consultarDatosLlamada() {

        List<TblLlamada> llamadaConDatosList = new ArrayList<TblLlamada>();

        if (this.llamadaList != null && !this.llamadaList.isEmpty()) {
            for (int index = 0; index < this.llamadaList.size(); index++) {
                TblLlamada llamadaConDatos = this.llamadaList.get(index);
                String callLogId = llamadaConDatos.getCallLogId();
                if (callLogId != null && !callLogId.trim().equals("")) {
                    System.out.println("callLogId: " + callLogId);

                    String errorCentral = null;
                    String[] erroresCentralTel = callLogId.split("#");
                    if (erroresCentralTel != null && erroresCentralTel.length > 1) {
                        errorCentral = erroresCentralTel[1];
                        System.out.println("Error Central: " + errorCentral);
                    } else {
                        System.out.println("Error Central: " + erroresCentralTel[0]);
                    }

                    if (errorCentral != null && !errorCentral.trim().equals("")) {

                        switch (errorCentral.trim()) {
                            case "0":
                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error!", "0 - No está configurado el servicio!"));
                                break;
                            case "1":
                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error!", "1 - El IP no está autorizado!"));
                                break;
                            case "001":
                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error!", "001 - No indica la extensión!"));
                                break;
                            case "002":
                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error!", "002 - El número a marcar no es correcto!"));
                                break;
                            case "004":
                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error!", "004 - La extensión no es numérica!"));
                                break;
                            case "008":
                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error!", "008 - La extensión no existe ni está como activa!"));
                                break;
                            case "016":
                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error!", "016 - Falla en generar la llamada local inicial!"));
                                break;
                            case "032":
                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error!", "032 - No logra recuperar el ID de la llamada!"));
                                break;
                            case "064":
                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error!", "064 - si se envía un ID de llamada que no sea numérico para el caso de escucharla!"));
                                break;
                            case "128":
                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error!", "128 - si no se especifica una extensión activa ni existe el contexto: context_qrm!"));
                                break;
                            default:
                                break;
                        }

                    } else {

                        String URL_CONSULTAR = this.http + this.ip_publica + "/PBXPortal/consultar.php?call_log_id=" + callLogId;

                        cliente = ClientBuilder.newClient();
                        //Leer una llamada (metodo get)
                        webTarget = cliente.target(URL_CONSULTAR);
                        // get extracted document as JSON

                        String jsonString = webTarget.request(MediaType.APPLICATION_JSON).get(String.class);
                        if (jsonString != null && !jsonString.trim().equals("")) {
                            JSONObject obj = new JSONObject(jsonString);
                            if (obj != null && !obj.isEmpty() && obj.length() > 0) {

                                boolean isTrue_date_ini = !obj.isNull("date_ini") ? true : false;
                                if (isTrue_date_ini) {
                                    String date_ini = obj.getString("date_ini");
                                    llamadaConDatos.setDateIni(Timestamp.valueOf(date_ini));
                                }

                                boolean isTrue_date_end = !obj.isNull("date_end") ? true : false;
                                if (isTrue_date_end) {
                                    String date_end = obj.getString("date_end");
                                    System.out.println("date_end: " + date_end);
                                    llamadaConDatos.setDateEnd(Timestamp.valueOf(date_end));
                                }

                                if (!obj.isNull("call_from_number")) {
                                    String call_from_number = obj.getString("call_from_number");
                                    llamadaConDatos.setCallFromNumber(call_from_number);
                                }
                                /*
                            if (!obj.isNull("call_to_number")) {
                                String call_to_number = obj.getString("call_to_number");
                                llamadaConDatos.setCallToNumber(call_to_number);
                            }
                                 */
                                if (!obj.isNull("dialstatus")) {
                                    String dialstatus = obj.getString("dialstatus");
                                    llamadaConDatos.setDialstatus(dialstatus);
                                }

                                if (!obj.isNull("conversation_length")) {
                                    String conversation_length = obj.getString("conversation_length");
                                    if (conversation_length != null && !conversation_length.trim().equals("")) {
                                        llamadaConDatos.setConversationLength(Integer.valueOf(conversation_length));
                                    } else {
                                        llamadaConDatos.setConversationLength(Integer.valueOf(0));
                                    }
                                }

                                llamadaConDatos.setIdGestion(this.gestion);
                                llamadaConDatos.setEstado("ING");
                                llamadaConDatos.setUsuarioingreso(this.usuario.getUsuario());
                                llamadaConDatos.setFechaingreso(this.fechaHoy.getTime());

                                if (llamadaConDatos.getIdTipotelefono() != null && llamadaConDatos.getIdTipotelefono().getIdTipotelefono() == null) {
                                    llamadaConDatos.setIdTipotelefono(null);
                                }

                                if (llamadaConDatos.getIdrazonmora() != null && llamadaConDatos.getIdrazonmora().getIdrazonmora() == null) {
                                    llamadaConDatos.setIdrazonmora(null);
                                }

                                FechaOperacion fo = new FechaOperacion();
                                Date callLength = fo.restarFechar(llamadaConDatos.getDateIni(), llamadaConDatos.getDateEnd());
                                llamadaConDatos.setCallLength(callLength);

                                llamadaConDatosList.add(llamadaConDatos);

                            }//if                       
                        }

                    }

                }
            }
        }
        return llamadaConDatosList;
    }// consultarDatosLlamada

    /**
     *
     * @param llamadaConDatosList
     */
    private void actualizarTelefonoContacto(List<TblLlamada> llamadaConDatosList) {
        boolean isTrueFlag = false;
        if (llamadaConDatosList != null && !llamadaConDatosList.isEmpty() && llamadaConDatosList.size() > 0) {
            for (int index = 0; index < llamadaConDatosList.size(); index++) {
                if (llamadaConDatosList.get(index).getIdTipotelefono() != null) {
                    String telefono = llamadaConDatosList.get(index).getCallToNumber();
                    if (this.contacto.getTblTelefonoList() != null && this.contacto.getTblTelefonoList().size() > 0) {
                        for (int indexTel = 0; indexTel < this.contacto.getTblTelefonoList().size(); indexTel++) {
                            if (this.contacto.getTblTelefonoList().get(indexTel).getTelefono().equals(telefono)) {
                                Tipotelefono tipoTel = llamadaConDatosList.get(index).getIdTipotelefono();
                                tipoTel.setUsuariomodifico(this.usuario.getUsuario());
                                tipoTel.setFechamodifico(this.fechaHoy.getTime());
                                this.contacto.getTblTelefonoList().get(indexTel).setIdTipotelefono(tipoTel);
                                isTrueFlag = true;
                            }
                        }
                    }
                }
            }
        }

        if (isTrueFlag) {
            this.contacto.setUsuariomodifico(this.usuario.getUsuario());
            this.contacto.setFechamodifico(this.fechaHoy.getTime());
            this.ejbContactoLocal.update(this.contacto);
        }
    }

    /**
     *
     * @param gestion
     */
    private void cargarGestionActual(TblGestion gestion) {
        if (gestion.getIdGestion() != null) {
            this.gestion = this.ejbLocal.findById(gestion);
            List<TblLlamada> llamadaConIdList = this.gestion.getTblLlamadaList();
            for (int index1 = 0; index1 < llamadaConIdList.size(); index1++) {
                TblLlamada llamadaConId = llamadaConIdList.get(index1);
                for (int index2 = 0; index2 < this.llamadaList.size(); index2++) {
                    TblLlamada llamadaSinId = this.llamadaList.get(index2);
                    if (llamadaConId.getCallToNumber().equals(llamadaSinId.getCallToNumber()) && llamadaConId.getCallLogId().equals(llamadaSinId.getCallLogId())) {
                        this.llamadaList.get(index2).setIdLlamada(llamadaConId.getIdLlamada());
                        List<TblPromesa> promesaConIdList = llamadaConId.getTblPromesaList();
                        if (promesaConIdList != null && !promesaConIdList.isEmpty() && promesaConIdList.size() > 0) {
                            for (int index3 = 0; index3 < promesaConIdList.size(); index3++) {
                                TblPromesa promesaConId = promesaConIdList.get(index3);
                                String operacion = promesaConId.getOperacion();
                                String telefono = promesaConId.getTelefono();
                                Date fechaPago = promesaConId.getFechaPago();
                                BigDecimal mtopago = promesaConId.getMtopago();

                                for (int index4 = 0; index4 < this.promesaList.size(); index4++) {
                                    TblPromesa promesaSinId = this.promesaList.get(index4);
                                    String operacionSinId = promesaSinId.getOperacion();
                                    String telefonoSinId = promesaSinId.getTelefono();
                                    Date fechaPagoSinId = promesaSinId.getFechaPago();
                                    BigDecimal mtopagoSinId = promesaSinId.getMtopago();

                                    boolean isTrueOperacion = operacion.equals(operacionSinId);
                                    boolean isTrueTelefono = telefono.equals(telefonoSinId);
                                    boolean isTrueFechaPago = fechaPago.equals(fechaPagoSinId);
                                    boolean isTrueMtopago = mtopago.equals(mtopagoSinId);

                                    if (isTrueOperacion && isTrueTelefono && isTrueFechaPago && isTrueMtopago) {
                                        this.promesaList.get(index4).setIdPromesa(promesaConId.getIdPromesa());
                                        this.promesaList.get(index4).setIdLlamada(promesaConId.getIdLlamada());
                                        this.promesaList.get(index4).setIdGestion(promesaConId.getIdGestion());
                                    }
                                }// index4
                            }//index3
                        }
                    }
                }// index2
            }// index1
        }
    }

    /*
    ***************************************************************************
    ***************************************************************************
    **************************** Llamada **************************************
    ***************************************************************************
    ***************************************************************************
     */
    /**
     *
     * @param event
     */
    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();

        if (oldValue != null) {
            if (newValue != null && !newValue.equals(oldValue)) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        }
    }

    /**
     *
     * @param callToNumber
     */
    public void generarLlamada(TblLlamada callToNumber) {

        if (prefijoSalidaSelected == null) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso!", "Debe seleccionar Prefijo Salida!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {

            callToNumber.setIdLlamada(null);
            System.out.println("Numero Seleccionado callToNumber: " + callToNumber.getCallToNumber());

            if (callToNumber != null && callToNumber.getCallToNumber() != null && !callToNumber.getCallToNumber().trim().equals("")) {
                //this.selectedLlamada.setIdLlamada(null);
                this.selectedLlamada = callToNumber;// llamada seleccionada...
                String telefono = callToNumber.getCallToNumber();
                
                if (this.validarURL()) {

                    String URL_LLAMAR = this.crearUrlLlamada(this.telefonoDefault);

                    cliente = ClientBuilder.newClient();

                    //Leer una llamada (metodo get)
                    webTarget = cliente.target(URL_LLAMAR);
                    // get extracted document as JSON
                    String jsonExtract = webTarget.request(MediaType.APPLICATION_JSON).get(String.class);
                    System.out.println("Generar una llamada: " + jsonExtract);

                    String errorCentral = null;
                    String[] erroresCentralTel = jsonExtract.split("#");
                    if (erroresCentralTel != null && erroresCentralTel.length > 1) {
                        errorCentral = erroresCentralTel[1];
                        System.out.println("Error Central: " + errorCentral);
                    } else {
                        System.out.println("Error Central: " + erroresCentralTel[0]);
                    }

                    if (errorCentral != null && !errorCentral.trim().equals("")) {

                        switch (errorCentral.trim()) {
                            case "0":
                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error!", "0 - No está configurado el servicio!"));
                                break;
                            case "1":
                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error!", "1 - El IP no está autorizado!"));
                                break;
                            case "001":
                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error!", "001 - No indica la extensión!"));
                                break;
                            case "002":
                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error!", "002 - El número a marcar no es correcto!"));
                                break;
                            case "004":
                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error!", "004 - La extensión no es numérica!"));
                                break;
                            case "008":
                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error!", "008 - La extensión no existe ni está como activa!"));
                                break;
                            case "016":
                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error!", "016 - Falla en generar la llamada local inicial!"));
                                break;
                            case "032":
                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error!", "032 - No logra recuperar el ID de la llamada!"));
                                break;
                            case "064":
                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error!", "064 - si se envía un ID de llamada que no sea numérico para el caso de escucharla!"));
                                break;
                            case "128":
                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error!", "128 - si no se especifica una extensión activa ni existe el contexto: context_qrm!"));
                                break;
                            default:
                                break;
                        }

                    } else {
                        if (this.llamadaList != null) {
                            for (int index = 0; index < this.llamadaList.size(); index++) {
                                if (this.llamadaList.get(index).getCallToNumber().equals(telefono)) {
                                    this.llamadaList.get(index).setCallLogId(jsonExtract);
                                }
                            }
                        }
                    }

                }

            }// if

        }

        this.selectedLlamada = null;
    }

    /*
    ***************************************************************************    
    **************************** Agregar, Eliminar Telefono *******************
    ***************************************************************************    
     */
    /**
     *
     * @param telefono
     */
    public void deleteTelefono(String telefono) {
        try {
            if (telefono != null && !telefono.trim().equals("")) {
                for (int index = 0; index < this.contacto.getTblTelefonoList().size(); index++) {
                    if (this.contacto.getTblTelefonoList().get(index).getTelefono().equals(telefono)) {
                        this.contacto.getTblTelefonoList().get(index).setEstado("INA");
                        this.contacto.getTblTelefonoList().get(index).setUsuariomodifico(this.usuario.getUsuario());
                        this.contacto.getTblTelefonoList().get(index).setFechamodifico(this.fechaHoy.getTime());
                    }
                }

                this.ejbContactoLocal.update(this.contacto);
                List<TblTelefono> telefonoList = this.ejbTelefonoLocal.findByContactoEstado(this.contacto.getIdContacto(), "ACT");
                this.contacto.setTblTelefonoList(telefonoList);
                this.telefonos = this.contacto.getTblTelefonoList();
                this.addTelefonoLlamada();

                for (int index = 0; index < this.llamadaList.size(); index++) {
                    if (this.llamadaList.get(index).getCallToNumber().equals(telefono)) {
                        this.llamadaList.remove(index);
                    }
                }

                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso!", "Teléfono Eliminado. Correcto!"));
                PrimeFaces.current().ajax().update("formGestion:idTabView:tblTelefono", "formGestion:messages");

            }

        } catch (NumberFormatException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error!", "Error eliminando Teléfono. Error!"));
            System.out.println(e.getMessage());
        }
    }

    /**
     *
     */
    public void deleteTelefono() {
        if (this.selectedLlamada != null) {
            String telefono = this.selectedLlamada.getCallToNumber();
            if (telefono != null && !telefono.trim().equals("")) {
                this.deleteTelefono(telefono);
            }
        }
    }

    /**
     *
     */
    public void addTelefono() {
        try {

            TblTelefono objT = new TblTelefono();
            objT.setTelefono(this.telefono.getTelefono());

            this.contacto = this.ejbContactoLocal.findById(this.contacto);

            objT.setIdContacto(this.contacto);
            objT.setIdTipotelefono(this.tipo);
            objT.setRanking(Integer.valueOf("0"));
            objT.setUsuarioingreso(this.usuario.getUsuario());
            objT.setFechaingreso(this.fechaHoy.getTime());
            objT.setEstado("ACT");

            this.contacto.getTblTelefonoList().add(objT);
            this.contacto.setFechamodifico(this.fechaHoy.getTime());
            this.contacto.setUsuariomodifico(this.usuario.getUsuario());
            this.ejbContactoLocal.update(this.contacto);
            List<TblTelefono> telefonoList = this.ejbTelefonoLocal.findByContactoEstado(this.contacto.getIdContacto(), "ACT");
            this.contacto.setTblTelefonoList(telefonoList);
            this.telefonos = this.contacto.getTblTelefonoList();
            this.addTelefonoLlamada();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso!", "Teléfono Registrado. Correcto!"));
            PrimeFaces.current().executeScript("PF('manageTelefonoDialog').hide()");
            PrimeFaces.current().ajax().update("formGestion:messages", "formGestion:idTabView:tblTelefono");

            this.telefono.setTelefono("");
            this.tipo = null;

        } catch (NumberFormatException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error!", "Error registrando Teléfono. Error!"));
            System.out.println(e.getMessage());
        }
    }

    /**
     *
     */
    public void addCorreoElectronico() {
        try {

            System.out.println("addCorreoElectronico ==> " + this.correoElectronico.getCorreo());

            TblCorreo objT = new TblCorreo();
            objT.setCorreo(this.correoElectronico.getCorreo());

            this.contacto = this.ejbContactoLocal.findById(this.contacto);

            objT.setIdContacto(this.contacto);
            objT.setRanking(Integer.valueOf("0"));
            objT.setUsuarioingreso(this.usuario.getUsuario());
            objT.setFechaingreso(this.fechaHoy.getTime());
            objT.setEstado("ACT");

            this.contacto.getTblCorreoList().add(objT);
            this.contacto.setFechamodifico(this.fechaHoy.getTime());
            this.contacto.setUsuariomodifico(this.usuario.getUsuario());
            this.ejbContactoLocal.update(this.contacto);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso!", "Correo electrónico Registrado. Correcto!"));
            PrimeFaces.current().executeScript("PF('manageCorreoDialog').hide()");
            PrimeFaces.current().ajax().update("formGestion:messages");

            this.correoElectronico.setCorreo("");

        } catch (NumberFormatException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error!", "Error registrando Correo electrónico. Error!"));
            System.out.println(e.getMessage());
        }
    }

    /*
    ***************************************************************************    
    ********************** Promesas, Arreglo de Pago **************************
    ***************************************************************************    
     */
    private TblPromesa selectedPromesa;
    private String clienteOperacion;
    private BigDecimal mtoSaldoOperacion;
    private String tipoDescuentoPromesa;
    private BigDecimal mtoDescuentoPromesa;
    private BigDecimal mtoSaldoPromesa;
    private Date fechaPagoPromesa;
    private String cuotas;

    public TblPromesa getSelectedPromesa() {
        return selectedPromesa;
    }

    public void setSelectedPromesa(TblPromesa selectedPromesa) {
        this.selectedPromesa = selectedPromesa;
    }

    public String getClienteOperacion() {
        return clienteOperacion;
    }

    public void setClienteOperacion(String clienteOperacion) {
        this.clienteOperacion = clienteOperacion;
    }

    public BigDecimal getMtoSaldoOperacion() {
        return mtoSaldoOperacion;
    }

    public void setMtoSaldoOperacion(BigDecimal mtoSaldoOperacion) {
        this.mtoSaldoOperacion = mtoSaldoOperacion;
    }

    public String getTipoDescuentoPromesa() {
        return tipoDescuentoPromesa;
    }

    public void setTipoDescuentoPromesa(String tipoDescuentoPromesa) {
        this.tipoDescuentoPromesa = tipoDescuentoPromesa;
    }

    public BigDecimal getMtoDescuentoPromesa() {
        return mtoDescuentoPromesa;
    }

    public void setMtoDescuentoPromesa(BigDecimal mtoDescuentoPromesa) {
        this.mtoDescuentoPromesa = mtoDescuentoPromesa;
    }

    public BigDecimal getMtoSaldoPromesa() {
        return mtoSaldoPromesa;
    }

    public void setMtoSaldoPromesa(BigDecimal mtoSaldoPromesa) {
        this.mtoSaldoPromesa = mtoSaldoPromesa;
    }

    public Date getFechaPagoPromesa() {
        return fechaPagoPromesa;
    }

    public void setFechaPagoPromesa(Date fechaPagoPromesa) {
        this.fechaPagoPromesa = fechaPagoPromesa;
    }

    public String getCuotas() {
        return cuotas;
    }

    public void setCuotas(String cuotas) {
        this.cuotas = cuotas;
    }

    public TblPrefijoSalida getPrefijoSalidaSelected() {
        return prefijoSalidaSelected;
    }

    public void setPrefijoSalidaSelected(TblPrefijoSalida prefijoSalidaSelected) {
        this.prefijoSalidaSelected = prefijoSalidaSelected;
    }

    public List<TblPrefijoSalida> getPrefijoSalidaList() {
        return prefijoSalidaList;
    }

    public void setPrefijoSalidaList(List<TblPrefijoSalida> prefijoSalidaList) {
        this.prefijoSalidaList = prefijoSalidaList;
    }

    /**
     *
     */
    public void onOperacionPromesaChange() {

        String operacion = this.gestion.getOperacion();
        String leyUsuara = this.gestion.getLeyusura();
        BigDecimal mtosaldocobrar = this.gestion.getMtosaldocobrar();

        if (this.clienteOperacion != null && !this.clienteOperacion.trim().equals("")) {

            if (operacion != null && !operacion.trim().equals("") && this.clienteOperacion.equals(operacion) && leyUsuara.equals("1")) {
                this.mtoSaldoOperacion = mtosaldocobrar;

            } else if (this.carteraList != null && !this.carteraList.isEmpty() && this.carteraList.size() > 0) {
                for (int index = 0; index < this.carteraList.size(); index++) {
                    if (this.carteraList.get(index).getNumeroCuenta().equals(this.clienteOperacion)) {
                        this.mtoSaldoOperacion = this.carteraList.get(index).getSaldoColones();
                    } //if
                } //for
            } //if
        } //if
    }

    /**
     *
     */
    public void calcNuevoMontoSegunDescuento() {
        BigDecimal cien = new BigDecimal("100");
        BigDecimal newSaldo = new BigDecimal(BigInteger.ZERO);
        BigDecimal mtoSaldo = this.mtoSaldoOperacion;
        BigDecimal mtoPort = this.mtoDescuentoPromesa;

        if (this.tipoDescuentoPromesa.equals("FIJ")) {
            newSaldo = mtoSaldo.subtract(mtoPort);

        } else {
            BigDecimal porcentage = mtoSaldo.multiply(mtoPort).divide(cien);
            newSaldo = mtoSaldo.subtract(porcentage);
        }

        this.mtoSaldoPromesa = newSaldo;
    }

    /*
*******************************************************************************
Arreglo de Pago    
*******************************************************************************    
     */
    /**
     *
     * @param codigoMoneda
     */
    public void agregarCancelacionTotal(String codigoMoneda) {
        if (this.selectedLlamada != null) {
            Moneda objMoneda = new Moneda();
            objMoneda.setCodigo(codigoMoneda);
            objMoneda = this.ejbMonedaService.findByCodigo(objMoneda);

            if (this.validArregloPago(codigoMoneda)) {
                TblPromesa promesa = new TblPromesa();

                if (this.usuario != null) {
                    promesa.setUsuarioingreso(this.usuario.getUsuario());
                }

                promesa.setIdGestion(this.gestion);
                promesa.setIdLlamada(this.llamada_En_Proceso);
                promesa.setOperacion(this.clienteOperacion);
                promesa.setTelefono(this.selectedLlamada.getCallToNumber());
                promesa.setFechaPago(this.fechaPagoPromesa);
                promesa.setEstado("SEG"); // Seguimiento
                promesa.setTipodescuento(this.tipoDescuentoPromesa); // Tipo Descuento: Monto Fijo o Porcentaje.
                promesa.setTipoarreglopago("CAT");//CAT = Cancelacion Total.
                promesa.setFechaingreso(this.fechaHoy.getTime());
                promesa.setIdMoneda(objMoneda);

                if (codigoMoneda.equals("CRC")) {
                    promesa.setMtopago(this.mtoSaldoPromesa);
                    promesa.setMtoporcentaje(this.mtoDescuentoPromesa); // Monto o %

                } else if (codigoMoneda.equals("USD")) {
                    promesa.setMtopago(this.mtoSaldoPromesaUSD);
                    promesa.setMtoporcentaje(this.mtoDescuentoPromesaUSD); // Monto o %
                }

                // borra las promesas...
                //this.deleteByOperacionAndArregloPago(this.clienteOperacion, "CAT", codigoMoneda);
                this.deleteArregloPago(this.clienteOperacion, codigoMoneda);

                //if (this.existOneCTC(promesa) && this.existOneREF(promesa) && this.validOnlyOneCAT(promesa) && this.existOnePAP(promesa)) {
                this.promesaList.add(promesa);
                FacesMessage msg = new FacesMessage("Promesa Agregada: ", promesa.getTelefono());
                FacesContext.getCurrentInstance().addMessage(null, msg);
                //}

            }

        } else {
            FacesMessage msg = new FacesMessage("Promesa NO Agregada!: ", null);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    /**
     *
     * @param codigoMoneda
     */
    public void agregarCancelacionTotalPorCuotas(String codigoMoneda) {
        if (this.selectedLlamada != null) {
            Moneda objMoneda = new Moneda();
            objMoneda.setCodigo(codigoMoneda);
            objMoneda = this.ejbMonedaService.findByCodigo(objMoneda);

            if (this.validArregloPago(codigoMoneda) && this.validCancelacionTotalPorCuotas()) {

                Calendar fechaInicial = Calendar.getInstance();
                fechaInicial.setTime(this.fechaPagoPromesa);

                BigDecimal coutasInt = new BigDecimal(this.cuotas);

                BigDecimal saldo = BigDecimal.ZERO;
                if (codigoMoneda.equals("CRC")) {
                    saldo = this.mtoSaldoPromesa.divide(coutasInt, 6, RoundingMode.HALF_UP);
                } else if (codigoMoneda.equals("USD")) {
                    saldo = this.mtoSaldoPromesaUSD.divide(coutasInt, 6, RoundingMode.HALF_UP);
                }

                // borra las promesas...
                this.deleteArregloPago(this.clienteOperacion, codigoMoneda);

                for (int count = 0; count < coutasInt.intValue(); count++) {
                    TblPromesa promesa = new TblPromesa();

                    if (this.usuario != null) {
                        promesa.setUsuarioingreso(this.usuario.getUsuario());
                    }

                    promesa.setIdGestion(this.gestion);
                    promesa.setIdLlamada(this.llamada_En_Proceso);
                    promesa.setOperacion(this.clienteOperacion);
                    promesa.setTelefono(this.selectedLlamada.getCallToNumber());
                    promesa.setFechaPago(fechaInicial.getTime());
                    fechaInicial.set(Calendar.MONTH, fechaInicial.get(Calendar.MONTH) + 1);
                    promesa.setMtopago(saldo);
                    promesa.setIdMoneda(objMoneda);
                    promesa.setEstado("SEG"); // Seguimiento                    
                    promesa.setTipodescuento(this.tipoDescuentoPromesa); // Tipo Descuento: Monto Fijo o Porcentaje.

                    if (codigoMoneda.equals("CRC")) {
                        promesa.setMtoporcentaje(this.mtoDescuentoPromesa); // Monto o %
                    } else if (codigoMoneda.equals("USD")) {
                        promesa.setMtoporcentaje(this.mtoDescuentoPromesaUSD); // Monto o %
                    }

                    promesa.setTipoarreglopago("CTC");//CTC = Cancelacion Total por Cuotas.
                    promesa.setFechaingreso(this.fechaHoy.getTime());

                    //if (this.existOneCAT(promesa) && this.existOneREF(promesa) && this.validOnlyThreeCTC(promesa) && this.existOnePAP(promesa)) {
                    this.promesaList.add(promesa);
                    FacesMessage msg = new FacesMessage("Promesa Agregada: ", promesa.getTelefono());
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                    //}
                }
            }

        } else {
            FacesMessage msg = new FacesMessage("Promesa NO Agregada!: ", null);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    /**
     *
     * @param codigoMoneda
     */
    public void calcSaldoRestanteREF(String codigoMoneda) {
        BigDecimal saldoRestante = new BigDecimal(BigInteger.ZERO);
        BigDecimal mtoSaldo = new BigDecimal(BigInteger.ZERO);
        BigDecimal primerTracto = new BigDecimal(BigInteger.ZERO);

        if (codigoMoneda.equals("CRC")) {
            mtoSaldo = this.mtoSaldoOperacion;
            primerTracto = this.mtoDescuentoPromesa;
        } else if (codigoMoneda.equals("USD")) {
            mtoSaldo = this.mtoSaldoOperacionUSD;
            primerTracto = this.mtoDescuentoPromesaUSD;
        }

        if (mtoSaldo != null && primerTracto != null) {
            if (mtoSaldo.compareTo(BigDecimal.ZERO) == 1) {// mayor que cero.
                if (primerTracto.compareTo(BigDecimal.ZERO) == 1) {// mayor que cero.
                    saldoRestante = mtoSaldo.subtract(primerTracto);
                } else {
                    saldoRestante = mtoSaldo;
                }
            }
        }

        if (codigoMoneda.equals("CRC")) {
            this.mtoSaldoPromesa = saldoRestante;
        } else if (codigoMoneda.equals("USD")) {
            this.mtoSaldoPromesaUSD = saldoRestante;
        }
    }

    /**
     *
     * @param codigoMoneda
     */
    public void agregarRefinanciamiento(String codigoMoneda) {
        if (this.selectedLlamada != null) {
            Moneda objMoneda = new Moneda();
            objMoneda.setCodigo(codigoMoneda);
            objMoneda = this.ejbMonedaService.findByCodigo(objMoneda);
            if (this.validRefinanciamiento(codigoMoneda)) {

                Calendar fechaInicial = Calendar.getInstance();
                fechaInicial.setTime(this.fechaPagoPromesa);

                BigDecimal coutasInt = new BigDecimal(this.cuotas);
                BigDecimal coutasIntLessOne = coutasInt.subtract(BigDecimal.ONE);// restar una cuota.

                BigDecimal primerTracto = BigDecimal.ZERO;// primer tracto
                BigDecimal saldo = BigDecimal.ZERO;// saldo restante
                if (codigoMoneda.equals("CRC")) {
                    primerTracto = this.mtoDescuentoPromesa;// primer tracto
                    saldo = this.mtoSaldoPromesa.divide(coutasIntLessOne, 6, RoundingMode.HALF_UP);// saldo restante
                } else if (codigoMoneda.equals("USD")) {
                    primerTracto = this.mtoDescuentoPromesaUSD;// primer tracto
                    saldo = this.mtoSaldoPromesaUSD.divide(coutasIntLessOne, 6, RoundingMode.HALF_UP);// saldo restante
                }

                // borra las promesas...
                //this.deleteByOperacionAndArregloPago(this.clienteOperacion, "REF", codigoMoneda);
                this.deleteArregloPago(this.clienteOperacion, codigoMoneda);

                for (int count = 0; count < coutasInt.intValue(); count++) {
                    TblPromesa promesa = new TblPromesa();

                    if (this.usuario != null) {
                        promesa.setUsuarioingreso(this.usuario.getUsuario());
                    }

                    promesa.setIdGestion(this.gestion);
                    promesa.setIdLlamada(this.llamada_En_Proceso);
                    promesa.setOperacion(this.clienteOperacion);
                    promesa.setTelefono(this.selectedLlamada.getCallToNumber());
                    promesa.setFechaPago(fechaInicial.getTime());
                    fechaInicial.set(Calendar.MONTH, fechaInicial.get(Calendar.MONTH) + 1);

                    if (count == 0 && primerTracto.compareTo(BigDecimal.ZERO) == 1) {
                        promesa.setMtopago(primerTracto);
                    } else {
                        promesa.setMtopago(saldo);
                    }

                    promesa.setIdMoneda(objMoneda);
                    promesa.setEstado("SEG"); // Seguimiento
                    promesa.setTipodescuento(null); // Tipo Descuento: Monto Fijo o Porcentaje.
                    promesa.setMtoporcentaje(BigDecimal.ZERO); // Monto o %
                    promesa.setTipoarreglopago("REF");//REF = Refinanciamiento.
                    promesa.setFechaingreso(this.fechaHoy.getTime());

                    //if (this.existOneCAT(promesa) && this.validOnlyThreeCTC(promesa) && this.existOneCTC(promesa) && this.existOnePAP(promesa)) {
                    this.promesaList.add(promesa);
                    FacesMessage msg = new FacesMessage("Promesa Agregada: ", promesa.getTelefono());
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                    //}
                }
            }

        } else {
            FacesMessage msg = new FacesMessage("Promesa NO Agregada!: ", null);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    /**
     * Arreglo de pago. Limpia campos del formulario
     */
    public void cleanFormArregloPago(String codigoMoneda) {
        String operacion = this.getClienteOperacion();

        this.setClienteOperacion(null); //clienteOperacion;
        this.setMtoSaldoOperacion(BigDecimal.ZERO); //mtoSaldoOperacion;
        this.setTipoDescuentoPromesa(null); //tipoDescuentoPromesa;
        this.setMtoDescuentoPromesa(BigDecimal.ZERO); //mtoDescuentoPromesa;
        this.setMtoSaldoPromesa(BigDecimal.ZERO); //mtoSaldoPromesa;
        this.setFechaPagoPromesa(null); //fechaPagoPromesa;
        this.setCuotas(null); //cuotas;

        this.setMtoSaldoOperacionUSD(BigDecimal.ZERO);
        this.setMtoDescuentoPromesaUSD(BigDecimal.ZERO);
        this.setMtoSaldoPromesaUSD(BigDecimal.ZERO);

        this.deleteArregloPago(operacion, codigoMoneda);
    }

    /**
     * CAT = Cancelacion Total. CTC = Cancelacion Total por Cuotas. REF =
     * Refinanciamiento. PAP = Pago Parcial
     */
    public void deleteArregloPago(String operacion, String codigoMoneda) {
        ArrayList<String> arregloPago = new ArrayList<String>();
        arregloPago.add("CAT");
        arregloPago.add("CTC");
        arregloPago.add("REF");
        arregloPago.add("PAP");

        int index = 0;
        boolean isTrue = true;
        while (this.promesaList.size() > 0 && isTrue) {//&& this.promesaList.size() > index
            boolean searchingMoneda = this.promesaList.get(index).getIdMoneda().getCodigo().equals(codigoMoneda);
            boolean searchingOperacion = this.promesaList.get(index).getOperacion().equals(operacion);
            if (searchingMoneda && searchingOperacion) {
                this.promesaList.remove(index);
                index = index - 1;
            }

            if (this.promesaList.size() <= 0) {
                isTrue = false;

            } else {
                int i = 0;
                while (i < this.promesaList.size()) {
                    boolean existMoneda = this.promesaList.get(i).getIdMoneda().getCodigo().equals(codigoMoneda);
                    boolean existOperacion = this.promesaList.get(i).getOperacion().equals(operacion);
                    if (existMoneda && existOperacion) {
                        isTrue = true;
                        i++;
                        break;
                    }
                    isTrue = false;
                    i++;
                }
            }
            index++;
        }
    }

    /**
     * Remueve las promesas, segun operacion y tipo de arrglo de pago.
     *
     * @param operacion
     * @param arregloPago
     */
    public void deleteByOperacionAndArregloPago(String operacion, String arregloPago, String codigoMoneda) {
        int index = 0;
        while (this.promesaList.size() > 0 && this.promesaList.size() > index) {
            boolean isOperacion = this.promesaList.get(index).getOperacion().equals(operacion);
            boolean isAP = this.promesaList.get(index).getTipoarreglopago().equals(arregloPago);
            boolean isMoneda = this.promesaList.get(index).getIdMoneda().getCodigo().equals(codigoMoneda);
            if (isOperacion && isAP && isMoneda) {
                this.promesaList.remove(index);
            } else {
                index++;
            }
        }
    }

    /**
     *
     * @param codigoMoneda
     */
    public void agregarPagoParcial(String codigoMoneda) {
        if (this.selectedLlamada != null) {

            if (this.validPagoParcial(codigoMoneda)) {

                Calendar fechaInicial = Calendar.getInstance();
                fechaInicial.setTime(this.fechaPagoPromesa);

                TblPromesa promesa = new TblPromesa();

                if (this.usuario != null) {
                    promesa.setUsuarioingreso(this.usuario.getUsuario());
                }

                promesa.setIdGestion(this.gestion);
                promesa.setIdLlamada(this.llamada_En_Proceso);
                promesa.setOperacion(this.clienteOperacion);
                promesa.setTelefono(this.selectedLlamada.getCallToNumber());
                promesa.setFechaPago(fechaInicial.getTime());

                if (codigoMoneda.equals("CRC")) {
                    promesa.setMtopago(this.mtoSaldoPromesa);
                } else if (codigoMoneda.equals("USD")) {
                    promesa.setMtopago(this.mtoSaldoPromesaUSD);
                }

                Moneda objMoneda = new Moneda();
                objMoneda.setCodigo(codigoMoneda);
                objMoneda = this.ejbMonedaService.findByCodigo(objMoneda);
                promesa.setIdMoneda(objMoneda);

                promesa.setEstado("SEG"); // Seguimiento
                promesa.setTipodescuento(null); // Tipo Descuento: Monto Fijo o Porcentaje.
                promesa.setMtoporcentaje(BigDecimal.ZERO); // Monto o %
                promesa.setTipoarreglopago("PAP");//PAP = Pago Parcial
                promesa.setFechaingreso(this.fechaHoy.getTime());

                // borra las promesas...                
                this.deleteArregloPago(this.clienteOperacion, codigoMoneda);

                //if (this.existOneCAT(promesa) && this.existOneREF(promesa) && this.existOneCTC(promesa) && this.existOnePAP(promesa)) {
                this.promesaList.add(promesa);
                FacesMessage msg = new FacesMessage("Promesa Agregada: ", promesa.getTelefono());
                FacesContext.getCurrentInstance().addMessage(null, msg);
                //}

            }

        } else {
            FacesMessage msg = new FacesMessage("Promesa NO Agregada!: ", null);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    /**
     *
     * @return
     */
    public boolean validPagoParcial(String codigoMoneda) {

        if (this.clienteOperacion == null || this.clienteOperacion.trim().equals("")) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso!", "Debe seleccionar una Operación!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return false;
        }

        if (codigoMoneda.equals("CRC")) {
            if (this.mtoSaldoOperacion == null || this.mtoSaldoOperacion.compareTo(BigDecimal.ZERO) <= 0) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso!", "Saldo de la operación debe ser mayor a cero!");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                return false;

            } else if (this.mtoSaldoPromesa == null || this.mtoSaldoPromesa.compareTo(BigDecimal.ZERO) <= 0) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso!", "Monto Promesa debe ser mayor a cero!");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                return false;

            } else if (this.mtoSaldoOperacion.compareTo(this.mtoSaldoPromesa) < 0) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso!", "Monto Promesa debe ser menor o igual al saldo de la operación!");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                return false;
            }

        } else if (codigoMoneda.equals("USD")) {
            if (this.mtoSaldoOperacionUSD == null || this.mtoSaldoOperacionUSD.compareTo(BigDecimal.ZERO) <= 0) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso!", "Saldo de la operación debe ser mayor a cero!");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                return false;

            } else if (this.mtoSaldoPromesaUSD == null || this.mtoSaldoPromesaUSD.compareTo(BigDecimal.ZERO) <= 0) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso!", "Monto Promesa debe ser mayor a cero!");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                return false;

            } else if (this.mtoSaldoOperacionUSD.compareTo(this.mtoSaldoPromesaUSD) < 0) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso!", "Monto Promesa debe ser menor o igual al saldo de la operación!");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                return false;
            }
        }

        if (this.fechaPagoPromesa == null) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso!", "Debe seleccionar Fecha Pago!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return false;
        }

        return true;

    }

    /**
     * Verifica que solo exista una operacion, con el arreglo de pago,
     * Cancelacion Total.
     *
     * @param promesa
     * @return
     */
    public boolean validOnlyOneCAT(TblPromesa promesa) {
        if (this.promesaList != null && !this.promesaList.isEmpty()) {
            for (int index = 0; index < this.promesaList.size(); index++) {
                String operacion = promesa.getOperacion();
                String tipoarreglopago = "CAT";
                if (this.promesaList.get(index).getOperacion().equals(operacion) && this.promesaList.get(index).getTipoarreglopago().equals(tipoarreglopago)) {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso!", "Cancelación Total. Sólo puede haber uno!");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                    return false;
                }
            }
        }

        return true;
    }

    /**
     *
     * @param promesa
     * @return
     */
    public boolean existOnePAP(TblPromesa promesa) {
        if (this.promesaList != null && !this.promesaList.isEmpty()) {
            for (int index = 0; index < this.promesaList.size(); index++) {
                String operacion = promesa.getOperacion();
                String tipoarreglopago = "PAP";
                if (this.promesaList.get(index).getOperacion().equals(operacion) && this.promesaList.get(index).getTipoarreglopago().equals(tipoarreglopago)) {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso!", "Pago Parcial. Sólo puede haber uno!");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Ya existe una Cancelación Total por Cuotas!
     *
     * @param promesa
     * @return
     */
    public boolean existOneCTC(TblPromesa promesa) {
        if (this.promesaList != null && !this.promesaList.isEmpty()) {
            for (int index = 0; index < this.promesaList.size(); index++) {
                String operacion = promesa.getOperacion();
                String tipoarreglopago = "CTC";
                if (this.promesaList.get(index).getOperacion().equals(operacion) && this.promesaList.get(index).getTipoarreglopago().equals(tipoarreglopago)) {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso!", "Ya existe una Cancelación Total por Cuotas!");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Ya existe arreglo de pago, Refinanciamiento!
     *
     * @param promesa
     * @return
     */
    public boolean existOneREF(TblPromesa promesa) {
        if (this.promesaList != null && !this.promesaList.isEmpty()) {
            for (int index = 0; index < this.promesaList.size(); index++) {
                String operacion = promesa.getOperacion();
                String tipoarreglopago = "REF";//REF = Refinanciamiento.
                if (this.promesaList.get(index).getOperacion().equals(operacion) && this.promesaList.get(index).getTipoarreglopago().equals(tipoarreglopago)) {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso!", "Refinanciamiento. Ya existe arreglo de pago!");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Validar los datos del Arreglo de pago. Cancelacion Total
     *
     * @return
     */
    private boolean validArregloPago(String codigoMoneda) {

        if (this.clienteOperacion == null || this.clienteOperacion.trim().equals("")) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso!", "Debe seleccionar una Operación!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return false;
        }

        if (codigoMoneda.equals("CRC")) {
            if (this.mtoSaldoOperacion == null || this.mtoSaldoOperacion.compareTo(BigDecimal.ZERO) <= 0) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso!", "Saldo de la operación debe ser mayor a cero!");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                return false;
            }
        } else if (codigoMoneda.equals("USD")) {
            if (this.mtoSaldoOperacionUSD == null || this.mtoSaldoOperacionUSD.compareTo(BigDecimal.ZERO) <= 0) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso!", "Saldo de la operación debe ser mayor a cero!");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                return false;
            }
        }

        if (this.tipoDescuentoPromesa == null || this.tipoDescuentoPromesa.equals("")) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso!", "Debe seleccionar una Tipo Descuento!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return false;
        }

        if (codigoMoneda.equals("CRC")) {
            if (this.mtoDescuentoPromesa == null || this.mtoDescuentoPromesa.equals(BigDecimal.ZERO)) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso!", "Debe digitar Monto o %!");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                return false;
            } else if (this.mtoSaldoPromesa == null || this.mtoSaldoPromesa.compareTo(BigDecimal.ZERO) == 0 || this.mtoSaldoPromesa.compareTo(BigDecimal.ZERO) == -1) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso!", "Monto Promesa debe ser mayor a cero!");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                return false;
            }

        } else if (codigoMoneda.equals("USD")) {
            if (this.mtoDescuentoPromesaUSD == null || this.mtoDescuentoPromesaUSD.equals(BigDecimal.ZERO)) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso!", "Debe digitar Monto o %!");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                return false;

            } else if (this.mtoSaldoPromesaUSD == null || this.mtoSaldoPromesaUSD.compareTo(BigDecimal.ZERO) == 0 || this.mtoSaldoPromesaUSD.compareTo(BigDecimal.ZERO) == -1) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso!", "Monto Promesa debe ser mayor a cero!");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                return false;

            }
        }

        if (this.fechaPagoPromesa == null) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso!", "Debe seleccionar Fecha Pago!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return false;
        }

        return true;
    }

    /**
     *
     * @return
     */
    private boolean validCancelacionTotalPorCuotas() {

        if (this.cuotas == null || this.cuotas.trim().equals("")) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso!", "Debe seleccionar una cuota!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return false;
        }

        return true;
    }

    /**
     * Sólo puede haber uno. Ya existe una Cancelación Total!
     *
     * @param promesa
     * @return
     */
    public boolean existOneCAT(TblPromesa promesa) {
        if (this.promesaList != null && !this.promesaList.isEmpty()) {
            for (int index = 0; index < this.promesaList.size(); index++) {
                String operacion = promesa.getOperacion();
                String tipoarreglopago = "CAT";
                if (this.promesaList.get(index).getOperacion().equals(operacion) && this.promesaList.get(index).getTipoarreglopago().equals(tipoarreglopago)) {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso!", "Ya existe una Cancelación Total. Sólo puede haber uno!");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                    return false;
                }
            }
        }
        return true;
    }

    /**
     *
     * @param promesa
     * @return
     */
    public boolean validOnlyThreeCTC(TblPromesa promesa) {
        int countCTC = 0;
        if (this.promesaList != null && !this.promesaList.isEmpty()) {
            for (int i = 0; i < this.promesaList.size(); i++) {
                if (this.promesaList.get(i).getTipoarreglopago().equals("CTC")) {
                    countCTC++;
                }
            }

            if (countCTC >= 3) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso!", "Cancelacion Total por Cuotas. Maximo tres arreglos de pago!");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                return false;
            }
        }

        return true;
    }

    /**
     *
     * @return
     */
    private boolean validRefinanciamiento(String codigoMoneda) {

        if (this.clienteOperacion == null || this.clienteOperacion.trim().equals("")) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso!", "Debe seleccionar una Operación!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return false;

        }

        if (codigoMoneda.equals("CRC")) {
            if (this.mtoSaldoOperacion == null || this.mtoSaldoOperacion.compareTo(BigDecimal.ZERO) <= 0) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso!", "Saldo de la operación debe ser mayor a cero!");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                return false;
            } else if (this.mtoDescuentoPromesa == null || this.mtoDescuentoPromesa.compareTo(BigDecimal.ZERO) == 0 || this.mtoDescuentoPromesa.compareTo(BigDecimal.ZERO) == -1) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso!", "Primer Tracto, debe ser mayor a cero!");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                return false;
            } else if (this.mtoSaldoPromesa == null || this.mtoSaldoPromesa.compareTo(BigDecimal.ZERO) == 0 || this.mtoSaldoPromesa.compareTo(BigDecimal.ZERO) == -1) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso!", "Saldo restante, debe ser mayor a cero!");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                return false;
            }

        } else if (codigoMoneda.equals("USD")) {
            if (this.mtoSaldoOperacionUSD == null || this.mtoSaldoOperacionUSD.compareTo(BigDecimal.ZERO) <= 0) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso!", "Saldo de la operación debe ser mayor a cero!");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                return false;
            } else if (this.mtoDescuentoPromesaUSD == null || this.mtoDescuentoPromesaUSD.compareTo(BigDecimal.ZERO) == 0 || this.mtoDescuentoPromesaUSD.compareTo(BigDecimal.ZERO) == -1) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso!", "Primer Tracto, debe ser mayor a cero!");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                return false;
            } else if (this.mtoSaldoPromesaUSD == null || this.mtoSaldoPromesaUSD.compareTo(BigDecimal.ZERO) == 0 || this.mtoSaldoPromesaUSD.compareTo(BigDecimal.ZERO) == -1) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso!", "Saldo restante, debe ser mayor a cero!");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                return false;
            }
        }

        if (this.fechaPagoPromesa == null) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso!", "Debe seleccionar Fecha Pago Inicial!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return false;

        } else if (this.cuotas == null || this.cuotas.trim().equals("")) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso!", "Debe seleccionar una cuota!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return false;
        }

        return true;
    }

    public void onRowEdit(RowEditEvent<TblPromesa> event) {
        FacesMessage msg = new FacesMessage("Promesa Editada", event.getObject().getFechaPago().toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowCancel(RowEditEvent<TblPromesa> event) {
        FacesMessage msg = new FacesMessage("Edición Cancelada", event.getObject().getFechaPago().toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    /**
     *
     */
    public void deletePromesa() {
        for (int index = 0; index < this.promesaList.size(); index++) {
            if (this.selectedPromesa != null && (this.promesaList != null && this.promesaList.size() > 0)) {

                boolean isTrueTelefono = this.promesaList.get(index).getTelefono().equals(this.selectedPromesa.getTelefono()) ? true : false;
                boolean isTrueOperacion = this.promesaList.get(index).getOperacion().equals(this.selectedPromesa.getOperacion()) ? true : false;
                //boolean isTrueTipoDescuento = this.promesaList.get(index).getTipodescuento().equals(this.selectedPromesa.getTipodescuento()) ? true : false;
                //boolean isTrueMontoPorcentaje = this.promesaList.get(index).getMtoporcentaje().equals(this.selectedPromesa.getMtoporcentaje()) ? true : false;
                boolean isTrueMtopago = this.promesaList.get(index).getMtopago().equals(this.selectedPromesa.getMtopago()) ? true : false;
                boolean isTrueFechaPago = this.promesaList.get(index).getFechaPago().equals(this.selectedPromesa.getFechaPago()) ? true : false;
                boolean isTrueEstado = this.promesaList.get(index).getEstado().equals(this.selectedPromesa.getEstado()) ? true : false;
                boolean isTrueTipoArreglo = this.promesaList.get(index).getEstado().equals(this.selectedPromesa.getEstado()) ? true : false;
                boolean isTrueMoneda = this.promesaList.get(index).getIdMoneda().equals(this.selectedPromesa.getIdMoneda()) ? true : false;

                if (this.promesaList.get(index).getOperacion() != null) {
                    isTrueOperacion = this.promesaList.get(index).getOperacion().equals(this.selectedPromesa.getOperacion()) ? true : false;
                }

                // isTrueTipoDescuento && isTrueMontoPorcentaje &&
                if (isTrueTelefono && isTrueOperacion && isTrueMtopago && isTrueFechaPago && isTrueEstado && isTrueTipoArreglo && isTrueMoneda) {

                    TblPromesa promesa = this.promesaList.get(index);
                    boolean hasOperation = promesa.getOperacion() != null && !promesa.getOperacion().trim().equals("") ? true : false;
                    boolean hasMtopago = promesa.getMtopago() != null && !promesa.getMtopago().equals(0) ? true : false;
                    boolean hasIdGestion = promesa.getIdGestion() != null && promesa.getIdGestion().getIdGestion() != null ? true : false;

                    if (hasOperation && hasMtopago && hasIdGestion) {
                        promesa.setEstado("DEL");
                        this.ejbPromesaLocal.update(promesa);
                    }

                    this.promesaList.remove(index);
                    this.selectedPromesa = null;
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Promesa Removida"));
                    PrimeFaces.current().ajax().update("formGestion:messages");
                }
            }
        }
    }

    /**
     *
     * @param objLlamada
     */
    public void setSubtipificacionNullonLlamada(TblLlamada objLlamada) {
        if (objLlamada != null && this.llamadaList != null) {
            String telefono = objLlamada.getCallToNumber();
            for (int index = 0; index < this.llamadaList.size(); index++) {
                TblLlamada obj = this.llamadaList.get(index);
                if (obj.getCallToNumber().equals(telefono)) {
                    this.llamadaList.get(index).setIdSubtipificacion(null);
                }
            }
        }
    }

    /**
     *
     * @param objLlamada
     */
    public void setResultadoGestionNullonLlamada(TblLlamada objLlamada) {
        if (objLlamada != null && this.llamadaList != null) {
            String telefono = objLlamada.getCallToNumber();
            for (int index = 0; index < this.llamadaList.size(); index++) {
                TblLlamada obj = this.llamadaList.get(index);
                if (obj.getCallToNumber().equals(telefono)) {
                    this.llamadaList.get(index).setIdResultadogestion(null);
                }
            }
        }
    }

    /**
     *
     * @param objLlamada
     */
    public void setResultadoTerceroNullonLlamada(TblLlamada objLlamada) {
        if (objLlamada != null && this.llamadaList != null) {
            String telefono = objLlamada.getCallToNumber();
            for (int index = 0; index < this.llamadaList.size(); index++) {
                TblLlamada obj = this.llamadaList.get(index);
                if (obj.getCallToNumber().equals(telefono)) {
                    this.llamadaList.get(index).setIdResultadotercero(null);
                }
            }
        }
    }

    /**
     *
     * @return
     */
    private List<TblGestionsaldo> agregarSaldos() {
        List<TblGestionsaldo> saldos = new ArrayList<TblGestionsaldo>();

        BigDecimal saldoColones = this.selectedCartera.getSaldoColones();
        BigDecimal interesesColones = this.selectedCartera.getInteresesColones();
        Moneda monedaColones = this.selectedCartera.getIdMonedaColones();

        BigDecimal saldoDolares = this.selectedCartera.getSaldoDolares();
        BigDecimal interesesDolares = this.selectedCartera.getInteresesDolares();
        Moneda monedaDolares = this.selectedCartera.getIdMonedaDolares();

        TblGestionsaldo colones = new TblGestionsaldo();
        colones.setSaldo(saldoColones);
        colones.setIntereses(interesesColones);
        colones.setIdMoneda(monedaColones);

        if (this.gestion.getLeyusura() != null && this.gestion.getLeyusura().equals("1")) {
            if (this.gestion.getMtosaldocobrar() != null) {
                colones.setSaldo(this.gestion.getMtosaldocobrar());
            } else {
                colones.setSaldo(BigDecimal.ZERO);
            }
        }
        colones.setIdGestion(this.gestion);

        TblGestionsaldo dolares = new TblGestionsaldo();
        dolares.setSaldo(saldoDolares);
        dolares.setIntereses(interesesDolares);
        dolares.setIdMoneda(monedaDolares);
        dolares.setIdGestion(this.gestion);

        saldos.add(colones);
        saldos.add(dolares);

        return saldos;
    }

    /**
     *
     * @param prefijo
     */
    public void onPrefijoSalidaChange() {

        if (this.prefijoSalidaSelected != null) {
            System.out.println("Prefijo: " + this.prefijoSalidaSelected.getId() + " - " + this.prefijoSalidaSelected.getPrefijo() + " - " + this.prefijoSalidaSelected.getNombre() + " - " + this.prefijoSalidaSelected.getDescripcion());
        }

    }

    /**
     *
     * @param pOperacion
     * @param pmtoPago
     * @param pFechaPago
     */
    public void recalcularCuotas(String pOperacion, BigDecimal pmtoPago, Date pFechaPago, String tipoarreglopago, String codigoMoneda) {

        if (tipoarreglopago.equals("CTC") && codigoMoneda.equals("CRC")) {

            BigDecimal coutasInt = new BigDecimal(this.cuotas);
            BigDecimal saldo = new BigDecimal(BigInteger.ZERO);
            BigDecimal sumar = new BigDecimal(BigInteger.ZERO);
            BigDecimal contador = new BigDecimal(BigInteger.ZERO);

            if (pmtoPago.compareTo(this.mtoSaldoPromesa) > 0) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso!", "Monto digitado es mayor al Monto Promesa!");
                FacesContext.getCurrentInstance().addMessage(null, msg);

                saldo = this.mtoSaldoPromesa.divide(coutasInt, 6, RoundingMode.HALF_UP);

                for (int index = 0; index < this.promesaList.size(); index++) {

                    String operacion = this.promesaList.get(index).getOperacion();
                    Date fechaPago = this.promesaList.get(index).getFechaPago();
                    String monedaString = this.promesaList.get(index).getIdMoneda().getCodigo();
                    String arreglopago = this.promesaList.get(index).getTipoarreglopago();

                    boolean isTrueOperacion = pOperacion.equals(operacion);
                    boolean isFechaPagoEquals = fechaPago.equals(pFechaPago);
                    boolean isFechaPagoAfter = fechaPago.after(pFechaPago);
                    boolean isMoneda = codigoMoneda.equals(monedaString);

                    boolean isArregloPago = arreglopago.equals(tipoarreglopago);
                    if (isArregloPago) {
                        contador = contador.add(BigDecimal.ONE);
                    }

                    if (isArregloPago && isMoneda && isTrueOperacion && isFechaPagoEquals && index == 0) {
                        this.promesaList.get(index).setMtopago(saldo);

                    } else if (isArregloPago && isMoneda && isTrueOperacion && isFechaPagoEquals && !coutasInt.subtract(contador).equals(BigDecimal.ZERO)) {
                        this.promesaList.get(index).setMtopago(saldo);

                    } else if (isArregloPago && isMoneda && isTrueOperacion && isFechaPagoAfter) {
                        this.promesaList.get(index).setMtopago(saldo);

                    } else if (isArregloPago && isMoneda && isTrueOperacion && coutasInt.subtract(contador).equals(BigDecimal.ZERO)) {
                        BigDecimal mtoUltimo = this.mtoSaldoPromesa.subtract(sumar);
                        this.promesaList.get(index).setMtopago(mtoUltimo);

                    } else if (isArregloPago && isMoneda && isTrueOperacion) {
                        sumar = sumar.add(this.promesaList.get(index).getMtopago());
                        BigDecimal saldoRestante = this.mtoSaldoPromesa.subtract(sumar);
                        if (isArregloPago && isMoneda && isTrueOperacion && !coutasInt.subtract(contador).equals(BigDecimal.ZERO)) {
                            saldo = saldoRestante.divide(coutasInt.subtract(contador), 6, RoundingMode.HALF_UP);
                        }
                    }
                }//for

            } else {

                for (int index = 0; index < this.promesaList.size(); index++) {

                    String operacion = this.promesaList.get(index).getOperacion();
                    Date fechaPago = this.promesaList.get(index).getFechaPago();
                    String monedaString = this.promesaList.get(index).getIdMoneda().getCodigo();
                    String arreglopago = this.promesaList.get(index).getTipoarreglopago();

                    boolean isTrueOperacion = pOperacion.equals(operacion);
                    boolean isFechaPagoAfter = fechaPago.after(pFechaPago);
                    boolean isMoneda = codigoMoneda.equals(monedaString);
                    boolean isArregloPago = arreglopago.equals(tipoarreglopago);
                    if (isArregloPago) {
                        contador = contador.add(BigDecimal.ONE);
                    }

                    if (sumar.compareTo(this.mtoSaldoPromesa) > 0) {
                        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso!", "La suma de las cuotas es mayor al Monto Promesa!");
                        FacesContext.getCurrentInstance().addMessage(null, msg);

                        BigDecimal mtoPagoSiguiente = BigDecimal.ZERO;
                        boolean isTrue = (this.promesaList.size() - index) >= 1;
                        if (isTrue) {
                            BigDecimal indexMenosUno = new BigDecimal(index - 1);
                            BigDecimal mtoPagoAnterior = this.promesaList.get(indexMenosUno.intValue()).getMtopago();
                            sumar = sumar.subtract(mtoPagoAnterior);
                            BigDecimal saldoRestante = this.mtoSaldoPromesa.subtract(sumar);
                            BigDecimal contar = BigDecimal.ZERO;
                            for (int i = 0; i < this.promesaList.size(); i++) {
                                String isCTCString = this.promesaList.get(i).getTipoarreglopago();
                                String isUSDString = this.promesaList.get(i).getIdMoneda().getCodigo();
                                String isOperacionString = this.promesaList.get(i).getOperacion();
                                boolean isCTC = tipoarreglopago.equals(isCTCString);
                                boolean isUSD = codigoMoneda.equals(isUSDString);
                                boolean isOperacion = pOperacion.equals(isOperacionString);

                                if (isCTC && isUSD && isOperacion) {
                                    contar = contar.add(BigDecimal.ONE);
                                }
                            }

                            BigDecimal promesaSize = new BigDecimal(this.promesaList.size());
                            // contar = promesaSize.subtract(contar);

                            BigDecimal cantidaRestante = contar.subtract(BigDecimal.ONE);
                            saldoRestante = saldoRestante.divide(cantidaRestante, 6, RoundingMode.HALF_UP);
                            index = indexMenosUno.intValue();
                            while (index < this.promesaList.size()) {
                                String isCTCString = this.promesaList.get(index).getTipoarreglopago();
                                String isUSDString = this.promesaList.get(index).getIdMoneda().getCodigo();
                                String isOperacionString = this.promesaList.get(index).getOperacion();
                                boolean isCTC = tipoarreglopago.equals(isCTCString);
                                boolean isUSD = codigoMoneda.equals(isUSDString);
                                boolean isOperacion = pOperacion.equals(isOperacionString);

                                if (isCTC && isUSD && isOperacion) {
                                    this.promesaList.get(index).setMtopago(saldoRestante);
                                }

                                index++;
                            }
                        }

                        break;

                    } else {

                        if (isArregloPago && isMoneda && isTrueOperacion && isFechaPagoAfter) {
                            this.promesaList.get(index).setMtopago(saldo);

                        } else if (isArregloPago && isMoneda && isTrueOperacion && coutasInt.subtract(contador).equals(BigDecimal.ZERO)) {
                            BigDecimal mtoUltimo = this.mtoSaldoPromesa.subtract(sumar);
                            this.promesaList.get(index).setMtopago(mtoUltimo);

                        } else if (isArregloPago && isMoneda && isTrueOperacion) {
                            sumar = sumar.add(this.promesaList.get(index).getMtopago());
                            BigDecimal saldoRestante = this.mtoSaldoPromesa.subtract(sumar);
                            if (isArregloPago && isMoneda && isTrueOperacion && !coutasInt.subtract(contador).equals(BigDecimal.ZERO)) {
                                saldo = saldoRestante.divide(coutasInt.subtract(contador), 6, RoundingMode.HALF_UP);
                            }
                        }
                    }//else
                }//for
            }//else
        }// if CTC
    }

    /**
     *
     * @param pOperacion
     * @param pmtoPago
     * @param pFechaPago
     * @param tipoarreglopago
     * @param codigoMoneda
     */
    public void recalcularCuotasREF(String pOperacion, BigDecimal pmtoPago, Date pFechaPago, String tipoarreglopago, String codigoMoneda) {

        if (tipoarreglopago.equals("REF") && codigoMoneda.equals("CRC")) {

            BigDecimal coutasInt = new BigDecimal(this.cuotas);
            BigDecimal saldo = new BigDecimal(BigInteger.ZERO);
            BigDecimal sumar = new BigDecimal(BigInteger.ZERO);
            BigDecimal contador = new BigDecimal(BigInteger.ZERO);

            if (pmtoPago.compareTo(this.mtoSaldoOperacion) > 0) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso!", "Monto digitado es mayor al Monto Promesa!");
                FacesContext.getCurrentInstance().addMessage(null, msg);

                saldo = this.mtoSaldoOperacion.divide(coutasInt, 6, RoundingMode.HALF_UP);

                for (int index = 0; index < this.promesaList.size(); index++) {

                    String operacion = this.promesaList.get(index).getOperacion();
                    Date fechaPago = this.promesaList.get(index).getFechaPago();
                    String monedaString = this.promesaList.get(index).getIdMoneda().getCodigo();
                    String arreglopago = this.promesaList.get(index).getTipoarreglopago();

                    boolean isTrueOperacion = pOperacion.equals(operacion);
                    boolean isFechaPagoEquals = fechaPago.equals(pFechaPago);
                    boolean isFechaPagoAfter = fechaPago.after(pFechaPago);
                    boolean isMoneda = codigoMoneda.equals(monedaString);
                    boolean isArregloPago = tipoarreglopago.equals(arreglopago);
                    contador = contador.add(BigDecimal.ONE);

                    if (isArregloPago && isMoneda && isTrueOperacion && isFechaPagoEquals && index == 0) {
                        this.promesaList.get(index).setMtopago(saldo);

                    } else if (isArregloPago && isMoneda && isTrueOperacion && isFechaPagoEquals && !coutasInt.subtract(contador).equals(BigDecimal.ZERO)) {
                        this.promesaList.get(index).setMtopago(saldo);

                    } else if (isArregloPago && isMoneda && isTrueOperacion && isFechaPagoAfter) {
                        this.promesaList.get(index).setMtopago(saldo);

                    } else if (isArregloPago && isMoneda && coutasInt.subtract(contador).equals(BigDecimal.ZERO)) {
                        BigDecimal mtoUltimo = this.mtoSaldoOperacion.subtract(sumar);
                        this.promesaList.get(index).setMtopago(mtoUltimo);

                    } else if (isArregloPago && isMoneda && isTrueOperacion) {
                        sumar = sumar.add(this.promesaList.get(index).getMtopago());
                        BigDecimal saldoRestante = this.mtoSaldoOperacion.subtract(sumar);
                        if (isArregloPago && isMoneda && isTrueOperacion && !coutasInt.subtract(contador).equals(BigDecimal.ZERO)) {
                            saldo = saldoRestante.divide(coutasInt.subtract(contador), 6, RoundingMode.HALF_UP);
                        }
                    }
                }//for

            } else {

                for (int index = 0; index < this.promesaList.size(); index++) {
                    if (sumar.compareTo(this.mtoSaldoOperacion) > 0) {
                        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso!", "La suma de las cuotas es mayor al Monto Promesa!");
                        FacesContext.getCurrentInstance().addMessage(null, msg);

                        BigDecimal mtoPagoSiguiente = BigDecimal.ZERO;
                        boolean isTrue = (this.promesaList.size() - index) >= 1;
                        if (isTrue) {
                            int indexMenosUno = index - 1;
                            BigDecimal mtoPagoAnterior = this.promesaList.get(indexMenosUno).getMtopago();
                            sumar = sumar.subtract(mtoPagoAnterior);
                            BigDecimal saldoRestante = this.mtoSaldoOperacion.subtract(sumar);
                            int cantida = this.promesaList.size() - indexMenosUno;
                            BigDecimal cantidaRestante = new BigDecimal(cantida);
                            saldoRestante = saldoRestante.divide(cantidaRestante, 6, RoundingMode.HALF_UP);
                            index = indexMenosUno;
                            while (index < this.promesaList.size()) {
                                this.promesaList.get(index).setMtopago(saldoRestante);
                                index++;
                            }
                        }

                        break;

                    } else {
                        String operacion = this.promesaList.get(index).getOperacion();
                        Date fechaPago = this.promesaList.get(index).getFechaPago();
                        String monedaString = this.promesaList.get(index).getIdMoneda().getCodigo();
                        String arreglopago = this.promesaList.get(index).getTipoarreglopago();

                        boolean isTrueOperacion = pOperacion.equals(operacion);
                        boolean isFechaPagoAfter = fechaPago.after(pFechaPago);
                        boolean isMoneda = codigoMoneda.equals(monedaString);
                        boolean isArregloPago = tipoarreglopago.equals(arreglopago);
                        contador = contador.add(BigDecimal.ONE);

                        if (isArregloPago && isMoneda && isTrueOperacion && isFechaPagoAfter) {
                            this.promesaList.get(index).setMtopago(saldo);

                        } else if (isArregloPago && isMoneda && coutasInt.subtract(contador).equals(BigDecimal.ZERO)) {
                            BigDecimal mtoUltimo = this.mtoSaldoOperacion.subtract(sumar);
                            this.promesaList.get(index).setMtopago(mtoUltimo);

                        } else if (isArregloPago && isMoneda && isTrueOperacion) {
                            sumar = sumar.add(this.promesaList.get(index).getMtopago());
                            BigDecimal saldoRestante = this.mtoSaldoOperacion.subtract(sumar);
                            if (isArregloPago && isMoneda && isTrueOperacion && !coutasInt.subtract(contador).equals(BigDecimal.ZERO)) {
                                saldo = saldoRestante.divide(coutasInt.subtract(contador), 6, RoundingMode.HALF_UP);
                            }
                        }

                    }//else
                }//for
            }//else
        }// REF CRC
    }

    /**
     *
     * @param event
     */
    public void fechaPagoChanged(SelectEvent event) {
        Date fecha = (Date) event.getObject();
        this.fechaPagoPromesa.setTime(fecha.getTime());
    }

    /*
    ****************************************************************************
    ********************* Arreglo de pago USD **********************************
    ****************************************************************************    
     */
    private BigDecimal mtoSaldoOperacionUSD;
    private BigDecimal mtoDescuentoPromesaUSD;
    private BigDecimal mtoSaldoPromesaUSD;

    public BigDecimal getMtoSaldoOperacionUSD() {
        return mtoSaldoOperacionUSD;
    }

    public void setMtoSaldoOperacionUSD(BigDecimal mtoSaldoOperacionUSD) {
        this.mtoSaldoOperacionUSD = mtoSaldoOperacionUSD;
    }

    public BigDecimal getMtoDescuentoPromesaUSD() {
        return mtoDescuentoPromesaUSD;
    }

    public void setMtoDescuentoPromesaUSD(BigDecimal mtoDescuentoPromesaUSD) {
        this.mtoDescuentoPromesaUSD = mtoDescuentoPromesaUSD;
    }

    public BigDecimal getMtoSaldoPromesaUSD() {
        return mtoSaldoPromesaUSD;
    }

    public void setMtoSaldoPromesaUSD(BigDecimal mtoSaldoPromesaUSD) {
        this.mtoSaldoPromesaUSD = mtoSaldoPromesaUSD;
    }

    /**
     *
     */
    public void onOperacionPromesaChangeUSD() {

        if (this.clienteOperacion != null && !this.clienteOperacion.trim().equals("")) {
            if (this.carteraList != null && !this.carteraList.isEmpty() && this.carteraList.size() > 0) {
                for (int index = 0; index < this.carteraList.size(); index++) {
                    if (this.carteraList.get(index).getNumeroCuenta().equals(this.clienteOperacion)) {
                        this.mtoSaldoOperacionUSD = this.carteraList.get(index).getSaldoDolares();
                    } //if
                } //for
            } //if
        } //if
    }

    /**
     * USD Cacula nuevo monto degun descuento.
     */
    public void calcNuevoMontoSegunDescuentoUSD() {
        BigDecimal cien = new BigDecimal("100");
        BigDecimal newSaldo = new BigDecimal(BigInteger.ZERO);
        BigDecimal mtoSaldo = this.mtoSaldoOperacionUSD;
        BigDecimal mtoPort = this.mtoDescuentoPromesaUSD;

        if (this.tipoDescuentoPromesa.equals("FIJ")) {
            newSaldo = mtoSaldo.subtract(mtoPort);

        } else {
            BigDecimal porcentage = mtoSaldo.multiply(mtoPort).divide(cien);
            newSaldo = mtoSaldo.subtract(porcentage);
        }

        this.mtoSaldoPromesaUSD = newSaldo;
    }

    /**
     *
     * @param pOperacion
     * @param pmtoPago
     * @param pFechaPago
     * @param tipoarreglopago
     * @param codigoMoneda
     */
    public void recalcularCuotasUSD(String pOperacion, BigDecimal pmtoPago, Date pFechaPago, String tipoarreglopago, String codigoMoneda) {

        if (tipoarreglopago.equals("CTC") && codigoMoneda.equals("USD")) {

            BigDecimal coutasInt = new BigDecimal(this.cuotas);
            BigDecimal saldo = new BigDecimal(BigInteger.ZERO);
            BigDecimal sumar = new BigDecimal(BigInteger.ZERO);
            BigDecimal contador = new BigDecimal(BigInteger.ZERO);

            if (pmtoPago.compareTo(this.mtoSaldoPromesaUSD) > 0) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso!", "Monto digitado es mayor al Monto Promesa!");
                FacesContext.getCurrentInstance().addMessage(null, msg);

                saldo = this.mtoSaldoPromesaUSD.divide(coutasInt, 6, RoundingMode.HALF_UP);

                for (int index = 0; index < this.promesaList.size(); index++) {

                    String operacion = this.promesaList.get(index).getOperacion();
                    Date fechaPago = this.promesaList.get(index).getFechaPago();
                    String monedaString = this.promesaList.get(index).getIdMoneda().getCodigo();
                    String arreglopago = this.promesaList.get(index).getTipoarreglopago();

                    boolean isTrueOperacion = pOperacion.equals(operacion);
                    boolean isFechaPagoEquals = fechaPago.equals(pFechaPago);
                    boolean isFechaPagoAfter = fechaPago.after(pFechaPago);
                    boolean isMoneda = codigoMoneda.equals(monedaString);

                    boolean isArregloPago = arreglopago.equals(tipoarreglopago);
                    if (isArregloPago) {
                        contador = contador.add(BigDecimal.ONE);
                    }

                    if (isArregloPago && isMoneda && isTrueOperacion && isFechaPagoEquals && index == 0) {
                        this.promesaList.get(index).setMtopago(saldo);

                    } else if (isArregloPago && isMoneda && isTrueOperacion && isFechaPagoEquals && !coutasInt.subtract(contador).equals(BigDecimal.ZERO)) {
                        this.promesaList.get(index).setMtopago(saldo);

                    } else if (isArregloPago && isMoneda && isTrueOperacion && isFechaPagoAfter) {
                        this.promesaList.get(index).setMtopago(saldo);

                    } else if (isArregloPago && isMoneda && isTrueOperacion && coutasInt.subtract(contador).equals(BigDecimal.ZERO)) {
                        BigDecimal mtoUltimo = this.mtoSaldoPromesaUSD.subtract(sumar);
                        this.promesaList.get(index).setMtopago(mtoUltimo);

                    } else if (isArregloPago && isMoneda && isTrueOperacion) {
                        sumar = sumar.add(this.promesaList.get(index).getMtopago());
                        BigDecimal saldoRestante = this.mtoSaldoPromesaUSD.subtract(sumar);
                        if (isArregloPago && isMoneda && isTrueOperacion && !coutasInt.subtract(contador).equals(BigDecimal.ZERO)) {
                            saldo = saldoRestante.divide(coutasInt.subtract(contador), 6, RoundingMode.HALF_UP);
                        }
                    }
                }//for

            } else {

                for (int index = 0; index < this.promesaList.size(); index++) {

                    String operacion = this.promesaList.get(index).getOperacion();
                    Date fechaPago = this.promesaList.get(index).getFechaPago();
                    String monedaString = this.promesaList.get(index).getIdMoneda().getCodigo();
                    String arreglopago = this.promesaList.get(index).getTipoarreglopago();

                    boolean isTrueOperacion = pOperacion.equals(operacion);
                    boolean isFechaPagoAfter = fechaPago.after(pFechaPago);
                    boolean isMoneda = codigoMoneda.equals(monedaString);
                    boolean isArregloPago = arreglopago.equals(tipoarreglopago);
                    if (isArregloPago) {
                        contador = contador.add(BigDecimal.ONE);
                    }

                    if (sumar.compareTo(this.mtoSaldoPromesaUSD) > 0) {
                        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso!", "La suma de las cuotas es mayor al Monto Promesa!");
                        FacesContext.getCurrentInstance().addMessage(null, msg);

                        BigDecimal mtoPagoSiguiente = BigDecimal.ZERO;
                        boolean isTrue = (this.promesaList.size() - index) >= 1;
                        if (isTrue) {
                            BigDecimal indexMenosUno = new BigDecimal(index - 1);
                            BigDecimal mtoPagoAnterior = this.promesaList.get(indexMenosUno.intValue()).getMtopago();
                            sumar = sumar.subtract(mtoPagoAnterior);
                            BigDecimal saldoRestante = this.mtoSaldoPromesaUSD.subtract(sumar);
                            BigDecimal contar = BigDecimal.ZERO;
                            for (int i = 0; i < this.promesaList.size(); i++) {
                                String isCTCString = this.promesaList.get(i).getTipoarreglopago();
                                String isUSDString = this.promesaList.get(i).getIdMoneda().getCodigo();
                                String isOperacionString = this.promesaList.get(i).getOperacion();
                                boolean isCTC = tipoarreglopago.equals(isCTCString);
                                boolean isUSD = codigoMoneda.equals(isUSDString);
                                boolean isOperacion = pOperacion.equals(isOperacionString);

                                if (isCTC && isUSD && isOperacion) {
                                    contar = contar.add(BigDecimal.ONE);
                                }
                            }

                            BigDecimal promesaSize = new BigDecimal(this.promesaList.size());
                            // contar = promesaSize.subtract(contar);

                            BigDecimal cantidaRestante = contar.subtract(BigDecimal.ONE);
                            saldoRestante = saldoRestante.divide(cantidaRestante, 6, RoundingMode.HALF_UP);
                            index = indexMenosUno.intValue();
                            while (index < this.promesaList.size()) {
                                String isCTCString = this.promesaList.get(index).getTipoarreglopago();
                                String isUSDString = this.promesaList.get(index).getIdMoneda().getCodigo();
                                String isOperacionString = this.promesaList.get(index).getOperacion();
                                boolean isCTC = tipoarreglopago.equals(isCTCString);
                                boolean isUSD = codigoMoneda.equals(isUSDString);
                                boolean isOperacion = pOperacion.equals(isOperacionString);

                                if (isCTC && isUSD && isOperacion) {
                                    this.promesaList.get(index).setMtopago(saldoRestante);
                                }

                                index++;
                            }
                        }

                        break;

                    } else {

                        if (isArregloPago && isMoneda && isTrueOperacion && isFechaPagoAfter) {
                            this.promesaList.get(index).setMtopago(saldo);

                        } else if (isArregloPago && isMoneda && isTrueOperacion && coutasInt.subtract(contador).equals(BigDecimal.ZERO)) {
                            BigDecimal mtoUltimo = this.mtoSaldoPromesaUSD.subtract(sumar);
                            this.promesaList.get(index).setMtopago(mtoUltimo);

                        } else if (isArregloPago && isMoneda && isTrueOperacion) {
                            sumar = sumar.add(this.promesaList.get(index).getMtopago());
                            BigDecimal saldoRestante = this.mtoSaldoPromesaUSD.subtract(sumar);
                            if (isArregloPago && isMoneda && isTrueOperacion && !coutasInt.subtract(contador).equals(BigDecimal.ZERO)) {
                                saldo = saldoRestante.divide(coutasInt.subtract(contador), 6, RoundingMode.HALF_UP);
                            }
                        }
                    }//else
                }//for
            }//else
        }// if CTC
    }

    /**
     *
     * @param pOperacion
     * @param pmtoPago
     * @param pFechaPago
     * @param tipoarreglopago
     * @param codigoMoneda
     */
    public void recalcularCuotasREFUSD(String pOperacion, BigDecimal pmtoPago, Date pFechaPago, String tipoarreglopago, String codigoMoneda) {

        // mtoSaldoOperacionUSD // Saldo
        BigDecimal primerTracto = this.mtoDescuentoPromesaUSD; // Primer Tracto
        BigDecimal saldoRestanteUSD = this.mtoSaldoPromesaUSD;    // Saldo restante

        if (tipoarreglopago.equals("REF") && codigoMoneda.equals("USD")) {

            BigDecimal coutasInt = new BigDecimal(this.cuotas);
            BigDecimal saldo = new BigDecimal(BigInteger.ZERO);
            BigDecimal sumar = new BigDecimal(BigInteger.ZERO);
            BigDecimal contador = new BigDecimal(BigInteger.ZERO);

            if (pmtoPago.compareTo(this.mtoSaldoOperacionUSD) > 0) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso!", "Monto digitado es mayor al saldo operación!");
                FacesContext.getCurrentInstance().addMessage(null, msg);

                BigDecimal coutasRestante = coutasInt.subtract(BigDecimal.ONE);
                saldo = saldoRestanteUSD.divide(coutasRestante, 6, RoundingMode.HALF_UP);//Saldo

                for (int index = 0; index < this.promesaList.size(); index++) {

                    String operacion = this.promesaList.get(index).getOperacion();
                    Date fechaPago = this.promesaList.get(index).getFechaPago();
                    String monedaString = this.promesaList.get(index).getIdMoneda().getCodigo();
                    String arreglopago = this.promesaList.get(index).getTipoarreglopago();

                    boolean isTrueOperacion = pOperacion.equals(operacion);
                    boolean isFechaPagoEquals = fechaPago.equals(pFechaPago);
                    boolean isFechaPagoAfter = fechaPago.after(pFechaPago);
                    boolean isMoneda = codigoMoneda.equals(monedaString);

                    boolean isArregloPago = arreglopago.equals(tipoarreglopago);
                    if (isArregloPago && isMoneda && isTrueOperacion) {
                        contador = contador.add(BigDecimal.ONE);
                    }

                    if (isArregloPago && isMoneda && isTrueOperacion && isFechaPagoEquals && contador.equals(BigDecimal.ONE)) {
                        this.promesaList.get(index).setMtopago(primerTracto);

                    } else if (isArregloPago && isMoneda && isTrueOperacion && isFechaPagoEquals && !coutasInt.subtract(contador).equals(BigDecimal.ZERO)) {
                        this.promesaList.get(index).setMtopago(saldo);

                    } else if (isArregloPago && isMoneda && isTrueOperacion && isFechaPagoAfter) {
                        this.promesaList.get(index).setMtopago(saldo);

                    } else if (isArregloPago && isMoneda && isTrueOperacion && coutasInt.subtract(contador).equals(BigDecimal.ZERO)) {
                        BigDecimal mtoUltimo = this.mtoSaldoOperacionUSD.subtract(sumar);// Saldo operacion
                        this.promesaList.get(index).setMtopago(mtoUltimo);

                    } else if (isArregloPago && isMoneda && isTrueOperacion) {
                        sumar = sumar.add(this.promesaList.get(index).getMtopago());
                        BigDecimal saldoRestante = this.mtoSaldoOperacionUSD.subtract(sumar);// Saldo operacion
                        if (isArregloPago && isMoneda && isTrueOperacion && !coutasInt.subtract(contador).equals(BigDecimal.ZERO)) {
                            saldo = saldoRestante.divide(coutasInt.subtract(contador), 6, RoundingMode.HALF_UP);
                        }
                    }
                }//for

            } else {

                for (int index = 0; index < this.promesaList.size(); index++) {

                    String operacion = this.promesaList.get(index).getOperacion();
                    Date fechaPago = this.promesaList.get(index).getFechaPago();
                    String monedaString = this.promesaList.get(index).getIdMoneda().getCodigo();
                    String arreglopago = this.promesaList.get(index).getTipoarreglopago();

                    boolean isTrueOperacion = pOperacion.equals(operacion);
                    boolean isFechaPagoAfter = fechaPago.after(pFechaPago);
                    boolean isMoneda = codigoMoneda.equals(monedaString);
                    boolean isArregloPago = arreglopago.equals(tipoarreglopago);
                    if (isArregloPago && isMoneda && isTrueOperacion) {
                        contador = contador.add(BigDecimal.ONE);
                    }

                    if (sumar.compareTo(this.mtoSaldoOperacionUSD) > 0) {// Saldo Operacion
                        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso!", "La suma de las cuotas es mayor al saldo operación!");
                        FacesContext.getCurrentInstance().addMessage(null, msg);

                        BigDecimal mtoPagoSiguiente = BigDecimal.ZERO;
                        boolean isTrue = (this.promesaList.size() - index) >= 1;
                        if (isTrue) {
                            BigDecimal indexMenosUno = new BigDecimal(index - 1);
                            BigDecimal mtoPagoAnterior = this.promesaList.get(indexMenosUno.intValue()).getMtopago();
                            sumar = sumar.subtract(mtoPagoAnterior);
                            BigDecimal saldoRestante = this.mtoSaldoOperacionUSD.subtract(sumar);// Saldo Operacion
                            BigDecimal contar = BigDecimal.ZERO;

                            if (isTrueOperacion && isMoneda && isArregloPago) {
                                contar = contar.add(BigDecimal.ONE);
                            }

                            BigDecimal cantidaRestante = coutasInt.subtract(contar);
                            cantidaRestante = cantidaRestante.subtract(BigDecimal.ONE);
                            saldoRestante = saldoRestante.divide(cantidaRestante, 6, RoundingMode.HALF_UP);
                            index = indexMenosUno.intValue();
                            while (index < this.promesaList.size()) {
                                String isCTCString = this.promesaList.get(index).getTipoarreglopago();
                                String isUSDString = this.promesaList.get(index).getIdMoneda().getCodigo();
                                String isOperacionString = this.promesaList.get(index).getOperacion();
                                boolean isCTC = tipoarreglopago.equals(isCTCString);
                                boolean isUSD = codigoMoneda.equals(isUSDString);
                                boolean isOperacion = pOperacion.equals(isOperacionString);

                                if (isCTC && isUSD && isOperacion) {
                                    this.promesaList.get(index).setMtopago(saldoRestante);
                                }

                                index++;
                            }
                        }

                        break;

                    } else {

                        if (isArregloPago && isMoneda && isTrueOperacion && isFechaPagoAfter) {
                            this.promesaList.get(index).setMtopago(saldo);

                        } else if (isArregloPago && isMoneda && isTrueOperacion && coutasInt.subtract(contador).equals(BigDecimal.ZERO)) {
                            BigDecimal mtoUltimo = this.mtoSaldoOperacionUSD.subtract(sumar);// Saldo restante
                            this.promesaList.get(index).setMtopago(mtoUltimo);

                        } else if (isArregloPago && isMoneda && isTrueOperacion) {
                            sumar = sumar.add(this.promesaList.get(index).getMtopago());
                            BigDecimal saldoRestante = this.mtoSaldoOperacionUSD.subtract(sumar);// Saldo restante
                            if (isArregloPago && isMoneda && isTrueOperacion && !coutasInt.subtract(contador).equals(BigDecimal.ZERO)) {
                                saldo = saldoRestante.divide(coutasInt.subtract(contador), 6, RoundingMode.HALF_UP);
                            }
                        }
                    }//else
                }//for

            }//else
        } // REF USD
    }

    /**
     * 
     * @return 
     */
    private boolean validarURL() {
        TblCentral central = this.prefijoSalidaList.get(0).getTblCentral();

        if (central == null) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso!", "Falta configuar la central para hacer llamada!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return false;

        } else {

            this.http = central.getProtocolo();
            this.ip_publica = central.getIpCentral();
            this.directorioCentral = central.getDirectorio();

            if (this.http == null && this.http.trim().equals("")) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso!", "Falta protocolo de salida, (http o https)!");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                return false;

            } else if (this.ip_publica == null && this.ip_publica.trim().equals("")) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso!", "Falta IP pública!");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                return false;

            } else if (this.directorioCentral == null && this.directorioCentral.trim().equals("")) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso!", "Falta directorio de la central (PBXPortal)!");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                return false;
            }

            List<TblUrlLlamada> tblUrlLlamadaList = central.getTblUrlLlamadaList();
            if (tblUrlLlamadaList == null || tblUrlLlamadaList.isEmpty() || tblUrlLlamadaList.size() <= 0) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso!", "Falta el servicio que publica la central!");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                return false;

            } else {
                TblUrlLlamada urlLlamar = tblUrlLlamadaList.get(0);// llamar. Buscar servicio para llamar.
                if (urlLlamar == null) {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso!", "Falta el servicio que publica la central!");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                    return false;
                }
                
                this.servicio = urlLlamar.getServicio();
                this.ext = this.usuario.getExtEnsion();
                this.parametro = urlLlamar.getParametro();
                
                if (this.servicio == null || this.servicio.trim().equals("")) {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso!", "Falta el servicio que publica la central!");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                    return false;
                    
                } else if(this.ext == null || this.ext.trim().equals("")){
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso!", "Debe asignar una extensión al usuario!");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                    return false;
                    
                } else if(this.parametro == null || this.parametro.trim().equals("")){
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso!", "URL Llamada debe tener configurado un parametro!");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                    return false;
                }
            }
        }
        
        return true;

    }

}//end
