package com.ksspace.jfunkin;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector4;
import com.badlogic.gdx.utils.Array;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static com.ksspace.jfunkin.Funkin.paths.images.characters;

public class SparrowSprite {

    public Texture image;
    public Array<SparrowSpriteAnimation> animations;
    public SparrowSpriteAnimation currentAnimation;
    private float frameIndex;
    public SparrowSprite(String fileName) {

        this.animations = new Array<>();
        try {
            File file = Gdx.files.local(characters + fileName + ".xml").file();
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(file);
            NodeList subTextureNodeList = document.getElementsByTagName("SubTexture");

            System.out.printf("<Funkin>: Searching for .png with name '%s'.. (currently at: %s)", fileName, characters);
            String imagePath = characters + fileName + ".png"; //textureAtlas.getAttribute("imagePath");
            this.image = new Texture(imagePath);
            System.out.println(" | .png found!");

            Array<TextureRegion> textures = new Array<>();
            ArrayList<Vector4> _framesPositions = new ArrayList<>();
            for (int i = 0; i < subTextureNodeList.getLength(); i++) {
                Element futureSubTextureElement = null;
                Element subTextureElement = (Element) subTextureNodeList.item(i);
                if (i < subTextureNodeList.getLength())
                    futureSubTextureElement = (Element) subTextureNodeList.item(i + 1);

                String name = subTextureElement.getAttribute("name");
                String futureName = "";
                if (futureSubTextureElement != null)
                    futureName = futureSubTextureElement.getAttribute("name");
                int x = Integer.parseInt(subTextureElement.getAttribute("x"));
                int y = Integer.parseInt(subTextureElement.getAttribute("y"));
                int width = Integer.parseInt(subTextureElement.getAttribute("width"));
                int height = Integer.parseInt(subTextureElement.getAttribute("height"));
                int frameX = Integer.parseInt(subTextureElement.getAttribute("frameX"));
                int frameY = Integer.parseInt(subTextureElement.getAttribute("frameY"));
                int frameWidth = Integer.parseInt(subTextureElement.getAttribute("frameWidth"));
                int frameHeight = Integer.parseInt(subTextureElement.getAttribute("frameHeight"));

                if (!futureName.isEmpty()) futureName = futureName.substring(0, futureName.length() - 4);
                name = name.substring(0, name.length() - 4);

                //System.out.println(name + ": " + x + "/" + y + " | " + width + "/" + height + " | " + frameX + "/" + frameY);
                textures.add(new TextureRegion(image, x, y, width, height));
                _framesPositions.add(new Vector4(frameX, frameY, frameWidth, frameHeight));
                if (!name.equals(futureName)) {
                    animations.add(new SparrowSpriteAnimation(name, 1f, textures, _framesPositions.toArray(new Vector4[0])));
                    textures.clear();
                    _framesPositions.clear();
                }
            }
        } catch (ParserConfigurationException | IOException | SAXException e) {
            throw new RuntimeException(e);
        }

        String leftAlignFormat = "| %-40s | %-11s |%n";

        __printSomeRandomLinesOverThere();
        System.out.format(leftAlignFormat, "'" + fileName + "' Animations (" + this.animations.size + " animations)", "Frame Count");
        __printSomeRandomLinesOverThere();
        for (SparrowSpriteAnimation spriteAnimation : this.animations) {
            String name = spriteAnimation.name;
            String length = String.valueOf((int) (spriteAnimation.getAnimationDuration() / spriteAnimation.getFrameDuration()));
            System.out.format(leftAlignFormat, name, length);
            // Print all the TexturesRegion that are inside the Animations
            /*for (Object tex : value.getKeyFrames()) {
                System.out.println("\t" + tex.toString());
            }*/
        }
        __printSomeRandomLinesOverThere();

        setCurrentAnimation("BF idle dance");
    }

    public void __printSomeRandomLinesOverThere() {
        System.out.format("+------------------------------------------+-------------+%n");
    }

    public SparrowSpriteAnimation getAnimation(String prefix) {
        for (SparrowSpriteAnimation anim : this.animations) {
            if (!anim.name.equals(prefix)) continue;

            return anim;
        }

        return null;
    }

    public TextureRegion getAnimationFrame(String prefix, int frame, boolean looping) {
        SparrowSpriteAnimation anim = getAnimation(prefix);
        return anim.getKeyFrame(frame, looping);
    }

    public SparrowSpriteAnimation getCurrentAnimation() {
        return currentAnimation;
    }

//    public void setFrameDuration(float frameDuration) {
//        for (SparrowSpriteAnimation animation : this.animations) {
//            animation.setFrameDuration(frameDuration);
//        }
//    }

    public void setCurrentAnimation(String prefix) {
        frameIndex = 0f;
        currentAnimation = getAnimation(prefix);
        System.out.println(prefix);
    }

    public TextureRegion getCurrentAnimationFrame(int frame, boolean looping) {
        return getAnimationFrame(getCurrentAnimation().name, frame, looping);
    }

    public float getFrameIndex() {
        return this.frameIndex;
    }

    public void setFrameIndex(float frameIndex) {
        this.frameIndex = frameIndex;
    }

    public void update() {
        frameIndex %= this.getCurrentAnimation().getAnimationDuration();
    }

//    public void draw(SpriteBatch batch, Vector2 pos, boolean loop) {
//        draw(batch, pos.x, pos.y, loop);
//    }
    public void draw(SpriteBatch batch, float x, float y, boolean loop) {
        TextureRegion frame = this.getCurrentAnimationFrame((int) frameIndex, loop);//.getCurrentAnimationFrame((int) time, false);

        //System.out.println("frame pos " + getCurrentAnimation().framesPos);
        Vector4 framePos = getCurrentAnimation().framesPos[(int) frameIndex];
        if (frame != null) batch.draw(frame, x - framePos.x, y);
    }

    public static class SparrowSpriteAnimation extends Animation<TextureRegion> {

        public String name;
        public Vector4[] framesPos;

        public SparrowSpriteAnimation(String name, float frameDuration, Array<TextureRegion> keyFrames, Vector4[] framesPos) {
            super(frameDuration, keyFrames);
            this.name = name;
            this.framesPos = framesPos;
        }

    }

}
