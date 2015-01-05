package com.henry.mserver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by hyun on 1/3/2015.
 */
public class CommThread extends Thread {

	private Socket              _clientSocket;
	private DataInputStream     _dis;
	private DataOutputStream    _dos;


	public CommThread(Socket clientSocket) {
		_clientSocket = clientSocket;

		try {
			_dis = new DataInputStream(_clientSocket.getInputStream());
			_dos = new DataOutputStream(_clientSocket.getOutputStream());

		} catch(IOException e) {
			e.printStackTrace();;
		}

	}

	public void run() {

		while( !Thread.currentThread().isInterrupted()) {

			byte [] buff = new byte[100];

			try {
				Packet packet = readPacket(_clientSocket);
				handlePacket(packet);

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public void terminate() {
		try {
			_clientSocket.close();

		} catch(IOException e) {
			e.printStackTrace();;
		}

	}

	public Packet readPacket(Socket sock) {

		// read code
		int code = _dis.readInt();


		// read body length
		int length = _dis.readInt();

		// read body

	}

	public static byte[] read(Socket sock, int len) {
		int nLeft = len;
		int nRead = 0;
		int nReadTotal = 0;
		int offset = 0;
		InputStream dis = null;

		byte[] buff = new byte[len];

		try {
			sock.setSoTimeout(10000);

		} catch(IOException e) {
			e.printStackTrace();
			return new byte[0];
		}

		try {
			dis = sock.getInputStream();

		} catch(IOException e) {
			e.printStackTrace();
		}

		while( nLeft > 0 ) {

			try {
				nRead = dis.read(buff, offset, nLeft);

			} catch(IOException e) {
				e.printStackTrace();
			}

			nLeft -= nRead;
			offset += nRead;
			nReadTotal += nRead;
		}

		return buff;
	}

	public int write(Socket sock, byte[] buff, int len) {
		int nLeft = len;
		int nWrite = 0;
		int nWriteTotal = 0;
		int offset = 0;
		OutputStream dos = null;

		try {
			sock.setSoTimeout(10000);

		} catch(IOException e) {
			e.printStackTrace();
			return -1;
		}

		try {
			dos = sock.getOutputStream();

		} catch(IOException e) {
			e.printStackTrace();
		}

		while( nLeft > 0 ) {

			try {
				dos.write();
				nWrite = dos.write(buff, offset, nLeft);

			} catch(IOException e) {
				e.printStackTrace();
			}

			nLeft -= nWrite;
			offset += nWrite;
			nWriteTotal += nWrite;
		}

		return nWriteTotal;
	}

}
