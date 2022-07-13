module com.numismatics_gae {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.numismatics_gae to javafx.fxml;
    exports com.numismatics_gae;
}
