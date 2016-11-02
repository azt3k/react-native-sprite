package com.azt3k.sprite;

import android.content.Context;
import android.graphics.Rect;
//import android.view.ViewGroup;
import android.opengl.GLSurfaceView;
//import android.R;
import android.util.Log;

import com.twicecircled.spritebatcher.Drawer;
import com.twicecircled.spritebatcher.SpriteBatcher;

//import java.util.List;

import javax.microedition.khronos.opengles.GL10;

public class Sprite extends GLSurfaceView implements Drawer {
    // ViewGroup {

    private final Context _context;
    private Rect sourceRect = new Rect(0, 0, 210, 240);
    private Rect destRect = new Rect(0, 0, 210, 240);

    public Sprite(Context context) {
        super(context);
        this._context = context;

        // Log.d("Sprite", context.getPackageName());

        int[] resourceIds = new int[] {
            context.getResources().getIdentifier("spins_pin_0", "drawable", context.getPackageName()),
            context.getResources().getIdentifier("spins_pin_1", "drawable", context.getPackageName()),
            context.getResources().getIdentifier("spins_pin_prize_1", "drawable", context.getPackageName())
        };

        // Log.d("Sprite", "" + resourceIds[2]);
        // Log.d("Sprite", context.getResources().getResourceTypeName(resourceIds[0]));

        this.setRenderer(new SpriteBatcher(context, resourceIds, this));

    }

    @Override
    public void onDrawFrame(GL10 gl, SpriteBatcher sb) {
        sb.draw(
                this._context.getResources().getIdentifier("spins_pin_0", "drawable", this._context.getPackageName()),
                this.sourceRect,
                this.destRect
        );
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
      // No-op since UIManagerModule handles actually laying out children.
    }

    public void setImageNumber(Integer imageNumber) {

    }

    public void setRepeatCount(Integer repeatCount) {

    }

    public void setImageLayout(String imageLayout) {

    }

    public void setAnimated(Boolean animated) {

    }

    public void setDuration(Double duration) {

    }
}
