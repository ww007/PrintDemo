package com.example.printdemo;

import com.example.printdemo.R;
import com.smartdevice.aidl.IZKCService;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

public class BaseActivity extends Activity {

	public static String MODULE_FLAG = "module_flag";
	public static int module_flag = 0;
	public static int DEVICE_MODEL = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
		module_flag = getIntent().getIntExtra(MODULE_FLAG, 8);
		bindService();
	}

	public boolean bindSuccessFlag = false;
	public static IZKCService mIzkcService;
	private ServiceConnection mServiceConn = new ServiceConnection() {
		@Override
		public void onServiceDisconnected(ComponentName name) {
			Log.e("client", "onServiceDisconnected");
			mIzkcService = null;
			bindSuccessFlag = false;
			Toast.makeText(BaseActivity.this, getString(R.string.service_bind_fail), Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			Log.e("client", "onServiceConnected");
			mIzkcService = IZKCService.Stub.asInterface(service);
			if (mIzkcService != null) {
				try {
					Toast.makeText(BaseActivity.this, getString(R.string.service_bind_success), Toast.LENGTH_SHORT)
							.show();
					DEVICE_MODEL = mIzkcService.getDeviceModel();
					mIzkcService.setModuleFlag(module_flag);
					if (module_flag == 3) {
						mIzkcService.openBackLight(1);
					}
				} catch (RemoteException e) {
					e.printStackTrace();
				}
				bindSuccessFlag = true;
			}
		}
	};

	public void bindService() {
		Intent intent = new Intent("com.zkc.aidl.all");
		intent.setPackage("com.smartdevice.aidl");
		bindService(intent, mServiceConn, Context.BIND_AUTO_CREATE);
		Log.i("绑定成功", "-------------");
	}

	public void unbindService() {
		unbindService(mServiceConn);
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onStop() {
		if (module_flag == 3) {
			try {
				mIzkcService.openBackLight(0);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		// if(module_flag!=8){
		// unbindService();
		// }
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		if (module_flag == 8) {
			try {
				mIzkcService.setModuleFlag(9);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		unbindService();
		super.onDestroy();
	}

}
