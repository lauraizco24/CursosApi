package ar.com.ada.api.cursos.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.*;

import ar.com.ada.api.cursos.entities.Categoria;
import ar.com.ada.api.cursos.services.CategoriaService;
import ar.com.ada.api.cursos.models.request.modifCategoriaRequest;
import ar.com.ada.api.cursos.models.response.CategoriaResponse;
import ar.com.ada.api.cursos.models.response.GenericResponse;

@RestController
public class CategoriaController {
    
    @Autowired
    CategoriaService categoriaService;

    @PostMapping("/categorias")
    ResponseEntity<GenericResponse> crearCategoria(@RequestBody Categoria categoria){
        categoriaService.crearCategoria(categoria);

        GenericResponse r = new GenericResponse();
        r.isOk = true;
        r.message = "Categoria creada con exito";
        r.id = categoria.getCategoriaId();
// Aca vamos a usar Ok
        // Cuando se crea, generalmetnte para los puristas, se usa el
        // CreatedAtAction(201)
        return ResponseEntity.ok(r);

    }

    @PutMapping("api/categorias/{id}")
    ResponseEntity<GenericResponse> modificarCategoria(@PathVariable Integer id, @RequestBody modifCategoriaRequest modifReq){

        Categoria categoria = categoriaService.buscarPorId(id);

        if(categoria == null){
            return ResponseEntity.notFound().build();

        }else {
            categoria.setNombre(modifReq.nombre);
            categoria.setDescripcion(modifReq.descripcion);
            Categoria categoriaModificada = categoriaService.modificarCategoria(categoria);

           GenericResponse r = new GenericResponse();
           r.isOk = true;
           r.message= "Categoria Modificada exitosamente";
           r.id = categoriaModificada.getCategoriaId();
           
           return ResponseEntity.ok(r);
        }
    

    }

    @GetMapping("/api/categorias")
    public ResponseEntity<List<CategoriaResponse>> listarCategorias() {
        List<Categoria> listaDeCategorias = categoriaService.listarTodas();
        List<CategoriaResponse> listaCategoriasResponse = new ArrayList<CategoriaResponse>();
        for (Categoria c : listaDeCategorias) {
            CategoriaResponse catResponse = new CategoriaResponse();
            catResponse.nombre = c.getNombre();
            catResponse.descripcion = c.getDescripcion();
            listaCategoriasResponse.add(catResponse);
        }
        return ResponseEntity.ok(listaCategoriasResponse);
    }

    @GetMapping("/api/categorias/{id}")
    ResponseEntity<CategoriaResponse> buscarPorIdCategoria(@PathVariable Integer id) {
        Categoria categoria = categoriaService.buscarPorId(id);

        CategoriaResponse catResponse = new CategoriaResponse();
        catResponse.nombre = categoria.getNombre();
        catResponse.descripcion = categoria.getDescripcion();

        return ResponseEntity.ok(catResponse);
    }





}