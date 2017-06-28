package com.example.david.sushi.Common;

import com.example.david.sushi.Database.Data.Menus;
import com.example.david.sushi.MainActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by David on 21/03/2017.
 */

public class Constant {


    // ============== App Name & Version =========================
    public static String APP_NAME = "TomoSushi";
    public static String APP_VERSION = "0.1.1";

    // ============== APIs =========================
    public static final String API_URL = "https://projek-it.com/pos/";

    // ============== Response Code =========================
    public static final String API_SUCCESS = "200";
    public static final String UNAUTHORIZED = "401";

    // ============== Database =====================
    public static String DATABASE_NAME = "Tomo_sushi_database";
    public static int DATABASE_VERSION = 1 ;
    public static String DAO_PACKAGE_NAME = "com.example.david.sushi.database.Data";

    //Font
    public static String DEFAULT_FONT = "fonts/varela_round_regular.ttf";

    //Cart
    public static List<Menus> cart = new ArrayList<>();
    public static List<Menus> bill = new ArrayList<>();

    //Time Idle
    public static long START_TIME = 100000*1000;
    public static final long INTERVAL = 1000;
    public static boolean SHOW_SCREENSAVER = true;

    //Main Activity
    public static MainActivity mainActivity;

    //Id Table
    public static String ID_MEJA = "1";

    //Status Meja
    public static String AVAILABLE = "1";
    public static String SEATED = "2";
    public static String ORDERED = "3";
    public static String PAYMENT = "4";
}
