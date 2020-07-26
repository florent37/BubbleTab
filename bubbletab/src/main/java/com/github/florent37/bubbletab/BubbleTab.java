package com.github.florent37.bubbletab;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class BubbleTab extends LinearLayout {

    int numberOfIcons = 0;
    @Nullable
    ViewPager viewPager;

    private Circle circle = new Circle();
    private Setting setting;
    private List<View> icons;
    private final ChangeListener pageChangeListener = new ChangeListener();
    private final class ChangeListener implements ViewPager.OnPageChangeListener {
        float oldPositionOffset;
        boolean toRight;

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            //Log.d("onPageScrolled", position + " : " + positionOffset);

            if (oldPositionOffset == 0) {
                toRight = positionOffset > oldPositionOffset;
            }

            circle.layout(BubbleTab.this, position, positionOffset);

            if (positionOffset != 0) {
                if (toRight) {
                    if (positionOffset < 0.5f) {
                        icons.get(position).setSelected(true);
                        if (position + 1 < numberOfIcons) {
                            icons.get(position + 1).setSelected(false);
                        }
                    } else {
                        icons.get(position).setSelected(false);
                        if (position + 1 < numberOfIcons) {
                            icons.get(position + 1).setSelected(true);
                        }
                    }
                } else {
                    if (positionOffset < 0.5f) {
                        icons.get(position).setSelected(true);
                        if (position - 1 > 0) {
                            icons.get(position + 1).setSelected(false);
                        }
                    } else {
                        icons.get(position).setSelected(false);
                        if (position - 1 > 0) {
                            icons.get(position + 1).setSelected(true);
                        }
                    }
                }
            }

            oldPositionOffset = positionOffset;
            postInvalidate();
        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    public BubbleTab(Context context) {
        super(context);
        init(context, null);
    }

    public BubbleTab(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public BubbleTab(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public void setupWithViewPager(final ViewPager viewPager) {
        if(this.viewPager != null)
            this.viewPager.removeOnPageChangeListener(pageChangeListener);

        this.viewPager = viewPager;
        this.viewPager.addOnPageChangeListener(pageChangeListener);

        final int currentItem = viewPager.getCurrentItem();
        for (int i = 0; i < icons.size(); i++) {
            icons.get(i).setSelected(i == currentItem);
        }
        //no need to update here since we override "onLayout(...)"
        //  but still we want to support change of pager more then ones
        circle.layout(this, this.viewPager);
        postInvalidate();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (viewPager != null) {
            viewPager.addOnPageChangeListener(pageChangeListener);
            postInvalidate();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (viewPager != null) {
            viewPager.removeOnPageChangeListener(pageChangeListener);
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        circle.setColor(setting.circleColor);
        circle.setRatio(setting.circleRatio);

        numberOfIcons = getChildCount();
        icons = new ArrayList<>();
        for (int i = 0; i < numberOfIcons; i++) {
            final int index = i;
            final View childAt = getChildAt(index);
            icons.add(childAt);
            childAt.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (viewPager != null) {
                        viewPager.setCurrentItem(index, true);
                    }
                }
            });
        }
    }

    protected float dpToPx(int dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    private void init(Context context, AttributeSet attrs) {
        setWillNotDraw(false);
        setting = new Setting(context, attrs);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        //prepare shape even before any "onPageScrolled(...)" event
        circle.layout(this, viewPager);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        circle.onDraw(canvas);
        super.onDraw(canvas);
    }

    private static class Circle {
        public float lastOffset = 0f; //current shape-offset in percentage (from 0f to 1f)

        private Paint paint = new Paint();

        private float translationX = 1f;
        private int width;
        private float circleRatio = 1f;
        private float scale = 1f;

        public Circle() {
            paint.setColor(Color.BLACK);
            paint.setAntiAlias(true);
            paint.setStrokeWidth(1);
            paint.setStyle(Paint.Style.FILL_AND_STROKE);
        }

        public void onDraw(Canvas canvas) {
            canvas.save();
            canvas.translate(translationX, 0);
            final int height = canvas.getHeight();
            canvas.drawCircle(width / 2f, height / 2f, (width / 2f) * circleRatio * scale, paint);
            canvas.restore();
        }

        public void setTranslationX(float translationX) {
            this.translationX = translationX;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getColor() {
            return paint.getColor();
        }

        public void setColor(int color) {
            paint.setColor(color);
        }

        public void setRatio(float circleRatio) {
            this.circleRatio = circleRatio;
        }

        public void setScale(float scale) {
            this.scale = scale;
        }


        static int valueForFrame(int valueStart, int valueEnd, float frameStart, float frames, float currentFrame) {
            return (int)( (valueEnd-valueStart)/frames * (currentFrame-frameStart) + valueStart );
        }

        public void layout(BubbleTab owner, int position, float positionOffset) {
            int widthStart = 0; int widthEnd;
            View c = owner.getChildAt(position);
            if(c != null) {
                widthStart = c.getWidth();
                //animate to next tabs size and reverse
                View next = owner.getChildAt(position+1);
                if(next != null) {
                    widthEnd = valueForFrame(widthStart, next.getWidth(), 0f, 1f, positionOffset);
                } else {
                    widthEnd = widthStart;
                }
                this.setWidth(widthEnd);
            }

            float x = c.getX() + widthStart * positionOffset;
            this.setTranslationX(x);

            float distanceFromMiddle = Math.abs(positionOffset - 0.5f);
            float min = 0f;
            float scale = min + (1 - min) * (distanceFromMiddle + 0.5f);

            this.setScale(scale);
            lastOffset = positionOffset;
        }
        //prepare shape based on "ViewPager" position
        public void layout(BubbleTab owner, ViewPager viewPager) {
            //get current Page-index
            int position = viewPager != null ? viewPager.getCurrentItem() : 0;
            //convert Page-index to position (position is always less than current Page-index)
            if(position > 1)
                position -= 1;
            this.layout(owner, position, this.lastOffset);
        }
    }

    static class Setting {
        @ColorInt
        int selectedColor;
        @ColorInt
        int unselectedColor;
        @ColorInt
        int circleColor;
        float circleRatio;
        boolean firstIconDifferent;
        List<Integer> images;
        @DrawableRes
        int image0Colored;

        public Setting(Context context, AttributeSet attrs) {
            images = new ArrayList<>();
            final TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.BubbleTab);
            if (array != null) {
                selectedColor = array.getColor(R.styleable.BubbleTab_bubbleTab_selectedColor, Color.WHITE);
                unselectedColor = array.getColor(R.styleable.BubbleTab_bubbleTab_unselectedColor, Color.parseColor("#c0c0c0"));
                circleColor = array.getInt(R.styleable.BubbleTab_bubbleTab_circleColor, Color.BLACK);
                circleRatio = array.getFloat(R.styleable.BubbleTab_bubbleTab_circleRatio, 1.2f);
                image0Colored = array.getResourceId(R.styleable.BubbleTab_bubbleTab_image0Colored, 0);
                add(array, R.styleable.BubbleTab_bubbleTab_image0);
                add(array, R.styleable.BubbleTab_bubbleTab_image1);
                add(array, R.styleable.BubbleTab_bubbleTab_image2);
                add(array, R.styleable.BubbleTab_bubbleTab_image3);
                add(array, R.styleable.BubbleTab_bubbleTab_image4);
                add(array, R.styleable.BubbleTab_bubbleTab_image5);
                add(array, R.styleable.BubbleTab_bubbleTab_image6);
                add(array, R.styleable.BubbleTab_bubbleTab_image7);
                add(array, R.styleable.BubbleTab_bubbleTab_image8);
                add(array, R.styleable.BubbleTab_bubbleTab_image9);
                add(array, R.styleable.BubbleTab_bubbleTab_image10);
                array.recycle();

                firstIconDifferent = image0Colored != 0;
            }
        }

        private void add(TypedArray array, int res) {
            int value = array.getResourceId(res, 0);
            if (value != 0) {
                images.add(value);
            }
        }
    }
}
