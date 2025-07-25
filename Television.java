package org.example;

public class Television {

    private String brand;
    private int channel;
    private int volume;
    private boolean isOn;

    public Television() {
        this.brand = "Неизвестно";
        this.channel = 1;
        this.volume = 10;
        this.isOn = false;
    }

    public Television(String brand, int channel, int volume) {
        this.brand = brand;
        this.channel = channel;
        this.volume = volume;
        this.isOn = false;
    }

    public String getBrand(){
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        if (channel > 1 && channel <= 100) {
            this.channel = channel;
        }else {
            System.out.println("Недопустимый номер. Принимается только от 1 до 100");
        }
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        if (volume >= 0 && volume <= 100) {
            this.volume = volume;
        } else {
            System.out.println("Недопустимый уровень громкости. Принимается только от 0 до 100");
        }
    }

    public boolean isOn() {
        return isOn;
    }

    public void turnOn() {
        if (!isOn) {
            isOn = true;
            System.out.println("ТВ включен");
        } else {
            System.out.println("ТВ уже включен");
        }
    }

    public void turnOff() {
        if (isOn) {
            isOn = false;
            System.out.println("ТВ выключен");
        } else {
            System.out.println("ТВ уже выключен");
        }
    }

    public void changeChannel(int newChannel) {
        if (isOn) {
            if (newChannel >= 1 && newChannel <= 100) {
                channel = newChannel;
                System.out.println("Теперь ваш канал" + channel);
            } else {
                System.out.println("Недпустимый номер канала");
            }
        } else {
            System.out.println("Сначала включите ТВ");
        }
    }

    public void increaseVolume() {
        if (isOn) {
            if (volume < 100) {
                volume++;
                System.out.println("Уровень громкости:" + volume);
            } else {
                System.out.println("Громкость на минимум");
            }
        } else {
            System.out.println("Включите ТВ и потом меняйте звук");
        }
    }
}