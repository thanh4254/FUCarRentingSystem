package com.fu.carrenting.repository;

import com.fu.carrenting.entity.CarRental;
import com.fu.carrenting.entity.CarRentalId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface CarRentalRepository extends JpaRepository<CarRental, CarRentalId> {

    List<CarRental> findByCustomer_CustomerId(Integer customerId);

    @Query("SELECT cr FROM CarRental cr WHERE " +
            "cr.pickupDate >= :startDate AND cr.returnDate <= :endDate " +
            "ORDER BY cr.rentPrice DESC")
    List<CarRental> findByPeriod(LocalDate startDate, LocalDate endDate);

    @Query("SELECT cr FROM CarRental cr ORDER BY cr.pickupDate DESC")
    List<CarRental> findAllOrderByPickupDateDesc();
}