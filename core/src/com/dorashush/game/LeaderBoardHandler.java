package com.dorashush.game;

import java.util.ArrayList;

/**
 * Created by Dor on 05/05/18.
 */

public interface LeaderBoardHandler {
        public void addPlayerScoreToDataBase(String name , float score);
        public ArrayList getScoreList();
}
