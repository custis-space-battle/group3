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
        return genRandomField();
    }

    public BattleField genRandomField() {
        BattleField field = new BattleField();
        field.getEmptyField();

        for (String ship : ships) {
            if (random.nextInt() % 2 == 0) {
                verticalShip(ship.length(), field);
            } else {
                horizontalShip(ship.length(), field);
            }
        }
        return field;
    }

    private void horizontalShip(int size, BattleField field) {
       boolean success = false;
            ship: while (!success) {
                int x = random.nextInt(BattleField.SIZE);
                int y = random.nextInt(BattleField.SIZE);
                for (int i = 0; i < size; i++) {
                    if (! field.isCellType(x, y + i, BattleField.Cell.EMPTY)) continue ship;
                }

                for (int i = -1; i < size + 1; i++) {
                    if (field.isCellType(x - 1, y + i, BattleField.Cell.SHIP)) continue ship;
                    if (field.isCellType(x + 1, y + i, BattleField.Cell.SHIP)) continue ship;
                }

                if (field.isCellType(x, y - 1, BattleField.Cell.SHIP)) continue;
                if (field.isCellType(x, y + 1, BattleField.Cell.SHIP)) continue;

                for (int i = 0; i < size; i++) {
                    field.put(x, y + i, BattleField.Cell.SHIP);
                }

                success = true;
            }
    }

    private void verticalShip(int size, BattleField field) {
        boolean success = false;
        ship: while (!success) {
            int x = random.nextInt(BattleField.SIZE);
            int y = random.nextInt(BattleField.SIZE);
            for (int i = 0; i < size; i++) {
                if (! field.isCellType(x+i, y, BattleField.Cell.EMPTY)) continue ship;
            }

            for (int i = -1; i < size + 1; i++) {
                if (field.isCellType(x + i, y - 1, BattleField.Cell.SHIP)) continue ship;
                if (field.isCellType(x + i, y + 1, BattleField.Cell.SHIP)) continue ship;
            }

            if (field.isCellType(x - 1, y, BattleField.Cell.SHIP)) continue;
            if (field.isCellType(x + 1, y, BattleField.Cell.SHIP)) continue;

            for (int i = 0; i < size; i++) {
                field.put(x + i, y, BattleField.Cell.SHIP);
            }

            success = true;
        }
    }

    public BattleField genHardcodedField() {
        BattleField field = new BattleField();
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
