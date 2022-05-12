module javafx {
    requires transitive javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;

    opens javafx to javafx.fxml;
    exports javafx;
}
