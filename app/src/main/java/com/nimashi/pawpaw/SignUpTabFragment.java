package com.nimashi.pawpaw;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class SignUpTabFragment extends Fragment {

    EditText username,email,password,confirm_password,mobile;
    Button btn1,btn2;
    String session;
    ProgressBar loading;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.signup_tab_fragment,container,false);

        username=view.findViewById(R.id.username);
        email=view.findViewById(R.id.email);
        password=view.findViewById(R.id.password);
        confirm_password=view.findViewById(R.id.confirm_password);
        mobile=view.findViewById(R.id.mobile);

        loading=view.findViewById(R.id.loading);
        btn1=view.findViewById(R.id.signBtn);
        btn2=view.findViewById(R.id.loginHere);

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading.setVisibility(View.VISIBLE);
                final String name = username.getText().toString().trim();
                final String uEmail = email.getText().toString().trim();
                final String pass = password.getText().toString().trim();
                final String con_pass = confirm_password.getText().toString().trim();
                final String uMobile = mobile.getText().toString().trim();

                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                if (TextUtils.isEmpty(name)) {
                    username.setError("Username is required");
                    loading.setVisibility(View.GONE);
                    return;
                }
                else if (TextUtils.isEmpty(uEmail)) {
                    email.setError("Email is required");
                    loading.setVisibility(View.GONE);
                    return;
                }
                else if (!uEmail.matches(emailPattern)) {
                    email.setError("Invalid Email Address");
                    loading.setVisibility(View.GONE);
                    return;
                }
                else if (TextUtils.isEmpty(pass)) {
                    password.setError("Password Mismatch");
                    loading.setVisibility(View.GONE);
                    return;
                }
                else if (TextUtils.isEmpty(con_pass)) {
                    confirm_password.setError("Username is required");
                    loading.setVisibility(View.GONE);
                    return;
                }
                else if (!pass.equals(con_pass)) {
                    confirm_password.setError("not same password");
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
                    String url = "https://beezzserver.com/nimashi/paw/person/insert.php";
                    StringRequest request = new StringRequest(Request.Method.POST, url,

                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                                    username.setText("");
                                    email.setText("");
                                    password.setText("");
                                    confirm_password.setText("");
                                    mobile.setText("");


                                    loading.setVisibility(View.GONE);
                                    login();
                                }
                            },

                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    error.printStackTrace();
                                    Toast.makeText(getActivity(), "Register Not Successful", Toast.LENGTH_SHORT).show();
                                    loading.setVisibility(View.GONE);
                                }
                            }
                    ) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String, String> params = new HashMap<>();
                            params.put("name", name);
                            params.put("email", uEmail);
                            params.put("password", pass);
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

    public void login()
    {

                LoginFragment loginFragment= new LoginFragment();
                FragmentTransaction fragmentTransaction=getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container,loginFragment);
                fragmentTransaction.commit();

    }
}
