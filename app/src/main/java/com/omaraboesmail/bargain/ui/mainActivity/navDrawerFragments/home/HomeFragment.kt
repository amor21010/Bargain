package com.omaraboesmail.bargain.ui.mainActivity.navDrawerFragments.home

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.omaraboesmail.bargain.R
import com.omaraboesmail.bargain.data.FireBaseConst.firebaseAuthInstance
import com.omaraboesmail.bargain.data.UserRepo.fbUserLive
import com.omaraboesmail.bargain.data.UserRepo.setFirebaseUser
import com.omaraboesmail.bargain.pojo.User

import com.omaraboesmail.bargain.utils.Const.TAG
import com.omaraboesmail.bargain.utils.DialogMaker
import com.omaraboesmail.bargain.utils.DialogMaker.mContext
import com.omaraboesmail.bargain.utils.setEmail

import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModels()

    private lateinit var dialog: Dialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onResume() {
        getData()
        super.onResume()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mContext = activity as AppCompatActivity
        dialog = DialogMaker.authDialog()
        getData()

    }

    @SuppressLint("SetTextI18n")
    private fun getData() {

        dialog = DialogMaker.verifyEmailDialog()
        firebaseAuthInstance.currentUser?.reload()
        setFirebaseUser(firebaseAuthInstance.currentUser)

        homeViewModel.getCurrentUserData.observe(viewLifecycleOwner, Observer { user ->
            Log.d(TAG, "user +$user")
            if (user != null) {
                setEmail(user.email)
                getEmailVerStat(user)


            }
        })
    }


    @SuppressLint("SetTextI18n")
    private fun getEmailVerStat(user: User) {

        homeViewModel.isUserEmailVerified()
            .observe(viewLifecycleOwner, Observer { state ->
                text_home.text = state.msg + "\n" + user.email
                Log.d(TAG, "stata +$state")
                if (!state.state) {
                    dialog.dismiss()
                    dialog.show()
                } else {
                    if (dialog.isShowing) dialog.dismiss()
                    if (user.id != fbUserLive.value!!.uid || user.approved != state.state) {
                        user.id = fbUserLive.value!!.uid
                        user.approved = state.state
                        homeViewModel.updateUser(user = user)
                        Log.d(TAG, "aproved +${user.approved}")

                    }
                }


            })
    }

}
