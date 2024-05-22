package com.ksspace.jfunkin;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class Funkin extends Game {
    public static Funkin INSTANCE;
    private int windowWidth, windowHeight;

    public static final class paths {

        public static final String images = "shared/images/";
        public static class images {

            public static final String characters = "shared/images/characters/";

        }
    }

    public Funkin() {
        INSTANCE = this;
    }

    @Override
    public void create () {
        System.out.println("<Funkin>: We Funkin'!");

        this.windowWidth 	= Gdx.graphics.getWidth();
        this.windowHeight 	= Gdx.graphics.getHeight();
        OrthographicCamera orthographicCamera = new OrthographicCamera();
        orthographicCamera.setToOrtho(false, windowWidth, windowHeight);

        setScreen(new GameScreen(orthographicCamera));

        //batch = new SpriteBatch();
        //img = new Texture("badlogic.jpg");
    }
}
