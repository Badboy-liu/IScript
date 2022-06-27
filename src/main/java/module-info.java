module com.javalearn.iScript {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires com.google.common;
    requires hutool.all;
    opens com.javalearn.iScript to javafx.fxml;
    exports com.javalearn.iScript;
    exports com.javalearn.iScript.controller;
    opens com.javalearn.iScript.controller to javafx.fxml;
}