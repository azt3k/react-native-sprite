package com.azt3k.sprite;


import com.facebook.react.uimanager.*;
import com.facebook.react.uimanager.annotations.ReactProp;


public class SpriteManager extends ViewGroupManager<Sprite> {
    private static final String REACT_CLASS = "Sprite";

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @Override
    public Sprite createViewInstance(ThemedReactContext context) {
        return new Sprite(context);
    }

    // @ReactProp(name = "aspect")
    // public void setAspect(Sprite view, int aspect) {
    //     view.setAspect(aspect);
    // }
}
