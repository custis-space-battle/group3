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
        return genIdealField();
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

    private void genIdealPrepare1(BattleField field) {
        field.put(0,0, BattleField.Cell.SHIP);
        field.put(1,0, BattleField.Cell.SHIP);
        field.put(2,0, BattleField.Cell.SHIP);
        field.put(9,0, BattleField.Cell.SHIP);
        field.put(9,1, BattleField.Cell.SHIP);
        field.put(9,2, BattleField.Cell.SHIP);
        field.put(9,6, BattleField.Cell.SHIP);
        field.put(9,7, BattleField.Cell.SHIP);
        field.put(9,8, BattleField.Cell.SHIP);
        field.put(9,9, BattleField.Cell.SHIP);
        field.put(0,8, BattleField.Cell.SHIP);
        field.put(0,9, BattleField.Cell.SHIP);
        field.put(3,9, BattleField.Cell.SHIP);
        field.put(4,9, BattleField.Cell.SHIP);
        field.put(6,9, BattleField.Cell.SHIP);
        field.put(7,9, BattleField.Cell.SHIP);
    }

    private void genIdealPrepare2(BattleField field) {
        field.put(0,4, BattleField.Cell.SHIP);
        field.put(0,5, BattleField.Cell.SHIP);
        field.put(0,6, BattleField.Cell.SHIP);
        field.put(2,0, BattleField.Cell.SHIP);
        field.put(2,1, BattleField.Cell.SHIP);
        field.put(5,0, BattleField.Cell.SHIP);
        field.put(6,0, BattleField.Cell.SHIP);
        field.put(8,4, BattleField.Cell.SHIP);
        field.put(9,4, BattleField.Cell.SHIP);
        field.put(7,7, BattleField.Cell.SHIP);
        field.put(8,7, BattleField.Cell.SHIP);
        field.put(9,7, BattleField.Cell.SHIP);
        field.put(5,9, BattleField.Cell.SHIP);
        field.put(6,9, BattleField.Cell.SHIP);
        field.put(7,9, BattleField.Cell.SHIP);
        field.put(8,9, BattleField.Cell.SHIP);
    }


    private void genIdealPrepare3(BattleField field) {
        field.put(0, 2, BattleField.Cell.SHIP);
        field.put(1, 2, BattleField.Cell.SHIP);
        field.put(0, 5, BattleField.Cell.SHIP);
        field.put(1, 5, BattleField.Cell.SHIP);
        field.put(0,8, BattleField.Cell.SHIP);
        field.put(1,8, BattleField.Cell.SHIP);
        field.put(2,8, BattleField.Cell.SHIP);
        field.put(4,0, BattleField.Cell.SHIP);
        field.put(4,1, BattleField.Cell.SHIP);
        field.put(9,0, BattleField.Cell.SHIP);
        field.put(9,1, BattleField.Cell.SHIP);
        field.put(9,2, BattleField.Cell.SHIP);
        field.put(9,3, BattleField.Cell.SHIP);
        field.put(9,5, BattleField.Cell.SHIP);
        field.put(8,5, BattleField.Cell.SHIP);
        field.put(7,5, BattleField.Cell.SHIP);
    }

    public BattleField genIdealField() {
        BattleField field = new BattleField();
        field.getEmptyField();
        switch (random.nextInt(3)) {
            case 0:
                genIdealPrepare1(field);
                break;
            case 1:
                genIdealPrepare2(field);
                break;
            case 2:
                genIdealPrepare3(field);
                break;
        }
        for (int i = 0; i < 4; i++) {
            putUniShip(field);
        }
        return field;
    }

    private void putUniShip(BattleField field) {
        boolean success = false;
        while (! success) {
            int x = random.nextInt(BattleField.SIZE - 2) + 1;
            int y = random.nextInt(BattleField.SIZE - 2) + 1;
            if (field.isExistCellInAround(x, y, BattleField.Cell.SHIP)) continue;
            field.put(x, y, BattleField.Cell.SHIP);
            success = true;
        }
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

    public static void main(String[] args) {
        GenerateField generateField = new GenerateField();

        System.out.println(generateField.genIdealField());
    }
}
