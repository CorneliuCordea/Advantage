package com.cordea.tennis.advantage;

import java.sql.Timestamp;
import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.cordea.tennis.advantage.util.SystemUiHider;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class AdvantageActivity extends Activity {
	/**
	 * Whether or not the system UI should be auto-hidden after
	 * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
	 */
	private static final boolean AUTO_HIDE = true;

	/**
	 * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
	 * user interaction before hiding the system UI.
	 */
	private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

	/**
	 * If set, will toggle the system UI visibility upon interaction. Otherwise,
	 * will show the system UI visibility upon interaction.
	 */
	private static final boolean TOGGLE_ON_CLICK = false;

	/**
	 * The flags to pass to {@link SystemUiHider#getInstance}.
	 */
	private static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION;

	/**
	 * The instance of the {@link SystemUiHider} for this activity.
	 */
	private SystemUiHider mSystemUiHider;

	private RadioButton indoorYES;
	private RadioButton indoorNO;

	private RadioButton competitionYES;
	private RadioButton competitionNO;

	private RadioButton scoring2_3;
	private RadioButton scoring2_3STB;

	private RadioButton opponentRating5;
	private RadioButton opponentRating6;
	private RadioButton opponentRating7;

	private RadioButton pebbleuserRating5;
	private RadioButton pebbleuserRating6;
	private RadioButton pebbleuserRating7;

	private EditText comments;

	private EditText pebbleuserName;

	private EditText uuidValue;

	private EditText opponentName;

	private Button generateUUIDButton;

	private Button createUpdateMatchButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_advantage);

		initialize();
		findViewById(R.id.create_update_match).setOnTouchListener(
				mDelayHideTouchListener);
	}

	private void initialize() {
		indoorYES = (RadioButton) findViewById(R.id.indoorYES);
		indoorNO = (RadioButton) findViewById(R.id.indoorNO);
		indoorNO.setChecked(!LightPersist.indoor);
		indoorYES.setChecked(LightPersist.indoor);

		competitionYES = (RadioButton) findViewById(R.id.competitionYES);
		competitionNO = (RadioButton) findViewById(R.id.competitionNO);
		competitionNO.setChecked(!LightPersist.competition);
		competitionYES.setChecked(LightPersist.competition);

		scoring2_3 = (RadioButton) findViewById(R.id.scoring2_3);
		scoring2_3STB = (RadioButton) findViewById(R.id.scoring2_3TB);
		if (LightPersist.scoring.equals("2/3"))
			scoring2_3.setChecked(true);
		else
			scoring2_3STB.setChecked(true);

		opponentRating5 = (RadioButton) findViewById(R.id.opponentRating5);
		opponentRating6 = (RadioButton) findViewById(R.id.opponentRating6);
		opponentRating7 = (RadioButton) findViewById(R.id.opponentRating7);
		switch (LightPersist.opponentRating) {
		case 5:
			opponentRating5.setChecked(true);
		case 6:
			opponentRating6.setChecked(true);
		case 7:
			opponentRating7.setChecked(true);
		default:
			opponentRating6.setChecked(true);
		}

		pebbleuserRating5 = (RadioButton) findViewById(R.id.pebbleuserRating5);
		pebbleuserRating6 = (RadioButton) findViewById(R.id.pebbleuserRating6);
		pebbleuserRating7 = (RadioButton) findViewById(R.id.pebbleuserRating7);
		switch (LightPersist.pebbleuserRating) {
		case 5:
			pebbleuserRating5.setChecked(true);
		case 6:
			pebbleuserRating6.setChecked(true);
		case 7:
			pebbleuserRating7.setChecked(true);
		default:
			pebbleuserRating6.setChecked(true);
		}

		pebbleuserName = (EditText) findViewById(R.id.pebbleuserTextField);
		pebbleuserName.setText(LightPersist.pebbleuserName);

		opponentName = (EditText) findViewById(R.id.opponentEditText);
		if (!LightPersist.opponentName.equals("Name"))
			opponentName.setText(LightPersist.opponentName);
		else
			opponentName.setHint(LightPersist.opponentName);

		uuidValue = (EditText) findViewById(R.id.uuidText);
		uuidValue.setText(LightPersist.uuid);
		generateUUIDButton = (Button) findViewById(R.id.uuidGenerate);
		generateUUIDButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				LightPersist.uuid = UUID.randomUUID().toString();
				LightPersist.pointIndex = 0;
				uuidValue.setText(LightPersist.uuid);
			}
		});

		createUpdateMatchButton = (Button) findViewById(R.id.create_update_match);
		createUpdateMatchButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				PostMessageSender postMessageSender = new PostMessageSender();
				postMessageSender
						.sendJson(createMatchJson(),
								"http://1-dot-advantagecorneliu.appspot.com/rest/receiver/add/match");
				// PostMessageSender.getPointsQueue().clear();
				Toast.makeText(getApplicationContext(),
						"Match created/updated!", Toast.LENGTH_SHORT).show();

				comments.setText("");
			}
		});

		comments = (EditText) findViewById(R.id.commentsEditArea);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		delayedHide(100);
	}

	/**
	 * Touch listener to use for in-layout UI controls to delay hiding the
	 * system UI. This is to prevent the jarring behavior of controls going away
	 * while interacting with activity UI.
	 */
	View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
		@Override
		public boolean onTouch(View view, MotionEvent motionEvent) {
			if (AUTO_HIDE) {
				delayedHide(AUTO_HIDE_DELAY_MILLIS);
			}
			return false;
		}
	};

	Handler mHideHandler = new Handler();
	Runnable mHideRunnable = new Runnable() {
		@Override
		public void run() {
			// mSystemUiHider.hide();
		}
	};

	/**
	 * Schedules a call to hide() in [delay] milliseconds, canceling any
	 * previously scheduled calls.
	 */
	private void delayedHide(int delayMillis) {
		mHideHandler.removeCallbacks(mHideRunnable);
		mHideHandler.postDelayed(mHideRunnable, delayMillis);
	}

	private JSONObject createMatchJson() {
		JSONObject json = new JSONObject();
		try {
			if (competitionYES.isChecked())
				json.put("matchType", "Competition");
			else
				json.put("matchType", "Friendly");

			java.util.Date date = new java.util.Date();
			json.put("matchTime", new Timestamp(date.getTime()));

			if (scoring2_3.isChecked())
				json.put("scoringType", "2/3");
			else
				json.put("scoringType", "2/3STB");

			if (indoorYES.isChecked())
				json.put("indoor", "true");
			else
				json.put("indoor", "false");

			json.put("matchUUID", uuidValue.getText());

			json.put("pebbleUser", pebbleuserName.getText());

			if (pebbleuserRating5.isChecked())
				json.put("pebbleUserTpRating", 5);
			else if (pebbleuserRating6.isChecked())
				json.put("pebbleUserTpRating", 6);
			else if (pebbleuserRating7.isChecked())
				json.put("pebbleUserTpRating", 7);

			json.put("opponent", opponentName.getText());

			if (opponentRating5.isChecked())
				json.put("opponentTpRating", 5);
			else if (opponentRating6.isChecked())
				json.put("opponentTpRating", 6);
			else if (opponentRating7.isChecked())
				json.put("opponentTpRating", 7);

			json.put("comments", comments.getText());

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return json;
	}
}
