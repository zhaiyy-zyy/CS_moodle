package javafxpropertyfxml;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

/**
 * IMPORTANT NOTE:
 * The source code is dedicated to demonstrate the MVC pattern, properties and binding,
 * but be aware that design patterns and principles are not applied here.
 */

public class Controller {
    @FXML
    ListView<Person> listViewLeaders;
    @FXML
    TableView<Person> tableViewEmployee;
    @FXML
    TableColumn<Person, String> tableColumnAlias;
    @FXML
    TableColumn<Person, String> tableColumnFirstName;
    @FXML
    TableColumn<Person, String> tableColumnLastName;

    ObservableList<Person> teamMembers;

    @FXML
    public void initialize() {
        ObservableList<Person> leaders = getPeople();
        listViewLeaders.setItems(leaders);
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

        teamMembers = FXCollections.observableArrayList();
        tableViewEmployee.setItems(teamMembers);

        tableColumnAlias.setCellValueFactory(new PropertyValueFactory("aliasName"));
        tableColumnAlias.setPrefWidth(tableViewEmployee.getPrefWidth() / 3);

        tableColumnFirstName.setCellValueFactory(new PropertyValueFactory("firstName"));
        tableColumnFirstName.setPrefWidth(tableViewEmployee.getPrefWidth() / 3);

        tableColumnLastName.setCellValueFactory(new PropertyValueFactory("lastName"));
        tableColumnLastName.setPrefWidth(tableViewEmployee.getPrefWidth() / 3);

        listViewLeaders.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (observable != null && observable.getValue() != null) {
                teamMembers.clear();
                teamMembers.addAll(observable.getValue().employeesProperty());
            }
        });
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
