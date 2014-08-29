package com.wararyo.utility.handyssh;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends ActionBarActivity {
	
	EditText editName,editCommand,editDelay;
	Button btnMake;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		editName = (EditText)findViewById(R.id.editName);
		editCommand = (EditText)findViewById(R.id.editCommand);
		editDelay	 = (EditText)findViewById(R.id.editDelay);
		btnMake = (Button)findViewById(R.id.btnMake);
		btnMake.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//ショートカットをタッチで発行されるIntentを作成
				Intent shortcutIntent = new Intent(Intent.ACTION_VIEW);
				shortcutIntent.setClassName(getApplicationContext(), SSHActivity.class.getName());
				shortcutIntent.putExtra("COMMAND",editCommand.getText());
				shortcutIntent.putExtra("DELAY", editDelay.getText());
				//shortcutIntentをショートカットに登録するIntent
				Intent intent = new Intent();
				
				intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
				
				Parcelable iconResource = Intent.ShortcutIconResource.fromContext(getApplicationContext(), R.drawable.ic_launcher);
				intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, iconResource);
				
				intent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
				
				sendBroadcast(intent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		/*int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}*/
		return super.onOptionsItemSelected(item);
	}
}
