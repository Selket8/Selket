package org.example;

public class App {
    public static void main(String[] args) {
        Television tv = new Television("Samsung", 5, 20);

        tv.turnOn();
        System.out.println("Бренд " + tv.getBrand());
        System.out.println("Текущий канал " + tv.getChannel());
        System.out.println("Текущая громкость " + tv.getVolume());

        tv.changeChannel(10);
        tv.increaseVolume();
        tv.turnOff();

        tv.changeChannel(15);
        tv.increaseVolume();
    }
}
