package com.fu.carrenting.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "Car")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CarID")
    private Integer carId;

    @NotBlank(message = "Tên xe không được để trống")
    @Column(name = "CarName", nullable = false, length = 100)
    private String carName;

    @NotNull(message = "Năm SX không được để trống")
    @Column(name = "CarModelYear", nullable = false)
    private Integer carModelYear;

    @NotBlank(message = "Màu sắc không được để trống")
    @Column(name = "Color", nullable = false, length = 50)
    private String color;

    @NotNull(message = "Số chỗ không được để trống")
    @Column(name = "Capacity", nullable = false)
    private Integer capacity;

    @NotBlank(message = "Mô tả không được để trống")
    @Column(name = "Description", nullable = false, columnDefinition = "NVARCHAR(MAX)")
    private String description;

    @NotNull(message = "Ngày nhập không được để trống")
    @Column(name = "ImportDate", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate importDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ProducerID", nullable = false)
    private CarProducer producer;

    @NotNull(message = "Giá thuê không được để trống")
    @Column(name = "RentPrice", nullable = false, precision = 18, scale = 2)
    private BigDecimal rentPrice;

    @NotBlank(message = "Trạng thái không được để trống")
    @Column(name = "Status", nullable = false, length = 20)
    private String status;

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<CarRental> carRentals;

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Review> reviews;
}