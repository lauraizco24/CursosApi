package ar.com.ada.api.cursos.entities;

import java.util.Date;

import javax.persistence.*;

import ar.com.ada.api.cursos.entities.Pais.PaisEnum;
import ar.com.ada.api.cursos.entities.Pais.TipoDocuEnum;

@MappedSuperclass  //Anotacion de la clase padre que no tiene tabla en la base de datos
public class Persona {

    //Atributos
    private String nombre;
    @Column(name = "pais_id")
    private Integer paisId;
    @Column(name = "tipo_doc_id")
    private Integer tipoDocumentoId;
    private String documento;
    @Column(name = "fecha_nacimiento")
    private Date fechaNacimiento;



    //Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

   
    public TipoDocuEnum getTipoDocumentoId() {
        return TipoDocuEnum.parse(this.tipoDocumentoId);
    }

    public void setTipoDocumentoId(TipoDocuEnum tipoDocumentoId) {
        this.tipoDocumentoId = tipoDocumentoId.getValue();
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Integer getPaisId() {
        return paisId;
    }

    public void setPaisId(Integer paisId) {
        this.paisId = paisId;
    }

    public void setTipoDocumentoId(Integer tipoDocumentoId) {
        this.tipoDocumentoId = tipoDocumentoId;
    }

    
}