package com.dorashush.game.Tools;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

/**
 * Created by Dor on 05/12/18.
 */

public class SkinsController {
    private TextureRegion CurrentobstacleTexture,CurrentBackGroundTexture;
    private TextureRegion wall1,wall2,wall3,wall4,wall5,wall6,wall7;
    private AssetManager manager;
    public SkinsController(AssetManager manager) {
        this.manager = manager;
        CurrentBackGroundTexture = new TextureRegion(manager.get("images/backgroundfull.png",Texture.class));

        wall1 = new TextureRegion(manager.get("images/wall.png",Texture.class));
        wall2 = new TextureRegion(manager.get("images/wall2.png",Texture.class));
        wall3 = new TextureRegion(manager.get("images/wall3.png",Texture.class));
        wall4 = new TextureRegion(manager.get("images/wall4.png",Texture.class));
        wall5 = new TextureRegion(manager.get("images/wall5.png",Texture.class));
        CurrentobstacleTexture = wall1;


    }

    public TextureRegion getCurrentobstacleTexture(float gameTime){

    if(gameTime<=20){
        //CurrentobstacleTexture = new TextureRegion(manager.get("images/wall.png",Texture.class));
        CurrentobstacleTexture = wall1;
    }
    else if(gameTime<=40){
        //CurrentobstacleTexture = new TextureRegion(manager.get("images/wall2.png",Texture.class));
        CurrentobstacleTexture = wall2;
    }
    else if(gameTime<=60){
        //CurrentobstacleTexture = new TextureRegion(manager.get("images/wall3.png",Texture.class));
        CurrentobstacleTexture = wall3;

    }
    else if(gameTime<=80){
     //   CurrentobstacleTexture = new TextureRegion(manager.get("images/wall4.png",Texture.class));
        CurrentobstacleTexture = wall4;

    }
    else if(gameTime<=100){
       // CurrentobstacleTexture = new TextureRegion(manager.get("images/wall5.png",Texture.class));
        CurrentobstacleTexture = wall5;

    }
    else if(gameTime<=120){
     //   CurrentobstacleTexture = new TextureRegion(manager.get("images/wall.png",Texture.class));
        CurrentobstacleTexture = wall1;

    }
    else if(gameTime<=140){
        //CurrentobstacleTexture = new TextureRegion(manager.get("images/wall.png",Texture.class));
        CurrentobstacleTexture = wall1;

    }
    else if(gameTime<=160){
     //   CurrentobstacleTexture = new TextureRegion(manager.get("images/wall.png",Texture.class));
        CurrentobstacleTexture = wall1;

    }
    else if(gameTime<=180){
        //CurrentobstacleTexture = new TextureRegion(manager.get("images/wall.png",Texture.class));
        CurrentobstacleTexture = wall1;

    }

    else if(gameTime<=200){
        //CurrentobstacleTexture = new TextureRegion(manager.get("images/wall.png",Texture.class));
        CurrentobstacleTexture = wall1;

    }
    else{
        //CurrentobstacleTexture = new TextureRegion(manager.get("images/wall.png",Texture.class));
        CurrentobstacleTexture = wall1;

    }
    return CurrentobstacleTexture;
    }

    public TextureRegion getCurrentBackGroundTexture(float gameTime){

        if(gameTime<=20){
            CurrentBackGroundTexture = new TextureRegion(manager.get("images/backgroundfull.png",Texture.class));
        }
        else if(gameTime<=40){
            CurrentBackGroundTexture = new TextureRegion(manager.get("images/backgroundfull.png",Texture.class));

        }
        else if(gameTime<=60){
            CurrentBackGroundTexture = new TextureRegion(manager.get("images/backgroundfull.png",Texture.class));

        }
        else if(gameTime<=80){
            CurrentBackGroundTexture = new TextureRegion(manager.get("images/backgroundfull.png",Texture.class));

        }
        else if(gameTime<=100){
            CurrentBackGroundTexture = new TextureRegion(manager.get("images/backgroundfull.png",Texture.class));

        }
        else if(gameTime<=120){
            CurrentBackGroundTexture = new TextureRegion(manager.get("images/backgroundfull.png",Texture.class));

        }
        else if(gameTime<=140){
            CurrentBackGroundTexture = new TextureRegion(manager.get("images/backgroundfull.png",Texture.class));

        }
        else if(gameTime<=160){
            CurrentBackGroundTexture = new TextureRegion(manager.get("images/backgroundfull.png",Texture.class));

        }
        else if(gameTime<=180){
            CurrentBackGroundTexture = new TextureRegion(manager.get("images/backgroundfull.png",Texture.class));

        }

        else if(gameTime<=200){
            CurrentBackGroundTexture = new TextureRegion(manager.get("images/backgroundfull.png",Texture.class));

        }
        else{
            CurrentBackGroundTexture = new TextureRegion(manager.get("images/backgroundfull.png",Texture.class));

        }
        return CurrentBackGroundTexture;
    }
}
