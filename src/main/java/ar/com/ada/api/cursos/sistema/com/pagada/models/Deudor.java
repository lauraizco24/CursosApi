package ar.com.ada.api.cursos.sistema.com.pagada.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import ar.com.ada.api.cursos.entities.Estudiante;

@Entity
@Table(name="deudor")
public class Deudor {
     /*{
        "paisId":32,
        "tipoIdImpositivo": "DNI",
        "idImpositivo":20000001331,
        "nombre": "Karen"
    }*/
    @Id
    @Column(name="deudor_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;
    @Column(name="pais_id")
    public Integer paisId;
    @Column(name="tipo_id_impositivo")
    public String tipoIdImpositivo;
    @Column(name="id_impositivo")
    public String idImpositivo;
    @Column(name="nombre")
    public String nombre;
    
    @ManyToMany(mappedBy = "deudor")
    public Estudiante estudiante;

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    
}
