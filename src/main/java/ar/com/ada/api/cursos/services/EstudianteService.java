package ar.com.ada.api.cursos.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import ar.com.ada.api.cursos.entities.Curso;
import ar.com.ada.api.cursos.entities.Estudiante;
import ar.com.ada.api.cursos.entities.Inscripcion;
import ar.com.ada.api.cursos.entities.Inscripcion.EstadoInscripcionEnum;
import ar.com.ada.api.cursos.entities.Pais.PaisEnum;
import ar.com.ada.api.cursos.entities.Pais.TipoDocuEnum;
import ar.com.ada.api.cursos.repos.EstudianteRepository;

@Service
public class EstudianteService {
    @Autowired
    EstudianteRepository estudianteRepo;
    @Autowired
    CursoService cursoService;
    
    public boolean crearEstudiante(Estudiante estudiante) {
        if (estudianteRepo.existsEstudiante(estudiante.getPaisId().getValue(), estudiante.getTipoDocumentoId().getValue(),
        estudiante.getDocumento())){
            return false;
                    }           
                     estudianteRepo.save(estudiante);
        return true;
    }

    public Estudiante crearEstudiante(String nombre, PaisEnum paisEnum, TipoDocuEnum tipoDocuEnum, String documento,
            Date fechaNacimiento) {
                Estudiante estudiante = new Estudiante();
        estudiante.setNombre(nombre);
        estudiante.setPaisId(paisEnum);
        estudiante.setTipoDocumentoId(tipoDocuEnum);
        estudiante.setDocumento(documento);
        estudiante.setFechaNacimiento(fechaNacimiento);
        boolean resultado = crearEstudiante(estudiante);
        if (resultado)
            return estudiante;
        else
            return null;

    }

    public Estudiante buscarPorId(Integer id) {
        Optional<Estudiante> opEstudiante = estudianteRepo.findById(id);

        if (opEstudiante.isPresent())
            return opEstudiante.get();
        else
            return null;

    }

    public List<Estudiante> obtenerEstudiantes() {
        return estudianteRepo.findAll();
    }

    public Inscripcion inscribir(Integer estudianteId, Integer cursoId) {
        // TODO:buscar el estudiante por Id
        // buscar el curso por Id;
        // Crear la inscripcion(aprobada por defecto)
        // Asignar la inscripcion al Usuario del Estudiante
        // Agregar el Estudiante a la Lista de Estudiantes que tiene Curso

        Estudiante estudiante = buscarPorId(estudianteId);
        Curso curso = cursoService.buscarPorId(cursoId);
        Inscripcion inscripcion = new Inscripcion();

        inscripcion.setFechaInscripcion(new Date());
        inscripcion.setEstadoInscripcionId(EstadoInscripcionEnum.ACTIVO);

        // inscripcion.setCurso(curso);
        inscripcion.setUsuario(estudiante.getUsuario());

        curso.agregarInscripcion(inscripcion);
        curso.asignarEstudiante(estudiante);

        estudianteRepo.save(estudiante);
        return inscripcion;
    } 
}