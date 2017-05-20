package ru.battle.model;

import lombok.Getter;
import lombok.Setter;
import ru.battle.actions.GenerateField;

/**
 * Created by onotole on 20/05/2017.
 */
@Getter
@Setter
public class Game {
    private BattleField ourField;
    private BattleField enemyField;
    private int[] lastHit = new int[]{};

    public void startNewGame() {
        ourField = new GenerateField().genField();
        enemyField = new BattleField();
        enemyField.getUnknownField();
    }

    public String showFields() {
        StringBuilder sb = new StringBuilder(300);
        sb.append("\n");

        for (int i = 0; i < BattleField.SIZE; i++) {
            sb.append(i);
        }
        sb.append("\t");
        for (int i = 0; i < BattleField.SIZE; i++) {
            sb.append(i);
        }
        sb.append("\n");

        for (int i = 0; i < BattleField.SIZE; i++) {
            sb.append(i);
            for (int j = 0; j < BattleField.SIZE; j++) {
                sb.append(ourField.getCell(i, j));
            }
            sb.append("\t");
            sb.append(i);
            for (int j = 0; j < BattleField.SIZE; j++) {
                sb.append(enemyField.getCell(i, j));
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
