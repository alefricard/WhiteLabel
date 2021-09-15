package br.com.ricardo.whitelabel.domain.usecase

import br.com.ricardo.whitelabel.domain.model.Product

interface GetProductUseCase {
    suspend operator fun invoke(): List<Product>
}