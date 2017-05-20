package ru.battle.actions;

import lombok.extern.slf4j.Slf4j;
import ru.battle.model.BattleField;

import java.util.Random;

/**
 * Created by onotole on 20/05/2017.
 */
@Slf4j
public class GenerateField {
    private Random random = new Random();
    private String[] ships = {
            "####", "###", "###", "##", "##", "##", "#", "#", "#", "#"
    };

    public BattleField genField() {
        BattleField field = new BattleField();

//        ship: for (String ship: ships) {
//            boolean success = false;
//            while (! success) {
//                int x1 = random.nextInt(BattleField.SIZE);
//                int y1 = random.nextInt(BattleField.SIZE);
//                if (! field.isEmpty(x1, y1)) continue;
//
//
//
//            }
//        }
        field.put(0,0, BattleField.Cell.SHIP);
        field.put(2,0, BattleField.Cell.SHIP);
        field.put(4,0, BattleField.Cell.SHIP);
        field.put(4,1, BattleField.Cell.SHIP);
        field.put(0,2, BattleField.Cell.SHIP);
        field.put(2,2, BattleField.Cell.SHIP);
        field.put(6,1, BattleField.Cell.SHIP);
        field.put(6,2, BattleField.Cell.SHIP);
        field.put(8,1, BattleField.Cell.SHIP);
        field.put(8,2, BattleField.Cell.SHIP);
        field.put(4,3, BattleField.Cell.SHIP);
        field.put(4,4, BattleField.Cell.SHIP);
        field.put(4,5, BattleField.Cell.SHIP);
        field.put(4,6, BattleField.Cell.SHIP);
        field.put(6,4, BattleField.Cell.SHIP);
        field.put(6,5, BattleField.Cell.SHIP);
        field.put(6,6, BattleField.Cell.SHIP);
        field.put(8,4, BattleField.Cell.SHIP);
        field.put(8,5, BattleField.Cell.SHIP);
        field.put(8,6, BattleField.Cell.SHIP);
        log.info("\n" + field.toString());
        return field;
    }
}
