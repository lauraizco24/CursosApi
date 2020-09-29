package ar.com.ada.api.cursos.sistema.com.pagada;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ada.api.cursos.entities.Curso;
import ar.com.ada.api.cursos.entities.Estudiante;
import ar.com.ada.api.cursos.services.EstudianteService;
import ar.com.ada.api.cursos.sistema.com.pagada.models.*;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;

@Service
public class PagADAService {

    public Integer crearDeudor(Estudiante estudiante) {

        Deudor deudor = new Deudor();
        // Mapear los datos del estudiante en el deudor
        deudor.nombre = estudiante.getNombre();
        deudor.paisId = estudiante.getPaisId();
        deudor.tipoIdImpositivo = estudiante.getTipoDocumentoId().toString();
        deudor.idImpositivo = estudiante.getDocumento();

        // ...

        ResultadoCreacionDeudor rd = crearDeudor(deudor);

        if (rd.isOk)
            return rd.id;

        return 0;
    }

    public ResultadoCreacionDeudor crearDeudor(Deudor deudor) {
        ResultadoCreacionDeudor resultado = new ResultadoCreacionDeudor();

        JsonNode r;
        HttpResponse<JsonNode> request = Unirest.post("https://pagada.herokuapp.com/api/deudores")
                .header("content-type", "application/json").body(deudor) // AcaPasamos el RequestBody
                .header("api", "831DYEY1811NOMECORTENELSERVICIO").asJson();

        r = request.getBody();

        JSONObject jsonObject = r.getObject();

        resultado.isOk = jsonObject.getBoolean("isOk");
        resultado.id = jsonObject.getInt("id");
        resultado.message = jsonObject.getString("message");

        return resultado;

    }

    public ResultadoCreacionDeuda crearDeuda(Deuda deuda) {
        ResultadoCreacionDeuda resultado = new ResultadoCreacionDeuda();

        JsonNode r;
        HttpResponse<JsonNode> request = Unirest.post("https://pagada.herokuapp.com/api/servicios")
                .header("content-type", "application/json").body(deuda) // AcaPasamos el RequestBody
                .header("api", "831DYEY1811NOMECORTENELSERVICIO").asJson();

        r = request.getBody();

        JSONObject jsonObject = r.getObject();

        resultado.isOk = jsonObject.getBoolean("isOk");
        resultado.id = jsonObject.getInt("id");
        resultado.message = jsonObject.getString("message");

        return resultado;

    }

    public Deuda altaDeDeuda(Integer deudorId, Curso curso) {

        Deuda deuda = new Deuda();
        deuda.numero = "939393981";
        deuda.importe = curso.getImporte();
        deuda.fechaEmision = new Date();
        deuda.fechaVencimiento = new Date();
        deuda.empresaId = 2;
        deuda.moneda = "ARS";
        deuda.deudorId = deudorId;
        deuda.tipoComprobanteId = "FACTURA";
        deuda.estadoId = "PENDIENTE";
        deuda.tipoServicioId = 12;
        deuda.codigoDeBarras = "12345681";
        return deuda;

    }
}
