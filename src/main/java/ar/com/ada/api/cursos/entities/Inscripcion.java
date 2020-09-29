package ar.com.ada.api.cursos.entities;

import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name="inscripcion")
public class Inscripcion {
    @Id
    @Column(name = "inscripcion_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer inscripcionId;
    @ManyToOne 
    @JoinColumn(name ="curso_id", referencedColumnName = "curso_id" )
    private Curso curso;
    private Date fechaInscripcion;
    @ManyToOne
    @JoinColumn(name ="usuario_id", referencedColumnName = "usuario_id" )
    private Usuario usuario;
    @Column(name="estado_inscripcion_id")
    private int estadoInscripcionId;

    private Double importe;

    private String numero;
    @Column(name="codigo_barras")
    private String codigoDeBarra;


    
//------------------Empieza enum----------------------------
  
    public enum EstadoInscripcionEnum {
        ACTIVO(1), INACTIVO(2);

        private final int value;

        // NOTE: Enum constructor tiene que estar en privado
        private EstadoInscripcionEnum(int value) {
            this.value = value;

        }

        public int getValue() {
            return value;
        }

        public static EstadoInscripcionEnum parse(int id) {
            EstadoInscripcionEnum status = null; // Default
            for (EstadoInscripcionEnum item : EstadoInscripcionEnum.values()) {
                if (item.getValue() == id) {
                    status = item;
                    break;
                }
            }
            return status;
     
        }
    }
    //______________________TERMINA ENUM ___________________________

	public Integer getInscripcionId() {
		return inscripcionId;
	}


	public void setInscripcionId(Integer inscripcionId) {
		this.inscripcionId = inscripcionId;
	}


	public Curso getCurso() {
		return curso;
	}


	public void setCurso(Curso curso) {
		this.curso = curso;
	}


	public Date getFechaInscripcion() {
		return fechaInscripcion;
	}


	public void setFechaInscripcion(Date fechaInscripcion) {
		this.fechaInscripcion = fechaInscripcion;
	}


	public Usuario getUsuario() {
		return usuario;
	}


	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}


	public int getEstadoInscripcionId() {
		return estadoInscripcionId;
	}


	public void setEstadoInscripcionId(EstadoInscripcionEnum estadoInscripcionId) {
		this.estadoInscripcionId = estadoInscripcionId.getValue();
	}

    public void setEstadoInscripcionId(int estadoInscripcionId) {
        this.estadoInscripcionId = estadoInscripcionId;
    }

    public Double getImporte() {
        return importe;
    }

    public void setImporte(Double importe) {
        this.importe = importe;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCodigoDeBarra() {
        return codigoDeBarra;
    }

    public void setCodigoDeBarra(String codigoDeBarra) {
        this.codigoDeBarra = codigoDeBarra;
    }




}