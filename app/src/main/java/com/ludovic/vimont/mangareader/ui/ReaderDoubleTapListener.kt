package com.ludovic.vimont.mangareader.ui

import android.view.GestureDetector
import android.view.MotionEvent
import com.github.chrisbanes.photoview.PhotoView

class ReaderDoubleTapListener(
    private val photoView: PhotoView
): GestureDetector.SimpleOnGestureListener() {
    override fun onDoubleTap(motionEvent: MotionEvent): Boolean {
        if (photoView.scale > 1f) {
            photoView.setScale(1f, motionEvent.x, motionEvent.y, true)
        } else {
            photoView.setScale(2f, motionEvent.x, motionEvent.y, true)
        }
        return true
    }
}