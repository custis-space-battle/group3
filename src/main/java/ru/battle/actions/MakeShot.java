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
        borderInitialWeb();
    }

    private void borderInitialWeb() {
        pointsToShoot.add(new Point(0, 0));
        for (int i = 1; i < BattleField.SIZE / 2; i++) {
            pointsToShoot.add(new Point(0, 2*i));
            pointsToShoot.add(new Point(9, 2*i));
            pointsToShoot.add(new Point(2*i, 0));
            pointsToShoot.add(new Point(2*i, 9));
        }
    }

    public void setPointsToShoot(List<Point> pointsToShoot) {
        this.pointsToShoot = pointsToShoot;
    }

    private void initWeb(int webConfiguration) {
        log.info("init web with config: " + webConfiguration);
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
        if (game.getLastHit() == null) {

            while (p == null || ! game.getEnemyField().isCellType(p.getX(), p.getY(), BattleField.Cell.UNKNOWN)) {
                if (pointsToShoot.isEmpty()) {
                    initWeb((webConfiguration++) % 2);
                }
                int number = random.nextInt(pointsToShoot.size());
                p = pointsToShoot.remove(number);
                log.info("[sh] no last success: " + p);
            }
        } else {

            ShipOrientation orientation = detectEnemyShipOrientation();
            log.info("[sh] orientation of last success hit: " + orientation + " coord: " + game.getLastHit());
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

    public void markZoneAroundDestroyedShip() {
        ShipOrientation orientation = detectEnemyShipOrientation();
        int x = game.getLastHit().getX();
        int y = game.getLastHit().getY();
        log.info("[mk] start marking zone after destroy, x = " + x + " y = " + y + " orient: " + orientation);

        if (orientation == HORIZONTAL) {
            Point bottom = findLeftOfHorizontalShip(game.getLastHit());
            log.info("[mk] found left point: " + bottom);
            x = bottom.getX();
            y = bottom.getY();
            //    X
            //    X
            //--> X
            //
            game.getEnemyField().put(x - 1, y - 1, BattleField.Cell.EMPTY);
            game.getEnemyField().put(x - 1, y, BattleField.Cell.EMPTY);
            game.getEnemyField().put(x - 1, y + 1, BattleField.Cell.EMPTY);

//            game.getEnemyField().put(x - 1, y + 1, BattleField.Cell.EMPTY);
//            game.getEnemyField().put(x, y + 1, BattleField.Cell.EMPTY);
//            game.getEnemyField().put(x + 1, y + 1, BattleField.Cell.EMPTY);

            for (int i = 0; i < 6; i++) {
                game.getEnemyField().put(x + i, y - 1, BattleField.Cell.EMPTY);
                game.getEnemyField().put(x + i, y + 1, BattleField.Cell.EMPTY);
                if (! game.getEnemyField().isCellType(x + i, y, BattleField.Cell.SHIP)) {
                    game.getEnemyField().put(x + i, y, BattleField.Cell.EMPTY);
                    break;
                }
            }
        } else if (orientation == VERTICAL) {
            Point left = findBottomOfVerticalShip(game.getLastHit());
            log.info("[mk] found bottom point: " + left);

            x = left.getX();
            y = left.getY();

            game.getEnemyField().put(x - 1, y -1, BattleField.Cell.EMPTY);
            game.getEnemyField().put(x , y - 1, BattleField.Cell.EMPTY);
            game.getEnemyField().put(x + 1, y - 1, BattleField.Cell.EMPTY);

            for (int i = 0; i < 6; i++) {
                game.getEnemyField().put(x - 1, y + i, BattleField.Cell.EMPTY);
                game.getEnemyField().put(x + 1, y + i, BattleField.Cell.EMPTY);
                if (! game.getEnemyField().isCellType(x, y + i, BattleField.Cell.SHIP)) {
                    game.getEnemyField().put(x, y + i, BattleField.Cell.EMPTY);
                    break;
                }
            }
        } else {
            game.getEnemyField().put( x + 1, y + 1, BattleField.Cell.EMPTY);
            game.getEnemyField().put( x, y + 1, BattleField.Cell.EMPTY);
            game.getEnemyField().put( x - 1, y + 1, BattleField.Cell.EMPTY);
            game.getEnemyField().put( x + 1, y, BattleField.Cell.EMPTY);
            game.getEnemyField().put( x - 1,  y, BattleField.Cell.EMPTY);
            game.getEnemyField().put( x + 1, y - 1, BattleField.Cell.EMPTY);
            game.getEnemyField().put( x, y - 1, BattleField.Cell.EMPTY);
            game.getEnemyField().put( x - 1, y - 1, BattleField.Cell.EMPTY);
        }
        log.info("[mk] finish marking zone after destroy, x = " + x + " y = " + y + " orient: " + orientation);

    }

    private Point findLeftOfHorizontalShip(Point p) {
        //
        //  --> XXX
        //
        for (int i = 0; i < 4; i++) {
            if (! game.getEnemyField().isCellType(p.getX() - i - 1, p.getY(), BattleField.Cell.SHIP)) {
                return new Point(p.getX() - i, p.getY());
            }
        }
        throw new RuntimeException("[mk] Couldn't find left of ship!");
    }

    private Point findBottomOfVerticalShip(Point p) {
        for (int i = 0; i < 4; i++) {
            if (! game.getEnemyField().isCellType(p.getX(), p.getY() - i - 1, BattleField.Cell.SHIP)) {
                return new Point(p.getX(), p.getY() - i);
            }
        }
        throw new RuntimeException("[mk] Couldn't find bottom of ship!");
    }

    private ShipOrientation detectEnemyShipOrientation() {
        int x = game.getLastHit().getX();
        int y = game.getLastHit().getY();
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
        boolean downOk = true;
        int x = game.getLastHit().getX();
        int y = game.getLastHit().getY();
        int shift = 0;
        while (shift < 6) {
            shift++;
            if (game.getEnemyField().isCellType(x + shift, y, BattleField.Cell.EMPTY)) {
                downOk = false;
            }
            if ( downOk && game.getEnemyField().isCellType(x + shift, y, BattleField.Cell.UNKNOWN)) {
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
        boolean rightOk = true;
        int x = game.getLastHit().getX();
        int y = game.getLastHit().getY();
        int shift = 0;
        while (true) {
            shift++;
            if (game.getEnemyField().isCellType(x, y + shift, BattleField.Cell.EMPTY)) {
                rightOk = false;
            }
            if (rightOk && game.getEnemyField().isCellType(x, y + shift, BattleField.Cell.UNKNOWN)) {
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
        int x = game.getLastHit().getX();
        int y = game.getLastHit().getY();
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
