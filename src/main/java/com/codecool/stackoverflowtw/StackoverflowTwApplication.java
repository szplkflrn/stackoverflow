package com.codecool.stackoverflowtw;

import com.codecool.stackoverflowtw.controller.ServerConnector;
import com.codecool.stackoverflowtw.dao.QuestionsDAO;
import com.codecool.stackoverflowtw.dao.QuestionsDaoJdbc;
import com.codecool.stackoverflowtw.logger.ConsoleLogger;
import com.codecool.stackoverflowtw.logger.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class StackoverflowTwApplication {

    public static void main(String[] args) {
        SpringApplication.run(StackoverflowTwApplication.class, args);
    }

    @Bean
    public QuestionsDAO questionsDAO() {
        ServerConnector serverConnector = new ServerConnector();
        Logger consoleLogger = new ConsoleLogger();


        return new QuestionsDaoJdbc(serverConnector, consoleLogger);
    }
}
