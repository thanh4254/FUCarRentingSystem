package com.fu.carrenting.service.impl;

import com.fu.carrenting.entity.CarRental;
import com.fu.carrenting.repository.CarRentalRepository;
import com.fu.carrenting.service.CarRentalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CarRentalServiceImpl implements CarRentalService {

    private final CarRentalRepository carRentalRepository;

    @Override public List<CarRental> findAll() { return carRentalRepository.findAllOrderByPickupDateDesc(); }
    @Override public List<CarRental> findByCustomerId(Integer customerId) { return carRentalRepository.findByCustomer_CustomerId(customerId); }
    @Override public CarRental save(CarRental carRental) { return carRentalRepository.save(carRental); }
    @Override public List<CarRental> findByPeriod(LocalDate startDate, LocalDate endDate) { return carRentalRepository.findByPeriod(startDate, endDate); }
}