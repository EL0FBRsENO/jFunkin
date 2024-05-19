package com.ksspace.jfunkin;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;

public class GameScreen extends ScreenAdapter {

    private Camera camera;
    private SpriteBatch batch;
    //public Texture img;
    float time;

    public SparrowSprite sprite;

    public GameScreen(Camera camera) {
        this.camera = camera;
        this.camera.position.set(new Vector3(0, 0, 0));
        this.batch = new SpriteBatch();

        sprite = new SparrowSprite("BOYFRIEND", 24);
    }

    public void update(float deltaTime) {
        batch.setProjectionMatrix(camera.combined);
        //camera.position.add(new Vector3(.1f,.5f, 0));
        updateCamera();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
    }

    private void updateCamera() {
        //camera.position.set(new Vector3(0f, 0f, 0f));
        camera.update();
    }

    @Override
    public void render(float deltaTime) {
        this.update(deltaTime);

        float grayLevel = 28f / 255f;
        Gdx.gl.glClearColor(grayLevel, grayLevel, grayLevel,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        // Render
        //for (TextureRegion sprite : this.sprites) {
        //    batch.draw(sprite, sprite.getRegionX(), sprite.getRegionY());
        //}
        //batch.draw(region, 0f, 0f);
        TextureRegion frame = sprite.getCurrentAnimationFrame((int) time ++, true);
        if (frame != null) batch.draw(frame, 0f, 0f);

        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

}
