package com.ksspace.jfunkin;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class Funkin extends Game {

    public static Funkin INSTANCE;
    private int windowWidth, windowHeight;
    private OrthographicCamera orthographicCamera;

    public Funkin() {
        INSTANCE = this;
    }

    @Override
    public void create () {
        System.out.println("We Funkin'!");

        this.windowWidth 	= Gdx.graphics.getWidth();
        this.windowHeight 	= Gdx.graphics.getHeight();
        this.orthographicCamera = new OrthographicCamera();
        this.orthographicCamera.setToOrtho(false, windowWidth, windowHeight);

        setScreen(new GameScreen(orthographicCamera));

        //batch = new SpriteBatch();
        //img = new Texture("badlogic.jpg");
    }
}
