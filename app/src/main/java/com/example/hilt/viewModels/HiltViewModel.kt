package com.example.hilt.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hilt.database.AppDatabase
import com.example.hilt.model.ActivityResponse
import com.example.hilt.network.Repository
import com.example.hilt.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HiltViewModel @Inject constructor(
    private val repository: Repository,
    private val appDatabase: AppDatabase
) : ViewModel() {
    val resultUser = MutableLiveData<Resource<ActivityResponse>>()
    private var currentIndex = 0

    fun getRepo() {

        resultUser.value = Resource.loading(null)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val cachedData = appDatabase.roomDao().getAllUsers()

                if (cachedData.isNotEmpty() && currentIndex < cachedData.size) {
                    val nextData = cachedData[currentIndex]
                    currentIndex++
                    withContext(Dispatchers.Main) {
                        resultUser.value =
                            Resource.success(nextData, "Next data loaded from database.")
                    }
                } else {
                    val response = repository.getRepo()
                    if (response.isSuccessful) {
                        response.body()?.let {

                            withContext(Dispatchers.Main) {
                                resultUser.value = Resource.success(
                                    it, "Data loaded from API and saved to database."
                                )
                                withContext(Dispatchers.Main){
                                    appDatabase.roomDao().insert(it)
                                }
                            }
                        } ?: run {
                            withContext(Dispatchers.Main) {
                                resultUser.value = Resource.error(null, "Response body is null.")
                            }
                        }
                    } else {
                        withContext(Dispatchers.Main) {
                            resultUser.value = Resource.error(null, response.message().toString())
                        }
                    }
                }
            } catch (t: Exception) {
                withContext(Dispatchers.Main) {
                    resultUser.value = Resource.error(null, t.message.toString())
                }
            }
        }
    }


}
