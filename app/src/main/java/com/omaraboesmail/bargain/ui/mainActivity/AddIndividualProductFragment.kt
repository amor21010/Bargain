package com.omaraboesmail.bargain.ui.mainActivity

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.omaraboesmail.bargain.R
import com.omaraboesmail.bargain.data.UserRepo
import com.omaraboesmail.bargain.pojo.IndividualProduct
import com.omaraboesmail.bargain.utils.Const.TAG
import com.omaraboesmail.bargain.utils.getText
import com.omaraboesmail.bargain.utils.isValidInput
import com.omaraboesmail.bargain.utils.validateInput
import kotlinx.android.synthetic.main.add_individual_product_fragment.*

class AddIndividualProductFragment : Fragment() {

    companion object {
        fun newInstance() = AddIndividualProductFragment()
    }

    private lateinit var viewModel: AddIndividualProductViewModel
    lateinit var arrayList: Array<String>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_individual_product_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AddIndividualProductViewModel::class.java)
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
        spinner.setSelection(0)
        addBtn.setOnClickListener {
            if (validateInput()) {
                Log.d(TAG, getData().type)

            }

        }

    }

    private fun getData(): IndividualProduct {
        return IndividualProduct(
            name = productName.getText()!!,
            price = price.getText()!!,
            type = getType(),
            unit = unit.getText()!!,
            seller = UserRepo.currant.value?.name!!,
            quantityAvailable = 1
        )

    }

    private fun getType(): String {
        return spinner.selectedItem.toString()
    }

    private fun validateInput(): Boolean {
        return (productName.validateInput() and price.validateInput() and
                unit.validateInput() and getType().isValidInput())
    }
}