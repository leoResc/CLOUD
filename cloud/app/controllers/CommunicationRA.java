package controllers;

import play.Logger;
import play.mvc.Controller;
import play.mvc.Result;

public class CommunicationRA extends Controller {

	static String startSerial = "import serial\n"
			+ "ser = serial.Serial('/dev/ttyACM0',9600)";
	static boolean serialAviable = false;

	public static Result changeMode(int mode) {
		Logger.info("mode " + mode + " requested");
		if (!serialAviable) {
			startConnection();
		}
		System.out.println("ser.write('" + mode + "')");
		return ok();
	}

	public static Result displayArduinoMessage() {
		if (!serialAviable) {
			startConnection();
		}
		System.out.println("ser.readline()");
		return ok();
	}
	
	public static void startConnection() {
		Logger.info("communication started");
		System.out.println("python");
		System.out.println(startSerial);
		serialAviable = true;
	}

}
