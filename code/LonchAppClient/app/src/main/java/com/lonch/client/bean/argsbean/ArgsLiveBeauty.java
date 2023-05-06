package com.lonch.client.bean.argsbean;

public class ArgsLiveBeauty {
    private boolean isOpen;
    private String  beautyStyle;
    private int white;
    private int ruddiness;

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public String getBeautyStyle() {
        return beautyStyle;
    }

    public void setBeautyStyle(String beautyStyle) {
        this.beautyStyle = beautyStyle;
    }

    public int getWhite() {
        return white;
    }

    public void setWhite(int white) {
        this.white = white;
    }

    public int getRuddiness() {
        return ruddiness;
    }

    public void setRuddiness(int ruddiness) {
        this.ruddiness = ruddiness;
    }
}
