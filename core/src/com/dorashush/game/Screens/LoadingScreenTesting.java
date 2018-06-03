package com.dorashush.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dorashush.game.FlappyPug;

/**
 * Created by Dor on 05/19/18.
 */

public class LoadingScreenTesting implements Screen {
    private FlappyPug game;
    private ShapeRenderer mShapeRenderer;
    private float progress;
    private int progressInt;
    private Texture background, area, fill, fillStart, logo;
    private Skin skin;
    private Label loadingPercentageLabel;
    private BitmapFont loadingPercentageFont;
    //private Viewport mViewport;
    private ExtendViewport extendViewport;
    //private OrthographicCamera mOrthographicCamera;
    private AssetManager manager;

    public LoadingScreenTesting(FlappyPug game) {
        this.game = game;
       // myGdxGame.handler.viewAd(false);
        this.manager = game.getManager();
        this.mShapeRenderer = new ShapeRenderer();
        skin = new Skin(Gdx.files.internal("textSkin/comic-ui.json"));
        getAssetsFromBeforeLoadingScreen();
        queueAssets();

        progress = 0;
       // mOrthographicCamera = new OrthographicCamera(FlappyPug.WIDTH, FlappyPug.HEIGHT);
        //mOrthographicCamera.position.set(FlappyPug.WIDTH / 2, FlappyPug.HEIGHT / 2, 0);

        //mViewport = new StretchViewport(FlappyPug.WIDTH, FlappyPug.HEIGHT, mOrthographicCamera);
        extendViewport = new ExtendViewport(FlappyPug.WIDTH / 2,FlappyPug.HEIGHT / 2,new OrthographicCamera());

        //GameManager.getInstance().mGameData.setMusicOn(true);
    }

    private void getAssetsFromBeforeLoadingScreen() {
        background = game.getManager().get("images/loadingbackground.png", Texture.class);
        area = game.getManager().get("images/area.png", Texture.class);
        //fillStart = mMyGdxGame.getAssetManager().get("loading/FillStart.png", Texture.class);
        fill = game.getManager().get("images/fill.png", Texture.class);
       // loadingPercentageFont = mMyGdxGame.getAssetManager().get("size100.ttf", BitmapFont.class);
        logo = game.getManager().get("images/mylogo.png", Texture.class);

       loadingPercentageLabel = new Label("percent",skin);

    }


    private void queueAssets() {

        manager.load("images/openingscreen.png", Texture.class);
        manager.load("images/mainscreentitle.png", Texture.class);
        manager.load("images/play.png", Texture.class);
        manager.load("images/settings.png", Texture.class);
        manager.load("images/highscore.png", Texture.class);
        manager.load("images/windowpanel.png", Texture.class);
        manager.load("images/spinbtn.png", Texture.class);
        manager.load("images/freespinbtn.png", Texture.class);
        manager.load("images/spinthewheelmenubtn.png", Texture.class);


        manager.load("atlas/animations", TextureAtlas.class);
        manager.load("images/fireon.png", Texture.class);
        manager.load("images/fireoff.png", Texture.class);
        manager.load("images/wall.png", Texture.class);
        manager.load("images/wall2.png", Texture.class);
        manager.load("images/wall3.png", Texture.class);
        manager.load("images/wall4.png", Texture.class);
        manager.load("images/wall5.png", Texture.class);
        manager.load("images/wall6.png", Texture.class);

        manager.load("images/touch.png", Texture.class);


        manager.load("images/background.png", Texture.class);
        manager.load("images/replaybtn.png", Texture.class);
        manager.load("images/score.png", Texture.class);
        manager.load("images/homebtn.png", Texture.class);
        manager.load("images/backgroundfull.png", Texture.class);
      //  manager.load("images/coulds.png", Texture.class);
     //   manager.load("images/ground.png", Texture.class);
        manager.load("images/topbar.png", Texture.class);
        manager.load("images/botbar.png", Texture.class);
        manager.load("images/avatar.png", Texture.class);


        manager.load("images/p.png", Texture.class);
        manager.load("images/u.png", Texture.class);
        manager.load("images/g.png", Texture.class);
        manager.load("images/pnobubble.png", Texture.class);
        manager.load("images/unobubble.png", Texture.class);
        manager.load("images/gnobbuble.png", Texture.class);
        manager.load("images/sandclock.png", Texture.class);
        manager.load("images/speeddown.png", Texture.class);
        manager.load("images/timeaddtion.png", Texture.class);
        manager.load("images/testscoreline.png", Texture.class);
        manager.load("images/currencypowerup.png", Texture.class);

        //spinning wheel
        manager.load("atlas/spin_wheel_ui.atlas", TextureAtlas.class);
        manager.load("images/dimondcounter.png", Texture.class);
        manager.load("images/coincounter.png", Texture.class);
        manager.load("images/spincounterimage.png", Texture.class);
        manager.load("images/bigbtnnotpush.png", Texture.class);
        manager.load("images/bigbtnpushed.png", Texture.class);
        manager.load("images/diamond.png", Texture.class);
        manager.load("images/money.png", Texture.class);
        manager.load("images/wheel.png", Texture.class);
    }

    @Override
    public void show() {

    }

    private void drawAssets(SpriteBatch batch, float dt) {
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        batch.draw(logo, Gdx.graphics.getWidth()/2f-logo.getWidth()/2, Gdx.graphics.getHeight()/2f,Gdx.graphics.getWidth()/4,Gdx.graphics.getWidth()/4);
        batch.draw(area, Gdx.graphics.getWidth()*0.1f, Gdx.graphics.getHeight() / 3f,Gdx.graphics.getWidth()*0.8f,Gdx.graphics.getHeight()/13);

    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        progress = Interpolation.linear.apply(progress, manager.getProgress(), 0.1f);
        game.batch.begin();
        drawAssets(game.batch, delta);

        game.batch.draw(fill, Gdx.graphics.getWidth()*0.1f+area.getWidth()/27, Gdx.graphics.getHeight() / 3f, area.getWidth() *2.8f* progress, Gdx.graphics.getHeight()/13);
        progressInt = Math.round(((progress+0.1f )* 100));
       // game.batch.draw(fillStart, 255, (FlappyPug.HEIGHT / 4f) + area.getHeight() / 3f);

        //loadingPercentageFont.draw(game.batch, String.valueOf(progressInt + 1f) + "%", (Gdx.graphics.getWidth() / 4f), (Gdx.graphics.getHeight() / 5f));
        loadingPercentageLabel.setText(String.valueOf(progressInt + 1f) + "%");
        loadingPercentageLabel.setPosition(Gdx.graphics.getWidth() / 2f-loadingPercentageLabel.getWidth()/2,Gdx.graphics.getHeight() / 3f+area.getHeight()*2);
        //loadingPercentageLabel.setSize(area.getWidth()/5,area.getHeight()*0.8f);
        //loadingPercentageLabel.setBounds(Gdx.graphics.getWidth() / 2f,Gdx.graphics.getHeight() / 4f,area.getWidth()/5,area.getHeight()*0.8f);
        loadingPercentageLabel.setFontScale(5f);
        loadingPercentageLabel.draw(game.batch,1f);
        game.batch.end();

        if (manager.update()) {


            game.setScreen(new MainMenuScreen(game,true));


        }




        //THIS CODE STOPS BAR SHOWING UP


//        while (!mMyGdxGame.getAssetManager().update()) {
//            System.out.println(mMyGdxGame.getAssetManager().getProgress());
//        }


    }

    @Override
    public void resize(int width, int height) {
        extendViewport.update(width, height);
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
        mShapeRenderer.dispose();

    }
}
