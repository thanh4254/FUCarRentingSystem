package com.fu.carrenting.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "CarProducer")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarProducer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProducerID")
    private Integer producerId;

    @NotBlank(message = "Tên nhà sản xuất không được để trống")
    @Column(name = "ProducerName", nullable = false, length = 100)
    private String producerName;

    @NotBlank(message = "Địa chỉ không được để trống")
    @Column(name = "Address", nullable = false, length = 255)
    private String address;

    @NotBlank(message = "Quốc gia không được để trống")
    @Column(name = "Country", nullable = false, length = 100)
    private String country;

    @OneToMany(mappedBy = "producer", cascade = CascadeType.ALL)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Car> cars;
}