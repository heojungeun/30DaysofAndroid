package com.example.transparenticonsplash

import android.R
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewManager
import android.view.animation.OvershootInterpolator
import com.example.transparenticonsplash.R.*


class SplashView : View {
    private final val TAG = "SplashView"

    interface ISplashListener {
        fun onStart()
        fun onUpdate(completionFraction: Float)
        fun onEnd()
    }

    constructor(context: Context?):super(context) {
        initialize()
    }

    constructor(context: Context?, attrs: AttributeSet?):super(context, attrs) {
        initialize()
        setupAttributes(attrs)
    }

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ):super(context, attrs, defStyleAttr){
        initialize()
        setupAttributes(attrs)
    }

    val DEFAULT_HOLE_FILL_COLOR: Int = android.graphics.Color.WHITE
    val DEFAULT_ICON_COLOR: Int = android.graphics.Color.rgb(23, 169, 229)
    val DEFAULT_DURATION = 500
    val DEFAULT_REMOVE_FROM_PARENT_ON_END = true

    private val PAINT_STROKE_WIDTH = 2 // give a stroke width to the paint so that the rectangles get a little overlap

    // most important item, cannot be null
    private var mIcon: Drawable? = null
    private var mHoleFillColor = DEFAULT_HOLE_FILL_COLOR // color to be shown in the transparent hole before the animation starts

    private var mIconColor = DEFAULT_ICON_COLOR // should be the same color of as the icon background

    // total duration, in ms, of the animation
    private var mDuration = DEFAULT_DURATION.toLong()
    private var mRemoveFromParentOnEnd = true // a flag for removing the view from its parent once the animation is over

    private var mCurrentScale = 1f // used for keeping track of how far along the animation we are

    // cache some dimension values to make the onDraw method simpler looking
    private var mWidth = 0  // cache some dimension values to make the onDraw method simpler looking
    private var mHeight = 0
    private var mIconWidth = 0
    private var mIconHeight:Int = 0
    private var mMaxScale = 1f

    // cache the paint object so that it doesn't need to be allocated in onDraw
    private val mPaint: Paint = Paint()

    /**
     * Setup custom attributes from XML
     * @param attrs
     *
     * com.example.transparenticonsplash.R.styleable
     */
    private fun setupAttributes(attrs: AttributeSet?) {
        val context = context
        val a = context.obtainStyledAttributes(attrs, styleable.TwitterSplashView)
        val numAttrs = a.indexCount
        for (i in 0 until numAttrs) {
            val attr = a.getIndex(i)
            when (attr) {
                styleable.TwitterSplashView_icon -> setIconDrawable(a.getDrawable(i))
                styleable.TwitterSplashView_iconColor -> setIconColor(
                    a.getColor(
                        i,
                        DEFAULT_ICON_COLOR
                    )
                )
                styleable.TwitterSplashView_holeFillColor -> setHoleFillColor(
                    a.getColor(
                        i,
                        DEFAULT_HOLE_FILL_COLOR
                    )
                )
                styleable.TwitterSplashView_duration -> setDuration(
                    a.getInt(i, DEFAULT_DURATION).toLong()
                )
                styleable.TwitterSplashView_removeFromParentOnEnd -> setRemoveFromParentOnEnd(
                    a.getBoolean(
                        i,
                        DEFAULT_REMOVE_FROM_PARENT_ON_END
                    )
                )
            }
        }
        a.recycle()
    }

    /**
     * Initialized the view properties. No much is done in this method since most variables already have set defaults
     */
    private fun initialize() {
        // make the background transparent so that the view does not automatically draw any unwanted colors
        setBackgroundColor(Color.TRANSPARENT)

        // set fill style on the paint so that the rectangles get filled
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE)
        mPaint.setStrokeWidth(PAINT_STROKE_WIDTH.toFloat())
    }

    /**
     * Set the fill color of the view that will be seen through the transparent hole of the icon before the animation starts
     */
    fun setHoleFillColor(bgColor: Int) {
        mHoleFillColor = bgColor
    }

    /**
     * Set the color of the icon. This value will be used to draw 4 rectangles around the icon to fill the entire view.
     * If not set, or set incorrectly the edges of the icon image will be visible. There are a few tricks to make this animation
     * look right, and this is one of them. Make sure this color is set correctly.
     * @param iconColor
     */
    fun setIconColor(iconColor: Int) {
        mIconColor = iconColor
    }

    /**
     * Set the duration of the entire animation in milliseconds.
     * @param duration
     */
    fun setDuration(duration: Long) {
        require(duration >= 0) { "duration cannot be less than 0" }
        mDuration = duration
    }

    /**
     * Set the resource id of the Drawable to be used as the icon. See setIconDrawable(Drawable) for more details.
     * @param resId
     */
    fun setIconResource(resId: Int) {
        val icon = resources.getDrawable(resId)
            ?: throw IllegalArgumentException("no drawable found for the resId: $resId")
        setIconDrawable(icon)
    }

    /**
     * Set the Drawable to be used as the icon. It can be any kind of Drawable that has an intrinsic width and height.
     * So far changing the size of the Drawable is not supported but can be added in the future
     * @param icon
     */
    fun setIconDrawable(icon: Drawable?) {
        mIcon = icon
        if (mIcon != null) {
            mIconWidth = mIcon!!.intrinsicWidth
            mIconHeight = mIcon!!.intrinsicHeight
            // set the bounds of the drawable to its own dimensions
            // canvas scaling will be used to change the bounds of the icon
            val iconBounds = Rect()
            iconBounds.left = 0
            iconBounds.top = 0
            iconBounds.right = mIconWidth
            iconBounds.bottom = mIconHeight
            mIcon!!.bounds = iconBounds
        } else {
            mIconWidth = 0
            mIconHeight = 0
        }
        setMaxScale()
    }

    /**
     * Set the flag to remove or keep the view after the animation is over. This is set to true by default. The view must be inside a ViewManager
     * (or ViewParent) for this to work. Otherwise, the view will not be removed and a warning log will be produced.
     * @param shouldRemove
     */
    fun setRemoveFromParentOnEnd(shouldRemove: Boolean) {
        mRemoveFromParentOnEnd = shouldRemove
    }

    /**
     * A helper method for determining for large the icon should be enlarged before the animation ends. There is a chance that the entire view will not become
     * transparent by the end of the animation.
     */
    private fun setMaxScale() {
        if (mIconWidth < 1 || mIconHeight < 1) {
            mMaxScale = 1f
            return
        }
        mMaxScale = 2 * Math.max(
            mWidth.toFloat() / mIconWidth,
            mHeight.toFloat() / mIconHeight
        )

        // just to make sure the animation does not actually work backwards
        if (mMaxScale < 1) {
            mMaxScale = 1f
        }
    }

    /**
     * Starts the splash and disappear animation. If a listener is provided it will notify the listener on animation events
     * @param listener
     */
    fun splashAndDisappear(listener: ISplashListener?) {
        // create an animator from scale 1 to max
        val animator = ValueAnimator.ofFloat(1f, mMaxScale)
        // set the duration
        animator.duration = mDuration
        // set an overshoot interpolator with a low tension value so that the icon becomes a little smaller before it expands
        animator.interpolator = OvershootInterpolator(1f)

        // add an update listener so that we draw the view on each update
        animator.addUpdateListener { animation -> // keep in mind that the animation runs in reverse to get the desired effect from the interpolator
            // therefore we need to subtract to correct for this effect, and then add 1 so that the scale doesn't dip below 0
            // this is NOT fool-proof, and therefore can be made better
            mCurrentScale = 1 + mMaxScale - animation.animatedValue as Float

            // invalidate the view so that it gets redraw if it needs to be
            invalidate()

            // notify the listener if set
            // for some reason this animation can run beyond 100%
            listener?.onUpdate(animation.currentPlayTime.toFloat() / mDuration)
        }

        // add a listener for the general animation events, use the AnimatorListenerAdapter so that we don't clutter the code
        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator) {
                // notify the listener of animation start (if listener is set)
                listener?.onStart()
            }

            override fun onAnimationEnd(animation: Animator) {
                // check if we need to remove the view on animation end
                if (mRemoveFromParentOnEnd) {
                    // get the view parent
                    val parent = parent
                    // check if a parent exists and that it implements the ViewManager interface
                    if (parent != null && parent is ViewManager) {
                        val viewManager = parent as ViewManager
                        // remove the view from its parent
                        viewManager.removeView(this@SplashView)
                    }
                }

                // notify the listener of animation end (if listener is set)
                listener?.onEnd()
            }
        })

        // start the animation using post so that the animation does not start if the view is not in foreground
        post { // start the animation in reverse to get the desired effect from the interpolator
            animator.reverse()
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        // do whatever the super wants to do
        super.onSizeChanged(w, h, oldw, oldh)

        // cache the width and height for easy access
        mWidth = w
        mHeight = h

        // re-set the max scale because the size has changed
        setMaxScale()
    }

    override fun onDraw(canvas: Canvas) {
        // calculate the scaled width and height
        val iconWidth = mIconWidth * mCurrentScale
        val iconHeight: Float = mIconHeight * mCurrentScale

        // calculate all corners of the icon rectangle with the icon in the middle
        val mIconLeft = (mWidth - iconWidth) / 2
        val mIconRight = mIconLeft + iconWidth
        val mIconTop = (mHeight - iconHeight) / 2
        val mIconBottom = mIconTop + iconHeight

        // if the scale is less than 2, then don't enable the transparent hole yet
        if (mCurrentScale < 2) {
            // draw a bgColored rectangle right underneath the icon, make the rectangle a little bigger using the threshold value
            mPaint.setColor(mHoleFillColor)
            canvas.drawRect(mIconLeft, mIconTop, mIconRight, mIconBottom, mPaint)
        }

        // draw 4 rectangles around the icon to cover the entire screen, use threshold value to expand and overlap the rectangles
        mPaint.setColor(mIconColor)
        canvas.drawRect(0F, 0F, mIconLeft, mHeight.toFloat(), mPaint)
        canvas.drawRect(mIconLeft, 0F, mIconRight, mIconTop, mPaint)
        canvas.drawRect(mIconLeft, mIconBottom, mIconRight, mHeight.toFloat(), mPaint)
        canvas.drawRect(mIconRight, 0F, mWidth.toFloat(), mHeight.toFloat(), mPaint)
        if (mIcon != null) {
            // save the current canvas state
            canvas.save()
            // translate the canvas to draw the icon
            canvas.translate(mIconLeft, mIconTop)
            // scale the canvas for the desired icon scale
            canvas.scale(mCurrentScale, mCurrentScale)
            // draw the icon on the canvas
            mIcon!!.draw(canvas)
            // restore the canvas to its original state
            canvas.restore()
        }
    }
}