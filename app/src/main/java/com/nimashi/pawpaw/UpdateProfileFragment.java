package com.nimashi.pawpaw;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UpdateProfileFragment extends Fragment {


    EditText username,mobile;
    TextView email,password;
    Button btn1,btn2;
    String session;
    ProgressBar loading;
    String id,pname,pmobile;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_update_profile,container,false);

        username=view.findViewById(R.id.username);
        email=view.findViewById(R.id.email);
        password=view.findViewById(R.id.password);
        mobile=view.findViewById(R.id.mobile);

        loading=view.findViewById(R.id.loading);
        btn1=view.findViewById(R.id.updateprofile);

        SessionManagement sessionManagement=new SessionManagement(getActivity());
        HashMap<String,String> userdetails= sessionManagement.getUserDetailsFromSession();
        id=userdetails.get(SessionManagement.Key_ID);

        details();

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading.setVisibility(View.VISIBLE);
                final String name = username.getText().toString().trim();
                final String uMobile = mobile.getText().toString().trim();


                if (TextUtils.isEmpty(name)) {
                    username.setError("Username is required");
                    loading.setVisibility(View.GONE);
                    return;
                }


                else if (TextUtils.isEmpty(uMobile)) {
                    mobile.setError("Mobile number is required");
                    loading.setVisibility(View.GONE);
                    return;
                }

                else if (uMobile.length() != 10 || !uMobile.matches("\\d+(?:\\.\\d+)?")) {
                    mobile.setError("Invalid Mobile Number");
                    loading.setVisibility(View.GONE);
                    return;
                } else {

                    RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
                    String url = "https://beezzserver.com/nimashi/paw/person/update.php";
                    StringRequest request = new StringRequest(Request.Method.POST, url,

                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Toast.makeText(getActivity(), "Update Successfully", Toast.LENGTH_SHORT).show();


                                    //create session

                                    SessionManagement sessionManagement=new SessionManagement(getActivity());
                                    sessionManagement.cretateLoginSession(String.valueOf(id),name,uMobile);

                                    loading.setVisibility(View.GONE);
                                    ProfileFragment profileFragment= new ProfileFragment();
                                    FragmentTransaction fragmentTransaction=getFragmentManager().beginTransaction();
                                    fragmentTransaction.replace(R.id.fragment_container,profileFragment);
                                    fragmentTransaction.commit();
                                }
                            },

                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    error.printStackTrace();
                                    Toast.makeText(getActivity(), "Update Not Successful", Toast.LENGTH_SHORT).show();
                                    loading.setVisibility(View.GONE);
                                }
                            }
                    ) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String, String> params = new HashMap<>();
                            params.put("id",id);
                            params.put("name", name);
                            params.put("mobile", uMobile);


                            return params;
                        }
                    };
                    queue.add(request);


                }
            }
        });


        return view;
    }

    public void details()
    {
        RequestQueue queue= Volley.newRequestQueue(getActivity().getApplicationContext());
        String url="http://beezzserver.com/nimashi/paw/person/index.php?id="+id+"";
        JsonArrayRequest request=new JsonArrayRequest(Request.Method.GET,url,null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {



                try {

                    JSONObject obj = response.getJSONObject(0);


                    pname=obj.getString("username");
                    String pemail=obj.getString("email");
                    String ppassword=obj.getString("password");
                    pmobile=obj.getString("mobile");


                    username.setText(pname);
                    email.setText(pemail);
                    password.setText(ppassword);
                    mobile.setText(pmobile);



                    //Toast.makeText(getActivity(), ""+pname, Toast.LENGTH_SHORT).show();





                }
                catch (Exception e) {
                    e.printStackTrace();


                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(request);
    }


}
