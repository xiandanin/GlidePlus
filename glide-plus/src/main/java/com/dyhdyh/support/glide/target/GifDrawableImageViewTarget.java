package com.dyhdyh.support.glide.target;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.bumptech.glide.request.target.SquaringDrawable;

import pl.droidsonroids.gif.GifDrawable;

/**
 * A { com.bumptech.glide.request.target.Target} that can display an {@link Drawable} in
 * an {@link ImageView}.
 */
public class GifDrawableImageViewTarget extends ImageViewTarget<Drawable> {
    private static final float SQUARE_RATIO_MARGIN = 0.05f;
    private int maxLoopCount;
    private Drawable resource;

    /**
     * Constructor for an { com.bumptech.glide.request.target.Target} that can display an
     * { GlideDrawable} in an {@link ImageView}.
     *
     * @param view The view to display the drawable in.
     */
    public GifDrawableImageViewTarget(ImageView view) {
        this(view, 0);
    }

    /**
     * Constructor for an { com.bumptech.glide.request.target.Target} that can display an
     * { GlideDrawable} in an {@link ImageView}.
     *
     * @param view         The view to display the drawable in.
     * @param maxLoopCount A value to pass to to { GlideDrawable}s
     *                     indicating how many times they should repeat their animation (if they have one). See
     *                     { GlideDrawable#setLoopCount(int)}.
     */
    public GifDrawableImageViewTarget(ImageView view, int maxLoopCount) {
        super(view);
        this.maxLoopCount = maxLoopCount;
    }

    /**
     * {@inheritDoc}
     * If no { GlideAnimation} is given or if the animation does not set the
     * {@link Drawable} on the view, the drawable is set using
     * {@link ImageView#setImageDrawable(Drawable)}.
     *
     * @param resource  {@inheritDoc}
     * @param animation {@inheritDoc}
     */
    @Override
    public void onResourceReady(Drawable resource, GlideAnimation<? super Drawable> animation) {
        if (resource instanceof GlideDrawable) {
            if (!((GlideDrawable) resource).isAnimated()) {
                //TODO: Try to generalize this to other sizes/shapes.
                // This is a dirty hack that tries to make loading square thumbnails and then square full images less costly
                // by forcing both the smaller thumb and the larger version to have exactly the same intrinsic dimensions.
                // If a drawable is replaced in an ImageView by another drawable with different intrinsic dimensions,
                // the ImageView requests a layout. Scrolling rapidly while replacing thumbs with larger images triggers
                // lots of these calls and causes significant amounts of jank.
                float viewRatio = view.getWidth() / (float) view.getHeight();
                float drawableRatio = resource.getIntrinsicWidth() / (float) resource.getIntrinsicHeight();
                if (Math.abs(viewRatio - 1f) <= SQUARE_RATIO_MARGIN
                        && Math.abs(drawableRatio - 1f) <= SQUARE_RATIO_MARGIN) {
                    resource = new SquaringDrawable(((GlideDrawable) resource), view.getWidth());
                }
            }
        }
        super.onResourceReady(resource, animation);
        this.resource = resource;
        if (resource instanceof GifDrawable) {
            ((GifDrawable) resource).setLoopCount(maxLoopCount);
            ((GifDrawable) resource).start();
        }
    }

    /**
     * Sets the drawable on the view using
     * {@link ImageView#setImageDrawable(Drawable)}.
     *
     * @param resource The {@link Drawable} to display in the view.
     */
    @Override
    protected void setResource(Drawable resource) {
        view.setImageDrawable(resource);
    }

    @Override
    public void onStart() {
        if (resource != null && resource instanceof GifDrawable) {
            ((GifDrawable) resource).start();
        }
    }

    @Override
    public void onStop() {
        if (resource != null && resource instanceof GifDrawable) {
            ((GifDrawable) resource).stop();
        }
    }
}
