package com.javalearn.iScript.cache;

import com.google.common.collect.Maps;

import java.util.Map;

public abstract class CacheUtil {
    public static Map<String,Object> controllerMap= Maps.newConcurrentMap();
    public final static String MainController="Main";
}
