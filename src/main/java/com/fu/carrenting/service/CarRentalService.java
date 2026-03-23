package com.fu.carrenting.service;

import com.fu.carrenting.entity.CarRental;
import java.time.LocalDate;
import java.util.List;

public interface CarRentalService {
    List<CarRental> findAll();
    List<CarRental> findByCustomerId(Integer customerId);
    CarRental save(CarRental carRental);
    List<CarRental> findByPeriod(LocalDate startDate, LocalDate endDate);
}
