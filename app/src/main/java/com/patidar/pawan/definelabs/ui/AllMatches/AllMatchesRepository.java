package com.patidar.pawan.definelabs.ui.AllMatches;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.patidar.pawan.definelabs.ApiInterFace.ApiClient;
import com.patidar.pawan.definelabs.ApiInterFace.ApiInterface;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllMatchesRepository {

    private static   AllMatchesRepository allMatchesRepository;

    public static AllMatchesRepository getInstance(){
        if(allMatchesRepository==null){
            allMatchesRepository = new AllMatchesRepository();

        }
        return allMatchesRepository;
    }


    public MutableLiveData<List<MatchesModel>> getMatchesData(){
        MutableLiveData<List<MatchesModel>> mutableLiveData = new MutableLiveData<>();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> call = apiService.getVenues();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {

                    if (response.code() == 200) {
                        List<MatchesModel> matchesModelList = new ArrayList<>();
                        JSONObject object = new JSONObject(response.body().string());
                        JSONObject responsedata = object.getJSONObject("response");
                        JSONArray jsonArray = responsedata.getJSONArray("venues");
                        for(int i=0 ; i< jsonArray.length() ; i ++){
                            JSONObject object1 = jsonArray.getJSONObject(i);
                            String id = object1.getString("id");
                            String vanueName = object1.getString("name");
                            JSONObject location = object1.getJSONObject("location");
                            JSONArray arrayaddress = location.getJSONArray("formattedAddress");
                            String address= (""+arrayaddress).substring(1,(""+arrayaddress).length()-1);
                            Log.e("Address ", ""+address);
                            MatchesModel matchesModel = new MatchesModel(id,vanueName,address,false);
                            matchesModelList.add(matchesModel);
                        }
                        mutableLiveData.setValue(matchesModelList);
                    }else {
                       mutableLiveData.setValue(null);
                    }
                } catch (Exception e) {
                    e.printStackTrace();

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                mutableLiveData.setValue(null);
                Log.e("LocationActivty", " " + t.getMessage());
            }
        });

        return mutableLiveData;
    }
}
