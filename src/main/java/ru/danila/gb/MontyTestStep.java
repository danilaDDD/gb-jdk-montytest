package ru.danila.gb;

import lombok.AllArgsConstructor;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@ToString
public class MontyTestStep {
    private List<Choice> variants;
    private int playerChoiceIndex;
    private int presenterChoiceIndex;
    private boolean playerWon;
}
