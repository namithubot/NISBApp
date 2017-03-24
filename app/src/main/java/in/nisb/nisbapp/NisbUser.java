package in.nisb.nisbapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.json.JSONObject;


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

    public static int doUserLogin(Context c,String email,String ieeeno,String wname,String data){
        try{
            SQLiteDatabase db = c.openOrCreateDatabase(database,c.MODE_PRIVATE,null);
            db.execSQL("create table if not exists user(email text,ieeeno int,name text,data text);");
            db.execSQL("insert into user values('"+ email +"',"+ieeeno+",'"+wname+"','"+data+"')");
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
            db.execSQL("create table if not exists user(email text,ieeeno int,name text,data text);");
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
            db.execSQL("create table if not exists user(email text,ieeeno int,name text,data text);");
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
    public static String getUserName(Context c){
        try{
            SQLiteDatabase db = c.openOrCreateDatabase("nisb",c.MODE_PRIVATE,null);
            db.execSQL("create table if not exists user(email text,ieeeno int,name text,data text);");
            Cursor rs = db.rawQuery("select * from user",null);
            int num_rows=rs.getCount();
            db.close();
            if (num_rows>0){
                rs.moveToFirst();
                String e = rs.getString(2);
                return e;
            }
        }catch (Exception e){
            return e.getMessage();
        }
        return "Error 2";
    }
    public static JSONObject getUserData(Context c){
        try{
            SQLiteDatabase db = c.openOrCreateDatabase("nisb",c.MODE_PRIVATE,null);
            db.execSQL("create table if not exists user(email text,ieeeno int,name text,data text);");
            Cursor rs = db.rawQuery("select * from user",null);
            int num_rows=rs.getCount();
            db.close();
            if (num_rows>0){
                rs.moveToFirst();
                String e = rs.getString(3);
                return new JSONObject(e);
            }
        }catch (Exception e){
            return null;
        }
        return null;
    }
    public static int getUserIEEEno(Context c){
        try{
            SQLiteDatabase db = c.openOrCreateDatabase("nisb",c.MODE_PRIVATE,null);
            db.execSQL("create table if not exists user(email text,ieeeno int,name text,data text);");
            Cursor rs = db.rawQuery("select * from user",null);
            int num_rows=rs.getCount();
            db.close();
            if (num_rows>0){
                rs.moveToFirst();
                int e = rs.getInt(1);
                return e;
            }
        }catch (Exception e){
            //return e.getMessage();
            return 0;
        }
        return 0;
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
