package co.com.telefonica.ws.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;

/**
 * Description: Entidad del modelo MVC.
 *
 * @autor:  COE-Arquitectura-Telefonica
 * @date:   06-06-2023
 * @version 2.1.0
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Builder
@Table(name = "tbl_locations", schema = "public", catalog = "telefonicadb")
@AllArgsConstructor
public class Locations {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "region")
    private String region;

    @Column(name = "c_digo_dane_del_departamento")
    private String codigoDaneDelDepartamento;

    @Column(name = "departamento")
    private String departamento;

    @Column(name = "c_digo_dane_del_municipio")
    private String codigoDaneDelMunicipio;

    @Column(name = "municipio")
    private String municipio;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Locations locations = (Locations) o;
        return getId() != null && Objects.equals(getId(), locations.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
