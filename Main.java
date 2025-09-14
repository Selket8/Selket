package test;

import model.Car;
import repository.CarsRepository;
import repository.CarsRepositoryImpl;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

class Main {
    public static void main(String[] args) throws IOException {
        // Исходные данные
        String data =
                "a123me|Mercedes|White|0|8300000\n" +
                        "b873of|Volga|Black|0|673000\n" +
                        "w487mn|Lexus|Grey|76000|900000\n" +
                        "p987hj|Volga|Red|610|704340\n" +
                        "c987ss|Toyota|White|254000|761000\n" +
                        "o983op|Toyota|Black|698000|740000\n" +
                        "p146op|BMW|White|271000|850000\n" +
                        "u893ii|Toyota|Purple|210900|440000\n" +
                        "l097df|Toyota|Black|108000|780000\n" +
                        "y876wd|Toyota|Black|160000|1000000";

        String inputFileName = "input.txt";
        String outputFileName = "output.txt";

        // Запись данных в файл input.txt
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(inputFileName))) {
            writer.write(data);
            // Добавляем в конце дополнительные тестовые строки (по условию, например)
            writer.newLine();
            writer.write("Black, 0L");
            writer.newLine();
            writer.write("700_000L, 800_000L");
            writer.newLine();
            writer.write("Toyota");
            writer.newLine();
            writer.write("Volvo");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Создаем репозиторий и читаем данные
        CarsRepository carsRepository = new CarsRepositoryImpl();

        try (BufferedReader br = new BufferedReader(new FileReader(inputFileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Проверяем, что строка содержит нужные данные
                if (line.contains("|")) {
                    String[] parts = line.split("\\|");
                    if (parts.length == 5) {
                        String number = parts[0];
                        String model = parts[1];
                        String color = parts[2];
                        int mileage = Integer.parseInt(parts[3]);
                        long cost = Long.parseLong(parts[4]);
                        Car car = new Car(number, model, color, mileage, cost);
                        carsRepository.addCar(car);
                    }
                } else {
                    // Обработка других строк, если нужно
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Car> cars = carsRepository.getAllCars();

        // Записываем в файл результат
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputFileName))) {
            // Вывод всех автомобилей
            bw.write("Автомобили в базе:\n");
            bw.write(String.format("%-10s %-10s %-10s %-10s %-10s\n", "Number", "Model", "Color", "Mileage", "Cost"));
            for (Car car : cars) {
                bw.write(String.format("%-10s %-10s %-10s %-10d %-10d\n",
                        car.getNumber(), car.getModel(), car.getColor(), car.getMileage(), car.getCost()));
            }

            // 1) Цвет автомобиля с минимальной стоимостью
            Optional<Car> minCostCarOpt = cars.stream()
                    .min(Comparator.comparingLong(Car::getCost));
            if (minCostCarOpt.isPresent()) {
                String minCostColor = minCostCarOpt.get().getColor();
                bw.write("\nЦвет автомобиля с минимальной стоимостью: " + minCostColor + "\n");
            }

            // 2) Средняя стоимость искомой модели
            String modelToFind = "Toyota"; // Можно вынести в переменную или получать из входных данных
            List<Car> carsOfModel = cars.stream()
                    .filter(c -> c.getModel().equalsIgnoreCase(modelToFind))
                    .collect(Collectors.toList());
            double averageCost = carsOfModel.stream()
                    .mapToLong(Car::getCost)
                    .average()
                    .orElse(0.0);
            bw.write(String.format("Средняя стоимость модели %s: %.2f,\n", modelToFind, averageCost));

            // Средняя стоимость модели Volvo, если есть
            String modelVolvo = "Volvo";
            List<Car> volvoCars = cars.stream()
                    .filter(c -> c.getModel().equalsIgnoreCase(modelVolvo))
                    .collect(Collectors.toList());
            double avgVolvo = volvoCars.stream()
                    .mapToLong(Car::getCost)
                    .average()
                    .orElse(0.0);
            bw.write(String.format("Средняя стоимость модели %s: %.2f\n", modelVolvo, avgVolvo));

            // Вывод на консоль (по желанию)
            System.out.println("Обработка завершена. Результаты записаны в файл " + outputFileName);
        }
    }
}