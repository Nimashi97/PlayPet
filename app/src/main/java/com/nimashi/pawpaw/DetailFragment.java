package com.nimashi.pawpaw;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONObject;

public class DetailFragment extends Fragment {

    String id1;
    ImageView img,call,email;
    TextView id,username,sex,weight,height,age,description,place,petName,petSpeciality,pEmail,pMobile,cancel;
    Button delete,edit;
    Dialog dialog;
    String personEmail;
    int personMobile;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_details,container,false);

        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        img=view.findViewById(R.id.pet_photo);
        id=view.findViewById(R.id.pet_id);
        petName=view.findViewById(R.id.pet_name);
        petSpeciality=view.findViewById(R.id.pet_speciality);
        age=view.findViewById(R.id.pet_age);
        sex=view.findViewById(R.id.sex);
        height=view.findViewById(R.id.pet_height);
        weight=view.findViewById(R.id.pet_weight);
        place=view.findViewById(R.id.pet_place);
        description=view.findViewById(R.id.pet_description);
        username=view.findViewById(R.id.username);

        delete=view.findViewById(R.id.delete);
        edit=view.findViewById(R.id.edit);



        //id1=getIntent().getStringExtra("id");
        // id1= (String) id.getText();

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //Toast.makeText(DetailsActivity.this, ""+id1, Toast.LENGTH_SHORT).show();
        details();
        return view;
    }

    public void details()
    {
        RequestQueue queue= Volley.newRequestQueue(getActivity().getApplicationContext());
        String url="http://beezzserver.com/nimashi/paw/pet/index.php?id="+id1+"";
        JsonArrayRequest request=new JsonArrayRequest(Request.Method.GET,url,null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {



                try {

                    JSONObject obj = response.getJSONObject(0);

                    String imageUrl=obj.getString("pet_photo");
                    String url="http://beezzserver.com/nimashi/paw/pet/Images/"+imageUrl;
                    Glide.with(getActivity()).load(url).centerCrop().into(img);
                    String petname=obj.getString("pet_name");
                    String petspeciality=obj.getString("pet_speciality");
                    String petage=obj.getString("age");
                    String petsex=obj.getString("sex");
                    String petweight=obj.getString("weight");
                    String petheight=obj.getString("height");

                    String personname=obj.getString("username");
                    personMobile=obj.getInt("mobile");
                    personEmail=obj.getString("email");
                    String petplace=obj.getString("address");

                    String petdes=obj.getString("description");


                    petName.setText(petname);
                    petSpeciality.setText(petspeciality);
                    age.setText(petage);
                    sex.setText(petsex);
                    weight.setText(petweight);
                    height.setText(petheight);
                    username.setText(personname);
                    place.setText(petplace);
                    description.setText(petdes);



                    //Toast.makeText(DetailsActivity.this, ""+petName, Toast.LENGTH_SHORT).show();





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
