package com.dorashush.game;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AndroidLauncher extends AndroidApplication implements LeaderBoardHandler{

	public static FirebaseDatabase mFirebaseDatabase;
	public static DatabaseReference mLeaderboardReference;
	private ArrayList<ScoreLine> scoreList;
	private ArrayList<String> scoreListAsString;
	public static Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			//Adding new score to the data base
			String playerName =((Bundle)msg.getData()).getString("name","fail");
			float playerScore = ((Bundle)msg.getData()).getFloat("score",0);

			//add The new high score
			mLeaderboardReference.push().setValue(new ScoreLine(playerName,playerScore));
		}
	};


	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mFirebaseDatabase = FirebaseDatabase.getInstance();
		mLeaderboardReference = mFirebaseDatabase.getReference().child("leaderboard");

		scoreList = new ArrayList<ScoreLine>();
		scoreListAsString = new ArrayList<String>();
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new FlappyPug(this), config);

		mLeaderboardReference.orderByChild("score").limitToLast(100).addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {
				ArrayList<ScoreLine> temp = new ArrayList<ScoreLine>();
				scoreList.clear();
				for (DataSnapshot areaSnapshot: dataSnapshot.getChildren()) {
					temp.add(areaSnapshot.getValue(ScoreLine.class));
				}
				Collections.sort(temp, new ScoreLine.CustomComparator());

				scoreList.addAll(temp);
			}

			@Override
			public void onCancelled(DatabaseError databaseError) {

			}
		});


	}

	@Override
	public void addPlayerScoreToDataBase(String name, float score) {
		Bundle bundle = new Bundle();
		bundle.putString("name",name);
		bundle.putFloat("score",score);

		Message highScore = new Message();

		highScore.setData(bundle);

		handler.sendMessage(highScore);
	}

	@Override
	public ArrayList<String> getScoreList() {
		scoreListAsString.clear();

		for (ScoreLine sl : scoreList){
			scoreListAsString.add(sl.toString());
		}

		return scoreListAsString;
	}
}
