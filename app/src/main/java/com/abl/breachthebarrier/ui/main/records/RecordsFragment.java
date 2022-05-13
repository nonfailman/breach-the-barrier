package com.abl.breachthebarrier.ui.main.records;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.abl.breachthebarrier.databinding.FragmentRecordsBinding;


public class RecordsFragment extends Fragment {

    private FragmentRecordsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        RecordsViewModel recordsViewModel =
                new ViewModelProvider(this).get(RecordsViewModel.class);

        binding = FragmentRecordsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textDashboard;
        recordsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    private void initInterface(){

    }

    private void getDataFromDb(){

    }

    private void setData(){

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}