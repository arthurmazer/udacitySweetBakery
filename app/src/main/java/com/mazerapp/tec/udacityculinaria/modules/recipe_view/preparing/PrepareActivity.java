package com.mazerapp.tec.udacityculinaria.modules.recipe_view.preparing;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.mazerapp.tec.udacityculinaria.R;
import com.mazerapp.tec.udacityculinaria.models.Steps;
import com.mazerapp.tec.udacityculinaria.modules.base.BaseActivity;

import java.util.ArrayList;

import static com.mazerapp.tec.udacityculinaria.utils.Constants.EXTRA_LIST_STEPS;
import static com.mazerapp.tec.udacityculinaria.utils.Constants.EXTRA_STEP_SELECTED;

public class PrepareActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prepare);
        getExtras();
    }

    private void getExtras() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            ArrayList<Steps> listSteps = bundle.getParcelableArrayList(EXTRA_LIST_STEPS);
            int indexSelected = bundle.getInt(EXTRA_STEP_SELECTED);
            setPrepareFragment(listSteps, indexSelected);
        }
    }

    public void setPrepareFragment(ArrayList<Steps> listSteps, int selectedIndex){
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_preparing,
                PrepareFragment.newInstance(listSteps,selectedIndex)).commit();
    }
}
