package com.nimashi.pawpaw;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
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
import java.util.List;

public class AdoptFragment extends Fragment implements View.OnClickListener {

    String dog="Dog";
    String cat="Cat";

    ProgressBar bar;
    Bitmap bitmap;
    Button dogs,cats,birds,others,all;
    ListView lv;
    List<Model> list;
    List<Model> list1;
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_adopt,container,false);

        getActivity().setTitle("Paw");

        if(!isConnected(getActivity()))
        {
            showCutomDialog();
        }



        all= view.findViewById(R.id.all);
        dogs=view.findViewById(R.id.dogs);
        cats=view.findViewById(R.id.cats);
        birds=view.findViewById(R.id.birds);
        others=view.findViewById(R.id.others);
        list= new ArrayList<>();
        list1= new ArrayList<>();
        //ItemAdapter itemAdapter= new ItemAdapter(getActivity(),list);

        recyclerView=view.findViewById(R.id.data_list);
        recyclerView.setHasFixedSize(true);
        //recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //recyclerView.setAdapter(itemAdapter);
        //all.setBackgroundColor(getResources().getColor(R.color.secondaryText));


        all.setOnClickListener(this);
        dogs.setOnClickListener(this);
        cats.setOnClickListener(this);



        return view;
    }

    private void showCutomDialog() {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setMessage("Please Check You are Connected to the Internet")
                .setCancelable(false)
                .setPositiveButton("Connect", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(getActivity(),MainActivity.class));
                    }
                });
    }

    private boolean isConnected(Context context) {
        ConnectivityManager connectivityManager= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiConn= connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobConn= connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if((wifiConn !=null && wifiConn.isConnected()) || (mobConn !=null && mobConn.isConnected()))
        {
            return true;
        }
        else
        {
            return  false;
        }

    }

    @Override
    public void onResume() {
        super.onResume();

        loadData();

//        //Toast.makeText(getActivity(), "ok", Toast.LENGTH_SHORT).show();
//        dogs.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                dogs.setBackgroundColor(getResources().getColor(R.color.secondaryText));
//                loadDogData();
//            }
//        });
//        all.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                all.setBackgroundColor(getResources().getColor(R.color.secondaryText));
//                loadData();
//            }
//        });

    }

    private void loadData() {

        RequestQueue queue= Volley.newRequestQueue(getActivity());
        String url="http://beezzserver.com/nimashi/paw/pet/index.php";
        JsonArrayRequest request=new JsonArrayRequest(Request.Method.GET,url,null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                JSONObject obj = null;

                try {
                for (int i = 0; i < response.length(); i++) {



                        obj = response.getJSONObject(i);

                        String imageUrl=obj.getString("pet_photo");
                        String url="http://beezzserver.com/nimashi/paw/pet/Images/"+imageUrl;
                        Model model = new Model();

                        model.setImage(url);
                        model.setName(obj.getString("pet_name"));
                        model.setType(obj.getString("pet_type"));
                        model.setSpeciality(obj.getString("pet_speciality"));
                        model.setAge(obj.getString("age"));
                        model.setPlace(obj.getString("address"));
                        model.setId(obj.getString("id"));

                       // Toast.makeText(getActivity(), "ok"+obj.getString("pet_name"), Toast.LENGTH_SHORT).show();

                        list.add(model);

                    }
                    setuprecycleview(list);

                }
                catch (Exception e) {
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

        private void setuprecycleview(List<Model> list) {

        ItemAdapter myadapter =new ItemAdapter(getContext(),list);
        int numCol =2;
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),numCol));
        recyclerView.setAdapter(myadapter);

    }

    private void loadDogData() {

        RequestQueue queue= Volley.newRequestQueue(getActivity());
        String url="http://beezzserver.com/nimashi/paw/pet/index.php?pet_type="+dog+"";
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
                        Model model = new Model();

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

                    setuprecycleview(list);

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

    private void loadCatData() {

        RequestQueue queue= Volley.newRequestQueue(getActivity());
        String url="http://beezzserver.com/nimashi/paw/pet/index.php?pet_type="+cat+"";
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
                        Model model = new Model();

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

                    setuprecycleview(list);

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


    @Override
    public void onClick(View view) {


        switch (view.getId())
        {
            case R.id.all:

                Intent intent= new Intent(getActivity(),Home.class);
                startActivity(intent);
                all.setBackgroundColor(getResources().getColor(R.color.secondaryText));

                break;

            case R.id.dogs:
                loadDogData();
                dogs.setBackgroundColor(getResources().getColor(R.color.secondaryText));
                break;

            case R.id.cats:
                loadCatData();
                cats.setBackgroundColor(getResources().getColor(R.color.secondaryText));
                break;


        }
    }
}
