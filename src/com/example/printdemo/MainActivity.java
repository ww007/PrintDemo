package com.example.printdemo;

import com.example.printdemo.R;
import com.example.printdemo.printer.PrinterHelper;
import com.example.printdemo.printer.entity.GradesBill;

import android.content.Intent;
import android.os.Bundle;
import android.os.RemoteException;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends BaseActivity {

	private boolean runFlag = true;
	private Button btnPrint;
	private TextView tv;
	private Button btnScan;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		btnPrint = (Button) findViewById(R.id.btn_print);
		btnScan = (Button) findViewById(R.id.btn_scan);
		tv = (TextView) findViewById(R.id.tv);

		Intent intent = getIntent();
		String result = intent.getStringExtra("grade");
		tv.setText(result);

		btnPrint.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				printPurcase(true, false);
				// printGBKText();
			}
		});

		btnScan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
				startActivity(intent);
			}
		});
	}

	private void printPurcase(boolean hasStartPic, boolean hasEndPic) {
		GradesBill bill = PrinterHelper.getInstance(this).getGradesBill(mIzkcService, hasStartPic, hasEndPic);
		PrinterHelper.getInstance(this).printGradesBillModelOne(mIzkcService, bill);
	}

	private boolean autoOutputPaper = true;

}
