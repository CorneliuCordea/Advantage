package com.cordea.tennis.advantage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.getpebble.android.kit.Constants;
import com.getpebble.android.kit.PebbleKit;
import com.getpebble.android.kit.util.PebbleDictionary;

public class Receiver extends BroadcastReceiver {
	private static final UUID TENNIS_SPIN_UUID = UUID
			.fromString("1f3599a5-cf96-4c09-bbea-aa113e8cc18f");

//	private void log(String message) throws IOException {
//		File scoreFile = new File("/sdcard/tennisspin/", LightPersist.uuid
//				+ ".score");
//		FileWriter writer = new FileWriter(scoreFile, true);
//		writer.append(message + "\n");
//		writer.flush();
//		writer.close();
//	}

	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals(Constants.INTENT_APP_RECEIVE)) {
			final UUID receivedUuid = (UUID) intent
					.getSerializableExtra(Constants.APP_UUID);

			// Pebble-enabled apps are expected to be good citizens and only
			// inspect broadcasts containing their UUID
			if (!TENNIS_SPIN_UUID.equals(receivedUuid)) {
				return;
			}
			final int transactionId = intent.getIntExtra(
					Constants.TRANSACTION_ID, -1);
			final String jsonData = intent.getStringExtra(Constants.MSG_DATA);

			if (jsonData == null || jsonData.equals("")) {
				return;
			}
			PebbleDictionary data = null;
			try {
				data = PebbleDictionary.fromJson(jsonData);
				// do what you need with the data
				PebbleKit.sendAckToPebble(context, transactionId);
			} catch (JSONException e) {
				return;
			}
			try {

				Long point = data.getUnsignedIntegerAsLong(1);
				// // save locally
				 File scoreFile = new File("/sdcard/tennisspin/",
				 LightPersist.uuid + ".score");
				 FileWriter writer = new FileWriter(scoreFile, true);
				 writer.append(point + ", ");
				 writer.flush();
				 writer.close();
				if (point == 0) {
					PostMessageSender sendDeleteLastPoint = new PostMessageSender();
					String url = "http://1-dot-advantagecorneliu.appspot.com/rest/receiver/add/point/"
							+ LightPersist.uuid;
					JSONObject jsonPoint = createPointJson(new Long(1023));
					sendDeleteLastPoint.sendJson(jsonPoint, url);
				} else {
					JSONObject jsonPoint = createPointJson(point);
					PostMessageSender postMessageSender = new PostMessageSender();
					postMessageSender.sendJson(jsonPoint,
							"http://1-dot-advantagecorneliu.appspot.com/rest/receiver/add/point/"
									+ LightPersist.uuid);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} 

		}
	}

	private JSONObject createPointJson(Long point) {
		JSONObject json = new JSONObject();
		try {
			if (PointManager.isPeebbleUserServing(point.intValue()))
				json.put("pebbleuserServing", true);
			else
				json.put("pebbleuserServing", false);
			json.put("firstServe", PointManager.isFirstServe(point.intValue()));
			json.put("doubleFault", PointManager.isDouble(point.intValue()));
			json.put("ace", PointManager.isAce(point.intValue()));
			json.put("win", PointManager.isWin(point.intValue()));
			json.put("winner", PointManager.isWinner(point.intValue()));
			json.put("forced", PointManager.isForced(point.intValue()));

			if (PointManager.isUnforcedActiveOpportune(point.intValue()))
				json.put("unforcedType", "active+");
			else if (PointManager.isUnforcedActiveInopportune(point.intValue()))
				json.put("unforcedType", "active-");
			else if (PointManager.isUnforcedPassive(point.intValue()))
				json.put("unforcedType", "passive");

			if (PointManager.isForehand(point.intValue()))
				json.put("handSide", "FH");
			else
				json.put("handSide", "BH");
			json.put("pointIndex", LightPersist.getPointIndex());
			json.put("pointAtNet", PointManager.isPointAtNet(point.intValue()));

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return json;
	}
}
