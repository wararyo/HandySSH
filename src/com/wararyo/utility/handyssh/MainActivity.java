package com.wararyo.utility.handyssh;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends ActionBarActivity {
	
	EditText editName,editCommand,editDelay,editHost,editUser,editPass;
	Button btnMake;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//this.getWindow().setSoftInputMode(LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		setContentView(R.layout.activity_main);
		
		final boolean isShortcut;
		if(getIntent().getComponent().getClassName().equals(getPackageName() + ".MainActivityShortcut")){
			//�V���[�g�J�b�g�I����ʂ���N�����Ă���
			isShortcut = true;
		}
		else{
			//�z�[����ʃA�v���ꗗ����N�����Ă���
			isShortcut = false;
		}
		//Log.v("HandySSH",i.getExtras().toString());
		
		editName = (EditText)findViewById(R.id.editName);
		editCommand = (EditText)findViewById(R.id.editCommand);
		editDelay	 = (EditText)findViewById(R.id.editDelay);
		editHost	 = (EditText)findViewById(R.id.editHost);
		editUser	 = (EditText)findViewById(R.id.editUser);
		editPass	 = (EditText)findViewById(R.id.editPass);
		btnMake = (Button)findViewById(R.id.btnMake);
		if(isShortcut) btnMake.setText(getResources().getString(android.R.string.ok));
		btnMake.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//�V���[�g�J�b�g���^�b�`�Ŕ��s�����Intent���쐬
				Intent shortcutIntent = new Intent(Intent.ACTION_VIEW);
				shortcutIntent.setClassName(getApplicationContext(), SSHActivity.class.getName());
				shortcutIntent.putExtra("HOST",editHost.getText().toString());
				shortcutIntent.putExtra("USER",editUser.getText().toString());
				shortcutIntent.putExtra("PASS",editPass.getText().toString());
				shortcutIntent.putExtra("COMMAND",editCommand.getText().toString());
				shortcutIntent.putExtra("DELAY", editDelay.getText().toString());
				//shortcutIntent���V���[�g�J�b�g�ɓo�^����Intent
				Intent intent = new Intent();
				
				intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, editName.getText().toString());
				intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
				
				Parcelable iconResource = Intent.ShortcutIconResource.fromContext(getApplicationContext(), R.drawable.ic_launcher);
				intent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, iconResource);
				
				intent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
				
				if(isShortcut) setResult(RESULT_OK, intent);
				else sendBroadcast(intent);
				finish();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.main, menu);
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
