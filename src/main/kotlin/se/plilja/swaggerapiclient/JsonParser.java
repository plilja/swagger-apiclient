package se.plilja.swaggerapiclient;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.Map;
import java.util.function.Function;

public final class JsonParser {
    private final Function<String, Object> parser;

    private JsonParser(Function<String, Object> parser) {
        this.parser = parser;
    }

    public static JsonParser newInstance() {
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
        try {
            engine.eval("function parse(s) {" +
                    "return JSON.parse(s);" +
                    "}");
        } catch (ScriptException e) {
            throw new RuntimeException("Error while compiling JsonParser", e);
        }
        Invocable inv = (Invocable) engine;
        Function<String, Object> parser = s -> {
            try {
                return inv.invokeFunction("parse", s);
            } catch (ScriptException e) {
                throw new RuntimeException("Error while parsing JSON", e);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException("Error while parsing JSON", e);
            }
        };
        return new JsonParser(parser);
    }

    public Map<String, Object> parse(String jsonString) {
        return (Map<String, Object>) parser.apply(jsonString);
    }
}
