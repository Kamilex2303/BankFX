package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.*;


public class ContactController {
    @FXML
    private AnchorPane anchorPaneContact;
    @FXML
    private Label mainacc;
    @FXML
    private Label name;
    @FXML
    private Label saveacc;

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

        anchorPaneContact.getChildren().setAll(node);



        ((AccountController) loader.getController()).getNameLabel().setText(getName().getText());
        ((AccountController) loader.getController()).getBalancemain().setText(getMainacc().getText());
        ((AccountController) loader.getController()).getBalanceplus().setText(getSaveacc().getText());
        ((AccountController) loader.getController()).getAccnumber().setText(String.valueOf(accnumber.getText()));

    }
}
