package com.azt3k.sprite;

import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.content.Context;
import android.graphics.Rect;
import android.opengl.GLSurfaceView;
import android.graphics.PixelFormat;
import android.util.Log;

import com.facebook.react.ReactInstanceManager;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.twicecircled.spritebatcher.Drawer;
import com.twicecircled.spritebatcher.SpriteBatcher;

import java.util.ArrayList;
import java.lang.Math;

import javax.microedition.khronos.opengles.GL10;

public class Sprite extends GLSurfaceView implements Drawer {
    // ViewGroup {

    private final Context _context;
    private SpriteBatcher _renderer;
    private Rect sourceRect = new Rect(0, 0, 210, 240);
    private Rect destRect = new Rect(0, 0, 210, 240);
    private int[] resourceIds;
    private int imageNumber = 0;
    private int repeatCount = 0;
    private int sW = 0;
    private int sH = 0;
    private int dW = 0;
    private int dH = 0;
    private String imageLayout = "contain";
    private boolean animated;
    private double duration;
    private int count = 0;
    private boolean eventSent = false;

    public Sprite(Context context) {

        super(context);
        this._context = context;

        // set transparent BG
        // this.setZOrderOnTop(true);
        // this.getHolder().setFormat(PixelFormat.TRANSPARENT);
        //
        // // set transparent bg?
        // this.getHolder().setFormat(PixelFormat.RGB_888);
        // this.setZOrderOnTop(false);

        this.setZOrderOnTop(true);
        this.setEGLConfigChooser( 8, 8, 8, 8, 16, 0 );
        this.getHolder().setFormat( PixelFormat.TRANSPARENT );
    }

    @Override
    public void onDrawFrame(GL10 gl, SpriteBatcher sb) {

        if (this.resourceIds == null) return;
        if (this.resourceIds.length < 1) return;

        sb.draw(
            this.resourceIds[this.imageNumber],
            this.sourceRect,
            this.destRect
        );

        if (!eventSent) {

            // emit loaded event
            ReactContext cntx = (ReactContext) this.getContext();
            cntx.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit("spriteLoaded", true);

            // dont do this again
            eventSent = true;
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        if (changed) {
            this.dW = right - left;
            this.dH = bottom - top;
            this.updateDestRect();
        }
    }

    protected void updateDestRect() {

        // default = contain
        // case "contain":
        // case "cover":
        // case "stretch":
        // case "redraw":
        // case "center":
        // case "top":
        // case "bottom":
        // case "left":
        // case "right":
        // case "topLeft", "top-left":
        // case "topRight", "top-right":
        // case "bottomLeft", "bottom-left":
        // case "bottomRight", "bottom-right":
        switch (this.imageLayout) {
            case "stretch":
                this.destRect = new Rect(0, 0, this.dW, this.dH);
                break;

            case "cover":
            case "redraw":
            case "center":
            case "top":
            case "bottom":
            case "left":
            case "right":
            case "topLeft":
            case "top-left":
            case "topRight":
            case "top-right":
            case "bottomLeft":
            case "bottom-left":
            case "bottomRight":
            case "bottom-right":
            case "contain":
            default:

                if (!this.imageLayout.equals("contain")) {
                    Log.d("Sprite", "imageLayout " + this.imageLayout + " not implemented");
                }

                // init the vars
                float sr = (float)this.sW / this.sH;
                float dr = (float)this.dW / this.dH;
                int nW;
                int nH;
                int nT = 0;
                int nL = 0;
                int nR = 0;
                int nB = 0;

                // scale to width
                if (sr > dr) {
                    nW = this.dW;
                    nH = Math.round(this.sH * ((float)nW / this.sW));
                    nT = Math.round((float)(this.dH - nH) / 2);
                    nR = nW;
                    nB = nH + nT;
                }

                // scale to height
                else {
                    nH = this.dH;
                    nW = Math.round(this.sW * ((float)nH / this.sH));
                    nL = Math.round((float)(this.dW - nW) / 2);
                    nR = nW + nL;
                    nB = nH;
                }

                // set the dest rect
                this.destRect = new Rect(nL, nT, nR, nB);
                break;
        }
    }

    public void setImageNumber(int imageNumber) {

        // valid range
        if (imageNumber > this.count - 1) imageNumber = this.count - 1;
        if (imageNumber < 0) imageNumber = 0;

        this.imageNumber = imageNumber;
        // Log.d("Sprite", "imageNumber " + imageNumber);
    }

    public void setRepeatCount(int repeatCount) {
        this.repeatCount = repeatCount;
        // Log.d("Sprite", "repeatCount " + repeatCount);
    }

    public void setImageLayout(String imageLayout) {
        this.imageLayout = imageLayout;
        // Log.d("Sprite", "imageLayout " + imageLayout);
    }

    public void setAnimated(boolean animated) {
        this.animated = animated;
        // Log.d("Sprite", "animated " + animated);
    }

    public void setDuration(double duration) {
        this.duration = duration;
        // Log.d("Sprite", "duration " + duration);
    }

    public void createSequence(String nameWithPath, int count, String format, double duration) {

        // init the resource id array
        int[] r = new int[count];

        // build array
        for (int i = 0; i < count; i++) {
            r[i] = this._context.getResources().getIdentifier(
                nameWithPath + i,
                "drawable",
                this._context.getPackageName()
            );
        }

        // calc source rect - assumes all images are same dims
        if (count > 0) {
            final Options opt = new BitmapFactory.Options();
            opt.inJustDecodeBounds = true;
            BitmapFactory.decodeResource(
                this._context.getResources(),
                r[0],
                opt
            );
            this.sW = opt.outWidth;
            this.sH = opt.outHeight;
            this.sourceRect = new Rect(0, 0, this.sW, this.sH);
        }

        // update this
        this.resourceIds = r;
        this.count = count;
        this._renderer = new SpriteBatcher(this._context, this.resourceIds, this);

        // set renderer
        this.setRenderer(this._renderer);
    }

    public void animate(boolean shouldPlay) {
        Log.d("SpriteModule", "animate: YES!!!");
    }
}
