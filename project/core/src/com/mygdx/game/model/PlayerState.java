package com.mygdx.game.model;

public enum PlayerState {
    IDLE("IDlE"), GLIDE("GLIDE"), THROW("THROW"), DEAD("DEAD"), FLASH("FLASH");
    private String displayName;

    PlayerState(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
