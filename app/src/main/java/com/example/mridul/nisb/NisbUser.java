package com.example.mridul.nisb;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;



public class NisbUser {

    private static String database = "nisb";
    private static String email="";
    private static String password="";

    public NisbUser(){

        //constructor
    }

    public static void setEmail(String e){
        email=e;
    }
    public static void setPassword(String p){
        password=p;
    }
    public static String getEmail(){
        return email;
    }

    public static int doGuestLogin(Context c){
        try{
            SQLiteDatabase db = c.openOrCreateDatabase(database,c.MODE_PRIVATE,null);
            db.execSQL("create table if not exists guest(Logged int);");
            db.execSQL("insert into guest values(1)");
            db.close();
            return 1;
        }catch (Exception e){
            return 0;
        }
    }

    public static int doUserLogin(Context c,String email){
        try{
            SQLiteDatabase db = c.openOrCreateDatabase(database,c.MODE_PRIVATE,null);
            db.execSQL("create table if not exists user(email text);");
            db.execSQL("insert into user values('"+ email +"')");
            db.close();
            setEmail(email);
            return 1;
        }catch (Exception e){
            return 0;
        }
    }

    public static boolean isGuestLogged(Context c){
        try{
            SQLiteDatabase db = c.openOrCreateDatabase("nisb",c.MODE_PRIVATE,null);
            db.execSQL("create table if not exists guest(Logged int);");
            Cursor rs = db.rawQuery("select * from guest",null);
            int num_rows=rs.getCount();
            db.close();
            if (num_rows>0)
                return true;
        }catch (Exception e){
            return false;
        }
        return false;
    }

    public static boolean isUserLogged(Context c){
        try{
            SQLiteDatabase db = c.openOrCreateDatabase("nisb",c.MODE_PRIVATE,null);
            db.execSQL("create table if not exists user(email text);");
            Cursor rs = db.rawQuery("select * from user",null);
            int num_rows=rs.getCount();
            db.close();
            if (num_rows>0)
                return true;
        }catch (Exception e){
            return false;
        }
        return false;
    }

    public static String getUserEmail(Context c){
        try{
            SQLiteDatabase db = c.openOrCreateDatabase("nisb",c.MODE_PRIVATE,null);
            db.execSQL("create table if not exists user(email text);");
            Cursor rs = db.rawQuery("select * from user",null);
            int num_rows=rs.getCount();
            db.close();
            if (num_rows>0){
                rs.moveToFirst();
                String e = rs.getString(0);
                return e;
            }
        }catch (Exception e){
            return e.getMessage();
        }
        return "Error 2";
    }

    public static int doGuestLogout(Context c){
        try{
            SQLiteDatabase db = c.openOrCreateDatabase("nisb",c.MODE_PRIVATE,null);
            db.execSQL("Delete from guest");
            db.close();
            return 1;
        }catch (Exception e){
            return 0;
        }
    }
    public static int doUserLogout(Context c){
        try{
            SQLiteDatabase db = c.openOrCreateDatabase("nisb",c.MODE_PRIVATE,null);
            db.execSQL("Delete from user");
            db.close();
            return 1;
        }catch (Exception e){
            return 0;
        }
    }
}
