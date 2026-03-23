package com.fu.carrenting.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "Review")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Review {

    @EmbeddedId
    private ReviewId id = new ReviewId();

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

    @NotNull
    @Min(1) @Max(5)
    @Column(name = "ReviewStar", nullable = false)
    private Integer reviewStar;

    @NotBlank
    @Column(name = "Comment", nullable = false, columnDefinition = "NVARCHAR(MAX)")
    private String comment;
}