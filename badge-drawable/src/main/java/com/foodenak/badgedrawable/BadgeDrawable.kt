package com.foodenak.badgedrawable

import android.graphics.*
import android.graphics.drawable.Drawable

/**
 * Created by ITP on 5/11/16.
 */
class BadgeDrawable(private val textSize: Float, textColor: Int, backgroundColor: Int) : Drawable() {

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
    textPaint.textAlign = Paint.Align.CENTER
  }

  fun setText(text: Int?) {
    val badge = text.toString()
    if (this.badge.equals(badge)) return
    this.badge = badge
    willDraw = text != null && text > 0;
    invalidateSelf()
  }

  override fun draw(canvas: Canvas?) {
    if (!willDraw || canvas == null) return

    val bounds = bounds
    val width = (bounds.right - bounds.left).toFloat()
    val height = (bounds.top - bounds.bottom).toFloat()
    val radius = ((Math.min(width, height) / 2) - 1) / 2
    val centerX = width - radius - 1
    val centerY = radius + 1
    canvas.drawCircle(centerX, centerY, radius, backgroundPaint)

    val textRect = Rect()
    textPaint.getTextBounds(badge, 0, badge.length, textRect)
    val textHeight = (textRect.top - textRect.bottom).toFloat()
    val textY = (centerY + (textHeight / 2F))
    canvas.drawText(badge, centerX, textY, textPaint)
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
}