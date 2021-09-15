package br.com.ricardo.whitelabel.domain.usecase

import android.net.Uri
import br.com.ricardo.whitelabel.domain.model.Product

interface CreateProductUseCase {
    suspend operator fun invoke(description: String, price : Double,imageUri : Uri): Product
}