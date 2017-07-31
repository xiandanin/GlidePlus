package com.dyhdyh.support.glide.animator;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.view.View;
import android.view.animation.BounceInterpolator;

import com.bumptech.glide.request.animation.ViewPropertyAnimation;

/**
 * 缩放弹性动画
 * author  dengyuhan
 * created 2017/3/30 17:31
 */
public class DrawableScaleBounceAnimator implements ViewPropertyAnimation.Animator {

    private final int duration;
    private static final int DEFAULT_DURATION_MS = 800;


    public DrawableScaleBounceAnimator() {
        this(DEFAULT_DURATION_MS);
    }

    public DrawableScaleBounceAnimator(int duration) {
        this.duration = duration;
    }

    @Override
    public void animate(View view) {
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(view,
                PropertyValuesHolder.ofFloat("scaleX", 0f, 1f),
                PropertyValuesHolder.ofFloat("scaleY", 0f, 1f))
                .setDuration(duration);
        animator.setInterpolator(new BounceInterpolator());
        animator.start();
    }
}
