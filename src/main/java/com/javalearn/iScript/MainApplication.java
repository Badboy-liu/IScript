package com.javalearn.iScript;

import com.javalearn.iScript.cache.CacheUtil;
import com.javalearn.iScript.controller.MainController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.Closeable;
import java.io.IOException;

public class MainApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("IScript.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("脚本编辑器!");

        stage.setScene(scene);
        CacheUtil.controllerMap.put(CacheUtil.MainController,fxmlLoader.getController());

        stage.setOnShown(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                MainController mainController = (MainController) CacheUtil.controllerMap.get(CacheUtil.MainController);
                mainController.init();
            }
        });

        stage.show();


    }



    public static void main(String[] args) {
        launch();
    }

    @Override
    public void stop() throws Exception {
        Closeable closeable = (Closeable) CacheUtil.controllerMap.get(CacheUtil.MainController);
        closeable.close();
    }
}