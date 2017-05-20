package ru.battle.model;

import lombok.Getter;
import ru.battle.actions.GenerateField;

/**
 * Created by onotole on 20/05/2017.
 */
@Getter
public class Game {
    private BattleField ourField;
    private BattleField enemyField;
    private int[] lastHit;


    public void startNewGame() {
        ourField = new GenerateField().genField();
        enemyField = new BattleField();
        enemyField.getUnknownField();
    }

    public String showFields() {
        StringBuilder sb = new StringBuilder(300);
        for (int i = 0; i < BattleField.SIZE; i++) {
            for (int j = 0; j < BattleField.SIZE; j++) {
                sb.append(ourField.getCell(i, j));
            }
            sb.append("\t");
            for (int j = 0; j < BattleField.SIZE; j++) {
                sb.append(enemyField.getCell(i, j));
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
