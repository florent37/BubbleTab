# ExpandingPager
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-ExpandingPager-green.svg?style=true)](https://android-arsenal.com/details/1/3747)

ExpandingPager is a card peek/pop controller

[![gif](img/preview.gif)]()

#Setup

```xml
<com.github.florent37.bubbletab.BubbleTab
        android:id="@+id/bubbleTab"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:clipToPadding="false"
        android:background="@android:color/white"
        android:elevation="10dp"

        app:bubbleTab_circleColor="@color/colorAccent"
        app:bubbleTab_circleRatio="1.25"
        app:bubbleTab_image0="@drawable/house"
        app:bubbleTab_image1="@drawable/male"
        app:bubbleTab_image2="@drawable/cocktail"
        app:bubbleTab_image3="@drawable/magnifying"
        app:bubbleTab_image4="@drawable/alarm"
        />
```

```java
bubbleTab.setupWithViewPager(viewPager);
```

##Adapter

Just extends `ExpandingViewPagerAdapter` in your Fragment Adapter, which returns `ExpandingFragment`

```java
public class CustomViewPagerAdapter extends ExpandingViewPagerAdapter {

    @Override
    public Fragment getItem(int position) {
        return CustomExpandingFragment.newInstance();
    }

}
```

Your `ExpandingFragment` must returns a FragmentTop and a FragmentBottom 

```java
public class CustomExpandingFragment extends ExpandingFragment {

    @Override
    public Fragment getFragmentFront() {
        return CustomFragmentTop.newInstance();
    }

    @Override
    public Fragment getFragmentBottom() {
        return CustomFragmentBottom.newInstance();
    }
}

```

##Fragments

###Top

Create your top fragment implementing `ExpandingFragment.ChildTop`

```java
public class CustomFragmentTop extends Fragment implements ExpandingFragment.ChildTop {
    
    @Nullable ExpandingFragment expandingFragment;
    
    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (expandingFragment != null) {
                    if (expandingFragment.isOpenend()) {
                        //start your activity 
                    } else {
                        expandingFragment.open();
                    }
                }
            }
        });
    }


    @Override
    public void onAttachedToExpanding(ExpandingFragment expandingFragment) {
        this.expandingFragment = expandingFragment;
    }

    @Override
    public void onDetachedToExpanding() {
        this.expandingFragment = null;
    }
}
```

##Bottom

Create your top fragment implementing `ExpandingFragment.ChildTop`

```java
public class CustomFragmentBottom extends Fragment implements ExpandingFragment.ChildBottom {
    
    @Nullable ExpandingFragment expandingFragment;

    @Override
    public void onAttachedToExpanding(ExpandingFragment expandingFragment) {
        this.expandingFragment = expandingFragment;
    }

    @Override
    public void onDetachedToExpanding() {
        this.expandingFragment = null;
    }
}
```

##BackPress


```java
@Override
public void onBackPressed() {
    if(!expandingViewPager.onBackPressed()){
        super.onBackPressed();
    }
}
```