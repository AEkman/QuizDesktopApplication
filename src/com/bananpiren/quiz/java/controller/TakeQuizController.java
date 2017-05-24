package com.bananpiren.quiz.java.controller;

import com.bananpiren.quiz.Entity.TakeQuiz;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;


public class TakeQuizController {

    @FXML
    private Label quizNameHeader;

    VBox createQuizQuestions(ArrayList<TakeQuiz> takeQuizList) {
        // length of the list divided with the number of questions
        int len = takeQuizList.size()/4;

        String[] questionName = new String[len];
        Label[] questionLabel = new Label[len];
        String questionType = "";

        String[] answer = new String[takeQuizList.size()];
        Label[] answerLabel = new Label[takeQuizList.size()];
        CheckBox[] answerCheckbox = new CheckBox[takeQuizList.size()];

        RadioButton[] answerButton = new RadioButton[takeQuizList.size()];

        ToggleGroup[] toggleGroups = new ToggleGroup[len]; // set with the number of questions


        HBox[] answerBox = new HBox[takeQuizList.size()];


        VBox questionBox = new VBox();

        int incQuest = 0;
        int incAnswer = 0;
        int answerNo = 4;

        // loop through the questions
        for (int i = 0; i < len; i++) {
            // increment the question with the number of answers and get the question
            incQuest = i * answerNo;

            questionName[i] = takeQuizList.get(incQuest).getQuestion();
            questionType = takeQuizList.get(incQuest).getQuestionType();

            questionLabel[i] = new Label(questionName[i]);
            questionBox.getChildren().add(questionLabel[i]);
            questionBox.setSpacing(5);

            // loop through the answers
            for (int j = 0; j < answerNo; j++) {
                incAnswer = incQuest + j;

                answer[j] = takeQuizList.get(incAnswer).getAnswer();

                answerLabel[j] = new Label(answer[j]);
                answerBox[j] = new HBox();

                answerBox[j].getChildren().add(answerLabel[j]);
                answerBox[j].setSpacing(5);

                // checks what kind of question
                if(questionType.equals("multiple")) {
                    answerCheckbox[j] = new CheckBox();
                    answerBox[j].getChildren().add(answerCheckbox[j]);
                } else {
                    ToggleGroup radioGroup = new ToggleGroup();

                    answerButton[j] = new RadioButton();
                    answerButton[j].setToggleGroup(radioGroup);

                    answerBox[j].getChildren().add(answerButton[j]);
                }
//                toggleGroups = new ToggleGroup(); Lägg till denna
//                // if you are on the last run with radiobuttons
//                if(j == answerNo-1 && !questionType.equals("multiple")) {
//                    answer1Button.setToggleGroup(radioGroup);
//                    answer2Button.setToggleGroup(radioGroup);
//                    answer3Button.setToggleGroup(radioGroup);
//                    answer4Button.setToggleGroup(radioGroup);
//                }

                questionBox.getChildren().add(answerBox[j]);
            }
        }

        return questionBox;
    }

}
