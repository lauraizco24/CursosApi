package ar.com.ada.api.cursos.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ar.com.ada.api.cursos.entities.Estudiante;

@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante, Integer> {

	
    @Query("select CASE WHEN  count(d) > 0 THEN true ELSE false END from Docente d where d.paisId=:pais and d.tipoDocumentoId=:tipoDocuEnum and d.documento=:documento")
    boolean existsEstudiante(Integer pais, Integer tipoDocuEnum, String documento);

    @Query("select d from Docente d where d.paisId=:pais and d.tipoDocumentoId=:tipoDocuEnum and d.documento=:documento")
    Estudiante buscarEstudiantePorDocu(Integer pais, Integer tipoDocuEnum, String documento);

}