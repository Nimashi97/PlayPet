package com.nimashi.pawpaw;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

public class AddPetFragment extends Fragment implements View.OnClickListener {

    ImageView img1;
    Button btn;
    Bitmap bitmap1;
    Uri uri;
    String [] listitems;
    RadioButton male,female,selectedRadio;
    EditText petName,petCategory,petSpeciality,petAge,petWeight,petSex,petHeight,petPlace,petDescription;
    String selected;
    RadioGroup radioGroup;
    String sex;
    Dialog dialog;
    String encode;
    ProgressBar bar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         View view= inflater.inflate(R.layout.fragment_add_pet,container,false);

        getActivity().setTitle("Add a Pet");
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        img1 =view.findViewById(R.id.cam);
        btn = view.findViewById(R.id.add_pet);
        final Spinner spinner= view.findViewById(R.id.pet_category);
        final Spinner spinner1= view.findViewById(R.id.pet_speciality);

        male=view.findViewById(R.id.male);
        male.setOnClickListener(this);
        female=view.findViewById(R.id.female);
        female.setOnClickListener(this);

        radioGroup = view.findViewById(R.id.radioGroup);


        bar=view.findViewById(R.id.loading);

        petName=view.findViewById(R.id.pet_name);
        petAge=view.findViewById(R.id.pet_age);
        petWeight=view.findViewById(R.id.pet_weight);
        petHeight=view.findViewById(R.id.pet_height);
        petPlace=view.findViewById(R.id.pet_place);
        petDescription=view.findViewById(R.id.pet_description);


        dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.custom_dialoge);
        //dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_dialoge));


        //dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.setCancelable(true);
        dialog.getWindow().getAttributes().windowAnimations= R.style.animation;

        Button cancel=dialog.findViewById(R.id.cancel);
        Button ok=dialog.findViewById(R.id.ok);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = petName.getText().toString();
                final String category = spinner.getSelectedItem().toString();
                final String speciality = spinner1.getSelectedItem().toString();

                //Toast.makeText(getActivity(), ""+img, Toast.LENGTH_SHORT).show();

                final String age=petAge.getText().toString();
                final String height=petHeight.getText().toString();
                final String weight=petWeight.getText().toString();
                final String place=petPlace.getText().toString();
                final String des=petDescription.getText().toString();

                if(TextUtils.isEmpty(name)) {
                    petName.setError("pet name is required");
                }

                else if(spinner !=null && spinner.getSelectedItem() !=null && spinner.getSelectedItem() =="-SELECT-")
                {
                    TextView error=(TextView)spinner.getSelectedView();
                    error.setError("you must select category");
                }
                else if(TextUtils.isEmpty(age)) {
                    petAge.setError("pet's age is required");
                }
                else if(TextUtils.isEmpty(height)) {
                    petHeight.setError("pet's Height is required");
                }
                else if(TextUtils.isEmpty(weight)) {
                    petWeight.setError("pet's Weight is required");
                }
                else if(TextUtils.isEmpty(place)) {
                    petPlace.setError("pet's Place is required");
                }
                else if(TextUtils.isEmpty(des)) {
                    petDescription.setError("Description about pet is required");
                }
                else if(img1.getDrawable()==null)
                {
                    Toast.makeText(getActivity(), "You have to add your pet photo", Toast.LENGTH_SHORT).show();
                }



                else {
                    dialog.show();
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "cancel", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });




        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),R.array.pet_category,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(getActivity(),R.array.dog_array,android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getActivity(),R.array.cat_array,android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);






        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(getActivity(),R.array.dog_array,android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


                ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getActivity(),R.array.cat_array,android.R.layout.simple_spinner_item);
                adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                 parent.getItemAtPosition(position);
                if(position==1) {
                    //Toast.makeText(parent.getContext(), "Selected: " + petCategory, Toast.LENGTH_LONG).show();
                    spinner1.setAdapter(adapter1);
                }
                else if (position==2)
                {
                    spinner1.setAdapter(adapter2);
                }
            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });



        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listitems = new String[]{"Take Photo","Choose from Gallery","Remove Image"};
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Select Image");
                //builder.setIcon(R.drawable.ic_launcher_background);
                builder.setItems(listitems, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        if (listitems[i].equals("Take Photo"))
                        {
                            camera1();
                        }

                        else if(listitems[i].equals("Choose from Gallery"))
                        {
                            gallery1();
                        }


                    }
                });

//cancel button
                builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();


            }



            public void camera1()
            {

                Dexter.withContext(getActivity().getApplicationContext())
                        .withPermission(Manifest.permission.CAMERA)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(intent,111);

                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                                permissionToken.continuePermissionRequest();
                            }
                        }).check();
            }


            public void gallery1()
            {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,1);
            }


        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bar.setVisibility(View.VISIBLE);
                final String name = petName.getText().toString();
                final String category = spinner.getSelectedItem().toString();
                final String speciality = spinner1.getSelectedItem().toString();

                //Toast.makeText(getActivity(), ""+img, Toast.LENGTH_SHORT).show();

                final String age=petAge.getText().toString();
                final String height=petHeight.getText().toString();
                final String weight=petWeight.getText().toString();
                final String place=petPlace.getText().toString();
                final String des=petDescription.getText().toString();

                final SessionManagement sessionManagement=new SessionManagement(getActivity());
                HashMap<String,String> userdetails= sessionManagement.getUserDetailsFromSession();
                final String id=userdetails.get(SessionManagement.Key_ID);

                //Toast.makeText(getActivity(), "userid"+id, Toast.LENGTH_SHORT).show();





                RequestQueue queue= Volley.newRequestQueue(getActivity().getApplicationContext());
                String url="https://beezzserver.com/nimashi/paw/pet/insert.php";
                StringRequest request=new StringRequest(Request.Method.POST, url,

                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try
                                {
                                    JSONObject jsonObject=new JSONObject(response);
                                    String message=jsonObject.getString("status");

                                    if(message.equals("0"))
                                    {
                                        Toast.makeText(getActivity(), "Not Success", Toast.LENGTH_SHORT).show();
                                        bar.setVisibility(View.GONE);
                                    }

                                    else
                                    {
                                        Toast.makeText(getActivity(), "Successfully added", Toast.LENGTH_SHORT).show();

                                        Intent intent=new Intent(getActivity(),Home.class);
                                        startActivity(intent);
                                        bar.setVisibility(View.GONE);
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Toast.makeText(getActivity(), "error"+e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        },

                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                error.printStackTrace();
                               // Toast.makeText(getActivity(), "error"+error.getMessage(), Toast.LENGTH_SHORT).show();
                                bar.setVisibility(View.GONE);
                            }
                        }
                )
                {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String,String> params = new HashMap<>();
                        params.put("person_id",id);
                        params.put("name",name);
                        params.put("category",category);
                        params.put("speciality",speciality);
                        params.put("sex",sex);
                        params.put("age",age);
                        params.put("height",height);
                        params.put("weight",weight);
                        params.put("place",place);
                        params.put("img",encode);
                        params.put("des",des);

                        return params;
                    }
                };
                queue.add(request);
                //Toast.makeText(getActivity(), "ok", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        return view;
    }

    private void getStringImage(Bitmap bitmap1)
    {
        ByteArrayOutputStream ba=new ByteArrayOutputStream();
        bitmap1.compress(Bitmap.CompressFormat.JPEG,100,ba);
        byte[] imageByte = ba.toByteArray();
        encode= android.util.Base64.encodeToString(imageByte,Base64.DEFAULT);




    }

    @Override
    public void onClick(View view)
    {
        boolean checked=((RadioButton) view).isChecked();

        switch (view.getId())
        {
            case R.id.male:

                if(checked)
                {
                    Toast.makeText(getActivity(), "male", Toast.LENGTH_SHORT).show();
                    sex=male.getText().toString();


                }
                break;
            case R.id.female:
                if(checked)
                {
                    Toast.makeText(getActivity(), "female", Toast.LENGTH_SHORT).show();
                    sex=female.getText().toString();


                }
                break;
        }
    }











    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode==111 & resultCode==RESULT_OK)
        {
            bitmap1=(Bitmap)data.getExtras().get("data");
            img1.setImageBitmap(bitmap1);
            getStringImage(bitmap1);


        }

        if(requestCode==1 & resultCode==RESULT_OK)
        {
            uri=data.getData();
            try {

                InputStream inputStream=getActivity().getContentResolver().openInputStream(uri);
                bitmap1= BitmapFactory.decodeStream(inputStream);

                //bitmap1= MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),uri);
                img1.setImageBitmap(bitmap1);
                getStringImage(bitmap1);




            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }


}
