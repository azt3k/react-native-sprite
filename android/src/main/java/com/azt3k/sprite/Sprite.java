package com.azt3k.sprite;

import android.content.Context;
import android.view.OrientationEventListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View;

import java.util.List;

public class Sprite extends ViewGroup {

    private final Context _context;

    public Sprite(Context context) {
        super(context);
        this._context = context;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
      // No-op since UIManagerModule handles actually laying out children.
    }
}
