package ar.com.ada.api.cursos.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import java.security.Principal;
import java.util.*;

import ar.com.ada.api.cursos.entities.Categoria;
import ar.com.ada.api.cursos.entities.Usuario;
import ar.com.ada.api.cursos.entities.Usuario.TipoUsuarioEnum;
import ar.com.ada.api.cursos.services.CategoriaService;
import ar.com.ada.api.cursos.services.UsuarioService;
import ar.com.ada.api.cursos.models.request.modifCategoriaRequest;
import ar.com.ada.api.cursos.models.response.CategoriaResponse;
import ar.com.ada.api.cursos.models.response.GenericResponse;

@RestController
public class CategoriaController {

    @Autowired
    CategoriaService categoriaService;

    @Autowired
    UsuarioService usuarioService;

    
    // Autorizacion Forma 1.
    // Se agrega el parametro Principal, que es una abstraccion que permite acceder
    // al usuario que esta logueado.
    @PostMapping("/api/categorias")
    ResponseEntity<GenericResponse> crearCategoria(Principal principal, @RequestBody Categoria categoria) {

        Usuario usuario = usuarioService.buscarPorUsername(principal.getName());
        if (usuario.getTipoUsuarioId() != TipoUsuarioEnum.STAFF) {

            // Si es distinto no puede crear una categoria y le damos 403:Forbidden
            // Este le avisamos que hay algo pero no lo dejamos entrar.
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

            // en vez de tirar un 403 tiramos un 404 y le mentimos: por seguridad
            // en este caso ni siquiera le contamos que hay algo ahi ara que siga
            // intentando.
            // return ResponseEntity.notFound().build();
        }
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

    // Autorizacion Forma 2:
    // haciendo lo mismo que antes pero usando Spring Expression LANGUAGE(magic)
    // Aca el principal es el User, este principal no es el mismo principal del
    // metodo anterior
    // pero apunta a uno parecido(el de arriba es el principal authentication)
    // https://docs.spring.io/spring-security/site/docs/3.0.x/reference/el-access.html
    @PutMapping("api/categorias/{id}")
    // Tengo que decirle que tipo de error va a devolver
    // no devuelve el mismo error que el primer metodo
    @PreAuthorize("@usuarioService.buscarPorUsername(principal.getUsername()).getTipoUsuarioId().getValue() == 3") // En
                                                                                                                   // este
                                                                                                                   // caso
                                                                                                                   // quiero
                                                                                                                   // que
                                                                                                                   // sea
                                                                                                                   // Staff
    ResponseEntity<GenericResponse> modificarCategoria(@PathVariable Integer id,
            @RequestBody modifCategoriaRequest modifReq) {

        Categoria categoria = categoriaService.buscarPorId(id);

        if (categoria == null) {
            return ResponseEntity.notFound().build();

        } else {
            categoria.setNombre(modifReq.nombre);
            categoria.setDescripcion(modifReq.descripcion);
            Categoria categoriaModificada = categoriaService.modificarCategoria(categoria);

            GenericResponse r = new GenericResponse();
            r.isOk = true;
            r.message = "Categoria Modificada exitosamente";
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
// Autorizacion Forma 3:
    // Metodo Verificacion 3: haciendo lo mismo que antes, pero leyendo
    // desde el el authority. O sea , cuando creamos el User para el UserDetails(no
    // el usuario)
    // Le seteamos una autoridad sobre el tipo de usuario
    // Esto lo que hace es preguntar si tiene esa autoridad seteada.
    // Dentro de este, tenemos 2 formas de llenar el Authority
    // Llenandolo desde la Base de datos, o desde el JWT
    // Desde la DB nos da mas seguridad pero cada vez que se ejecute es ir a buscar
    // a la DB
    // Desde el JWT, si bien exponemos el entityId, nos permite evitarnos ir a la
    // db.
    // Este CLAIM lo podemos hacer con cualquier propiedad que querramos mandar
    // al JWT
    @GetMapping("/api/categorias/{id}")
    @PreAuthorize("hasAuthority('CLAIM_userType_STAFF')")
    ResponseEntity<CategoriaResponse> buscarPorIdCategoria(@PathVariable Integer id) {
        Categoria categoria = categoriaService.buscarPorId(id);

        CategoriaResponse catResponse = new CategoriaResponse();
        catResponse.nombre = categoria.getNombre();
        catResponse.descripcion = categoria.getDescripcion();

        return ResponseEntity.ok(catResponse);
    }

}