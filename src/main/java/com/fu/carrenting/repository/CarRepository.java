package com.fu.carrenting.repository;

import com.fu.carrenting.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {

    List<Car> findByStatus(String status);

    @Query("SELECT c FROM Car c WHERE " +
            "LOWER(c.carName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(c.color) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(c.producer.producerName) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Car> searchCars(String keyword);

    @Query("SELECT COUNT(cr) FROM CarRental cr WHERE cr.car.carId = :carId")
    long countRentalsByCarId(Integer carId);
}