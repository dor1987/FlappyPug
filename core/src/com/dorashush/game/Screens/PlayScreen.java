package com.dorashush.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.dorashush.game.FlappyPug;
import com.dorashush.game.Sprites.Dog;
import com.dorashush.game.Sprites.Ground;
import com.dorashush.game.Sprites.Sky;
import com.dorashush.game.Tools.WorldContactListener;

/**
 * Created by Dor on 03/27/18.
 */

public class PlayScreen implements Screen {
    private FlappyPug game;
   /*
    private static final int TUBE_SPACING = 125;
    private static final int TUBE_COUNT = 4;
    */
    private static final int GROUND_Y_OFFSET = -50;
    private World world;
    private Box2DDebugRenderer b2dr;
    private Dog dog;
    private Texture backGround;
    //private Texture bottom,top;
    private Ground ground1 , ground2;
    private Sky sky1, sky2;

    private Vector2 groundPos1, groundPos2;
    private OrthographicCamera gameCam;

    //private Array<Obstacle> obstacle;
    private AssetManager manager;
private FitViewport gamePort;

    public PlayScreen(FlappyPug game) {
        this.manager = game.getManager();
        this.game = game;
        gameCam = new OrthographicCamera();
        gameCam.setToOrtho(false, FlappyPug.WIDTH / 2, FlappyPug.HEIGHT / 2);

        world = new World(new Vector2(0,0),true);
        world.setContactListener(new WorldContactListener());

        dog  = new Dog(this);
        ground1 = new Ground(this,0);
        ground2 = new Ground(this,480);
        sky1 = new Sky(this,0);
        sky2 = new Sky(this,480);

        b2dr = new Box2DDebugRenderer();

    }


    public void handleInput(float dt){
        if(Gdx.input.justTouched())
            dog.Fly();
    }

    public void update(float dt){
        handleInput(dt);
        world.step(1/60f,6,2);
        dog.update(dt);




        updateGround(dt);
        updateSky(dt);
    //    game.batch.begin();
  //      dog.draw(game.batch);
//        game.batch.end();
    }


    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        update(delta);

        game.batch.setProjectionMatrix(gameCam.combined);
        b2dr.render(world,gameCam.combined);

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    public World getWorld(){
        return world;
    }

    public AssetManager getManager() {
        return manager;
    }

    private void updateGround(float dt){
        ground1.update(dt);
        ground2.update(dt);

        if(gameCam.position.x - (gameCam.viewportWidth / 2) > ground1.getPoisition() + ground1.getWidth())
         ground1.setPos(ground1.getWidth()*2);

        if(gameCam.position.x - (gameCam.viewportWidth / 2) > ground2.getPoisition() + ground2.getWidth())
            ground2.setPos(ground2.getWidth()*2);
    }

    private void updateSky(float dt){
        sky1.update(dt);
        sky2.update(dt);

        if(gameCam.position.x - (gameCam.viewportWidth / 2) > sky1.getPoisition() + sky1.getWidth())
            sky1.setPos(sky1.getWidth()*2);

        if(gameCam.position.x - (gameCam.viewportWidth / 2) > sky2.getPoisition() + sky2.getWidth())
            sky2.setPos(sky2.getWidth()*2);
    }

}
