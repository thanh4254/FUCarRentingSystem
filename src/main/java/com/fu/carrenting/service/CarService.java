package com.fu.carrenting.service;


import com.fu.carrenting.entity.Car;
import java.util.List;
import java.util.Optional;

public interface CarService {
    List<Car> findAll();
    Optional<Car> findById(Integer id);
    Car save(Car car);
    void deleteOrDeactivate(Integer id);
    List<Car> search(String keyword);
    List<Car> findAvailable();
    boolean hasRentals(Integer carId);
}