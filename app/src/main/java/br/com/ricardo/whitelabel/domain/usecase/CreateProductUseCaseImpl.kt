package br.com.ricardo.whitelabel.domain.usecase

import android.net.Uri
import br.com.ricardo.whitelabel.data.ProductRepository
import br.com.ricardo.whitelabel.domain.model.Product
import java.lang.Exception
import java.util.*

class CreateProductUseCaseImpl(

    private val uploadProductImageUseCase: UploadProductImageUseCase,
    private val productRepository: ProductRepository

    ): CreateProductUseCase {

    override suspend fun invoke(description: String, price: Double, imageUri: Uri): Product {

        return try {
            val imageUrl = uploadProductImageUseCase(imageUri)
            val product = Product(UUID.randomUUID().toString(), description, price, imageUrl)
            productRepository.createProduct(product)
        } catch (e: Exception) {
            throw e
        }
    }
}