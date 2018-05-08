package com.dorashush.game;

import java.util.Comparator;

class ScoreLine {
  public String name;
  public float score;

    public ScoreLine(){
    }

  public ScoreLine(String name, float score){
      this.name=name;
      this.score=score;
  }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
      return String.format("%-15s %3.2f",name,score) ;
        //return   name  + "       " + score ;
    }

    public static class CustomComparator implements Comparator<ScoreLine> {


        @Override
        public int compare(ScoreLine o1, ScoreLine o2) {

            return -1*Float.compare(o1.score,o2.score);
        }
    }
}
