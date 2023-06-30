package com.admore.demo.adapter;

public class MenuBean {
    String name;
    String action;
    String className;

    public MenuBean() {
    }

    public MenuBean(String name, String action) {
        this.name = name;
        this.action = action;
    }

    public MenuBean(String name, String action, String className) {
        this.name = name;
        this.action = action;
        this.className = className;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
