
module com.litzysystems.menuarboles {

    requires javafx.controls;
    requires javafx.fxml;

    // Paquete principal de la aplicación
    exports com.litzysystems.menuarboles;

    // Paquete de los controladores
    exports com.litzysystems.menuarboles.controller;

    // Paquete de las clases de los árboles
    exports com.litzysystems.menuarboles.clase;

    // Permitir que JavaFX acceda a las clases mediante FXML
    opens com.litzysystems.menuarboles to javafx.fxml;
    opens com.litzysystems.menuarboles.controller to javafx.fxml;
}
