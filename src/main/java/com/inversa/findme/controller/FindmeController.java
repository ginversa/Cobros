/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.findme.controller;

import com.inversa.cobros.controller.CarteraController;
import com.inversa.cobros.controller.GestionController;
import com.inversa.cobros.ejb.PromesaService;
import com.inversa.cobros.model.TblCartera;
import com.inversa.cobros.model.TblLlamada;
import com.inversa.cobros.model.TblPromesa;
import com.inversa.cobros.model.TblUsuario;
import com.inversa.findme.ejb.FindmeService;
import com.inversa.findme.model.ArbolFamiliar;
import com.inversa.findme.model.HistorialCorreo;
import com.inversa.findme.model.HistorialDireccion;
import com.inversa.findme.model.HistorialJudicial;
import com.inversa.findme.model.HistorialLaboral;
import com.inversa.findme.model.HistorialTelefono;
import com.inversa.findme.model.PropiedadRegistrada;
import com.inversa.findme.model.SociedadAnonima;
import com.inversa.findme.model.Vehiculo;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
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
import org.primefaces.component.tabview.TabView;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.TabChangeEvent;

/**
 *
 * @author Z420WK
 */
@Named
@ViewScoped
public class FindmeController implements Serializable {

    @Inject
    private FindmeService ejbLocal;

    @Inject
    private PromesaService ejbPromesaLocal;

    private List<HistorialLaboral> historialLaboralList;

    private List<HistorialCorreo> historialCorreoList;

    private List<HistorialTelefono> historialTelefonoList;

    private List<Vehiculo> vehiculoList;

    private List<PropiedadRegistrada> propiedadRegistradaList;

    private List<HistorialDireccion> historialDireccionList;

    private List<HistorialJudicial> historialJudicialList;

    private List<SociedadAnonima> sociedadAnonimaList;

    private List<ArbolFamiliar> arbolFamiliarList;

    @Inject
    private GestionController gestionController;

    /*
    @Inject
    private DeudorController deudorController;
    private TblDeudor deudor;
    */
    
    @Inject
    private CarteraController carteraController;
    
    private TblCartera cartera;
    private String cedula;

    private Calendar fechaHoy;
    private TblUsuario usuario;

    private List<TblLlamada> llamadaList;
    private TblLlamada selectedLlamada;
    private HistorialTelefono selectedHistorialTelefono;

    private List<TblPromesa> promesaList;
    private TblPromesa selectedPromesa;

    @PostConstruct
    public void init() {
        
        this.selectedLlamada = new TblLlamada();
        
        this.llamadaList = new ArrayList<TblLlamada>();
        this.promesaList = new ArrayList<TblPromesa>();

        // Usuario de session...
        this.usuario = (TblUsuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        this.fechaHoy = Calendar.getInstance();

        this.cartera = this.carteraController.getCartera();
                
        if (this.cartera != null) {
            cedula = this.cartera.getIdentificacion();
            //cedula = "108020318";
            //cedula.trim();
            //cargarFindme();
        }

        
    }

    public List<HistorialLaboral> getHistorialLaboralList() {
        return historialLaboralList;
    }

    public void setHistorialLaboralList(List<HistorialLaboral> historialLaboralList) {
        this.historialLaboralList = historialLaboralList;
    }

    public List<HistorialCorreo> getHistorialCorreoList() {
        return historialCorreoList;
    }

    public void setHistorialCorreoList(List<HistorialCorreo> historialCorreoList) {
        this.historialCorreoList = historialCorreoList;
    }

    public List<HistorialTelefono> getHistorialTelefonoList() {
        return historialTelefonoList;
    }

    public void setHistorialTelefonoList(List<HistorialTelefono> historialTelefonoList) {
        this.historialTelefonoList = historialTelefonoList;
    }

    public List<Vehiculo> getVehiculoList() {
        return vehiculoList;
    }

    public void setVehiculoList(List<Vehiculo> vehiculoList) {
        this.vehiculoList = vehiculoList;
    }

    public List<PropiedadRegistrada> getPropiedadRegistradaList() {
        return propiedadRegistradaList;
    }

    public void setPropiedadRegistradaList(List<PropiedadRegistrada> propiedadRegistradaList) {
        this.propiedadRegistradaList = propiedadRegistradaList;
    }

    public List<HistorialDireccion> getHistorialDireccionList() {
        return historialDireccionList;
    }

    public void setHistorialDireccionList(List<HistorialDireccion> historialDireccionList) {
        this.historialDireccionList = historialDireccionList;
    }

    public List<HistorialJudicial> getHistorialJudicialList() {
        return historialJudicialList;
    }

    public void setHistorialJudicialList(List<HistorialJudicial> historialJudicialList) {
        this.historialJudicialList = historialJudicialList;
    }

    public List<SociedadAnonima> getSociedadAnonimaList() {
        return sociedadAnonimaList;
    }

    public void setSociedadAnonimaList(List<SociedadAnonima> sociedadAnonimaList) {
        this.sociedadAnonimaList = sociedadAnonimaList;
    }

    public List<ArbolFamiliar> getArbolFamiliarList() {
        return arbolFamiliarList;
    }

    public void setArbolFamiliarList(List<ArbolFamiliar> arbolFamiliarList) {
        this.arbolFamiliarList = arbolFamiliarList;
    }

    /*
    private Integer activeTabIndex = 0;

    public Integer getActiveTabIndex() {
        return activeTabIndex;
    }

    public void setActiveTabIndex(Integer activeTabIndex) {
        this.activeTabIndex = activeTabIndex;
    }

    public void onTabViewChange(TabChangeEvent event) {
        TabView tv = (TabView) event.getComponent();
        this.activeTabIndex = tv.getActiveIndex();
        System.out.println("ActiveIndex                             : " + tv.getActiveIndex());
        System.out.println("event.getTab().getId()                  : " + event.getTab().getId());
        System.out.println("tv.getChildren().indexOf(event.getTab()): " + tv.getChildren().indexOf(event.getTab()));
        int index = tv.getChildren().indexOf(event.getTab());
        if (index == 1) {
            cargarFindme();
        }
    }
*/
    /**
     *
     */
    public void cargarFindme() {
        if (cedula != null && !cedula.trim().equals("")) {
            this.historialTelefonoList = this.ejbLocal.historial_telefonos(cedula);
            this.historialCorreoList = this.ejbLocal.historial_correos(cedula);
            this.historialDireccionList = this.ejbLocal.historial_direcciones(cedula);
            this.arbolFamiliarList = this.ejbLocal.arbol_familiar(cedula);
            this.historialLaboralList = this.ejbLocal.analisis_laboral(cedula);
            this.sociedadAnonimaList = this.ejbLocal.sociedades_anonimas(cedula);
            this.propiedadRegistradaList = this.ejbLocal.propiedades_registradas(cedula);
            this.vehiculoList = this.ejbLocal.consulta_vehiculos(cedula);
            this.historialJudicialList = this.ejbLocal.historial_judicial(cedula);
        }

        PrimeFaces.current().ajax().update("formGestion:idTabView:idHistorialTelefonos", "formGestion:idTabView:idHistorialDireccion", "formGestion:idTabView:idArbolFamiliar", "formGestion:idTabView:idAnalisisLaboral", "formGestion:idTabView:idSociedadAnonima", "formGestion:idTabView:idPropiedadRegistrada", "formGestion:idTabView:idVehiculo", "formGestion:idTabView:idHistorialJudicial");
    }

    /*
***************************************************************************    
     */
    private static Client cliente;
    private static WebTarget webTarget;
    private TblLlamada llamada_En_Proceso;

    private String clienteOperacion;

    /**
     *
     * @param historialTelefono
     */
    public void generarLlamada(HistorialTelefono historialTelefono) {
        if (historialTelefono != null) {
            
            if(this.selectedHistorialTelefono == null){
                this.selectedHistorialTelefono = historialTelefono;
            }            
            
            String telefono = historialTelefono.getTelefono();
            if (telefono != null && !telefono.trim().equals("")) {
                TblLlamada callToNumber = new TblLlamada();
                callToNumber.setCallToNumber(telefono);
                this.generarLlamada(callToNumber);
            }
        }
    }

    public void generarLlamada(TblLlamada callToNumber) {
        callToNumber.setIdLlamada(null);
        System.out.println("Numero Seleccionado callToNumber: " + callToNumber.getCallToNumber());

        if (callToNumber != null && callToNumber.getCallToNumber() != null && !callToNumber.getCallToNumber().trim().equals("")) {
            String telefono = callToNumber.getCallToNumber();
            this.selectedLlamada = callToNumber; // llamada seleccionada...
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
                this.selectedLlamada = callToNumber; // llamada seleccionada...
                System.out.println("Telefono Seleccionado: " + this.selectedLlamada.getCallToNumber());

            }
        }// if
    }

/*
    public void onAddNewPromesa() {
        // Add one new promesa to the list:
        if (this.selectedLlamada != null) {
            TblPromesa promesa = new TblPromesa();

            if (this.usuario != null) {
                promesa.setUsuarioingreso(this.usuario.getUsuario());
            }
            promesa.setIdGestion(this.gestionController.getGestion());
            promesa.setIdLlamada(this.llamada_En_Proceso);
            promesa.setOperacion(null);
            promesa.setTelefono(this.selectedLlamada.getCallToNumber());
            promesa.setFechaPago(this.fechaHoy.getTime());
            promesa.setMtopago(BigDecimal.ZERO);
            //promesa.setMoneda("CRC");
            promesa.setEstado("ING");
            promesa.setFechaingreso(this.fechaHoy.getTime());
            this.promesaList.add(promesa);
            FacesMessage msg = new FacesMessage("Promesa Agregada: ", promesa.getTelefono());
            FacesContext.getCurrentInstance().addMessage(null, msg);

        } else {
            FacesMessage msg = new FacesMessage("Promesa NO Agregada. Debe hacer una llamada! ", null);
            FacesContext.getCurrentInstance().addMessage(null, msg);

        }
    }
*/
    
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

    public TblLlamada getLlamada_En_Proceso() {
        return llamada_En_Proceso;
    }

    public void setLlamada_En_Proceso(TblLlamada llamada_En_Proceso) {
        this.llamada_En_Proceso = llamada_En_Proceso;
    }

    public String getClienteOperacion() {
        return clienteOperacion;
    }

    public void setClienteOperacion(String clienteOperacion) {
        this.clienteOperacion = clienteOperacion;
    }

    public TblPromesa getSelectedPromesa() {
        return selectedPromesa;
    }

    public void setSelectedPromesa(TblPromesa selectedPromesa) {
        this.selectedPromesa = selectedPromesa;
    }

    public List<TblPromesa> getPromesaList() {
        return promesaList;
    }

    public void setPromesaList(List<TblPromesa> promesaList) {
        this.promesaList = promesaList;
    }

    public HistorialTelefono getSelectedHistorialTelefono() {
        return selectedHistorialTelefono;
    }

    public void setSelectedHistorialTelefono(HistorialTelefono selectedHistorialTelefono) {
        this.selectedHistorialTelefono = selectedHistorialTelefono;
    }   
    

    public void onRowEdit(RowEditEvent<TblPromesa> event) {
        FacesMessage msg = new FacesMessage("Promesa Editada", event.getObject().getFechaPago().toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowCancel(RowEditEvent<TblPromesa> event) {
        FacesMessage msg = new FacesMessage("Edición Cancelada", event.getObject().getFechaPago().toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void deletePromesa() {
        for (int index = 0; index < this.promesaList.size(); index++) {
            if (this.selectedPromesa != null && (this.promesaList != null && this.promesaList.size() > 0)) {

                boolean isTrueTelefono = this.promesaList.get(index).getTelefono().equals(this.selectedPromesa.getTelefono()) ? true : false;
                boolean isTrueFechaPago = this.promesaList.get(index).getFechaPago().equals(this.selectedPromesa.getFechaPago()) ? true : false;
                boolean isTrueMtopago = this.promesaList.get(index).getMtopago().equals(this.selectedPromesa.getMtopago()) ? true : false;
                //boolean isTrueMoneda = this.promesaList.get(index).getMoneda().equals(this.selectedPromesa.getMoneda()) ? true : false;
                boolean isTrueOperacion = true;
                if (this.promesaList.get(index).getOperacion() != null) {
                    isTrueOperacion = this.promesaList.get(index).getOperacion().equals(this.selectedPromesa.getOperacion()) ? true : false;
                }

                if (isTrueTelefono && isTrueFechaPago && isTrueMtopago /*&& isTrueMoneda*/ && isTrueOperacion) {

                    TblPromesa promesa = this.promesaList.get(index);
                    boolean hasOperation = promesa.getOperacion() != null && !promesa.getOperacion().trim().equals("") ? true : false;
                    boolean hasMtopago = promesa.getMtopago() != null && !promesa.getMtopago().equals(0) ? true : false;

                    if (hasOperation && hasMtopago) {
                        promesa.setEstado("DEL");
                        this.ejbPromesaLocal.update(promesa);
                    }

                    this.promesaList.remove(index);
                    this.selectedPromesa = null;
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Promesa Removida"));
                    PrimeFaces.current().ajax().update("formGestion:messages", "formGestion:tblPromesa_Findme");
                }
            }
        }
    }
}
