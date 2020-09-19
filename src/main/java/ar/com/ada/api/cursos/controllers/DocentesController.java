package ar.com.ada.api.cursos.controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ar.com.ada.api.cursos.entities.Docente;
import ar.com.ada.api.cursos.models.response.GenericResponse;

import ar.com.ada.api.cursos.services.CursoService;
import ar.com.ada.api.cursos.services.DocenteService;

@RestController
public class DocentesController {
    

    @Autowired
    DocenteService docenteService;
    CursoService cursoService;

    @PostMapping("/api/docentes")
    public ResponseEntity<GenericResponse> crearDocente(@RequestBody Docente docente) {

        GenericResponse r = new GenericResponse();
        Docente docenteCreado = new Docente();
        docenteCreado = docenteService.crearDocente(docente.getNombre(), docente.getPaisId(), docente.getTipoDocumentoId(), docente.getDocumento(), docente.getFechaNacimiento());
        if (docenteCreado == null) {
            return ResponseEntity.badRequest().build();
          } else {

        r.isOk = true;
        r.message = "Docente creada con exito";
        r.id = docente.getDocenteId();

        return ResponseEntity.ok(r);
          }
    }

    @GetMapping("/api/docentes/{id}")
    ResponseEntity<Docente> buscarPorIdDocente(@PathVariable Integer id) {
        Docente docente = docenteService.buscarPorId(id);
        if (docente == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(docente);
    }

    // En este caso se llama al metodo buscarPorId en el DocenteService 2 veces
    // @GetMapping("/api/docentes/{id}")
    // ResponseEntity<Docente> buscarPorIdDocente(@PathVariable Integer id) {
    // if (docenteService.buscarPorId(id) == null)
    // return ResponseEntity.notFound().build();
    // return ResponseEntity.ok(docenteService.buscarPorId(id));
    // }

    @GetMapping("/api/docentes")
    ResponseEntity<List<Docente>> listarDocentes() {
        List<Docente> listaDocentes = docenteService.obtenerDocentes();
        return ResponseEntity.ok(listaDocentes);
    }


    // @GetMapping("/api/cursos/docentes/{id_docente}")
    // ResponseEntity<List<Curso>> cursosPorIdDocente(@PathVariable (value = "id_docente") Integer id) {
    
    //       List<Curso> cursos = docenteService.buscarPorId(id).getCursosQueDicta();
    //     return ResponseEntity.ok(cursos);
      
    // }
}