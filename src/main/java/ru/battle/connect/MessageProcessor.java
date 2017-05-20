package ru.battle.connect;

import lombok.extern.slf4j.Slf4j;
import ru.battle.actions.MakeShot;
import ru.battle.model.BattleField;
import ru.battle.model.Game;
import ru.battle.model.Point;

/**
 * Created by onotole on 20/05/2017.
 */
@Slf4j
public class MessageProcessor {
    private Game game = new Game();
    private MakeShot makeShot = new MakeShot(game);

    public String processMessage(String inputMessage) {
        if (inputMessage.equals("prepare!")) {
            game.startNewGame();
            return game.getOurField().getConfiguration();
        }
        if (inputMessage.startsWith("winner")) {
            game = new Game();
            game.startNewGame();
            makeShot = new MakeShot(game);
            return "start: bot4";
        }


        if (inputMessage.startsWith("Warning") || inputMessage.startsWith("winner") || inputMessage.startsWith("Info")) {
            return "";
        }

        if (inputMessage.startsWith("fire result:")) {
            String[] parts = inputMessage.split(":");
            String result = parts[1];
            String[] coord = parts[2].split(",");
            int x = Integer.valueOf(coord[0].trim());
            int y = Integer.valueOf(coord[1]);
            BattleField.Cell cell;
            switch (result.trim()) {
                case "MISS":
                    cell = BattleField.Cell.EMPTY;
                    break;
                case "HIT":
                    cell = BattleField.Cell.SHIP;
                    game.setLastHit(new Point(x - 1,y - 1));
                    break;
                case "KILL":
                    cell = BattleField.Cell.SHIP;
                    game.getEnemyField().put(x-1 , y - 1, cell);
                    game.setLastHit(new Point(x - 1, y - 1));
                    makeShot.markZoneAroundDestroyedShip();
                    game.setLastHit(null);
                    break;
                case "HIT_MINE":
                    cell = BattleField.Cell.MINE;
                    break;
                default:
                    throw new RuntimeException("smth went wrong: result of hit: " + result);
            }
            game.getEnemyField().put(x-1 , y - 1, cell);
            log.info(game.showFields());
            return "";
        }

        if (inputMessage.equals("fire!")) {
            return makeShot.makeShot().addOneToEach().toString();
//           return game.getEnemyField().getRandomUnknownCell();
        }

        if (inputMessage.startsWith("incoming fire: ")) {
            String[] hit = inputMessage.split(":")[2].split(",");
            int x = Integer.valueOf(hit[0].trim());
            int y = Integer.valueOf(hit[1]);
//            game.getOurField().put(x-1, y-1, BattleField.Cell.WRECK);
            return "";
        }

        return "blah";
    }
}
