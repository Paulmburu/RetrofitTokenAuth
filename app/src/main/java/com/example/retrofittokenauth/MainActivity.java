package com.example.retrofittokenauth;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.retrofittokenauth.model.Login;
import com.example.retrofittokenauth.model.User;
import com.example.retrofittokenauth.service.UserClient;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl("http://178.79.155.54/events/api/mark_ticket/")
            .addConverterFactory(GsonConverterFactory.create());


    Retrofit retrofit=builder.build();
    UserClient userClient=retrofit.create(UserClient.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        findViewById(R.id.btn_secret).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSecret();
            }
        });
    }

    private static String token;

    private void login(){
        Login login=new Login("charles@deveint.com","deveint#");

        Call<User> call = userClient.login(login);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    Toast.makeText(MainActivity.this, response.body().getToken(), Toast.LENGTH_SHORT).show();
                    token =response.body().getToken();
                }else{

                    Toast.makeText(MainActivity.this, "login not correct :(", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error :(", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void getSecret(){
        Call<ResponseBody> call = userClient.getSecret("Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6IjYwYTliMzM0YzcwMDExYmUyMGM0NjgxYzMwYWZlMzhlNThiMTAwODRjOGIzN2NkYWI2ZGQwYjI2ZjRkZWVhNTkyNGZjM2YxYWU1NDM5NzIxIn0.eyJhdWQiOiIyIiwianRpIjoiNjBhOWIzMzRjNzAwMTFiZTIwYzQ2ODFjMzBhZmUzOGU1OGIxMDA4NGM4YjM3Y2RhYjZkZDBiMjZmNGRlZWE1OTI0ZmMzZjFhZTU0Mzk3MjEiLCJpYXQiOjE1NTc0ODEyOTAsIm5iZiI6MTU1NzQ4MTI5MCwiZXhwIjoxNTg5MTAzNjkwLCJzdWIiOiIyIiwic2NvcGVzIjpbXX0.qqj8BEA_8cPVSgYUbsMgWtjEFEqEj9zhYbZhVkO9USvTeLRD4tpsCFiBiaHIZJt4xSUrGXVutO5l5Ajj8aD3Ct1tje_Bn_Y-gwTyvBdcq5Nfli90X2InqOjp6LmpMFrOKjhEY_ebb6W6ZM5Kd57XcwdMiz26yyG5-zyqPtg_Cl0eEO-ZdNvjhVjAZvN92zkZOgA0-Z3XcosHTNgn3NnpGS5Hy_PlQ_fsGiYd0mbNPq4Lk943uh1tC-RoUjEtPAxb-KX21pABHqyyBX19pJr5aCKDLzhg_XbmaEVg2PlaeamEdcRqF8GdqNNNi0V36kVT2E8arykbvsMSgUxGP3559BBWaqsty4SiuUhQhhDc4PwU0-_l9tf56OCIr_sIXnbpfGvyGC_juoWl_8ihBQyJ1pPxGjWytWfRLLOdAo66vKfbPWr7RpKBEh0wJ49vFsiidmJ8EqCQB9hU_0gNRu2OX3zNsmDP0eCZiK3o3wxmTabwkckEDorrr0DY6pHSI8lyt37kSNZOVo5sqQRSas_BlbHf9-Pzd5FK_kfZ7AHu1T28AKu9umWPC6pqaTXEbH56rZwMYzHMDK904dMXpmBtoBHZCOjLyekE4gVKHETNnomfGlxhrpiWNvjBHyRaVp7Y-8wpgyFCVaNjYfXwHwUnGuhAgpFjdVGumGgkqB0BcXA");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    try {
                        Toast.makeText(MainActivity.this, response.body().string(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{

                    Toast.makeText(MainActivity.this, "Token not correct :(", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error :(", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
