package com.henry.mserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by hyun on 1/3/2015.
 */
public class ServerThread extends Thread {

	private ServerSocket mServerSocket = null;
	private int mPort = 0;
	private ArrayList<CommThread> mCommThreads;

	public ServerThread(int port) {
		this.mPort = port;
		mCommThreads = new ArrayList<CommThread>();
	}

	public void run() {

		Socket socket = null;

		try {
			mServerSocket = new ServerSocket(mPort);

		} catch (IOException e) {
			e.printStackTrace();
		}

		while( !Thread.currentThread().isInterrupted()) {

			try {
				socket = mServerSocket.accept();

				CommThread commThread = new CommThread(socket);
				mCommThreads.add(commThread);
				new Thread(commThread).start();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void terminate() {

		// terminate all CommThread
		for (CommThread ct : mCommThreads) {
			ct.terminate();
		}

		mCommThreads.clear();

		try {
			mServerSocket.close();

		} catch (IOException e) {
			e.printStackTrace();;
		}
	}
}
