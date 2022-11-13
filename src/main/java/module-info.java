module uv.fei.polinizadores {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires javafx.web;


    opens uv.fei.polinizadores to javafx.fxml;
    exports uv.fei.polinizadores;
    exports uv.fei.bussinesslogic;
    exports uv.fei.domain;
}