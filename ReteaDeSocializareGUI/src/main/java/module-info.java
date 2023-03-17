module com.example.reteadesocializaregui {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires org.testng;

    opens com.example.reteadesocializaregui to javafx.fxml;
    exports com.example.reteadesocializaregui;
    exports com.example.reteadesocializaregui.controllers;
    opens com.example.reteadesocializaregui.controllers to javafx.fxml;
    exports com.example.reteadesocializaregui.domain;
    opens com.example.reteadesocializaregui.domain to javafx.fxml;
}