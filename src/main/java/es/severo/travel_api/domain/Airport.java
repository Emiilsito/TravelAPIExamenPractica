package es.severo.travel_api.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "airports")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Airport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code", unique = true, nullable = false, length = 3)
    private String code;

    @Column(name = "name", length = 120, nullable = false)
    private String name;

    @Column(name = "city", length = 80, nullable = false)
    private String city;

    @Column(name = "country", length = 80, nullable = false)
    private String country;

}
