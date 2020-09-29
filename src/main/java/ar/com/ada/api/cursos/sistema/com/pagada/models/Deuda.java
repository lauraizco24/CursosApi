package ar.com.ada.api.cursos.sistema.com.pagada.models;

import java.util.Date;

public class Deuda {

    public Integer empresaId;
    public Integer deudorId;
    public Integer tipoServicioId;
    public String tipoComprobanteId;
    public String numero;
    public Date fechaEmision;
    public Date fechaVencimiento;
    public Double importe;
    public String moneda;
    public String codigoDeBarras;
    public String estadoId;


    
}
