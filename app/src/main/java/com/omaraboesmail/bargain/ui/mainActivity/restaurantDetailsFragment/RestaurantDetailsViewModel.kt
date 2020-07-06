package com.omaraboesmail.bargain.ui.mainActivity.restaurantDetailsFragment

import androidx.lifecycle.ViewModel
import com.omaraboesmail.bargain.data.RestaurantRepo

class RestaurantDetailsViewModel : ViewModel() {

    fun getRestaurant() = RestaurantRepo.restaurantByName
    fun getCachedName() = RestaurantRepo.restaurantName.value
}