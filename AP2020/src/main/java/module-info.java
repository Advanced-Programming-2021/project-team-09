module AP2020 {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.media;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.annotation;
    requires annotations;
    requires com.google.gson;
    requires opencsv;
    requires com.fasterxml.jackson.databind;
    opens view to javafx.fxml;
    opens view.graphics to javafx.fxml;
    opens view.graphics.deckmenu to javafx.fxml;
    opens view.graphics.profile to javafx.fxml;
    opens model.graphicalModels to javafx.fxml;
    exports view.graphics to javafx.fxml;
    exports view;
    exports controller;
    opens controller to javafx.fxml;
    opens main to javafx.graphics;

}