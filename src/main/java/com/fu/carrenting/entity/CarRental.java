package com.fu.carrenting.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "CarRental")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarRental {

    @EmbeddedId
    private CarRentalId id = new CarRentalId();

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("customerId")
    @JoinColumn(name = "CustomerID")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Customer customer;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("carId")
    @JoinColumn(name = "CarID")
    private Car car;

    @NotNull(message = "Ngày nhận xe không được để trống")
    @Column(name = "PickupDate", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate pickupDate;

    @NotNull(message = "Ngày trả xe không được để trống")
    @Column(name = "ReturnDate", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate returnDate;

    @NotNull(message = "Giá thuê không được để trống")
    @Column(name = "RentPrice", nullable = false, precision = 18, scale = 2)
    private BigDecimal rentPrice;

    @NotBlank(message = "Trạng thái không được để trống")
    @Column(name = "Status", nullable = false, length = 20)
    private String status;
}