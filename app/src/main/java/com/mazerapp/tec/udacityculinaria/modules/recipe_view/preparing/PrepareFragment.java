package com.mazerapp.tec.udacityculinaria.modules.recipe_view.preparing;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.mazerapp.tec.udacityculinaria.R;
import com.mazerapp.tec.udacityculinaria.models.Steps;
import com.mazerapp.tec.udacityculinaria.modules.recipe_view.video_view.VideoViewFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.mazerapp.tec.udacityculinaria.utils.Constants.EXTRA_LIST_STEPS;
import static com.mazerapp.tec.udacityculinaria.utils.Constants.EXTRA_STEP_SELECTED;

public class PrepareFragment extends Fragment {


    @BindView(R.id.tv_step_description)
    TextView tvStepDescription;

    @BindView(R.id.btn_previous_step)
    Button btnPrevious;

    @BindView(R.id.btn_next_step)
    Button btnNext;

    @BindView(R.id.frame_video)
    @Nullable
    FrameLayout frameVideo;

    private PrepareViewModel prepareViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_prepare, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getActivity() == null) return;
        ButterKnife.bind(this, view);
        prepareViewModel = ViewModelProviders.of(getActivity()).get(PrepareViewModel.class);
        registerObservers();
        getExtras();
        setupView();
    }

    private void registerObservers(){
        prepareViewModel.getCurrentStepLiveData().observe(this, this::loadInfo);
        prepareViewModel.getToolbarTextLiveData().observe(this, this::setupToolbarText);
        prepareViewModel.getShowBtnPreviousLiveData().observe(this , this::setButtonPrevious);
        prepareViewModel.getShowBtnNextLiveData().observe(this , this::setButtonNext);
    }


    private void setButtonPrevious(boolean show) {
        btnPrevious.setEnabled(show);
    }

    private void setButtonNext(boolean show) {
        btnNext.setEnabled(show);
    }

    public static PrepareFragment newInstance(ArrayList<Steps> listSteps, int indexSelected) {
        PrepareFragment myFragment = new PrepareFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(EXTRA_LIST_STEPS, listSteps);
        args.putInt(EXTRA_STEP_SELECTED, indexSelected);
        myFragment.setArguments(args);
        return myFragment;
    }

    private void setupView(){
        btnPrevious.setOnClickListener(v -> prepareViewModel.onLastStepClicked());
        btnNext.setOnClickListener(v -> prepareViewModel.onNextStepClicked());
    }

    private void loadInfo(Steps step){
        tvStepDescription.setText(step.getDescription());
        setupVideoView(step.getVideoURL());
    }

    private void setupVideoView(String videoURL){

        if (getActivity() == null || frameVideo == null) return;
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_video,
                VideoViewFragment.newInstance(videoURL,0L,0)).commit();
    }

    private void getExtras(){
        if (getActivity() == null) return;
        prepareViewModel.getExtras(getArguments());
    }

    private void setupToolbarText(String text){
        if (getActivity() == null) return;
        getActivity().setTitle(text);
    }

}
