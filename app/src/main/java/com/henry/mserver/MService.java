package com.henry.mserver;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

public class MService extends Service {

	private ServerThread    mServerThread;
	private final int SERVERPORT = 6000;

	@Override
	public void onCreate() {
		super.onCreate();

		mServerThread = new ServerThread(SERVERPORT);
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO: Return the communication channel to the service.
		throw new UnsupportedOperationException("Not yet implemented");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();

		// stop server thread
		mServerThread.terminate();

		Toast.makeText(this, "Service Ended", Toast.LENGTH_SHORT).show();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		super.onStartCommand(intent, flags, startId);

		// start server thread
		mServerThread.start();

		Toast.makeText(this, "Service Started", Toast.LENGTH_SHORT).show();

		return START_STICKY;
	}
}
