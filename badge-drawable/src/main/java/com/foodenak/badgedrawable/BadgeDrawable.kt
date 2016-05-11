package com.foodenak.badgedrawable

import android.graphics.*
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.util.Log

/**
 * Created by ITP on 5/11/16.
 */
class BadgeDrawable(private val textSize: Float, textColor: Int, backgroundColor: Int,
    private val textPadding: Float) : Drawable() {

  private var badge = "";
  private var willDraw = false

  private val backgroundPaint = Paint()
  private val textPaint = Paint()

  init {
    backgroundPaint.color = backgroundColor
    backgroundPaint.isAntiAlias = true
    backgroundPaint.style = Paint.Style.FILL

    textPaint.color = textColor
    textPaint.textSize = textSize
    textPaint.isAntiAlias = true
    textPaint.typeface = Typeface.DEFAULT_BOLD
    textPaint.textAlign = Paint.Align.CENTER
  }

  fun setText(text: Int?) {
    val badge = if (text == null) "" else text.toString()
    if (this.badge.equals(badge)) return
    Log.d(TAG, "badge changed from: ${this.badge} to: $badge")
    this.badge = badge
    willDraw = text != null && text > 0;
    invalidateSelf()
  }

  fun setText(text: String?) {
    val badge = text ?: ""
    if (this.badge.equals(badge)) return
    Log.d(TAG, "badge changed from: ${this.badge} to: $badge")
    this.badge = badge
    willDraw = !TextUtils.isEmpty(this.badge);
    invalidateSelf()
  }

  override fun draw(canvas: Canvas?) {
    Log.d(TAG, "draw, willDraw = " + willDraw)
    if (!willDraw || canvas == null) return

    val bounds = bounds

    val textRect = Rect()
    textPaint.getTextBounds(badge, 0, badge.length, textRect)
    val textHeight = (textRect.bottom - textRect.top).toFloat()
    val textWidth = (textRect.right - textRect.left).toFloat()

    val doublePadding = (textPadding * 2).toFloat()

    val backGroundWidth = textWidth + doublePadding
    val backGroundHeight = textHeight + doublePadding

    val bottom = bounds.top + backGroundHeight
    val left = bounds.right - backGroundWidth

    val round = backGroundHeight / 2F

    canvas.drawRoundRect(left, bounds.top.toFloat(),
        bounds.right.toFloat(), bottom, round, round, backgroundPaint)
    canvas.drawText(badge, left + (backGroundWidth / 2F), bounds.top + (bottom - textPadding),
        textPaint)
  }

  override fun setAlpha(alpha: Int) {
    backgroundPaint.alpha = alpha
    invalidateSelf()
  }

  override fun getOpacity(): Int {
    return PixelFormat.UNKNOWN
  }

  override fun setColorFilter(colorFilter: ColorFilter?) {
    backgroundPaint.colorFilter = colorFilter
  }

  companion object {
    const val TAG = "BadgeDrawable";
  }
}