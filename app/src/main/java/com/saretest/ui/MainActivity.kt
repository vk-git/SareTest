package com.saretest.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.saretest.R
import com.saretest.data.api.ApiService
import com.saretest.data.api.MainRepository
import com.saretest.data.model.UserData
import com.saretest.databinding.ActivityMainBinding
import com.saretest.ui.adapter.MediaAdapter
import com.saretest.utils.RetrofitFactory
import com.saretest.utils.Status

class MainActivity : AppCompatActivity() {

    companion object {
        const val API = "https://androidtask.herokuapp.com/"
    }

    private lateinit var binding: ActivityMainBinding
    private var viewModel: MainViewModel? = null
    private var mediaAdapter: MediaAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init() {
        val apiService = RetrofitFactory.getRetrofit(this, API)
            .create(ApiService::class.java)
        val mainRepository = MainRepository(apiService)
        viewModel = MainViewModel(mainRepository = mainRepository)

        mediaAdapter = MediaAdapter()
        with(binding.mediaRecyclerView) {
            layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
            setHasFixedSize(true)
            adapter = mediaAdapter
        }

        viewModel?.userdata?.observe(this, {
            when (it.status) {
                Status.SUCCESS -> {
                    setUIData(it.data)
                }
                Status.ERROR -> {
                    AlertDialog.Builder(this@MainActivity)
                        .setTitle(getString(R.string.error_title))
                        .setMessage(getString(R.string.error_message))
                        .setCancelable(false)
                        .setPositiveButton(
                            getString(R.string.dialog_ok)
                        ) { _, _ ->

                        }.show()
                }
            }
        })
    }

    @SuppressLint("SetTextI18n")
    private fun setUIData(data: UserData?) {
        data?.run {
            Glide.with(this@MainActivity)
                .load(coverImage)
                .into(binding.coverImg)

            Glide.with(this@MainActivity)
                .load(profileImage)
                .into(binding.profileImg)

            binding.txtName.text = fullName
            binding.txtUserName.text = "@$username"
            binding.txtProfileLogo.text = "$videoCount"
            binding.txtProfileLike.text = "$likeCount"
            binding.txtFollowing.text = "$numberFollowing ${getString(R.string.following)}"
            binding.txtFollowers.text = "$numberFollowers ${getString(R.string.followers)}"
            binding.txtDesc.text = description
            binding.btnFollow.visibility = if (follow) View.VISIBLE else View.GONE

            mediaAdapter?.setFragmentImages(fragmentImages = fragmentImages)
        }
    }
}