package com.omaraboesmail.bargain.ui.mainActivity.restaurantsFragment

import androidx.lifecycle.ViewModel
import com.omaraboesmail.bargain.data.RestaurantRepo

class RestaurantsViewModel : ViewModel() {
    fun getAllRestaurants() = RestaurantRepo.getAllStores()
}
