package com.mygdx.game.model.constant;

public enum SkinState {
    NINJA("ninja"), NINJAGRIRL("ninjagirl"),;
    private String displayName;

    SkinState(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
