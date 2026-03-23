package com.fu.carrenting.service.impl;

import com.fu.carrenting.entity.Car;
import com.fu.carrenting.repository.CarRepository;
import com.fu.carrenting.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;

    @Override public List<Car> findAll() { return carRepository.findAll(); }
    @Override public Optional<Car> findById(Integer id) { return carRepository.findById(id); }
    @Override public Car save(Car car) { return carRepository.save(car); }
    @Override public List<Car> findAvailable() { return carRepository.findByStatus("Available"); }
    @Override public boolean hasRentals(Integer carId) { return carRepository.countRentalsByCarId(carId) > 0; }

    @Override
    public List<Car> search(String keyword) {
        if (keyword == null || keyword.isBlank()) return findAll();
        return carRepository.searchCars(keyword);
    }

    @Override
    public void deleteOrDeactivate(Integer id) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Car not found: " + id));
        if (hasRentals(id)) {
            car.setStatus("Maintenance");
            carRepository.save(car);
        } else {
            carRepository.deleteById(id);
        }
    }
}