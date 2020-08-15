package ar.com.ada.api.cursos.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name = "cursos")
public class Curso {

    @Id
    @Column(name = "curso_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cursoId;
    private String nombre;
    private String descripcion;
    // diferenciar a los cursos de docete y de estudiante
    @ManyToMany(mappedBy = "cursosQueDicta")
    private List<Docente> docentes = new ArrayList<>();
    @ManyToMany(mappedBy = "cursosQueAsiste")
    private List<Estudiante> estudiantes = new ArrayList<>();
    @OneToMany(mappedBy = "curso", cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Clase> clases = new ArrayList<>();
    @ManyToMany(mappedBy = "cursos")
    private List<Categoria> categorias = new ArrayList<>();
    @OneToMany(mappedBy = "curso", cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Inscripcion> inscripciones = new ArrayList<>();
    @Column(name="duracion_horas")
    private Integer duracionHoras;

    // Getters y Setters

    public Integer getCursoId() {
        return cursoId;
    }

    public void setCursoId(Integer cursoId) {
        this.cursoId = cursoId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Docente> getDocentes() {
        return docentes;
    }

    public void setDocentes(List<Docente> docentes) {
        this.docentes = docentes;
    }

    public List<Estudiante> getEstudiantes() {
        return estudiantes;
    }

    public void setEstudiantes(List<Estudiante> estudiantes) {
        this.estudiantes = estudiantes;
    }

    public List<Clase> getClases() {
        return clases;
    }

    public void setClases(List<Clase> clases) {
        this.clases = clases;
    }

    public List<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }

    public List<Inscripcion> getInscripciones() {
        return inscripciones;
    }

    public void setInscripciones(List<Inscripcion> inscripciones) {
        this.inscripciones = inscripciones;
    }

    public void asignarDocente(Docente docente) {
        this.docentes.add(docente);
        docente.getCursosQueDicta().add(this);

    }

    public void asignarEstudiante(Estudiante estudiante) {
        this.estudiantes.add(estudiante);
        estudiante.getCursosQueAsiste().add(this);
    }

    public void agregarClase(Clase clase){
        this.clases.add(clase);

        clase.setCurso(this);

    }

    public void agregarCategoria(Categoria categoria){
        this.categorias.add(categoria);
        categoria.getCursos().add(this);
    }

    public void agregarInscripcion(Inscripcion inscripcion){
        this.inscripciones.add(inscripcion);
        inscripcion.setCurso(this);
    }

    public Integer getDuracionHoras() {
        return duracionHoras;
    }

    public void setDuracionHoras(Integer duracionHoras) {
        this.duracionHoras = duracionHoras;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}