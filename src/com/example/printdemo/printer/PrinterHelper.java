package com.example.printdemo.printer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.RemoteException;
import android.os.SystemClock;

import com.example.printdemo.R;
import com.example.printdemo.printer.entity.GoodsInfo;
import com.example.printdemo.printer.entity.GradesBill;
import com.example.printdemo.printer.entity.GradesInfo;
import com.example.printdemo.printer.entity.PersonBill;
import com.example.printdemo.printer.entity.PersonInfo;
import com.example.printdemo.printer.entity.SupermakerBill;
import com.smartdevice.aidl.IZKCService;

public class PrinterHelper {

	/* 等待打印缓冲刷新的时间 */
	private static final int mIzkcService_BUFFER_FLUSH_WAITTIME = 150;
	/* 分割线 */
	private static final String mIzkcService_CUT_OFF_RULE = "--------------------------------\n";

	// 品名占位长度
	private static final int GOODS_NAME_LENGTH = 6;
	// 单价占位长度
	private static final int GOODS_UNIT_PRICE_LENGTH = 6;
	// 价格占位长度
	private static final int GOODS_PRICE_LENGTH = 6;
	// 数量占位长度
	private static final int GOODS_AMOUNT = 6;

	private Context mContext;
	private String text;

	private static PrinterHelper _instance;

	private PrinterHelper(Context mContext) {
		this.mContext = mContext;
	}

	synchronized public static PrinterHelper getInstance(Context mContext) {
		if (null == _instance)
			_instance = new PrinterHelper(mContext);
		return _instance;
	}

	synchronized public void printGradesBillModelOne(IZKCService mIzkcService, GradesBill bill) {

		try {
			if (mIzkcService.checkPrinterAvailable()) {

				mIzkcService.printTextAlgin(bill.itemName, 0, 0, 1);
				// mIzkcService.printTextAlgin(bill.item_xuhao, 0, 0, 0);
				// mIzkcService.printTextAlgin(bill.item_stuName, 0, 0, 1);
				// mIzkcService.printTextAlgin(bill.item_grade, 0, 0, 2);
				mIzkcService.printGBKText("\n");

				String space01 = "";
				String space02 = "";

				String xuhao1 = bill.item_xuhao;
				String name1 = bill.item_stuName;
				String grade1 = bill.item_grade;

				int space_length01 = GOODS_NAME_LENGTH - xuhao1.length();
				int space_length02 = GOODS_UNIT_PRICE_LENGTH - name1.length();

				for (int j = 0; j < space_length01; j++) {
					space01 += " ";
				}
				for (int j = 0; j < space_length02; j++) {
					space02 += "  ";
				}
				mIzkcService.printGBKText(xuhao1 + space01 + " " + name1 + space02 + "  " + grade1 + "\n");

				for (int i = 0; i < bill.gradesInfos.size(); i++) {
					// mIzkcService.printTextAlgin(bill.gradesInfos.get(i).xuhao,
					// 0, 0, 0);
					// mIzkcService.printTextAlgin(bill.gradesInfos.get(i).name,
					// 0, 0, 1);
					// mIzkcService.printTextAlgin(bill.gradesInfos.get(i).grade,
					// 0, 0, 2);
					// mIzkcService.printGBKText("\n");

					String space0 = "";
					String space1 = "";

					String xuhao = bill.gradesInfos.get(i).xuhao;
					String name = bill.gradesInfos.get(i).name;
					String grade = bill.gradesInfos.get(i).grade;

					int space_length0 = GOODS_NAME_LENGTH - xuhao.length();
					int space_length1 = GOODS_UNIT_PRICE_LENGTH - name.length();

					for (int j = 0; j < space_length0; j++) {
						space0 += " ";
					}
					for (int j = 0; j < space_length1; j++) {
						space1 += "  ";
					}

					mIzkcService.printGBKText(xuhao + space0 + " " + name + space1 + "  " + grade + "\n");
					// mIzkcService.printGBKText("\n" +
					// bill.gradesInfos.get(i).xuhao + " "
					// + bill.gradesInfos.get(i).name + " " +
					// bill.gradesInfos.get(i).grade);
				}
				mIzkcService.printGBKText("\n\n");
				for (int i = 0; i < bill.personInfos.size(); i++) {
					mIzkcService.printTextAlgin(bill.personInfos.get(i).item, 0, 0, 1);
					mIzkcService.printTextAlgin("/n序号:" + bill.personInfos.get(i).xuhao, 0, 0, 0);
					mIzkcService.printTextAlgin("/n考好:" + bill.personInfos.get(i).stuCode, 0, 0, 0);
					mIzkcService.printTextAlgin("/n姓名:" + bill.personInfos.get(i).name, 0, 0, 0);
					mIzkcService.printTextAlgin("/n性别:" + bill.personInfos.get(i).sex, 0, 0, 0);
					mIzkcService.printTextAlgin("/n单位:" + bill.personInfos.get(i).unit, 0, 0, 0);
					mIzkcService.printTextAlgin("/n成绩:" + bill.personInfos.get(i).grade, 0, 0, 0);
					mIzkcService.printTextAlgin("/n" + bill.personInfos.get(i).date + "/n/n", 0, 0, 0);

				}

			}
		} catch (Exception e) {
		}
	}

	synchronized public void printPurchaseBillModelOne(IZKCService mIzkcService, SupermakerBill bill) {

		try {
			if (mIzkcService.checkPrinterAvailable()) {

				mIzkcService.printGBKText("\n\n");
				if (bill.start_bitmap != null) {
					mIzkcService.printBitmapAlgin(bill.start_bitmap, 376, 120, 1);
				}
				mIzkcService.printGBKText(bill.supermaker_name + "\n\n");
				mIzkcService.printGBKText(PrintTag.PurchaseBillTag.SERIAL_NUMBER_TAG + bill.serial_number + "\t\t\n"
						+ bill.purchase_time + "\n");

				mIzkcService.printGBKText(mIzkcService_CUT_OFF_RULE);

				mIzkcService.printGBKText(PrintTag.PurchaseBillTag.GOODS_NAME_TAG + "          "
						+ PrintTag.PurchaseBillTag.GOODS_UNIT_PRICE_TAG + "  "
						+ PrintTag.PurchaseBillTag.GOODS_AMOUNT_TAG + "  " + PrintTag.PurchaseBillTag.GOODS_PRICE_TAG
						+ "  " + "\n");

				mIzkcService.printGBKText(mIzkcService_CUT_OFF_RULE);

				for (int i = 0; i < bill.goosInfos.size(); i++) {

					String space0 = "";
					String space1 = "";
					String space2 = "";
					String space3 = "";

					String name = bill.goosInfos.get(i).goods_name;
					String unit_price = bill.goosInfos.get(i).goods_unit_price;
					String amount = bill.goosInfos.get(i).goods_amount;
					String price = bill.goosInfos.get(i).goods_price;

					int name_length = name.length();
					int unit_price_length = unit_price.length();
					int amount_length = amount.length();
					int price_length = price.length();

					int space_length0 = GOODS_NAME_LENGTH - name_length;
					int space_length1 = GOODS_UNIT_PRICE_LENGTH - unit_price_length;
					int space_length2 = GOODS_AMOUNT - amount_length;
					int space_length3 = GOODS_PRICE_LENGTH - price_length;

					String name1 = "";
					String name2 = "";

					if (name_length > GOODS_NAME_LENGTH) {
						name1 = name.substring(0, 6);
						name2 = name.substring(6, name_length);

						for (int j = 0; j < space_length1; j++) {
							space1 += " ";
						}
						for (int j = 0; j < space_length2 - 1; j++) {
							space2 += " ";
						}

						mIzkcService.printGBKText(
								name1 + "  " + unit_price + space1 + " " + amount + space2 + price + "\n");

						mIzkcService.printGBKText(name2 + "\n");

					} else {
						for (int j = 0; j < space_length0; j++) {
							space0 += "  ";
						}
						for (int j = 0; j < space_length1; j++) {
							space1 += " ";
						}
						for (int j = 0; j < space_length2 - 1; j++) {
							space2 += " ";
						}

						mIzkcService.printGBKText(
								name + space0 + "  " + unit_price + space1 + " " + amount + space2 + price + "\n");
					}
				}

				mIzkcService.printGBKText(mIzkcService_CUT_OFF_RULE);
				mIzkcService.printGBKText(PrintTag.PurchaseBillTag.FAVOURABLE_CASH_TAG + bill.favorable_cash + "\t\t"
						+ PrintTag.PurchaseBillTag.RECEIPT_CASH_TAG + bill.receipt_cash + "\n");
				mIzkcService.printGBKText(PrintTag.PurchaseBillTag.RECEIVED_CASH_TAG + bill.recived_cash + "\t\t"
						+ PrintTag.PurchaseBillTag.ODD_CHANGE_TAG + bill.odd_change + "\n");
				mIzkcService.printGBKText(mIzkcService_CUT_OFF_RULE);
				mIzkcService.printGBKText(PrintTag.PurchaseBillTag.VIP_NUMBER_TAG + bill.vip_number + "\n");
				mIzkcService.printGBKText(PrintTag.PurchaseBillTag.ADD_INTEGRAL_TAG + bill.add_integral + "\n");
				mIzkcService.printGBKText(PrintTag.PurchaseBillTag.CURRENT_INTEGRAL_TAG + bill.current_integral + "\n");
				mIzkcService.printGBKText(mIzkcService_CUT_OFF_RULE);

				/*
				 * =============================================================
				 * = =========
				 * 
				 * 
				 * =============================================================
				 * = ========
				 */
				mIzkcService.printGBKText(PrintTag.PurchaseBillTag.SUPERMAKER_ADDRESS + bill.supermaker_address + "\n");
				mIzkcService.printGBKText(PrintTag.PurchaseBillTag.SUPERMAKER_CALL + bill.supermaker_call + "\n");
				mIzkcService.printGBKText(PrintTag.PurchaseBillTag.WELCOM_TO_HERE + "\n\n");

				// if(mIzkcService.getBufferState(100)){
				if (bill.end_bitmap != null) {
					SystemClock.sleep(3000);
					mIzkcService.printBitmap(bill.end_bitmap);
				}
				// }

			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public GradesBill getGradesBill(IZKCService mIzkcService, boolean display_start_pic, boolean display_end_pic) {
		GradesBill bill = new GradesBill();
		bill.itemName = "肺活量";
		bill.item_xuhao = "序号";
		bill.item_stuName = "姓名";
		bill.item_grade = "成绩";

		addGradesInfo(bill.gradesInfos, bill.personInfos);
		return bill;
	}

	private void addGradesInfo(ArrayList<GradesInfo> gradesInfos, ArrayList<PersonInfo> personInfos) {
		String[] name = { "文昊", "徐岩", "刘博文", "郭智奇", "孙浩天天", "龚浩" };
		for (int i = 0; i < name.length; i++) {
			GradesInfo gradesInfo = new GradesInfo();
			PersonInfo personInfo = new PersonInfo();
			gradesInfo.grade = (i + 1) * 1000 + "";
			gradesInfo.name = name[i];
			gradesInfo.xuhao = i + 1 + "";
			gradesInfos.add(gradesInfo);

			personInfo.item = "肺活量";
			personInfo.xuhao = "" + i + 1;
			personInfo.stuCode = i + 20170525 + "";
			personInfo.name = name[i];
			personInfo.sex = "男";
			personInfo.unit = "宁国中学初中部";
			personInfo.grade = (i + 1) * 1000 + "";
			personInfo.date = "2017-05-25 9:20:00";
			personInfos.add(personInfo);
		}

	}

	public SupermakerBill getSupermakerBill(IZKCService mIzkcService, boolean display_start_pic,
			boolean display_end_pic) {
		SupermakerBill bill = new SupermakerBill();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date = new Date();
		String dateStr = sdf.format(date);
		bill.supermaker_name = "XXXX考试成绩单";
		bill.serial_number = System.currentTimeMillis() + "";
		bill.purchase_time = dateStr;
		bill.total_amount = "36";
		bill.total_cash = "1681.86";
		bill.favorable_cash = "81.86";
		bill.receipt_cash = "1600";
		bill.recived_cash = "1600";
		bill.odd_change = "0.0";
		bill.vip_number = "111111111111";
		bill.add_integral = "1600";
		bill.current_integral = "36000";
		bill.supermaker_address = "深圳市宝安区鹤州xxxxxxxx";
		bill.supermaker_call = "0755-99991668";

		generalBitmap(mIzkcService, bill, display_start_pic, display_end_pic);
		addGoodsInfo(bill.goosInfos);
		return bill;

	}

	private void generalBitmap(IZKCService mIzkcService, SupermakerBill bill, boolean display_start_pic,
			boolean display_end_pic) {

		if (display_start_pic) {
			Bitmap mBitmap = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ic_launcher);
			bill.start_bitmap = mBitmap;
		}
		if (display_end_pic) {
			Bitmap btMap;
			try {
				btMap = mIzkcService.createQRCode("扫描关注本店，有惊喜喔", 240, 240);
				bill.end_bitmap = btMap;
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void addGoodsInfo(ArrayList<GoodsInfo> goosInfos) {

		GoodsInfo goodsInfo0 = new GoodsInfo();
		goodsInfo0.goods_name = "黑人牙膏";
		goodsInfo0.goods_unit_price = "14.5";
		goodsInfo0.goods_amount = "2";
		goodsInfo0.goods_price = "29";

		GoodsInfo goodsInfo1 = new GoodsInfo();
		goodsInfo1.goods_name = "啤酒";
		goodsInfo1.goods_unit_price = "2.5";
		goodsInfo1.goods_amount = "12";
		goodsInfo1.goods_price = "30";

		GoodsInfo goodsInfo2 = new GoodsInfo();
		goodsInfo2.goods_name = "美的电饭煲";
		goodsInfo2.goods_unit_price = "288";
		goodsInfo2.goods_amount = "1";
		goodsInfo2.goods_price = "288";

		GoodsInfo goodsInfo3 = new GoodsInfo();
		goodsInfo3.goods_name = "剃须刀";
		goodsInfo3.goods_unit_price = "78";
		goodsInfo3.goods_amount = "1";
		goodsInfo3.goods_price = "78";

		GoodsInfo goodsInfo4 = new GoodsInfo();
		goodsInfo4.goods_name = "泰国进口红提";
		goodsInfo4.goods_unit_price = "22";
		goodsInfo4.goods_amount = "2";
		goodsInfo4.goods_price = "44";

		GoodsInfo goodsInfo5 = new GoodsInfo();
		goodsInfo5.goods_name = "太空椒";
		goodsInfo5.goods_unit_price = "4.5";
		goodsInfo5.goods_amount = "2";
		goodsInfo5.goods_price = "9";

		GoodsInfo goodsInfo6 = new GoodsInfo();
		goodsInfo6.goods_name = "进口香蕉";
		goodsInfo6.goods_unit_price = "3.98";
		goodsInfo6.goods_amount = "3";
		goodsInfo6.goods_price = "11.86";

		GoodsInfo goodsInfo7 = new GoodsInfo();
		goodsInfo7.goods_name = "烟熏腊肉";
		goodsInfo7.goods_unit_price = "33";
		goodsInfo7.goods_amount = "2";
		goodsInfo7.goods_price = "66";

		GoodsInfo goodsInfo8 = new GoodsInfo();
		goodsInfo8.goods_name = "长城红葡萄干酒";
		goodsInfo8.goods_unit_price = "39";
		goodsInfo8.goods_amount = "2";
		goodsInfo8.goods_price = "78";

		GoodsInfo goodsInfo9 = new GoodsInfo();
		goodsInfo9.goods_name = "白人牙刷";
		goodsInfo9.goods_unit_price = "14";
		goodsInfo9.goods_amount = "2";
		goodsInfo9.goods_price = "28";

		GoodsInfo goodsInfo10 = new GoodsInfo();
		goodsInfo10.goods_name = "苹果醋";
		goodsInfo10.goods_unit_price = "4";
		goodsInfo10.goods_amount = "5";
		goodsInfo10.goods_price = "20";

		GoodsInfo goodsInfo11 = new GoodsInfo();
		goodsInfo11.goods_name = "这个商品名有点长有点长有点长不是一般的长";
		goodsInfo11.goods_unit_price = "500";
		goodsInfo11.goods_amount = "2";
		goodsInfo11.goods_price = "1000";

		goosInfos.add(goodsInfo0);
		goosInfos.add(goodsInfo1);
		goosInfos.add(goodsInfo2);
		goosInfos.add(goodsInfo3);
		goosInfos.add(goodsInfo4);
		goosInfos.add(goodsInfo5);
		goosInfos.add(goodsInfo6);
		goosInfos.add(goodsInfo7);
		goosInfos.add(goodsInfo8);
		goosInfos.add(goodsInfo9);
		goosInfos.add(goodsInfo10);
		goosInfos.add(goodsInfo11);

	}

}
