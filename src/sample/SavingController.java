package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class SavingController {
    @FXML
    private AnchorPane panel;
    @FXML
    private Label mainacc;
    @FXML
    private Label name;
    @FXML
    private Label saveacc;
    @FXML
    private Button onoff;
    @FXML
    private Button transfer;

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

    public Button getOnoff() {
        return onoff;
    }

    public void setOnoff(Button onoff) {
        this.onoff = onoff;
    }

    public Button getTransfer() {
        return transfer;
    }

    public void setTransfer(Button transfer) {
        this.transfer = transfer;
    }

    public void onoff(ActionEvent actionEvent) {
        if(onoff.getText().equals("On")){
            onoff.setText("Off");
        }
        else{
            onoff.setText("On");
        }
        Connection c = null;
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:/home/kamil/Pulpit/BankFX/bankdb");

            System.out.println("Connected");
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM User;" );
            while ( rs.next() ) {

                int accnumber = rs.getInt("accnumber");
                int checkx= rs.getInt("save");

                if(accnumber == Integer.parseInt(getAccnumber().getText())){
                    if(checkx==1){
                        String sql = "update User set save=0 where accnumber = "+Integer.parseInt(getAccnumber().getText())+";";
                        stmt.executeUpdate(sql);
                    }
                    else{
                        String sql = "update User set save=1 where accnumber = "+Integer.parseInt(getAccnumber().getText())+";";
                        stmt.executeUpdate(sql);
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

    }

    public void transfer(ActionEvent actionEvent) {

        Connection c = null;
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:/home/kamil/Pulpit/BankFX/bankdb");

            System.out.println("Connected");
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM User;" );
            while ( rs.next() ) {

                int accnumber = rs.getInt("accnumber");
                float mainacc = rs.getInt("mainbalance");
                float saveacc = rs.getInt("savebalance");

                if(accnumber == Integer.parseInt(getAccnumber().getText())){
                    String sql = "update User set mainbalance=mainbalance+savebalance where accnumber="+Integer.parseInt(getAccnumber().getText())+";";
                    stmt.executeUpdate(sql);
                    sql = "update User set savebalance=0 where accnumber="+Integer.parseInt(getAccnumber().getText())+";";
                    stmt.executeUpdate(sql);
                }

                getMainacc().setText(String.valueOf(Float.parseFloat(getMainacc().getText())+Float.parseFloat(getSaveacc().getText())));
                getSaveacc().setText("0");


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
