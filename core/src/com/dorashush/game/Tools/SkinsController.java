package com.dorashush.game.Tools;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Dor on 05/12/18.
 */

public class SkinsController {
    private TextureRegion CurrentobstacleTexture,CurrentBackGroundTexture;
    private AssetManager manager;
    public SkinsController(AssetManager manager) {
        this.manager = manager;
        CurrentobstacleTexture = new TextureRegion(manager.get("images/wall.png",Texture.class));
        CurrentBackGroundTexture = new TextureRegion(manager.get("images/backgroundfull.png",Texture.class));


    }

    public TextureRegion getCurrentobstacleTexture(float gameTime){

    if(gameTime<=20){
        CurrentobstacleTexture = new TextureRegion(manager.get("images/wall.png",Texture.class));
    }
    else if(gameTime<=40){
        CurrentobstacleTexture = new TextureRegion(manager.get("images/wall2.png",Texture.class));

    }
    else if(gameTime<=60){
        CurrentobstacleTexture = new TextureRegion(manager.get("images/wall3.png",Texture.class));

    }
    else if(gameTime<=80){
        CurrentobstacleTexture = new TextureRegion(manager.get("images/wall4.png",Texture.class));

    }
    else if(gameTime<=100){
        CurrentobstacleTexture = new TextureRegion(manager.get("images/wall5.png",Texture.class));

    }
    else if(gameTime<=120){
        CurrentobstacleTexture = new TextureRegion(manager.get("images/wall.png",Texture.class));

    }
    else if(gameTime<=140){
        CurrentobstacleTexture = new TextureRegion(manager.get("images/wall.png",Texture.class));

    }
    else if(gameTime<=160){
        CurrentobstacleTexture = new TextureRegion(manager.get("images/wall.png",Texture.class));

    }
    else if(gameTime<=180){
        CurrentobstacleTexture = new TextureRegion(manager.get("images/wall.png",Texture.class));

    }

    else if(gameTime<=200){
        CurrentobstacleTexture = new TextureRegion(manager.get("images/wall.png",Texture.class));

    }
    else{
        CurrentobstacleTexture = new TextureRegion(manager.get("images/wall.png",Texture.class));

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
