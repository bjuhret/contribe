package se.contribe.store.console;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Consumer;

/**
 * Mock adapter allowing listening in on handler communication
 * @author bjuhr
 */
public class MockCLIAdapter implements UserLineInterface {
    
    private final Map<String,String> questionsToAnswers = new HashMap<>();
    private Consumer<String> tellConsumer;
    private Consumer<String> yellConsumer;
    
    public void registerAnswer(String answer, String forQuestion){
        questionsToAnswers.put(forQuestion, answer);
    }

    public void setTellConsumer(Consumer<String> tellConsumer) {
        this.tellConsumer = tellConsumer;
    }

    public void setYellConsumer(Consumer<String> yellConsumer) {
        this.yellConsumer = yellConsumer;
    }

    @Override
    public Scanner ask(String question) {
        if (!questionsToAnswers.containsKey(question)) {
            throw new IllegalStateException("No answer for question " + question);
        }
        return new Scanner(questionsToAnswers.get(question));

    }

    @Override
    public void tell(String statement) {
        if(tellConsumer != null){
            tellConsumer.accept(statement);
        }
    }

    @Override
    public void yell(String statement) {
        if(yellConsumer != null){
            yellConsumer.accept(statement);
        }
    }
    
    
    
}
