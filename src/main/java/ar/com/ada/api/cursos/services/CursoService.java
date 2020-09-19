package ar.com.ada.api.cursos.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ada.api.cursos.entities.Curso;
import ar.com.ada.api.cursos.entities.Docente;
import ar.com.ada.api.cursos.entities.Estudiante;
import ar.com.ada.api.cursos.repos.CursoRepository;


@Service
public class CursoService {
    @Autowired
    CursoRepository cursoRepo;
    @Autowired
    CategoriaService categoriaService;
    @Autowired
    DocenteService docenteService;

    // 21/07 21:39 el profe dijo que tenemos que cambiar algo en el resultado
    public boolean crearCurso(Curso curso) {
        if (cursoRepo.existsByNombre(curso.getNombre()))
            return false;
        else 
        grabarCurso(curso);
        return true;
    }

    public void grabarCurso(Curso curso){

        cursoRepo.save(curso);
    }

    public Curso crearCurso(String nombre, Integer categoriaId, Integer duracionHoras, String descripcion) {
        Curso curso = new Curso();
        curso.setNombre(nombre);
        curso.agregarCategoria(categoriaService.buscarPorId(categoriaId));
        curso.setDuracionHoras(duracionHoras);
        curso.setDescripcion(descripcion);
        // llamo al metodo creado en la linea 19
        boolean cursoCreado = crearCurso(curso);
        if (cursoCreado)
            return curso;
        else
            return null;
    }

    public List<Curso> listaCurso() {
        return cursoRepo.findAll();
    }

	
    public boolean asignarDocente(Integer cursoId, Integer docenteId) {
        Curso curso = new Curso();
        this.buscarPorId(cursoId);
        List<Docente> docentes = curso.getDocentes();
        for (Docente docente : docentes) {
            docente.getDocenteId().equals(docenteId);
            return false;
        }
        
        Docente docente = docenteService.buscarPorId(docenteId);
        // Relacion Bidireccional
        curso.asignarDocente(docente);
        return true;
    }

    public Curso buscarPorId(Integer id) {
        Optional<Curso> opCurso = cursoRepo.findById(id);

        // Si tiene un valor de categoria en el elemento que trajo.
        // Camion con heladera dentro. hasta que no abrimos la puerta no sabemos si la
        // trajo.
        if (opCurso.isPresent())
            return opCurso.get();
        else
            return null;

    }

	public List<Curso> listaCursoSinDocentes() {
        List<Curso> cursos = this.listaCurso();
        List<Curso> listaCursosSinDocentes = new ArrayList<> ();
        
        for (Curso curso : cursos) {
            if(curso.getDocentes().isEmpty()){
                listaCursosSinDocentes.add(curso);
            }
            
        }
        return listaCursosSinDocentes;
	}

	public List<Curso> listaCursosDisponibles(Estudiante estudiante) {
        List<Curso> listaCursosDisponibles = new ArrayList<>();
        for (Curso curso : listaCurso()) {
            List<Estudiante> estudiantes = curso.getEstudiantes();

            // buscar el nro 8, levantar la mano cuando encuentro

            // 1
            // 3
            // 5          // 8 <- levantar la mano
            // 7

            boolean anotado = false;

            for (Estudiante e : estudiantes) {
                if (estudiante.getEstudianteId().equals(e.getEstudianteId())) {
                    anotado = true;
                    break; // rompe el ciclo for actual
                }

            }
            if (!anotado) {
                listaCursosDisponibles.add(curso);
            }

        }
        return listaCursosDisponibles;

    }


}