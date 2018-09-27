/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.contribe.store.console;

import java.util.Scanner;

/**
 *
 * @author bjuhr
 */
public class ConsoleAdapter implements UserLineInterface{

    @Override
    public Scanner ask(String question) {
        System.out.println(question);
        return new Scanner(System.in);
    }

    @Override
    public void tell(String statement) {
        System.out.println(statement);
    }

    @Override
    public void yell(String statement) {
        System.err.println(statement);
    }
        
}
