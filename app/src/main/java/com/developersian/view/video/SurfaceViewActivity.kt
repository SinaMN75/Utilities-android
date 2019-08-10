package com.developersian.view.video

import android.graphics.Point
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.view.Surface
import android.view.SurfaceHolder
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.developersian.R
import com.satya.utilites.extentions.gone
import com.satya.utilites.extentions.visible
import kotlinx.android.synthetic.main.activity_surface_view.*

class SurfaceViewActivity : AppCompatActivity(), SurfaceHolder.Callback {
	override fun surfaceChanged(p0: SurfaceHolder?, p1: Int, p2: Int, p3: Int) {}
	override fun surfaceDestroyed(p0: SurfaceHolder?) {}
	
	override fun surfaceCreated(p: SurfaceHolder) {
		val surface = p.surface
		setupMediaPlayer(surface)
		prepareMediaPlayer()
	}
	
	private lateinit var mediaPlayer: MediaPlayer
	private var playbackPosition = 0
	private val realtimeUrl = "https://sample-videos.com/video123/mp4/720/big_buck_bunny_720p_30mb.mp4"
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_surface_view)
		
		val holder = surfaceViewSurfaceViewActivity.holder
		holder.addCallback(this)
		
	}
	
	override fun onPause() {
		super.onPause()
		
		playbackPosition = mediaPlayer.currentPosition
	}
	
	override fun onStop() {
		mediaPlayer.stop()
		mediaPlayer.release()
		super.onStop()
	}
	
	private fun createAudioAttribute(): AudioAttributes {
		val builder = AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_MEDIA).setContentType(AudioAttributes.CONTENT_TYPE_MOVIE)
		return builder.build()
	}
	
	private fun setupMediaPlayer(surface: Surface) {
		progressBarSurfaceViewActivity.visible()
		mediaPlayer = MediaPlayer()
		mediaPlayer.setSurface(surface)
		val audioAttributes = createAudioAttribute()
		mediaPlayer.setAudioAttributes(audioAttributes)
		val uri = Uri.parse(realtimeUrl)
		try {
			mediaPlayer.setDataSource(this, uri)
		} catch (e: Exception) {
			e.printStackTrace()
		}
	}
	
	private fun setSurfaceDimension(player: MediaPlayer, width: Int, height: Int) {
		if (width > 0 && height > 0) {
			val asspectRatio = height.toFloat() / width.toFloat()
			val screenDimension = Point()
			windowManager.defaultDisplay.getSize(screenDimension)
			val surfaceWidth = screenDimension.x
			val surfaceHeight = (surfaceWidth * asspectRatio).toInt()
			
			val layoutParams = FrameLayout.LayoutParams(surfaceWidth, surfaceHeight)
			surfaceViewSurfaceViewActivity.layoutParams = layoutParams
			val holder = surfaceViewSurfaceViewActivity.holder
			player.setDisplay(holder)
		}
	}
	
	private fun prepareMediaPlayer() {
		try {
			mediaPlayer.prepareAsync()
		} catch (e: Exception) {
			e.printStackTrace()
		}
		
		mediaPlayer.setOnPreparedListener {
			progressBarSurfaceViewActivity.gone()
			mediaPlayer.seekTo(playbackPosition)
			mediaPlayer.start()
		}
		
		mediaPlayer.setOnVideoSizeChangedListener { mediaPlayer, width, height ->
			setSurfaceDimension(mediaPlayer, width, height)
		}
		
		
	}
}