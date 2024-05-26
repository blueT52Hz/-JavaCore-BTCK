package com.mygdx.game.controller;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.model.*;
import com.mygdx.game.model.constant.PlayerState;
import com.mygdx.game.model.impl.Bullet.Flame;
import com.mygdx.game.model.impl.Bullet.Kunai;
import com.mygdx.game.model.impl.Player.Ninja;
import com.mygdx.game.view.Brick;
import com.mygdx.game.view.GameMap;
import com.mygdx.game.view.MainGameScreenTest;
import com.mygdx.game.view.MainMenuScreen;

public class CustomContactListener implements ContactListener {
    private GameMap gameMap;
    private MyGdxGame game;
    private MainGameScreenTest mainGameScreen;
    public CustomContactListener(GameMap gameMap, MyGdxGame game) {
        this.gameMap = gameMap;
        this.game = game;
    }
    // Xử lý khi bắt đầu va chạm
    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        // xử lí khi bullet đập vào cạnh
        if(     (fixtureA.getUserData() instanceof Bullet && fixtureB.getUserData() == "wall") || (fixtureB.getUserData() instanceof Bullet && fixtureA.getUserData() == "wall") ||
                (fixtureA.getUserData() instanceof Bullet && fixtureB.getUserData() == "platform") || (fixtureB.getUserData() instanceof Bullet && fixtureA.getUserData() == "platform")) {
            Bullet bullet = (fixtureA.getUserData() instanceof Bullet) ? (Bullet) fixtureA.getUserData() : (Bullet) fixtureB.getUserData();
            // nếu là đạn của quái và không thể bật tường thì bỏ nó ra khỏi list đạn của quái
            if(bullet instanceof EnemyBullet && !bullet.isCanBounce()) {
                bullet.setAppear(false);
                System.out.println("Remove EnemyBullet");
            }
            String s = (fixtureA.getUserData() instanceof Bullet) ? (String) fixtureB.getUserData() : (String) fixtureA.getUserData();
            if(s=="platform") bullet.setRotation(360-bullet.getRotation());
            else bullet.setRotation(180-bullet.getRotation());
            bullet.setHandledContact(false);
        }

        // xử lí khi playerBullet đập vào brick
        if((fixtureA.getUserData() instanceof PlayerBullet && fixtureB.getUserData() instanceof Brick|| fixtureA.getUserData() instanceof Brick && fixtureA.getUserData() instanceof PlayerBullet)) {
            PlayerBullet playerBullet = (fixtureA.getUserData() instanceof PlayerBullet) ? (PlayerBullet) fixtureA.getUserData() : (PlayerBullet) fixtureB.getUserData();
            playerBullet.setRotation(360 - playerBullet.getRotation());
            playerBullet.setHandledContact(false);
        }

        // xử lí khi player trúng enemyBullet
        if((fixtureA.getUserData() instanceof EnemyBullet && fixtureB.getUserData() instanceof Player || fixtureA.getUserData() instanceof Player && fixtureB.getUserData() instanceof EnemyBullet)) {
            System.out.println("Nhân vật trúng đạn");

            game.create();
            game.setScreen(new MainMenuScreen(game));
            EnemyBullet bullet = (fixtureA.getUserData() instanceof EnemyBullet) ? (EnemyBullet) fixtureA.getUserData() : (EnemyBullet) fixtureB.getUserData();
            if(bullet != null && !bullet.isCanBounce()) {
                bullet.setAppear(false);
                System.out.println("Remove EnemyBullet");
            }
        }

        // xử lí khi enemy trúng playerBullet
        if((fixtureA.getUserData() instanceof PlayerBullet && fixtureB.getUserData() instanceof Enemy || fixtureA.getUserData() instanceof Enemy && fixtureB.getUserData() instanceof PlayerBullet)) {
            System.out.println("Quái trúng đạn");
            PlayerBullet playerBullet = (fixtureA.getUserData() instanceof PlayerBullet) ? (PlayerBullet) fixtureA.getUserData() : (PlayerBullet) fixtureB.getUserData();
            Enemy enemy = (fixtureA.getUserData() instanceof Enemy) ? (Enemy) fixtureA.getUserData() : (Enemy) fixtureB.getUserData();
            if (enemy != null && playerBullet != null) {
                enemy.setDead(true);
                playerBullet.setAppear(false);
                gameMap.addScore(1);
            }
            enemy.setDead(true);
        }

        // xử lí khi enemyBullet trúng playerBullet
        if((fixtureA.getUserData() instanceof PlayerBullet && fixtureB.getUserData() instanceof EnemyBullet || fixtureA.getUserData() instanceof EnemyBullet && fixtureB.getUserData() instanceof PlayerBullet)) {
            System.out.println("Quái trúng đạn");
            EnemyBullet enemyBullet = (fixtureA.getUserData() instanceof EnemyBullet) ? (EnemyBullet) fixtureA.getUserData() : (EnemyBullet) fixtureB.getUserData();
            enemyBullet.setAppear(false);
        }









    }

    @Override
    public void endContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        if (    (fixtureA.getUserData() instanceof Player && fixtureB.getUserData() == "platform")  || (fixtureB.getUserData() instanceof Player && fixtureA.getUserData() == "platform") ||
                (fixtureA.getUserData() instanceof Player && fixtureB.getUserData() instanceof Brick)     || (fixtureB.getUserData() instanceof Player && fixtureA.getUserData() instanceof Brick)) {
            Player player = (fixtureA.getUserData() instanceof Player) ? (Player) fixtureA.getUserData() : (Player) fixtureB.getUserData();
            player.setPlayerState(PlayerState.GLIDE);
        }

    }

    @Override
    public void preSolve(Contact contact, Manifold manifold) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();


        if (    (fixtureA.getUserData() instanceof Player && fixtureB.getUserData() == "platform")  || (fixtureB.getUserData() instanceof Player && fixtureA.getUserData() == "platform") ||
                (fixtureA.getUserData() instanceof Player && fixtureB.getUserData() instanceof Brick)     || (fixtureB.getUserData() instanceof Player && fixtureA.getUserData() instanceof Brick)) {
            Player player = (fixtureA.getUserData() instanceof Player) ? (Player) fixtureA.getUserData() : (Player) fixtureB.getUserData();
            player.setPlayerState(PlayerState.IDLE);
        }


        if (    (fixtureA.getUserData() instanceof Player && fixtureB.getUserData() instanceof PlayerBullet) || (fixtureA.getUserData() instanceof PlayerBullet && fixtureB.getUserData() instanceof Player)||
                (fixtureA.getUserData() instanceof Enemy && fixtureB.getUserData() instanceof EnemyBullet)   || (fixtureA.getUserData() instanceof EnemyBullet && fixtureB.getUserData() instanceof Enemy)  ||
                (fixtureA.getUserData() instanceof EnemyBullet && fixtureB.getUserData() instanceof EnemyBullet)   ||
                (fixtureA.getUserData() instanceof Brick && fixtureB.getUserData() instanceof EnemyBullet)   || (fixtureA.getUserData() instanceof EnemyBullet && fixtureB.getUserData() instanceof Brick)) {
            // Vô hiệu hóa va chạm đạn đồng minh
            contact.setEnabled(false);
        }

    }
    @Override
    public void postSolve(Contact contact, ContactImpulse contactImpulse) {

    }

}

