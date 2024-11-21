package com.example.casino.imageViewScrolling

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import com.example.casino.R

class ImageViewScrolling : FrameLayout {

    private lateinit var eventEnd: IEventEnd
    private var lastResult = 0
    private var oldValue = 0

    companion object {
        private const val ANIMATION_DURATION = 150L
    }

    val value: Int
        get() = Integer.parseInt(nextImage.tag.toString())

    fun setEventEnd(eventEnd: IEventEnd) {
        this.eventEnd = eventEnd
    }

    private lateinit var currentImage: ImageView
    private lateinit var nextImage: ImageView

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context)
    }

    private fun init(context: Context) {
        LayoutInflater.from(context).inflate(R.layout.image_view_scrolling, this)
        currentImage = findViewById(R.id.currentImage)
        nextImage = findViewById(R.id.nextImage)
    }

    fun setValueRandom(image: Int, numRotate: Int) {
        currentImage.animate()
            .translationY((-height).toFloat())
            .setDuration(ANIMATION_DURATION)
            .withEndAction {
                currentImage.translationY = 0f
                nextImage.translationY = nextImage.height.toFloat()

                nextImage.animate().translationY(0f).setDuration(ANIMATION_DURATION)
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator) {
                            setImage(currentImage, oldValue % 6)
                            currentImage.translationY = 0f
                            if (oldValue != numRotate) {  // если еще есть вращение
                                setValueRandom(image, numRotate)
                                oldValue++
                            } else {
                                lastResult = 0
                                oldValue = 0
                                setImage(nextImage, image)
                                eventEnd.eventEnd(image % 6, numRotate)  // всего 6 изображений
                            }
                        }
                    }).start()
            }
    }

    private fun setImage(img: ImageView, value: Int) {
        val drawableRes = when (value) {
            Util.bar -> R.drawable.bar
            Util.lemon -> R.drawable.lemon
            Util.orange -> R.drawable.orange
            Util.seven -> R.drawable.seven
            Util.triple -> R.drawable.triple
            Util.watermelon -> R.drawable.watermelon
            else -> R.drawable.bar
        }
        img.setImageResource(drawableRes)
        img.tag = value
        lastResult = value
    }
}
