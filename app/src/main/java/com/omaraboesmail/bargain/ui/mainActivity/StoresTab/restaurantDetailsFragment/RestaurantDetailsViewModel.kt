package com.omaraboesmail.bargain.ui.mainActivity.StoresTab.restaurantDetailsFragment

import androidx.lifecycle.ViewModel
import com.omaraboesmail.bargain.data.stores.RestaurantRepo

class RestaurantDetailsViewModel : ViewModel() {

    fun getRestaurant() = RestaurantRepo.restaurantByName
    fun getCachedName() = RestaurantRepo.restaurantName.value
}