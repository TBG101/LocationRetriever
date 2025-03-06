package com.ziedharzallah.locationretriever;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PositionAdapater extends RecyclerView.Adapter {
    private List<Position> data;

    public PositionAdapater(List<Position> data) {
        this.data = data;
    }

}
