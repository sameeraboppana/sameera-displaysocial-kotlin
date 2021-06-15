package com.sameera.displaysocial.fragment

import android.app.Dialog
import android.graphics.BitmapFactory
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.sameera.displaysocial.activity.R
import com.sameera.displaysocial.utils.FileUtils
import com.sameera.displaysocial.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.user_entry_dialog.*
import java.io.File


/**
 * A placeholder fragment containing a simple view.
 */
class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    lateinit var tvWish: TextView;
    lateinit var imgView: ImageView;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java).apply {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        tvWish = view.tvWish
        imgView = view.imgView
        if (!homeViewModel.isUserNameSaved(requireContext())) {
            showDialog()
        } else {
            displayGreeting()
        }
        displayImage()
        return view
    }

    private fun displayImage() {
        val imgFile = FileUtils.getImagePath(requireContext())
        if (imgFile.exists()) {
            val myBitmap = BitmapFactory.decodeFile(imgFile.absolutePath)
            imgView.setImageBitmap(myBitmap)
        }
    }

    private fun displayGreeting() {
        homeViewModel.getGreetingMessage(requireContext())
        tvWish.text = homeViewModel.getGreetingMessage(requireContext())
    }

    private fun showDialog() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.user_entry_dialog)
        val edUserName = dialog.edUserName

        dialog.btnOk.setOnClickListener {
            if (!TextUtils.isEmpty(edUserName.text.toString())) {
                dialog.dismiss()
                homeViewModel.saveUserName(requireContext(), edUserName.text.toString())
                displayGreeting()
            } else {
                Toast.makeText(
                    requireContext(),
                    "Please enter the name to proceed!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        dialog.show()
    }

    companion object {

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(sectionNumber: Int): HomeFragment {
            return HomeFragment().apply {
            }
        }
    }
}