package com.wararyo.utility.handyssh;

import java.io.ByteArrayOutputStream;
import java.util.Properties;

import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;
import com.jcraft.jsch.*;

public class SSHActivity extends Activity {

	Handler handler = new Handler();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_ssh);
		Intent i = getIntent();
		final String host = i.getStringExtra("HOST");
		final String user = i.getStringExtra("USER");
		final String pass = i.getStringExtra("PASS");
		final String command = i.getStringExtra("COMMAND");
		final int delay = Integer.parseInt(i.getStringExtra("DELAY"));
		//String delay = i.getStringExtra("DELAY");
		//Log.v("HandySSH", host + user + pass + command + delay);
		
		
		Thread sessionthread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try{
						//Log.v("HandySSH", "0");
					JSch jsch = new JSch();
						//Log.v("HandySSH", "1");
					Session session = jsch.getSession(user, host, 22);
					session.setPassword(pass);
						//Log.v("HandySSH", "2");
					Properties config = new Properties();
					config.put("StrictHostKeyChecking", "no");
					session.setConfig(config);
					session.setTimeout(10000);
						//Log.v("HandySSH", "3");
					showToast("Connecting...");
					session.connect();
						//Log.v("HandySSH", "4");
					ChannelExec channel=(ChannelExec)session.openChannel("exec");
					channel.setCommand(command);
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					channel.setOutputStream(baos);
					Thread.sleep(delay * 1000);
					channel.connect();
						//Log.v("HandySSH", "5");
					channel.disconnect();
					session.disconnect();
						//Log.v("HandySSH", baos.toString() + "a");
					showToast("SSH command has been sent.");
				}
				catch (JSchException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					showToast("SSH connection failed");
						//Log.v("HandySSH","Failed...");
					//finish();
				}
				//Log.v("HandySSH","OKOK");
				catch (InterruptedException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
			}
			
			void showToast(final String message){
				handler.post(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
					}
				});
			}
		});
		sessionthread.start();

		
		//Toast.makeText(getApplicationContext(), "SSH command has been sent.", Toast.LENGTH_SHORT).show();
		finish();
	}
	

	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ssh, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}*/
}
