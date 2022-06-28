package com.javalearn.iScript.util;

import cn.hutool.core.util.URLUtil;
import javafx.beans.property.StringProperty;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseButton;
import javafx.util.Callback;

import java.util.function.Consumer;

/**
 * @Description: TODO
 * @author: zql
 * @since: 2022/6/28 0028 12:33
 */
public abstract class DialogUtil {

    public static  <T extends StringProperty> void buildDialog(String message, Consumer<String> text) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setContentText(message);
        dialog.show();
        Callback<ButtonType, String> resultConverter = dialog.getResultConverter();

        DialogPane dialogPane = dialog.getDialogPane();
        Node node = dialogPane.lookupButton(ButtonType.OK);
        node.setOnMousePressed(mouseEvent -> {
            if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                String urlText = resultConverter.call(ButtonType.OK);
                text.accept(urlText);
            }
        });
    }

    public static  <T extends StringProperty> void buildDialog(String message) {
        Alert dialog = new Alert(Alert.AlertType.WARNING,message);
        dialog.setContentText(message);
        dialog.show();
    }

}
