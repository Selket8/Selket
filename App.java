package ru.innopolis.app;

import java.sql.*;
import java.time.LocalDate;


public class App {
    private static Connection connection;

    /**
     * Основной метод, выполняющий последовательность операций.
     */
    public static void main(String[] args) {
        try {
            // Инициализация подключения к базе данных напрямую в коде
            connect();

            // 1. Вставка нового товара и покупателя
            insertProduct(45, "Пуховка", 120, 10, "косметика");
            insertCustomer(506, "Иван", "Белый", "6532", "it@ya.ru");

            // 2. Добавление нового заказа
            insertOrder(23, 5, 4365, LocalDate.now(), 3, 77);

            // 3. Вывод 5 заказов с JOIN на таблицы product и customer
            printOrders();

            // 4. Обновление данных в таблице product
            updateProductPrice(2, 500);
            updateProductQuantity(7, 20);

            // 5. Удаление тестовых записей (пример)
            deleteProductById(45);
            deleteCustomerById(506);
            deleteOrderById(23);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }
    }

    /**
     * Устанавливает соединение с базой данных прямо в коде.
     */
    private static void connect() throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String user = "postgres";
        String password = "ZXasqw12";
        connection = DriverManager.getConnection(url, user, password);
        System.out.println("Подключение установлено");
    }

    /**
     * Заканчивает соединение с базой.
     */
    private static void disconnect() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Соединение закрыто");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Вставляет новый товар в таблицу product.
     */
    private static void insertProduct(int id, String description, double price, int quantity, String category) throws SQLException {
        String sql = "INSERT INTO product (id, description, price, quantity, category) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, description);
            pstmt.setDouble(3, price);
            pstmt.setInt(4, quantity);
            pstmt.setString(5, category);
            pstmt.executeUpdate();
            System.out.println("Добавлен товар с ID " + id);
        }
    }

    /**
     * Вставляет нового покупателя в таблицу customer.
     */
    private static void insertCustomer(int id, String firstName, String lastName, String phone, String email) throws SQLException {
        String sql = "INSERT INTO customer (id, first_name, last_name, phone, email) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, firstName);
            pstmt.setString(3, lastName);
            pstmt.setString(4, phone);
            pstmt.setString(5, email);
            pstmt.executeUpdate();
            System.out.println("Добавлен покупатель с ID " + id);
        }
    }

    /**
     * Вставляет новый заказ в таблицу order.
     */
    private static void insertOrder(int id, int productId, int customerId, LocalDate orderDate, int quantity, int statusId) throws SQLException {
        String sql = "INSERT INTO \"order\" (id, product_id, customer_id, order_date, quantity, status_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setInt(2, productId);
            pstmt.setInt(3, customerId);
            pstmt.setDate(4, Date.valueOf(orderDate));
            pstmt.setInt(5, quantity);
            pstmt.setInt(6, statusId);
            pstmt.executeUpdate();
            System.out.println("Добавлен заказ с ID " + id);
        }
    }

    /**
     * Выводит 5 заказов с информацией о товаре и покупателе.
     */
    private static void printOrders() throws SQLException {
        String sql = "SELECT o.id AS order_id, o.order_date, o.quantity, o.status_id, " +
                "p.description AS product_description, c.first_name, c.last_name " +
                "FROM \"order\" o " +
                "JOIN product p ON o.product_id = p.id " +
                "JOIN customer c ON o.customer_id = c.id " +
                "LIMIT 5";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                System.out.printf("Order ID: %d, Date: %s, Quantity: %d, Status ID: %d, Product: %s, Customer: %s %s%n",
                        rs.getInt("order_id"),
                        rs.getDate("order_date"),
                        rs.getInt("quantity"),
                        rs.getInt("status_id"),
                        rs.getString("product_description"),
                        rs.getString("first_name"),
                        rs.getString("last_name"));
            }
        }
    }

    /**
     * Обновляет цену товара по ID.
     */
    private static void updateProductPrice(int id, double newPrice) throws SQLException {
        String sql = "UPDATE product SET price = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setDouble(1, newPrice);
            pstmt.setInt(2, id);
            int affected = pstmt.executeUpdate();
            System.out.println("Обновлено " + affected + " записей для товара ID " + id);
        }
    }

    /**
     * Обновляет количество товара по ID.
     */
    private static void updateProductQuantity(int id, int newQuantity) throws SQLException {
        String sql = "UPDATE product SET quantity = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, newQuantity);
            pstmt.setInt(2, id);
            int affected = pstmt.executeUpdate();
            System.out.println("Обновлено " + affected + " записей для товара ID " + id);
        }
    }

    /**
     * Удаляет товар по ID.
     */
    private static void deleteProductById(int id) throws SQLException {
        String sql = "DELETE FROM product WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int affected = pstmt.executeUpdate();
            System.out.println("Удалено товаров с ID " + id + ": " + affected);
        }
    }

    /**
     * Удаляет покупателя по ID.
     */
    private static void deleteCustomerById(int id) throws SQLException {
        String sql = "DELETE FROM customer WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int affected = pstmt.executeUpdate();
            System.out.println("Удалено покупателей с ID " + id + ": " + affected);
        }
    }

    /**
     * Удаляет заказ по ID.
     */
    private static void deleteOrderById(int id) throws SQLException {
        String sql = "DELETE FROM \"order\" WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int affected = pstmt.executeUpdate();
            System.out.println("Удалено заказов с ID " + id + ": " + affected);
        }
    }
}