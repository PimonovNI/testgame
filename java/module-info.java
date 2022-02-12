module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.demo to javafx.fxml;
    exports com.example.demo;
    opens com.example.demo.interfaceForFloby to javafx.fxml;
    exports com.example.demo.interfaceForFloby;
    opens com.example.demo.flobyLVL.snowYar to javafx.fxml;
    exports com.example.demo.flobyLVL.snowYar;
    opens com.example.demo.proceduralGeneration to javafx.fxml;
    exports com.example.demo.proceduralGeneration;
}