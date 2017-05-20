import ru.battle.actions.GenerateField;
import ru.battle.connect.Connect;
import ru.battle.model.BattleField;

/**
 * Created by onotole on 20/05/2017.
 */
public class Main {
    public static void main(String[] args) {
        GenerateField generateField = new GenerateField();
        BattleField bf = generateField.genField();
        System.out.println(bf);
        System.out.println(bf.getConfiguration());
//        Connect connect = new Connect();
//        connect.send("start: bot");

    }
}
