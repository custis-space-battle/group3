package ru.battle.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by onotole on 20/05/2017.
 */
public class BattleField {
    private static final int SIZE = 10;
    private List<List<Cell>> field;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                sb.append(field.get(i).get(j).symbol);
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public BattleField() {
        ArrayList<Cell> list;
        field = new ArrayList<>();
        for (int i = 0; i < SIZE; i++) {
            list = new ArrayList<>();

            for (int j = 0; j < SIZE; j++) {
                list.add(Cell.UNKNOWN);
            }
            field.add(list);
        }
    }

    public void put(int x, int y, Cell cell) {
        field.get(x).set(y, cell);
    }

    enum Cell {
        EMPTY(" "), SHIP("#"), WRECK("*"), MINE("@"), UNKNOWN(".");

        private String symbol;
        Cell(String symbol) {
            this.symbol = symbol;
        }
    }

}
