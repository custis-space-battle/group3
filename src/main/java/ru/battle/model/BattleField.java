package ru.battle.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by onotole on 20/05/2017.
 */
public class BattleField {
    public static final int SIZE = 10;
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
        field = new ArrayList<>();
        getEmptyField();
    }

    public void getEmptyField() {
        fillFieldWith(Cell.EMPTY);
    }

    public void getUnknownField() {
        fillFieldWith(Cell.UNKNOWN);
    }

    public void put(int x, int y, Cell cell) {
        field.get(x).set(y, cell);
    }

    public String getConfiguration() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (field.get(i).get(j) == Cell.SHIP) {
                    sb.append(i+1).append(",").append(j+1).append(";");
                }
            }
        }
        return sb.toString();
    }

    public boolean isEmpty(int x, int y) {
        if (x < 0 && y < 0 && x >= SIZE && y >= SIZE) {
            return false;
        }

        return field.get(x).get(y) == Cell.EMPTY;
    }

    private void fillFieldWith(Cell cell) {
        field = new ArrayList<>();
        ArrayList<Cell> list;
        for (int i = 0; i < SIZE; i++) {
            list = new ArrayList<>();

            for (int j = 0; j < SIZE; j++) {
                list.add(Cell.UNKNOWN);
            }
            field.add(list);
        }
    }


    public enum Cell {
        EMPTY(" "), SHIP("#"), WRECK("*"), MINE("@"), UNKNOWN(".");

        private String symbol;
        Cell(String symbol) {
            this.symbol = symbol;
        }
    }

}
