package ru.battle.connect;

import lombok.extern.slf4j.Slf4j;
import ru.battle.actions.GenerateField;
import ru.battle.model.BattleField;
import ru.battle.model.Game;

/**
 * Created by onotole on 20/05/2017.
 */
@Slf4j
public class MessageProcessor {
    private Game game = new Game();

    public String processMessage(String inputMessage) {
        if (inputMessage.equals("prepare!")) {
            game.startNewGame();
            return game.getOurField().getConfiguration();
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
                case "KILL":
                    cell = BattleField.Cell.SHIP;
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
           return game.getEnemyField().getRandomUnknownCell();
        }

        if (inputMessage.startsWith("incoming fire: ")) {
            String[] hit = inputMessage.split(":")[2].split(",");
            int x = Integer.valueOf(hit[0].trim());
            int y = Integer.valueOf(hit[1]);
            game.getOurField().put(x-1, y-1, BattleField.Cell.WRECK);
            return "";
        }

        return "blah";
    }
}
