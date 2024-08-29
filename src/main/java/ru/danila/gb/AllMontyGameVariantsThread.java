package ru.danila.gb;

import lombok.Getter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class AllMontyGameVariantsThread extends Thread{
    private final List<Choice> variants;
    @Getter
    private final List<MontyTestStep> testSteps = new LinkedList<>();
    private CountDownLatch cdl;

    public AllMontyGameVariantsThread(List<Choice> variants, CountDownLatch cdl) {
        this.variants = variants;
        this.cdl = cdl;
    }

    @Override
    public void run() {
        for (int i = 0; i < variants.size(); i++) {
            for (int j = 0; j < variants.size(); j++) {
                Choice playerChoice = variants.get(i);
                Choice presenterChoice = variants.get(j);

                if(presenterChoice != playerChoice && presenterChoice != Choice.AUTO){
                    boolean win = playerChoice == Choice.AUTO;
                    testSteps.add(new MontyTestStep(variants, i, j, win, true));
                    testSteps.add(new MontyTestStep(variants, i, j, win, false));
                }
            }
        }

        this.cdl.countDown();
    }
}
