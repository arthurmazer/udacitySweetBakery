package com.mazerapp.tec.udacityculinaria.utils.interfaces;

import com.mazerapp.tec.udacityculinaria.models.Steps;

import java.util.ArrayList;

public interface OnStepClicked {
    void onStepClicked(ArrayList<Steps> list, int selectedIndex);
}
