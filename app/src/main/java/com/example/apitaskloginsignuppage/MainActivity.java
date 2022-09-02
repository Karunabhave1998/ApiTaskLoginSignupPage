package com.example.apitaskloginsignuppage;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    EditText em,pass;
    TextView response_text;

    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    String baseurl="https://www.androiddada.com/Clostitch/Code/Clo/Login";
    String emStr,passStr;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        em=findViewById(R.id.em_edt);
        pass=findViewById(R.id.pass_edt);
        response_text=findViewById(R.id.response_text);

        requestQueue= Volley.newRequestQueue(this);
        progressDialog=new ProgressDialog(this);
        progressDialog.setCancelable(false);

        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loginMethod();
            }
        });

        findViewById(R.id.signup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(MainActivity.this,SignUpActivity.class));
            }
        });
    }

    private void loginMethod() {
        progressDialog.show();

        emStr=em.getText().toString();
        passStr=pass.getText().toString();



        HashMap<String,Object> map=new HashMap<>();
        map.put("Email",emStr);
        map.put("Mobile",emStr);
        map.put("Password",passStr);

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, baseurl, new JSONObject(map), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();

//
//                String message=response.getString("message");
//                String description=response.getString("description");


                try {

//                        JSONObject jsonObject = response.getJSONObject(map);
//                        response_text.setText(jsonObject.names().getString(1));

                    //  response_text.setText(""+response.get("info"));

//                response_text.setText(response.names().getString());

                    String code = response.getString("code");
                    String message = response.getString("message");
                    String description = response.getString("description");

                    JSONArray jsonArray =response.getJSONArray("Info");
                    JSONObject infoObj = jsonArray.getJSONObject(0);

                    String name = infoObj.getString("Name");
                    String mobile = infoObj.getString("Mobile");
                    String email = infoObj.getString("Email");

                    response_text.setText(description+"\n"+name+"\n"+mobile+"\n"+email);






                } catch (JSONException e) {
                    e.printStackTrace();
                }


//                String message=response.getString("message");
//                String description=response.getString("description");
//                response_txt.setText(""+message+"\n"+
//                        description);





            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                response_text.setText(""+error.getLocalizedMessage());


            }
        })
                //Form Data Key,value using HashMap
        /*{
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }
        }*/
                ;

        requestQueue.add(jsonObjectRequest);
    }
}