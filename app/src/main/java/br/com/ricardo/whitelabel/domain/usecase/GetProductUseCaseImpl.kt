package br.com.ricardo.whitelabel.domain.usecase

import br.com.ricardo.whitelabel.data.ProductRepository
import br.com.ricardo.whitelabel.domain.model.Product

class GetProductUseCaseImpl(
    private val productRepository: ProductRepository
): GetProductUseCase {
    override suspend fun invoke(): List<Product> {
        return productRepository.getProduct()
    }

}