package com.example.todoapp.presentation.functions

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.TypedValue
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat

fun Context.getColorFromAttr(attr: Int): Int {
    val typedValue = TypedValue()
    theme.resolveAttribute(attr, typedValue, true)
    return ContextCompat.getColor(this, typedValue.resourceId)
}
fun Context.getDrawableWithColor(drawableResId: Int, attrColorResId: Int): Drawable? {
    val drawable = ContextCompat.getDrawable(this, drawableResId) ?: return null
    val wrappedDrawable = DrawableCompat.wrap(drawable).mutate()
    val color = getColorFromAttr(attrColorResId)
    DrawableCompat.setTint(wrappedDrawable, color)
    return wrappedDrawable
}