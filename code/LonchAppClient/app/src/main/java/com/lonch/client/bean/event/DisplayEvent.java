package com.lonch.client.bean.event;

public class DisplayEvent {
    private String disPlay;
    public DisplayEvent(String disPlay){
        this.disPlay = disPlay;
    }

    public String getDisPlay() {
        return disPlay;
    }

    public void setDisPlay(String disPlay) {
        this.disPlay = disPlay;
    }
}
