package com.nimashi.pawpaw;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginTabFragment extends Fragment {

    ProgressBar bar;
    ScrollView scrollView;
    Button login,btn;
    EditText email,password;
    TextView user,pass;
    String session1="session1";
float v=0;
SessionManagement sessionManagement;

public static LoginTabFragment getInstance()
{
    LoginTabFragment loginTabFragment = new LoginTabFragment();
    return loginTabFragment;
}

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_tab_fragment, container, false);
        Button btn = view.findViewById(R.id.login);
        email=view.findViewById(R.id.email);
        password=view.findViewById(R.id.password);



        bar=view.findViewById(R.id.loading);




        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bar.setVisibility(View.VISIBLE);
                final String uEmail=email.getText().toString().trim();
                final String uPassword=password.getText().toString().trim();

                if(TextUtils.isEmpty(uEmail))
                {
                    email.setError("Email is Empty");
                    return;

                }
                else if(TextUtils.isEmpty(uPassword))
                {
                    password.setError("Password is Empty");
                    return;

                }
                else
                    {
                    RequestQueue queue= Volley.newRequestQueue(getActivity().getApplicationContext());
                    String url="http://beezzserver.com/nimashi/paw/person/login.php";
                    StringRequest request=new StringRequest(Request.Method.POST, url,

                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    // Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                                    try {
                                        JSONObject jsonObject=new JSONObject(response);
                                        String message=jsonObject.getString("status");

                                        if(message.equals("0"))
                                        {

                                                Toast.makeText(getActivity(), "Login Failed!", Toast.LENGTH_SHORT).show();
                                            bar.setVisibility(View.GONE);

                                        }
                                        else
                                        {
                                            String values=jsonObject.getString("values");
                                            JSONArray jsonArray= new JSONArray(values);
                                            JSONObject object=jsonArray.getJSONObject(0);
                                            String un=object.getString("username");
                                            String ue=object.getString("email");
                                            String up=object.getString("password");
                                            int num=object.getInt("mobile");
                                            int id=object.getInt("person_id");

                                            //create session

                                            SessionManagement sessionManagement=new SessionManagement(getActivity());
                                            sessionManagement.cretateLoginSession(String.valueOf(id),un,ue,up,String.valueOf(num));




                                            Toast.makeText(getActivity(), un+ " You are Logged in Successfully"+id, Toast.LENGTH_SHORT).show();
                                            ProfileFragment profileFragment= new ProfileFragment();
                                            FragmentTransaction fragmentTransaction=getFragmentManager().beginTransaction();
                                            fragmentTransaction.replace(R.id.fragment_container,profileFragment);
                                            fragmentTransaction.commit();




                                        }


                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        Toast.makeText(getActivity(), "error"+e.toString(), Toast.LENGTH_SHORT).show();
                                        bar.setVisibility(View.GONE);
                                    }
                                }
                            },

                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    error.printStackTrace();
                                    Toast.makeText(getActivity(), "error"+error.getMessage(), Toast.LENGTH_SHORT).show();
                                    bar.setVisibility(View.GONE);
                                }
                            }
                    )
                    {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String,String> params = new HashMap<>();
                            params.put("email",uEmail);
                            params.put("password",uPassword);



                            return params;
                        }
                    };
                    queue.add(request);


                }
                }

            }
        );

//        login=view.findViewById(R.id.login);
//        user=view.findViewById(R.id.user);
//        username=view.findViewById(R.id.username);
//        pass=view.findViewById(R.id.pass);
//        password=view.findViewById(R.id.password);
//
//        login.setTranslationX(800);
//        user.setTranslationX(800);
//        username.setTranslationX(800);
//        pass.setTranslationX(800);
//        password.setTranslationX(800);
//
//        user.setAlpha(v);
//        username.setAlpha(v);
//        pass.setAlpha(v);
//        password.setAlpha(v);
//        login.setAlpha(v);
//
//        user.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
//        username.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
//        pass.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
//        password.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
//        user.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();

        return view;




    }





}

