/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.controller;

import com.inversa.cobros.ejb.ContactoService;
import com.inversa.cobros.ejb.DeudorService;
import com.inversa.cobros.ejb.GestionService;
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

    @Inject
    private GestionService ejbLocal;

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

    /**
     *
     */
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
                boolean isTrueFechaPago = this.promesaList.get(index).getFechaPago().equals(this.selectedPromesa.getFechaPago()) ? true : false;
                boolean isTrueMtopago = this.promesaList.get(index).getMtopago().equals(this.selectedPromesa.getMtopago()) ? true : false;
                boolean isTrueMoneda = this.promesaList.get(index).getMoneda().equals(this.selectedPromesa.getMoneda()) ? true : false;
                boolean isTrueOperacion = true;
                if (this.promesaList.get(index).getOperacion() != null) {
                    isTrueOperacion = this.promesaList.get(index).getOperacion().equals(this.selectedPromesa.getOperacion()) ? true : false;
                }

                if (isTrueTelefono && isTrueFechaPago && isTrueMtopago && isTrueMoneda && isTrueOperacion) {
                    this.promesaList.remove(index);
                    this.selectedPromesa = null;
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Promesa Removida"));
                    PrimeFaces.current().ajax().update("form:messages", "form:tblPromesa");
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
        System.out.println("Numero Seleccionado callToNumber: "+callToNumber.getCallToNumber());
        
        if (callToNumber != null && callToNumber.getCallToNumber() != null && !callToNumber.getCallToNumber().trim().equals("")) {
            //this.selectedLlamada.setIdLlamada(null);
            String telefono = callToNumber.getCallToNumber();

            String URL_LLAMAR = "http://192.168.7.201/PBXPortal/llamar.php?ext=118&numero=987356220";
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

                        String URL_CONSULTAR = "http://192.168.7.201/PBXPortal/consultar.php?call_log_id=" + callLogId;

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
                                        promesas.add(this.promesaList.get(i));
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
            PrimeFaces.current().ajax().update("form:messages", "form:tblTelefono");

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

                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso!", "Teléfono Eliminado. Correcto!"));
                PrimeFaces.current().ajax().update("form:messages", "form:tblTelefono");

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
            this.llamadaList = new ArrayList<TblLlamada>();
            this.promesaList = new ArrayList<TblPromesa>();
            this.gestion = this.ejbLocal.findById(gestion);
            this.llamadaList = this.gestion.getTblLlamadaList();

            for (int index = 0; index < this.llamadaList.size(); index++) {
                List<TblPromesa> promesas = this.llamadaList.get(index).getTblPromesaList();
                if (promesas != null && !promesas.isEmpty() && promesas.size() > 0) {
                    this.promesaList.addAll(promesas);
                }
            }
        }
    }

}
