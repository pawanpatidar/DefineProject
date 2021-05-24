package com.patidar.pawan.definelabs.ui.AllMatches;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.patidar.pawan.definelabs.DBHelper;
import com.patidar.pawan.definelabs.MatchesAdapter;
import com.patidar.pawan.definelabs.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AllMatchesFragment extends Fragment {

    private AllMatchesViewModel allMatchesViewModel;

    RecyclerView recyclerView;
    List<MatchesModel> allmatchList = new ArrayList<>();
    MatchesAdapter adapter ;
    ProgressDialog progressBar = null;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        allMatchesViewModel = new ViewModelProvider(this).get(AllMatchesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = root.findViewById(R.id.recycle_allmatches);
        ShowProgress();
        setAllMatchedData();
        return root;
    }


    public void setAllMatchedData(){
        allMatchesViewModel.getData().observe(getViewLifecycleOwner(), new Observer<List<MatchesModel>>() {
            @Override
            public void onChanged(List<MatchesModel> matchesModels) {
                allmatchList = matchesModels;
                if(allmatchList!=null) {
                    HashMap<String, String> hashMap = DBHelper.getmInstance(getActivity()).getMatches();
                    for (int i = 0; i < allmatchList.size();i++){
                        if(hashMap.containsKey(allmatchList.get(i).getId())){
                            allmatchList.get(i).setMatched(true);
                        }
                    }
                }
                SetRecyclerViewData();

            }
        });
    }

    public void SetRecyclerViewData(){
        StopProgress();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new MatchesAdapter(getActivity(), allmatchList,false);
        recyclerView.setAdapter(adapter);
    }

    public void ShowProgress(){
            progressBar = new ProgressDialog(getActivity());
            progressBar.setTitle("Please wait..");
            progressBar.show();

    }

    public void StopProgress(){
        if(progressBar!=null){
            progressBar.cancel();
        }
    }
}