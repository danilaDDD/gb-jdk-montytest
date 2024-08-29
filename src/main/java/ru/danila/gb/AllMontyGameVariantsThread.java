package ru.danila.gb;

import lombok.Getter;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class AllMontyGameVariantsThread extends Thread{
    private final List<Choice> variants;
    @Getter
    private final List<MontyTestStep> testSteps = new LinkedList<>();
    private final CountDownLatch cdl;

    public AllMontyGameVariantsThread(List<Choice> variants, CountDownLatch cdl) {
        this.variants = variants;
        this.cdl = cdl;
    }

    @Override
    public void run() {
        boolean agreePresentChoice = false;
        for (int i = 0; i < variants.size(); i++) {
            boolean win = variants.get(i) == Choice.AUTO;
            testSteps.add(new MontyTestStep(variants, i, win, agreePresentChoice));
        }

        agreePresentChoice = true;
        for (int playerChoiceIndex = 0; playerChoiceIndex < variants.size(); playerChoiceIndex++) {
            for (int presentChoiceIndex = 0; presentChoiceIndex < variants.size(); presentChoiceIndex++) {
                Choice playerChoice = variants.get(playerChoiceIndex);
                Choice presenterChoice = variants.get(presentChoiceIndex);

                if(playerChoiceIndex != presentChoiceIndex && presenterChoice != Choice.AUTO){
                    boolean win = playerChoice == Choice.AUTO;
                    testSteps.add(new MontyTestStep(variants, playerChoiceIndex, presentChoiceIndex, win, agreePresentChoice));
                }
            }
        }

        this.cdl.countDown();
    }
}
