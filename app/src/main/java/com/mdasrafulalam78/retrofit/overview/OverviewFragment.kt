package com.mdasrafulalam78.retrofit.overview

import android.app.Application
import android.content.ContentValues.TAG
import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.mdasrafulalam78.retrofit.R
import com.mdasrafulalam78.retrofit.adapter.PhotoGridAdapter
import com.mdasrafulalam78.retrofit.databinding.FragmentOverviewBinding

class OverviewFragment : Fragment() {
    private val viewModel: OverviewViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentOverviewBinding.inflate(inflater)
        binding.lifecycleOwner = this
        checkNetwork()
        binding.viewModel = viewModel
        binding.statusImage.setOnClickListener {
            checkNetwork()
            binding.viewModel = viewModel
        }
        binding.photosGrid.adapter = PhotoGridAdapter()
//        division()
        return binding.root
    }
    private fun verifyAvailableNetwork(context: Context):Boolean{
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo=connectivityManager.activeNetworkInfo
        return  networkInfo!=null && networkInfo.isConnected
    }

    fun division() {
        val numerator = 60
        var denominator = 4
        repeat(5) {
            Log.v("TAG", "${numerator / denominator}")
            denominator--
        }
    }

    private fun checkNetwork(){
        viewModel.isOnline.value = verifyAvailableNetwork(requireContext())
        viewModel.isOnline.observe(viewLifecycleOwner){
            if (it){
                viewModel.getImages()
            }else{
                Toast.makeText(requireContext(), "NO INTERNET! Check your connection!", Toast.LENGTH_SHORT).show()
            }
        }
    }

}