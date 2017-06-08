package com.example.printdemo;

import android.content.Context;
import android.content.res.Configuration;

public class SystemUtil {
	
	public static int getScreenOrientent(Context mContext){
	    Configuration newConfig = mContext.getResources().getConfiguration();
	    int direct = 0;
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){  
            //横屏 
        	direct = 0;
        }else if(newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){  
            //竖屏 
        	direct = 1;
        }else if(newConfig.hardKeyboardHidden == Configuration.KEYBOARDHIDDEN_NO){  
            //键盘没关闭�?�屏幕方向为横屏 
        	direct = 2;
        }else if(newConfig.hardKeyboardHidden == Configuration.KEYBOARDHIDDEN_YES){  
            //键盘关闭。屏幕方向为竖屏
        	direct = 3;
        } 
        return direct;
	}

}
