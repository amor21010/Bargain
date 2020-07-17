package com.omaraboesmail.bargain.ui.mainActivity.StoresTab.restaurantsFragment

import androidx.lifecycle.ViewModel
import com.omaraboesmail.bargain.data.stores.RestaurantRepo

class RestaurantsViewModel : ViewModel() {
    fun getAllRestaurants() = RestaurantRepo.getAllStores()
}
