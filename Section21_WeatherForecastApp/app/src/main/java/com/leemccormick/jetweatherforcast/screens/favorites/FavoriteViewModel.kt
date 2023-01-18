package com.leemccormick.jetweatherforcast.screens.favorites

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.viewModelScope
import com.leemccormick.jetweatherforcast.model.Favorite
import com.leemccormick.jetweatherforcast.repository.WeatherDbRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val repository: WeatherDbRepository) :
    ViewModel() {
    private val _favList = MutableStateFlow<List<Favorite>>(emptyList())
    val favList = _favList.asStateFlow()


    init {
        // Do something in the background using viewModelScope.launch
        viewModelScope.launch(Dispatchers.IO) {
            repository.getFavorites().distinctUntilChanged().collect { listOfFavs ->
                if (listOfFavs.isNullOrEmpty()) {
                    Log.d("viewModelScope", "Empty Favorites !")
                } else {
                    _favList.value = listOfFavs
                    Log.d("viewModelScope", "List of Favorites : ${_favList.value}!")
                }
            }
        }
    }

    fun insertFavorite(favorite: Favorite) =
        viewModelScope.launch { repository.insertFavorite(favorite) }

    fun updateFavorite(favorite: Favorite) =
        viewModelScope.launch { repository.updateFavorite(favorite) }

    fun deleteFavorite(favorite: Favorite) =
        viewModelScope.launch { repository.deleteFavorite(favorite) }

    fun deleteAllFavorites() = viewModelScope.launch { repository.deleteAllFavorites() }

    fun getFavById(city: String) = viewModelScope.launch { repository.getFavById(city) }
}