package gui.tableview;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class View extends Application
{
    private ObservableList<StringDoublePair> tableData;
    private TableView<StringDoublePair> table;
    
    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        VBox root = new VBox();
        root.setPadding(new Insets(10));
        
        StringDoublePair test = new StringDoublePair("E1", 1337.00);
        StringDoublePair test2 = new StringDoublePair("E2", 1337.00);
        StringDoublePair test3 = new StringDoublePair("E3", 1337.00);
        
        this.tableData = FXCollections.observableArrayList();
        tableData.add(test);
        tableData.add(test2);
        tableData.add(test3);
        
        this.table = new TableView<StringDoublePair>();
        TableColumn<StringDoublePair, String> entgeltColumn = new TableColumn<>("Entgelt");
        entgeltColumn.setCellValueFactory(new PropertyValueFactory<>("getEntgelt"));
        TableColumn<StringDoublePair, Double> gehaltColumn = new TableColumn<>("Gehalt");
        gehaltColumn.setCellValueFactory(new PropertyValueFactory<>("getGehalt"));        
        this.table.getColumns().addAll(entgeltColumn, gehaltColumn);
        
        this.table.setItems(tableData);
        
        Button add = new Button("Hinzufügen");
        Button edit = new Button("Ändern");
        Button delete = new Button("Löschen");
        Button editAll = new Button("Alle anpassen");

        root.getChildren().add(table);
        root.getChildren().add(add);
        root.getChildren().add(edit);
        root.getChildren().add(delete);
        root.getChildren().add(editAll);
        
        add.setOnAction(e -> addEntry());
        edit.setOnAction(e -> editEntry());
        delete.setOnAction(e -> deleteEntry());
        editAll.setOnAction(e -> editAllEntry());
        
        Scene scene = new Scene(root, 800, 600);

        primaryStage.setScene(scene);

        primaryStage.setTitle("Entgelttabelle Öffentlicher Dienst");
        primaryStage.show(); 
    }
    
    private void addEntry()
    {
        Stage dialog = new Stage();

        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(20));

        TextField entgeltInput = new TextField();
        TextField gehaltInput = new TextField();
        
        grid.add(new Label("Entgelt:"), 0, 0);
        grid.add(entgeltInput, 1, 0);
        
        grid.add(new Label("Gehalt:"), 0, 1);
        grid.add(gehaltInput, 1, 1);

        Button submitButton = new Button("Hinzufügen");
        Button cancelButton = new Button("Abbrechen");
        submitButton.setOnAction(event -> {
            String entgelt = entgeltInput.getText();
            Double gehalt = Double.parseDouble(gehaltInput.getText());

            tableData.add(new StringDoublePair(entgelt, gehalt));

            dialog.close();
        });
        
        grid.add(submitButton, 1, 2);
        grid.add(cancelButton, 2, 2);

        Scene dialogScene = new Scene(grid, 600, 400);
        dialog.setScene(dialogScene);

        dialog.setTitle("Neuen Eintrag hinzufügen");
        dialog.show();
    }
    
    private void editEntry()
    {
        StringDoublePair selectedEntry = this.table.getSelectionModel().getSelectedItem();
        
        if (selectedEntry == null)
        {
            return;
        }

        Stage dialog = new Stage();

        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(20));

        TextField entgeltInput = new TextField(selectedEntry.getEntgelt());
        TextField gehaltInput = new TextField(String.valueOf(selectedEntry.getGehalt()));

        grid.add(new Label("Entgelt:"), 0, 0);
        grid.add(entgeltInput, 1, 0);
        
        grid.add(new Label("Gehalt:"), 0, 1);
        grid.add(gehaltInput, 1, 1);

        Button submitButton = new Button("Übernehmen");
        submitButton.setOnAction(event -> {
            String newEntgelt = entgeltInput.getText();
            Double newGehalt = Double.parseDouble(gehaltInput.getText());

            selectedEntry.setEntgelt(newEntgelt);
            selectedEntry.setGehalt(newGehalt);
            
            dialog.close();
        });
        
        grid.add(submitButton, 1, 2);

        Scene dialogScene = new Scene(grid, 300, 200);
        dialog.setScene(dialogScene);

        dialog.setTitle("Eintrag bearbeiten");
        dialog.show();
    }

    
    private void deleteEntry()
    {
        StringDoublePair selectedEntry = table.getSelectionModel().getSelectedItem();
        
        if (selectedEntry != null)
        {
            tableData.remove(selectedEntry);
        }
    }
    
    private void editAllEntry()
    {
        Stage dialog = new Stage();

        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(20));

        TextField percentageInput = new TextField();

        grid.add(new Label("Prozentsatz (%):"), 0, 0);
        grid.add(percentageInput, 1, 0);

        Button submitButton = new Button("Anwenden");
        submitButton.setOnAction(event -> {
            try
            {
                double percentage = Double.parseDouble(percentageInput.getText());

                for (StringDoublePair entry : tableData) {
                    double oldGehalt = entry.getGehalt();
                    double newGehalt = oldGehalt + (oldGehalt * percentage / 100);
                    entry.setGehalt(newGehalt);
                }

                dialog.close();
            } 
            catch (NumberFormatException e)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Fehler");
                alert.setHeaderText(null);
                alert.setContentText("Bitte geben Sie einen gültigen Prozentsatz ein.");
                alert.showAndWait();
            }
        });

        grid.add(submitButton, 1, 1);

        Scene dialogScene = new Scene(grid, 600, 400);
        dialog.setScene(dialogScene);

        dialog.setTitle("Alle Gehälter anpassen");
        dialog.show();
    }

}
