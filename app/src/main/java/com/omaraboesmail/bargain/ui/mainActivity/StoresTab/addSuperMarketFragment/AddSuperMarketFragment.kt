package com.omaraboesmail.bargain.ui.mainActivity.StoresTab.addSuperMarketFragment

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.omaraboesmail.bargain.R
import com.omaraboesmail.bargain.pojo.SuperMarket
import com.omaraboesmail.bargain.resultStats.DbCRUDState
import com.omaraboesmail.bargain.utils.Const.TAG
import com.omaraboesmail.bargain.utils.DialogMaker
import com.omaraboesmail.bargain.utils.ToastMaker
import com.omaraboesmail.bargain.utils.getText
import kotlinx.android.synthetic.main.add_super_market_fragment.*


class AddSuperMarketFragment : Fragment() {

    companion object {
        fun newInstance() = AddSuperMarketFragment()
        private const val PERMISSION_CODE = 0
        private const val IMAGE_CAPTURE_CODE = 1
        private const val REQUEST_SELECT_IMAGE_IN_ALBUM = 1992
        var image_uri: Uri? = null
    }

    var dialog = DialogMaker.uploadPhotoProgressDialog()

    private val viewModel: AddSuperMarketViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_super_market_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        addPhoto.setOnClickListener {
            selectImageInAlbum()
        }

        liveImage.setOnClickListener {
            //if system os is Marshmallow or Above, we need to request runtime permission
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_DENIED ||
                    checkSelfPermission(
                        requireContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    )
                    == PackageManager.PERMISSION_DENIED
                ) {
                    //permission was not enabled
                    val permission = arrayOf(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    )
                    //show popup to request permission
                    requestPermissions(permission, PERMISSION_CODE)
                } else {
                    //permission already granted
                    openCamera()
                }
            } else {
                //system os is < marshmallow
                openCamera()
            }
        }

        addBtn.setOnClickListener {
            val superMarket = getSuperMarketData()
            Log.d(TAG, superMarket.toString())
            if (superMarket != null && image_uri != null) {
                viewModel.addMarket(image_uri!!, superMarket)
                viewModel.insertstate().observe(viewLifecycleOwner, Observer {
                    if (it == DbCRUDState.INSERTED) uploadphoto()
                    else ToastMaker(requireContext(), it.msg)
                })

            } else ToastMaker(requireContext(), "Make sure you entered all fields")
        }


    }

    private fun uploadphoto() {
        viewModel.uploadStatus().observe(viewLifecycleOwner, Observer {
            Log.d(TAG, it.msg)
            DialogMaker.loadingstate.value = it.msg

        })
        viewModel.uploadProgress().observe(viewLifecycleOwner, Observer {
            DialogMaker.progress.value = it


        })
        dialog.show()
    }

    fun selectImageInAlbum() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        if (intent.resolveActivity(requireActivity().packageManager) != null) {
            startActivityForResult(intent, REQUEST_SELECT_IMAGE_IN_ALBUM)
        }
    }

    private fun openCamera() {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "New Picture")
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera")
        image_uri = requireContext().contentResolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            values
        )
        //camera intent
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri)
        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE)
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        //called when user presses ALLOW or DENY from Permission Request Popup
        when (requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED
                ) {
                    //permission from popup was granted
                    openCamera()
                } else {
                    //permission from popup was denied
                    ToastMaker(requireContext(), "Permission denied")
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        //called when image was captured from camera intent
        if (resultCode == Activity.RESULT_OK) {
            //set image captured to image view
            try {
                image_uri = data!!.data
                liveImage.setImageURI(image_uri)
            } catch (e: Exception) {
                liveImage.setImageURI(image_uri)
            }

        } else ToastMaker(requireContext(), "Permission denied")

    }

    private fun getSuperMarketData(): SuperMarket? {
        if ((name.getText() != null)
            and (address.getText() != null)
            and (phone.getText() != null)
        )
            return SuperMarket(
                id = name.getText()!!,
                name = name.getText()!!,
                address = address.getText()!!,
                phone = phone.getText()!!,
                discount = 0f,
                photo = ""
            )
        else return null
    }


}
