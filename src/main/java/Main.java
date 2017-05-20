import lombok.extern.slf4j.Slf4j;
import ru.battle.actions.GenerateField;
import ru.battle.connect.Connect;
import ru.battle.model.BattleField;

/**
 * Created by onotole on 20/05/2017.
 */
@Slf4j
public class Main {
    public static void main(String[] args) {
        Connect connect = new Connect();
        connect.send("start: bot");



//        GenerateField generateField = new GenerateField();
//        BattleField bf = generateField.genField();
//        System.out.println(bf.getRandomUnknownCell());
    }
}
