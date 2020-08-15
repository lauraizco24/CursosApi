package ar.com.ada.api.cursos.entities;

public class Pais {
    //------------------Empieza enum----------------------------
  
    public enum PaisEnum{
        ARGENTINA(32),
        VENEZUELA(840),
        ESTADOS_UNIDOS(862);

        private final Integer value;

        // NOTE: Enum constructor tiene que estar en privado
        private PaisEnum(Integer value) {
            this.value = value;

    }
    public Integer getValue() {
        return value;
    }

    public static PaisEnum parse(Integer id) {
        PaisEnum status = null; // Default
        for (PaisEnum item : PaisEnum.values()) {
            if (item.getValue() == id) {
                status = item;
                break;
            }
        }
        return status;
    }
}
//------------------Termina enum----------------------------
  

public enum TipoDocuEnum{
    DNI(1), PASAPORTE(2);


    private final Integer value;

    // NOTE: Enum constructor tiene que estar en privado
    private TipoDocuEnum(Integer value) {
        this.value = value;

}
public Integer getValue() {
    return value;
}

public static TipoDocuEnum parse(Integer id) {
    TipoDocuEnum status = null; // Default
    for (TipoDocuEnum item : TipoDocuEnum.values()) {
        if (item.getValue() == id) {
            status = item;
            break;
        }
    }
    return status;
}
}





}