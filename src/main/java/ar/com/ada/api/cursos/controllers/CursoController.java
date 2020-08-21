package ar.com.ada.api.cursos.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ar.com.ada.api.cursos.entities.Curso;
import ar.com.ada.api.cursos.models.request.CursoAsigDocRequest;
import ar.com.ada.api.cursos.models.request.CursoRequest;
import ar.com.ada.api.cursos.models.response.GenericResponse;
import ar.com.ada.api.cursos.services.CategoriaService;
import ar.com.ada.api.cursos.services.CursoService;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class CursoController {

  @Autowired
  CursoService cursoService;

  @Autowired
  CategoriaService catService;

  @PostMapping("/api/cursos")
  public ResponseEntity<GenericResponse> crearCurso(@RequestBody CursoRequest cursoReq) {
    GenericResponse gR = new GenericResponse();
    Curso cursoCreado = new Curso();
    cursoCreado = cursoService.crearCurso(cursoReq.nombre, cursoReq.categoriaId, cursoReq.duracionHoras,
        cursoReq.descripcion);

    if (cursoCreado == null) {
      return ResponseEntity.badRequest().build();
    } else {
      gR.isOk = true;
      gR.message = "Curso creado con éxito";
      gR.id = cursoCreado.getCursoId();
      return ResponseEntity.ok(gR);
    }

  }
// con filtro sin docentes : /api/cursos?sinDocentes=true
  @GetMapping("/api/cursos")
  public ResponseEntity<List<Curso>> listaCursos(@RequestParam(value = "sinDocentes", required = false) boolean sinDocentes) {
    List<Curso> listaCursos = new ArrayList<>();
    

    if(sinDocentes){
      //algo que nos devuelvA LA lista de cursos sin docentes.
listaCursos = cursoService.listaCursoSinDocentes();

    }else{
      listaCursos = cursoService.listaCurso();
    }

    return ResponseEntity.ok(listaCursos);

  }

  @GetMapping("/api/cursos/categorias/{id_categoria}")
  ResponseEntity<List<Curso>> buscarPorIdCategoria(@PathVariable(value = "id_categoria") Integer id) {

    List<Curso> cursos = catService.buscarPorId(id).getCursos();
    return ResponseEntity.ok(cursos);

  }

  @PostMapping("/api/cursos/{cursoId}/docentes")
  @PreAuthorize("hasAuthority('CLAIM_userType_STAFF')")
    public ResponseEntity<GenericResponse> asignarDocente(@PathVariable Integer cursoId, @RequestBody CursoAsigDocRequest cADR){
    GenericResponse gR =new GenericResponse();
    if(cursoService.asignarDocente(cursoId, cADR.docenteId)){
      gR.isOk = true;
      gR.message=" El docente fue asignado con exito";
     
      return ResponseEntity.ok(gR);
    }else{
      gR.isOk = false;
      gR.message = "El docente no pudo ser asignado ";
     return ResponseEntity.badRequest().body(gR);
    }
   

  }

 
  
}