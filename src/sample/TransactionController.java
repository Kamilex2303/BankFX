package sample;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.*;

public class TransactionController {
    @FXML
    private AnchorPane panel;
    @FXML
    private Label mainacc;
    @FXML
    private Label name;
    @FXML
    private Label saveacc;
    @FXML
    private TextField amount;
    @FXML
    private TextField number;



    public Label getAccnumber() {
        return accnumber;
    }

    public void setAccnumber(Label accnumber) {
        this.accnumber = accnumber;
    }

    @FXML
    private Label accnumber;


    public Label getMainacc() {
        return mainacc;
    }

    public void setMainacc(Label mainacc) {
        this.mainacc = mainacc;
    }

    public Label getName() {
        return name;
    }

    public void setName(Label name) {
        this.name = name;
    }

    public Label getSaveacc() {
        return saveacc;
    }

    public void setSaveacc(Label saveacc) {
        this.saveacc = saveacc;
    }

    public void back(ActionEvent actionEvent) throws IOException {


        FXMLLoader loader = new FXMLLoader();

        Parent node = loader.load(getClass().getResource("Account.fxml").openStream());

        panel.getChildren().setAll(node);



        ((AccountController) loader.getController()).getNameLabel().setText(getName().getText());
        ((AccountController) loader.getController()).getBalancemain().setText(getMainacc().getText());
        ((AccountController) loader.getController()).getBalanceplus().setText(getSaveacc().getText());
        ((AccountController) loader.getController()).getAccnumber().setText(String.valueOf(accnumber.getText()));

    }

    public TextField getAmount() {
        return amount;
    }

    public void setAmount(TextField amount) {
        this.amount = amount;
    }

    public TextField getNumber() {
        return number;
    }

    public void setNumber(TextField number) {
        this.number = number;
    }

    public void send(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {

        Connection c = null;
        Statement stmt = null;
        float amount = Float.parseFloat(getAmount().getText());
        int number = Integer.parseInt(getNumber().getText());

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:/home/kamil/Pulpit/BankFX/bankdb");

            System.out.println("Connected");
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM User;" );
            while ( rs.next() ) {

                int accnumber = rs.getInt("accnumber");
                String history = rs.getString("History");
                int check = rs.getInt("save");



                if (accnumber == number) {
                    if (Float.parseFloat(getMainacc().getText()) >= amount) {
                        String sql = "update User set mainbalance = mainbalance+" + amount + " where accnumber=" + number + ";";
                        stmt.executeUpdate(sql);
                        sql = "update User set mainbalance = mainbalance-" + amount + " where accnumber=" + Integer.parseInt(getAccnumber().getText()) + ";";
                        stmt.executeUpdate(sql);

                        getMainacc().setText(String.valueOf(Float.parseFloat(getMainacc().getText()) - amount));

                    }
                    history = "+" + amount + "<--" + getAccnumber().getText() + "\n" + history;
                    System.out.println(history);
                    String sql = "update User set History = '" + history + "' where accnumber=" + number + "";
                    stmt.executeUpdate(sql);

                }
            }
            rs.close();
            stmt.close();
            c.close();

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }


        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:/home/kamil/Pulpit/BankFX/bankdb");

            System.out.println("Connected");
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM User;" );
            while ( rs.next() ) {

                int accnumber = rs.getInt("accnumber");
                String history = rs.getString("History");
                int check = rs.getInt("save");
                float saveacc = rs.getFloat("savebalance");

                if(accnumber == Integer.parseInt(getAccnumber().getText())) {
                    history = "-" + amount + "-->" + number + "\n" + history;
                    System.out.println(history);
                    String sql = "update User set History = '" + history + "' where accnumber=" + Integer.parseInt(getAccnumber().getText()) + "";
                    stmt.executeUpdate(sql);
                    if(check==1){
                        System.out.println("tak");
                        sql="update User set savebalance = savebalance+(0.01*"+amount+") WHERE accnumber = " + Integer.parseInt(getAccnumber().getText()) + ";";
                        stmt.executeUpdate(sql);;
                        sql="UPDATE User set mainbalance = mainbalance-(0.01*"+amount+") WHERE accnumber = " + Integer.parseInt(getAccnumber().getText()) + ";";
                        stmt.executeUpdate(sql);
                    }
                    else{
                        System.out.println("nie");
                    }
                }



            }
            rs.close();
            stmt.close();
            c.close();


        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:/home/kamil/Pulpit/BankFX/bankdb");

            System.out.println("Connected");
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM User;" );
            while ( rs.next() ) {

                int accnumber = rs.getInt("accnumber");
                String history = rs.getString("History");
                int check = rs.getInt("save");
                float saveacc = rs.getFloat("savebalance");
                float mainacc = rs.getFloat("mainbalance");


                if(accnumber == Integer.parseInt(getAccnumber().getText())) {
                    getMainacc().setText(String.valueOf(mainacc));
                    getSaveacc().setText(String.valueOf(saveacc));
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
}
