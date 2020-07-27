package com.omaraboesmail.bargain.ui.editProductFragment

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.ContentValues
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
import com.omaraboesmail.bargain.databinding.EditProductFragmentBinding
import com.omaraboesmail.bargain.pojo.IndividualProduct
import com.omaraboesmail.bargain.pojo.getPhotoUri
import com.omaraboesmail.bargain.utils.*
import kotlinx.android.synthetic.main.add_individual_product_fragment.*

class EditProductFragment : Fragment() {
    private var _binding: EditProductFragmentBinding? = null
    private val binding get() = _binding!!
    lateinit var arrayList: Array<String>
    private var imageUri: Uri? = null
    private val PERMISSION_CODE = 5
    private val IMAGE_CAPTURE_CODE = 2
    private val REQUEST_SELECT_IMAGE_IN_ALBUM = 1952

    companion object {
        fun newInstance() =
            EditProductFragment()
    }

    val viewModel: EditProductViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = EditProductFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var product = viewModel.getProductClicked()
        if (product != null) {
            product.getPhotoUri().observe(viewLifecycleOwner, Observer {
                Glide.with(requireContext()).load(it).into(binding.productImage)

            })
            binding.price.editText?.setText(product.price)
            binding.productName.editText?.setText(product.name)
            binding.unit.editText?.setText(product.unit)
            arrayList = requireContext().resources.getStringArray(R.array.individual_types)
            ArrayAdapter.createFromResource(
                requireContext(),
                R.array.individual_types,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                // Apply the adapter to the spinner
                binding.spinner.adapter = adapter
            }
            binding.spinner.setSelection(setSelected(product.type))
            binding.productImage.setOnClickListener {
                selectImage()
            }
            binding.addImageText.setOnClickListener {
                selectImage()
            }
            DialogMaker.mContext = requireActivity() as AppCompatActivity
            val dialog = DialogMaker.uploadPhotoProgressDialog()
            binding.updateBtn.setOnClickListener {
                product = getData()
                if (validateInput() && product != null) {
                    if (imageUri != null) {
                        viewModel.updateProduct(product!!)
                        viewModel.uploadImage(imageUri!!, product!!)
                        dialog.show()
                        viewModel.imageStat.observe(viewLifecycleOwner, Observer {
                            if (it > 0) {
                                DialogMaker.progress.value = it
                            }
                        })

                    }
                }
            }

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

    private fun getType(): String {
        return when (spinner.selectedItemPosition) {
            0 -> "handMade"
            1 -> "homeStore"
            2 -> "homeService"
            else -> spinner.selectedItem.toString()
        }
    }

    private fun setSelected(string: String): Int {
        return when (string) {
            "handMade" -> 0
            "homeStore" -> 1
            "homeService" -> 2
            else -> arrayList.lastIndexOf(string)
        }
    }

    private fun selectImageInAlbum() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        if (intent.resolveActivity(requireActivity().packageManager) != null) {
            startActivityForResult(intent, REQUEST_SELECT_IMAGE_IN_ALBUM)
        }
    }

    private fun getData(): IndividualProduct? {
        return UserRepo.currant.value?.name?.let { userName ->
            IndividualProduct(
                name = productName.getText()!!,
                price = price.getText()!!,
                type = getType(),
                unit = unit.getText()!!,
                seller = userName,
                quantityAvailable = 1
            )
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
                Glide.with(requireContext()).load(imageUri).into(binding.productImage)
            } catch (e: Exception) {
                Glide.with(requireContext()).load(imageUri).into(binding.productImage)

            }

        } else ToastMaker(requireContext(), "Permission denied")

    }

    private fun selectImage() {
        val options =
            arrayOf<CharSequence>("Take Photo", "Choose from Gallery", "Cancel")
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Choose your profile picture")
        builder.setItems(options) { dialog, item ->
            if (options[item] == "Take Photo") {
                getPermissions()
            } else if (options[item] == "Choose from Gallery") {
                selectImageInAlbum()
            } else if (options[item] == "Cancel") {
                dialog.dismiss()
            }
        }
        builder.show()
    }

}