package com.nimashi.pawpaw;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UpdatepetFragment extends Fragment {

    ImageView img1;
    Button btn;
    Bitmap bitmap1;
    Uri uri;
    String [] listitems;
    EditText petName,petAge,petWeight,petHeight,petPlace,petDescription;
    String selected;
    Dialog dialog;
    String encode;
    ProgressBar bar;
    String id,id1;
    boolean checked;
    TextView petCategory,petSpeciality,petSex;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_update,container,false);

        getActivity().setTitle("Update pet Details");
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        btn = view.findViewById(R.id.update);
        petCategory= view.findViewById(R.id.pet_category);
        petSpeciality= view.findViewById(R.id.pet_speciality);
        petSex = view.findViewById(R.id.pet_sex);

        final SessionManagement sessionManagement=new SessionManagement(getActivity());
        HashMap<String,String> userdetails= sessionManagement.getUserDetailsFromSession();
        id=userdetails.get(SessionManagement.Key_ID);

        id1=getArguments().getString("id");
       // Toast.makeText(getActivity(), ""+id1, Toast.LENGTH_SHORT).show();


        //Toast.makeText(getActivity(), id, Toast.LENGTH_SHORT).show();


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

        details();

        //dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.setCancelable(true);
        dialog.getWindow().getAttributes().windowAnimations= R.style.animation;

        Button cancel=dialog.findViewById(R.id.cancel);
        Button ok=dialog.findViewById(R.id.ok);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = petName.getText().toString();
                final String category = petCategory.getText().toString();
                final String speciality = petSpeciality.getText().toString();
                final String sex = petSex.getText().toString();

                //Toast.makeText(getActivity(), ""+img, Toast.LENGTH_SHORT).show();

                final String age=petAge.getText().toString();
                final String height=petHeight.getText().toString();
                final String weight=petWeight.getText().toString();
                final String place=petPlace.getText().toString();
                final String des=petDescription.getText().toString();

                if(TextUtils.isEmpty(name)) {
                    petName.setError("pet name is required");
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




                else {
                    dialog.show();
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(getActivity(), "cancel", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });








//        img1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                listitems = new String[]{"Take Photo","Choose from Gallery","Remove Image"};
//                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                builder.setTitle("Select Image");
//                //builder.setIcon(R.drawable.ic_launcher_background);
//                builder.setItems(listitems, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//
//                        if (listitems[i].equals("Take Photo"))
//                        {
//                            camera1();
//                        }
//
//                        else if(listitems[i].equals("Choose from Gallery"))
//                        {
//                            gallery1();
//                        }
//
//
//                    }
//                });
//
////cancel button
//                builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//
//                    }
//                });
//                AlertDialog dialog = builder.create();
//                dialog.show();
//
//
//            }
//
//
//
//            public void camera1()
//            {
//
//                Dexter.withContext(getActivity().getApplicationContext())
//                        .withPermission(Manifest.permission.CAMERA)
//                        .withListener(new PermissionListener() {
//                            @Override
//                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
//                                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                                startActivityForResult(intent,111);
//
//                            }
//
//                            @Override
//                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
//
//                            }
//
//                            @Override
//                            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
//                                permissionToken.continuePermissionRequest();
//                            }
//                        }).check();
//            }
//
//
//            public void gallery1()
//            {
//                Intent intent = new Intent(Intent.ACTION_PICK);
//                intent.setType("image/*");
//                startActivityForResult(intent,1);
//            }
//
//
//        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bar.setVisibility(View.VISIBLE);
                final String name = petName.getText().toString();
                final String category = petCategory.getText().toString();
                final String speciality = petSpeciality.getText().toString();
                final String sex = petSex.getText().toString();

                //Toast.makeText(getActivity(), ""+img, Toast.LENGTH_SHORT).show();

                final String age=petAge.getText().toString();
                final String height=petHeight.getText().toString();
                final String weight=petWeight.getText().toString();
                final String place=petPlace.getText().toString();
                final String des=petDescription.getText().toString();



                //Toast.makeText(getActivity(), "userid"+id, Toast.LENGTH_SHORT).show();





                RequestQueue queue= Volley.newRequestQueue(getActivity().getApplicationContext());
                String url="https://beezzserver.com/nimashi/paw/pet/update.php";
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
                                        Toast.makeText(getActivity(), "Successfully Updated", Toast.LENGTH_SHORT).show();

                                        ProfileFragment profileFragment= new ProfileFragment();
                                        FragmentTransaction fragmentTransaction=getFragmentManager().beginTransaction();
                                        fragmentTransaction.replace(R.id.fragment_container,profileFragment);
                                        fragmentTransaction.commit();
                                        bar.setVisibility(View.GONE);
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    //Toast.makeText(getActivity(), "error"+e.getMessage(), Toast.LENGTH_SHORT).show();
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
                        params.put("id",id1);
                        params.put("name",name);
                        params.put("age",age);
                        params.put("height",height);
                        params.put("weight",weight);
                        params.put("place",place);
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

    public void details()
    {
        RequestQueue queue= Volley.newRequestQueue(getActivity().getApplicationContext());
        String url="http://beezzserver.com/nimashi/paw/pet/index.php?id="+id1+"";
        JsonArrayRequest request=new JsonArrayRequest(Request.Method.GET,url,null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {



                try {

                    JSONObject obj = response.getJSONObject(0);

//                    String imageUrl=obj.getString("pet_photo");
//                    String url="http://beezzserver.com/nimashi/paw/pet/Images/"+imageUrl;

                    String petname=obj.getString("pet_name");
                    String petcategory=obj.getString("pet_type");
                    String petspeciality=obj.getString("pet_speciality");
                    String petsex=obj.getString("sex");
                    String petage=obj.getString("age");
                    String petweight=obj.getString("weight");
                    String petheight=obj.getString("height");
                    String petplace=obj.getString("address");
                   // Glide.with(getActivity()).load(url).centerCrop().into(img1);
                    String petdes=obj.getString("description");


                    petName.setText(petname);
                    petCategory.setText(petcategory);
                    petSpeciality.setText(petspeciality);
                    petAge.setText(petage);
                    petSex.setText(petsex);
                    petWeight.setText(petweight);
                    petHeight.setText(petheight);
                    petPlace.setText(petplace);
                    petDescription.setText(petdes);



                    //Toast.makeText(getActivity(), ""+petname, Toast.LENGTH_SHORT).show();





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

//    private void getStringImage(Bitmap bitmap1)
//    {
//        ByteArrayOutputStream ba=new ByteArrayOutputStream();
//        bitmap1.compress(Bitmap.CompressFormat.JPEG,100,ba);
//        byte[] imageByte = ba.toByteArray();
//        encode= android.util.Base64.encodeToString(imageByte, Base64.DEFAULT);
//
//
//
//
//    }
//
//
//
//
//
//
//
//
//
//
//
//
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//
//        if (requestCode==111 & resultCode==RESULT_OK)
//        {
//            bitmap1=(Bitmap)data.getExtras().get("data");
//            img1.setImageBitmap(bitmap1);
//            getStringImage(bitmap1);
//
//
//        }
//
//        if(requestCode==1 & resultCode==RESULT_OK)
//        {
//            uri=data.getData();
//            try {
//
//                InputStream inputStream=getActivity().getContentResolver().openInputStream(uri);
//                bitmap1= BitmapFactory.decodeStream(inputStream);
//
//                //bitmap1= MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),uri);
//                img1.setImageBitmap(bitmap1);
//                getStringImage(bitmap1);
//
//
//
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//        super.onActivityResult(requestCode, resultCode, data);
//    }


}
