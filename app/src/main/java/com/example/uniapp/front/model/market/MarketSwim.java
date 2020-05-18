package com.example.uniapp.front.model.market;

import android.content.Context;

import com.example.uniapp.front.presenter.global.AboutScreen;
import com.example.uniapp.R;

public class MarketSwim {
    public static String convertSwimFromEnglishToFrench(String swim) {
        switch (swim.toLowerCase()) {
            case "butterfly":
                return "Papillon";
            case "backstroke":
                return "Dos";
            case "breaststroke":
                return "Brasse";
            case "freestyle":
                return "Nage Libre";
            case "IM":
                return "4 Nages";
        }
        return "SaisPas";
    }

    public static String convertShortSwim(String swim) {
        switch (swim) {
            case "butterfly":
                return "pap";
            case "backstroke":
                return "dos";
            case "breaststroke":
                return "br";
            case "freestyle":
                return "NL";
            case "IM":
                return "4N";
            default:
                return "JSP";
        }
    }

    public static String convertSwimFromFrenchToEnglish(String swim) {
        swim = swim.toLowerCase();
        switch (swim) {
            case "papillon":
                return "butterfly";
            case "dos":
                return "backstroke";
            case "brasse":
                return "breaststroke";
            case "nage libre":
                return "freestyle";
            case "4 nages":
                return "IM";
            default:
                return "JSP";
        }
    }

    public static int getCurrentColor(Context context, String swim) {
        switch (swim) {
            case "butterfly":
                return AboutScreen.getColorByThemeAttr(context, R.attr.secondaryColor, R.color.colorSecondaryLight);
            case "backstroke":
                return AboutScreen.getColorByThemeAttr(context, R.attr.greenLightColor, R.color.greenLight);
            case "breaststroke":
                return AboutScreen.getColorByThemeAttr(context, R.attr.orangeLightColor, R.color.orangeLight);
            case "freestyle":
                return AboutScreen.getColorByThemeAttr(context, R.attr.redLightColor, R.color.redLight);
            case "IM":
                return AboutScreen.getColorByThemeAttr(context, R.attr.blueLightColor, R.color.blueLight);
            default:
                return -1;
        }
    }

    public static int getCurrentDrawable(String swim) {
        switch (swim) {
            case "butterfly":
            case "IM":
                return R.drawable.sh_gradient_blue;
            case "backstroke":
                return R.drawable.sh_gradient_green;
            case "breaststroke":
                return R.drawable.sh_gradient_orange;
            case "freestyle":
                return R.drawable.sh_gradient_red;
            default:
                return -1;
        }
    }

    public static int getSwimIndex(String swim) {
        switch (swim) {
            case "butterfly":
                return 0;
            case "backstroke":
                return 1;
            case "breaststroke":
                return 2;
            case "freestyle":
                return 3;
            case "IM":
                return 4;
            default:
                return -1;
        }
    }
}
