package com.foodenak.badgedrawable

import android.graphics.*
import android.graphics.drawable.Drawable
import android.text.TextUtils

/**
 * Created by ITP on 5/11/16.
 */
class BadgeDrawable(private val textSize: Float, textColor: Int, backgroundColor: Int,
    private val textPadding: Float, private val minSize: Float, private val elevation: Float = 0F) : Drawable() {

  private var badge = "";
  private var willDraw = false

  private val backgroundPaint = Paint()
  private val textPaint = Paint()
  private val elevationPaint: Paint?

  init {
    elevationPaint = if (elevation > 0F) {
      val paint = Paint()
      paint.color = Color.BLACK
      paint.alpha = 85
      paint.style = Paint.Style.FILL
      paint
    } else {
      null
    }
    backgroundPaint.color = backgroundColor
    backgroundPaint.isAntiAlias = true
    backgroundPaint.style = Paint.Style.FILL

    textPaint.color = textColor
    textPaint.textSize = textSize
    textPaint.isAntiAlias = true
    textPaint.textAlign = Paint.Align.CENTER
  }

  fun setText(text: Int?) {
    val badge = if (text == null) "" else text.toString()
    if (this.badge.equals(badge)) return
    this.badge = badge
    willDraw = text != null && text > 0;
    invalidateSelf()
  }

  fun setText(text: String?) {
    val badge = text ?: ""
    if (this.badge.equals(badge)) return
    this.badge = badge
    willDraw = !TextUtils.isEmpty(this.badge);
    invalidateSelf()
  }

  override fun draw(canvas: Canvas?) {
    if (!willDraw || canvas == null) return

    val bounds = bounds

    val textRect = Rect()
    textPaint.getTextBounds(badge, 0, badge.length, textRect)
    val textHeight = (textRect.bottom - textRect.top).toFloat()
    val textWidth = (textRect.right - textRect.left).toFloat()

    val doublePadding = (textPadding * 2).toFloat()

    val backGroundWidth = Math.max(textWidth + doublePadding, minSize)
    val backGroundHeight = Math.max(textHeight + doublePadding, minSize)

    val bottom = bounds.top + backGroundHeight
    val left = bounds.right - backGroundWidth
    val top = bounds.top.toFloat()
    val right = bounds.right.toFloat()

    val round = backGroundHeight / 2F

    if (elevationPaint != null) {
      canvas.drawRoundRect(left, top + elevation, right, bottom + elevation, round, round,
          elevationPaint)
    }

    canvas.drawRoundRect(left, top, right, bottom, round, round, backgroundPaint)

    val textX = left + (backGroundWidth / 2)
    val textY = top + ((textHeight + backGroundHeight) / 2F)

    canvas.drawText(badge, textX, textY, textPaint)
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