/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.controller;

import com.inversa.cobros.ejb.ContactoService;
import com.inversa.cobros.ejb.DeudorService;
import com.inversa.cobros.ejb.GestionService;
import com.inversa.cobros.ejb.PromesaService;
import com.inversa.cobros.ejb.TelefonoService;
import com.inversa.cobros.model.Razonmora;
import com.inversa.cobros.model.Subtipificacion;
import com.inversa.cobros.model.TblContacto;
import com.inversa.cobros.model.TblDeudor;
import com.inversa.cobros.model.TblGestion;
import com.inversa.cobros.model.TblLlamada;
import com.inversa.cobros.model.TblPromesa;
import com.inversa.cobros.model.TblTelefono;
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
import org.primefaces.shaded.json.JSONObject;

/**
 *
 * @author Z420WK
 */
@Named
@ViewScoped
public class GestionController implements Serializable {
    
    //private static String ip_publica = "192.168.7.201";    
    private static String ip_publica = "190.106.65.237";    
    private static String http = "http://";
    //private static String http = "https://";
    private static String telefonoDefault = "71723220";
    
    @Inject
    private GestionService ejbLocal;

    @Inject
    private PromesaService ejbPromesaLocal;

    @Inject
    private ContactoService ejbContactoLocal;

    @Inject
    private DeudorController deudorController;

    @Inject
    private DeudorService ejbDeudorLocal;

    @Inject
    private TelefonoService ejbTelefonoLocal;

    @Inject
    private TipificacionController tipificacionController;

    private TblDeudor deudor;
    private List<TblTelefono> telefonos;

    private TblGestion gestion;
    private List<TblPromesa> promesaList;
    private List<TblLlamada> llamadaList;
    private TblLlamada selectedLlamada;
    private String clienteOperacion;

    private List<TblDeudor> deudorList;

    private TblPromesa selectedPromesa;
    private Calendar fechaHoy;

    private TblUsuario usuario;

    @PostConstruct
    public void init() {

        this.llamadaList = new ArrayList<TblLlamada>();

        // Usuario de session...
        this.usuario = (TblUsuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        this.fechaHoy = Calendar.getInstance();

        this.gestion = new TblGestion();
        this.deudor = deudorController.getDeudor();

        if (this.deudor != null) {
            String codigoCartera = this.deudor.getCodigoCartera();
            String codigoGestor = usuario.getCodigoGestor();
            String documento = this.deudor.getDocumento();
            this.contactoABuscar(documento);

            // info de la gestion *******************
            this.gestion.setCodigoCartera(codigoCartera);
            this.gestion.setNombreCliente(this.deudor.getNombre());
            this.gestion.setDocumento(documento);
            this.gestion.setCodigoGestor(codigoGestor);
            this.gestion.setSaldo(this.deudor.getSaldo());
            this.gestion.setMoneda(null);
            this.gestion.setFechaGestion(this.fechaHoy.getTime());// fecha Hoy...
            this.gestion.setUsuarioingreso(this.usuario.getUsuario());
            this.gestion.setFechaingreso(this.fechaHoy.getTime());// fecha Hoy...
            //this.gestion.setDescripcion("");
            //***************************************

            TblDeudor objDeudor = new TblDeudor();
            objDeudor.setCodigoCartera(codigoCartera);
            objDeudor.setCodigoGestor(codigoGestor);
            objDeudor.setDocumento(documento);
            this.deudorList = this.ejbDeudorLocal.findByCarteraGestorDocumento(objDeudor);

        }

        this.promesaList = new ArrayList<TblPromesa>();
        this.telefono = new TblTelefono();

        this.tipificacionController.setIsDisabledPromesa(true);

        this.mtoSaldoOperacion = new BigDecimal(BigInteger.ZERO);
        this.mtoDescuentoPromesa = new BigDecimal(BigInteger.ZERO);
        this.mtoSaldoPromesa = new BigDecimal(BigInteger.ZERO);

    }

    public TblGestion getGestion() {
        return gestion;
    }

    public void setGestion(TblGestion gestion) {
        this.gestion = gestion;
    }

    public TblDeudor getDeudor() {
        return deudor;
    }

    public void setDeudor(TblDeudor deudor) {
        this.deudor = deudor;
    }

    public List<TblTelefono> getTelefonos() {
        return telefonos;
    }

    public void setTelefonos(List<TblTelefono> telefonos) {
        this.telefonos = telefonos;
    }

    public List<TblDeudor> getDeudorList() {
        return deudorList;
    }

    public void setDeudorList(List<TblDeudor> deudorList) {
        this.deudorList = deudorList;
    }

    /*    
    Promesas
     */
    public List<TblPromesa> getPromesaList() {
        return promesaList;
    }

    public void setPromesaList(List<TblPromesa> promesaList) {
        this.promesaList = promesaList;
    }

    /*
    public void onAddNewPromesa() {
        // Add one new promesa to the list:
        if (this.selectedLlamada != null) {
            TblPromesa promesa = new TblPromesa();

            if (this.usuario != null) {
                promesa.setUsuarioingreso(this.usuario.getUsuario());
            }
            promesa.setIdGestion(this.gestion);
            promesa.setIdLlamada(this.llamada_En_Proceso);
            promesa.setOperacion(null);
            promesa.setTelefono(this.selectedLlamada.getCallToNumber());
            promesa.setFechaPago(this.fechaHoy.getTime());
            promesa.setMtopago(BigDecimal.ZERO);
            promesa.setMoneda("CRC");
            promesa.setEstado("ING");
            promesa.setFechaingreso(this.fechaHoy.getTime());
            this.promesaList.add(promesa);
            FacesMessage msg = new FacesMessage("Promesa Agregada: ", promesa.getTelefono());
            FacesContext.getCurrentInstance().addMessage(null, msg);

        } else {
            FacesMessage msg = new FacesMessage("Promesa NO Agregada!: ", null);
            FacesContext.getCurrentInstance().addMessage(null, msg);

        }
    }
     */
    public void onRowEdit(RowEditEvent<TblPromesa> event) {
        FacesMessage msg = new FacesMessage("Promesa Editada", event.getObject().getFechaPago().toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowCancel(RowEditEvent<TblPromesa> event) {
        FacesMessage msg = new FacesMessage("Edición Cancelada", event.getObject().getFechaPago().toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public TblPromesa getSelectedPromesa() {
        return selectedPromesa;
    }

    public void setSelectedPromesa(TblPromesa selectedPromesa) {
        this.selectedPromesa = selectedPromesa;
    }

    /**
     *
     */
    public void deletePromesa() {
        for (int index = 0; index < this.promesaList.size(); index++) {
            if (this.selectedPromesa != null && (this.promesaList != null && this.promesaList.size() > 0)) {

                boolean isTrueTelefono = this.promesaList.get(index).getTelefono().equals(this.selectedPromesa.getTelefono()) ? true : false;
                boolean isTrueOperacion = true;
                boolean isTrueTipoDescuento = this.promesaList.get(index).getTipodescuento().equals(this.selectedPromesa.getTipodescuento()) ? true : false;
                boolean isTrueMontoPorcentaje = this.promesaList.get(index).getMtoporcentaje().equals(this.selectedPromesa.getMtoporcentaje()) ? true : false;
                boolean isTrueMtopago = this.promesaList.get(index).getMtopago().equals(this.selectedPromesa.getMtopago()) ? true : false;
                boolean isTrueFechaPago = this.promesaList.get(index).getFechaPago().equals(this.selectedPromesa.getFechaPago()) ? true : false;
                boolean isTrueEstado = this.promesaList.get(index).getEstado().equals(this.selectedPromesa.getEstado()) ? true : false;
                boolean isTrueTipoArreglo = this.promesaList.get(index).getEstado().equals(this.selectedPromesa.getEstado()) ? true : false;
                boolean isTrueMoneda = this.promesaList.get(index).getMoneda().equals(this.selectedPromesa.getMoneda()) ? true : false;

                if (this.promesaList.get(index).getOperacion() != null) {
                    isTrueOperacion = this.promesaList.get(index).getOperacion().equals(this.selectedPromesa.getOperacion()) ? true : false;
                }

                if (isTrueTelefono && isTrueOperacion && isTrueTipoDescuento && isTrueMontoPorcentaje && isTrueMtopago && isTrueFechaPago && isTrueEstado && isTrueTipoArreglo && isTrueMoneda) {

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
                    PrimeFaces.current().ajax().update("formGestion:messages", "formGestion:tblPromesa");
                }
            }
        }
    }

    /**
     *
     */
    public void cancelarPromesas() {
        if (this.promesaList != null && this.promesaList.size() > 0 && !this.promesaList.isEmpty()) {
            this.promesaList.clear();
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

    private static Client cliente;
    private static WebTarget webTarget;
    private TblLlamada llamada_En_Proceso;

    /**
     *
     */
    public void generarLlamada(TblLlamada callToNumber) {
        callToNumber.setIdLlamada(null);
        System.out.println("Numero Seleccionado callToNumber: " + callToNumber.getCallToNumber());

        if (callToNumber != null && callToNumber.getCallToNumber() != null && !callToNumber.getCallToNumber().trim().equals("")) {
            //this.selectedLlamada.setIdLlamada(null);
            this.selectedLlamada = callToNumber;// llamada seleccionada...
            String telefono = callToNumber.getCallToNumber();
            
            String URL_LLAMAR = this.http + this.ip_publica + "/PBXPortal/llamar.php?ext=118&numero=9"+this.telefonoDefault;
            
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

        }// if

        this.selectedLlamada = null;
    }

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
                        
                        String URL_CONSULTAR = this.http + this.ip_publica+"/PBXPortal/consultar.php?call_log_id=" + callLogId;

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

    public void guardarGestion() {
        boolean isTrue = guardarGestion(true);
    }

    /**
     *
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

                    this.gestion.setEstado("ING");
                    this.gestion.setFechaingreso(this.fechaHoy.getTime());
                    this.ejbLocal.insert(this.gestion);
                    this.actualizarTelefonoContacto(llamadaConDatosList);
                    this.cargarGestionActual(this.gestion);
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso!", "Gestión Registrada. Correcto!"));

                } else {// actualizar gestion...

                    if (usuario != null) {// agregar usuario sesion...
                        this.gestion.setUsuariomodifico(usuario.getUsuario());// usuario que esta registrando la gestion
                    }

                    this.gestion.setFechamodifico(this.fechaHoy.getTime());
                    this.ejbLocal.update(this.gestion);
                    this.actualizarTelefonoContacto(llamadaConDatosList);
                    this.cargarGestionActual(this.gestion);
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

    /*
    ***************************************************************************
    ***************************************************************************
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

    /*
    ***************************************************************************
    ***************************************************************************
     */
    private TblContacto contacto;

    private TblTelefono telefono;

    private Tipotelefono tipo;
    private List<Tipotelefono> tipoList;

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

    public List<Tipotelefono> getTipoList() {
        return tipoList;
    }

    public void setTipoList(List<Tipotelefono> tipoList) {
        this.tipoList = tipoList;
    }

    public TblContacto getContacto() {
        return contacto;
    }

    public void setContacto(TblContacto contacto) {
        this.contacto = contacto;
    }

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
     */
    public void onOperacionPromesaChange() {
        if (this.clienteOperacion != null && !clienteOperacion.trim().equals("")) {
            if (this.deudorList != null && !this.deudorList.isEmpty() && this.deudorList.size() > 0) {
                for (int index = 0; index < this.deudorList.size(); index++) {
                    if (this.deudorList.get(index).getClienteOperacion().equals(this.clienteOperacion)) {
                        this.mtoSaldoOperacion = this.deudorList.get(index).getSaldo();
                    } //if
                } //for
            } //if
        } //if
    }

    public String getClienteOperacion() {
        return clienteOperacion;
    }

    public void setClienteOperacion(String clienteOperacion) {
        this.clienteOperacion = clienteOperacion;
    }

    /*
Promesa.
Hacer arreglo de pago    
     */
    private BigDecimal mtoSaldoOperacion;
    private String tipoDescuentoPromesa;
    private BigDecimal mtoDescuentoPromesa;
    private BigDecimal mtoSaldoPromesa;
    private Date fechaPagoPromesa;
    private String cuotas;

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

    public void validarSelectedLlamada() {
        boolean isTrue = this.validarSelectedLlamada(true);
    }

    public boolean validarSelectedLlamada(boolean isTrue) {

        if (this.selectedLlamada != null) {
            String telefono = this.selectedLlamada.getCallToNumber();
            if (telefono == null || telefono.trim().equals("")) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso!", "Debe hacer una Llamada!");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                return false;
            }

        } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso!", "Debe hacer una Llamada!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return false;
        }

        return true;
    }

    /*
*******************************************************************************
Arreglo de Pago    
*******************************************************************************    
     */
    /**
     *
     */
    public void agregarCancelacionTotal() {
        if (this.selectedLlamada != null) {

            if (this.validArregloPago()) {
                TblPromesa promesa = new TblPromesa();

                if (this.usuario != null) {
                    promesa.setUsuarioingreso(this.usuario.getUsuario());
                }

                promesa.setIdGestion(this.gestion);
                promesa.setIdLlamada(this.llamada_En_Proceso);
                promesa.setOperacion(this.clienteOperacion);
                promesa.setTelefono(this.selectedLlamada.getCallToNumber());
                promesa.setFechaPago(this.fechaPagoPromesa);
                promesa.setMtopago(this.mtoSaldoPromesa);
                promesa.setMoneda("CRC");
                promesa.setEstado("SEG"); // Seguimiento
                promesa.setTipodescuento(this.tipoDescuentoPromesa); // Tipo Descuento: Monto Fijo o Porcentaje.
                promesa.setMtoporcentaje(this.mtoDescuentoPromesa); // Monto o %
                promesa.setTipoarreglopago("CAT");//CAT = Cancelacion Total.
                promesa.setFechaingreso(this.fechaHoy.getTime());

                if (this.existOneCTC(promesa) && this.existOneREF(promesa) && this.validOnlyOneCAT(promesa) && this.existOnePAP(promesa)) {
                    this.promesaList.add(promesa);
                    FacesMessage msg = new FacesMessage("Promesa Agregada: ", promesa.getTelefono());
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }

            }

        } else {
            FacesMessage msg = new FacesMessage("Promesa NO Agregada!: ", null);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void agregarCancelacionTotalPorCuotas() {
        if (this.selectedLlamada != null) {

            if (this.validArregloPago() && this.validCancelacionTotalPorCuotas()) {

                Calendar fechaInicial = Calendar.getInstance();
                fechaInicial.setTime(this.fechaPagoPromesa);

                BigDecimal coutasInt = new BigDecimal(this.cuotas);
                BigDecimal saldo = this.mtoSaldoPromesa.divide(coutasInt, 6, RoundingMode.HALF_UP);

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
                    promesa.setMoneda("CRC");
                    promesa.setEstado("SEG"); // Seguimiento
                    promesa.setTipodescuento(this.tipoDescuentoPromesa); // Tipo Descuento: Monto Fijo o Porcentaje.
                    promesa.setMtoporcentaje(this.mtoDescuentoPromesa); // Monto o %
                    promesa.setTipoarreglopago("CTC");//CTC = Cancelacion Total por Cuotas.
                    promesa.setFechaingreso(this.fechaHoy.getTime());

                    if (this.existOneCAT(promesa) && this.existOneREF(promesa) && this.validOnlyThreeCTC(promesa) && this.existOnePAP(promesa)) {
                        this.promesaList.add(promesa);
                        FacesMessage msg = new FacesMessage("Promesa Agregada: ", promesa.getTelefono());
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    }
                }
            }

        } else {
            FacesMessage msg = new FacesMessage("Promesa NO Agregada!: ", null);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void agregarRefinanciamiento() {
        if (this.selectedLlamada != null) {
            if (this.validRefinanciamiento()) {

                Calendar fechaInicial = Calendar.getInstance();
                fechaInicial.setTime(this.fechaPagoPromesa);

                BigDecimal coutasInt = new BigDecimal(this.cuotas);
                BigDecimal coutasIntLessOne = coutasInt.subtract(BigDecimal.ONE);// restar una cuota.
                BigDecimal primerTracto = this.mtoDescuentoPromesa;// primer tracto
                BigDecimal saldo = this.mtoSaldoPromesa.divide(coutasIntLessOne, 6, RoundingMode.HALF_UP);// saldo restante

                // borra las promesas...
                this.deleteByOperacionAndArregloPago(this.clienteOperacion, "REF");

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

                    promesa.setMoneda("CRC");
                    promesa.setEstado("SEG"); // Seguimiento
                    promesa.setTipodescuento(null); // Tipo Descuento: Monto Fijo o Porcentaje.
                    promesa.setMtoporcentaje(BigDecimal.ZERO); // Monto o %
                    promesa.setTipoarreglopago("REF");//REF = Refinanciamiento.
                    promesa.setFechaingreso(this.fechaHoy.getTime());

                    if (this.existOneCAT(promesa) && this.validOnlyThreeCTC(promesa) && this.existOneCTC(promesa) && this.existOnePAP(promesa)) {
                        this.promesaList.add(promesa);
                        FacesMessage msg = new FacesMessage("Promesa Agregada: ", promesa.getTelefono());
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    }
                }
            }

        } else {
            FacesMessage msg = new FacesMessage("Promesa NO Agregada!: ", null);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    /**
     *
     */
    public void agregarPagoParcial() {
        if (this.selectedLlamada != null) {

            if (this.validPagoParcial()) {

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
                promesa.setMtopago(this.mtoSaldoPromesa);
                promesa.setMoneda("CRC");
                promesa.setEstado("SEG"); // Seguimiento
                promesa.setTipodescuento(null); // Tipo Descuento: Monto Fijo o Porcentaje.
                promesa.setMtoporcentaje(BigDecimal.ZERO); // Monto o %
                promesa.setTipoarreglopago("PAP");//CTC = Cancelacion Total por Cuotas.
                promesa.setFechaingreso(this.fechaHoy.getTime());

                if (this.existOneCAT(promesa) && this.existOneREF(promesa) && this.existOneCTC(promesa) && this.existOnePAP(promesa)) {
                    this.promesaList.add(promesa);
                    FacesMessage msg = new FacesMessage("Promesa Agregada: ", promesa.getTelefono());
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }

            }

        } else {
            FacesMessage msg = new FacesMessage("Promesa NO Agregada!: ", null);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public boolean validPagoParcial() {
        if (this.clienteOperacion == null || this.clienteOperacion.trim().equals("")) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso!", "Debe seleccionar una Operación!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return false;

        } else if (this.mtoSaldoPromesa == null || this.mtoSaldoPromesa.compareTo(BigDecimal.ZERO) == 0 || this.mtoSaldoPromesa.compareTo(BigDecimal.ZERO) == -1) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso!", "Monto Promesa debe ser mayor a cero!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return false;

        } else if (this.fechaPagoPromesa == null) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso!", "Debe seleccionar Fecha Pago!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return false;
        }

        return true;

    }

    /**
     * Remueve las promesas, segun operacion y tipo de arrglo de pago.
     *
     * @param operacion
     * @param arregloPago
     */
    public void deleteByOperacionAndArregloPago(String operacion, String arregloPago) {
        int index = 0;
        while (this.promesaList.size() > 0 && this.promesaList.size() > index) {
            boolean isOperacion = this.promesaList.get(index).getOperacion().equals(operacion);
            boolean isAP = this.promesaList.get(index).getTipoarreglopago().equals(arregloPago);
            if (isOperacion && isAP) {
                this.promesaList.remove(index);
            } else {
                index++;
            }
        }
    }

    /**
     *
     */
    public void calcSaldoRestanteREF() {
        BigDecimal saldoRestante = new BigDecimal(BigInteger.ZERO);
        BigDecimal mtoSaldo = this.mtoSaldoOperacion;
        BigDecimal primerTracto = this.mtoDescuentoPromesa;

        if (mtoSaldo != null && primerTracto != null) {
            if (mtoSaldo.compareTo(BigDecimal.ZERO) == 1) {// mayor que cero.
                if (primerTracto.compareTo(BigDecimal.ZERO) == 1) {// mayor que cero.
                    saldoRestante = mtoSaldo.subtract(primerTracto);
                } else {
                    saldoRestante = mtoSaldo;
                }
            }
        }

        this.mtoSaldoPromesa = saldoRestante;
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

    /**
     * Validar los datos del Arreglo de pago. Cancelacion Total
     *
     * @return
     */
    private boolean validArregloPago() {

        if (this.clienteOperacion == null || this.clienteOperacion.trim().equals("")) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso!", "Debe seleccionar una Operación!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return false;

        } else if (this.tipoDescuentoPromesa == null || this.tipoDescuentoPromesa.equals("")) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso!", "Debe seleccionar una Tipo Descuento!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return false;

        } else if (this.mtoDescuentoPromesa == null || this.mtoDescuentoPromesa.equals(BigDecimal.ZERO)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso!", "Debe digitar Monto o %!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return false;

        } else if (this.mtoSaldoPromesa == null || this.mtoSaldoPromesa.compareTo(BigDecimal.ZERO) == 0 || this.mtoSaldoPromesa.compareTo(BigDecimal.ZERO) == -1) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso!", "Monto Promesa debe ser mayor a cero!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return false;

        } else if (this.fechaPagoPromesa == null) {
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
     *
     * @return
     */
    private boolean validRefinanciamiento() {

        if (this.clienteOperacion == null || this.clienteOperacion.trim().equals("")) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso!", "Debe seleccionar una Operación!");
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

        } else if (this.fechaPagoPromesa == null) {
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

}
