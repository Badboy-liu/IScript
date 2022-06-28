package com.javalearn.iScript.util;

import cn.hutool.core.util.ObjectUtil;
import com.google.common.collect.Maps;
import com.javalearn.iScript.controller.MainController;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URL;
import java.util.Map;

public abstract class FileUtil {
    static {
        File file=new File("");
        rootPath =file.getAbsolutePath();
    }

    private static Map<String,RandomAccessFile> fileMap= Maps.newConcurrentMap();

    private static final String logName="log";
    private static final String configName="config";

    private static final String rootPath;
    public static RandomAccessFile getLogText(){
        try {
            RandomAccessFile randomAccessFile = fileMap.get(logName);
            if (ObjectUtil.isNotNull(randomAccessFile)){
                return randomAccessFile;
            }
            File logText = new File(rootPath+"/log.txt");
            if (!logText.exists()){
                logText.createNewFile();
            }
            randomAccessFile = new RandomAccessFile(logText, "rw");
            fileMap.put(logName,randomAccessFile);
            return randomAccessFile;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
        }
    }

    public static RandomAccessFile getConfigText(){
        try {
            RandomAccessFile randomAccessFile = fileMap.get(configName);
            if (ObjectUtil.isNotNull(randomAccessFile)){
                return randomAccessFile;
            }
            File configFile = new File(rootPath+"/config.json");
            if (!configFile.exists()){
                configFile.createNewFile();
            }
            randomAccessFile = new RandomAccessFile(configFile, "rw");
            fileMap.put(configName,randomAccessFile);
            return  randomAccessFile;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
        }
    }



    public static void saveLog(String message){
        try {
            RandomAccessFile logFile = FileUtil.getLogText();
            logFile.write((message).getBytes("GBK"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
        }
    }
}
