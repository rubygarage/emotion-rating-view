# EmotionRatingView

## Inspiration

EmotionRatingView was inspired by a UI/UX Demo by[Adip Nayak](https://dribbble.com/shots/3828382-Feedback)

## Description
`EmotionRatingView` library contains `EmotionView`, `RatingView`, and `GradientBackgroundView`.

`EmotionView` - Displays an animated face that responds to a rating change.\
`RatingView` - Displays rating bar with animated grades.\
`GradientBackgroundView` - Displays a smoothly changing background with a gradient that responds to the rating change.

## Library Usage

Add **JitPack** repository to your `build.gradle` file

``` gradle
allprojects {
	repositories {
	     ...
	     maven { url 'https://jitpack.io' }
	}
}
```

Add the Dependency 

``` gradle
dependencies {
    implementation ‘com.github.rubygarage:emotion-rating-view:v1.0.0’
}
```

Add `EmotionRatingView` library to your layout. You can also use all views separately.

``` xml
    <android.support.constraint.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:app="http://schemas.android.com/apk/res-auto"
        android:layout_width="match_parent"
        android:layout_height="match_parent">
        
        <com.dm.emotionrating.library.GradientBackgroundView
                android:id="@+id/gradientBackgroundView"
                android:layout_width="0dp"
                android:layout_height="0dp"
                app:layout_constraintBottom_toBottomOf="parent"
                app:layout_constraintEnd_toEndOf="parent"
                app:layout_constraintStart_toStartOf="parent"
                app:layout_constraintTop_toTopOf="parent" />
                
        <com.dm.emotionrating.library.EmotionView
                android:id="@+id/emotionView"
                android:layout_width="0dp"
                android:layout_height="wrap_content"
                android:layout_marginBottom="8dp"
                android:layout_marginEnd="48dp"
                android:layout_marginLeft="48dp"
                android:layout_marginRight="48dp"
                android:layout_marginStart="48dp"
                android:layout_marginTop="8dp"
                app:layout_constraintBottom_toBottomOf="parent"
                app:layout_constraintEnd_toEndOf="parent"
                app:layout_constraintStart_toStartOf="parent"
                app:layout_constraintTop_toTopOf="parent" />
                
        <com.dm.emotionrating.library.RatingView
                android:id="@+id/ratingView"
                android:layout_width="wrap_content"
                android:layout_height="40dp"
                android:layout_marginEnd="8dp"
                android:layout_marginLeft="8dp"
                android:layout_marginRight="8dp"
                android:layout_marginStart="8dp"
                android:layout_marginTop="16dp"
                android:padding="5dp"
                app:gap="5dp"
                app:layout_constraintEnd_toEndOf="parent"
                app:layout_constraintStart_toStartOf="parent"
                app:layout_constraintTop_toBottomOf="@+id/emotionView" />
                
    </android.support.constraint.ConstraintLayout>
```

In your activity.

``` kotlin
ratingView.setRatingChangeListener { previousRating, newRating ->
            emotionView.setRating(previousRating, newRating)
            gradientBackgroundView.changeBackground(previousRating, newRating)
        }
```

Set `RatingChangeListener` to get notified when rating changed.

``` kotlin
ratingView.setRatingChangeListener { previousRating, newRating ->
            ...
        }
```

Set rating for all views from 0 to 5. The default rating is 0.

``` kotlin
ratingView.setRating(rating)
 
emotionView.setRating(previousRating, newRating)
 
gradientBackgroundView.changeBackground(previousRating, newRating)
```
You can also see the example to get better understanding.

## Customisation

`RatingView`

| Property | Description |
|:---:|:---:|
|android:layout_height| Changes the height of grades|
|android:background| Changes the background of the RatingView|
|app:gap| Changes the distance between grades|

To change the color of the face and grades, you can override the attributes in your theme.

``` xml
<style name="YourCustomThemeTheme">
    ...
    <item name="faceColor">@color/colorWhite</item>
    <item name="gradeColor">@color/orange</item>
</style>
```

To change the gradient color set, you can override the attributes in the `GradientBackgroundView` or in your theme.

``` xml
<style name="YourCustomThemeTheme">
    ...
    <item name="zeroGradeGradientColors">@array/garageZeroGradeGradientColors</item>
    <item name="oneGradeGradientColors">@array/garageOneGradeGradientColors</item>
    <item name="twoGradeGradientColors">@array/garageTwoGradeGradientColors</item>
    <item name="threeGradeGradientColors">@array/garageThreeGradeGradientColors</item>
    <item name="fourGradeGradientColors">@array/garageFourGradeGradientColors</item>
    <item name="fiveGradeGradientColors">@array/garageFiveGradeGradientColors</item>
</style>
```

Also, you have to define the arrays of colors that you want to use. **There must be at least two colors in one set.**

``` xml
<integer-array name="garageZeroGradeGradientColors">
   <item>@color/thirdGradientColor</item>
   <item>@color/thirdGradientColor</item>
</integer-array>
    
<integer-array name="garageOneGradeGradientColors">
   <item>@color/fifthGradientColor</item>
   <item>@color/fifthGradientColor</item>
</integer-array>
    
<integer-array name="garageTwoGradeGradientColors">
   <item>@color/fifthGradientColor</item>
   <item>@color/fourthGradientColor</item>
   <item>@color/thirdGradientColor</item>
   <item>@color/secondGradientColor</item>
</integer-array>
    
<integer-array name="garageThreeGradeGradientColors">
    <item>@color/fourthGradientColor</item>
    <item>@color/thirdGradientColor</item>
    <item>@color/secondGradientColor</item>
    <item>@color/firstGradientColor</item>
</integer-array>
    
<integer-array name="garageFourGradeGradientColors">
    <item>@color/thirdGradientColor</item>
    <item>@color/firstGradientColor</item>
</integer-array>
    
<integer-array name="garageFiveGradeGradientColors">
    <item>@color/secondGradientColor</item>
    <item>@color/firstGradientColor</item>
    <item>@color/firstGradientColor</item>
</integer-array>