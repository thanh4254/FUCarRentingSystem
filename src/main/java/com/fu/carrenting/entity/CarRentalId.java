package com.fu.carrenting.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarRentalId implements Serializable {

    @Column(name = "CustomerID")
    private Integer customerId;

    @Column(name = "CarID")
    private Integer carId;
}