/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.llamar;

import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Z420WK
 */

@XmlRootElement
public class Llamada {

    private String callLogId;

    private Date dateIni;

    private Date dateEnd;

    private String callFromNumber;

    private String callToNumber;

    private String dialstatus;

    private Integer conversationLength;
    

    public String getCallLogId() {
        return callLogId;
    }

    public void setCallLogId(String callLogId) {
        this.callLogId = callLogId;
    }

    public Date getDateIni() {
        return dateIni;
    }

    public void setDateIni(Date dateIni) {
        this.dateIni = dateIni;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getCallFromNumber() {
        return callFromNumber;
    }

    public void setCallFromNumber(String callFromNumber) {
        this.callFromNumber = callFromNumber;
    }

    public String getCallToNumber() {
        return callToNumber;
    }

    public void setCallToNumber(String callToNumber) {
        this.callToNumber = callToNumber;
    }

    public String getDialstatus() {
        return dialstatus;
    }

    public void setDialstatus(String dialstatus) {
        this.dialstatus = dialstatus;
    }

    public Integer getConversationLength() {
        return conversationLength;
    }

    public void setConversationLength(Integer conversationLength) {
        this.conversationLength = conversationLength;
    }
    
}
