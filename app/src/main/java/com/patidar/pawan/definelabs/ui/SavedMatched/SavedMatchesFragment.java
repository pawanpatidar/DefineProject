package com.patidar.pawan.definelabs.ui.SavedMatched;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.patidar.pawan.definelabs.DBHelper;
import com.patidar.pawan.definelabs.MatchesAdapter;
import com.patidar.pawan.definelabs.R;
import com.patidar.pawan.definelabs.ui.AllMatches.MatchesModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SavedMatchesFragment extends Fragment {

    private SavedMatchesViewModel savedMatchesViewModel;

    RecyclerView recyclerView;
    List<MatchesModel> savedMatchesList = new ArrayList<>();
    MatchesAdapter adapter ;
    ProgressDialog progressBar = null;
    TextView nodata;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        savedMatchesViewModel =
                new ViewModelProvider(this).get(SavedMatchesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        recyclerView = root.findViewById(R.id.recycle_savedmatches);
        nodata = root.findViewById(R.id.nodata);
        ShowProgress();
        setSavedMatchedData();
        return root;
    }


    public void setSavedMatchedData(){
        savedMatchesViewModel.getData().observe(getViewLifecycleOwner(), new Observer<List<MatchesModel>>() {
            @Override
            public void onChanged(List<MatchesModel> matchesModels) {
                if(matchesModels !=null) {
                    HashMap<String, String> hashMap = DBHelper.getmInstance(getActivity()).getMatches();
                    for (int i = 0; i < matchesModels.size(); i++){
                        if(hashMap.containsKey(matchesModels.get(i).getId())){
                            matchesModels.get(i).setMatched(true);
                            savedMatchesList.add(matchesModels.get(i));
                        }
                    }
                }
                SetRecyclerViewData();

            }
        });
    }

    public void SetRecyclerViewData(){
        StopProgress();
        if(savedMatchesList!=null && savedMatchesList.size()>0) {
            nodata.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            adapter = new MatchesAdapter(getActivity(), savedMatchesList,true);
            recyclerView.setAdapter(adapter);
        }else{
            recyclerView.setVisibility(View.GONE);
            nodata.setVisibility(View.VISIBLE);

        }
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