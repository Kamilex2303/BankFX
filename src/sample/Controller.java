package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class Controller{



    @FXML
    private GridPane grid1;
    @FXML
    private TextField loginfield;
    @FXML
    private TextField passfield;
    @FXML
    private Label error;





    @FXML
    public void singInButton(ActionEvent actionEvent) throws IOException  {

        Connection c = null;
        Statement stmt = null;
        String logintext = loginfield.getText();
        String passtext = passfield.getText();

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:/home/kamil/Pulpit/BankFX/bankdb");

            System.out.println("Connected");
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM User;" );
            while ( rs.next() ) {

                String name = rs.getString("name");
                String surename = rs.getString("surename");
                String login = rs.getString("login");
                String password = rs.getString("password");
                float mainbalance = rs.getInt("mainbalance");
                float savebalance = rs.getInt("savebalance");
                int accnumber = rs.getInt("accnumber");


                if(logintext.equals(login)  && passtext.equals(password)){




                    FXMLLoader loader = new FXMLLoader();

                    Parent node = loader.load(getClass().getResource("Account.fxml").openStream());

                    grid1.getChildren().setAll(node);



                    ((AccountController) loader.getController()).getNameLabel().setText(name + " " + surename);
                    ((AccountController) loader.getController()).getBalancemain().setText(String.valueOf(mainbalance));
                    ((AccountController) loader.getController()).getBalancemain().setEditable(false);
                    ((AccountController) loader.getController()).getBalanceplus().setEditable(false);
                    ((AccountController) loader.getController()).getBalanceplus().setText(String.valueOf(savebalance));
                    ((AccountController) loader.getController()).getAccnumber().setText(String.valueOf(accnumber));


                    break;
                }
                else{
                    error.setText("Złe dane ! ");
                }
            }
            rs.close();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }

    }



    public void singUpButton(ActionEvent actionEvent) throws IOException {

        Parent node = FXMLLoader.load(getClass().getResource("singup.fxml"));
        grid1.getChildren().setAll(node);

    }

}
