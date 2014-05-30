package se.chalmers.spaceshooter.leaderboard;

public class HighScoreParser {

	public String[] parseQuery(String query) {
		String[] response;
		String[] parts;

		parts = query.split(":");

		if (parts[0].equals("table")) {
			response = new String[parts.length - 1];
			for (int i = 1; i < parts.length; i++) {
				String[] pair = parts[i].split("%");

				response[i - 1] = pair[0] + ": " + pair[1];
			}
		} else
			return null;

		return response;
	}

}
