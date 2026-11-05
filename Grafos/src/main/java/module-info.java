
module com.litzysystems.grafos {
    requires javafx.controls;
    requires javafx.fxml;

    exports com.litzysystems.grafos;
    exports com.litzysystems.grafos.clases;
    exports com.litzysystems.grafos.controller;

    opens com.litzysystems.grafos to javafx.fxml;
    opens com.litzysystems.grafos.controller to javafx.fxml;
}
