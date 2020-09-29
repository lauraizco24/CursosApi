package ar.com.ada.api.cursos.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import ar.com.ada.api.cursos.entities.Curso;
import ar.com.ada.api.cursos.entities.Estudiante;
import ar.com.ada.api.cursos.entities.Inscripcion;
import ar.com.ada.api.cursos.entities.Inscripcion.EstadoInscripcionEnum;
import ar.com.ada.api.cursos.entities.Pais.PaisEnum;
import ar.com.ada.api.cursos.entities.Pais.TipoDocuEnum;
import ar.com.ada.api.cursos.repos.EstudianteRepository;
import ar.com.ada.api.cursos.sistema.com.pagada.PagADAService;
import ar.com.ada.api.cursos.sistema.com.pagada.models.Deuda;
import ar.com.ada.api.cursos.sistema.com.pagada.models.Deudor;
import ar.com.ada.api.cursos.sistema.com.pagada.models.ResultadoCreacionDeuda;

@Service
public class EstudianteService {
    @Autowired
    EstudianteRepository estudianteRepo;
    @Autowired
    CursoService cursoService;
    @Autowired
    PagADAService pagAdaService;

    public boolean crearEstudiante(Estudiante estudiante) {
        if (estudianteRepo.existsEstudiante(estudiante.getPaisId(), estudiante.getTipoDocumentoId().getValue(),
                estudiante.getDocumento())) {
            return false;
        }
        estudianteRepo.save(estudiante);
        return true;
    }

    public Estudiante crearEstudiante(String nombre, Integer paisEnum, TipoDocuEnum tipoDocuEnum, String documento,
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

    public List<Deudor> obtenerDeudores() {
        return obtenerEstudiantes().stream().map(e -> e.getDeudor()).flatMap(ds -> ds.stream())
                .collect(Collectors.toList());

    }

    public Estudiante buscarPorDeudorId(Integer deudorId) {

        // return obtenerDeudores().stream().filter(d ->
        // d.id.equals(deudorId)).findAny().get().getEstudiantes();
        return obtenerDeudores().stream().filter(d -> d.id.equals(deudorId)).findAny().get().getEstudiantes().stream()
                .findAny().get();

    }

    public Inscripcion inscribir(Integer estudianteId, Integer cursoId) {
        // buscar el estudiante por Id
        // buscar el curso por Id;
        // Crear la inscripcion(aprobada por defecto)
        // Asignar la inscripcion al Usuario del Estudiante
        // Agregar el Estudiante a la Lista de Estudiantes que tiene Curso

        Estudiante estudiante = buscarPorId(estudianteId);

        // Alta del Deudor
        Deudor deudor = new Deudor();
        deudor.id = pagAdaService.crearDeudor(estudiante);

        Curso curso = cursoService.buscarPorId(cursoId);

        Deuda deuda = pagAdaService.altaDeDeuda(deudor.id, curso);
        // Se crea la inscripcion
        Inscripcion inscripcion = new Inscripcion();

        inscripcion.setFechaInscripcion(new Date());

        // Alta de la Deuda
        inscripcion.setEstadoInscripcionId(EstadoInscripcionEnum.INACTIVO);
        inscripcion.setImporte(curso.getImporte());
        deuda.fechaEmision = inscripcion.getFechaInscripcion();
        deuda.fechaVencimiento = inscripcion.getFechaInscripcion();

        ResultadoCreacionDeuda rd = pagAdaService.crearDeuda(deuda);

        // ejecutar API para crear el Servicio
        if (rd.isOk) {
            // si todo esta bien cambiar a estado activo
            inscripcion.setEstadoInscripcionId(EstadoInscripcionEnum.ACTIVO);
        } else {
            inscripcion.setEstadoInscripcionId(EstadoInscripcionEnum.INACTIVO);
        }

        // inscripcion.setCurso(curso);
        inscripcion.setUsuario(estudiante.getUsuario());

        curso.agregarInscripcion(inscripcion);
        curso.asignarEstudiante(estudiante);
        estudiante.asignarDeudor(deudor);

        estudianteRepo.save(estudiante);
        return inscripcion;
    }
}