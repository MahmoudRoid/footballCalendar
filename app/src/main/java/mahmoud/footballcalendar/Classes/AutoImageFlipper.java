package mahmoud.footballcalendar.Classes;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import java.util.ArrayList;

import mahmoud.footballcalendar.R;

public class AutoImageFlipper extends RelativeLayout {

    private static final int DEFAULT_FLIPPING_INTERVAL = 10000, DEFAULT_ANIM_DURATION = 1000;
    private static final double DEFAULT_ASPECT_RATIO = 4/3;

    private ArrayList<Drawable> drawables;
    private ImageView img1, img2;
    private Handler handler;
    private int position;
    private int flippingInterval, animDuration;
    private double aspectRatio;
    private boolean isShowingImg1 = true;
    private boolean started;
    private int width, height;
    private AnimationSet animationSet1, animationSet2;

    // constructions...
    public AutoImageFlipper(Context context) {
        super(context);
        preInit();
    }

    public AutoImageFlipper(Context context, AttributeSet attrs) {
        super(context, attrs);
        preInit();
    }

    public AutoImageFlipper(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        preInit();
    }

    // initializations
    private void preInit() {
        addView(LayoutInflater.from(getContext()).inflate(R.layout.layout_aif, this, false));
        drawables = new ArrayList<>();
        img1 = (ImageView) findViewById(R.id.aif_img1);
        img2 = (ImageView) findViewById(R.id.aif_img2);
        position = -1;
        setFlippingInterval(DEFAULT_FLIPPING_INTERVAL);
        setAnimDuration(DEFAULT_ANIM_DURATION);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if(width==0)
            init(getMeasuredWidth(), getMeasuredHeight());
    }
    private void init(int width, int height) {
        this.width = width;
        this.height = height;
        setAspectRatio(DEFAULT_ASPECT_RATIO);
        startFlipping();
    }

    // setting drawables and start flipping them....
    @SuppressWarnings("unused")
    public void setDrawables(ArrayList<Drawable> drawables) {
        this.drawables = drawables;
        startFlipping();
    }

    @SuppressWarnings("unused")
    public void addDrawable(Drawable drawable) {
        this.drawables.add(drawable);
        startFlipping();
    }

    // setting position in drawable list
    @SuppressWarnings("unused")
    public void setPosition(int position) throws ArrayIndexOutOfBoundsException {
        if (position >= 0 && position < drawables.size()) {
            this.position = position-1;
        } else {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    public void setFlippingInterval(int flippingInterval) {
        this.flippingInterval = flippingInterval;
    }

    public void setAnimDuration(int animDuration) {
        this.animDuration = animDuration;
    }

    @SuppressWarnings("unused")
    public void setAspectRatio(double aspectRatio) {
        this.aspectRatio = aspectRatio;
        initAnimSets();
    }


    // flipper engine::
    private void startFlipping() { // if possible
        if (started || width==0 || drawables.size()==0)
            return;
        started = true;
        handler = new Handler();
        handler.post(flipperRunnable);
    }

    private Runnable flipperRunnable = new Runnable() {
        @Override
        public void run() {
            nextPosition();

            if (isShowingImg1) {
                img2.setImageDrawable(drawables.get(position));
                img2.startAnimation(animationSet2);
                img2.setVisibility(VISIBLE);
                isShowingImg1 = false;
            } else {
                img1.setImageDrawable(drawables.get(position));
                img1.startAnimation(animationSet1);
                img1.setVisibility(VISIBLE);
                isShowingImg1 = true;
            }

            handler.postDelayed(this, flippingInterval);
        }
    };

    // helpers

    private void nextPosition() {
        if (position < drawables.size() - 1) {
            position++;
        } else {
            position = 0;
        }
    }

    private void initAnimSets() {
        animationSet1 = newAnimSet();
        animationSet2 = newAnimSet();
    }

    private AnimationSet newAnimSet() {
        AnimationSet animationSet = new AnimationSet(true);
        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setDuration(animDuration);
        fadeIn.setFillAfter(true);
        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setDuration(animDuration);
        fadeOut.setStartOffset(flippingInterval);
        fadeOut.setFillAfter(true);
        Animation flip = new TranslateAnimation(0, (float) -(height*aspectRatio-getScreenWidth()), 0, 0);
        flip.setDuration(flippingInterval+animDuration);
        flip.setFillAfter(true);
        animationSet.setInterpolator(new AccelerateInterpolator());
        animationSet.addAnimation(fadeIn);
        animationSet.addAnimation(flip);
        animationSet.addAnimation(fadeOut);
        animationSet.setFillAfter(true);
        return animationSet;
    }

    private int getScreenWidth() {
        Display display = ((Activity) getContext()).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }
}