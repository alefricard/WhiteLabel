package br.com.ricardo.whitelabel.data

import android.net.Uri
import br.com.ricardo.whitelabel.domain.model.Product

class ProductRepository(private val dataSource: ProductDataSource) {

    suspend fun getProduct(): List<Product> = dataSource.getProduct()

    suspend fun uploadProductImage(imageUri: Uri): String =
        dataSource.uploadProductImage(imageUri)

    suspend fun createProduct(product: Product): Product =
        dataSource.createProduct(product)

}