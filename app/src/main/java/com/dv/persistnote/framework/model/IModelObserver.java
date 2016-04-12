package com.dv.persistnote.framework.model;

/**
 * Created by Hang on 2016/4/12.
 */
public interface IModelObserver {
    public boolean handleData(int modelId, Object arg, Object result);
}
