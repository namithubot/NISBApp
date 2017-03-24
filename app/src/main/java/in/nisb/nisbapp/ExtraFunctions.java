package in.nisb.nisbapp;

import android.graphics.Color;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;


/**
 * Created by mridul on 23/3/17.
 */

public class ExtraFunctions {

    public static void setSBColor(Window w,int c){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = w;
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(c);
        }
    }

}
