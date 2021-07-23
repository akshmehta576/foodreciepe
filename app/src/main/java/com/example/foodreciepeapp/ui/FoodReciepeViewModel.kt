package com.example.foodreciepeapp.ui

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodreciepeapp.model.data.FoodReciepeListEntry
import com.example.foodreciepeapp.repository.FoodReciepeRepository
import com.example.foodreciepeapp.util.Constants.PAGE_SIZE
import com.example.foodreciepeapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*
import javax.inject.Inject
@HiltViewModel
class FoodReciepeViewModel @Inject constructor(
    private val foodReciepeRepository: FoodReciepeRepository
) : ViewModel()
{

    private var currentPage = 1
    var foodList = mutableStateOf<List<FoodReciepeListEntry>>(listOf())
    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)
    var endReached = mutableStateOf(false)
    private var cachedFoodList = listOf<FoodReciepeListEntry>()
    private var isSearchStarting = true
    var isSearching = mutableStateOf(false)

    init {
        getAllFood()
    }

    fun searchFood(text : String)
    {
        val foodList = mutableStateOf<List<FoodReciepeListEntry>>(listOf())
        val loadError = mutableStateOf("")
        val isLoading = mutableStateOf(false)
        var endReached = mutableStateOf(false)

        val listToSearch = if(isSearchStarting){
            foodList.value
        }else{
            cachedFoodList
        }
        viewModelScope.launch {
            isLoading.value = true
            if(text.isEmpty()){
                foodList.value = cachedFoodList
                isSearching.value=false
                isSearchStarting = true
                return@launch
            }
            val response = foodReciepeRepository.getFoodReciepeList(1, text)
            endReached.value = currentPage * PAGE_SIZE >= response.data!!.count
            when (response)
            {
                is Resource.Success -> {
                    val foodentrie: List<FoodReciepeListEntry> =
                        response.data.results.mapIndexed { index, result ->
                            FoodReciepeListEntry(
                                result.title.capitalize(Locale.ROOT),
                                result.featured_image
                            )
                        }

                    currentPage++
                    loadError.value = ""
                    isLoading.value = false
                    foodList.value += foodentrie
                    isSearching.value = true

                }

                is Resource.Error ->{
                    loadError.value = response.message!!
                    isLoading.value = false
                }

            }


        }
    }

    fun getAllFood()
    {
        viewModelScope.launch {

            isLoading.value = true
            val response = foodReciepeRepository.getFoodReciepeList(currentPage,"")
            endReached.value = currentPage * PAGE_SIZE >= response.data!!.count
            when(response) {
                is Resource.Success -> {

                    val foodentries : List<FoodReciepeListEntry> =
                        response.data.results.mapIndexed { index, result ->
                            FoodReciepeListEntry(result.title.capitalize(Locale.ROOT), result.featured_image)
                        }

                    currentPage++
                    loadError.value = ""
                    isLoading.value = false
                    foodList.value += foodentries

                }


                is Resource.Error ->{
                    loadError.value = response.message!!
                    isLoading.value = false
                }
            }
        }

    }

}
