package repository;

import model.Car;

import java.util.ArrayList;
import java.util.List;

public class CarsRepositoryImpl implements CarsRepository {
    private List<Car> cars = new ArrayList<>();

    public CarsRepositoryImpl() {
        // Можно оставить пустым, добавим через метод
    }

    @Override
    public List<Car> getAllCars() {
        return cars;
    }

    @Override
    public void addCar(Car car) {
        cars.add(car);
    }
}
