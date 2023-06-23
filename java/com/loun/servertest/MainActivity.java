package com.loun.servertest;

import android.animation.*;
import android.app.*;
import android.app.Activity;
import android.content.*;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.net.Uri;
import android.os.*;
import android.os.Vibrator;
import android.text.*;
import android.text.style.*;
import android.util.*;
import android.view.*;
import android.view.View;
import android.view.View.*;
import android.view.animation.*;
import android.webkit.*;
import android.widget.*;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.regex.*;
import org.json.*;

public class MainActivity extends AppCompatActivity {
	
	private double networktest = 0;
	private String currenttest = "";
	private double servertest = 0;
	
	private LinearLayout linear2;
	private TextView txtnetworktest;
	private ProgressBar progressbar1;
	private TextView txtoutnetworktest;
	private LinearLayout separator;
	private TextView txtservertest;
	private ProgressBar progressbar2;
	private TextView txtoutservertest;
	private Button changeactivity;
	
	private SharedPreferences serverurl;
	private Intent intent = new Intent();
	private RequestNetwork network;
	private RequestNetwork.RequestListener _network_request_listener;
	private Vibrator vibrate;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.main);
		initialize(_savedInstanceState);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		linear2 = findViewById(R.id.linear2);
		txtnetworktest = findViewById(R.id.txtnetworktest);
		progressbar1 = findViewById(R.id.progressbar1);
		txtoutnetworktest = findViewById(R.id.txtoutnetworktest);
		separator = findViewById(R.id.separator);
		txtservertest = findViewById(R.id.txtservertest);
		progressbar2 = findViewById(R.id.progressbar2);
		txtoutservertest = findViewById(R.id.txtoutservertest);
		changeactivity = findViewById(R.id.changeactivity);
		serverurl = getSharedPreferences("serverurl", Activity.MODE_PRIVATE);
		network = new RequestNetwork(this);
		vibrate = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		
		changeactivity.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				intent.setClass(getApplicationContext(), ChangeserverurlActivity.class);
				startActivity(intent);
				finish();
			}
		});
		
		_network_request_listener = new RequestNetwork.RequestListener() {
			@Override
			public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
				final String _tag = _param1;
				final String _response = _param2;
				final HashMap<String, Object> _responseHeaders = _param3;
				if (currenttest.equals("network")) {
					networktest = 1;
					txtoutnetworktest.setText("Yes");
					separator.setVisibility(View.VISIBLE);
					txtservertest.setVisibility(View.VISIBLE);
					txtoutservertest.setVisibility(View.VISIBLE);
					changeactivity.setVisibility(View.VISIBLE);
					vibrate.vibrate((long)(10));
					linear2.setBackgroundColor(0xFFFFEB3B);
					progressbar2.setVisibility(View.VISIBLE);
					txtoutnetworktest.setVisibility(View.VISIBLE);
					progressbar1.setVisibility(View.GONE);
					currenttest = "server";
					network.startRequestNetwork(RequestNetworkController.GET, serverurl.getString("serverurl", ""), "", _network_request_listener);
				}
				if (currenttest.equals("server")) {
					servertest = 1;
					txtoutservertest.setText("Yes");
					vibrate.vibrate((long)(20));
					linear2.setBackgroundColor(0xFF2E7D32);
					progressbar2.setVisibility(View.GONE);
					txtoutservertest.setVisibility(View.VISIBLE);
				}
			}
			
			@Override
			public void onErrorResponse(String _param1, String _param2) {
				final String _tag = _param1;
				final String _message = _param2;
				if (currenttest.equals("network")) {
					networktest = 0;
					txtoutnetworktest.setText("Error");
					linear2.setBackgroundColor(0xFFF44336);
					network.startRequestNetwork(RequestNetworkController.GET, "https://google.com", "", _network_request_listener);
				}
				if (currenttest.equals("server")) {
					servertest = 0;
					txtoutservertest.setText("Error");
					linear2.setBackgroundColor(0xFF9C27B0);
					network.startRequestNetwork(RequestNetworkController.GET, serverurl.getString("serverurl", ""), "", _network_request_listener);
				}
			}
		};
	}
	
	private void initializeLogic() {
		if (serverurl.getString("serverurl", "").equals("")) {
			intent.setClass(getApplicationContext(), ChangeserverurlActivity.class);
			startActivity(intent);
			finish();
		}
		else {
			separator.setVisibility(View.GONE);
			txtservertest.setVisibility(View.GONE);
			txtoutservertest.setVisibility(View.GONE);
			changeactivity.setVisibility(View.GONE);
			txtoutnetworktest.setVisibility(View.GONE);
			txtoutservertest.setVisibility(View.GONE);
			progressbar2.setVisibility(View.GONE);
			currenttest = "network";
			network.startRequestNetwork(RequestNetworkController.GET, "https://google.com", "", _network_request_listener);
		}
	}
	
	
	@Deprecated
	public void showMessage(String _s) {
		Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
	}
	
	@Deprecated
	public int getLocationX(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[0];
	}
	
	@Deprecated
	public int getLocationY(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[1];
	}
	
	@Deprecated
	public int getRandom(int _min, int _max) {
		Random random = new Random();
		return random.nextInt(_max - _min + 1) + _min;
	}
	
	@Deprecated
	public ArrayList<Double> getCheckedItemPositionsToArray(ListView _list) {
		ArrayList<Double> _result = new ArrayList<Double>();
		SparseBooleanArray _arr = _list.getCheckedItemPositions();
		for (int _iIdx = 0; _iIdx < _arr.size(); _iIdx++) {
			if (_arr.valueAt(_iIdx))
			_result.add((double)_arr.keyAt(_iIdx));
		}
		return _result;
	}
	
	@Deprecated
	public float getDip(int _input) {
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, getResources().getDisplayMetrics());
	}
	
	@Deprecated
	public int getDisplayWidthPixels() {
		return getResources().getDisplayMetrics().widthPixels;
	}
	
	@Deprecated
	public int getDisplayHeightPixels() {
		return getResources().getDisplayMetrics().heightPixels;
	}
}