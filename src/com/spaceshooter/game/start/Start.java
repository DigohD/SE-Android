package com.spaceshooter.game.start;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.spaceshooter.game.menu.TabMenu;

public class Start extends Activity {

	public boolean musicState;
	public boolean sfxState;

	private static final String BASE64_PUBLIC_KEY = "ADD_THE_REAL_KEY_HERE";
	private static final byte[] SALT = new byte[] { -118, -13, 32, 100, -74,
			46, 106, 41, -123, -55, 89, 78, 16, 20, -57, -120, 7, 91, -102, 86 };
	private Handler mHandler;
	// private LicenseChecker mChecker;
	// private LicenseCheckerCallback mLicenseCheckerCallback;
	boolean licensed;
	boolean checkingLicense;
	boolean didCheck;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getSettings();
		// String deviceId = Secure.getString(getContentResolver(),
		// Secure.ANDROID_ID);
		// Log.i("Device Id", deviceId); // AN EXAMPLE OF LOGGING THAT YOU
		// SHOULD
		// // BE DOING :)
		// mHandler = new Handler();
		// mLicenseCheckerCallback = new MyLicenseCheckerCallback();
		// mChecker = new LicenseChecker(this, new ServerManagedPolicy(this,
		// new AESObfuscator(SALT, getPackageName(), deviceId)),
		// BASE64_PUBLIC_KEY);
		// doCheck();
		licensed = true;
		if (licensed) {
			openStartMenu();
		}
	}

	public void getSettings() {
		// TODO: add SharedPreference functions
		musicState = true;
		sfxState = true;
	}

	// private void doCheck() {
	// didCheck = false;
	// checkingLicense = true;
	// setProgressBarIndeterminateVisibility(true);
	// mChecker.checkAccess(mLicenseCheckerCallback);
	// }
	//
	// private class MyLicenseCheckerCallback implements LicenseCheckerCallback
	// {
	//
	// @Override
	// public void allow(int reason) {
	// // TODO Auto-generated method stub
	// if (isFinishing()) {
	// // Don't update UI if Activity is finishing.
	// return;
	// }
	// Log.i("License", "Accepted!");
	//
	// // You can do other things here, like saving the licensed status to
	// // a
	// // SharedPreference so the app only has to check the license once.
	//
	// licensed = true;
	// checkingLicense = false;
	// didCheck = true;
	// }
	//
	// @SuppressWarnings("deprecation")
	// @Override
	// public void dontAllow(int reason) {
	// // TODO Auto-generated method stub
	// if (isFinishing()) {
	// // Don't update UI if Activity is finishing.
	// return;
	// }
	// Log.i("License", "Denied!");
	// Log.i("License", "Reason for denial: " + reason);
	//
	// // You can do other things here, like saving the licensed status to
	// // a
	// // SharedPreference so the app only has to check the license once.
	//
	// licensed = false;
	// checkingLicense = false;
	// didCheck = true;
	// showDialog(0);
	// }
	//
	// @SuppressWarnings("deprecation")
	// @Override
	// public void applicationError(int reason) {
	// // TODO Auto-generated method stub
	// Log.i("License", "Error: " + reason);
	// if (isFinishing()) {
	// // Don't update UI if Activity is finishing.
	// return;
	// }
	// licensed = true;
	// checkingLicense = false;
	// didCheck = false;
	// showDialog(0);
	// }
	// }
	//
	// protected Dialog onCreateDialog(int id) {
	// // We have only one dialog.
	// return new AlertDialog.Builder(this)
	// .setTitle("Application not licensed")
	// .setMessage(
	// "This application is not licensed, please install it from the Play Store to use it.")
	// .setPositiveButton("Play Store listing",
	// new DialogInterface.OnClickListener() {
	// public void onClick(DialogInterface dialog,
	// int which) {
	// Intent marketIntent = new Intent(
	// Intent.ACTION_VIEW,
	// Uri.parse("http://market.android.com/details?id="
	// + getPackageName()));
	// startActivity(marketIntent);
	// finish();
	// }
	// })
	// .setNegativeButton("Exit",
	// new DialogInterface.OnClickListener() {
	// public void onClick(DialogInterface dialog,
	// int which) {
	// finish();
	// }
	// })
	// .setNeutralButton("Re-Check",
	// new DialogInterface.OnClickListener() {
	// public void onClick(DialogInterface dialog,
	// int which) {
	// doCheck();
	// }
	// })
	//
	// .setCancelable(false)
	// .setOnKeyListener(new DialogInterface.OnKeyListener() {
	// public boolean onKey(DialogInterface dialogInterface,
	// int i, KeyEvent keyEvent) {
	// Log.i("License", "Key Listener");
	// finish();
	// return true;
	// }
	// }).create();
	// }

	public void openStartMenu() {
		Intent intent = new Intent(this, TabMenu.class);
		intent.putExtra("EXTRA_musicState", musicState);
		intent.putExtra("EXTRA_sfxState", sfxState);
		startActivity(intent);
		finish();
	}
}
