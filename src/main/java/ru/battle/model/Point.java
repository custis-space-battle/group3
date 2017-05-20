package ru.battle.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by onotole on 20/05/2017.
 */
@Getter
@AllArgsConstructor
public class Point {
    private int x;
    private int y;

    public Point addOneToEach() {
        x++;
        y++;
        return this;
    }

    @Override
    public String toString() {
        return "" + x + "," + y;
    }
}
