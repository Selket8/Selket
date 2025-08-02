package org.example;

import java.util.*;

class Person {
    private String name;
    private double money;
    private List<Product> packageProducts;

    public Person(String name, double money) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Имя не может быть пустым");
        }
        if (name.length() < 3) {
            throw new IllegalArgumentException("Имя не может быть короче 3 символов");
        }
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
        long temp;
        result = name.hashCode();
        temp = Double.doubleToLongBits(money);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + packageProducts.hashCode();
        return result;
    }
}

class Product {
    private String name;
    private double price;

    public Product(String name, double price) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Название продукта не может быть пустым");
        }
        if (price < 0) {
            throw new IllegalArgumentException("Стоимость не может быть отрицательной");
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

class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Map<String, Person> buyers = new HashMap<>();
        Map<String, Product> productsMap = new HashMap<>();

        // Ввод покупателей
        System.out.println("Введите список покупателей в формате \"Имя=Сумма\" (например: Ivan=100):");
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
                checkName(name);
                double money = Double.parseDouble(sumStr);
                if (money < 0) {
                    System.out.println("Деньги не могут быть отрицательными");
                    continue;
                }
                Person person = new Person(name, money);
                buyers.put(name, person);
            } catch (NumberFormatException e) {
                System.out.println("Некорректное число: " + sumStr);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        // Ввод продуктов
        System.out.println("Введите список продуктов в формате \"Название=Цена\" (например: Bread=10):");
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
                checkProductName(name);
                double price = Double.parseDouble(priceStr);
                if (price < 0) {
                    System.out.println("Стоимость не может быть отрицательной");
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

        // Обработка покупок
        System.out.println("Для покупки введите строки в формате \"Имя - Название продукта\", или \"END\" для завершения:");
        String line;
        while (true) {
            line = scanner.nextLine();
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

        // Вывод результатов
        System.out.println("Результаты покупок:");
        for (Person person : buyers.values()) {
            List<Product> purchasedProducts = person.getPackageProducts();
            if (purchasedProducts.isEmpty()) {
                System.out.println(person.getName() + " - Ничего не куплено");
            } else {
                String productList = "";
                for (int i = 0; i < purchasedProducts.size(); i++) {
                    productList += purchasedProducts.get(i).getName();
                    if (i != purchasedProducts.size() - 1) {
                        productList += ", ";
                    }
                }
                System.out.println(person.getName() + " - " + productList);
            }
        }

        scanner.close();
    }

    private static void checkName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Имя не может быть пустым");
        }
        if (name.length() < 3) {
            throw new IllegalArgumentException("Имя не может быть короче 3 символов");
        }
    }

    private static void checkProductName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Название продукта не может быть пустым");
        }
    }
}