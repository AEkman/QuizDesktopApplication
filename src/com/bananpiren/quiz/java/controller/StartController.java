package com.bananpiren.quiz.java.controller;

import com.bananpiren.quiz.Entity.Quiz;
import com.bananpiren.quiz.Entity.TakeQuiz;
import com.bananpiren.quiz.Services.QuizService;
import com.bananpiren.quiz.java.view.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;

public class StartController {

    private ObservableList<Quiz> data = FXCollections.observableArrayList();
    //    private ObservableList<VBox> vbox = FXCollections.observableArrayList();
    private ArrayList<TakeQuiz> takeQuizList = new ArrayList<>();

    private VBox vbox;

    private QuizService quizService = new QuizService();
    public int currentQuizId;

    @FXML
    private TableColumn<Quiz, Integer> quizIdColumn;

    @FXML
    private TableColumn<Quiz, String> quizNameColumn;

    @FXML
    private TableView<Quiz> quizTableView;

    @FXML
    private TableColumn<Quiz, String> quizEndDateColumn;

    @FXML
    private Button takeQuizButton;


    public StartController() {
        data.addAll(quizService.findAllQuiz());
    }

    @FXML
    private void initialize() {
        // Setting data to right column "cellvalue"
        quizIdColumn.setCellValueFactory(new PropertyValueFactory<Quiz, Integer>("quizId"));
        quizNameColumn.setCellValueFactory(new PropertyValueFactory<Quiz, String>("quizName"));
        quizEndDateColumn.setCellValueFactory(new PropertyValueFactory<Quiz, String>("quizEndDate"));
        quizTableView.setItems(data);

        takeQuizButton.setDisable(true);
        quizTableView.getSelectionModel().selectedIndexProperty().addListener(y -> {
            takeQuizButton.setDisable(false);
        });


        takeQuizButton.setOnAction((ActionEvent e) -> {

            currentQuizId = quizTableView.getSelectionModel().selectedItemProperty().getValue().getQuizId();

            TakeQuizController takeQuizController = new TakeQuizController();
            VBox newCoolVbox = new VBox();

            // TODO: Hämta allt i quiz med query

            // get the list of the current quiz
            takeQuizList = quizService.currentQuiz(currentQuizId);

            int count = 0;
            for (TakeQuiz t : takeQuizList) {
                count++;
                System.out.println("\n" + "QUIZNAMEdd " + t.getQuizName() + " count " + count);
                System.out.println("QUIZQUESTION " + t.getQuestion() + " count " + count);
                System.out.println("QUIZANSWER " + t.getAnswer() + " count " + count);
                System.out.println("QUESTIONTYPE " + t.getQuestionType() + " count " + count);
            }

            // displaying the question and answers on vbox
            vbox = takeQuizController.createQuizQuestions(takeQuizList);
            newCoolVbox.getChildren().addAll(vbox);

            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(Main.class.getResource("TakeQuiz.fxml"));
                BorderPane takeQuiz = loader.load();
                takeQuiz.setCenter(newCoolVbox);
                Main.mainLayout.setCenter(takeQuiz);
            } catch (IOException f) {
                System.out.println("Couldn't load TakeQuiz.fxml: " + f);
            }
        });
    }
}
