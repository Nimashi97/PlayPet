package com.nimashi.pawpaw;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManagement {


    //variables

    SharedPreferences userSession;
    SharedPreferences.Editor editor;
    Context context;

    private static final String IS_LOGIN="IsLoggedIn";

    public static final String Key_ID="id";
    public static final String Key_NAME="name";
    public static final String Key_EMAIL="email";
    public static final String Key_PASSWORD="password";
    public static final String Key_MOBILE="mobile";

    public SessionManagement(Context _context)
    {
        context=_context;
        userSession =context.getSharedPreferences("userLoginSession",Context.MODE_PRIVATE);
        editor=userSession.edit();

    }

    public void cretateLoginSession(String id, String name, String email,String password,String mobile)
    {
        editor.putBoolean(IS_LOGIN,true);

        editor.putString(Key_ID,id);
        editor.putString(Key_NAME,name);
        editor.putString(Key_EMAIL,email);
        editor.putString(Key_PASSWORD,password);
        editor.putString(Key_MOBILE,mobile);

        editor.commit();

    }

    public HashMap<String ,String> getUserDetailsFromSession()
    {
        HashMap<String ,String > userData=new HashMap<String,String>();
        userData.put(Key_ID,userSession.getString(Key_ID,null));
        userData.put(Key_NAME,userSession.getString(Key_NAME,null));
        userData.put(Key_EMAIL,userSession.getString(Key_EMAIL,null));
        userData.put(Key_PASSWORD,userSession.getString(Key_PASSWORD,null));
        userData.put(Key_MOBILE,userSession.getString(Key_MOBILE,null));
        return userData;

    }

    public boolean checkLogin()
    {
        if(userSession.getBoolean(IS_LOGIN,false))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public void logoutUserFromSession()
    {
        editor.clear();
        editor.commit();
    }


//    public static void SessionManagement(Context context,String name, String value)
//    {
//
//        SharedPreferences sharedPreferences=context.getSharedPreferences("LOGIN",context.MODE_PRIVATE);
//        SharedPreferences.Editor editor=sharedPreferences.edit();
//        editor.putString(name, value);
//        editor.apply();
//    }
//
//    public static String read(Context context, String name,String dvalue)
//    {
//        SharedPreferences sharedPreferences=context.getSharedPreferences("LOGIN",context.MODE_PRIVATE);
//        return sharedPreferences.getString(name,dvalue);
//
//    }
////    public boolean isLoggin()
////    {
////
////        return sharedPreferences.getBoolean(Login,false);
////    }
////
////    public LoginFragment checkLogin()
////    {
////        if(!this.isLoggin())
////        {
////            LoginFragment loginFragment= new LoginFragment();
////            //FragmentTransaction fragmentTransaction=FragmentTransaction.beginTransaction();
////           // fragmentTransaction.replace(R.id.fragment_container,loginFragment);
////           // fragmentTransaction.commit();
////            Toast.makeText(context, "You have to login first", Toast.LENGTH_SHORT).show();
////            return loginFragment;
//////            Intent i= new Intent(context,LoginTabFragment.class);
//////            context.startActivity(i);
////           // ((Home) context).finish();
////        }
////        return null;
////    }
////
////
////    public HashMap<String, String> getuserDetails()
////    {
////        HashMap<String, String> user= new HashMap<>();
////        user.put(Name,sharedPreferences.getString(Name, null));
////        user.put(Name,sharedPreferences.getString(Name, null));
////
////        return user;
////    }
////
////    public  void logout()
////    {
////        editor.clear();
////        editor.commit();
////        Toast.makeText(context, "Log out", Toast.LENGTH_SHORT).show();
//////        Intent i= new Intent(context,LoginTabFragment.class);
//////        context.startActivity(i);
////
////    }


}
