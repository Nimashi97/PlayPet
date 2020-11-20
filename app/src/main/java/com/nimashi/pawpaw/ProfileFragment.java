package com.nimashi.pawpaw;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProfileFragment extends Fragment {

    Button btn;
    TextView uEmail,uName,uMobile;
    RecyclerView recyclerView;
    List<PModel> list;
    String id;
    int id1;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile,container,false);

        getActivity().setTitle("My Profile");


        final SessionManagement sessionManagement=new SessionManagement(getActivity());
        HashMap<String,String> userdetails= sessionManagement.getUserDetailsFromSession();


        list= new ArrayList<>();
        recyclerView=view.findViewById(R.id.data_listp);
        recyclerView.setHasFixedSize(true);
        uEmail=view.findViewById(R.id.uEmail);
        uName=view.findViewById(R.id.userName);
        uMobile=view.findViewById(R.id.mobile);

        String name=userdetails.get(SessionManagement.Key_NAME);
        String email=userdetails.get(SessionManagement.Key_EMAIL);
        String mobile=userdetails.get(SessionManagement.Key_MOBILE);
        id=userdetails.get(SessionManagement.Key_ID);

        id1=Integer.parseInt(id);

        Toast.makeText(getActivity(), ""+id1, Toast.LENGTH_SHORT).show();

        uName.setText(name);
        uEmail.setText(email);
        uMobile.setText(mobile);
        btn=view.findViewById(R.id.logout);
        loadData();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManagement.logoutUserFromSession();
                LoginFragment loginFragment= new LoginFragment();
                FragmentTransaction fragmentTransaction=getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container,loginFragment);
                fragmentTransaction.commit();
            }
        });

//        RequestQueue queue= Volley.newRequestQueue(getActivity());
//        String url="http://beezzserver.com/nimashi/paw/person/index.php?person_id="+id1+"";
//        JsonArrayRequest request=new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
//            @Override
//            public void onResponse(JSONArray response) {
//
//            }
//        },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//
//                    }
//                });



        return view;
    }

    private void loadData() {

        RequestQueue queue= Volley.newRequestQueue(getActivity());
        String url="http://beezzserver.com/nimashi/paw/person/petlist.php?person_id="+id1;
        JsonArrayRequest request=new JsonArrayRequest(Request.Method.GET,url,null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {


                if(list!=null)
                {
                    list.clear();
                }

                try {

                    for (int i = 0; i < response.length(); i++) {




                        JSONObject obj = response.getJSONObject(i);

                        String imageUrl=obj.getString("pet_photo");
                        String url="http://beezzserver.com/nimashi/paw/pet/Images/"+imageUrl;
                        PModel model = new PModel();

                        model.setImage(url);
                        model.setName(obj.getString("pet_name"));
                        model.setType(obj.getString("pet_type"));
                        model.setSpeciality(obj.getString("pet_speciality"));
                        model.setAge(obj.getString("age"));
                        model.setPlace(obj.getString("address"));
                        model.setId(obj.getString("id"));

                        //Toast.makeText(getActivity(), "ok"+obj.getString("pet_name"), Toast.LENGTH_SHORT).show();

                        list.add(model);

                    }

                    ItemAdapterP myadapter =new ItemAdapterP(getContext(),list);
                    int numCol =2;
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
                    recyclerView.setAdapter(myadapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "ok"+e.getMessage(), Toast.LENGTH_SHORT).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "ok"+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(request);
    }


}
