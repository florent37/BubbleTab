package com.github.florent37.bubbletab;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class BubbleTab extends FrameLayout {

    int numberOfIcons = 0;

    ViewGroup iconsLayout;
    ImageView circle;

    Setting setting;
    List<ImageView> icons;

    @Nullable ViewPager viewPager;
    int circleWidth;
    int viewPagerWidth;
    int tabWidth;
    int barHeight;
    float toCenterCircleX;
    float toCenterCircleY;
    ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {

        float oldPositionOffset;
        boolean toRight;

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            Log.d("percent", "" + positionOffset);

            if (oldPositionOffset == 0) {
                toRight = positionOffset > oldPositionOffset;
            }

            float x = position * tabWidth + tabWidth * positionOffset;
            circle.setTranslationX(x - toCenterCircleX);
            circle.setTranslationY(-toCenterCircleY);

            float distanceFromMiddle = Math.abs(positionOffset - 0.5f);
            float min = 0f;
            float scale = min + (1 - min) * (distanceFromMiddle + 0.5f);

            circle.setScaleX(scale);
            circle.setScaleY(scale);

            if (positionOffset != 0) {
                if (toRight) {
                    if (positionOffset < 0.5f) {
                        if (setting.firstIconDifferent && position == 0) {
                            icons.get(position).setImageResource(setting.images.get(0));
                        } else {
                            icons.get(position).setColorFilter(setting.selectedColor);
                        }
                        if (position + 1 < numberOfIcons) {
                            icons.get(position + 1).setColorFilter(setting.unselectedColor);
                        }
                    } else {
                        if (setting.firstIconDifferent && position == 0) {
                            icons.get(position).setImageResource(setting.image0Colored);
                        } else {
                            icons.get(position).setColorFilter(setting.unselectedColor);
                        }
                        if (position + 1 < numberOfIcons) {
                            icons.get(position + 1).setColorFilter(setting.selectedColor);
                        }
                    }
                } else {
                    if (positionOffset < 0.5f) {
                        icons.get(position).setColorFilter(setting.selectedColor);
                        if (position - 1 > 0) {
                            if (setting.firstIconDifferent && position - 1 == 0) {
                                icons.get(position - 1).setImageResource(setting.image0Colored);
                            } else {
                                icons.get(position - 1).setColorFilter(setting.unselectedColor);
                            }
                        }
                    } else {
                        icons.get(position).setColorFilter(setting.unselectedColor);
                        if (position - 1 > 0) {
                            if (setting.firstIconDifferent && position - 1 == 0) {
                                icons.get(position - 1).setImageResource(setting.images.get(0));
                            } else {
                                icons.get(position - 1).setColorFilter(setting.selectedColor);
                            }
                        }
                    }
                }
            }

            oldPositionOffset = positionOffset;
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
        this.viewPager = viewPager;

        getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                if (numberOfIcons != 0) {
                    viewPagerWidth = viewPager.getWidth();
                    tabWidth = viewPagerWidth / numberOfIcons;

                    ViewGroup.LayoutParams circleLayoutParams = circle.getLayoutParams();
                    circleWidth = (int) (tabWidth * setting.circleRatio);
                    circleLayoutParams.width = circleWidth;
                    circleLayoutParams.height = circleWidth;
                    circle.setLayoutParams(circleLayoutParams);
                    barHeight = getHeight();

                    toCenterCircleX = (circleWidth - tabWidth) / 2f;
                    toCenterCircleY = (circleWidth - barHeight) / 2f;

                    circle.setTranslationX(-toCenterCircleX);
                    circle.setTranslationY(-toCenterCircleY);
                }
                getViewTreeObserver().removeOnPreDrawListener(this);
                return false;
            }
        });

        viewPager.addOnPageChangeListener(pageChangeListener);
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
        inflate(getContext(), R.layout.bubbletab_layout, this);

        iconsLayout = (ViewGroup) findViewById(R.id.iconsLayout);
        circle = (ImageView) findViewById(R.id.circle);

        circle.setColorFilter(setting.circleColor);
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());

        numberOfIcons = setting.images.size();
        icons = new ArrayList<>(numberOfIcons);
        for (int i = 0; i < numberOfIcons; ++i) {
            ImageView icon = (ImageView) layoutInflater.inflate(R.layout.bubbletab_icon, iconsLayout, false);
            icons.add(icon);
            iconsLayout.addView(icon);

            final int position = i;
            int imageRes = setting.images.get(position);
            icon.setVisibility(View.VISIBLE);
            icon.setImageResource(imageRes);
            if (position > 0) {
                icon.setColorFilter(setting.unselectedColor);
            } else if (!setting.firstIconDifferent) {
                icon.setColorFilter(setting.selectedColor);
            }
            icon.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (viewPager != null) {
                        viewPager.setCurrentItem(position, true);
                    }
                }
            });
        }

        int padding = (int) dpToPx(8);
        icons.get(0).setPadding(padding, padding, padding, padding);
    }

    protected float dpToPx(int dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    private void init(Context context, AttributeSet attrs) {
        setting = new Setting(context, attrs);
    }

    static class Setting {
        @ColorInt int selectedColor;
        @ColorInt int unselectedColor;
        @ColorInt int circleColor;
        float circleRatio;
        boolean firstIconDifferent;
        List<Integer> images;
        @DrawableRes int image0Colored;

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
