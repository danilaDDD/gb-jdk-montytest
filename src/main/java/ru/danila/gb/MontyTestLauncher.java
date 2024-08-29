package ru.danila.gb;

import com.google.common.collect.Collections2;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

public class MontyTestLauncher extends Thread{
    private int choiceCount;

    public MontyTestLauncher(int choiceCount) {
        this.choiceCount = choiceCount;
    }

    @Override
    public void run() {
        List<Choice> startCombine = getStartCombine();
        Set<List<Choice>> choiceCombineSet = Collections.synchronizedSet(new HashSet<>());
        choiceCombineSet.addAll(Collections2.permutations(startCombine));
        // счетаем варианты для каждого варианта игры параллельно в отдельном потоке
        CountDownLatch cdl = new CountDownLatch(choiceCombineSet.size());
        List<AllMontyGameVariantsThread> gameThreads = new ArrayList<>();
        try {

            for (var choiceCombine : choiceCombineSet) {
                var game = new AllMontyGameVariantsThread(choiceCombine, cdl);
                game.start();
                gameThreads.add(game);
            }
            cdl.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        int step = 1;
        Map<Integer, MontyTestStep> stepToTest = new ConcurrentHashMap<>();
        for(var game: gameThreads){
            for(var testStep: game.getTestSteps()){
                stepToTest.put(step++, testStep);
            }
        }

    }

    private List<Choice> getStartCombine() {
        List<Choice> combine = new ArrayList<>(choiceCount);
        combine.add(Choice.AUTO);
        for (int i = 0; i < choiceCount - 1; i++) {
            combine.add(Choice.GOAT);
        }

        return combine;
    }
}
