package in.nisb.nisbapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;


public class NisbUser {

    private static String database = "nisb";

    public NisbUser(){

        //constructor
    }


    public static void makeTables(Context c){
        try{
            SQLiteDatabase db = c.openOrCreateDatabase(database,c.MODE_PRIVATE,null);
            //guest table
            db.execSQL("create table if not exists guest(Logged int)");
            //user table
            db.execSQL("create table if not exists user(email text,ieeeno int,name text,data text)");
            //notification table
            db.execSQL("create table if not exists notifs(data text)");
        }catch (Exception e){}
    }

    private static JSONObject getWelcomeNotif(){
        try{
            JSONObject jo = new JSONObject();
            jo.put("body","Welcome to NISB App!");
            return jo;
        }catch(JSONException e){}

        return null;
    }

    public static int doGuestLogin(Context c){
        try{
            SQLiteDatabase db = c.openOrCreateDatabase(database,c.MODE_PRIVATE,null);
            makeTables(c);
            db.execSQL("insert into guest values(1)");
            db.close();
            addNotification(c,getWelcomeNotif().toString());
            FirebaseMessaging.getInstance().subscribeToTopic("main");
            return 1;
        }catch (Exception e){
            return 0;
        }
    }

    public static int doUserLogin(Context c,String email,String ieeeno,String wname,String data){
        try{
            SQLiteDatabase db = c.openOrCreateDatabase(database,c.MODE_PRIVATE,null);
            makeTables(c);
            db.execSQL("insert into user values('"+ email +"',"+ieeeno+",'"+wname+"','"+data+"')");
            db.close();

            addNotification(c,getWelcomeNotif().toString());
            FirebaseMessaging.getInstance().subscribeToTopic("main");
            return 1;
        }catch (Exception e){
            return 0;
        }
    }

    public static boolean isGuestLogged(Context c){
        try{
            SQLiteDatabase db = c.openOrCreateDatabase("nisb",c.MODE_PRIVATE,null);
            makeTables(c);
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
            makeTables(c);
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
            makeTables(c);
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
            makeTables(c);
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
            makeTables(c);
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
            makeTables(c);
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
            clearNotifications(c);
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
            clearNotifications(c);
            return 1;
        }catch (Exception e){
            return 0;
        }
    }

    public static void addNotification(Context c, String data){
        try{
            SQLiteDatabase db = c.openOrCreateDatabase(database,c.MODE_PRIVATE,null);
            db.execSQL("insert into notifs values('"+data+"')");
        }catch (Exception e){}
    }

    public static JSONObject[] getNotifications(Context c){
        try{
            SQLiteDatabase db = c.openOrCreateDatabase(database,c.MODE_PRIVATE,null);
            Cursor rs = db.rawQuery("select * from notifs",null);
            int num_rows=rs.getCount();
            db.close();
            if (num_rows>0){
                JSONObject vals[] = new JSONObject[num_rows];
                rs.moveToFirst();
                for (int i=0;i<rs.getCount();i++){
                    rs.moveToPosition(i);
                    JSONObject jobj1 = new JSONObject(rs.getString(0));
                    vals[i] = jobj1;
                }
                return vals;
            }
        }catch(Exception e){}
        return null;
    }
    public static void clearNotifications(Context c){
        try{
            SQLiteDatabase db = c.openOrCreateDatabase(database,c.MODE_PRIVATE,null);
            db.execSQL("delete from notifs");
        }catch (Exception e){}
    }
}
