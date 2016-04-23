package com.dv.persistnote.framework.ui;

/**
 * Created by Hang on 2016/4/24.
 */
public interface IUIObserver {
    public boolean handleAction(int actionId, Object arg, Object result);
}
