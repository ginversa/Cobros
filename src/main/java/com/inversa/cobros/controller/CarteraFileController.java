/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;
import org.primefaces.model.file.UploadedFiles;

/**
 *
 * @author Z420WK
 */
@Named
@ViewScoped
public class CarteraFileController implements Serializable {

    // Columnas.
    private final static List<String> VALID_COLUMN_KEYS = new ArrayList<String>();//Arrays.asList("id", "brand", "year", "color", "price");

    //private String columnTemplate = "";
    private List<ColumnModel> columns;

    private List<TreeMap> cartera;

    private List<TreeMap> filteredCartera;

    //*************************************************************************
    private UploadedFile file;
    private UploadedFiles files;

    private List<String> nombreColumnas = new ArrayList<String>();
    private List cellDataList = new ArrayList();

    @PostConstruct
    public void init() {
        this.cartera = new ArrayList<>();
        this.filteredCartera = new ArrayList<>();
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public UploadedFiles getFiles() {
        return files;
    }

    public void setFiles(UploadedFiles files) {
        this.files = files;
    }

    public void upload() throws IOException {
        if (file != null) {
            /*
            FacesMessage message = new FacesMessage("Successful", file.getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
             */
            InputStream inputStream = file.getInputStream();
            //Workbook wb = WorkbookFactory.create(inputStream);

            XSSFWorkbook workBook = new XSSFWorkbook(inputStream);
            XSSFSheet hssfSheet = workBook.getSheetAt(0);
            Iterator rowIterator = hssfSheet.rowIterator();
            while (rowIterator.hasNext()) {
                XSSFRow hssfRow = (XSSFRow) rowIterator.next();
                Iterator iterator = hssfRow.cellIterator();
                List cellTempList = new ArrayList();
                while (iterator.hasNext()) {
                    XSSFCell hssfCell = (XSSFCell) iterator.next();
                    cellTempList.add(hssfCell);
                }
                cellDataList.add(cellTempList);
            }

            this.Leer(cellDataList);
        }
    }

    public void uploadMultiple() {
        if (files != null) {
            for (UploadedFile f : files.getFiles()) {
                FacesMessage message = new FacesMessage("Successful", f.getFileName() + " is uploaded.");
                FacesContext.getCurrentInstance().addMessage(null, message);
            }
        }
    }

    public void handleFileUpload(FileUploadEvent event) {
        FacesMessage msg = new FacesMessage("Successful", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    /**
     *
     * @param cellDataList
     */
    private void Leer(List cellDataList) {        
        List cellNameColumList = new ArrayList();
        for (int i = 0; i < cellDataList.size(); i++) {
            TreeMap objMap = new TreeMap();
            List cellTempList = (List) cellDataList.get(i);
            for (int j = 0; j < cellTempList.size(); j++) {                
                XSSFCell hssfCell = (XSSFCell) cellTempList.get(j);
                String stringCellValue = hssfCell.toString();
                if (i == 0) {// Nombre de las columnas
                    VALID_COLUMN_KEYS.add(stringCellValue);
                } else {                    
                    objMap.put(VALID_COLUMN_KEYS.get(j), stringCellValue);
                    this.cartera.add(objMap);
                }
            }//for
        }//for
        this.createDynamicColumns();
        for(int i=0;i<1000;i++){
            TreeMap objMap = (TreeMap) cartera.get(i);
            for(int index=0;index<VALID_COLUMN_KEYS.size();index++){
                System.out.println(VALID_COLUMN_KEYS.get(index)+" - "+objMap.get(VALID_COLUMN_KEYS.get(index)));                
            }
            System.out.println("/*************************************************************************/");
        }
    }

    /**
     *
     */
    private void createDynamicColumns() {
        columns = new ArrayList<ColumnModel>();
        for (String columnKey : VALID_COLUMN_KEYS) {
            String key = columnKey.trim();
            columns.add(new ColumnModel(key.toUpperCase(), key));
        }
    }

    public void updateColumns() {
        //reset table state
        UIComponent table = FacesContext.getCurrentInstance().getViewRoot().findComponent(":form:cars");
        table.setValueExpression("sortBy", null);

        //update columns
        createDynamicColumns();
    }

    public List<ColumnModel> getColumns() {
        return columns;
    }

    public void setColumns(List<ColumnModel> columns) {
        this.columns = columns;
    }

    public List<TreeMap> getCartera() {
        return cartera;
    }

    public void setCartera(List<TreeMap> cartera) {
        this.cartera = cartera;
    }

    public List<TreeMap> getFilteredCartera() {
        return filteredCartera;
    }

    public void setFilteredCartera(List<TreeMap> filteredCartera) {
        this.filteredCartera = filteredCartera;
    }

    static public class ColumnModel implements Serializable {

        private String header;
        private String property;

        public ColumnModel(String header, String property) {
            this.header = header;
            this.property = property;
        }

        public String getHeader() {
            return header;
        }

        public String getProperty() {
            return property;
        }
    }

}
