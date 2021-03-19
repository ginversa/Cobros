/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.controller;

import com.inversa.cobros.ejb.ClienteCarteraService;
import com.inversa.cobros.model.TblCentral;
import com.inversa.cobros.model.TblCliente;
import com.inversa.cobros.model.TblClienteCartera;
import com.inversa.cobros.model.TblLlamada;
import com.inversa.cobros.model.TblPrefijoSalida;
import com.inversa.cobros.model.TblUrlLlamada;
import com.inversa.cobros.model.TblUsuario;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import org.apache.commons.io.FileUtils;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Z420WK
 */
@Named
@ViewScoped
public class LlamarController implements Serializable {

    @Inject
    private ClienteCarteraService ejbClienteCarteraService;

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

    private TblUsuario usuario;

    private StreamedContent file;

    @PostConstruct
    public void init() {
        this.usuario = (TblUsuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        String codigoCartera = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("codigo_cartera");
        TblClienteCartera cartera = new TblClienteCartera();
        cartera.setCodigoCartera(codigoCartera);
        List<TblClienteCartera> carteraList = this.ejbClienteCarteraService.findByCodigoCartera(cartera);
        TblCliente cliente = carteraList.get(0).getIdCliente();
        this.prefijoSalidaList = cliente.getTblPrefijoSalidaList();
    }

    /**
     *
     * @param telefono
     */
    public void hacerLlamada(String telefono) {
        String URL_LLAMAR = this.crearUrlHacerLlamada(telefono);
        cliente = ClientBuilder.newClient();
        //Leer una llamada (metodo get)
        webTarget = cliente.target(URL_LLAMAR);
        // get extracted document as JSON
        String jsonExtract = webTarget.request(MediaType.APPLICATION_JSON).get(String.class);
        System.out.println("Generar una llamada: " + jsonExtract);
    }

    /**
     *
     * @param telefono
     * @return
     */
    private String crearUrlHacerLlamada(String telefono) {
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

    /**
     * Escuchar una llamada Genera una llamada para poder escuchar en la
     * extensión XXX la llamada con id YYY
     *
     * @param llamada
     */
    public void escucharLlamada(TblLlamada llamada) throws MalformedURLException {
        String callLogId = llamada.getCallLogId();
        String URL_LLAMAR = this.crearUrlEscucharLlamada(callLogId);
        cliente = ClientBuilder.newClient();
        //Leer una llamada (metodo get)
        webTarget = cliente.target(URL_LLAMAR);
        // get extracted document as JSON
        String jsonExtract = webTarget.request(MediaType.APPLICATION_JSON).get(String.class);
        System.out.println("Generar una llamada: " + jsonExtract);
    }

    /**
     * @param telefono
     * @return
     */
    private String crearUrlEscucharLlamada(String callLogId) {

        TblCentral central = this.prefijoSalidaList.get(0).getTblCentral();
        this.http = central.getProtocolo();
        this.ip_publica = "192.168.7.201";//central.getIpCentral();
        this.directorioCentral = central.getDirectorio();
        //TblUrlLlamada urlLlamar = central.getTblUrlLlamadaList().get(2);// llamar. Buscar servicio para llamar.
        this.servicio = "llamar.php?ext=";//urlLlamar.getServicio();
        this.ext = "118";//this.usuario.getExtEnsion();
        this.parametro = "&escuchar=";//urlLlamar.getParametro();        
        String URL_LLAMAR = this.http + this.ip_publica + this.directorioCentral + this.servicio + this.ext + this.parametro + callLogId;
        System.out.println("URL_LLAMAR: " + URL_LLAMAR);
        return URL_LLAMAR;
    }

    /**
     *
     * @param llamada
     */
    public void bajarLlamada(TblLlamada llamada) throws MalformedURLException, IOException {
        String callLogId = llamada.getCallLogId();
        String URL_LLAMAR = this.crearUrlBajarLlamada(callLogId);

        try {
            URL url = new URL(URL_LLAMAR);
            URLConnection urlCon = url.openConnection();
            String contentType = urlCon.getContentType();
            System.out.println("contentType: " + contentType);
            String[] arrayString = contentType.split("/");
            System.out.println(arrayString[0]);
            System.out.println(arrayString[1]);

            // Se obtiene el inputStream de la foto web y se abre el fichero
            // local.
            InputStream is = urlCon.getInputStream();
            FileOutputStream fos = new FileOutputStream("01 - bajarLlamada.mp3");

            // Lectura de la foto de la web y escritura en fichero local
            byte[] array = new byte[1000]; // buffer temporal de lectura.
            int leido = is.read(array);
            while (leido > 0) {
                fos.write(array, 0, leido);
                leido = is.read(array);
            }

            // cierre de conexion y fichero.
            is.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param llamada
     */
    public void bajarUsingJavaIO(TblLlamada llamada) {
        try {

            String file = "02 - bajarUsingJavaIO.mp3";
            String callLogId = llamada.getCallLogId();
            String urlStr = this.crearUrlBajarLlamada(callLogId);

            URL url = new URL(urlStr);
            BufferedInputStream bis = new BufferedInputStream(url.openStream());
            FileOutputStream fis = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int count = 0;
            while ((count = bis.read(buffer, 0, 1024)) != -1) {
                fis.write(buffer, 0, count);
            }
            fis.close();
            bis.close();

        } catch (MalformedURLException ex) {
            Logger.getLogger(LlamarController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LlamarController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param llamada
     */
    public void bajarUsingNIO(TblLlamada llamada) {
        String FILE_NAME = "03 - bajarUsingNIO.mp3";
        String callLogId = llamada.getCallLogId();
        String FILE_URL = this.crearUrlBajarLlamada(callLogId);

        try {
            URL url = new URL(FILE_URL);
            ReadableByteChannel readableByteChannel = Channels.newChannel(url.openStream());
            FileOutputStream fileOutputStream = new FileOutputStream(FILE_NAME);
            fileOutputStream.getChannel().transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
            fileOutputStream.close();
            readableByteChannel.close();

        } catch (Exception ex) {
            Logger.getLogger(LlamarController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param llamada
     */
    public void bajarFileChannelTransferFrom(TblLlamada llamada) {
        try {
            String callLogId = llamada.getCallLogId();
            String URL_LLAMAR = this.crearUrlBajarLlamada(callLogId);
            String FILE_NAME = "04 - bajarFileChannelTransferFrom.mp3";

            URL url = new URL(URL_LLAMAR);
            downloadFile1(url, FILE_NAME);
        } catch (MalformedURLException ex) {
            Logger.getLogger(LlamarController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LlamarController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param url
     * @param outputFileName
     * @throws IOException
     */
    public static void downloadFile1(URL url, String outputFileName) throws IOException {
        try (InputStream in = url.openStream();
                ReadableByteChannel rbc = Channels.newChannel(in);
                FileOutputStream fos = new FileOutputStream(outputFileName)) {
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        }
    }

    public void bajarFilesCopy(TblLlamada llamada) {
        try {
            String callLogId = llamada.getCallLogId();
            String URL_LLAMAR = this.crearUrlBajarLlamada(callLogId);
            String FILE_NAME = "05 - bajarFilesCopy.mp3";

            URL url = new URL(URL_LLAMAR);
            downloadFile2(url, FILE_NAME);
        } catch (MalformedURLException ex) {
            Logger.getLogger(LlamarController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LlamarController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(LlamarController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param url
     * @param fileName
     * @throws Exception
     */
    public static void downloadFile2(URL url, String fileName) throws Exception {
        try (InputStream in = url.openStream()) {
            Files.copy(in, Paths.get(fileName));
        }
    }

    /**
     *
     * @param llamada
     */
    public void bajarPlainJava(TblLlamada llamada) {
        try {
            String callLogId = llamada.getCallLogId();
            String URL_LLAMAR = this.crearUrlBajarLlamada(callLogId);
            String FILE_NAME = "06 - bajarPlainJava.mp3";

            URL url = new URL(URL_LLAMAR);
            downloadFile3(url, FILE_NAME);
        } catch (MalformedURLException ex) {
            Logger.getLogger(LlamarController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LlamarController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(LlamarController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param url
     * @param fileName
     * @throws IOException
     */
    public static void downloadFile3(URL url, String fileName) throws IOException {
        try (InputStream in = url.openStream();
                BufferedInputStream bis = new BufferedInputStream(in);
                FileOutputStream fos = new FileOutputStream(fileName)) {

            byte data[] = new byte[1024];
            int count;
            while ((count = bis.read(data, 0, 1024)) != -1) {
                fos.write(data, 0, count);
            }
        }
    }

    /**
     *
     * @param llamada
     */
    public void bajarApacheCommonsIO(TblLlamada llamada) {
        try {
            String callLogId = llamada.getCallLogId();
            String URL_LLAMAR = this.crearUrlBajarLlamada(callLogId);
            String FILE_NAME = "07 - bajarApacheCommonsIO.mp3";

            URL url = new URL(URL_LLAMAR);
            downloadFile4(url, FILE_NAME);
        } catch (MalformedURLException ex) {
            Logger.getLogger(LlamarController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LlamarController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(LlamarController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param url
     * @param fileName
     * @throws IOException
     */
    public static void downloadFile4(URL url, String fileName) throws IOException {
        FileUtils.copyURLToFile(url, new File(fileName));
    }

    /**
     * http://ip_servidor/PBXPortal/consultar.php?call_log_id=XXXX&bajar=1
     * consultar.php?call_log_id=
     *
     * @param callLogId
     * @return
     */
    private String crearUrlBajarLlamada(String callLogId) {

        TblCentral central = this.prefijoSalidaList.get(0).getTblCentral();
        this.http = central.getProtocolo();
        this.ip_publica = "192.168.7.201";//central.getIpCentral();
        this.directorioCentral = central.getDirectorio();
        //TblUrlLlamada urlLlamar = central.getTblUrlLlamadaList().get(2);// llamar. Buscar servicio para llamar.
        this.servicio = "consultar.php?call_log_id=";//urlLlamar.getServicio();
        this.parametro = "&bajar=1";//urlLlamar.getParametro();        
        String URL_LLAMAR = this.http + this.ip_publica + this.directorioCentral + this.servicio + callLogId + this.parametro;
        System.out.println("URL_LLAMAR: " + URL_LLAMAR);
        return URL_LLAMAR;
    }

    /**
     *
     * @param llamada
     */
    public void bajarFileUpload(TblLlamada llamada) {
        String callLogId = llamada.getCallLogId();
        String URL_LLAMAR = this.crearUrlBajarLlamada(callLogId);
        String FILE_NAME = "07 - bajarApacheCommonsIO.mp3";

        try {
            URL url = new URL(URL_LLAMAR);

        } catch (MalformedURLException ex) {
            Logger.getLogger(LlamarController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param llamada
     * @return
     */
    public StreamedContent getFileDownloadView(TblLlamada llamada) {
        String callLogId = llamada.getCallLogId();
        String URL_LLAMAR = this.crearUrlBajarLlamada(callLogId);

        Date fechaDate = llamada.getFechaingreso();
        Calendar cal = Calendar.getInstance();
        cal.setTime(fechaDate);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        int second = cal.get(Calendar.SECOND);
        int millis = cal.get(Calendar.MILLISECOND);

        String fecha = year + "-" + month + "-" + day + "_" + hour + ":" + minute + ":" + second;

        String codigoCartera = llamada.getIdGestion().getCodigoCartera();
        String codigoGestor = llamada.getIdGestion().getCodigoGestor();
        String telefono = llamada.getCallToNumber();

        String nameFile = fecha + "_" + codigoCartera + "_" + codigoGestor + "_" + telefono + "_" + ".mp3";
        System.out.println("Nombre archivo: " + nameFile);
        try {

            URL url = new URL(URL_LLAMAR);
            URLConnection urlCon = url.openConnection();
            String contentType = urlCon.getContentType();
            System.out.println("contentType: " + contentType);

            InputStream stream = urlCon.getInputStream();

            file = DefaultStreamedContent.builder()
                    .name(nameFile)
                    .contentType(contentType)
                    .stream(() -> stream)
                    .build();

        } catch (MalformedURLException ex) {
            Logger.getLogger(LlamarController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LlamarController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return file;
    }
}

/*
<p:column headerText="Bajar">
                                <p:commandLink actionListener="#{llamarController.bajarLlamada(llamada)}" >
                                    <i class="pi pi-download"></i>
                                </p:commandLink>
                            </p:column>
                            <p:column headerText="bajarUsingJavaIO">
                                <p:commandLink actionListener="#{llamarController.bajarUsingJavaIO(llamada)}" >
                                    <i class="pi pi-download"></i>
                                </p:commandLink>
                            </p:column>
                            <p:column headerText="bajarUsingNIO">
                                <p:commandLink actionListener="#{llamarController.bajarUsingNIO(llamada)}" >
                                    <i class="pi pi-download"></i>
                                </p:commandLink>
                            </p:column>
                            <p:column headerText="bajarFileChannelTransferFrom">
                                <p:commandLink actionListener="#{llamarController.bajarFileChannelTransferFrom(llamada)}" >
                                    <i class="pi pi-download"></i>
                                </p:commandLink>
                            </p:column>
                            <p:column headerText="bajarFilesCopy">
                                <p:commandLink actionListener="#{llamarController.bajarFilesCopy(llamada)}" >
                                    <i class="pi pi-download"></i>
                                </p:commandLink>
                            </p:column>
                            <p:column headerText="bajarPlainJava">
                                <p:commandLink actionListener="#{llamarController.bajarPlainJava(llamada)}" >
                                    <i class="pi pi-download"></i>
                                </p:commandLink>
                            </p:column>
                            <p:column headerText="bajarApacheCommonsIO">
                                <p:commandLink actionListener="#{llamarController.bajarApacheCommonsIO(llamada)}" >
                                    <i class="pi pi-download"></i>
                                </p:commandLink>
                            </p:column>
 */
