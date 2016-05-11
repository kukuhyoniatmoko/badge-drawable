@file:JvmName("BadgeDrawables")

package com.foodenak.badgedrawable

import android.content.Context
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.os.Build
import android.view.MenuItem

/**
 * Created by ITP on 5/11/16.
 */
fun createBadge(context: Context, icon: Drawable, badge: BadgeDrawable): LayerDrawable {
  val version = Build.VERSION.SDK_INT
  val drawable = if (version >= Build.VERSION_CODES.LOLLIPOP) {
    context.getDrawable(R.drawable.badge_drawable_icon).mutate() as LayerDrawable
  } else {
    @Suppress("DEPRECATION")
    context.resources.getDrawable(R.drawable.badge_drawable_icon).mutate() as LayerDrawable
  }
  drawable.setDrawableByLayerId(R.id.badge_drawable_icon, icon)
  drawable.paddingMode = LayerDrawable.PADDING_MODE_STACK
  drawable.setDrawableByLayerId(R.id.badge_drawable_badge, badge)
  return drawable
}

fun createBadge(context: Context, icon: Drawable, badge: BadgeDrawable,
    iconPadding: Int): LayerDrawable {
  val version = Build.VERSION.SDK_INT
  val drawable = if (version >= Build.VERSION_CODES.LOLLIPOP) {
    context.getDrawable(R.drawable.badge_drawable_icon).mutate() as LayerDrawable
  } else {
    @Suppress("DEPRECATION")
    context.resources.getDrawable(R.drawable.badge_drawable_icon).mutate() as LayerDrawable
  }
  drawable.setLayerInset(drawable.findIndexByLayerId(R.id.badge_drawable_icon), iconPadding,
      iconPadding, iconPadding, iconPadding)
  drawable.setDrawableByLayerId(R.id.badge_drawable_icon, icon)
  drawable.paddingMode = LayerDrawable.PADDING_MODE_STACK
  drawable.setDrawableByLayerId(R.id.badge_drawable_badge, badge)
  return drawable
}

fun LayerDrawable.setCount(count: Int?) {
  val badge = findDrawableByLayerId(R.id.badge_drawable_badge);
  if (badge is BadgeDrawable) {
    badge.setText(count)
  } else {
    throw IllegalStateException("create LayerDrawable with createBadge()")
  }
}

fun MenuItem.setBadgeCount(count: Int?) {
  val icon = if (icon is LayerDrawable) {
    icon as LayerDrawable
  } else {
    throw IllegalStateException("Icon should a LayerDrawable!")
  }
  icon.setCount(count)
}

fun LayerDrawable.setCount(count: String?) {
  val badge = findDrawableByLayerId(R.id.badge_drawable_badge);
  if (badge is BadgeDrawable) {
    badge.setText(count)
  } else {
    throw IllegalStateException("create LayerDrawable with createBadge()")
  }
}

fun MenuItem.setBadgeCount(count: String?) {
  val icon = if (icon is LayerDrawable) {
    icon as LayerDrawable
  } else {
    throw IllegalStateException("Icon should a LayerDrawable!")
  }
  icon.setCount(count)
}