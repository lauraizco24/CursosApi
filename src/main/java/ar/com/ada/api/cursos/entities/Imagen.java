package ar.com.ada.api.cursos.entities;

import javax.persistence.*;

@Entity
@Table(name = "imagenes")
public class Imagen {

    @Id
    @Column(name = "img_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer imgId;

    
    @OneToOne(mappedBy = "imagen", cascade = CascadeType.ALL)
    private Categoria categoria;

    @Lob
    private Byte url;

    public Integer getImgId() {
        return imgId;
    }

    public void setImgId(Integer imgId) {
        this.imgId = imgId;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Byte getUrl() {
        return url;
    }

    public void setUrl(Byte url) {
        this.url = url;
    }




    
}