package br.com.ricardo.whitelabel.domain.usecase

import br.com.ricardo.whitelabel.data.ProductRepository
import br.com.ricardo.whitelabel.domain.model.Product
import javax.inject.Inject

class GetProductUseCaseImpl @Inject constructor(
    private val productRepository: ProductRepository
): GetProductUseCase {
    override suspend fun invoke(): List<Product> {
        return productRepository.getProduct()
    }

}