package com.spaceshooter.tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import android.os.AsyncTask;
import android.widget.TextView;

public class TCPClient extends AsyncTask<String, Void, String> {
	 
	 @Override
     protected String doInBackground(String... params) {
		String serverHostname = new String ("213.100.75.188");
		
		System.out.println ("Attemping to connect to host " +
		serverHostname + " on port 12121.");
		
		Socket echoSocket = null;
		PrintWriter out = null;
		BufferedReader in = null;
		
		try {
		    echoSocket = new Socket(serverHostname, 12121);
		    out = new PrintWriter(echoSocket.getOutputStream(), true);
		    in = new BufferedReader(new InputStreamReader(
		                                echoSocket.getInputStream()));
		} catch (UnknownHostException e) {
		    System.err.println("Don't know about host: " + serverHostname);
		    System.exit(1);
		} catch (IOException e) {
		    System.err.println("Couldn't get I/O for "
		               + "the connection to: " + serverHostname);
		        System.exit(1);
		    }
		
		BufferedReader stdIn = new BufferedReader(
		                               new InputStreamReader(System.in));
		
		out.println("insert:" + params[0] + ":" + params[1]);
		
		try {
			System.out.println("echo: " + in.readLine());
		
			out.close();
			in.close();
			echoSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		 return "Executed";
     }

     @Override
     protected void onPostExecute(String result) {
//         TextView txt = (TextView) findViewById(R.id.output);
//         txt.setText("Executed"); // txt.setText(result);
         // might want to change "executed" for the returned string passed
         // into onPostExecute() but that is upto you
     }

     @Override
     protected void onPreExecute() {}

     @Override
     protected void onProgressUpdate(Void... values) {}
}