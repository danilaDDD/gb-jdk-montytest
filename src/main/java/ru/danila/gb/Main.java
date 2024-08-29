package ru.danila.gb;

public class Main {
    private static final int DOOR_COUNT = 3;
    public static void main(String[] args) {
        // полный перебор всех вариантов игры
        new MontyTestLauncher(DOOR_COUNT).start();
    }
}