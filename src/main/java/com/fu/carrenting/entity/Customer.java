package com.fu.carrenting.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "Customer")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CustomerID")
    private Integer customerId;

    @NotBlank(message = "Tên không được để trống")
    @Column(name = "CustomerName", nullable = false, length = 100)
    private String customerName;

    @NotBlank(message = "SĐT không được để trống")
    @Column(name = "Mobile", nullable = false, length = 20)
    private String mobile;

    @NotNull(message = "Ngày sinh không được để trống")
    @Column(name = "Birthday", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    @NotBlank(message = "CMND không được để trống")
    @Column(name = "IdentityCard", nullable = false, unique = true, length = 20)
    private String identityCard;

    @NotBlank(message = "Số bằng lái không được để trống")
    @Column(name = "LicenceNumber", nullable = false, unique = true, length = 30)
    private String licenceNumber;

    @NotNull(message = "Ngày cấp bằng không được để trống")
    @Column(name = "LicenceDate", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate licenceDate;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không đúng định dạng")
    @Column(name = "Email", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "Password", nullable = false, length = 255)
    private String password;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AccountID", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Account account;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<CarRental> carRentals;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Review> reviews;
}
