package ar.com.ada.api.cursos.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ada.api.cursos.entities.Docente;
import ar.com.ada.api.cursos.entities.Pais.PaisEnum;
import ar.com.ada.api.cursos.entities.Pais.TipoDocuEnum;
import ar.com.ada.api.cursos.repos.CursoRepository;
import ar.com.ada.api.cursos.repos.DocenteRepository;

@Service
public class DocenteService {

    @Autowired
    CursoRepository cursoRepo;
    @Autowired
    CategoriaService categoriaService;
    @Autowired
    DocenteRepository docenteRepo;

    public boolean crearDocente(Docente docente) {
        if (docenteRepo.existsDocente(docente.getPaisId(), docente.getTipoDocumentoId().getValue(),
                docente.getDocumento()))
            return false;
        docenteRepo.save(docente);
        return true;
    }

    public Docente crearDocente(String nombre, Integer paisId, TipoDocuEnum tipoDocumentoId, String documento,
            Date fechaNacimiento) {
        Docente docente = new Docente();
        docente.setNombre(nombre);
        docente.setPaisId(paisId);
        docente.setTipoDocumentoId(tipoDocumentoId);
        docente.setDocumento(documento);
        docente.setFechaNacimiento(fechaNacimiento);
        boolean resultado = crearDocente(docente);
        if (resultado)
            return docente;
        else
            return null;

    }

    public Docente buscarPorId(Integer id) {
        Optional<Docente> opDocente = docenteRepo.findById(id);

        if (opDocente.isPresent())
            return opDocente.get();
        else
            return null;

    }

    public List<Docente> obtenerDocentes() {
        return docenteRepo.findAll();
    }


}