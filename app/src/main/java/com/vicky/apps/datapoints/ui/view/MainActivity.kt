package com.vicky.apps.datapoints.ui.view

import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View

import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.vicky.apps.datapoints.R
import com.vicky.apps.datapoints.base.BaseActivity
import com.vicky.apps.datapoints.common.ViewModelProviderFactory
import com.vicky.apps.datapoints.ui.adapter.DataAdapter

import com.vicky.apps.datapoints.ui.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

import javax.inject.Inject


class MainActivity : BaseActivity() {


    @Inject
    lateinit var factory: ViewModelProviderFactory

    var mediaPlayer: MediaPlayer? = null

    private lateinit var viewModel: MainViewModel
    private lateinit var recyclerView: RecyclerView

    private lateinit var adapter: DataAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializeValues()
        inilializingRecyclerView()
        viewModel.getDataFromRemote()
        playContainer.visibility = View.GONE

        actionIcon.setOnClickListener {
            actionClicked()
        }
    }

    private fun actionClicked() {
        if(actionIcon.tag == "Stopped"){
            startPlaying()
        }else{
            stopPlaying()
        }
    }

    private fun inilializingRecyclerView() {

        recyclerView = mainRecycler
        recyclerView.layoutManager = GridLayoutManager(this, 2)


        adapter = DataAdapter(viewModel.getData()){
            songClicked(it)
        }

        recyclerView.adapter = adapter
    }

    private fun songClicked(position: Int) {
        Picasso.get().load(viewModel.getData()[position].coverImage).into(coverPicImage)
        songNameText.text = viewModel.getData()[position].song
        artistsNameText.text = viewModel.getData()[position].artists
        playContainer.visibility = View.VISIBLE
        stopPlaying()
        playSong(viewModel.getData()[position].url)
    }

    private fun playSong(url: String) {
        mediaPlayer = MediaPlayer().apply {
            setAudioStreamType(AudioManager.STREAM_MUSIC)
            setDataSource(url)
            setOnPreparedListener { preparedListener() }
            prepareAsync()
            start()
        }
    }

    private fun preparedListener() {
        var toggle = false
        toggle = mediaPlayer?.isPlaying!!
        if(toggle){
            stopPlaying()
        }else{
            startPlaying()
        }
    }

    private fun initializeValues() {

        viewModel = ViewModelProviders.of(this, factory).get(MainViewModel::class.java)

        viewModel.setCompositeData(compositeDisposable)

        viewModel.getSubscription().observe(this, Observer {
            if (it) {
                successCallback()
            } else {
                failureCallback()
            }
        })
    }

    private fun successCallback() {
        updateData()
    }

    private fun updateData() {
        adapter.updateData(viewModel.getData())
    }


    private fun failureCallback() {
        Toast.makeText(this, "API failed", Toast.LENGTH_LONG).show()
    }


    private fun stopPlaying(){
        actionIcon.tag = "Stopped"
        actionIcon.setImageResource(R.drawable.ic_play_arrow_black_24dp)
        mediaPlayer?.pause()
    }

    private fun startPlaying(){
        actionIcon.tag = "Started"
        actionIcon.setImageResource(R.drawable.ic_pause_black_24dp)
        mediaPlayer?.start()
    }

}
