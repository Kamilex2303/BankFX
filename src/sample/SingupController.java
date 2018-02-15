package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Random;


public class SingupController {

    @FXML
    private AnchorPane singUpPane;
    @FXML
    private TextField name1;
    @FXML
    private TextField surename1;
    @FXML
    private TextField login1;
    @FXML
    private TextField password1;
    @FXML
    private TextField password2;


    public void singUpFinal(ActionEvent actionEvent) throws IOException{
        Connection c = null;
        Statement stmt = null;
        String name = name1.getText();
        String surename = surename1.getText();
        String login = login1.getText();
        String password = password1.getText();
        String passwordr = password2.getText();

        if(password.equals(passwordr)) {
            try {
                Class.forName("org.sqlite.JDBC");
                c = DriverManager.getConnection("jdbc:sqlite:/home/kamil/Pulpit/BankFX/bankdb");
                c.setAutoCommit(false);
                stmt = c.createStatement();
                Random generator = new Random();
                int randomn = generator.nextInt(900000)+100000;
                String sql = "insert into user (name,login,password,surename,mainbalance,savebalance,accnumber,History,save) values ('" + name + "' , '" + login + "' , '" + password + "' , '" + surename + "' , 0 , 0 , "+randomn+", 'empty' , 0 );";
                stmt.executeUpdate(sql);

                stmt.close();
                c.commit();
                c.close();

            } catch (Exception e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                System.exit(0);


            }
        }
        else{
            password1.clear();
            password2.clear();
        }
    }

    public void backButton(ActionEvent actionEvent) throws IOException {
        Parent node = FXMLLoader.load(getClass().getResource("sample.fxml"));
        singUpPane.getChildren().setAll(node);
    }
}
