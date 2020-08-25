package ar.com.ada.api.cursos.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ar.com.ada.api.cursos.entities.Curso;
import ar.com.ada.api.cursos.entities.Docente;
import ar.com.ada.api.cursos.entities.Estudiante;
import ar.com.ada.api.cursos.entities.Inscripcion;
import ar.com.ada.api.cursos.models.request.InscripcionRequest;
import ar.com.ada.api.cursos.models.response.CursoEstudianteResponse;
import ar.com.ada.api.cursos.models.response.DocenteSimplificadoResponse;
import ar.com.ada.api.cursos.models.response.GenericResponse;
import ar.com.ada.api.cursos.services.CursoService;
import ar.com.ada.api.cursos.services.EstudianteService;

@RestController
public class EstudianteController {
   
    @Autowired
    EstudianteService estudianteService;
    @Autowired
    CursoService cursoService;

    @PostMapping("/api/estudiantes")
    public ResponseEntity<GenericResponse> crearEstudiante(@RequestBody Estudiante estudiante) {

        GenericResponse r = new GenericResponse();
        Estudiante estudianteCreado = new Estudiante();
        
        estudianteCreado = estudianteService.crearEstudiante(estudiante.getNombre(), estudiante.getPaisId(), estudiante.getTipoDocumentoId(), estudiante.getDocumento(), estudiante.getFechaNacimiento());
        if (estudianteCreado == null) {
            return ResponseEntity.badRequest().build();
          } else {

        r.isOk = true;
        r.message = "Estudiante creada con exito";
        r.id = estudiante.getEstudianteId();

        return ResponseEntity.ok(r);
          }
    }
//Metodo de autorizacion 4: en este caso, accedo a la variable del parametro.

    @GetMapping("/api/estudiantes/{id}")
    @PreAuthorize("hasAuthority('CLAIM_userType_STAFF') or (hasAuthority('CLAIM_userType_ESTUDIANTE') and hasAuthority('CLAIM_entityId_'+#id''))")
    ResponseEntity<Estudiante> buscarPorIdEstudiante(@PathVariable Integer id) {
        Estudiante estudiante = estudianteService.buscarPorId(id);
        if (estudiante == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(estudiante);
    }

    @GetMapping("/api/estudiantes/{id}/cursos")
    @PreAuthorize("hasAuthority('CLAIM_userType_STAFF')")
    public ResponseEntity<List<CursoEstudianteResponse>> listaCursos(@PathVariable Integer id,
            @RequestParam(value = "disponibles", required = false) boolean disponibles) {
        List<Curso> listaCursos = new ArrayList<>();
        Estudiante estudiante = estudianteService.buscarPorId(id);
        if (disponibles) {
            // listaCursos = algo que nos devuelva la llista de cursos disponibles.
            listaCursos = cursoService.listaCursosDisponibles(estudiante);
        } else {
            listaCursos = estudiante.getCursosQueAsiste();
        }

        List<CursoEstudianteResponse> listaSimplificada = new ArrayList<>();

        // Transformar, cada item de la lista de cursos, a un CursoEstudianteResponse
        for (Curso curso : listaCursos) {
            CursoEstudianteResponse nuevoCurso = new CursoEstudianteResponse();
            nuevoCurso.nombre = curso.getNombre();
            nuevoCurso.cursoId = curso.getCursoId();
            nuevoCurso.descripcion = curso.getDescripcion();
            nuevoCurso.categorias = curso.getCategorias();
            nuevoCurso.duracionHoras = curso.getDuracionHoras();

            for (Docente docente : curso.getDocentes()) {
                DocenteSimplificadoResponse dr = new DocenteSimplificadoResponse();
                dr.docenteId = docente.getDocenteId();
                dr.nombre = docente.getNombre();
                nuevoCurso.docentes.add(dr);
            }

            listaSimplificada.add(nuevoCurso);
        }
        return ResponseEntity.ok(listaSimplificada);
    }


    // - Estudiante -> Inscribirse a un curso(por defecto haremos que la inscripcion
    // se apruebe de una)!

    @PostMapping("/api/estudiantes/{id}/inscripciones")
    public ResponseEntity<GenericResponse> inscribir(@PathVariable Integer id, @RequestBody InscripcionRequest iR) {

        Inscripcion inscripcionCreada = estudianteService.inscribir(id, iR.cursoId);
        GenericResponse gR = new GenericResponse();
        if (inscripcionCreada == null) {
            gR.isOk = false;
            gR.message = "La inscripcion no pudo ser realizada";
            return ResponseEntity.badRequest().body(gR);
        } else {
            gR.isOk = true;
            gR.message = "La inscripcion se realizo con exito";
            gR.id = inscripcionCreada.getInscripcionId();
            return ResponseEntity.ok(gR);
        }

    }


}