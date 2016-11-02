package com.azt3k.sprite;


import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.uimanager.*;
import com.facebook.react.uimanager.annotations.ReactProp;


public class SpriteManager extends SimpleViewManager<Sprite> {

    private static final String REACT_CLASS = "Sprite";

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @Override
    public Sprite createViewInstance(ThemedReactContext context) {
        return new Sprite(context);
    }

    @ReactProp(name = "imageNumber")
    public void setImageNumber(Sprite view, Integer imageNumber) {
        view.setImageNumber(imageNumber);
    }

    @ReactProp(name = "repeatCount")
    public void setRepeatCount(Sprite view, Integer repeatCount) {
        view.setRepeatCount(repeatCount);
    }

    @ReactProp(name = "imageLayout")
    public void setImageLayout(Sprite view, String imageLayout) {
        view.setImageLayout(imageLayout);
    }

    @ReactProp(name = "animated")
    public void setAnimated(Sprite view, Boolean animated) {
        view.setAnimated(animated);
    }

    @ReactProp(name = "duration")
    public void setDuration(Sprite view, Double duration) {
        view.setDuration(duration);
    }
}
