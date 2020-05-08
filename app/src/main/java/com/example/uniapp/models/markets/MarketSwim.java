package com.example.uniapp.models.markets;

import android.content.Context;

import com.example.uniapp.R;
import com.example.uniapp.views.AboutScreen;

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
        System.out.println(swim);
        if (swim.equals("butterfly")) return "pap";
        else if (swim.equals("backstroke")) return "dos";
        else if (swim.equals("breaststroke")) return "br";
        else if (swim.equals("freestyle")) return "NL";
        else if (swim.equals("IM")) return "4N";
        else return "JSP";
    }

    public static String convertSwimFromFrenchToEnglish(String swim) {
        swim = swim.toLowerCase();
        if (swim.equals("papillon")) return "butterfly";
        else if (swim.equals("dos")) return "backstroke";
        else if (swim.equals("brasse")) return "breaststroke";
        else if (swim.equals("nage libre")) return "freestyle";
        else if (swim.equals("4 nages")) return "IM";
        else return "JSP";
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
        if (swim.equals("butterfly")) return 0;
        else if (swim.equals("backstroke")) return 1;
        else if (swim.equals("breaststroke")) return 2;
        else if (swim.equals("freestyle")) return 3;
        else if (swim.equals("IM")) return 4;
        else return -1;
    }
}
