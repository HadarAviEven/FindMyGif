package com.hadar.findmygif.ui.fragments

import android.Manifest
import android.app.DownloadManager
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.hadar.findmygif.R
import java.io.File
import java.lang.Exception

class FullScreenGifFragment : Fragment() {

    var fullScreenGifImageView: ImageView? = null
    private var saveToGalleryButton: Button? = null
    private var gifUrl: String? = null
    private var gifTitle: String? = null

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                saveToGallery()
            }
        }

    companion object {
        const val BUNDLE_GIF_URL = "gif_url"
        const val BUNDLE_GIF_TITLE = "gif_title"
        const val DIR_NAME = "Gif Downloads"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_full_screen_gif, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fullScreenGifImageView = view.findViewById(R.id.full_screen_gif_image_view)
        saveToGalleryButton = view.findViewById(R.id.save_to_gallery_button)

        gifUrl = arguments?.getString(BUNDLE_GIF_URL)
        gifTitle = arguments?.getString(BUNDLE_GIF_TITLE)

        setFullScreenGifImageView(gifUrl)

        saveToGalleryButton?.setOnClickListener {

            requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
    }

    private fun setFullScreenGifImageView(urlGif: String?) {
        if (urlGif != null) {
            Glide.with(fullScreenGifImageView!!.context)
                .load(urlGif)
                .placeholder(R.drawable.place_holder)
                .into(fullScreenGifImageView!!)
        }
    }

    private fun saveToGallery() {
        if (internetAccess()) {
            downloadImage()
        } else {
            toast("No Internet Access")
        }
    }

    private fun internetAccess(): Boolean {
        val connectivityManager =
            activity?.getSystemService(AppCompatActivity.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)!!.state == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)!!.state == NetworkInfo.State.CONNECTED
    }

    private fun downloadImage() {
        val fullGifName = gifTitle
        val fixedGifName = fullGifName?.split("GIF")?.first()
        val filename = "$fixedGifName.jpg"

        val downloadUrlOfImage = gifUrl
        val downloadUri: Uri = Uri.parse(downloadUrlOfImage)
        val request = DownloadManager.Request(downloadUri)
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
            .setAllowedOverRoaming(false)
            .setTitle(filename)
            .setMimeType("image/jpg")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationInExternalPublicDir(
                Environment.DIRECTORY_DCIM,
                File.separator + DIR_NAME + File.separator.toString() + filename
            )

        val manager =
            activity?.getSystemService(AppCompatActivity.DOWNLOAD_SERVICE) as DownloadManager
        try {
            manager.enqueue(request)
            disableButton()

        } catch (e: Exception) {
            toast("No Permission")
        }
    }

    private fun disableButton() {
        saveToGalleryButton?.setTextColor(Color.parseColor("#7f7f7f"))
        saveToGalleryButton?.isEnabled = false
    }

    private fun toast(message: CharSequence) =
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
}