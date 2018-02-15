package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class AccountController implements Initializable {

    @FXML
    private AnchorPane accountgrid;
    @FXML
    private Label namelabel;
    @FXML
    private TextArea balancemain;
    @FXML
    private TextArea balanceplus;

    public Label getNamelabel() {
        return namelabel;
    }

    public void setNamelabel(Label namelabel) {
        this.namelabel = namelabel;
    }

    public Label getAccnumber() {
        return accnumber;
    }

    public void setAccnumber(Label accnumber) {
        this.accnumber = accnumber;
    }

    @FXML
    private Label accnumber;

    public TextArea getBalancemain() {
        return balancemain;
    }

    public void setBalancemain(TextArea balancemain) {
        this.balancemain = balancemain;
    }

    public TextArea getBalanceplus() {
        return balanceplus;
    }

    public void setBalanceplus(TextArea balanceplus) {
        this.balanceplus = balanceplus;
    }

    public AnchorPane getAccountgrid() {
        return accountgrid;
    }

    public void setAccountgrid(AnchorPane accountgrid) {
        this.accountgrid = accountgrid;
    }

    public Label getNameLabel() {
        return namelabel;
    }

    public void setNamelable(Label namelable) {
        this.namelabel = namelabel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void back(ActionEvent actionEvent) throws IOException {
        Parent node = FXMLLoader.load(getClass().getResource("sample.fxml"));
        accountgrid.getChildren().setAll(node);
    }

    public void transaction(ActionEvent actionEvent) throws IOException {
        FXMLLoader f = new FXMLLoader();
        Parent node = f.load(getClass().getResource("transactionView.fxml").openStream());
        accountgrid.getChildren().setAll(node);
        ((TransactionController) f.getController()).getName().setText(namelabel.getText());
        ((TransactionController) f.getController()).getMainacc().setText(balancemain.getText());
        ((TransactionController) f.getController()).getSaveacc().setText(balanceplus.getText());
        ((TransactionController) f.getController()).getAccnumber().setText(String.valueOf(accnumber.getText()));
    }

    public void history(ActionEvent actionEvent) throws IOException {
        FXMLLoader f = new FXMLLoader();
        Parent node = f.load(getClass().getResource("historyView.fxml").openStream());
        accountgrid.getChildren().setAll(node);
        ((HistoryController) f.getController()).getName().setText(namelabel.getText());
        ((HistoryController) f.getController()).getMainacc().setText(balancemain.getText());
        ((HistoryController) f.getController()).getSaveacc().setText(balanceplus.getText());
        ((HistoryController) f.getController()).getAccnumber().setText(String.valueOf(accnumber.getText()));
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
                String history = rs.getString("History");

                if(Integer.parseInt(getAccnumber().getText()) == accnumber){
                    ((HistoryController) f.getController()).getList().setText(history);
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

    public void saving(ActionEvent actionEvent) throws IOException {
        FXMLLoader f = new FXMLLoader();
        Parent node = f.load(getClass().getResource("savingView.fxml").openStream());
        accountgrid.getChildren().setAll(node);
        ((SavingController) f.getController()).getName().setText(namelabel.getText());
        ((SavingController) f.getController()).getMainacc().setText(balancemain.getText());
        ((SavingController) f.getController()).getSaveacc().setText(balanceplus.getText());
        ((SavingController) f.getController()).getAccnumber().setText(String.valueOf(accnumber.getText()));
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
                int check = rs.getInt("save");

                if(Integer.parseInt(getAccnumber().getText()) == accnumber ){
                    if(check==1) {
                        ((SavingController) f.getController()).getOnoff().setText("On");
                    }
                    else{
                        ((SavingController) f.getController()).getOnoff().setText("Off");
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

    public void contact(ActionEvent actionEvent) throws IOException {
        FXMLLoader f = new FXMLLoader();
        Parent node = f.load(getClass().getResource("contactView.fxml").openStream());
        accountgrid.getChildren().setAll(node);
        ((ContactController) f.getController()).getName().setText(namelabel.getText());
        ((ContactController) f.getController()).getMainacc().setText(balancemain.getText());
        ((ContactController) f.getController()).getSaveacc().setText(balanceplus.getText());
        ((ContactController) f.getController()).getAccnumber().setText(String.valueOf(accnumber.getText()));
    }
}
