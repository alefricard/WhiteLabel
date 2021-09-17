package br.com.ricardo.whitelabel.ui.addproduct

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.ricardo.whitelabel.R
import br.com.ricardo.whitelabel.domain.usecase.CreateProductUseCase
import br.com.ricardo.whitelabel.util.fromCurrency
import kotlinx.coroutines.launch
import kotlin.Exception

class AddProductViewModel(
    private val createProductUseCase: CreateProductUseCase
) : ViewModel() {

    private val _imageUriErrorResId = MutableLiveData<Int>()
    val imageUriErrorResId: LiveData<Int> = _imageUriErrorResId

    private val _descriptionFieldErrorId = MutableLiveData<Int?>()
    val descriptionFieldError: LiveData<Int?> = _descriptionFieldErrorId

    private val _priceFieldErrorId = MutableLiveData<Int?>()
    val priceFieldErrorId: LiveData<Int?> = _priceFieldErrorId

    private var isFormValid = false



    fun createProduct(description: String, price: String, imageUri: Uri?) = viewModelScope.launch {
        isFormValid = true

        _imageUriErrorResId.value  = getDrawableResIdIfNull(imageUri)
        _descriptionFieldErrorId.value = getErrorStringResIdEmpty(description)
        _priceFieldErrorId.value = getErrorStringResIdEmpty(price)

        if (isFormValid){
            try {
                val product = createProductUseCase(description, price.fromCurrency(),imageUri!!)
            } catch (e: Exception){
                Log.e("CreateProduct", e.toString())
            }
        }
    }

    private fun getErrorStringResIdEmpty(value: String): Int?{
        return if (value.isEmpty()){
            isFormValid = false
            R.string.add_product_field_error
        }else null
    }

    private fun getDrawableResIdIfNull(value: Uri?): Int{
        return if (value == null){
            isFormValid = false
            R.drawable.background_product_image_error
        }else R.drawable.background_product_image
    }

}