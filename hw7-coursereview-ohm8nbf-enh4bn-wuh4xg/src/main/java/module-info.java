module edu.virginia.cs.hwseven {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.sql;

    opens edu.virginia.cs.hwseven to javafx.fxml;
    exports edu.virginia.cs.hwseven;
}