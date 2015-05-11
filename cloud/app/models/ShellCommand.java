package models;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ShellCommand {

	private String command;

	public ShellCommand(String command) {
		this.command = command;
	}

	/**
	 * Executes the given command
	 * @return returns the shell output
	 */
	public StringBuffer executeShellCommand() {
		StringBuffer output = new StringBuffer();
		Process p;
		try {
			p = Runtime.getRuntime().exec(this.command);
			p.waitFor();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					p.getInputStream()));
			String line = "";
			while ((line = reader.readLine()) != null) {
				output.append(line + System.lineSeparator());
			}
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		return output;
	}
}