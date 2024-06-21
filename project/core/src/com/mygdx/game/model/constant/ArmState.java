package com.mygdx.game.model.constant;

public enum ArmState {
    KUNAI("Kunai"), SHURIKEN("Shuriken"), BOMB("Bomb"), SHIELD("Shield"),
    FOLLDINGFAN("FoldingFan"), DART("Dart"), BOMERANG("Bomerang"), HAMMER("Hammer"), RUGBY("Rugby");
    private String displayName;

    ArmState(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
