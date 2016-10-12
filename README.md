# BubbleTab

[![screen](https://raw.githubusercontent.com/florent37/BubbleTab/master/media/video.gif)](https://github.com/florent37/BubbleTab)

#Setup

Add a BubbleTab with your icons on the layout.xml

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
        app:bubbleTab_image0="@drawable/ic_home"
        app:bubbleTab_image1="@drawable/ic_account"
        app:bubbleTab_image2="@drawable/ic_event"
        app:bubbleTab_image3="@drawable/ic_search"
        app:bubbleTab_image4="@drawable/ic_query"
        />

    <android.support.v4.view.ViewPager
        android:id="@+id/viewPager"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>
```

Then bound it with your viewPager

```java
bubbleTab.setupWithViewPager(viewPager);
```