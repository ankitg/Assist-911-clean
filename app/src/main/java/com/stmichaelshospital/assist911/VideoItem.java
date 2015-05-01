package com.stmichaelshospital.assist911;

import java.io.Serializable;

/**
 * Created by ankitg on 2015-05-01.
 */

public class VideoItem  implements Serializable {
    public String title;
    public String videoName;
    public Boolean isEmergency;
    public EMERGENCYTYPE type;

    public VideoItem(String title, String videoName, EMERGENCYTYPE type) {
        this.title = title;
        this.videoName = videoName;
        this.type = type;
        this.isEmergency = type.equals(EMERGENCYTYPE.NONE) ? false : true;
    }

    public enum EMERGENCYTYPE {
        FIRE,
        POLICE,
        AMBULANCE,
        NONE
    }
}
