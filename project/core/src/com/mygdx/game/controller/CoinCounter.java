package com.mygdx.game.controller;

public class CoinCounter {
    private static int coinIngame;
    private static int coin;
    public static void updateCoin(int coinIngame) {
        coin += coinIngame;
    }

    public static void addCoinIngame(int count) {
        coinIngame += count;
    }
    public static void resetCoinIngame() {
        coinIngame = 0;
    }

    public static int getCoinIngame() {
        return coinIngame;
    }

    public static int getCoin() {
        return coin;
    }
}
