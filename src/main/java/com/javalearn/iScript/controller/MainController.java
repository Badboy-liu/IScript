package com.javalearn.iScript.controller;

import cn.hutool.core.util.StrUtil;
import com.javalearn.iScript.util.FileUtil;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.util.Callback;
import org.kordamp.ikonli.javafx.Icon;

import java.io.Closeable;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.function.Consumer;

public class MainController implements Closeable {


    private static volatile boolean isRun=false;

    @FXML
    private ChoiceBox<String> RequestMethod;

    @FXML
    private TextArea headerText;
    @FXML
    private TextField urlText;
    @FXML
    private TextArea logText;

    @FXML
    private TextArea paramText;

    @FXML
    private Icon icon;

    @FXML
    private Button saveButton;

    @FXML
    private ChoiceBox<String> sendNum;

    public void initChoiceBox() {
        ObservableList<String> methodList = FXCollections.observableArrayList("GET","POST","DELETE","PUT");
        RequestMethod.setItems(methodList);
        RequestMethod.getSelectionModel().selectFirst();

        ObservableList<String> numList = FXCollections.observableArrayList("1","5","10","50","9999999");
        sendNum.setItems(numList);
        sendNum.getSelectionModel().selectFirst();
    }

    @FXML
    private Button startButton;

    public TextArea getLogText() {
        return logText;
    }

    public void setLogText(TextArea logText) {
        this.logText = logText;
    }


    @FXML
    void saveConfig(ActionEvent event) {
        String message;
        message=checkData()? "保存配置成功\n" :"配置不完整\n";
        appendLog(message);
        FileUtil.saveLog(message);
    }

    private void appendLog(String message) {
        logText.appendText(message);
    }

    private boolean checkData() {
        if (StrUtil.hasBlank(urlText.getText())){
            buildDialog("url不能为空",urlText.textProperty()::setValue);
            return false;
        }
        if (StrUtil.hasBlank(paramText.getText())){
            buildDialog("参数不能为空",paramText.textProperty()::setValue);
            return false;
        }
        if (StrUtil.hasBlank(headerText.getText())){
            buildDialog("头不能为空",headerText.textProperty()::setValue);
            return false;
        }
        return true;
    }

    private <T extends StringProperty> void buildDialog(String message, Consumer<String> text) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setContentText(message);
        dialog.show();
        Callback<ButtonType, String> resultConverter = dialog.getResultConverter();

        DialogPane dialogPane = dialog.getDialogPane();
        Node node = dialogPane.lookupButton(ButtonType.OK);
        node.setOnMousePressed(mouseEvent -> {
            if (mouseEvent.getButton().name().equals("PRIMARY")) {
                text.accept(resultConverter.call(ButtonType.OK));
            }
        });
    }

    private <T extends StringProperty> void buildDialog(String message) {
        Alert dialog = new Alert(Alert.AlertType.WARNING,message);
        dialog.setContentText(message);
        dialog.show();
    }

    @FXML
    void startSend(ActionEvent event) throws IOException {
        if (!isRun){
            isRun=true;
            startButton.setText("停止脚本");
        }else {
            startButton.setText("启动脚本");
            isRun=false;
        }

        String startStr= "脚本开始\n";
        logText.appendText(startStr);
        RandomAccessFile logFile = FileUtil.getLogText();
        logFile.write((startStr).getBytes("GBK"));
    }


    @Override
    public void close() throws IOException {
        isRun=false;
    }
}