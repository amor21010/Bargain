package com.omaraboesmail.bargain.ui.mainActivity.cartFragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.omaraboesmail.bargain.R
import com.omaraboesmail.bargain.data.UserRepo
import com.omaraboesmail.bargain.pojo.Cart
import com.omaraboesmail.bargain.pojo.DeliveryStat
import com.omaraboesmail.bargain.pojo.Order
import com.omaraboesmail.bargain.pojo.Product
import com.omaraboesmail.bargain.resultStats.DbCRUDState
import com.omaraboesmail.bargain.utils.Const.TAG
import com.omaraboesmail.bargain.utils.ToastMaker

class CartFragment : Fragment() {

    companion object {
        fun newInstance() =
            CartFragment()
    }


    private lateinit var totalPrice: TextView
    private lateinit var makeOrder: Button
    private var products: List<Product> = ArrayList()
    private val viewModel: CartViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.cart_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val cartAdapter = CartAdapter()
        val cartRecycler: RecyclerView = view.findViewById(R.id.cartRecycler)

        val itemCallBack: ItemTouchHelper.SimpleCallback =
            object :
                ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    TODO("Not yet implemented")
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    cartAdapter.removeItem(viewHolder.adapterPosition)
                    UserRepo.fbUserLive.value?.email?.let {
                        viewModel.getOnlineCart(it)
                    }
                    getTotalPrice()
                }
            }

        totalPrice = view.findViewById(R.id.totalPrice)
        makeOrder = view.findViewById(R.id.makeOrderBtn)

        cartRecycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        UserRepo.fbUserLive.value?.email?.let { email ->
            viewModel.getOnlineCart(email).observe(viewLifecycleOwner,
                Observer { cart ->
                    cartAdapter.swapData(cart.products)
                    products = cart.products
                    Log.d(TAG, products.toString())
                    getTotalPrice()
                    makeOrder.setOnClickListener {
                        if (!cart.products.isNullOrEmpty())
                            makeOrder(cart)
                        viewModel.insertOrderState.observe(viewLifecycleOwner, Observer {
                            when (it) {
                                DbCRUDState.LOADING -> ToastMaker(requireContext(), "Loading...")
                                DbCRUDState.INSERTED -> {
                                    ToastMaker(requireContext(), "your Order is on deliver")
                                    viewModel.updateCart(emptyList())
                                }
                                else -> {
                                    ToastMaker(
                                        requireContext(),
                                        "some thing went wrong ,Please contact Us"
                                    )
                                }
                            }
                        })
                    }
                })
        }
        cartRecycler.adapter = cartAdapter
        ItemTouchHelper(itemCallBack).attachToRecyclerView(cartRecycler)

    }

    private fun makeOrder(cart: Cart) {
        viewModel.placeOrder(
            Order(
                cart,
                totalPrice = getTotalPrice(),
                state = DeliveryStat.WAITING
            )
        )

    }

    private fun getTotalPrice(): Double {
        var total = 0.0
        products.forEach { total += (it.price.toInt() * it.quantityOrdered) }
        totalPrice.text = total.toString()
        return total
    }
}