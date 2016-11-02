package com.azt3k.sprite;

import java.util.Map;
import com.facebook.infer.annotation.Assertions;
import javax.annotation.Nullable;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.uimanager.*;
import com.facebook.react.uimanager.annotations.ReactProp;

public class SpriteManager extends SimpleViewManager<Sprite> {

    private static final String REACT_CLASS = "Sprite";

    public static final int COMMAND_ANIMATE = 1;
    public static final int COMMAND_CREATE_SEQUENCE = 2;

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
    public void setDuration(Sprite view, double duration) {
        view.setDuration(duration);
    }

    @Override
    public Map<String,Integer> getCommandsMap() {
        return MapBuilder.of(
            "animate", COMMAND_ANIMATE,
            "createSequence", COMMAND_CREATE_SEQUENCE
        );
    }

    @Override
    public void receiveCommand(Sprite view, int commandType, @Nullable ReadableArray args) {

        Assertions.assertNotNull(view);
        Assertions.assertNotNull(args);

        switch (commandType) {

            case COMMAND_ANIMATE: {
                // animate(Boolean shouldPlay)
                view.animate(args.getBoolean(0));
                return;
            }

            case COMMAND_CREATE_SEQUENCE: {
                // createSequence(String nameWithPath, Integer count, String format, double duration)
                view.createSequence(args.getString(0), args.getInt(1), args.getString(2), args.getDouble(3));
                return;
            }

            default:
                throw new IllegalArgumentException(String.format(
                    "Unsupported command %d received by %s.",
                    commandType,
                    getClass().getSimpleName()
                ));
        }
    }
}
