package com.ksspace.jfunkin;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

import java.util.HashMap;

public class GameScreen extends ScreenAdapter {

    private final SpriteBatch batch;
    private final Camera camera;

    public SparrowSprite sprite;

    public GameScreen(Camera camera) {
        this.camera = camera;
        this.camera.position.set(new Vector3(0, 0, 0));
        this.batch = new SpriteBatch();

        sprite = new SparrowSprite("bfCar");
        //sprite.setFrameDuration(1f / 24f);
        //System.out.println(sprite.getCurrentAnimation().getFrameDuration());
    }

    public void update(float deltaTime) {
        batch.setProjectionMatrix(camera.combined);
        //camera.position.add(new Vector3(.1f,.5f, 0));
        updateCamera();
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }

        sprite.setFrameIndex(sprite.getFrameIndex() + deltaTime * 24);

        //region Idle Animation
        if (!sprite.getCurrentAnimation().name.equals("BF idle dance") && sprite.getCurrentAnimation().isAnimationFinished(sprite.getFrameIndex())) {
            sprite.setCurrentAnimation("BF idle dance");
        }
        //endregion
        //region Notes Animations
        HashMap<Integer[], String> inputs = new HashMap<>();
        inputs.put(new Integer[] {Input.Keys.UP     }, "BF NOTE UP"         );
        inputs.put(new Integer[] {Input.Keys.LEFT   }, "BF NOTE LEFT"       );
        inputs.put(new Integer[] {Input.Keys.DOWN   }, "BF NOTE DOWN"       );
        inputs.put(new Integer[] {Input.Keys.RIGHT  }, "BF NOTE RIGHT"      );

        inputs.put(new Integer[] {Input.Keys.W      }, "BF NOTE UP MISS"    );
        inputs.put(new Integer[] {Input.Keys.A      }, "BF NOTE LEFT MISS"  );
        inputs.put(new Integer[] {Input.Keys.S      }, "BF NOTE DOWN MISS"  );
        inputs.put(new Integer[] {Input.Keys.D      }, "BF NOTE RIGHT MISS" );

        inputs.forEach((keys, prefix) -> {
            for (Integer key : keys) {
                if (Gdx.input.isKeyJustPressed(key)) {
                    sprite.setCurrentAnimation(prefix);
                }
            }
        });
        //endregion

        sprite.update();
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
        //TextureRegion frame = sprite.getAnimationFrame(sprite.getCurrentAnimation().name, (int) time, true);//.getCurrentAnimationFrame((int) time, false);
        //if (frame != null) batch.draw(frame, -350f/2, -446f/2);
        sprite.draw(batch, -350f/2, -446f/2, true);

        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

}
