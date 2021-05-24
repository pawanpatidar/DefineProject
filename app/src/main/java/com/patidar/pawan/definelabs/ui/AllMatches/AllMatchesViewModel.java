package com.patidar.pawan.definelabs.ui.AllMatches;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class AllMatchesViewModel extends ViewModel {

    private MutableLiveData<List<MatchesModel>> mTextListMutableLiveData;
    public AllMatchesRepository allMatchesRepository;

    public AllMatchesViewModel() {
        mTextListMutableLiveData = new MutableLiveData<>();
        allMatchesRepository = AllMatchesRepository.getInstance();
        mTextListMutableLiveData  = allMatchesRepository.getMatchesData();

    }

    public LiveData<List<MatchesModel>> getData() {
        return mTextListMutableLiveData;
    }
}