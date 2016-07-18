# BubbleTab

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