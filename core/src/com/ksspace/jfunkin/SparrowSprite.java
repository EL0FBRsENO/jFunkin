package com.ksspace.jfunkin;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class SparrowSprite {

    public Texture image;
    private final int frameRate;

    private static class SpriteAnimation<T> extends Animation {

        public String name;

        public SpriteAnimation(String name, float frameDuration, Array<? extends T> keyFrames) {
            super(frameDuration, keyFrames);
            this.name = name;
        }

    }
    public Array<SpriteAnimation> animations;
    public SpriteAnimation currentAnimation;

    public SparrowSprite(String fileName, int frameRate) {
        //this.sprites = new TextureRegion(img, 5388, 509, 395, 416);
        this.animations = new Array<>();
        this.frameRate = frameRate;
        try {
            File file = Gdx.files.local(fileName + ".xml").file();
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(file);
            NodeList subTextureNodeList = document.getElementsByTagName("SubTexture");

            Element textureAtlas = document.getDocumentElement();
            String imagePath = textureAtlas.getAttribute("imagePath");
            this.image = new Texture(imagePath);

            //this.subTextures = //{new Animation[subTextureNodeList.getLength()];

            String lastName = "";
            boolean isFirstAnimation = true;
            Array<TextureRegion> textures = new Array<>();
            for (int i = 0; i < subTextureNodeList.getLength(); i++) {
                Element futureSubTextureElement = null;
                Element subTextureElement       = (Element) subTextureNodeList.item(i);
                if (i < subTextureNodeList.getLength())
                    futureSubTextureElement = (Element) subTextureNodeList.item(i + 1);

                String name = subTextureElement.getAttribute("name");
                String futureName = "";
                if (futureSubTextureElement != null)
                    futureName = futureSubTextureElement.getAttribute("name");
                int x       = Integer.parseInt(subTextureElement.getAttribute("x"));
                int y       = Integer.parseInt(subTextureElement.getAttribute("y"));
                int width   = Integer.parseInt(subTextureElement.getAttribute("width"));
                int height  = Integer.parseInt(subTextureElement.getAttribute("height"));

                if (!futureName.isEmpty()) futureName = futureName.substring(0, futureName.length() - 4);
                name = name.substring(0, name.length() - 4);

                //System.out.println(name + ": " + x + "/" + y + " | " + width + "/" + height);
                textures.add(new TextureRegion(image, x, y, width, height));
                if (!name.equals(futureName)) {
                    animations.add(new SpriteAnimation(name, 41, textures));
                    textures.clear();
                }
            }
        } catch (ParserConfigurationException | IOException | SAXException e) {
            throw new RuntimeException(e);
        }

        currentAnimation = (SpriteAnimation) getAnimation("BF idle dance");

        System.out.println("'" + fileName + "' Animations \t(" + this.animations.size + " animations):");
        for (SpriteAnimation value : this.animations) {
            System.out.println("\t" + value.name + "\t\t\t(" + value.getKeyFrames().length + " frames)");
            // Print all the TexturesRegion that are inside the Animations
            /*for (Object tex : value.getKeyFrames()) {
                System.out.println("\t" + tex.toString());
            }*/
        }
    }

    public Animation<TextureRegion> getAnimation(String prefix) {
        for (SpriteAnimation anim : this.animations) {
            if (!anim.name.equals(prefix)) continue;

            return (Animation<TextureRegion>) anim;
        }

        return null;
    }

    public TextureRegion getAnimationFrame(String prefix, int frame, boolean looping) {
        Animation anim = getAnimation(prefix);
        float timeStep      = (float) this.getAnimationFrameRate() / 60;
        int animFrameCount  = anim.getKeyFrames().length;
        float stateTime     = frame * animFrameCount * timeStep;
        TextureRegion textureRegion = (TextureRegion) anim.getKeyFrame(stateTime, true);
        return textureRegion;
    }

    public int getAnimationFrameRate() {
        return this.frameRate;
    }

    public SpriteAnimation getCurrentAnimation() {
        return currentAnimation;
    }

    public TextureRegion getCurrentAnimationFrame(int frame, boolean looping) {
        return getAnimationFrame(getCurrentAnimation().name, frame, looping);
    }

}
