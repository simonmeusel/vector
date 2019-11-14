module vector {
    requires transitive javafx.controls;
    requires javafx.fxml;
	requires java.xml;

    opens de.simonmeusel.vector.ui to javafx.fxml;
    exports de.simonmeusel.vector.ui;
}