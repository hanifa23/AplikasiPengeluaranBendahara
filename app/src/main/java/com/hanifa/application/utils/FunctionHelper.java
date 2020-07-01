package com.hanifa.application.utils;

import java.text.DecimalFormat;

/**
 * Created by Hanifatus sholiha on 22/juni/2020.
 */

public class FunctionHelper {

    public static String rupiahFormat(int price) {
        DecimalFormat formatter = new DecimalFormat("#,###");
        return "Rp " + formatter.format(price).replaceAll(",", ".");
    }
}
