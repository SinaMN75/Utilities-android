package com.developersian.view.media

import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.widget.MediaController
import androidx.appcompat.app.AppCompatActivity
import com.developersian.R
import com.satya.utilites.extentions.gone
import com.satya.utilites.extentions.visible
import kotlinx.android.synthetic.main.activity_video_view.*

class VideoViewActivity : AppCompatActivity() {
	
	private var playbackPosition = 0
	private val videoUrl = "https://sample-videos.com/video123/mp4/720/big_buck_bunny_720p_30mb.mp4"
	private lateinit var mediaController: MediaController
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_video_view)
		
		mediaController = MediaController(this)
		
		videoViewVideoViewActivity.setOnPreparedListener {
			mediaController.setAnchorView(frameLayoutVideoViewActivity)
			videoViewVideoViewActivity.setMediaController(mediaController)
			videoViewVideoViewActivity.seekTo(playbackPosition)
			videoViewVideoViewActivity.start()
		}
		
		videoViewVideoViewActivity.setOnInfoListener { _, what, _ ->
			if (what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START) progressBarVideoViewActivity.gone()
			true
		}
	}
	
	override fun onStart() {
		super.onStart()
		val uri = Uri.parse(videoUrl)
		videoViewVideoViewActivity.setVideoURI(uri)
		progressBarVideoViewActivity.visible()
	}
	
	override fun onPause() {
		super.onPause()
		videoViewVideoViewActivity.pause()
		playbackPosition = videoViewVideoViewActivity.currentPosition
	}
	
	override fun onStop() {
		videoViewVideoViewActivity.stopPlayback()
		super.onStop()
	}
}