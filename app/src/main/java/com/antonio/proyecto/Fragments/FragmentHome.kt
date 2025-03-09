package com.antonio.proyecto.Fragments

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.VideoView
import com.antonio.proyecto.R
import com.antonio.proyecto.databinding.FragmentHomeBinding
import com.bumptech.glide.Glide

class FragmentHome : Fragment() {

    lateinit var bindingFragment: FragmentHomeBinding

    //Creamos la vista usando binding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindingFragment = FragmentHomeBinding.inflate(inflater, container, false)
        return bindingFragment.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Colocamos el video en el VideoView del layout del menu home
        val videoView = view.findViewById<VideoView>(R.id.videoView)
        videoView?.let {
            val videoPath = "android.resource://" + requireContext().packageName + "/" + R.raw.video
            it.setVideoURI(Uri.parse(videoPath))

            it.setOnPreparedListener { mediaPlayer ->

                //Silenciar el volumen del vídeo para que solo se escuche el de sonido
                mediaPlayer.setVolume(0f, 0f)
            }

            it.start()
        } ?: run {
            Log.e("MainMenuFragment", "Error: videoView es null")
        }

        //Cargar la imagen usando Glide
        val imageView = view.findViewById<ImageView>(R.id.imageView)

        // Es el mismo logo que el drawable, al poner un link tambien funciona pero no encuentro la foto en png
        val imageUrl = R.drawable.sanji

        Glide.with(this).load(imageUrl).into(imageView)
    }



}