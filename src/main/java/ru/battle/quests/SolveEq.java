package ru.battle.quests;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Created by onotole on 20/05/2017.
 */
public class SolveEq {
    private String rawEq = "1*x^2-3*x^1+2=0";
    private String[] eqParts;
    private int max = 3; //1_000_000;
    private ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
    private ScriptEngine engine = scriptEngineManager.getEngineByName("js");

    public SolveEq(String rawEq) {
        this.rawEq = rawEq;
    }

    public int solve() {
        for (int i = 0; i < max; i++) {
            System.out.println(i);
            String eq = rawEq.replace("x","" + i);
            System.out.println(eq + " : " + eval(eq));
            if (Objects.equals(eval(eq), "0"))  {
                return i;
            }
            eq = rawEq.replace("x","" + -i);
            if (Objects.equals(eval(eq), "0"))  {
                return i;
            }
        }
        return 0;
    }

    @SneakyThrows
    private String eval(String eq) {
        return engine.eval(eq).toString();
    }

    public static void main(String[] args) {
        SolveEq s = new SolveEq("1*x^2-3*x^1+2");
        LocalDateTime dt = LocalDateTime.now();
        System.out.println(s.solve());
        System.out.println("Time: " + Duration.between(dt, LocalDateTime.now()));
    }

}
