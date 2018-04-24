package com.dorashush.game.Screens;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.dorashush.game.FlappyPug;
import com.dorashush.game.Scenes.Hud;
import com.dorashush.game.Sprites.BottomObstcale;
import com.dorashush.game.Sprites.Dog;
import com.dorashush.game.Sprites.Ground;
import com.dorashush.game.Sprites.TopObstcale;
import com.dorashush.game.Sprites.Sky;
import com.dorashush.game.Tools.WorldContactListener;

/**
 * Created by Dor on 03/27/18.
 */

public class PlayScreen implements Screen ,InputProcessor{
    private FlappyPug game;

    private static final int TUBE_SPACING = 150;
    private static final int TUBE_COUNT = 2;
    private static final int START_POSITION_SPACING = 300;
    public static final float STARTING_SPEED = (float)-1;
    public static boolean SPEED_BOOST = false;
    private World world;
    private Box2DDebugRenderer b2dr;
    private Dog dog;

    private Ground ground1 , ground2;
    private Sky sky1, sky2;
    private OrthographicCamera gameCam;
    private TextureRegion backGround;

    private Array<TopObstcale> topObstacles;
    private Array<BottomObstcale> bottomObstacles;


    private AssetManager manager;

    private Hud hud;
    private boolean screenPressed;
   // GestureDetector gestureDetector;

    private TextureRegion restartBtn;

    public PlayScreen(FlappyPug game) {
        this.manager = game.getManager();
        this.game = game;

        //skin = manager.get("assets/textSkin/glassy-ui.json");
        gameCam = new OrthographicCamera();
        gameCam.setToOrtho(false, FlappyPug.WIDTH / 2 /FlappyPug.PPM, FlappyPug.HEIGHT / 2/FlappyPug.PPM);

        world = new World(new Vector2(0,0),true);
        world.setContactListener(new WorldContactListener());
        hud = new Hud(game.batch);

       // gestureDetector = new GestureDetector(this);
        //Gdx.input.setInputProcessor(gestureDetector);
        Gdx.input.setInputProcessor(this);

        dog  = new Dog(this);
        ground1 = new Ground(this,0);
        ground2 = new Ground(this,480/FlappyPug.PPM);
        sky1 = new Sky(this,0);
        sky2 = new Sky(this,480/FlappyPug.PPM);
        backGround = new TextureRegion(manager.get("images/background.png",Texture.class));

        topObstacles = new Array<TopObstcale>();
        bottomObstacles = new Array<BottomObstcale>();

        for(int i = 0 ; i< TUBE_COUNT ; i++){
            topObstacles.add(new TopObstcale(this,START_POSITION_SPACING+i*(TUBE_SPACING+TopObstcale.TUBE_WIDTH)));
            bottomObstacles.add(new BottomObstcale(this,START_POSITION_SPACING+i*(TUBE_SPACING+BottomObstcale.TUBE_WIDTH),topObstacles.get(i).getPoisitionY()));
        }


        b2dr = new Box2DDebugRenderer();

    }


    public void handleInput(float dt){

        if(dog.currentState != Dog.State.DEAD) {
/*
            if (Gdx.input.justTouched()) {
               //dog.Fly();
                screenPressed = true;
            }

            else{
                screenPressed = false;
            }
            */
            if(screenPressed){
                dog.Fly();
            }
        }

    }

    public void update(float dt){
        hud.update(dt);
        updateGround(dt);
        updateSky(dt);

        if(hud.getCountDownTimer()<=0) {
            handleInput(dt);
            world.step(1 / 60f, 6, 2);
            dog.update(dt);

            updateObstcale(dt);

        }


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

        //b2dr.render(world,gameCam.combined);
        //stage.draw();
        game.batch.setProjectionMatrix(gameCam.combined);
        game.batch.begin();
        game.batch.draw(backGround,0,0,FlappyPug.WIDTH/2f/FlappyPug.PPM,FlappyPug.HEIGHT/2f/FlappyPug.PPM);

        for(int i  = 0 ; i<topObstacles.size ; i++) {
            (topObstacles.get(i)).draw(game.batch);
            (bottomObstacles.get(i)).draw(game.batch);
        }
        dog.draw(game.batch);


        game.batch.end();

        hud.stage.draw();
        b2dr.render(world,gameCam.combined);

        if(gameOver()){
            //Todo change to Game over screen when Implmented
            game.setScreen(new PlayScreen(game));
            dispose();
        }
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
        world.dispose();
        b2dr.dispose();
        hud.dispose();
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

        if(gameCam.position.x - (gameCam.viewportWidth / 2)> ground1.getPoisition() + ground1.getWidth())
         ground1.setPos(ground1.getWidth()*2);

        if(gameCam.position.x - (gameCam.viewportWidth / 2)> ground2.getPoisition() + ground2.getWidth())
            ground2.setPos(ground2.getWidth()*2);

    }

    private void updateSky(float dt){
        sky1.update(dt);
        sky2.update(dt);

        if(gameCam.position.x - (gameCam.viewportWidth / 2) > sky1.getPoisition() + sky1.getWidth()) {
            sky1.setPos(sky1.getWidth() * 2);
        }

        if(gameCam.position.x - (gameCam.viewportWidth / 2) > sky2.getPoisition() + sky2.getWidth())
            sky2.setPos(sky2.getWidth()*2);
    }

    private void updateObstcale(float dt){

        for(int i  = 0 ; i<topObstacles.size ; i++) {
            TopObstcale topObstcale = topObstacles.get(i);
            BottomObstcale bottomObstcale = bottomObstacles.get(i);

            topObstcale.update(dt);
            bottomObstcale.update(dt);

            if(gameCam.position.x - (gameCam.viewportWidth / 2) > topObstcale.getPoisitionX() + topObstcale.getWidth()){
                topObstcale.reposition((TUBE_SPACING+TopObstcale.TUBE_WIDTH)*TUBE_COUNT);
                bottomObstcale.reposition((TUBE_SPACING+BottomObstcale.TUBE_WIDTH)*TUBE_COUNT,topObstcale.getPoisitionY());
            }

        }

    }

    public boolean gameOver(){
        if(dog.currentState == Dog.State.DEAD){
            //Stoping the hud score coutner
            hud.setGameOver();
        }

        if(dog.currentState == Dog.State.DEAD && dog.getStateTimer() > 2){
            return true;
        }
        return false;
    }

    private void showGameOverMenu(){
        restartBtn = new TextureRegion(manager.get("images/restartbtn.png",Texture.class));
       //todo add btn functionality

    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        screenPressed= true;
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        screenPressed = false;
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
