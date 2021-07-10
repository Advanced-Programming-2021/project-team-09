package view.graphics;

import controller.database.CSVInfoGetter;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.io.File;
import java.io.IOException;

public class ImportExportController {
    @FXML
    private TextField importSearchbar;
    @FXML
    private ChoiceBox<Object> exportChoiceBox;
    @FXML
    private Button importButton;
    @FXML
    private Button exportButton;

    private String exportCardName;
    private String importText;

    public ImportExportController(){

    }
    public void initialize(){
        setImportButton();
        setExportButton();
        setImportSearchbar();
        setExportChoiceBox();
    }
    public void setExportChoiceBox(){
        exportChoiceBox.setItems(FXCollections.observableArrayList(CSVInfoGetter.getCardNames()));
        exportChoiceBox.setOnAction(actionEvent -> setExportButton());
    }
    public void setImportSearchbar(){
        importSearchbar.setOnAction(actionEvent -> setImportButton());
    }
    public void setExportButton(){
        if (exportChoiceBox.getValue() == null)
            exportButton.setDisable(true);
        else {
            exportButton.setDisable(false);
            exportButton.setOnMouseClicked(mouseEvent -> {
                exportCardName =(String) exportChoiceBox.getValue();
                //todo
            });
        }
    }
    public void setImportButton(){
        if (importSearchbar.getText() == null)
            importButton.setDisable(true);
        else if (importSearchbar.getText().endsWith(".json") || importSearchbar.getText().endsWith(".csv")){
            importButton.setDisable(false);
            importText = importSearchbar.getText();
                File file = new File(importText);
                if (!file.exists())
                    Menu.showAlert("OPS! no file found!");
                else {
                   //todo
                }
        }
        else
            importButton.setDisable(true);
    }
    public void exit() {

    }
}
