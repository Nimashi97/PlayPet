package com.nimashi.pawpaw;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONObject;

public class DetailsActivity extends AppCompatActivity {

    String id1;
    ImageView img,call,email;
    TextView id,username,sex,weight,height,age,description,place,petName,petType,petSpeciality,pEmail,pMobile,cancel;
    Button adopt;
    Dialog dialog;
    String personEmail;
    int personMobile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        img=findViewById(R.id.pet_photo);
        id=findViewById(R.id.pet_id);
        petName=findViewById(R.id.pet_name);
        petSpeciality=findViewById(R.id.pet_speciality);
        age=findViewById(R.id.pet_age);
        sex=findViewById(R.id.sex);
        height=findViewById(R.id.pet_height);
        weight=findViewById(R.id.pet_weight);
        place=findViewById(R.id.pet_place);
        description=findViewById(R.id.pet_description);
        username=findViewById(R.id.username);

        adopt=findViewById(R.id.adopt);


        dialog = new Dialog(DetailsActivity.this);
        dialog.setContentView(R.layout.custom_pd_dialoge);

        dialog.setCancelable(true);
        dialog.getWindow().getAttributes().windowAnimations= R.style.animation;
        //Button cancel=dialog.findViewById(R.id.cancel);
        pEmail=dialog.findViewById(R.id.email);
        pMobile=dialog.findViewById(R.id.call);
        call=dialog.findViewById(R.id.callImg);
        email=dialog.findViewById(R.id.emailImg);
        cancel=dialog.findViewById(R.id.cancel);


        id1=getIntent().getStringExtra("id");
       // id1= (String) id.getText();

        //Toast.makeText(DetailsActivity.this, ""+id1, Toast.LENGTH_SHORT).show();
        details();


    }

    public void details()
    {
    RequestQueue queue= Volley.newRequestQueue(this);
    String url="http://beezzserver.com/nimashi/paw/pet/index.php?id="+id1+"";
    JsonArrayRequest request=new JsonArrayRequest(Request.Method.GET,url,null, new Response.Listener<JSONArray>() {
        @Override
        public void onResponse(JSONArray response) {



            try {

                JSONObject obj = response.getJSONObject(0);

                    String imageUrl=obj.getString("pet_photo");
                    String url="http://beezzserver.com/nimashi/paw/pet/Images/"+imageUrl;
                Glide.with(DetailsActivity.this).load(url).centerCrop().into(img);
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

   public void adopt(View view)
   {
       Toast.makeText(DetailsActivity.this, "Ok", Toast.LENGTH_SHORT).show();
       dialog.show();
       pEmail.setText(personEmail);
       pMobile.setText(String.valueOf(personMobile));

       call.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String pM=pMobile.getText().toString();
               String s="tel:0"+pM;
               Intent intent=new Intent(Intent.ACTION_DIAL);
               intent.setData(Uri.parse(s));
               startActivity(intent);

           }
       });

       cancel.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
              // Toast.makeText(DetailsActivity.this, "cancel", Toast.LENGTH_SHORT).show();
               dialog.dismiss();
           }
       });

   }
}
