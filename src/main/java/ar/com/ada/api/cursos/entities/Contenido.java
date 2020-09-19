package ar.com.ada.api.cursos.entities;

import javax.persistence.*;

@Entity
@Table(name= "contenido")
public class Contenido {
    @Id
    @Column(name = "contenido_id")
   private Integer contenidoId;
   private String descripcion;
   @Column(name="descripcion_larga")
   private String descripcionLarga;
   private String payload;
   private String payloadSimple;
   @ManyToOne
   @JoinColumn(name = "clase_id", referencedColumnName = "clase_id")
   private Clase clase;

//------------------Empieza enum----------------------------
   public enum TipoContenidoEnum{
    URL(1),
    TEXO(2),
    Video(3);

    private final int value;

    // NOTE: Enum constructor tiene que estar en privado
    private TipoContenidoEnum(int value) {
        this.value = value;

}
public int getValue() {
    return value;
}

public static TipoContenidoEnum parse(int id) {
    TipoContenidoEnum status = null; // Default
    for (TipoContenidoEnum item : TipoContenidoEnum.values()) {
        if (item.getValue() == id) {
            status = item;
            break;
        }
    }
    return status;
}
}
//------------------Termina enum----------------------------   



public Integer getContenidoId() {
    return contenidoId;
}

public void setContenidoId(Integer contenidoId) {
    this.contenidoId = contenidoId;
}

public String getDescripcion() {
    return descripcion;
}

public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
}

public String getDescripcionLarga() {
    return descripcionLarga;
}

public void setDescripcionLarga(String descripcionLarga) {
    this.descripcionLarga = descripcionLarga;
}

public String getPayload() {
    return payload;
}

public void setPayload(String payload) {
    this.payload = payload;
}

public String getPayloadSimple() {
    return payloadSimple;
}

public void setPayloadSimple(String payloadSimple) {
    this.payloadSimple = payloadSimple;
}

public Clase getClase() {
    return clase;
}

public void setClase(Clase clase) {
    this.clase = clase;
};
 //------------------Termina enum----------------------------   


 
}