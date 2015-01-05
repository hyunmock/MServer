package com.henry.mserver;

/**
 * Created by hyun on 1/3/2015.
 */
public enum Code {
	CAPTURE(0x00000001), TOUCH(0x00000002), HOME(0x00000003), START(0x00000004);

	private int _val;
	private Code(int val) {
		this._val = val;
	}

	public int value() {
		return this._val;
	}
}

public class Packet {

	int code;
	int len;

}
