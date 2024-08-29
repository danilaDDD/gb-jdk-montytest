package ru.danila.gb;

import com.google.common.collect.Collections2;

import java.util.*;

public class Main {
    private static int DOOR_COUNT = 3;
    public static void main(String[] args) {
        // полный перебор всех вариантов игры
        new MontyTestLauncher(DOOR_COUNT).start();
    }
}