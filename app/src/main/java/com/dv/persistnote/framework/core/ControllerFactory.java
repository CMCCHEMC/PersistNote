package com.dv.persistnote.framework.core;

import com.dv.persistnote.business.RootController;
import com.dv.persistnote.business.account.AccountController;
import com.dv.persistnote.business.habit.HabitDetailController;
import com.dv.persistnote.business.habit.NoteController;
import com.dv.persistnote.business.share.ShareController;

public class ControllerFactory {

    public ControllerFactory() {
    }

    public AbstractController createControllerByID(BaseEnv environment, int controllerID) {
        AbstractController controller = null;
        switch (controllerID) {
            case ControllerID.ROOT_CONTROLLER:
                return new RootController(environment);
            case ControllerID.ACCOUNT_CONTROLLER:
                return new AccountController(environment);
            case ControllerID.HABITDETAIL_CONTROLLER:
                return new HabitDetailController(environment);
            case ControllerID.SHARE_CONTROLLER:
                return new ShareController(environment);
            case ControllerID.NOTE_CONTROLLER:
                return new NoteController(environment);
            default:
                break;
        }

        return controller;
    }
}
