package dz6;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class Person {
    private String name;
    private double money;
    private List<Product> packageProducts;

    public Person(String name, double money) {
        validateName(name);
        if (money < 0) {
            throw new IllegalArgumentException("Деньги не могут быть отрицательными");
        }
        this.name = name;
        this.money = money;
        this.packageProducts = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public double getMoney() {
        return money;
    }

    public void deductMoney(double amount) {
        this.money -= amount;
    }

    public List<Product> getPackageProducts() {
        return packageProducts;
    }

    public void addProduct(Product product) {
        packageProducts.add(product);
    }

    private void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Имя не может быть пустым");
        }
        if (name.length() < 3) {
            throw new IllegalArgumentException("Имя не может быть короче 3 символов");
        }
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", money=" + money +
                ", packageProducts=" + packageProducts +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (Double.compare(person.money, money) != 0) return false;
        if (!name.equals(person.name)) return false;
        return packageProducts.equals(person.packageProducts);
    }

    @Override
    public int hashCode() {
        int result;
        long temp = Double.doubleToLongBits(money);
        result = name.hashCode();
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + packageProducts.hashCode();
        return result;
    }
}


class Product {
    private String name;
    private final double price;

    public Product(String name, double price) {
        validateName(name);
        if (price <= 0) {
            throw new IllegalArgumentException("Стоимость должна быть больше нуля");
        }
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    private void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Название продукта не может быть пустым");
        }
        if (name.length() < 3) {
            throw new IllegalArgumentException("Название продукта должно быть не короче 3 символов");
        }
        if (name.matches("\\d+")) {
            throw new IllegalArgumentException("Название не должно содержать только цифры");
        }
    }

    @Override
    public String toString() {
        return name + " (" + price + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (Double.compare(product.price, price) != 0) return false;
        return name.equals(product.name);
    }

    @Override
    public int hashCode() {
        int result;
        long temp = Double.doubleToLongBits(price);
        result = name.hashCode();
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}

class DiscountProduct extends Product {
    private double discountAmount;
    private LocalDate endDate; // срок действия скидки

    public DiscountProduct(String name, double price, double discountAmount, LocalDate endDate) {
        super(name, price);
        if (discountAmount < 0) {
            throw new IllegalArgumentException("Размер скидки не может быть отрицательным");
        }
        if (super.getPrice() - discountAmount <= 0) {
            throw new IllegalArgumentException("Цена со скидкой должна быть больше нуля");
        }
        this.discountAmount = discountAmount;
        this.endDate = endDate;
    }

    public double getDiscountedPrice() {
        return super.getPrice() - discountAmount;
    }

    public boolean isValid() {
        return LocalDate.now().isBefore(endDate) || LocalDate.now().isEqual(endDate);
    }

    @Override
    public String toString() {
        return getName() + " (скидка " + discountAmount + ", конец " + endDate + ")";
    }
}


public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Map<String, Person> buyers = new HashMap<>();
        Map<String, Product> productsMap = new HashMap<>();

        // Ввод покупателей
        System.out.println("Введите список покупателей в формате \"Имя = сумма\" (через запятую):");
        String buyersLine = scanner.nextLine();
        String[] buyersEntries = buyersLine.split(",");
        for (String entry : buyersEntries) {
            String[] parts = entry.trim().split("=");
            if (parts.length != 2) {
                System.out.println("Некорректный формат покупателя: " + entry);
                continue;
            }
            String name = parts[0].trim();
            String sumStr = parts[1].trim();
            try {
                validateName(name);
                double sum = Double.parseDouble(sumStr);
                if (sum < 0) {
                    System.out.println("Деньги не могут быть отрицательными");
                    continue;
                }
                Person person = new Person(name, sum);
                buyers.put(name, person);
            } catch (NumberFormatException e) {
                System.out.println("Некорректное число: " + sumStr);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        // Ввод продуктов
        System.out.println("Введите список продуктов в формате \"Название=цена\" (через запятую):");
        String productsLine = scanner.nextLine();
        String[] productsEntries = productsLine.split(",");
        for (String entry : productsEntries) {
            String[] parts = entry.trim().split("=");
            if (parts.length != 2) {
                System.out.println("Некорректный формат продукта: " + entry);
                continue;
            }
            String name = parts[0].trim();
            String priceStr = parts[1].trim();
            try {
                validateProductName(name);
                double price = Double.parseDouble(priceStr);
                if (price <= 0) {
                    System.out.println("Стоимость должна быть больше нуля");
                    continue;
                }
                Product product = new Product(name, price);
                productsMap.put(name, product);
            } catch (NumberFormatException e) {
                System.out.println("Некорректное число: " + priceStr);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        // Ввод покупок
        System.out.println("Введите покупки строками \"Имя - Название продукта\", или \"END\" для завершения:");
        while (true) {
            String line = scanner.nextLine();
            if (line.equalsIgnoreCase("END")) {
                break;
            }
            String[] parts = line.split("-");
            if (parts.length != 2) {
                System.out.println("Некорректный формат ввода: " + line);
                continue;
            }
            String buyerName = parts[0].trim();
            String productName = parts[1].trim();

            if (!buyers.containsKey(buyerName)) {
                System.out.println("Покупатель с именем " + buyerName + " не найден");
                continue;
            }
            if (!productsMap.containsKey(productName)) {
                System.out.println("Продукт с названием " + productName + " не найден");
                continue;
            }

            Person person = buyers.get(buyerName);
            Product product = productsMap.get(productName);

            if (person.getMoney() >= product.getPrice()) {
                person.deductMoney(product.getPrice());
                person.addProduct(product);
            } else {
                System.out.println(person.getName() + " не может позволить себе " + product.getName());
            }
        }

        // Вывод итоговых покупок
        System.out.println("Итоговые покупки:");
        for (Person person : buyers.values()) {
            List<Product> purchased = person.getPackageProducts();
            if (purchased.isEmpty()) {
                System.out.println(person.getName() + " - Ничего не куплено");
            } else {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < purchased.size(); i++) {
                    sb.append(purchased.get(i).getName());
                    if (i != purchased.size() - 1) {
                        sb.append(", ");
                    }
                }
                System.out.println(person.getName() + " - " + sb.toString());
            }
        }

        scanner.close();
    }

    private static void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Имя не может быть пустым");
        }
        if (name.length() < 3) {
            throw new IllegalArgumentException("Имя не может быть короче 3 символов");
        }
    }

    private static void validateProductName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Название продукта не может быть пустым");
        }
        if (name.length() < 3) {
            throw new IllegalArgumentException("Название продукта должно быть не короче 3 символов");
        }
        if (name.matches("\\d+")) {
            throw new IllegalArgumentException("Название не должно содержать только цифры");
        }
    }
}