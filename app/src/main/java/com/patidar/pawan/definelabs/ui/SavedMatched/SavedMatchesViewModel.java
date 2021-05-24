package com.patidar.pawan.definelabs.ui.SavedMatched;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.patidar.pawan.definelabs.ui.AllMatches.AllMatchesRepository;
import com.patidar.pawan.definelabs.ui.AllMatches.MatchesModel;

import java.util.List;

public class SavedMatchesViewModel extends ViewModel {

    private static MutableLiveData<List<MatchesModel>> mTextListMutableLiveData;
    public AllMatchesRepository allMatchesRepository;

    public SavedMatchesViewModel() {
        if(mTextListMutableLiveData==null) {
            mTextListMutableLiveData = new MutableLiveData<>();
            allMatchesRepository = AllMatchesRepository.getInstance();
            mTextListMutableLiveData = allMatchesRepository.getMatchesData();
        }
    }

    public LiveData<List<MatchesModel>> getData() {
        return mTextListMutableLiveData;
    }
}