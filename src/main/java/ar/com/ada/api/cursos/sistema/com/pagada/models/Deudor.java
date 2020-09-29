package ar.com.ada.api.cursos.sistema.com.pagada.models;

import java.util.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import ar.com.ada.api.cursos.entities.Estudiante;

@Entity
@Table(name = "deudor")
public class Deudor {
    /*
     * { "paisId":32, "tipoIdImpositivo": "DNI", "idImpositivo":20000001331,
     * "nombre": "Karen" }
     */
    @Id
    @Column(name = "deudor_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;
    @Column(name = "pais_id")
    public Integer paisId;
    @Column(name = "tipo_id_impositivo")
    public String tipoIdImpositivo;
    @Column(name = "id_impositivo")
    public String idImpositivo;
    @Column(name = "nombre")
    public String nombre;

    @ManyToMany(mappedBy = "deudor")
    public List<Estudiante> estudiantes = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPaisId() {
        return paisId;
    }

    public void setPaisId(Integer paisId) {
        this.paisId = paisId;
    }

    public String getTipoIdImpositivo() {
        return tipoIdImpositivo;
    }

    public void setTipoIdImpositivo(String tipoIdImpositivo) {
        this.tipoIdImpositivo = tipoIdImpositivo;
    }

    public String getIdImpositivo() {
        return idImpositivo;
    }

    public void setIdImpositivo(String idImpositivo) {
        this.idImpositivo = idImpositivo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Estudiante> getEstudiantes() {
        return estudiantes;
    }

    public void setEstudiantes(List<Estudiante> estudiantes) {
        this.estudiantes = estudiantes;
    }

}
