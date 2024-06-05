package com.akshat.customcomponents.widgets

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.VisibleForTesting
import androidx.core.content.ContextCompat
import com.akshat.customcomponents.R
import com.akshat.customcomponents.util.string

class Alert @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr){

    enum class AlertType(val type: Int){
        ALERT_SUCCESS(0),
        ALERT_INFO(1),
        ALERT_WARNING(2),
        ALERT_ERROR (3),
    }

    private val textView : TextView
    private val iconViewCenter : ImageView
    private val iconViewTop : ImageView
    private var iconText : String? = null
    private var textViewColor : ColorStateList? = null
    private var textViewLinkColor: ColorStateList? = null

    @VisibleForTesting
    var alertType = AlertType.ALERT_SUCCESS
        private set

    init {
        View.inflate(getContext(), R.layout.alert_message_view, this)

        textView = findViewById(R.id.text)
        iconViewCenter = findViewById(R.id.icon_center)
        iconViewTop = findViewById(R.id.icon_top)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.Alert, defStyleAttr, 0)

        setStyle(typedArray)
        updatePadding()

    }

    /**
     * Sets the style and text message to the AlertView
     */
    private fun setStyle(typedArray: TypedArray? = null, alertType: AlertType? = null) {
        try {
            if (alertType == null) {
                this.alertType = AlertType.values().first {
                    it.type == typedArray?.getInt(R.styleable.Alert_alertType, AlertType.ALERT_SUCCESS.type)
                }
            }
            background = typedArray?.getDrawable(R.styleable.Alert_android_background)
            if (textView.text.isNullOrEmpty()) {
                textView.text = typedArray?.getString(R.styleable.Alert_alertText)
            }
            if (typedArray?.hasValue(R.styleable.Alert_alertTextColor) == true) {
                textViewColor = typedArray.getColorStateList(R.styleable.Alert_alertTextColor)
                textView.setTextColor(textViewColor)
            }

            if (typedArray?.hasValue(R.styleable.Alert_alertTextColorLink) == true) {
                textViewLinkColor = typedArray.getColorStateList(R.styleable.Alert_alertTextColorLink)
                textView.setLinkTextColor(textViewLinkColor)
            }
        } finally {
            typedArray?.recycle()
        }
        when (this.alertType) {
            AlertType.ALERT_SUCCESS -> {
                iconText = resources.string(R.string.alert_success_icon_description)
                setResources(
                    R.drawable.iconCheckCircle,
                    ContextCompat.getColor(context, R.color.green_130),
                    ContextCompat.getDrawable(context, R.drawable.alert_background_success)
                )
            }
            AlertType.ALERT_ERROR -> {
                iconText = resources.string(R.string.alert_error_icon_description)
                setResources(
                    R.drawable.iconExclamation,
                    ContextCompat.getColor(context, R.color.red_130),
                    ContextCompat.getDrawable(context, R.drawable.alert_background_error),
                    ContextCompat.getColor(context, R.color.banner_container_variant_error_background_color)
                )
            }
            AlertType.ALERT_INFO -> {
                iconText = resources.string(R.string.alert_info_icon_description)
                setResources(
                    R.drawable.iconInformation,
                    ContextCompat.getColor(context, R.color.black),
                    ContextCompat.getDrawable(context, R.drawable.alert_background_info)
                )
            }
            AlertType.ALERT_WARNING -> {
                iconText = resources.string(R.string.alert_warning_icon_description)
                setResources(
                    0,
                    ContextCompat.getColor(context, R.color.yellow_160),
                    null,
                    ContextCompat.getColor(context, R.color.banner_container_variant_warning_background_color)
                )
            }
        }
    }

    /**
     * Sets the icon color, text color etc to the views.
     */
    private fun setResources(iconResId: Int, color: Int, backgroundDrawable: Drawable?, linkColor: Int? = null) {
        updateIcons(iconResId, color)
        if (background == null) {
            background = backgroundDrawable
        }
        if (textViewColor == null) {
            textView.setTextColor(color)
        }
        if (textViewLinkColor == null) {
            linkColor?.let { textView.setLinkTextColor(it) }
        }
    }

    private fun updateIcons(iconResId: Int, color: Int) {
        iconViewTop.apply {
            setImageResource(iconResId)
            setColorFilter(color)
            visibility = when (textView.lineCount) {
                1 -> View.INVISIBLE
                else -> View.VISIBLE
            }
        }
        iconViewCenter.apply {
            iconViewCenter.apply {
                setImageResource(iconResId)
                setColorFilter(color)
            }
            visibility = when {
                textView.lineCount != 1 -> View.INVISIBLE
                else -> View.VISIBLE
            }
        }
    }

    fun setAlertType(alertType: AlertType) {
        this.alertType = alertType
        setStyle(alertType = this.alertType)
        updatePadding()
    }

    /**
     * Gets the text of the message view
     */
    fun getText() = textView.text

    /**
     * Sets the text of the message view
     */
    fun setText(text: CharSequence?) {
        textView.text = text

        if (text is Spanned) {
            if (text.getSpans(0, text.length, ClickableSpan::class.java).isNotEmpty()) {
                // We have some clickable spans to activate
                if (!textView.isClickable) {
                    textView.isClickable = true
                }
                if (textView.movementMethod == null) {
                    textView.movementMethod = LinkMovementMethod.getInstance()
                }
            }
        }
    }

    /**
     * Gets the TextView instance used to display the text of the message view. This may be useful when working
     * with more elaborate content like linkified text and direct access may be required for setup/customization.
     */
    fun getTextView() = textView

    private fun updatePadding() {
        val padding = resources.getDimensionPixelSize(R.dimen.global_message_padding)
        val widePadding = resources.getDimensionPixelSize(R.dimen.global_message_wide_padding)
        setPadding(widePadding, padding, widePadding, padding)
    }
}
