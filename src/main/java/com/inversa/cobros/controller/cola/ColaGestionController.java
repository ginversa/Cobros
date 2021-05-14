/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.controller.cola;

import com.inversa.cobros.constante.comun.ConstanteComun;
import com.inversa.cobros.controller.CarteraController;
import com.inversa.cobros.controller.GestionController;
import com.inversa.cobros.controller.LlamarController;
import com.inversa.cobros.controller.PagosHistorialController;
import com.inversa.cobros.controller.TipificacionController;
import com.inversa.cobros.ejb.ArreglopagoService;
import com.inversa.cobros.ejb.CarteraService;
import com.inversa.cobros.ejb.ColaService;
import com.inversa.cobros.ejb.ContactoService;
import com.inversa.cobros.ejb.CorreoService;
import com.inversa.cobros.ejb.EstadopromesaService;
import com.inversa.cobros.ejb.GestionService;
import com.inversa.cobros.ejb.LlamadaService;
import com.inversa.cobros.ejb.MonedaService;
import com.inversa.cobros.ejb.PromesaService;
import com.inversa.cobros.ejb.SaldoService;
import com.inversa.cobros.ejb.SubtipificacionService;
import com.inversa.cobros.ejb.TelefonoService;
import com.inversa.cobros.ejb.TipificacionService;
import com.inversa.cobros.model.Arreglopago;
import com.inversa.cobros.model.Estadopromesa;
import com.inversa.cobros.model.Moneda;
import com.inversa.cobros.model.Razonmora;
import com.inversa.cobros.model.Subtipificacion;
import com.inversa.cobros.model.TblCartera;
import com.inversa.cobros.model.TblCentral;
import com.inversa.cobros.model.TblCliente;
import com.inversa.cobros.model.TblCola;
import com.inversa.cobros.model.TblContacto;
import com.inversa.cobros.model.TblCorreo;
import com.inversa.cobros.model.TblGestion;
import com.inversa.cobros.model.TblSaldo;
import com.inversa.cobros.model.TblLlamada;
import com.inversa.cobros.model.TblPrefijoSalida;
import com.inversa.cobros.model.TblPromesa;
import com.inversa.cobros.model.TblResultadogestion;
import com.inversa.cobros.model.TblResultadotercero;
import com.inversa.cobros.model.TblTelefono;
import com.inversa.cobros.model.TblUrlLlamada;
import com.inversa.cobros.model.TblUsuario;
import com.inversa.cobros.model.Tipificacion;
import com.inversa.cobros.model.Tipodescuento;
import com.inversa.cobros.model.Tipotelefono;
import com.inversa.cobros.util.FechaOperacion;
import com.inversa.findme.controller.FindmeController;
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
import javax.enterprise.context.SessionScoped;
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
import org.primefaces.component.tabview.TabView;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.shaded.json.JSONObject;

/**
 *
 * @author Z420WK
 */
@Named
@SessionScoped
public class ColaGestionController implements Serializable {

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

    //private static String telefonoDefault = "87356220";// 64801981
    private String ext = null;
    private String numeroSalida = null;

    private static Client cliente;
    private static WebTarget webTarget;

    @Inject
    private GestionService ejbLocal;

    @Inject
    private CarteraService ejbCarteraLocal;

    @Inject
    private SaldoService ejbSaldoLocal;

    @Inject
    private PromesaService ejbPromesaLocal;

    @Inject
    private LlamadaService ejbLlamadaLocal;

    @Inject
    private TipificacionService ejbTipificacionLocal;

    @Inject
    private SubtipificacionService ejbSubtipificacionLocal;

    @Inject
    private EstadopromesaService ejbEstadopromesaLocal;

    @Inject
    private ArreglopagoService ejbArreglopagoLocal;

    @Inject
    private ContactoService ejbContactoLocal;

    @Inject
    private TelefonoService ejbTelefonoLocal;

    @Inject
    private CorreoService ejbCorreoLocal;

    @Inject
    private MonedaService ejbMonedaService;

    @Inject
    private ColaService ejbColaLocal;

    @Inject
    private LlamarController llamarController;

    @Inject
    private GestionController gestionController;

    @Inject
    private CarteraController carteraController;

    @Inject
    private TipificacionController tipificacionController;

    @Inject
    private FindmeController findmeController;

    @Inject
    private PagosHistorialController pagosHistorialController;

    @Inject
    private ListarColaController listarColaController;

    private TblGestion gestion;
    private List<TblLlamada> llamadaList;
    private TblLlamada selectedLlamada;
    private List<TblTelefono> telefonos;

    private List<TblCartera> carteraList;
    private TblCartera operacionSelected;

    private Calendar fechaHoy;
    private TblUsuario usuario;

    // Saldo a cobrar, es requerido si aplica ley de usuara.
    private boolean leyusuraIsRequired;
    private boolean leyusuraDisabled;
    private BigDecimal mtoSaldoGestionCRC;

    private boolean isVisibleCancelacionTotalPorCuotas = false;

    private TabView tabView;

    private TblCola colaSelected;
    

    @PostConstruct
    public void init() {

        this.llamadaList = new ArrayList<>();
        this.selectedLlamada = new TblLlamada();
        this.operacionSelected = new TblCartera();

        this.usuario = (TblUsuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        this.fechaHoy = Calendar.getInstance();

        this.setCarteraTOGestion(this.carteraController.getCartera());
        
        if(this.colaSelected == null){
            this.colaSelected = this.listarColaController.getColaSelected();
        }

        this.telefono = new TblTelefono();
        this.correoElectronico = new TblCorreo();
        this.tipificacionController.setIsDisabledPromesa(true);

        String codigo_cliente = this.gestion.getCodigo_cliente();
        if (codigo_cliente != null && !codigo_cliente.trim().equals("")) {
            if (codigo_cliente.trim().equals(ConstanteComun.Credomatic)) {// Credomatic
                this.isVisibleCancelacionTotalPorCuotas = true;

            } else if (codigo_cliente.trim().equals(ConstanteComun.Davivienda)) {//Davivienda
                this.isVisibleCancelacionTotalPorCuotas = false;// deshabilitar el tab, Cancelación total por cuotas

            } else {
                this.isVisibleCancelacionTotalPorCuotas = true;
            }

        } else {
            this.isVisibleCancelacionTotalPorCuotas = true;
        }

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

    public TblCartera getOperacionSelected() {
        return operacionSelected;
    }

    public void setOperacionSelected(TblCartera operacionSelected) {
        this.operacionSelected = operacionSelected;
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

    public boolean isIsVisibleCancelacionTotalPorCuotas() {
        return isVisibleCancelacionTotalPorCuotas;
    }

    public void setIsVisibleCancelacionTotalPorCuotas(boolean isVisibleCancelacionTotalPorCuotas) {
        this.isVisibleCancelacionTotalPorCuotas = isVisibleCancelacionTotalPorCuotas;
    }

    public BigDecimal getMtoSaldoGestionCRC() {
        return mtoSaldoGestionCRC;
    }

    public void setMtoSaldoGestionCRC(BigDecimal mtoSaldoGestionCRC) {
        this.mtoSaldoGestionCRC = mtoSaldoGestionCRC;
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
    Seleccciona un registro de Cartera.
    selecciona una operacion.
     */
    public void onRowSelect(SelectEvent<TblCartera> event) {
        TblCartera operacion = event.getObject();
        this.setCarteraTOGestion(operacion);

        List<TblPromesa> promesasPorOperacion = this.ejbPromesaLocal.findPromesaPorOperacion(operacion.getCodigoCartera(), operacion.getIdentificacion(), operacion.getNumeroCuenta());
        this.getGestion().setTblPromesaList(promesasPorOperacion);

        FacesMessage msg = new FacesMessage("Operación seleccionada!", String.valueOf(operacion.getNumeroCuenta()));
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
            if (this.gestion == null || this.gestion.getIdGestion() == null) {
                this.gestion = new TblGestion();
                this.gestion.setCodigoCartera(objCartera.getCodigoCartera());
                this.gestion.setIdentificacion(objCartera.getIdentificacion());
                this.gestion = this.ejbLocal.findByCodigoCarteraANDIdentificacion(gestion);
            }

            if (this.gestion == null || this.gestion.getIdGestion() == null) {
                this.gestion = new TblGestion();
                List<TblPromesa> promesas = new ArrayList<>();
                List<TblLlamada> llamadas = new ArrayList<>();
                this.gestion.setTblLlamadaList(llamadas);
                this.gestion.setTblPromesaList(promesas);

            } else {

                if (this.gestion.getTblLlamadaList() == null) {
                    List<TblLlamada> llamadas = new ArrayList<>();
                    this.gestion.setTblLlamadaList(llamadas);
                }

                if (this.gestion.getTblPromesaList() == null) {
                    List<TblPromesa> promesas = new ArrayList<>();
                    this.gestion.setTblPromesaList(promesas);
                }
            }

            this.setOperacionSelected(objCartera);
            if (this.getSelectedLlamada() == null) {
                TblLlamada newLlamada = new TblLlamada();
                this.setSelectedLlamada(newLlamada);
                this.getSelectedLlamada().setOperacion(objCartera.getNumeroCuenta());
                this.getSelectedLlamada().setTblPromesaList(null);
            } else {
                this.getSelectedLlamada().setOperacion(objCartera.getNumeroCuenta());
                this.getSelectedLlamada().setTblPromesaList(null);
            }

            String codigoCartera = objCartera.getCodigoCartera();
            String codigoGestor = usuario.getCodigoGestor();
            String identificacion = objCartera.getIdentificacion();
            this.contactoABuscar(identificacion);

            // info de la gestion *******************
            this.gestion.setCodigoCartera(codigoCartera);
            if (objCartera.getIdCliente() != null) {
                this.gestion.setNombre_cartera(objCartera.getIdCliente().getNombre());
                this.gestion.setCodigo_cliente(objCartera.getIdCliente().getCodigo());
            }

            this.gestion.setIdentificacion(identificacion);
            this.gestion.setNombreCliente(objCartera.getNombreCliente());

            // Saldo e interese en colones y dolares...
            this.gestion.setCodigoGestor(codigoGestor);
            this.gestion.setFechaGestion(this.fechaHoy.getTime());// fecha Hoy...
            this.gestion.setUsuarioingreso(this.usuario.getUsuario());
            this.gestion.setFechaingreso(this.fechaHoy.getTime());// fecha Hoy...

            if (this.operacionSelected.getLeyusura() != null && this.operacionSelected.getLeyusura().equals("1")) {
                this.setLeyusuraIsRequired(true);
                this.setLeyusuraDisabled(false);

            } else {
                this.setLeyusuraIsRequired(false);
                this.setLeyusuraDisabled(true);
            }

            this.cargarCarteraList();

            TblCliente cliente = this.getOperacionSelected().getIdCliente();
            if (cliente != null) {
                this.prefijoSalidaList = cliente.getTblPrefijoSalidaList();
            }

        }

        this.mtoSaldoOperacion = new BigDecimal(BigInteger.ZERO);
        this.mtoDescuentoPromesa = new BigDecimal(BigInteger.ZERO);
        this.mtoSaldoPromesa = new BigDecimal(BigInteger.ZERO);
        this.mtoSaldoOperacionUSD = new BigDecimal(BigInteger.ZERO);
        this.mtoDescuentoPromesaUSD = new BigDecimal(BigInteger.ZERO);
        this.mtoSaldoPromesaUSD = new BigDecimal(BigInteger.ZERO);
        this.mtoSaldoGestionCRC = BigDecimal.ZERO;
    }

    /**
     *
     */
    public void cargarCarteraList() {
        TblCartera obj = new TblCartera();
        obj.setCodigoCartera(this.gestion.getCodigoCartera());
        obj.setIdentificacion(this.getOperacionSelected().getIdentificacion());
        this.carteraList = this.ejbCarteraLocal.findByCodigoCarteraAndIdentificacion(obj);

        for (int index = 0; index < this.carteraList.size(); index++) {
            String cod_cartera = this.carteraList.get(index).getCodigoCartera();
            String identificacion = this.carteraList.get(index).getIdentificacion();
            String operacion = this.carteraList.get(index).getNumeroCuenta();
            TblLlamada ultimaLlamada = this.ejbLlamadaLocal.findUltimaLlamada(cod_cartera, identificacion, operacion);
            TblPromesa ultimaPromesa = this.ejbPromesaLocal.findUltimaPromesa(cod_cartera, identificacion, operacion);
            this.carteraList.get(index).setUltimaLlamada(ultimaLlamada);
            this.carteraList.get(index).setUltimaPromesa(ultimaPromesa);
        }
    }

    /**
     * Guarda la informacion de la gestion. Guarda la informacion de las
     * promesas.
     */
    public void finalizarGestion() {

        String operacion = this.operacionSelected.getNumeroCuenta();

        try {

            if (operacion != null && !operacion.trim().equals("")) {
                this.guardarGestion();
                this.carteraController.cargarCartera();
                this.contactoABuscar(this.gestion.getIdentificacion());
                PrimeFaces.current().ajax().update("formGestion:idTabView:tblOperaciones", "formCartera:idTabViewCartera:idDTCartera", "formCartera:idTabViewCartera:pgInfoGestion:tblTelefono");

            } else {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso!", "Debe seleccionar una operación!");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }

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

        try {

            if (this.gestion != null) {

                // validaciones...
                if (this.gestion.getTblLlamadaList() == null || this.gestion.getTblLlamadaList().isEmpty() || this.gestion.getTblLlamadaList().size() <= 0) {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso!", "Debe hacer una Llamada!");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                    return false;

                } else {
                    this.consultarDatosLlamada();
                    this.agregarLlamadaYTipificacion();// agregar llamadas y tipificaciones.

                    if (!this.validarTipificacion()) {
                        return false;
                    }

                }//*****************************************************************************************

                // agregar promesas...
                //*****************************************************************************************
                //this.agregarPromesas();
                //*****************************************************************************************
                /**
                 * ************************************************************
                 * Multitipificacion
                 * *************************************************************
                 */
                this.multitipificacion();
                /*
                 ***************************************************************
                 */

                if (this.gestion.getIdGestion() == null) {

                    this.gestion.setEstado(ConstanteComun.Ingresar);
                    this.gestion.setFechaingreso(this.fechaHoy.getTime());
                    this.gestion.setUsuarioingreso(this.usuario.getUsuario());// usuario que esta registrando la gestion

                    Long idGestion = this.ejbLocal.insert(this.gestion);
                    if (this.gestion.getIdGestion() == null) {
                        this.gestion.setIdGestion(idGestion);
                    }

                    this.actualizarTelefonoContacto(this.gestion.getTblLlamadaList());
                    this.cargarGestionActual(this.gestion);
                    this.actualizarSaldos();
                    this.actualizarEstadoCola();
                    PrimeFaces.current().ajax().update("formGestion","formGestion:idTabView:tblTelefono");
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso!", "Gestión Registrada. Correcto!"));
                    if(this.gestionarSiguienteClienteEnCola()){
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso!", "Gestionar nuevo cliente!"));                        
                    }else{
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso!", "No mas Clientes. Cola Finalizada!"));
                        FacesContext.getCurrentInstance().getExternalContext().redirect("listarCola.xhtml");
                    }                    

                } else {// actualizar gestion...

                    this.gestion.setUsuariomodifico(usuario.getUsuario());// usuario que esta registrando la gestion
                    this.gestion.setFechamodifico(this.fechaHoy.getTime());

                    if (this.gestion.getTblLlamadaList() != null && !this.gestion.getTblLlamadaList().isEmpty()) {
                        for (int index = 0; index < this.gestion.getTblLlamadaList().size(); index++) {
                            TblLlamada objL = this.gestion.getTblLlamadaList().get(index);
                            if (objL.getIdLlamada() == null) {
                                Long idLlamada = this.ejbLlamadaLocal.insert(objL);
                                this.gestion.getTblLlamadaList().get(index).setIdLlamada(idLlamada);
                                String operacion = this.gestion.getTblLlamadaList().get(index).getOperacion();
                                if (this.gestion.getTblPromesaList() != null && !this.gestion.getTblPromesaList().isEmpty()) {
                                    for (int jdex = 0; jdex < this.gestion.getTblPromesaList().size(); jdex++) {
                                        if (this.gestion.getTblPromesaList().get(jdex).getOperacion().equals(operacion) && this.gestion.getTblPromesaList().get(jdex).getIdLlamada().getIdLlamada() == null) {
                                            objL.setIdLlamada(idLlamada);
                                            this.gestion.getTblPromesaList().get(jdex).setIdLlamada(objL);
                                        }
                                    }
                                }//if
                            }//if
                        }//for
                    }//if

                    this.ejbLocal.update(this.gestion);
                    this.actualizarTelefonoContacto(this.gestion.getTblLlamadaList());
                    this.cargarGestionActual(this.gestion);
                    this.actualizarSaldos();
                    this.actualizarEstadoCola();
                    PrimeFaces.current().ajax().update("formGestion","formGestion:idTabView:tblTelefono");
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso!", "Gestión Actulizar. Correcto!"));
                    if(this.gestionarSiguienteClienteEnCola()){
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso!", "Gestionar nuevo cliente!"));
                    }else{
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso!", "No mas Clientes. Cola Finalizada!"));
                        FacesContext.getCurrentInstance().getExternalContext().redirect("listarCola.xhtml");
                    }
                    
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
    public void consultarDatosLlamada() {

        String callLogId_selectedLlamada = this.selectedLlamada.getCallLogId();
        if (callLogId_selectedLlamada == null || callLogId_selectedLlamada.trim().equals("")) {
            for (int i = 0; i < this.gestion.getTblLlamadaList().size(); i++) {
                if (this.gestion.getTblLlamadaList().get(i).getCallLogId() != null && !this.gestion.getTblLlamadaList().get(i).getCallLogId().trim().equals("")) {
                    callLogId_selectedLlamada = this.gestion.getTblLlamadaList().get(i).getCallLogId();
                }
            }
        }

        if (this.gestion.getTblLlamadaList() != null && !this.gestion.getTblLlamadaList().isEmpty()) {
            for (int index = 0; index < this.gestion.getTblLlamadaList().size(); index++) {

                String callLogId = this.gestion.getTblLlamadaList().get(index).getCallLogId();

                if (callLogId == null || callLogId.trim().equals("")) {
                    callLogId = callLogId_selectedLlamada;
                    this.gestion.getTblLlamadaList().get(index).setCallLogId(callLogId);
                }

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

                        String URL_CONSULTAR = null;

                        if (this.http == null || this.ip_publica == null) {
                            URL_CONSULTAR = this.llamarController.crearUrlConsultarLlamada(callLogId);
                        } else {
                            URL_CONSULTAR = this.http + this.ip_publica + "/PBXPortal/consultar.php?call_log_id=" + callLogId;
                        }

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
                                    this.gestion.getTblLlamadaList().get(index).setDateIni(Timestamp.valueOf(date_ini));
                                }

                                boolean isTrue_date_end = !obj.isNull("date_end") ? true : false;
                                if (isTrue_date_end) {
                                    String date_end = obj.getString("date_end");
                                    System.out.println("date_end: " + date_end);
                                    this.gestion.getTblLlamadaList().get(index).setDateEnd(Timestamp.valueOf(date_end));
                                }

                                if (!obj.isNull("call_from_number")) {
                                    String call_from_number = obj.getString("call_from_number");
                                    this.gestion.getTblLlamadaList().get(index).setCallFromNumber(call_from_number);
                                }

                                if (!obj.isNull("call_to_number")) {
                                    String call_to_number = obj.getString("call_to_number");
                                    this.gestion.getTblLlamadaList().get(index).setCallToNumber(call_to_number);
                                }

                                if (!obj.isNull("dialstatus")) {
                                    String dialstatus = obj.getString("dialstatus");
                                    this.gestion.getTblLlamadaList().get(index).setDialstatus(dialstatus);
                                }

                                if (!obj.isNull("conversation_length")) {
                                    String conversation_length = obj.getString("conversation_length");
                                    if (conversation_length != null && !conversation_length.trim().equals("")) {
                                        this.gestion.getTblLlamadaList().get(index).setConversationLength(Integer.valueOf(conversation_length));
                                    } else {
                                        this.gestion.getTblLlamadaList().get(index).setConversationLength(Integer.valueOf(0));
                                    }
                                }

                                this.gestion.getTblLlamadaList().get(index).setIdGestion(this.gestion);
                                this.gestion.getTblLlamadaList().get(index).setEstado(ConstanteComun.Ingresar);
                                this.gestion.getTblLlamadaList().get(index).setUsuarioingreso(this.usuario.getUsuario());
                                this.gestion.getTblLlamadaList().get(index).setFechaingreso(this.fechaHoy.getTime());

                                if (this.gestion.getTblLlamadaList().get(index).getIdTipotelefono() != null && this.gestion.getTblLlamadaList().get(index).getIdTipotelefono().getIdTipotelefono() == null) {
                                    this.gestion.getTblLlamadaList().get(index).setIdTipotelefono(null);
                                }

                                if (this.gestion.getTblLlamadaList().get(index).getIdrazonmora() != null && this.gestion.getTblLlamadaList().get(index).getIdrazonmora().getIdrazonmora() == null) {
                                    this.gestion.getTblLlamadaList().get(index).setIdrazonmora(null);
                                }

                                FechaOperacion fo = new FechaOperacion();
                                Date callLength = fo.restarFechar(this.gestion.getTblLlamadaList().get(index).getDateIni(), this.gestion.getTblLlamadaList().get(index).getDateEnd());
                                this.gestion.getTblLlamadaList().get(index).setCallLength(callLength);

                            }//if                       
                        }

                    }

                }
            }
        }
        //return llamadaConDatosList;
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
            List<TblPromesa> promesasByGestion = this.ejbPromesaLocal.findByGestionAndDifferentDEL(this.gestion.getIdGestion());
            List<TblLlamada> llamadasByGestion = this.ejbLlamadaLocal.findByGestion(this.gestion.getIdGestion());

            this.gestion.setTblLlamadaList(llamadasByGestion);
            this.gestion.setTblPromesaList(promesasByGestion);
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
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Celda modificada", "Viejo: " + oldValue + ", Nuevo:" + newValue);
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        }
    }

    /**
     *
     * @param llamada
     */
    public void generarLlamada(TblLlamada llamada) {

        if (prefijoSalidaSelected == null) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso!", "Debe seleccionar Prefijo Salida!");
            FacesContext.getCurrentInstance().addMessage(null, msg);

        } else {

            if (llamada != null && llamada.getCallToNumber() != null && !llamada.getCallToNumber().trim().equals("")) {

                if (this.validarURL()) {

                    String URL_LLAMAR = this.crearUrlLlamada(llamada.getCallToNumber());//this.telefonoDefault

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

                        if (this.gestion.getTblLlamadaList() != null && !this.gestion.getTblLlamadaList().isEmpty() && this.gestion.getTblLlamadaList().size() > 0) {
                            boolean isTrue = false;
                            for (int index = 0; index < this.gestion.getTblLlamadaList().size(); index++) {

                                boolean isCallToNumber = this.gestion.getTblLlamadaList().get(index).getCallToNumber().equals(llamada.getCallToNumber());
                                boolean isOperacion = this.gestion.getTblLlamadaList().get(index).getOperacion().equals(llamada.getOperacion());

                                if (isCallToNumber && isOperacion) {
                                    this.gestion.getTblLlamadaList().get(index).setCallLogId(jsonExtract);
                                    isTrue = true;
                                    this.getSelectedLlamada().setCallLogId(jsonExtract);
                                }
                            }

                            if (!isTrue) {
                                llamada.setCallLogId(jsonExtract);
                                llamada.setIdLlamada(null);
                                this.getSelectedLlamada().setCallLogId(jsonExtract);
                                //this.addLlamadaToGestion(llamada);
                            }

                        } else {
                            llamada.setCallLogId(jsonExtract);
                            llamada.setIdLlamada(null);
                            this.getSelectedLlamada().setCallLogId(jsonExtract);
                            //this.addLlamadaToGestion(llamada);
                        }
                    }

                }//validarURL

            }// if

        }

        //this.selectedLlamada = null;
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
            PrimeFaces.current().ajax().update("formGestion:messages", "formGestion:idDTCorreoContacto");

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
    private Tipodescuento tipoDescuentoPromesa;
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

    public Tipodescuento getTipoDescuentoPromesa() {
        return tipoDescuentoPromesa;
    }

    public void setTipoDescuentoPromesa(Tipodescuento tipoDescuentoPromesa) {
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
    public void calcNuevoMontoSegunDescuento() {
        BigDecimal cien = new BigDecimal("100");
        BigDecimal newSaldo = new BigDecimal(BigInteger.ZERO);
        BigDecimal mtoSaldo = this.mtoSaldoOperacion;
        BigDecimal mtoPort = this.mtoDescuentoPromesa;

        if (this.tipoDescuentoPromesa != null) {
            if (this.tipoDescuentoPromesa.getCodigo().equals(ConstanteComun.Monto_Fijo)) {
                newSaldo = mtoSaldo.subtract(mtoPort);

            } else if (this.tipoDescuentoPromesa.getCodigo().equals(ConstanteComun.Porcentaje)) {
                if (mtoPort != null && mtoPort.compareTo(BigDecimal.ZERO) > 0) {
                    BigDecimal porcentage = mtoSaldo.multiply(mtoPort).divide(cien);
                    newSaldo = mtoSaldo.subtract(porcentage);
                } else {
                    newSaldo = mtoSaldo;
                }
            }
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

            if (this.validArregloPago(codigoMoneda)) {
                TblPromesa promesa = new TblPromesa();

                if (this.usuario != null) {
                    promesa.setUsuarioingreso(this.usuario.getUsuario());
                }

                Moneda monedaPromesa = new Moneda();
                monedaPromesa.setCodigo(codigoMoneda);
                monedaPromesa = this.ejbMonedaService.findByCodigo(monedaPromesa);

                Estadopromesa estadopromesa = new Estadopromesa();
                estadopromesa.setCodigo(ConstanteComun.Seguimiento);
                estadopromesa = this.ejbEstadopromesaLocal.findByCodigo(estadopromesa);

                Arreglopago arreglopagoPromesa = new Arreglopago();
                arreglopagoPromesa.setCodigo(ConstanteComun.Cancelacion_Total);
                arreglopagoPromesa = this.ejbArreglopagoLocal.findByCodigo(arreglopagoPromesa);

                promesa.setIdGestion(this.gestion);
                promesa.setIdLlamada(this.selectedLlamada);
                promesa.setOperacion(this.selectedLlamada.getOperacion());
                promesa.setTelefono(this.selectedLlamada.getCallToNumber());
                promesa.setFechaPago(this.fechaPagoPromesa);
                promesa.setIdestadopromesa(estadopromesa); // Seguimiento
                promesa.setIdtipodescuento(this.tipoDescuentoPromesa); // Tipo Descuento: Monto Fijo o Porcentaje.
                promesa.setIdarreglopago(arreglopagoPromesa);//CAT = Cancelacion Total.
                promesa.setFechaingreso(this.fechaHoy.getTime());
                promesa.setIdMoneda(monedaPromesa);

                if (codigoMoneda.equals(ConstanteComun.colones)) {
                    promesa.setMtopago(this.mtoSaldoPromesa);
                    promesa.setMtoporcentaje(this.mtoDescuentoPromesa); // Monto o %

                } else if (codigoMoneda.equals(ConstanteComun.dolares)) {
                    promesa.setMtopago(this.mtoSaldoPromesaUSD);
                    promesa.setMtoporcentaje(this.mtoDescuentoPromesaUSD); // Monto o %
                }

                // borra las promesas...                
                this.deleteArregloPago(this.operacionSelected.getNumeroCuenta(), codigoMoneda);

                if (this.gestion.getTblPromesaList() == null) {
                    List<TblPromesa> promesas = new ArrayList<>();
                    this.gestion.setTblPromesaList(promesas);
                }

                this.gestion.getTblPromesaList().add(promesa);
                this.addLlamadaToGestion(this.getSelectedLlamada());

                FacesMessage msg = new FacesMessage("Promesa Agregada: ", promesa.getTelefono());
                FacesContext.getCurrentInstance().addMessage(null, msg);
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

            if (this.validArregloPago(codigoMoneda) && this.validCancelacionTotalPorCuotas()) {

                Moneda monedaPromesa = new Moneda();
                monedaPromesa.setCodigo(codigoMoneda);
                monedaPromesa = this.ejbMonedaService.findByCodigo(monedaPromesa);

                Estadopromesa estadopromesa = new Estadopromesa();
                estadopromesa.setCodigo(ConstanteComun.Seguimiento);
                estadopromesa = this.ejbEstadopromesaLocal.findByCodigo(estadopromesa);

                Arreglopago arreglopagoPromesa = new Arreglopago();
                arreglopagoPromesa.setCodigo(ConstanteComun.Cancelacion_total_por_cuotas);
                arreglopagoPromesa = this.ejbArreglopagoLocal.findByCodigo(arreglopagoPromesa);

                Calendar fechaInicial = Calendar.getInstance();
                fechaInicial.setTime(this.fechaPagoPromesa);

                BigDecimal coutasInt = new BigDecimal(this.cuotas);

                BigDecimal saldo = BigDecimal.ZERO;
                if (codigoMoneda.equals(ConstanteComun.colones)) {
                    saldo = this.mtoSaldoPromesa.divide(coutasInt, 6, RoundingMode.HALF_UP);
                } else if (codigoMoneda.equals(ConstanteComun.dolares)) {
                    saldo = this.mtoSaldoPromesaUSD.divide(coutasInt, 6, RoundingMode.HALF_UP);
                }

                // borra las promesas...
                this.deleteArregloPago(this.operacionSelected.getNumeroCuenta(), codigoMoneda);

                for (int count = 0; count < coutasInt.intValue(); count++) {
                    TblPromesa promesa = new TblPromesa();

                    if (this.usuario != null) {
                        promesa.setUsuarioingreso(this.usuario.getUsuario());
                    }

                    promesa.setIdGestion(this.gestion);
                    promesa.setIdLlamada(this.selectedLlamada);
                    promesa.setOperacion(this.selectedLlamada.getOperacion());
                    promesa.setTelefono(this.selectedLlamada.getCallToNumber());
                    promesa.setFechaPago(fechaInicial.getTime());
                    fechaInicial.set(Calendar.MONTH, fechaInicial.get(Calendar.MONTH) + 1);
                    promesa.setIdestadopromesa(estadopromesa); // Seguimiento
                    promesa.setIdtipodescuento(this.tipoDescuentoPromesa); // Tipo Descuento: Monto Fijo o Porcentaje.
                    promesa.setIdarreglopago(arreglopagoPromesa);//CTC = Cancelacion Total por Cuotas.
                    promesa.setFechaingreso(this.fechaHoy.getTime());
                    promesa.setIdMoneda(monedaPromesa);
                    promesa.setMtopago(saldo);

                    if (codigoMoneda.equals(ConstanteComun.colones)) {
                        promesa.setMtoporcentaje(this.mtoDescuentoPromesa); // Monto o %
                    } else if (codigoMoneda.equals(ConstanteComun.dolares)) {
                        promesa.setMtoporcentaje(this.mtoDescuentoPromesaUSD); // Monto o %
                    }

                    if (this.gestion.getTblPromesaList() == null) {
                        List<TblPromesa> promesas = new ArrayList<>();
                        this.gestion.setTblPromesaList(promesas);
                    }

                    this.gestion.getTblPromesaList().add(promesa);

                    FacesMessage msg = new FacesMessage("Promesa Agregada: ", promesa.getTelefono());
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }

                this.addLlamadaToGestion(this.getSelectedLlamada());
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

        if (codigoMoneda.equals(ConstanteComun.colones)) {
            mtoSaldo = this.mtoSaldoOperacion;
            primerTracto = this.mtoDescuentoPromesa;
        } else if (codigoMoneda.equals(ConstanteComun.dolares)) {
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

        if (codigoMoneda.equals(ConstanteComun.colones)) {
            this.mtoSaldoPromesa = saldoRestante;
        } else if (codigoMoneda.equals(ConstanteComun.dolares)) {
            this.mtoSaldoPromesaUSD = saldoRestante;
        }
    }

    /**
     *
     * @param codigoMoneda
     */
    public void agregarRefinanciamiento(String codigoMoneda) {
        if (this.selectedLlamada != null) {

            if (this.validRefinanciamiento(codigoMoneda)) {

                Moneda monedaPromesa = new Moneda();
                monedaPromesa.setCodigo(codigoMoneda);
                monedaPromesa = this.ejbMonedaService.findByCodigo(monedaPromesa);

                Estadopromesa estadopromesa = new Estadopromesa();
                estadopromesa.setCodigo(ConstanteComun.Seguimiento);
                estadopromesa = this.ejbEstadopromesaLocal.findByCodigo(estadopromesa);

                Arreglopago arreglopagoPromesa = new Arreglopago();
                arreglopagoPromesa.setCodigo(ConstanteComun.Refinanciamiento);
                arreglopagoPromesa = this.ejbArreglopagoLocal.findByCodigo(arreglopagoPromesa);

                Calendar fechaInicial = Calendar.getInstance();
                fechaInicial.setTime(this.fechaPagoPromesa);

                BigDecimal coutasInt = new BigDecimal(this.cuotas);
                BigDecimal coutasIntLessOne = coutasInt.subtract(BigDecimal.ONE);// restar una cuota.

                BigDecimal primerTracto = BigDecimal.ZERO;// primer tracto
                BigDecimal saldo = BigDecimal.ZERO;// saldo restante
                if (codigoMoneda.equals(ConstanteComun.colones)) {
                    primerTracto = this.mtoDescuentoPromesa;// primer tracto
                    saldo = this.mtoSaldoPromesa.divide(coutasIntLessOne, 6, RoundingMode.HALF_UP);// saldo restante
                } else if (codigoMoneda.equals(ConstanteComun.dolares)) {
                    primerTracto = this.mtoDescuentoPromesaUSD;// primer tracto
                    saldo = this.mtoSaldoPromesaUSD.divide(coutasIntLessOne, 6, RoundingMode.HALF_UP);// saldo restante
                }

                // borra las promesas...                
                this.deleteArregloPago(this.operacionSelected.getNumeroCuenta(), codigoMoneda);

                for (int count = 0; count < coutasInt.intValue(); count++) {
                    TblPromesa promesa = new TblPromesa();

                    if (this.usuario != null) {
                        promesa.setUsuarioingreso(this.usuario.getUsuario());
                    }

                    promesa.setIdGestion(this.gestion);
                    promesa.setIdLlamada(this.selectedLlamada);
                    promesa.setOperacion(this.selectedLlamada.getOperacion());
                    promesa.setTelefono(this.selectedLlamada.getCallToNumber());
                    promesa.setFechaPago(fechaInicial.getTime());
                    fechaInicial.set(Calendar.MONTH, fechaInicial.get(Calendar.MONTH) + 1);
                    promesa.setIdestadopromesa(estadopromesa); // Seguimiento
                    promesa.setIdtipodescuento(null); // Tipo Descuento: Monto Fijo o Porcentaje.
                    promesa.setIdarreglopago(arreglopagoPromesa);//REF = Refinanciamiento.
                    promesa.setFechaingreso(this.fechaHoy.getTime());
                    promesa.setIdMoneda(monedaPromesa);
                    promesa.setMtoporcentaje(BigDecimal.ZERO); // Monto o %

                    if (count == 0 && primerTracto.compareTo(BigDecimal.ZERO) == 1) {
                        promesa.setMtopago(primerTracto);
                    } else {
                        promesa.setMtopago(saldo);
                    }

                    if (this.gestion.getTblPromesaList() == null) {
                        List<TblPromesa> promesas = new ArrayList<>();
                        this.gestion.setTblPromesaList(promesas);
                    }

                    this.gestion.getTblPromesaList().add(promesa);

                    FacesMessage msg = new FacesMessage("Promesa Agregada: ", promesa.getTelefono());
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }

                this.addLlamadaToGestion(this.getSelectedLlamada());
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
        String operacion = this.operacionSelected.getNumeroCuenta();
        this.cleanFormAP();
        this.deleteArregloPago(operacion, codigoMoneda);
    }

    /**
     *
     * @param e
     */
    public void onTabChanged(TabChangeEvent e) {
        this.cleanFormAP();
    }

    /**
     *
     */
    public void cleanFormAP() {
        this.setTipoDescuentoPromesa(null); //tipoDescuentoPromesa;
        this.setMtoDescuentoPromesa(BigDecimal.ZERO); //mtoDescuentoPromesa;
        this.setMtoSaldoPromesa(BigDecimal.ZERO); //mtoSaldoPromesa;
        this.setFechaPagoPromesa(null); //fechaPagoPromesa;
        this.setCuotas(null); //cuotas;

        this.setMtoDescuentoPromesaUSD(BigDecimal.ZERO);
        this.setMtoSaldoPromesaUSD(BigDecimal.ZERO);
    }

    /**
     * CAT = Cancelacion Total. CTC = Cancelacion Total por Cuotas. REF =
     * Refinanciamiento. PAP = Pago Parcial
     *
     * @param operacion
     * @param codigoMoneda
     */
    public void deleteArregloPago(String operacion, String codigoMoneda) {
        int index = 0;
        boolean isTrue = true;

        if (this.gestion.getTblPromesaList() != null) {
            while (this.gestion.getTblPromesaList().size() > 0 && isTrue) {//&& this.promesaList.size() > index
                boolean searchingMoneda = this.gestion.getTblPromesaList().get(index).getIdMoneda().getCodigo().equals(codigoMoneda);
                boolean searchingOperacion = this.gestion.getTblPromesaList().get(index).getOperacion().equals(operacion);
                if (searchingMoneda && searchingOperacion) {
                    Estadopromesa estado = new Estadopromesa();
                    estado.setCodigo(ConstanteComun.Registro_Borrado);
                    estado = this.ejbEstadopromesaLocal.findByCodigo(estado);
                    this.gestion.getTblPromesaList().get(index).setIdestadopromesa(estado);
                    this.ejbPromesaLocal.update(this.gestion.getTblPromesaList().get(index));
                    this.gestion.getTblPromesaList().remove(index);
                    index = index - 1;
                }

                if (this.gestion.getTblPromesaList().size() <= 0) {
                    isTrue = false;

                } else {
                    int i = 0;
                    while (i < this.gestion.getTblPromesaList().size()) {
                        boolean existMoneda = this.gestion.getTblPromesaList().get(i).getIdMoneda().getCodigo().equals(codigoMoneda);
                        boolean existOperacion = this.gestion.getTblPromesaList().get(i).getOperacion().equals(operacion);
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

        if (this.gestion != null && this.gestion.getTblLlamadaList() != null) {
            for (int j = 0; j < this.gestion.getTblLlamadaList().size(); j++) {
                int index02 = 0;
                boolean isTrue02 = true;

                if (this.gestion.getTblLlamadaList().get(j).getTblPromesaList() != null) {
                    while (this.gestion.getTblLlamadaList().get(j).getTblPromesaList().size() > 0 && isTrue02) {
                        boolean searchingMoneda02 = this.gestion.getTblLlamadaList().get(j).getTblPromesaList().get(index02).getIdMoneda().getCodigo().equals(codigoMoneda);
                        boolean searchingOperacion02 = this.gestion.getTblLlamadaList().get(j).getTblPromesaList().get(index02).getOperacion().equals(operacion);
                        if (searchingMoneda02 && searchingOperacion02) {
                            Estadopromesa estado = new Estadopromesa();
                            estado.setCodigo(ConstanteComun.Registro_Borrado);
                            estado = this.ejbEstadopromesaLocal.findByCodigo(estado);
                            this.gestion.getTblLlamadaList().get(j).getTblPromesaList().get(index02).setIdestadopromesa(estado);
                            this.ejbPromesaLocal.update(this.gestion.getTblLlamadaList().get(j).getTblPromesaList().get(index02));
                            this.gestion.getTblLlamadaList().get(j).getTblPromesaList().remove(index02);
                            index02 = index02 - 1;
                        }

                        if (this.gestion.getTblLlamadaList().get(j).getTblPromesaList().size() <= 0) {
                            isTrue02 = false;

                        } else {
                            int i02 = 0;
                            while (i02 < this.gestion.getTblLlamadaList().get(j).getTblPromesaList().size()) {
                                boolean existMoneda02 = this.gestion.getTblLlamadaList().get(j).getTblPromesaList().get(i02).getIdMoneda().getCodigo().equals(codigoMoneda);
                                boolean existOperacion02 = this.gestion.getTblLlamadaList().get(j).getTblPromesaList().get(i02).getOperacion().equals(operacion);
                                if (existMoneda02 && existOperacion02) {
                                    isTrue02 = true;
                                    i02++;
                                    break;
                                }
                                isTrue02 = false;
                                i02++;
                            }
                        }
                        index02++;
                    }// while
                }// if
            }// for
        }

    }

    /**
     * Remueve las promesas, segun operacion y tipo de arrglo de pago.
     *
     * @param operacion
     * @param arregloPago
     * @param codigoMoneda
     */
    public void deleteByOperacionAndArregloPago(String operacion, String arregloPago, String codigoMoneda) {
        int index = 0;
        while (this.gestion.getTblPromesaList().size() > 0 && this.gestion.getTblPromesaList().size() > index) {
            boolean isOperacion = this.gestion.getTblPromesaList().get(index).getOperacion().equals(operacion);
            boolean isAP = this.gestion.getTblPromesaList().get(index).getIdarreglopago().getCodigo().equals(arregloPago);
            boolean isMoneda = this.gestion.getTblPromesaList().get(index).getIdMoneda().getCodigo().equals(codigoMoneda);
            if (isOperacion && isAP && isMoneda) {
                this.gestion.getTblPromesaList().remove(index);
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

                Moneda monedaPromesa = new Moneda();
                monedaPromesa.setCodigo(codigoMoneda);
                monedaPromesa = this.ejbMonedaService.findByCodigo(monedaPromesa);

                Estadopromesa estadopromesa = new Estadopromesa();
                estadopromesa.setCodigo(ConstanteComun.Seguimiento);
                estadopromesa = this.ejbEstadopromesaLocal.findByCodigo(estadopromesa);

                Arreglopago arreglopagoPromesa = new Arreglopago();
                arreglopagoPromesa.setCodigo(ConstanteComun.Pago_Parcial);
                arreglopagoPromesa = this.ejbArreglopagoLocal.findByCodigo(arreglopagoPromesa);

                Calendar fechaInicial = Calendar.getInstance();
                fechaInicial.setTime(this.fechaPagoPromesa);

                TblPromesa promesa = new TblPromesa();

                if (this.usuario != null) {
                    promesa.setUsuarioingreso(this.usuario.getUsuario());
                }

                promesa.setIdGestion(this.gestion);
                promesa.setIdLlamada(this.selectedLlamada);
                promesa.setOperacion(this.selectedLlamada.getOperacion());
                promesa.setTelefono(this.selectedLlamada.getCallToNumber());
                promesa.setFechaPago(fechaInicial.getTime());
                promesa.setIdestadopromesa(estadopromesa); // Seguimiento
                promesa.setIdtipodescuento(null); // Tipo Descuento: Monto Fijo o Porcentaje.
                promesa.setIdarreglopago(arreglopagoPromesa);//PAP = Pago Parcial
                promesa.setFechaingreso(this.fechaHoy.getTime());
                promesa.setIdMoneda(monedaPromesa);
                promesa.setMtoporcentaje(BigDecimal.ZERO); // Monto o %

                if (codigoMoneda.equals(ConstanteComun.colones)) {
                    promesa.setMtopago(this.mtoSaldoPromesa);
                } else if (codigoMoneda.equals(ConstanteComun.dolares)) {
                    promesa.setMtopago(this.mtoSaldoPromesaUSD);
                }

                if (this.validarSumaPromesaContraSaldo(promesa)) {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso!", "Suma Monto Promesa debe ser menor o igual al saldo de la operación!");
                    FacesContext.getCurrentInstance().addMessage(null, msg);

                } else {

                    if (this.gestion.getTblPromesaList() == null) {
                        List<TblPromesa> promesas = new ArrayList<>();
                        this.gestion.setTblPromesaList(promesas);
                    }

                    this.gestion.getTblPromesaList().add(promesa);
                    this.addLlamadaToGestion(this.getSelectedLlamada());

                    FacesMessage msg = new FacesMessage("Promesa Agregada: ", promesa.getTelefono());
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }
            }

        } else {
            FacesMessage msg = new FacesMessage("Promesa NO Agregada!: ", null);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    /**
     * Si la suma de las promesas es mayor al saldo de la operacion retirna
     * true.
     *
     * @param promesa
     * @return
     */
    private boolean validarSumaPromesaContraSaldo(TblPromesa promesa) {

        BigDecimal suma = this.sumarArrergloPago(promesa.getOperacion(), promesa.getIdarreglopago().getCodigo(), promesa.getIdMoneda().getCodigo());
        suma = suma.add(promesa.getMtopago());

        if (suma.compareTo(this.mtoSaldoOperacion) > 0) {
            return true;
        }

        return false;
    }

    /**
     *
     * @param pOperacion
     * @param pTipoAP
     * @param pCodigoMoneda
     * @return
     */
    private BigDecimal sumarArrergloPago(String pOperacion, String pTipoAP, String pCodigoMoneda) {

        BigDecimal suma = BigDecimal.ZERO;
        for (int index = 0; index < this.gestion.getTblPromesaList().size(); index++) {
            String ope = this.gestion.getTblPromesaList().get(index).getOperacion();
            String tipoAP = this.gestion.getTblPromesaList().get(index).getIdarreglopago().getCodigo();
            String codigoMoneda = this.gestion.getTblPromesaList().get(index).getIdMoneda().getCodigo();
            String estado = this.gestion.getTblPromesaList().get(index).getIdestadopromesa().getCodigo();
            BigDecimal mtoPago = this.gestion.getTblPromesaList().get(index).getMtopago();

            boolean isOperacion = ope.trim().equals(pOperacion);
            boolean isTAP = tipoAP.trim().equals(pTipoAP);
            boolean isCM = codigoMoneda.trim().equals(pCodigoMoneda);
            boolean isDEL = estado.trim().equals(ConstanteComun.Registro_Borrado);

            if (isOperacion && isTAP && isCM && !isDEL) {
                suma = suma.add(mtoPago);
            }
        }

        return suma;
    }

    /**
     *
     * @param codigoMoneda
     * @return
     */
    public boolean validPagoParcial(String codigoMoneda) {

        if (codigoMoneda.equals(ConstanteComun.colones)) {
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

        } else if (codigoMoneda.equals(ConstanteComun.dolares)) {
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

        if (!this.validarfechaPagoPromesa()) {
            return false;
        }

        return true;

    }

    /**
     *
     * @return
     */
    private boolean validarfechaPagoPromesa() {

        if (this.fechaPagoPromesa == null) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso!", "Debe seleccionar Fecha Pago!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return false;
        }

        Calendar fechaPP = Calendar.getInstance();
        fechaPP.setTime(this.fechaPagoPromesa);

        Calendar fechaH = Calendar.getInstance();
        fechaH.setTime(this.fechaHoy.getTime());

        fechaPP.set(Calendar.HOUR_OF_DAY, 0);
        fechaPP.set(Calendar.MINUTE, 0);
        fechaPP.set(Calendar.SECOND, 0);
        fechaPP.set(Calendar.MILLISECOND, 0);

        fechaH.set(Calendar.HOUR_OF_DAY, 0);
        fechaH.set(Calendar.MINUTE, 0);
        fechaH.set(Calendar.SECOND, 0);
        fechaH.set(Calendar.MILLISECOND, 0);

        if (fechaPP.before(fechaH)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso!", "Fecha Pago debe ser mayor igual a la fecha de hoy!");
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
        if (this.gestion.getTblPromesaList() != null && !this.gestion.getTblPromesaList().isEmpty()) {
            for (int index = 0; index < this.gestion.getTblPromesaList().size(); index++) {
                String operacion = promesa.getOperacion();
                if (this.gestion.getTblPromesaList().get(index).getOperacion().equals(operacion) && this.gestion.getTblPromesaList().get(index).getIdarreglopago().getCodigo().equals(ConstanteComun.Cancelacion_Total)) {
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
        if (this.gestion.getTblPromesaList() != null && !this.gestion.getTblPromesaList().isEmpty()) {
            for (int index = 0; index < this.gestion.getTblPromesaList().size(); index++) {
                String operacion = promesa.getOperacion();
                if (this.gestion.getTblPromesaList().get(index).getOperacion().equals(operacion) && this.gestion.getTblPromesaList().get(index).getIdarreglopago().getCodigo().equals(ConstanteComun.Pago_Parcial)) {
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
        if (this.gestion.getTblPromesaList() != null && !this.gestion.getTblPromesaList().isEmpty()) {
            for (int index = 0; index < this.gestion.getTblPromesaList().size(); index++) {
                String operacion = promesa.getOperacion();
                if (this.gestion.getTblPromesaList().get(index).getOperacion().equals(operacion) && this.gestion.getTblPromesaList().get(index).getIdarreglopago().getCodigo().equals(ConstanteComun.Cancelacion_total_por_cuotas)) {
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
        if (this.gestion.getTblPromesaList() != null && !this.gestion.getTblPromesaList().isEmpty()) {
            for (int index = 0; index < this.gestion.getTblPromesaList().size(); index++) {
                String operacion = promesa.getOperacion();
                if (this.gestion.getTblPromesaList().get(index).getOperacion().equals(operacion) && this.gestion.getTblPromesaList().get(index).getIdarreglopago().getCodigo().equals(ConstanteComun.Refinanciamiento)) {
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

        if (codigoMoneda.equals(ConstanteComun.colones)) {
            if (this.mtoSaldoOperacion == null || this.mtoSaldoOperacion.compareTo(BigDecimal.ZERO) <= 0) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso!", "Saldo de la operación debe ser mayor a cero!");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                return false;
            }
        } else if (codigoMoneda.equals(ConstanteComun.dolares)) {
            if (this.mtoSaldoOperacionUSD == null || this.mtoSaldoOperacionUSD.compareTo(BigDecimal.ZERO) <= 0) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso!", "Saldo de la operación debe ser mayor a cero!");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                return false;
            }
        }

        if (this.tipoDescuentoPromesa == null || this.tipoDescuentoPromesa.getCodigo().equals("")) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso!", "Debe seleccionar un Tipo Descuento!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return false;
        }

        if (codigoMoneda.equals(ConstanteComun.colones)) {
            if (this.mtoSaldoPromesa == null || this.mtoSaldoPromesa.compareTo(BigDecimal.ZERO) == 0 || this.mtoSaldoPromesa.compareTo(BigDecimal.ZERO) == -1) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso!", "Monto Promesa debe ser mayor a cero!");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                return false;
            }

        } else if (codigoMoneda.equals(ConstanteComun.dolares)) {
            if (this.mtoSaldoPromesaUSD == null || this.mtoSaldoPromesaUSD.compareTo(BigDecimal.ZERO) == 0 || this.mtoSaldoPromesaUSD.compareTo(BigDecimal.ZERO) == -1) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso!", "Monto Promesa debe ser mayor a cero!");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                return false;
            }
        }

        if (!this.validarfechaPagoPromesa()) {
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
        if (this.gestion.getTblPromesaList() != null && !this.gestion.getTblPromesaList().isEmpty()) {
            for (int index = 0; index < this.gestion.getTblPromesaList().size(); index++) {
                String operacion = promesa.getOperacion();
                if (this.gestion.getTblPromesaList().get(index).getOperacion().equals(operacion) && this.gestion.getTblPromesaList().get(index).getIdarreglopago().getCodigo().equals(ConstanteComun.Cancelacion_Total)) {
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
        if (this.gestion.getTblPromesaList() != null && !this.gestion.getTblPromesaList().isEmpty()) {
            for (int i = 0; i < this.gestion.getTblPromesaList().size(); i++) {
                if (this.gestion.getTblPromesaList().get(i).getIdarreglopago().getCodigo().equals(ConstanteComun.Cancelacion_total_por_cuotas)) {
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

        if (codigoMoneda.equals(ConstanteComun.colones)) {
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

        } else if (codigoMoneda.equals(ConstanteComun.dolares)) {
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

        }

        if (!this.validarfechaPagoPromesa()) {
            return false;
        }

        if (this.cuotas == null || this.cuotas.trim().equals("")) {
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
        for (int index = 0; index < this.gestion.getTblPromesaList().size(); index++) {
            if (this.selectedPromesa != null && (this.gestion.getTblPromesaList() != null && this.gestion.getTblPromesaList().size() > 0)) {

                boolean isTrueTelefono = this.gestion.getTblPromesaList().get(index).getTelefono().equals(this.selectedPromesa.getTelefono());
                boolean isTrueOperacion = this.gestion.getTblPromesaList().get(index).getOperacion().equals(this.selectedPromesa.getOperacion());
                boolean isTrueMtopago = this.gestion.getTblPromesaList().get(index).getMtopago().equals(this.selectedPromesa.getMtopago());
                boolean isTrueFechaPago = this.gestion.getTblPromesaList().get(index).getFechaPago().equals(this.selectedPromesa.getFechaPago());
                boolean isTrueEstado = this.gestion.getTblPromesaList().get(index).getIdestadopromesa().getCodigo().equals(this.selectedPromesa.getIdestadopromesa().getCodigo());
                boolean isTrueTipoArreglo = this.gestion.getTblPromesaList().get(index).getIdestadopromesa().getCodigo().equals(this.selectedPromesa.getIdestadopromesa().getCodigo());
                boolean isTrueMoneda = this.gestion.getTblPromesaList().get(index).getIdMoneda().getCodigo().equals(this.selectedPromesa.getIdMoneda().getCodigo());

                if (this.gestion.getTblPromesaList().get(index).getOperacion() != null) {
                    isTrueOperacion = this.gestion.getTblPromesaList().get(index).getOperacion().equals(this.selectedPromesa.getOperacion());
                }

                if (isTrueTelefono && isTrueOperacion && isTrueMtopago && isTrueFechaPago && isTrueEstado && isTrueTipoArreglo && isTrueMoneda) {

                    TblPromesa promesa = this.gestion.getTblPromesaList().get(index);
                    boolean hasOperation = promesa.getOperacion() != null && !promesa.getOperacion().trim().equals("");
                    boolean hasMtopago = promesa.getMtopago() != null && !promesa.getMtopago().equals(0);
                    boolean hasIdGestion = promesa.getIdGestion() != null && promesa.getIdGestion().getIdGestion() != null;

                    if (hasOperation && hasMtopago && hasIdGestion) {
                        Estadopromesa estadopromesa = new Estadopromesa();
                        estadopromesa.setCodigo(ConstanteComun.Registro_Borrado);
                        estadopromesa = this.ejbEstadopromesaLocal.findByCodigo(estadopromesa);
                        promesa.setIdestadopromesa(estadopromesa);
                        this.ejbPromesaLocal.update(promesa);
                    }

                    this.gestion.getTblPromesaList().remove(index);
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
    private List<TblSaldo> agregarSaldos() {
        List<TblSaldo> saldos = new ArrayList<TblSaldo>();

        BigDecimal saldoColones = this.getOperacionSelected().getSaldoColones();
        BigDecimal interesesColones = this.getOperacionSelected().getInteresesColones();
        Moneda monedaColones = this.getOperacionSelected().getIdMonedaColones();

        BigDecimal saldoDolares = this.getOperacionSelected().getSaldoDolares();
        BigDecimal interesesDolares = this.getOperacionSelected().getInteresesDolares();
        Moneda monedaDolares = this.getOperacionSelected().getIdMonedaDolares();

        TblSaldo colones = new TblSaldo();
        colones.setSaldoCartera(saldoColones);
        colones.setIntereses(interesesColones);
        colones.setIdMoneda(monedaColones);

        if (this.gestion.getLeyusura() != null && this.gestion.getLeyusura().equals("1")) {
            if (this.mtoSaldoGestionCRC != null) {
                colones.setSaldoGestion(this.mtoSaldoGestionCRC);
                colones.setSaldoRestante(BigDecimal.ZERO);
            } else {
                colones.setSaldoGestion(BigDecimal.ZERO);
                colones.setSaldoRestante(BigDecimal.ZERO);
            }
        }
        colones.setIdCartera(this.getOperacionSelected());

        TblSaldo dolares = new TblSaldo();
        dolares.setSaldoCartera(saldoDolares);
        dolares.setIntereses(interesesDolares);
        dolares.setSaldoGestion(BigDecimal.ZERO);
        dolares.setSaldoRestante(saldoDolares);
        dolares.setIdMoneda(monedaDolares);
        dolares.setIdCartera(this.getOperacionSelected());

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

        if (tipoarreglopago.equals(ConstanteComun.Cancelacion_total_por_cuotas) && codigoMoneda.equals(ConstanteComun.colones)) {

            BigDecimal coutasInt = new BigDecimal(this.cuotas);
            BigDecimal saldo = new BigDecimal(BigInteger.ZERO);
            BigDecimal sumar = new BigDecimal(BigInteger.ZERO);
            BigDecimal contador = new BigDecimal(BigInteger.ZERO);

            if (pmtoPago.compareTo(this.mtoSaldoPromesa) > 0) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso!", "Monto digitado es mayor al Monto Promesa!");
                FacesContext.getCurrentInstance().addMessage(null, msg);

                saldo = this.mtoSaldoPromesa.divide(coutasInt, 6, RoundingMode.HALF_UP);

                for (int index = 0; index < this.gestion.getTblPromesaList().size(); index++) {

                    String operacion = this.gestion.getTblPromesaList().get(index).getOperacion();
                    Date fechaPago = this.gestion.getTblPromesaList().get(index).getFechaPago();
                    String monedaString = this.gestion.getTblPromesaList().get(index).getIdMoneda().getCodigo();
                    String arreglopago = this.gestion.getTblPromesaList().get(index).getIdarreglopago().getCodigo();

                    boolean isTrueOperacion = pOperacion.equals(operacion);
                    boolean isFechaPagoEquals = fechaPago.equals(pFechaPago);
                    boolean isFechaPagoAfter = fechaPago.after(pFechaPago);
                    boolean isMoneda = codigoMoneda.equals(monedaString);

                    boolean isArregloPago = arreglopago.equals(tipoarreglopago);
                    if (isArregloPago) {
                        contador = contador.add(BigDecimal.ONE);
                    }

                    if (isArregloPago && isMoneda && isTrueOperacion && isFechaPagoEquals && index == 0) {
                        this.gestion.getTblPromesaList().get(index).setMtopago(saldo);

                    } else if (isArregloPago && isMoneda && isTrueOperacion && isFechaPagoEquals && !coutasInt.subtract(contador).equals(BigDecimal.ZERO)) {
                        this.gestion.getTblPromesaList().get(index).setMtopago(saldo);

                    } else if (isArregloPago && isMoneda && isTrueOperacion && isFechaPagoAfter) {
                        this.gestion.getTblPromesaList().get(index).setMtopago(saldo);

                    } else if (isArregloPago && isMoneda && isTrueOperacion && coutasInt.subtract(contador).equals(BigDecimal.ZERO)) {
                        BigDecimal mtoUltimo = this.mtoSaldoPromesa.subtract(sumar);
                        this.gestion.getTblPromesaList().get(index).setMtopago(mtoUltimo);

                    } else if (isArregloPago && isMoneda && isTrueOperacion) {
                        sumar = sumar.add(this.gestion.getTblPromesaList().get(index).getMtopago());
                        BigDecimal saldoRestante = this.mtoSaldoPromesa.subtract(sumar);
                        if (isArregloPago && isMoneda && isTrueOperacion && !coutasInt.subtract(contador).equals(BigDecimal.ZERO)) {
                            saldo = saldoRestante.divide(coutasInt.subtract(contador), 6, RoundingMode.HALF_UP);
                        }
                    }
                }//for

            } else {

                for (int index = 0; index < this.gestion.getTblPromesaList().size(); index++) {

                    String operacion = this.gestion.getTblPromesaList().get(index).getOperacion();
                    Date fechaPago = this.gestion.getTblPromesaList().get(index).getFechaPago();
                    String monedaString = this.gestion.getTblPromesaList().get(index).getIdMoneda().getCodigo();
                    String arreglopago = this.gestion.getTblPromesaList().get(index).getIdarreglopago().getCodigo();

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

                        boolean isTrue = (this.gestion.getTblPromesaList().size() - index) >= 1;
                        if (isTrue) {
                            BigDecimal indexMenosUno = new BigDecimal(index - 1);
                            BigDecimal mtoPagoAnterior = this.gestion.getTblPromesaList().get(indexMenosUno.intValue()).getMtopago();
                            sumar = sumar.subtract(mtoPagoAnterior);
                            BigDecimal saldoRestante = this.mtoSaldoPromesa.subtract(sumar);
                            BigDecimal contar = BigDecimal.ZERO;
                            for (int i = 0; i < this.gestion.getTblPromesaList().size(); i++) {
                                String isCTCString = this.gestion.getTblPromesaList().get(i).getIdarreglopago().getCodigo();
                                String isUSDString = this.gestion.getTblPromesaList().get(i).getIdMoneda().getCodigo();
                                String isOperacionString = this.gestion.getTblPromesaList().get(i).getOperacion();
                                boolean isCTC = tipoarreglopago.equals(isCTCString);
                                boolean isUSD = codigoMoneda.equals(isUSDString);
                                boolean isOperacion = pOperacion.equals(isOperacionString);

                                if (isCTC && isUSD && isOperacion) {
                                    contar = contar.add(BigDecimal.ONE);
                                }
                            }

                            BigDecimal cantidaRestante = contar.subtract(BigDecimal.ONE);
                            saldoRestante = saldoRestante.divide(cantidaRestante, 6, RoundingMode.HALF_UP);
                            index = indexMenosUno.intValue();
                            while (index < this.gestion.getTblPromesaList().size()) {
                                String isCTCString = this.gestion.getTblPromesaList().get(index).getIdarreglopago().getCodigo();
                                String isUSDString = this.gestion.getTblPromesaList().get(index).getIdMoneda().getCodigo();
                                String isOperacionString = this.gestion.getTblPromesaList().get(index).getOperacion();
                                boolean isCTC = tipoarreglopago.equals(isCTCString);
                                boolean isUSD = codigoMoneda.equals(isUSDString);
                                boolean isOperacion = pOperacion.equals(isOperacionString);

                                if (isCTC && isUSD && isOperacion) {
                                    this.gestion.getTblPromesaList().get(index).setMtopago(saldoRestante);
                                }

                                index++;
                            }
                        }

                        break;

                    } else {

                        if (isArregloPago && isMoneda && isTrueOperacion && isFechaPagoAfter) {
                            this.gestion.getTblPromesaList().get(index).setMtopago(saldo);

                        } else if (isArregloPago && isMoneda && isTrueOperacion && coutasInt.subtract(contador).equals(BigDecimal.ZERO)) {
                            BigDecimal mtoUltimo = this.mtoSaldoPromesa.subtract(sumar);
                            this.gestion.getTblPromesaList().get(index).setMtopago(mtoUltimo);

                        } else if (isArregloPago && isMoneda && isTrueOperacion) {
                            sumar = sumar.add(this.gestion.getTblPromesaList().get(index).getMtopago());
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

        if (tipoarreglopago.equals(ConstanteComun.Refinanciamiento) && codigoMoneda.equals(ConstanteComun.colones)) {

            BigDecimal coutasInt = new BigDecimal(this.cuotas);
            BigDecimal saldo = new BigDecimal(BigInteger.ZERO);
            BigDecimal sumar = new BigDecimal(BigInteger.ZERO);
            BigDecimal contador = new BigDecimal(BigInteger.ZERO);

            if (pmtoPago.compareTo(this.mtoSaldoOperacion) > 0) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso!", "Monto digitado es mayor al Monto Promesa!");
                FacesContext.getCurrentInstance().addMessage(null, msg);

                saldo = this.mtoSaldoOperacion.divide(coutasInt, 6, RoundingMode.HALF_UP);

                for (int index = 0; index < this.gestion.getTblPromesaList().size(); index++) {

                    String operacion = this.gestion.getTblPromesaList().get(index).getOperacion();
                    Date fechaPago = this.gestion.getTblPromesaList().get(index).getFechaPago();
                    String monedaString = this.gestion.getTblPromesaList().get(index).getIdMoneda().getCodigo();
                    String arreglopago = this.gestion.getTblPromesaList().get(index).getIdarreglopago().getCodigo();

                    boolean isTrueOperacion = pOperacion.equals(operacion);
                    boolean isFechaPagoEquals = fechaPago.equals(pFechaPago);
                    boolean isFechaPagoAfter = fechaPago.after(pFechaPago);
                    boolean isMoneda = codigoMoneda.equals(monedaString);
                    boolean isArregloPago = tipoarreglopago.equals(arreglopago);
                    contador = contador.add(BigDecimal.ONE);

                    if (isArregloPago && isMoneda && isTrueOperacion && isFechaPagoEquals && index == 0) {
                        this.gestion.getTblPromesaList().get(index).setMtopago(saldo);

                    } else if (isArregloPago && isMoneda && isTrueOperacion && isFechaPagoEquals && !coutasInt.subtract(contador).equals(BigDecimal.ZERO)) {
                        this.gestion.getTblPromesaList().get(index).setMtopago(saldo);

                    } else if (isArregloPago && isMoneda && isTrueOperacion && isFechaPagoAfter) {
                        this.gestion.getTblPromesaList().get(index).setMtopago(saldo);

                    } else if (isArregloPago && isMoneda && coutasInt.subtract(contador).equals(BigDecimal.ZERO)) {
                        BigDecimal mtoUltimo = this.mtoSaldoOperacion.subtract(sumar);
                        this.gestion.getTblPromesaList().get(index).setMtopago(mtoUltimo);

                    } else if (isArregloPago && isMoneda && isTrueOperacion) {
                        sumar = sumar.add(this.gestion.getTblPromesaList().get(index).getMtopago());
                        BigDecimal saldoRestante = this.mtoSaldoOperacion.subtract(sumar);
                        if (isArregloPago && isMoneda && isTrueOperacion && !coutasInt.subtract(contador).equals(BigDecimal.ZERO)) {
                            saldo = saldoRestante.divide(coutasInt.subtract(contador), 6, RoundingMode.HALF_UP);
                        }
                    }
                }//for

            } else {

                for (int index = 0; index < this.gestion.getTblPromesaList().size(); index++) {
                    if (sumar.compareTo(this.mtoSaldoOperacion) > 0) {
                        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso!", "La suma de las cuotas es mayor al Monto Promesa!");
                        FacesContext.getCurrentInstance().addMessage(null, msg);

                        boolean isTrue = (this.gestion.getTblPromesaList().size() - index) >= 1;
                        if (isTrue) {
                            int indexMenosUno = index - 1;
                            BigDecimal mtoPagoAnterior = this.gestion.getTblPromesaList().get(indexMenosUno).getMtopago();
                            sumar = sumar.subtract(mtoPagoAnterior);
                            BigDecimal saldoRestante = this.mtoSaldoOperacion.subtract(sumar);
                            int cantida = this.gestion.getTblPromesaList().size() - indexMenosUno;
                            BigDecimal cantidaRestante = new BigDecimal(cantida);
                            saldoRestante = saldoRestante.divide(cantidaRestante, 6, RoundingMode.HALF_UP);
                            index = indexMenosUno;
                            while (index < this.gestion.getTblPromesaList().size()) {
                                this.gestion.getTblPromesaList().get(index).setMtopago(saldoRestante);
                                index++;
                            }
                        }

                        break;

                    } else {
                        String operacion = this.gestion.getTblPromesaList().get(index).getOperacion();
                        Date fechaPago = this.gestion.getTblPromesaList().get(index).getFechaPago();
                        String monedaString = this.gestion.getTblPromesaList().get(index).getIdMoneda().getCodigo();
                        String arreglopago = this.gestion.getTblPromesaList().get(index).getIdarreglopago().getCodigo();

                        boolean isTrueOperacion = pOperacion.equals(operacion);
                        boolean isFechaPagoAfter = fechaPago.after(pFechaPago);
                        boolean isMoneda = codigoMoneda.equals(monedaString);
                        boolean isArregloPago = tipoarreglopago.equals(arreglopago);
                        contador = contador.add(BigDecimal.ONE);

                        if (isArregloPago && isMoneda && isTrueOperacion && isFechaPagoAfter) {
                            this.gestion.getTblPromesaList().get(index).setMtopago(saldo);

                        } else if (isArregloPago && isMoneda && coutasInt.subtract(contador).equals(BigDecimal.ZERO)) {
                            BigDecimal mtoUltimo = this.mtoSaldoOperacion.subtract(sumar);
                            this.gestion.getTblPromesaList().get(index).setMtopago(mtoUltimo);

                        } else if (isArregloPago && isMoneda && isTrueOperacion) {
                            sumar = sumar.add(this.gestion.getTblPromesaList().get(index).getMtopago());
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

    /*
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
     */
    /**
     * USD Cacula nuevo monto degun descuento.
     */
    public void calcNuevoMontoSegunDescuentoUSD() {
        BigDecimal cien = new BigDecimal("100");
        BigDecimal newSaldo = new BigDecimal(BigInteger.ZERO);
        BigDecimal mtoSaldo = this.mtoSaldoOperacionUSD;
        BigDecimal mtoPort = this.mtoDescuentoPromesaUSD;

        if (this.tipoDescuentoPromesa != null) {
            if (this.tipoDescuentoPromesa.getCodigo().equals(ConstanteComun.Monto_Fijo)) {
                newSaldo = mtoSaldo.subtract(mtoPort);

            } else if (this.tipoDescuentoPromesa.getCodigo().equals(ConstanteComun.Porcentaje)) {
                if (mtoPort != null && mtoPort.compareTo(BigDecimal.ZERO) > 0) {
                    BigDecimal porcentage = mtoSaldo.multiply(mtoPort).divide(cien);
                    newSaldo = mtoSaldo.subtract(porcentage);
                } else {
                    newSaldo = mtoSaldo;
                }
            }
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

        if (tipoarreglopago.equals(ConstanteComun.Cancelacion_total_por_cuotas) && codigoMoneda.equals(ConstanteComun.dolares)) {

            BigDecimal coutasInt = new BigDecimal(this.cuotas);
            BigDecimal saldo = new BigDecimal(BigInteger.ZERO);
            BigDecimal sumar = new BigDecimal(BigInteger.ZERO);
            BigDecimal contador = new BigDecimal(BigInteger.ZERO);

            if (pmtoPago.compareTo(this.mtoSaldoPromesaUSD) > 0) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso!", "Monto digitado es mayor al Monto Promesa!");
                FacesContext.getCurrentInstance().addMessage(null, msg);

                saldo = this.mtoSaldoPromesaUSD.divide(coutasInt, 6, RoundingMode.HALF_UP);

                for (int index = 0; index < this.gestion.getTblPromesaList().size(); index++) {

                    String operacion = this.gestion.getTblPromesaList().get(index).getOperacion();
                    Date fechaPago = this.gestion.getTblPromesaList().get(index).getFechaPago();
                    String monedaString = this.gestion.getTblPromesaList().get(index).getIdMoneda().getCodigo();
                    String arreglopago = this.gestion.getTblPromesaList().get(index).getIdarreglopago().getCodigo();

                    boolean isTrueOperacion = pOperacion.equals(operacion);
                    boolean isFechaPagoEquals = fechaPago.equals(pFechaPago);
                    boolean isFechaPagoAfter = fechaPago.after(pFechaPago);
                    boolean isMoneda = codigoMoneda.equals(monedaString);

                    boolean isArregloPago = arreglopago.equals(tipoarreglopago);
                    if (isArregloPago) {
                        contador = contador.add(BigDecimal.ONE);
                    }

                    if (isArregloPago && isMoneda && isTrueOperacion && isFechaPagoEquals && index == 0) {
                        this.gestion.getTblPromesaList().get(index).setMtopago(saldo);

                    } else if (isArregloPago && isMoneda && isTrueOperacion && isFechaPagoEquals && !coutasInt.subtract(contador).equals(BigDecimal.ZERO)) {
                        this.gestion.getTblPromesaList().get(index).setMtopago(saldo);

                    } else if (isArregloPago && isMoneda && isTrueOperacion && isFechaPagoAfter) {
                        this.gestion.getTblPromesaList().get(index).setMtopago(saldo);

                    } else if (isArregloPago && isMoneda && isTrueOperacion && coutasInt.subtract(contador).equals(BigDecimal.ZERO)) {
                        BigDecimal mtoUltimo = this.mtoSaldoPromesaUSD.subtract(sumar);
                        this.gestion.getTblPromesaList().get(index).setMtopago(mtoUltimo);

                    } else if (isArregloPago && isMoneda && isTrueOperacion) {
                        sumar = sumar.add(this.gestion.getTblPromesaList().get(index).getMtopago());
                        BigDecimal saldoRestante = this.mtoSaldoPromesaUSD.subtract(sumar);
                        if (isArregloPago && isMoneda && isTrueOperacion && !coutasInt.subtract(contador).equals(BigDecimal.ZERO)) {
                            saldo = saldoRestante.divide(coutasInt.subtract(contador), 6, RoundingMode.HALF_UP);
                        }
                    }
                }//for

            } else {

                for (int index = 0; index < this.gestion.getTblPromesaList().size(); index++) {

                    String operacion = this.gestion.getTblPromesaList().get(index).getOperacion();
                    Date fechaPago = this.gestion.getTblPromesaList().get(index).getFechaPago();
                    String monedaString = this.gestion.getTblPromesaList().get(index).getIdMoneda().getCodigo();
                    String arreglopago = this.gestion.getTblPromesaList().get(index).getIdarreglopago().getCodigo();

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
                        boolean isTrue = (this.gestion.getTblPromesaList().size() - index) >= 1;
                        if (isTrue) {
                            BigDecimal indexMenosUno = new BigDecimal(index - 1);
                            BigDecimal mtoPagoAnterior = this.gestion.getTblPromesaList().get(indexMenosUno.intValue()).getMtopago();
                            sumar = sumar.subtract(mtoPagoAnterior);
                            BigDecimal saldoRestante = this.mtoSaldoPromesaUSD.subtract(sumar);
                            BigDecimal contar = BigDecimal.ZERO;
                            for (int i = 0; i < this.gestion.getTblPromesaList().size(); i++) {
                                String isCTCString = this.gestion.getTblPromesaList().get(i).getIdarreglopago().getCodigo();
                                String isUSDString = this.gestion.getTblPromesaList().get(i).getIdMoneda().getCodigo();
                                String isOperacionString = this.gestion.getTblPromesaList().get(i).getOperacion();
                                boolean isCTC = tipoarreglopago.equals(isCTCString);
                                boolean isUSD = codigoMoneda.equals(isUSDString);
                                boolean isOperacion = pOperacion.equals(isOperacionString);

                                if (isCTC && isUSD && isOperacion) {
                                    contar = contar.add(BigDecimal.ONE);
                                }
                            }

                            BigDecimal promesaSize = new BigDecimal(this.gestion.getTblPromesaList().size());

                            BigDecimal cantidaRestante = contar.subtract(BigDecimal.ONE);
                            saldoRestante = saldoRestante.divide(cantidaRestante, 6, RoundingMode.HALF_UP);
                            index = indexMenosUno.intValue();
                            while (index < this.gestion.getTblPromesaList().size()) {
                                String isCTCString = this.gestion.getTblPromesaList().get(index).getIdarreglopago().getCodigo();
                                String isUSDString = this.gestion.getTblPromesaList().get(index).getIdMoneda().getCodigo();
                                String isOperacionString = this.gestion.getTblPromesaList().get(index).getOperacion();
                                boolean isCTC = tipoarreglopago.equals(isCTCString);
                                boolean isUSD = codigoMoneda.equals(isUSDString);
                                boolean isOperacion = pOperacion.equals(isOperacionString);

                                if (isCTC && isUSD && isOperacion) {
                                    this.gestion.getTblPromesaList().get(index).setMtopago(saldoRestante);
                                }

                                index++;
                            }
                        }

                        break;

                    } else {

                        if (isArregloPago && isMoneda && isTrueOperacion && isFechaPagoAfter) {
                            this.gestion.getTblPromesaList().get(index).setMtopago(saldo);

                        } else if (isArregloPago && isMoneda && isTrueOperacion && coutasInt.subtract(contador).equals(BigDecimal.ZERO)) {
                            BigDecimal mtoUltimo = this.mtoSaldoPromesaUSD.subtract(sumar);
                            this.gestion.getTblPromesaList().get(index).setMtopago(mtoUltimo);

                        } else if (isArregloPago && isMoneda && isTrueOperacion) {
                            sumar = sumar.add(this.gestion.getTblPromesaList().get(index).getMtopago());
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

                for (int index = 0; index < this.gestion.getTblPromesaList().size(); index++) {

                    String operacion = this.gestion.getTblPromesaList().get(index).getOperacion();
                    Date fechaPago = this.gestion.getTblPromesaList().get(index).getFechaPago();
                    String monedaString = this.gestion.getTblPromesaList().get(index).getIdMoneda().getCodigo();
                    String arreglopago = this.gestion.getTblPromesaList().get(index).getIdarreglopago().getCodigo();

                    boolean isTrueOperacion = pOperacion.equals(operacion);
                    boolean isFechaPagoEquals = fechaPago.equals(pFechaPago);
                    boolean isFechaPagoAfter = fechaPago.after(pFechaPago);
                    boolean isMoneda = codigoMoneda.equals(monedaString);

                    boolean isArregloPago = arreglopago.equals(tipoarreglopago);
                    if (isArregloPago && isMoneda && isTrueOperacion) {
                        contador = contador.add(BigDecimal.ONE);
                    }

                    if (isArregloPago && isMoneda && isTrueOperacion && isFechaPagoEquals && contador.equals(BigDecimal.ONE)) {
                        this.gestion.getTblPromesaList().get(index).setMtopago(primerTracto);

                    } else if (isArregloPago && isMoneda && isTrueOperacion && isFechaPagoEquals && !coutasInt.subtract(contador).equals(BigDecimal.ZERO)) {
                        this.gestion.getTblPromesaList().get(index).setMtopago(saldo);

                    } else if (isArregloPago && isMoneda && isTrueOperacion && isFechaPagoAfter) {
                        this.gestion.getTblPromesaList().get(index).setMtopago(saldo);

                    } else if (isArregloPago && isMoneda && isTrueOperacion && coutasInt.subtract(contador).equals(BigDecimal.ZERO)) {
                        BigDecimal mtoUltimo = this.mtoSaldoOperacionUSD.subtract(sumar);// Saldo operacion
                        this.gestion.getTblPromesaList().get(index).setMtopago(mtoUltimo);

                    } else if (isArregloPago && isMoneda && isTrueOperacion) {
                        sumar = sumar.add(this.gestion.getTblPromesaList().get(index).getMtopago());
                        BigDecimal saldoRestante = this.mtoSaldoOperacionUSD.subtract(sumar);// Saldo operacion
                        if (isArregloPago && isMoneda && isTrueOperacion && !coutasInt.subtract(contador).equals(BigDecimal.ZERO)) {
                            saldo = saldoRestante.divide(coutasInt.subtract(contador), 6, RoundingMode.HALF_UP);
                        }
                    }
                }//for

            } else {

                for (int index = 0; index < this.gestion.getTblPromesaList().size(); index++) {

                    String operacion = this.gestion.getTblPromesaList().get(index).getOperacion();
                    Date fechaPago = this.gestion.getTblPromesaList().get(index).getFechaPago();
                    String monedaString = this.gestion.getTblPromesaList().get(index).getIdMoneda().getCodigo();
                    String arreglopago = this.gestion.getTblPromesaList().get(index).getIdarreglopago().getCodigo();

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
                        boolean isTrue = (this.gestion.getTblPromesaList().size() - index) >= 1;
                        if (isTrue) {
                            BigDecimal indexMenosUno = new BigDecimal(index - 1);
                            BigDecimal mtoPagoAnterior = this.gestion.getTblPromesaList().get(indexMenosUno.intValue()).getMtopago();
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
                            while (index < this.gestion.getTblPromesaList().size()) {
                                String isCTCString = this.gestion.getTblPromesaList().get(index).getIdarreglopago().getCodigo();
                                String isUSDString = this.gestion.getTblPromesaList().get(index).getIdMoneda().getCodigo();
                                String isOperacionString = this.gestion.getTblPromesaList().get(index).getOperacion();
                                boolean isCTC = tipoarreglopago.equals(isCTCString);
                                boolean isUSD = codigoMoneda.equals(isUSDString);
                                boolean isOperacion = pOperacion.equals(isOperacionString);

                                if (isCTC && isUSD && isOperacion) {
                                    this.gestion.getTblPromesaList().get(index).setMtopago(saldoRestante);
                                }

                                index++;
                            }
                        }

                        break;

                    } else {

                        if (isArregloPago && isMoneda && isTrueOperacion && isFechaPagoAfter) {
                            this.gestion.getTblPromesaList().get(index).setMtopago(saldo);

                        } else if (isArregloPago && isMoneda && isTrueOperacion && coutasInt.subtract(contador).equals(BigDecimal.ZERO)) {
                            BigDecimal mtoUltimo = this.mtoSaldoOperacionUSD.subtract(sumar);// Saldo restante
                            this.gestion.getTblPromesaList().get(index).setMtopago(mtoUltimo);

                        } else if (isArregloPago && isMoneda && isTrueOperacion) {
                            sumar = sumar.add(this.gestion.getTblPromesaList().get(index).getMtopago());
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

                } else if (this.ext == null || this.ext.trim().equals("")) {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso!", "Debe asignar una extensión al usuario!");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                    return false;

                } else if (this.parametro == null || this.parametro.trim().equals("")) {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso!", "URL Llamada debe tener configurado un parametro!");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                    return false;
                }
            }
        }

        return true;
    }

    public void onTabViewChange(TabChangeEvent event) {
        this.tabView = (TabView) event.getComponent();
        this.activeTabIndex = this.tabView.getActiveIndex();
        int index = this.tabView.getChildren().indexOf(event.getTab());
        switch (index) {
            case 1:
                this.findmeController.cargarFindme(this.gestion.getIdentificacion());
                break;
            case 2:
                this.pagosHistorialController.cargarPagos(this.gestion.getCodigoCartera(), this.operacionSelected.getNumeroCuenta(), this.gestion.getIdentificacion());
                PrimeFaces.current().ajax().update("formGestion:idTabView:idTablePagos");
                break;
            case 3:
                this.gestionController.cargarGestiones(this.gestion.getCodigoCartera(), this.gestion.getIdentificacion());
                PrimeFaces.current().ajax().update("formGestion:idTabView:idTableGH");
                break;
            default:
                break;
        }
    }

    private Integer activeTabIndex = 0;

    public Integer getActiveTabIndex() {
        return activeTabIndex;
    }

    public void setActiveTabIndex(Integer activeTabIndex) {
        this.activeTabIndex = activeTabIndex;
    }

    /**
     *
     * @param llamada
     */
    public void calcArregloPago(TblLlamada llamada) {
        this.addLlamadaToLlamada(llamada);
        if (this.operacionSelected != null) {
            String leyUsura = this.operacionSelected.getLeyusura();
            List<TblSaldo> saldoList = this.operacionSelected.getTblSaldoList();
            if (saldoList != null && !saldoList.isEmpty()) {
                for (int index = 0; index < saldoList.size(); index++) {
                    TblSaldo saldo = saldoList.get(index);
                    if (saldo.getIdMoneda().getCodigo().equals(ConstanteComun.colones)) {
                        if (leyUsura != null && this.operacionSelected.getLeyusura().equals("1")) {
                            this.mtoSaldoOperacion = saldo.getSaldoGestion();
                        } else {
                            this.mtoSaldoOperacion = saldo.getSaldoCartera();
                        }

                    } else if (saldo.getIdMoneda().getCodigo().equals(ConstanteComun.dolares)) {
                        if (leyUsura != null && this.operacionSelected.getLeyusura().equals("1")) {
                            this.mtoSaldoOperacionUSD = saldo.getSaldoGestion();
                        } else {
                            this.mtoSaldoOperacionUSD = saldo.getSaldoCartera();
                        }
                    }
                }

                // actualizar saldos
                for (int index = 0; index < this.carteraList.size(); index++) {
                    if (carteraList.get(index).getNumeroCuenta().equals(this.operacionSelected.getNumeroCuenta())) {
                        carteraList.get(index).setTblSaldoList(saldoList);
                    }
                }
                //**********************
            }//if
        }
    }

    /**
     *
     */
    public void callFromFindme() {
        TblLlamada llamada = this.findmeController.getSelectedLlamada();

        if (llamada != null && llamada.getCallLogId() != null && !llamada.getCallLogId().trim().equals("")) {
            if (this.existTelefonoContacto(llamada.getCallToNumber())) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso!", "Teléfono ya existe!");
                FacesContext.getCurrentInstance().addMessage(null, msg);

            } else {
                this.addCallFromFindme(llamada);
                this.tabView.setActiveIndex(0);// Tab Gestiones.
                PrimeFaces.current().ajax().update("formGestion:idTabView");
            }

        } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso!", "Debe hacer una Llamada!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    /**
     * verifica si el telefono ya existe.
     *
     * @param telefono
     * @return
     */
    private boolean existTelefonoContacto(String telefono) {

        if (this.llamadaList != null) {
            for (int index = 0; index < this.llamadaList.size(); index++) {
                if (this.llamadaList.get(index).getCallToNumber().equals(telefono)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     *
     * @param llamada
     */
    public void addCallFromFindme(TblLlamada llamada) {
        try {

            this.contacto = this.ejbContactoLocal.findById(this.contacto);

            TblTelefono objT = new TblTelefono();
            objT.setTelefono(llamada.getCallToNumber());
            objT.setIdContacto(this.contacto);
            objT.setIdTipotelefono(llamada.getIdTipotelefono());
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
            this.llamadaList.add(llamada);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso!", "Teléfono Registrado. Correcto!"));
            PrimeFaces.current().executeScript("PF('manageLlamarFindmeDialog').hide()");
            PrimeFaces.current().ajax().update("formGestion:messages", "formGestion:idTabView:tblTelefono");

        } catch (NumberFormatException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error!", "Error registrando Teléfono. Error!"));
            System.out.println(e.getMessage());
        }
    }

    public TabView getTabView() {
        return tabView;
    }

    public void setTabView(TabView tabView) {
        this.tabView = tabView;
    }

    /*
    ***************************************************************************    
    **************************** Eliminar Correo Electronico *******************
    ***************************************************************************    
     */
    /**
     *
     * @param email
     */
    public void deleteCorreoElectronico(String email) {
        try {
            if (email != null && !email.trim().equals("")) {
                for (int index = 0; index < this.contacto.getTblCorreoList().size(); index++) {
                    if (this.contacto.getTblCorreoList().get(index).getCorreo().equals(email)) {
                        this.contacto.getTblCorreoList().get(index).setEstado("INA");
                        this.contacto.getTblCorreoList().get(index).setUsuariomodifico(this.usuario.getUsuario());
                        this.contacto.getTblCorreoList().get(index).setFechamodifico(this.fechaHoy.getTime());
                    }
                }

                this.ejbContactoLocal.update(this.contacto);
                List<TblCorreo> correoList = this.ejbCorreoLocal.findByContactoEstado(this.contacto.getIdContacto(), "ACT");
                this.contacto.setTblCorreoList(correoList);

                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso!", "Correo electrónico Eliminado. Correcto!"));
                PrimeFaces.current().ajax().update("formGestion:messages", "formGestion:idDTCorreoContacto");

            }

        } catch (NumberFormatException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error!", "Error eliminando correo. Error!"));
            System.out.println(e.getMessage());
        }
    }

    /**
     * Agregar llamadas. Agregar Tipificaciones.
     */
    private void agregarLlamadaYTipificacion() {
        if (this.llamadaList != null && !this.llamadaList.isEmpty()) {
            for (int index = 0; index < this.llamadaList.size(); index++) {
                TblLlamada llamada = this.llamadaList.get(index);
                if (llamada.getIdTipificacion() != null && llamada.getIdTipificacion().getIdTipificacion() != null) {
                    if (this.gestion.getTblLlamadaList() != null && !this.gestion.getTblLlamadaList().isEmpty()) {
                        for (int i = 0; i < this.gestion.getTblLlamadaList().size(); i++) {

                            boolean true_CallToNumber = this.gestion.getTblLlamadaList().get(i).getCallToNumber().equals(llamada.getCallToNumber());
                            boolean true_Operacion = this.getOperacionSelected().getNumeroCuenta().equals(llamada.getOperacion());

                            if (true_CallToNumber && true_Operacion) {
                                Tipificacion tipificacion = llamada.getIdTipificacion();
                                Subtipificacion subtipificacion = llamada.getIdSubtipificacion();
                                TblResultadogestion resultadogestion = llamada.getIdResultadogestion();
                                TblResultadotercero respuesta = llamada.getIdResultadotercero();
                                Razonmora razonmora = llamada.getIdrazonmora();

                                if (tipificacion != null && tipificacion.getIdTipificacion() != null) {
                                    this.gestion.getTblLlamadaList().get(i).setIdTipificacion(tipificacion);
                                } else {
                                    this.gestion.getTblLlamadaList().get(i).setIdTipificacion(null);
                                }

                                if (subtipificacion != null && subtipificacion.getIdSubtipificacion() != null) {
                                    this.gestion.getTblLlamadaList().get(i).setIdSubtipificacion(subtipificacion);
                                } else {
                                    this.gestion.getTblLlamadaList().get(i).setIdSubtipificacion(null);
                                }

                                if (resultadogestion != null && resultadogestion.getIdResultadogestion() != null) {
                                    this.gestion.getTblLlamadaList().get(i).setIdResultadogestion(resultadogestion);
                                } else {
                                    this.gestion.getTblLlamadaList().get(i).setIdResultadogestion(null);
                                }

                                if (respuesta != null && respuesta.getIdResultadotercero() != null) {
                                    this.gestion.getTblLlamadaList().get(i).setIdResultadotercero(respuesta);
                                } else {
                                    this.gestion.getTblLlamadaList().get(i).setIdResultadotercero(null);
                                }

                                if (razonmora != null && razonmora.getIdrazonmora() != null) {
                                    this.gestion.getTblLlamadaList().get(i).setIdrazonmora(razonmora);
                                } else {
                                    this.gestion.getTblLlamadaList().get(i).setIdrazonmora(null);
                                }

                                if (this.gestion.getTblLlamadaList().get(i).getIdLlamada() != null) {
                                    this.gestion.getTblLlamadaList().get(i).setFechamodifico(this.fechaHoy.getTime());
                                    this.gestion.getTblLlamadaList().get(i).setUsuariomodifico(usuario.getUsuario());
                                } else {
                                    this.gestion.getTblLlamadaList().get(i).setFechaingreso(this.fechaHoy.getTime());
                                    this.gestion.getTblLlamadaList().get(i).setUsuarioingreso(usuario.getUsuario());
                                }

                                /*
                                this.gestion.getTblLlamadaList().get(i).setIdGestion(this.gestion);
                                this.gestion.getTblLlamadaList().get(i).setOperacion(this.operacionSelected.getNumeroCuenta());
                                 */
                            }
                        }

                    } else {
                        llamada.setIdGestion(this.gestion);
                        llamada.setOperacion(this.operacionSelected.getNumeroCuenta());
                        llamada.setFechaingreso(this.fechaHoy.getTime());
                        llamada.setUsuarioingreso(usuario.getUsuario());
                        this.gestion.getTblLlamadaList().add(llamada);
                    }
                }
            }
        }
    }

    /**
     *
     */
    private void agregarPromesas() {
        for (int index = 0; index < this.gestion.getTblLlamadaList().size(); index++) {
            if (this.gestion.getTblPromesaList() != null && !this.gestion.getTblPromesaList().isEmpty() && this.gestion.getTblPromesaList().size() > 0) {
                for (int i = 0; i < this.gestion.getTblPromesaList().size(); i++) {

                    boolean isCallToNumber = this.gestion.getTblLlamadaList().get(index).getCallToNumber().equals(this.gestion.getTblPromesaList().get(i).getTelefono());
                    boolean isOperacion = this.gestion.getTblLlamadaList().get(index).getOperacion().equals(this.gestion.getTblPromesaList().get(i).getOperacion());

                    if (isCallToNumber && isOperacion) {
                        this.gestion.getTblPromesaList().get(i).setIdLlamada(this.gestion.getTblLlamadaList().get(index));
                    }
                }
            }
        }
    }// agregarPromesas


    /*
*******************************************************************************
Multitipificacion
*******************************************************************************
     */
    /**
     * asociar la operacion seleccionada con la gestion y llamda.
     *
     * @param operacion
     */
    private void addLlamadaToLlamada(TblLlamada pLlamada) {
        if (pLlamada != null && this.selectedLlamada != null) {

            Long idLlamada = pLlamada.getIdLlamada();
            String callLogId = pLlamada.getCallLogId();
            Date dateIni = pLlamada.getDateIni();
            Date dateEnd = pLlamada.getDateEnd();
            String callFromNumber = pLlamada.getCallFromNumber();
            String callToNumber = pLlamada.getCallToNumber();
            String dialstatus = pLlamada.getDialstatus();
            Date callLength = pLlamada.getCallLength();
            Integer conversationLength = pLlamada.getConversationLength();
            String estado = pLlamada.getEstado();
            String usuarioingreso = pLlamada.getUsuarioingreso();
            Date fechaingreso = pLlamada.getFechaingreso();
            String usuariomodifico = pLlamada.getUsuariomodifico();
            Date fechamodifico = pLlamada.getFechamodifico();
            Razonmora idrazonmora = pLlamada.getIdrazonmora();
            Subtipificacion idSubtipificacion = pLlamada.getIdSubtipificacion();
            TblResultadogestion idResultadogestion = pLlamada.getIdResultadogestion();
            TblResultadotercero idResultadotercero = pLlamada.getIdResultadotercero();
            Tipificacion idTipificacion = pLlamada.getIdTipificacion();
            Tipotelefono idTipotelefono = pLlamada.getIdTipotelefono();
            String operacion = pLlamada.getOperacion();
            List<TblPromesa> tblPromesaList = pLlamada.getTblPromesaList();

            if (this.gestion != null) {
                this.selectedLlamada.setIdGestion(this.gestion);
            }

            if (idLlamada != null) {
                this.selectedLlamada.setIdLlamada(idLlamada);
            }

            if (callLogId != null && !callLogId.trim().equals("")) {
                this.selectedLlamada.setCallLogId(callLogId);
            }

            if (dateIni != null) {
                this.selectedLlamada.setDateIni(dateIni);
            }

            if (dateEnd != null) {
                this.selectedLlamada.setDateEnd(dateEnd);
            }

            if (callFromNumber != null && !callFromNumber.trim().equals("")) {
                this.selectedLlamada.setCallFromNumber(callFromNumber);
            }

            if (callToNumber != null && !callToNumber.trim().equals("")) {
                this.selectedLlamada.setCallToNumber(callToNumber);
            }

            if (dialstatus != null && !dialstatus.trim().equals("")) {
                this.selectedLlamada.setDialstatus(dialstatus);
            }

            if (callLength != null) {
                this.selectedLlamada.setCallLength(callLength);
            }

            if (conversationLength != null) {
                this.selectedLlamada.setConversationLength(conversationLength);
            }

            if (estado != null && !estado.trim().equals("")) {
                this.selectedLlamada.setEstado(estado);
            }

            if (usuarioingreso != null && usuarioingreso.trim().equals("")) {
                this.selectedLlamada.setUsuarioingreso(usuarioingreso);
            }

            if (fechaingreso != null) {
                this.selectedLlamada.setFechaingreso(fechaingreso);
            }

            if (usuariomodifico != null && usuariomodifico.trim().equals("")) {
                this.selectedLlamada.setUsuariomodifico(usuariomodifico);
            }

            if (fechamodifico != null) {
                this.selectedLlamada.setFechamodifico(fechamodifico);
            }

            if (idrazonmora != null && idrazonmora.getIdrazonmora() != null) {
                this.selectedLlamada.setIdrazonmora(idrazonmora);
            } else {
                this.selectedLlamada.setIdrazonmora(null);
            }

            if (idSubtipificacion != null && idSubtipificacion.getIdSubtipificacion() != null) {
                this.selectedLlamada.setIdSubtipificacion(idSubtipificacion);
            } else {
                this.selectedLlamada.setIdSubtipificacion(null);
            }

            if (idResultadogestion != null && idResultadogestion.getIdResultadogestion() != null) {
                this.selectedLlamada.setIdResultadogestion(idResultadogestion);
            } else {
                this.selectedLlamada.setIdResultadogestion(null);
            }

            if (idResultadotercero != null && idResultadotercero.getIdResultadotercero() != null) {
                this.selectedLlamada.setIdResultadotercero(idResultadotercero);
            } else {
                this.selectedLlamada.setIdResultadotercero(null);
            }

            if (idTipificacion != null && idTipificacion.getIdTipificacion() != null) {
                this.selectedLlamada.setIdTipificacion(idTipificacion);
            } else {
                this.selectedLlamada.setIdTipificacion(null);
            }

            if (idTipotelefono != null && idTipotelefono.getIdTipotelefono() != null) {
                this.selectedLlamada.setIdTipotelefono(idTipotelefono);
            } else {
                this.selectedLlamada.setIdTipotelefono(null);
            }

            if (operacion != null && !operacion.trim().equals("")) {
                this.selectedLlamada.setOperacion(operacion);
            }

            if (tblPromesaList != null && !tblPromesaList.isEmpty() && tblPromesaList.size() > 0) {
                if (this.selectedLlamada.getTblPromesaList() == null) {
                    this.selectedLlamada.setTblPromesaList(tblPromesaList);

                } else if (!this.selectedLlamada.getTblPromesaList().isEmpty() || this.selectedLlamada.getTblPromesaList().size() > 0) {
                    this.selectedLlamada.getTblPromesaList().addAll(tblPromesaList);
                }
            }

        }
    }

    /**
     *
     * @param llamada
     */
    private void addLlamadaToGestion(TblLlamada pllamada) {
        String callLogId = null;
        if (this.getSelectedLlamada() != null && this.getSelectedLlamada().getCallLogId() != null && !this.getSelectedLlamada().getCallLogId().trim().equals("")) {
            callLogId = this.getSelectedLlamada().getCallLogId();
        }

        boolean laEncontro = false;
        if (this.gestion.getTblLlamadaList().size() > 0) {
            for (int index = 0; index < this.gestion.getTblLlamadaList().size(); index++) {
                boolean isOperacion = this.gestion.getTblLlamadaList().get(index).getOperacion().equals(pllamada.getOperacion());
                boolean isTelefono = this.gestion.getTblLlamadaList().get(index).getCallToNumber().equals(pllamada.getCallToNumber());
                if (isOperacion && isTelefono) {
                    //this.gestion.getTblLlamadaList().get(index).setIdLlamada(pllamada.getIdLlamada());
                    this.gestion.getTblLlamadaList().get(index).setIdGestion(pllamada.getIdGestion());

                    if (pllamada.getCallLogId() != null && !pllamada.getCallLogId().trim().equals("")) {
                        this.gestion.getTblLlamadaList().get(index).setCallLogId(pllamada.getCallLogId());

                    } else {
                        this.gestion.getTblLlamadaList().get(index).setCallLogId(callLogId);
                    }

                    this.gestion.getTblLlamadaList().get(index).setDateIni(pllamada.getDateIni());
                    this.gestion.getTblLlamadaList().get(index).setDateEnd(pllamada.getDateEnd());
                    this.gestion.getTblLlamadaList().get(index).setCallFromNumber(pllamada.getCallFromNumber());
                    //this.gestion.getTblLlamadaList().get(index).setCallToNumber(pllamada.getCallToNumber());                    
                    this.gestion.getTblLlamadaList().get(index).setDialstatus(pllamada.getDialstatus());
                    this.gestion.getTblLlamadaList().get(index).setCallLength(pllamada.getCallLength());
                    this.gestion.getTblLlamadaList().get(index).setConversationLength(pllamada.getConversationLength());
                    this.gestion.getTblLlamadaList().get(index).setEstado(ConstanteComun.Ingresar);
                    this.gestion.getTblLlamadaList().get(index).setIdTipificacion(pllamada.getIdTipificacion());
                    this.gestion.getTblLlamadaList().get(index).setIdSubtipificacion(pllamada.getIdSubtipificacion());
                    this.gestion.getTblLlamadaList().get(index).setIdrazonmora(pllamada.getIdrazonmora());
                    this.gestion.getTblLlamadaList().get(index).setIdResultadogestion(pllamada.getIdResultadogestion());
                    this.gestion.getTblLlamadaList().get(index).setIdResultadotercero(pllamada.getIdResultadotercero());
                    this.gestion.getTblLlamadaList().get(index).setIdTipotelefono(pllamada.getIdTipotelefono());
                    this.gestion.getTblLlamadaList().get(index).setUsuariomodifico(this.usuario.getUsuario());
                    this.gestion.getTblLlamadaList().get(index).setFechamodifico(this.fechaHoy.getTime());

                    List<TblPromesa> promesas = pllamada.getTblPromesaList();
                    if (promesas != null && !promesas.isEmpty() && promesas.size() > 0) {
                        this.gestion.getTblLlamadaList().get(index).getTblPromesaList().addAll(promesas);
                    }
                    laEncontro = true;
                }
            }

            if (!laEncontro) {
                pllamada.setIdLlamada(null);
                pllamada.setEstado(ConstanteComun.Ingresar);
                pllamada.setUsuarioingreso(this.usuario.getUsuario());
                pllamada.setFechaingreso(this.fechaHoy.getTime());
                this.gestion.getTblLlamadaList().add(pllamada);
            }

        } else {
            pllamada.setIdLlamada(null);
            pllamada.setEstado(ConstanteComun.Ingresar);
            pllamada.setUsuarioingreso(this.usuario.getUsuario());
            pllamada.setFechaingreso(this.fechaHoy.getTime());
            this.gestion.getTblLlamadaList().add(pllamada);
        }
    }

    /**
     * Actualuzar cartera. Cambiar estado a gestionado. GES. Actualiza el saldo
     * de la cartera.
     */
    private void actualizarSaldos() {
        if (this.carteraList != null) {
            for (int indexOP = 0; indexOP < this.carteraList.size(); indexOP++) {
                List<TblSaldo> saldoList = this.carteraList.get(indexOP).getTblSaldoList();
                if (saldoList != null) {
                    for (int index = 0; index < saldoList.size(); index++) {
                        if (saldoList.get(index).getSaldoGestion() != null && saldoList.get(index).getSaldoGestion().compareTo(BigDecimal.ZERO) > 0) {
                            saldoList.get(index).setUsuariomodifico(this.usuario.getUsuario());
                            saldoList.get(index).setFechamodifico(this.fechaHoy.getTime());
                            this.ejbSaldoLocal.update(saldoList.get(index));
                        }
                    }
                }
                // actualuzar cartera. Cambiar estado a gestionado. GES
                this.carteraList.get(indexOP).setUsuarioModifico(this.usuario.getUsuario());
                this.carteraList.get(indexOP).setFechaModifico(this.fechaHoy.getTime());
                this.carteraList.get(indexOP).setEstado("GES");
                this.ejbCarteraLocal.update(this.carteraList.get(indexOP));
            }
        }
    }

    /**
     *
     * @return
     */
    private boolean validarTipificacion() {
        if (this.gestion.getTblLlamadaList() != null && !this.gestion.getTblLlamadaList().isEmpty() && this.gestion.getTblLlamadaList().size() > 0) {
            for (int index = 0; index < this.gestion.getTblLlamadaList().size(); index++) {
                String telefono = this.gestion.getTblLlamadaList().get(index).getCallToNumber();
                Tipificacion tipificacion = this.gestion.getTblLlamadaList().get(index).getIdTipificacion();
                Integer idTipificacion = null;
                if (tipificacion != null) {
                    idTipificacion = tipificacion.getIdTipificacion();
                }

                boolean isNullTipificacion = tipificacion == null;
                boolean isNullIDTipificacion = idTipificacion == null;

                if (isNullTipificacion || isNullIDTipificacion) {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso!", "( " + telefono + " ) - " + "Tipificación requerido!");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                    return false;
                }

                if (this.gestion.getTblLlamadaList().get(index).getIdSubtipificacion() == null || this.gestion.getTblLlamadaList().get(index).getIdSubtipificacion().getIdSubtipificacion() == null) {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso!", "( " + telefono + " ) - " + "Sub-Tipificación requerido!");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                    return false;
                }

                /*
                            Razon de mora.
                            No es requerido: si la tipificacion es;
                            --- Promesa de pago 1 - 
                            --- Contacto sin promesa - 6
                            --- No Contesta -  4
                            --- Mensaje Familiar - 3
                            --- Contacto con tercero - 5
                 */
                if (!isNullIDTipificacion) {
                    boolean isPP = idTipificacion.equals(1);
                    /* Promesas. requerido: si la tipificacion es; Promesa de pago */
                    if (isPP) {
                        if (this.gestion.getTblPromesaList() == null || this.gestion.getTblPromesaList().isEmpty() || this.gestion.getTblPromesaList().size() <= 0) {
                            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso!", "( " + telefono + " ) - " + "Debe registrar una PROMESA. Requerido!");
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                            return false;
                        }
                    }// Promesa de pago                                
                }// isNullIDTipificacion

            }// for
        }// if
        return true;
    }

    /**
     *
     */
    private void multitipificacion() {

        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(this.fechaHoy.getTime());
        int DAY_OF_MONTH = c1.get(Calendar.DAY_OF_MONTH);
        int MONTH = c1.get(Calendar.MONTH);
        int YEAR = c1.get(Calendar.YEAR);

        boolean PRP = false; // PRP - promesa de pago
        boolean REP = false; // REP - Recordatorio de pago
        boolean ESC = false; // ESC - Escalar.

        TblLlamada llamadaPRP = null;
        TblLlamada llamadaREP = null;
        TblLlamada llamadaESC = null;
        TblLlamada llamadaDefault = null;

        for (TblLlamada llamada : this.gestion.getTblLlamadaList()) {
            Date fechaIngreso = llamada.getFechaingreso();
            c2.setTime(fechaIngreso);
            int dia = c2.get(Calendar.DAY_OF_MONTH);
            int mes = c2.get(Calendar.MONTH);
            int anno = c2.get(Calendar.YEAR);
            boolean diaDAY = dia == DAY_OF_MONTH;
            boolean mesMONTH = mes == MONTH;
            boolean annoYEAR = anno == YEAR;

            if (llamada.getIdTipificacion().getCodigo().equals("PRP") && diaDAY && mesMONTH && annoYEAR) {
                PRP = true;
                llamadaPRP = new TblLlamada();
                llamadaPRP.setIdLlamada(null);
                llamadaPRP.setIdGestion(llamada.getIdGestion());
                llamadaPRP.setCallLogId(llamada.getCallLogId());
                llamadaPRP.setDateIni(llamada.getDateIni());
                llamadaPRP.setDateEnd(llamada.getDateEnd());
                llamadaPRP.setCallFromNumber(llamada.getCallFromNumber());
                llamadaPRP.setCallToNumber(llamada.getCallToNumber());
                llamadaPRP.setDialstatus(llamada.getDialstatus());
                llamadaPRP.setCallLength(llamada.getCallLength());
                llamadaPRP.setConversationLength(llamada.getConversationLength());
                llamadaPRP.setEstado(llamada.getEstado());
                llamadaPRP.setIdTipificacion(llamada.getIdTipificacion());
                llamadaPRP.setIdSubtipificacion(llamada.getIdSubtipificacion());
                llamadaPRP.setIdrazonmora(llamada.getIdrazonmora());
                llamadaPRP.setIdResultadogestion(llamada.getIdResultadogestion());
                llamadaPRP.setIdResultadotercero(llamada.getIdResultadotercero());
                llamadaPRP.setIdTipotelefono(llamada.getIdTipotelefono());
                llamadaPRP.setUsuarioingreso(llamada.getUsuarioingreso());
                llamadaPRP.setFechaingreso(llamada.getFechaingreso());
                llamadaPRP.setUsuariomodifico(llamada.getUsuariomodifico());
                llamadaPRP.setFechamodifico(llamada.getFechamodifico());
                llamadaPRP.setOperacion(llamada.getOperacion());

            } else if (llamada.getIdTipificacion().getCodigo().equals("REP") && diaDAY && mesMONTH && annoYEAR) {
                REP = true;
                llamadaREP = new TblLlamada();
                llamadaPRP.setIdLlamada(null);
                llamadaPRP.setIdGestion(llamada.getIdGestion());
                llamadaPRP.setCallLogId(llamada.getCallLogId());
                llamadaPRP.setDateIni(llamada.getDateIni());
                llamadaPRP.setDateEnd(llamada.getDateEnd());
                llamadaPRP.setCallFromNumber(llamada.getCallFromNumber());
                llamadaPRP.setCallToNumber(llamada.getCallToNumber());
                llamadaPRP.setDialstatus(llamada.getDialstatus());
                llamadaPRP.setCallLength(llamada.getCallLength());
                llamadaPRP.setConversationLength(llamada.getConversationLength());
                llamadaPRP.setEstado(llamada.getEstado());
                llamadaPRP.setIdTipificacion(llamada.getIdTipificacion());
                llamadaPRP.setIdSubtipificacion(llamada.getIdSubtipificacion());
                llamadaPRP.setIdrazonmora(llamada.getIdrazonmora());
                llamadaPRP.setIdResultadogestion(llamada.getIdResultadogestion());
                llamadaPRP.setIdResultadotercero(llamada.getIdResultadotercero());
                llamadaPRP.setIdTipotelefono(llamada.getIdTipotelefono());
                llamadaPRP.setUsuarioingreso(llamada.getUsuarioingreso());
                llamadaPRP.setFechaingreso(llamada.getFechaingreso());
                llamadaPRP.setUsuariomodifico(llamada.getUsuariomodifico());
                llamadaPRP.setFechamodifico(llamada.getFechamodifico());
                llamadaPRP.setOperacion(llamada.getOperacion());

            } else if (llamada.getIdTipificacion().getCodigo().equals("ESC") && diaDAY && mesMONTH && annoYEAR) {
                ESC = true;
                llamadaESC = new TblLlamada();
                llamadaESC.setIdLlamada(null);
                llamadaESC.setIdGestion(llamada.getIdGestion());
                llamadaESC.setCallLogId(llamada.getCallLogId());
                llamadaESC.setDateIni(llamada.getDateIni());
                llamadaESC.setDateEnd(llamada.getDateEnd());
                llamadaESC.setCallFromNumber(llamada.getCallFromNumber());
                llamadaESC.setCallToNumber(llamada.getCallToNumber());
                llamadaESC.setDialstatus(llamada.getDialstatus());
                llamadaESC.setCallLength(llamada.getCallLength());
                llamadaESC.setConversationLength(llamada.getConversationLength());
                llamadaESC.setEstado(llamada.getEstado());
                llamadaESC.setIdTipificacion(llamada.getIdTipificacion());
                llamadaESC.setIdSubtipificacion(llamada.getIdSubtipificacion());
                llamadaESC.setIdrazonmora(llamada.getIdrazonmora());
                llamadaESC.setIdResultadogestion(llamada.getIdResultadogestion());
                llamadaESC.setIdResultadotercero(llamada.getIdResultadotercero());
                llamadaESC.setIdTipotelefono(llamada.getIdTipotelefono());
                llamadaESC.setUsuarioingreso(llamada.getUsuarioingreso());
                llamadaESC.setFechaingreso(llamada.getFechaingreso());
                llamadaESC.setUsuariomodifico(llamada.getUsuariomodifico());
                llamadaESC.setFechamodifico(llamada.getFechamodifico());
                llamadaESC.setOperacion(llamada.getOperacion());

            } else {
                llamadaDefault = new TblLlamada();
                llamadaDefault.setIdLlamada(null);
                llamadaDefault.setIdGestion(llamada.getIdGestion());
                llamadaDefault.setCallLogId(llamada.getCallLogId());
                llamadaDefault.setDateIni(llamada.getDateIni());
                llamadaDefault.setDateEnd(llamada.getDateEnd());
                llamadaDefault.setCallFromNumber(llamada.getCallFromNumber());
                llamadaDefault.setCallToNumber(llamada.getCallToNumber());
                llamadaDefault.setDialstatus(llamada.getDialstatus());
                llamadaDefault.setCallLength(llamada.getCallLength());
                llamadaDefault.setConversationLength(llamada.getConversationLength());
                llamadaDefault.setEstado(llamada.getEstado());
                llamadaDefault.setIdTipificacion(llamada.getIdTipificacion());
                llamadaDefault.setIdSubtipificacion(llamada.getIdSubtipificacion());
                llamadaDefault.setIdrazonmora(llamada.getIdrazonmora());
                llamadaDefault.setIdResultadogestion(llamada.getIdResultadogestion());
                llamadaDefault.setIdResultadotercero(llamada.getIdResultadotercero());
                llamadaDefault.setIdTipotelefono(llamada.getIdTipotelefono());
                llamadaDefault.setUsuarioingreso(llamada.getUsuarioingreso());
                llamadaDefault.setFechaingreso(llamada.getFechaingreso());
                llamadaDefault.setUsuariomodifico(llamada.getUsuariomodifico());
                llamadaDefault.setFechamodifico(llamada.getFechamodifico());
                llamadaDefault.setOperacion(llamada.getOperacion());
            }
        }

        if (this.carteraList != null) {
            boolean operacionExist = false;
            for (TblCartera cartera : this.carteraList) {
                String numeroCuenta = cartera.getNumeroCuenta();
                for (TblLlamada llamada : this.gestion.getTblLlamadaList()) {
                    Date fechaIngreso = llamada.getFechaingreso();
                    c2.setTime(fechaIngreso);
                    int dia = c2.get(Calendar.DAY_OF_MONTH);
                    int mes = c2.get(Calendar.MONTH);
                    int anno = c2.get(Calendar.YEAR);
                    boolean diaDAY = dia == DAY_OF_MONTH;
                    boolean mesMONTH = mes == MONTH;
                    boolean annoYEAR = anno == YEAR;
                    String operacion = llamada.getOperacion();
                    if (numeroCuenta.equals(operacion) && diaDAY && mesMONTH && annoYEAR) {
                        operacionExist = true;
                    }
                }

                if (!operacionExist) {
                    if (PRP) {
                        llamadaPRP.setIdLlamada(null);
                        llamadaPRP.setOperacion(numeroCuenta);
                        llamadaPRP.setIdTipificacion(this.ejbTipificacionLocal.findByCodigo("CSP"));
                        llamadaPRP.setIdSubtipificacion(this.ejbSubtipificacionLocal.findByCodigo("ICP"));
                        this.gestion.getTblLlamadaList().add(llamadaPRP);

                    } else if (REP) {
                        llamadaREP.setIdLlamada(null);
                        llamadaREP.setOperacion(numeroCuenta);
                        llamadaREP.setIdTipificacion(this.ejbTipificacionLocal.findByCodigo("CSP"));
                        llamadaREP.setIdSubtipificacion(this.ejbSubtipificacionLocal.findByCodigo("ICP"));
                        this.gestion.getTblLlamadaList().add(llamadaREP);

                    } else if (ESC) {
                        llamadaESC.setIdLlamada(null);
                        llamadaESC.setOperacion(numeroCuenta);
                        llamadaESC.setIdTipificacion(this.ejbTipificacionLocal.findByCodigo("CSP"));
                        llamadaESC.setIdSubtipificacion(this.ejbSubtipificacionLocal.findByCodigo("ICP"));
                        this.gestion.getTblLlamadaList().add(llamadaESC);

                    } else {
                        llamadaDefault.setIdLlamada(null);
                        llamadaDefault.setOperacion(numeroCuenta);
                        //llamadaDefault.setIdTipificacion(this.ejbTipificacionLocal.findByCodigo("CSP"));
                        this.gestion.getTblLlamadaList().add(llamadaESC);
                    }
                }
                operacionExist = false;
            }
        }

    }

    /**
     *
     */
    private void actualizarEstadoCola() {        
        if (this.colaSelected != null && this.colaSelected.getIdCola() != null) {
            this.colaSelected.setEstado(ConstanteComun.GESTIONADO);// GES - Gestionado
            this.colaSelected.setUsuariomodifico(this.usuario.getUsuario());
            this.colaSelected.setFechamodifico(this.fechaHoy.getTime());
            this.colaSelected.setCodigoGestor(this.usuario.getCodigoGestor());
            //this.colaSelected.setIdGestion(this.gestion);
            this.ejbColaLocal.update(this.colaSelected);
            this.ejbColaLocal.updateIdGestion(this.gestion.getIdGestion(), this.colaSelected.getIdFiltrocola().getIdFiltrocola(), this.colaSelected.getIdCola(), this.colaSelected.getIdentificacion());
        }
    }

    /**
     * Busca el siguiente cliente sin gestionar en la cola.
     */
    private boolean gestionarSiguienteClienteEnCola() {
        if (this.colaSelected != null) {
            TblCola siguienteEnCola = new TblCola();
            siguienteEnCola.setCodigoCartera(this.colaSelected.getCodigoCartera());
            siguienteEnCola.setIdFiltrocola(this.colaSelected.getIdFiltrocola());
            siguienteEnCola.setEstado(ConstanteComun.SIN_GESTION);// SIN - Sin Gestion
            siguienteEnCola = this.ejbColaLocal.buscarSiguienteClienteEnCola(siguienteEnCola);
            if (siguienteEnCola != null) {
                String identificacion = siguienteEnCola.getIdentificacion();
                List<TblCartera> operacionList = this.carteraController.searchCarteraByIdentificacion(identificacion);
                if (operacionList != null && !operacionList.isEmpty() && operacionList.size() > 0) {
                    TblCartera operacion_cartera = operacionList.get(0);
                    this.setCarteraTOGestion(operacion_cartera);
                    return true;
                }
            }
        }
        
        return false;        
    }

    public TblCola getColaSelected() {
        return colaSelected;
    }

    public void setColaSelected(TblCola colaSelected) {
        this.colaSelected = colaSelected;
    }    

}//end
