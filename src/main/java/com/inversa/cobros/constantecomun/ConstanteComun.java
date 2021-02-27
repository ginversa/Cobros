/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.constantecomun;

/**
 *
 * @author Z420WK
 */
public class ConstanteComun {

    public static final String Credomatic = "00017";
    public static final String Davivienda = "00005";

// Estado
    public static final String Ingresar = "ING";
    public static final String Activo = "ACT";
    public static final String Inactivo = "INA";
    public static final String Datos = "DAT";

//Estados de una promesa.
    public static final String Seguimiento = "SEG";// - , antes de recordatorio
    public static final String Promesa = "PRO";// - , si cuando lo llame recordando, dice que si paga maÃƒÂ±ana.
    public static final String Incumplida = "INC";// - , al resivir los pago, se comprueba que no pago.
    public static final String Efectiva = "EFE";// - , al resivir los pago, se comprueba que si pago.
    public static final String Registro_Borrado = "DEL";// - Registro borrado
    public static final String Reprogramada = "REP";// - 
    public static final String Historico = "HIS"; // - Histórico

}
