package ru.battle.actions;

import lombok.extern.slf4j.Slf4j;
import ru.battle.model.BattleField;
import ru.battle.model.Game;
import ru.battle.model.Point;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static ru.battle.actions.MakeShot.ShipOrientation.HORIZONTAL;
import static ru.battle.actions.MakeShot.ShipOrientation.UNKNOWN;
import static ru.battle.actions.MakeShot.ShipOrientation.VERTICAL;

/**
 * Created by onotole on 20/05/2017.
 */
@Slf4j
public class MakeShot {
    private Game game;
    private List<Point> pointsToShoot = new ArrayList<>();
    private Random random = new Random();
    int webConfiguration = random.nextInt() % 2;

    public MakeShot(Game game) {
        this.game = game;
        initWeb(webConfiguration);
    }

    private void initWeb(int webConfiguration) {
        int i = webConfiguration;
        for (int j = 0; j < 10; j++) {
            for (int k = 0; k < 5; k++) {
                pointsToShoot.add(
                        new Point(j, 2*k + i)
                );
            }
            i = (i + 1) % 2;
        }
    }

    public Point makeShot() {
        Point p = null;
        if (game.getLastHit().length == 0) {

            while (p == null || ! game.getEnemyField().isCellType(p.getX(), p.getY(), BattleField.Cell.UNKNOWN)) {
                if (pointsToShoot.isEmpty()) {
                    initWeb((webConfiguration + 1) % 2);
                }
                int number = random.nextInt(pointsToShoot.size());
                p = pointsToShoot.remove(number);
                log.info("[sh] no last success: " + p);
            }
        } else {

            ShipOrientation orientation = detectEnemyShipOrientation();
            log.info("[sh] orientation of last success hit: " + orientation + " coord: " + Arrays.toString(game.getLastHit()));
            switch (orientation) {
                case VERTICAL:
                    p = makeVerticalShot();
                    break;
                case HORIZONTAL:
                    p = makeHorizontalShot();
                    break;
                case UNKNOWN:
                    p = makeAnyOrientationShot();
                    break;
            }
        }

        pointsToShoot.remove(p);
        log.info("[sh] make shoot to : " + p);
        return p;
    }

    public void markZoneAroundDestoyedShip() {
        ShipOrientation orientation = detectEnemyShipOrientation();
        int x = game.getLastHit()[0];
        int y = game.getLastHit()[1];

    }

    private ShipOrientation detectEnemyShipOrientation() {
        int x = game.getLastHit()[0];
        int y = game.getLastHit()[1];
        if (game.getEnemyField().isCellType(x - 1, y, BattleField.Cell.SHIP) ||
                game.getEnemyField().isCellType(x+ 1, y, BattleField.Cell.SHIP)){
            return HORIZONTAL;
        }

        if (game.getEnemyField().isCellType(x, y - 1, BattleField.Cell.SHIP) ||
                game.getEnemyField().isCellType(x, y + 1, BattleField.Cell.SHIP)){
            return VERTICAL;
        }

        return UNKNOWN;
    }

    private Point makeHorizontalShot() {
        int x = game.getLastHit()[0];
        int y = game.getLastHit()[1];
        int shift = 0;
        while (shift < 6) {
            shift++;
            if (game.getEnemyField().isCellType(x + shift, y, BattleField.Cell.UNKNOWN)) {
                break;
            }
            if (game.getEnemyField().isCellType(x - shift, y, BattleField.Cell.UNKNOWN)) {
                shift = -shift;
                break;
            }
        }
        return new Point(x + shift, y);
    }

    private Point makeVerticalShot() {
        int x = game.getLastHit()[0];
        int y = game.getLastHit()[1];
        int shift = 0;
        while (true) {
            shift++;
            if (game.getEnemyField().isCellType(x, y + shift, BattleField.Cell.UNKNOWN)) {
                break;
            }
            if (game.getEnemyField().isCellType(x, y - shift, BattleField.Cell.UNKNOWN)) {
                shift = -shift;
                break;
            }
        }

        return new Point(x, y+ shift);
    }

    private Point makeAnyOrientationShot() {
        log.info("[sh]");
        int x = game.getLastHit()[0];
        int y = game.getLastHit()[1];
        int shift = 0;
        while (shift < 6) {
            shift++;
            log.info("[sh] shift = " + shift);
            if (game.getEnemyField().isCellType(x + shift, y, BattleField.Cell.UNKNOWN)) {
                x = x + shift;
                log.info("[sh] x + shift");
                break;
            }
            if (game.getEnemyField().isCellType(x - shift, y, BattleField.Cell.UNKNOWN)) {
                log.info("[sh] x - shift");
                x = x - shift;
                break;
            }
            if (game.getEnemyField().isCellType(x, y + shift, BattleField.Cell.UNKNOWN)) {
                log.info("[sh] y + shift");
                y = y + shift;
                break;
            }
            if (game.getEnemyField().isCellType(x, y - shift, BattleField.Cell.UNKNOWN)) {
                log.info("[sh] y - shift");
                y = y - shift;
                break;
            }
        }
        return new Point(x, y);
    }

    enum ShipOrientation {
        VERTICAL, HORIZONTAL, UNKNOWN;
    }

    public static void main(String[] args) {
//        Game game = new Game();
//        game.startNewGame();
//        game.setLastHit(new int[]{3,4});
//        MakeShot makeShot = new MakeShot(game);
//        makeShot.initWeb();
//        System.out.println(makeShot.pointsToShoot);
//        System.out.println(makeShot.makeShot());
    }
}
