package ar.com.ada.api.cursos.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ar.com.ada.api.cursos.sistema.com.pagada.models.Deudor;

@Entity
@Table(name = "estudiante")
public class Estudiante extends Persona {

    @Id
    @Column(name = "estudiante_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer estudianteId;
    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "estudiante_x_curso", joinColumns = @JoinColumn(name = "estudiante_id"), inverseJoinColumns = @JoinColumn(name = "curso_id"))
    private List<Curso> cursosQueAsiste = new ArrayList<>();
    @JsonIgnore
    @OneToOne(mappedBy = "estudiante", cascade = CascadeType.ALL)
    private Usuario usuario;
    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "deudor_x_estudiante", joinColumns = @JoinColumn(name = "estudiante_id"), inverseJoinColumns = @JoinColumn(name = "deudor_id"))
    private List<Deudor> deudor;

    // Getters y Setters

    public Integer getEstudianteId() {
        return estudianteId;
    }

    public void setEstudianteId(Integer estudianteId) {
        this.estudianteId = estudianteId;
    }

    public List<Curso> getCursosQueAsiste() {
        return cursosQueAsiste;
    }

    public void setCursosQueAsiste(List<Curso> cursosQueAsiste) {
        this.cursosQueAsiste = cursosQueAsiste;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        usuario.setEstudiante(this);
    }

    public List<Deudor> getDeudor() {
        return deudor;
    }

    public void setDeudor(List<Deudor> deudor) {
        this.deudor = deudor;
        usuario.setEstudiante(this);
    }

  

}