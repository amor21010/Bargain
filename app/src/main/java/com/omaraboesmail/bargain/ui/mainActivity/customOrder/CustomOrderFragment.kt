package com.omaraboesmail.bargain.ui.mainActivity.customOrder

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.omaraboesmail.bargain.R
import com.omaraboesmail.bargain.utils.isValidInput

class CustomOrderFragment : Fragment() {
    lateinit var orderText: EditText

    companion object {
        fun newInstance() = CustomOrderFragment()
    }

    private val viewModel: CostumeOrderViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.custom_order_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        orderText = view.findViewById(R.id.orderText)
        val orderBtn: Button = view.findViewById(R.id.orderBtn)
        //saveState(savedInstanceState)


        orderBtn.setOnClickListener {
            if (orderText.text.isValidInput()) {
                makeOrder(orderText.text.toString())
                /*viewModel.orderState.observe(viewLifecycleOwner, Observer {
                    when (it) {
                        DbCRUDState.INSERTED -> TODO()  *//* NavigationFlow(requireContext()).navigateToFragment(
                            R.id.nav_home*//*

                        )
                    }
                })*/
            } else orderText.error = "this field must be filled"
        }
    }

    private fun makeOrder(text: String) {
        viewModel.makeOrder(text)
    }

    fun saveState(stat: Bundle?) {
        if (stat != null) {
            try {
                orderText.setText(stat.getString("orderText"))
            } catch (e: Exception) {
                orderText.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(it: Editable?) {
                        if (it != null)
                            stat.putString("orderText", it.toString())
                    }

                    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    }

                    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                        if (p0 != null)
                            stat.putString("orderText", p0.toString())
                    }

                })

            }

        }
    }


}
