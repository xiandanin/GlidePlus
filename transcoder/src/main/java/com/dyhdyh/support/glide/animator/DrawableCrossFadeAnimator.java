package com.dyhdyh.support.glide.animator;

import android.animation.ObjectAnimator;
import android.view.View;

import com.bumptech.glide.request.animation.ViewPropertyAnimation;

/**
 * 淡入淡出动画
 * author  dengyuhan
 * created 2017/3/30 17:31
 */
public class DrawableCrossFadeAnimator implements ViewPropertyAnimation.Animator {

    private final int duration;
    private static final int DEFAULT_DURATION_MS = 300;


    public DrawableCrossFadeAnimator() {
        this(DEFAULT_DURATION_MS);
    }

    public DrawableCrossFadeAnimator(int duration) {
        this.duration = duration;
    }

    @Override
    public void animate(View view) {
        ObjectAnimator.ofFloat(view, "alpha", 0f, 1f).setDuration(duration).start();
    }
}
