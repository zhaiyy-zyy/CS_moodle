package javafxpropertynofxml;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("JavaFX Sample Code with Properties and Binding");
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 500, 250, Color.WHITE);

        // create a grid pane
        GridPane gridpane = new GridPane();
        gridpane.setPadding(new Insets(5));
        gridpane.setHgap(10);
        gridpane.setVgap(10);
        root.setCenter(gridpane);

        // candidates label
        Label labelCandidates = new Label("Boss");
        GridPane.setHalignment(labelCandidates, HPos.CENTER);
        gridpane.add(labelCandidates, 0, 0);

        // List of leaders
        ObservableList<Person> leaders = getPeople();
        final ListView<Person> listViewLeaders = new ListView<>(leaders);
        listViewLeaders.setPrefWidth(150);
        listViewLeaders.setMaxWidth(Double.MAX_VALUE);
        listViewLeaders.setPrefHeight(150);

        // display first and last name with tooltip using alias
        listViewLeaders.setCellFactory(new Callback<ListView<Person>, ListCell<Person>>() {

            @Override
            public ListCell<Person> call(ListView<Person> param) {
                final Label labelLeader = new Label();
                final Tooltip tooltip = new Tooltip();
                final ListCell<Person> cell = new ListCell<Person>() {
                    @Override
                    public void updateItem(Person item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            labelLeader.setText(item.getAliasName());
                            setText(item.getFirstName() + " " + item.getLastName());
                            tooltip.setText(item.getAliasName());
                            setTooltip(tooltip);
                        }
                    }
                };
                return cell;

            }
        });

        gridpane.add(listViewLeaders, 0, 1);

        Label labelEmployee = new Label("Employees");
        gridpane.add(labelEmployee, 2, 0);
        GridPane.setHalignment(labelEmployee, HPos.CENTER);

        final TableView<Person> tableViewEmployee = new TableView<>();
        tableViewEmployee.setPrefWidth(300);

        final ObservableList<Person> teamMembers = FXCollections.observableArrayList();
        tableViewEmployee.setItems(teamMembers);

        TableColumn<Person, String> tableColumnAlias = new TableColumn<>("Alias");
        tableColumnAlias.setEditable(true);
        tableColumnAlias.setCellValueFactory(new PropertyValueFactory("aliasName"));

        tableColumnAlias.setPrefWidth(tableViewEmployee.getPrefWidth() / 3);

        TableColumn<Person, String> tableColumnFirstName = new TableColumn<>("First Name");
        tableColumnFirstName.setCellValueFactory(new PropertyValueFactory("firstName"));
        tableColumnFirstName.setPrefWidth(tableViewEmployee.getPrefWidth() / 3);

        TableColumn<Person, String> tableColumnLastName = new TableColumn<>("Last Name");
        tableColumnLastName.setCellValueFactory(new PropertyValueFactory("lastName"));
        tableColumnLastName.setPrefWidth(tableViewEmployee.getPrefWidth() / 3);

        tableViewEmployee.getColumns().setAll(tableColumnAlias, tableColumnFirstName, tableColumnLastName);
        gridpane.add(tableViewEmployee, 2, 1);

        // selection listening
        listViewLeaders.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Person> observable, Person oldValue, Person newValue) -> {
            if (observable != null && observable.getValue() != null) {
                teamMembers.clear();
                teamMembers.addAll(observable.getValue().employeesProperty());
            }
        });

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private ObservableList<Person> getPeople() {
        ObservableList<Person> people = FXCollections.observableArrayList();
        Person docX = new Person("Professor X", "Charles", "Xavier");
        docX.employeesProperty().add(new Person("Wolverine", "James", "Howlett"));
        docX.employeesProperty().add(new Person("Cyclops", "Scott", "Summers"));
        docX.employeesProperty().add(new Person("Storm", "Ororo", "Munroe"));

        Person magneto = new Person("Magneto", "Max", "Eisenhardt");
        magneto.employeesProperty().add(new Person("Juggernaut", "Cain", "Marko"));
        magneto.employeesProperty().add(new Person("Mystique", "Raven", "Darkhölme"));
        magneto.employeesProperty().add(new Person("Sabretooth", "Victor", "Creed"));

        Person biker = new Person("Mountain Biker", "Jonathan", "Gennick");
        biker.employeesProperty().add(new Person("Josh", "Joshua", "Juneau"));
        biker.employeesProperty().add(new Person("Freddy", "Freddy", "Guime"));
        biker.employeesProperty().add(new Person("Mark", "Mark", "Beaty"));
        biker.employeesProperty().add(new Person("John", "John", "O'Conner"));
        biker.employeesProperty().add(new Person("D-Man", "Carl", "Dea"));

        people.add(docX);
        people.add(magneto);
        people.add(biker);

        return people;
    }
}
