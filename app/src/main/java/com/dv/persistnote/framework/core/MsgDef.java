
package com.dv.persistnote.framework.core;

public class MsgDef {

    private static int id_base = 0x00000000;

    private static int generateID() {
        return id_base++;
    }

    public static final int MSG_INIT_ROOTSCREEN = generateID();

    public static final int MSG_SHOW_WELCOME_SCREEN = generateID();

    public static final int MSG_OPEN_HABIT_DETAIL = generateID();

    public static final int MSG_OPEN_SHARE_PLATFORM = generateID();

    public static final int MSG_SHARE_TO_WX_TIMELINE = generateID();

    public static final int MSG_OPEN_NOTE_SCREEN = generateID();

    public static final int MSG_ACCOUNT_CAMERA_RETURN = generateID();

    public static final int MSG_ROOT_CAMERA_RETURN = generateID();
}
