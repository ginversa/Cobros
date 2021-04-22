/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inversa.cobros.constante.comun;

/**
 *
 * @author Z420WK
 */
public class ConstanteComun {
    
    public static final String USUARIO = "usuario";
    public static final String COD_CARTERA = "codigo_cartera";    
    public static final String ROL_USUARIO = "rol_usuario";    

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
    public static final String Pago_por_Verificar = "PVF"; // PVF - Pago por Verificar
    
    // Tipos de Arreglos de Pago
    public static final String Cancelacion_Total = "CAT"; // CAT - Cancelación Total
    public static final String Cancelacion_total_por_cuotas = "CTC"; // CTC - Cancelación total por cuotas
    public static final String Refinanciamiento = "REF"; // REF - Refinanciamiento
    public static final String Pago_Parcial = "PAP"; // PAP - Pago Parcial
    
    // Tipo de Descuento
    public static final String Monto_Fijo = "FIJ";  // FIJ - Monto Fijo
    public static final String Porcentaje = "POR";  // POR - Porcentaje
    
    public static final String colones = "CRC";
    public static final String dolares = "USD";
    
    // Rol Usuario, codigo.    
    public static final String COD_GESTOR = "gescar";
    public static final String COD_SUPERVISOR = "supcar";
    
    public static final String REDIRECT = "../../faces/login.xhtml";
                               

}
