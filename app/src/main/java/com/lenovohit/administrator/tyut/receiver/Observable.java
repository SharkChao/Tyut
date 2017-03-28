package com.lenovohit.administrator.tyut.receiver;


/**
 * 被观察类
 */
public interface Observable{
    void notifyObserver();
    void registerObserver(Observer observer);
    void UnRegisterObserver(Observer observer);
}