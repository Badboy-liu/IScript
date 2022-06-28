package com.javalearn.iScript.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import com.javalearn.iScript.constant.ErrorConstant;
import com.javalearn.iScript.constant.MessageConstant;
import com.javalearn.iScript.util.DialogUtil;
import com.javalearn.iScript.util.FileUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.kordamp.ikonli.javafx.Icon;

import java.io.Closeable;
import java.io.IOException;
import java.io.RandomAccessFile;

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

    public void init() {
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
        if (StrUtil.hasBlank(urlText.getText())) {
            DialogUtil.buildDialog(ErrorConstant.URL_ERROR, urlText.textProperty()::setValue);
            return false;
        } else if (StrUtil.hasBlank(paramText.getText())) {
            DialogUtil.buildDialog(ErrorConstant.PARAM_ERROR, paramText.textProperty()::setValue);
            return false;
        } else if (StrUtil.hasBlank(headerText.getText())) {
            DialogUtil.buildDialog(ErrorConstant.HEADER_ERROR, headerText.textProperty()::setValue);
            return false;
        }
        return true;
    }





    @FXML
    void startSend(ActionEvent event) throws IOException {
        String message ;

        if (!isRun){
            message = setText(MessageConstant.STOP_MESSAGE,true);
        }else {
            message = setText(MessageConstant.START_MESSAGE,false);
        }

        logText.appendText(message);
        RandomAccessFile logFile = FileUtil.getLogText();
        logFile.write((message).getBytes("GBK"));
    }


    private String setText(String message,boolean value) {
        startButton.setText(message);
        isRun=value;
        return message;
    }


    @Override
    public void close() throws IOException {
        isRun=false;
    }
}