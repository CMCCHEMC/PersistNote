package com.dv.persistnote.framework.core;


import com.dv.persistnote.framework.core.ControllerCenter.MessagePolicy;

public class ControllerRegister{
   
    private ControllerCenter mControllerCenter;

    public ControllerRegister(ControllerCenter controllerCenter) {
        mControllerCenter = controllerCenter;
    }

    /**
     * 注册启动过程需要初始化的Controller
     */
    public void registerControllers() {

        registerRootController();
        registerAccountController();
        registerHabitDetailController();
        registerShareController();
        registerNoteController();
    }

    public void registerRootController(){
        int controllerID = ControllerID.ROOT_CONTROLLER;

        int[] messageIDs = new int[]{
                MsgDef.MSG_INIT_ROOTSCREEN,
                };
        mControllerCenter.addPolicy(MessagePolicy.create(controllerID, messageIDs));
    }

    public void registerAccountController(){
        int controllerID = ControllerID.ACCOUNT_CONTROLLER;

        int[] messageIDs = new int[]{
                MsgDef.MSG_SHOW_WELCOME_SCREEN,
        };
        mControllerCenter.addPolicy(MessagePolicy.create(controllerID, messageIDs));
    }

    public void registerHabitDetailController(){
        int controllerID = ControllerID.HABITDETAIL_CONTROLLER;

        int[] messageIDs = new int[]{
                MsgDef.MSG_OPEN_HABIT_DETAIL,
        };
        mControllerCenter.addPolicy(MessagePolicy.create(controllerID, messageIDs));
    }

    public void registerShareController(){
        int controllerID = ControllerID.SHARE_CONTROLLER;

        int[] messageIDs = new int[]{
                MsgDef.MSG_OPEN_SHARE_PLATFORM,
                MsgDef.MSG_SHARE_TO_WX_TIMELINE
        };
        mControllerCenter.addPolicy(MessagePolicy.create(controllerID, messageIDs));
    }

    public void registerNoteController(){
        int controllerID = ControllerID.NOTE_CONTROLLER;

        int[] messageIDs = new int[]{
                MsgDef.MSG_OPEN_NOTE_SCREEN,
        };
        mControllerCenter.addPolicy(MessagePolicy.create(controllerID, messageIDs));
    }


}