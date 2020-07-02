# BubbleTab

Put some bubble in your tabs and give your apps a supa fresh style !

<a href="https://goo.gl/WXW8Dc">
  <img alt="Android app on Google Play" src="https://developer.android.com/images/brand/en_app_rgb_wo_45.png" />
</a>



[![screen](https://raw.githubusercontent.com/florent37/BubbleTab/master/media/withScreen_cropped.png)](https://github.com/florent37/BubbleTab)

# Usage

[![screen](https://raw.githubusercontent.com/florent37/BubbleTab/master/media/video.gif)](https://github.com/florent37/BubbleTab)

Add a BubbleTab with your icons on the layout.xml

Customisable parameters :
- circleColor
- circleRatio

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
        >

                <ImageView
                    android:layout_width="match_parent"
                    android:layout_height="match_parent"
                    android:layout_weight="1"
                    android:padding="16dp"
                    android:src="@drawable/bubbletab_ic_hourglass_selector" />

                <ImageView
                    android:layout_width="match_parent"
                    android:layout_height="match_parent"
                    android:layout_weight="1"
                    android:padding="16dp"
                    android:src="@drawable/bubbletab_ic_event_selector" />

                <ImageView
                    android:layout_width="match_parent"
                    android:layout_height="match_parent"
                    android:layout_weight="1"
                    android:padding="16dp"
                    android:src="@drawable/bubbletab_ic_query_selector" />

                <ImageView
                    android:layout_width="match_parent"
                    android:layout_height="match_parent"
                    android:layout_weight="1"
                    android:padding="16dp"
                    android:src="@drawable/bubbletab_ic_search_selector" />

                <ImageView
                    android:layout_width="match_parent"
                    android:layout_height="match_parent"
                    android:layout_weight="1"
                    android:padding="16dp"
                    android:src="@drawable/bubbletab_ic_home_selector" />

</com.github.florent37.bubbletab.BubbleTab>

<android.support.v4.view.ViewPager
    android:id="@+id/viewPager"
    android:layout_width="match_parent"
    android:layout_height="match_parent"/>
```

Then bound it with your viewPager

```java
bubbleTab.setupWithViewPager(viewPager);
```

# Customisation

To display your home icon with a different color / content if selected / unselected

Simply use selectors !

[![screen](https://raw.githubusercontent.com/florent37/BubbleTab/master/media/different_icon.gif)](https://github.com/florent37/BubbleTab)

```xml
<?xml version="1.0" encoding="utf-8"?>
<selector xmlns:android="http://schemas.android.com/apk/res/android">
    <item android:state_selected="true" android:drawable="@drawable/bubbletab_ic_account_selected"/>
    <item android:drawable="@drawable/bubbletab_ic_account"/>
</selector>
```

# Download

In your module [![Download](https://api.bintray.com/packages/florent37/maven/BubbleTab/images/download.svg)](https://bintray.com/florent37/maven/BubbleTab/_latestVersion)
```groovy
compile 'com.github.florent37:bubbletab:1.0.2'
```

# Credits

Author: Florent Champigny 

Fiches Plateau Moto : [https://www.fiches-plateau-moto.fr/](https://www.fiches-plateau-moto.fr/)

<a href="https://goo.gl/WXW8Dc">
  <img alt="Android app on Google Play" src="https://developer.android.com/images/brand/en_app_rgb_wo_45.png" />
</a>


<a href="https://plus.google.com/+florentchampigny">
  <img alt="Follow me on Google+"
       src="https://raw.githubusercontent.com/florent37/DaVinci/master/mobile/src/main/res/drawable-hdpi/gplus.png" />
</a>
<a href="https://twitter.com/florent_champ">
  <img alt="Follow me on Twitter"
       src="https://raw.githubusercontent.com/florent37/DaVinci/master/mobile/src/main/res/drawable-hdpi/twitter.png" />
</a>
<a href="https://www.linkedin.com/in/florentchampigny">
  <img alt="Follow me on LinkedIn"
       src="https://raw.githubusercontent.com/florent37/DaVinci/master/mobile/src/main/res/drawable-hdpi/linkedin.png" />
</a>


License
--------

    Copyright 2016 florent37, Inc.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
