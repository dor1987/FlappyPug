package com.dorashush.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.dorashush.game.FlappyPug;
import com.dorashush.game.Scenes.EndGameMenu;
import com.dorashush.game.Scenes.Hud;
import com.dorashush.game.Sprites.Background;
import com.dorashush.game.Sprites.BirdEnemy;
import com.dorashush.game.Sprites.BottomObstcale;
import com.dorashush.game.Sprites.Dog;
import com.dorashush.game.Sprites.Enemy;
import com.dorashush.game.Sprites.GLetterPowerUp;
import com.dorashush.game.Sprites.Ground;
import com.dorashush.game.Sprites.PLetterPowerUp;
import com.dorashush.game.Sprites.PowerUp;
import com.dorashush.game.Sprites.SpeedReducePowerUp;
import com.dorashush.game.Sprites.TimeAddPowerUp;
import com.dorashush.game.Sprites.TopObstcale;
import com.dorashush.game.Sprites.Sky;
import com.dorashush.game.Sprites.ULetterPowerUp;
import com.dorashush.game.Tools.WorldContactListener;

import java.util.Random;

import static com.dorashush.game.FlappyPug.SPEED_MODIFIER;
import static com.dorashush.game.FlappyPug.SPEED_TIME_JUMP;

/**
 * Created by Dor on 03/27/18.
 */

public class PlayScreen implements Screen ,InputProcessor{
    private FlappyPug game;

    private static final int TUBE_SPACING = 150;
    private static final int TUBE_COUNT = 2;
    private static final int START_POSITION_SPACING = 300;
    public static final float STARTING_SPEED = (float)-1;
    public static final float POWERUPS_DELAY = 2f;
    public static boolean SPEED_BOOST = false;
    private World world;
    private Box2DDebugRenderer b2dr;
    private Dog dog;
    private Ground ground1 , ground2;
    private Sky sky1, sky2;
    private Background background1,background2;

    private OrthographicCamera gameCam;
    //private TextureRegion backGround;

    private Array<Enemy> topObstacles;
    private Array<Enemy> bottomObstacles;



    private AssetManager manager;

    private Hud hud;
    private boolean screenPressed;
   // GestureDetector gestureDetector;

    private EndGameMenu endGameMenu;


    //animations
    private TextureAtlas atlas;

    //Phases
    private float gameSpeed,timer,phaseTimer;
    private String phaseKind;
    private boolean phaseChanging;

    //powerups
    private float powerUpTimeCount,timeBetweenPowerUps;
    private Array<PowerUp> powerUpArray;
    private PowerUp pLetterPowerUp,uLetterPowerUp,gLetterPowerUp,speedReducePowerUp,timeAddPowerUp;



    public PlayScreen(FlappyPug game) {
        this.manager = game.getManager();
        atlas = manager.get("atlas/animations",TextureAtlas.class);

        this.game = game;

        //skin = manager.get("assets/textSkin/glassy-ui.json");
        gameCam = new OrthographicCamera();
        gameCam.setToOrtho(false, FlappyPug.WIDTH / 2 /FlappyPug.PPM, FlappyPug.HEIGHT / 2/FlappyPug.PPM);

        world = new World(new Vector2(0,0),true);
        world.setContactListener(new WorldContactListener());
        hud = new Hud(game.batch,this);

       // gestureDetector = new GestureDetector(this);
        //Gdx.input.setInputProcessor(gestureDetector);
        //Gdx.input.setInputProcessor(this);
        Gdx.input.setInputProcessor(this);
        gameSpeed =STARTING_SPEED;
        timer = 0;
        phaseTimer = 0;

        dog  = new Dog(this);
        ground1 = new Ground(this,0);
//        ground2 = new Ground(this,480/FlappyPug.PPM);
        ground2 = new Ground(this,769/FlappyPug.PPM);

        sky1 = new Sky(this,0);
//        sky2 = new Sky(this,480/FlappyPug.PPM);
        sky2 = new Sky(this,769/FlappyPug.PPM);



        //backGround = new TextureRegion(manager.get("images/background1.png",Texture.class));
        background1 = new Background(this,0);
        background2 = new Background(this,769/FlappyPug.PPM);


        topObstacles = new Array<Enemy>();
        bottomObstacles = new Array<Enemy>();



        addObstacles();
        endGameMenu = new EndGameMenu(this, game.batch);

        //Powerups
        powerUpTimeCount = 0;
        timeBetweenPowerUps = 10f;


        powerUpArray = new Array<PowerUp>();

        pLetterPowerUp = new PLetterPowerUp(this);
        uLetterPowerUp = new ULetterPowerUp(this);
        gLetterPowerUp = new GLetterPowerUp(this);
        speedReducePowerUp = new SpeedReducePowerUp(this);
        timeAddPowerUp = new TimeAddPowerUp(this);



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
        updateBackground(dt);
        hud.update(dt);
        updateGround(dt);
        updateSky(dt);

        powerUpsAdder(dt);
        powerUpsupdate(dt);

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

        if(dog.currentState!=Dog.State.DEAD){
            Gdx.input.setInputProcessor(this);
            if(dog.CheckIfSpeedrecudeCought()){ //Speed powerup Implment
                gameSpeed*=0.8;
            }
            speedControl(delta);
        }
        //b2dr.render(world,gameCam.combined);
        //stage.draw();
        game.batch.setProjectionMatrix(gameCam.combined);
        game.batch.begin();

       // game.batch.draw(backGround,0,0,FlappyPug.WIDTH/2f/FlappyPug.PPM,FlappyPug.HEIGHT/2f/FlappyPug.PPM);
        background1.draw(game.batch);
        background2.draw(game.batch);


        for(int i  = 0 ; i<topObstacles.size ; i++) {
            (topObstacles.get(i)).draw(game.batch);
            (bottomObstacles.get(i)).draw(game.batch);
        }



            //removeObstacles();

        ground1.draw(game.batch);
        ground2.draw(game.batch);
        sky1.draw(game.batch);
        sky2.draw(game.batch);


        dog.draw(game.batch);


        powerUpRemoveControl();

        game.batch.end();

        hud.stage.draw();

        //b2dr.render(world,gameCam.combined);


        if(gameOver()){
            //Todo change to Game over screen when Implmented
            showGameOverMenu(delta);
            handleMenuTouch();
            //dispose();
            //game.setScreen(new PlayScreen(game));
            //dispose();
        }
    }

    private void handleMenuTouch(){
        if(endGameMenu.isPlayPressed()){
            game.setScreen(new PlayScreen(game));
            dispose();
        }

        else if(endGameMenu.isHomePressed()){
            game.setScreen(new MainMenuScreen(game,false));
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
        endGameMenu.dispose();
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

/*
        if(gameCam.position.x - (gameCam.viewportWidth / 2)> ground1.getPoisition() + ground1.getWidth())
         ground1.setPos(ground1.getWidth()*2);

        if(gameCam.position.x - (gameCam.viewportWidth / 2)> ground2.getPoisition() + ground2.getWidth())
            ground2.setPos(ground2.getWidth()*2);
*/

        if(gameCam.position.x  - (gameCam.viewportWidth/2) > ground1.getPoisition() + ground1.getWidth()) {
            ground1.setPos(ground2.getX()+ground2.getWidth());
        }
        if(gameCam.position.x - (gameCam.viewportWidth/2 ) > ground2.getPoisition() + ground2.getWidth())
            ground2.setPos(ground1.getX()+ground1.getWidth());
    }

    private void updateSky(float dt){
        sky1.update(dt);
        sky2.update(dt);
/*
        if(gameCam.position.x - (gameCam.viewportWidth / 2) > sky1.getPoisition() + sky1.getWidth()) {
            sky1.setPos(sky1.getWidth() * 2);
        }

        if(gameCam.position.x - (gameCam.viewportWidth / 2) > sky2.getPoisition() + sky2.getWidth())
            sky2.setPos(sky2.getWidth()*2);
*/
        if(gameCam.position.x  - (gameCam.viewportWidth/2) > sky1.getPoisition() + sky1.getWidth()) {
            sky1.setPos(sky2.getX()+sky2.getWidth());
        }
        if(gameCam.position.x - (gameCam.viewportWidth/2 ) > sky2.getPoisition() + sky2.getWidth())
            sky2.setPos(sky1.getX()+sky1.getWidth());
    }


    private void updateBackground(float dt){
        background1.update(dt);
        background2.update(dt);

        if(gameCam.position.x  - (gameCam.viewportWidth/2) > background1.getPoisition() + background1.getWidth()) {
            background1.setPos(background2.getX()+background2.getWidth());
        }

        if(gameCam.position.x - (gameCam.viewportWidth/2 ) > background2.getPoisition() + background2.getWidth())
            background2.setPos(background1.getX()+background1.getWidth());
    }
    private void updateObstcale(float dt){
        phaseTimer+=dt;

        for(int i  = 0 ; i<topObstacles.size ; i++) {
            TopObstcale topObstcale = (TopObstcale) topObstacles.get(i);
            BottomObstcale bottomObstcale = (BottomObstcale) bottomObstacles.get(i);

            topObstcale.update(dt);
            bottomObstcale.update(dt);

            if(gameCam.position.x - (gameCam.viewportWidth / 2) > topObstcale.getPoisitionX() + topObstcale.getWidth()){
                    topObstcale.reposition((TUBE_SPACING + TopObstcale.TUBE_WIDTH) * TUBE_COUNT);
                    bottomObstcale.reposition((TUBE_SPACING + BottomObstcale.TUBE_WIDTH) * TUBE_COUNT, topObstcale.getPoisitionY());
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

    private void showGameOverMenu(float dt){

       // restartBtn = new TextureRegion(manager.get("images/replaybtn.png",Texture.class));
        //scoreIcon = new TextureRegion(manager.get("images/score.png",Texture.class));
       endGameMenu.setScore(hud.getTime());
        endGameMenu.draw(dt);

        //todo add btn functionality

    }

    private void speedControl(float dt){
        timer+=dt;
        if(timer >= SPEED_TIME_JUMP) {
            gameSpeed += SPEED_MODIFIER;
            timer=0;
        }

        hud.setSpeed(gameSpeed);
        sky1.setSpeed(gameSpeed);
        sky2.setSpeed(gameSpeed);
        ground1.setSpeed(gameSpeed);
        ground2.setSpeed(gameSpeed);
        background1.setSpeed(gameSpeed);
        background2.setSpeed(gameSpeed);

        for(int i  = 0 ; i<topObstacles.size ; i++) {
            (topObstacles.get(i)).setSpeed(gameSpeed);
            (bottomObstacles.get(i)).setSpeed(gameSpeed);
        }

    }

    public void addObstacles(){
                for (int i = 0; i < TUBE_COUNT; i++) {
                    topObstacles.add(new TopObstcale(this, START_POSITION_SPACING + i * (TUBE_SPACING + TopObstcale.TUBE_WIDTH)));
                    bottomObstacles.add(new BottomObstcale(this, START_POSITION_SPACING + i * (TUBE_SPACING + BottomObstcale.TUBE_WIDTH), topObstacles.get(i).getPoisitionY()));
                }

    }

    public void removeObstacles(){
        for (Enemy enemy : topObstacles) {
            if (enemy.removed)
                topObstacles.removeValue(enemy, true);

            if (enemy != null) {
                enemy.draw(game.batch);
            }
        }

        for (Enemy enemy : bottomObstacles) {
            if (enemy.removed)
                bottomObstacles.removeValue(enemy, true);

            if (enemy != null) {
                enemy.draw(game.batch);
            }
        }
    }
    public int generateNumber(int maxNum) {
        Random random = new Random();
        int result = random.nextInt(maxNum+1); //to avoid maxnum been 0
        return result;
    }

    public void powerUpsAdder(float dt){
        powerUpTimeCount += dt;
        if (powerUpTimeCount >= timeBetweenPowerUps) {
            powerUpArray.add(initlizePowerUp());
            powerUpTimeCount = 0;
            timeBetweenPowerUps = POWERUPS_DELAY+generateNumber(15);
        }

    }
    public void powerUpsupdate(float dt){

        for (PowerUp powerUp : powerUpArray) {
            if(gameCam.position.x - (gameCam.viewportWidth / 2) > powerUp.getX() + powerUp.getWidth())
                powerUp.setToRemove();
            powerUp.update(dt);
        }
    }
    public void powerUpRemoveControl(){
        for (PowerUp powerUp : powerUpArray) {
            if (powerUp.removed)
                powerUpArray.removeValue(powerUp, true);

            if (powerUp != null) {
                powerUp.draw(game.batch);
            }
        }
    }


    public PowerUp initlizePowerUp(){
        PowerUp powerUp;

        int powerUpToInitilize = generateNumber(5);

        switch (powerUpToInitilize){
            case 0:
                pLetterPowerUp.setToAppear();
                powerUp = pLetterPowerUp;
                break;
            case 1:
                uLetterPowerUp.setToAppear();
                powerUp = uLetterPowerUp;
                break;

            case 2:
                gLetterPowerUp.setToAppear();
                powerUp = gLetterPowerUp;
                break;

            case 3:
                speedReducePowerUp.setToAppear();
                powerUp = speedReducePowerUp;
                break;

            case 4:
                timeAddPowerUp.setToAppear();
                powerUp = timeAddPowerUp;
                break;

            default:
                pLetterPowerUp.setToAppear();
                powerUp = pLetterPowerUp;
                break;
        }

        return powerUp;
    }

    public TextureAtlas getAtlas(){
        return atlas;
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
