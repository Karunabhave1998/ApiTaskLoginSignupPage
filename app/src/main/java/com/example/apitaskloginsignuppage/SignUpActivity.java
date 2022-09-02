package com.example.apitaskloginsignuppage;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity {

    EditText name,mail,mob,pass;
    TextView response_txt;

    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    String baseurl="https://www.androiddada.com/Clostitch/Code/Clo/Register";
    String nameStr,mailStr,mobStr,passStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        name=findViewById(R.id.name_edt);
        mail=findViewById(R.id.mail_edt);
        mob=findViewById(R.id.mobile_edt);
        pass=findViewById(R.id.pass_edt);
        response_txt=findViewById(R.id.response_text);

        requestQueue= Volley.newRequestQueue(this);
        progressDialog=new ProgressDialog(this);
        progressDialog.setCancelable(false);

        findViewById(R.id.signup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                signupMethod();
            }
        });
    }

    private void signupMethod() {
        progressDialog.show();

        nameStr=name.getText().toString();
        mailStr=mail.getText().toString();
        mobStr=mob.getText().toString();
        passStr=pass.getText().toString();

        HashMap<String,Object> map=new HashMap<>();
        map.put("Name",nameStr);
        map.put("Email",mailStr);
        map.put("Mobile",mobStr);
        map.put("Password",passStr);


        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, baseurl, new JSONObject(map), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();
                try {
                    String message=response.getString("message");
                    String description=response.getString("description");

                    response_txt.setText(""+message+"\n"+
                            description);






                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                response_txt.setText(""+error.getLocalizedMessage());


            }
        });
        //Form Data Key,value using HashMap
        /*{
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }
        }*/


        requestQueue.add(jsonObjectRequest);
    }
}