package com.omaraboesmail.bargain.ui.mainActivity.IndividualsTab.AddIndividualProductFragment

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.ContentValues
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.omaraboesmail.bargain.R
import com.omaraboesmail.bargain.data.UserRepo
import com.omaraboesmail.bargain.pojo.IndividualProduct
import com.omaraboesmail.bargain.utils.*
import kotlinx.android.synthetic.main.add_individual_product_fragment.*

class AddIndividualProductFragment : Fragment() {
    private var imageUri: Uri? = null
    private val PERMISSION_CODE = 0
    private val IMAGE_CAPTURE_CODE = 1
    private val REQUEST_SELECT_IMAGE_IN_ALBUM = 1992

    companion object {
        fun newInstance() =
            AddIndividualProductFragment()


    }

    private val viewModel: AddIndividualProductViewModel by viewModels()
    lateinit var arrayList: Array<String>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_individual_product_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        arrayList = requireContext().resources.getStringArray(R.array.individual_types)
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.individual_types,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }
        DialogMaker.mContext = requireActivity() as AppCompatActivity
        val dialog = DialogMaker.uploadPhotoProgressDialog(R.id.homeServiceFragment)
        spinner.setSelection(0)
        addBtn.setOnClickListener {
            val product = getData()
            if (validateInput() && product != null) {
                if (imageUri != null) {
                    viewModel.addIndividualProduct(product)
                    viewModel.uploadPhoto(imageUri!!, product)
                    dialog.show()
                    viewModel.imageStat.observe(viewLifecycleOwner, Observer {
                        if (it > 0) {
                            DialogMaker.progress.value = it
                        }
                    })
                } else selectImage()
            }

        }

        productImage.setOnClickListener {
            selectImage()
        }
        addImageText.setOnClickListener {
            selectImage()
        }


    }

    private fun selectImageInAlbum() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        if (intent.resolveActivity(requireActivity().packageManager) != null) {
            startActivityForResult(intent, REQUEST_SELECT_IMAGE_IN_ALBUM)
        }
    }

    private fun getPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED ||
                ContextCompat.checkSelfPermission(
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

    private fun getData(): IndividualProduct? {
        return UserRepo.currant.value?.name?.let { userNaem ->
            IndividualProduct(
                name = productName.getText()!!,
                price = price.getText()!!,
                type = getType(),
                unit = unit.getText()!!,
                seller = userNaem,
                quantityAvailable = 1
            )
        }

    }

    private fun getType(): String {
        return when (spinner.selectedItemPosition) {
            0 -> "handMade"
            1 -> "homeStore"
            2 -> "homeService"
            else -> spinner.selectedItem.toString()
        }

    }

    private fun validateInput(): Boolean {
        return (productName.validateInput() and price.validateInput() and
                unit.validateInput() and getType().isValidInput())
    }

    private fun openCamera() {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "New Picture")
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera")
        imageUri = requireContext().contentResolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            values
        )
        //camera intent
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
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
                imageUri = data!!.data
                Glide.with(requireContext()).load(imageUri).into(productImage)
            } catch (e: Exception) {
                Glide.with(requireContext()).load(imageUri).into(productImage)

            }

        } else ToastMaker(requireContext(), "Permission denied")

    }

    private fun selectImage() {
        val options =
            arrayOf<CharSequence>("Take Photo", "Choose from Gallery", "Cancel")
        val builder: AlertDialog.Builder = AlertDialog.Builder(DialogMaker.mContext)
        builder.setTitle("Choose your profile picture")
        builder.setItems(options, DialogInterface.OnClickListener { dialog, item ->
            if (options[item] == "Take Photo") {
                getPermissions()
            } else if (options[item] == "Choose from Gallery") {
                selectImageInAlbum()
            } else if (options[item] == "Cancel") {
                dialog.dismiss()
            }
        })
        builder.show()
    }
}