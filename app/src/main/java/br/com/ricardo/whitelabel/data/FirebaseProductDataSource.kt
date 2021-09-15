package br.com.ricardo.whitelabel.data

import android.net.Uri
import br.com.ricardo.whitelabel.BuildConfig
import br.com.ricardo.whitelabel.domain.model.Product
import br.com.ricardo.whitelabel.util.COLLECTION_PRODUCTS
import br.com.ricardo.whitelabel.util.COLLECTION_ROOT
import br.com.ricardo.whitelabel.util.STORAGE_IMAGES
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.util.*
import kotlin.coroutines.suspendCoroutine

class FirebaseProductDataSource(
    firebaseFirestore : FirebaseFirestore,
    firebaseStorage: FirebaseStorage

) : ProductDataSource {
    /* Estrutura das coleções:
        data/car/products/timestamp/productA
        data/bike/products/timestamp/productB
    */

    private val documentReference = firebaseFirestore
        .document("$COLLECTION_ROOT/${BuildConfig.FIREBASE_FLAVOR_COLLECTION}/",)

    private val storageReference = firebaseStorage.reference

    /*Faz a comparação dos atributos para chamar e montar a lista
    * usando coroutine para facilitar a implementação e evitar fazer varios callbacks*/

    override suspend fun getProduct(): List<Product> {
        return suspendCoroutine { continuation ->
            val productReference = documentReference.collection(COLLECTION_PRODUCTS)
            productReference.get().addOnSuccessListener { documents ->
                val products = mutableListOf<Product>()
                for (document in documents){
                    document.toObject(Product::class.java).run {
                        products.add(this)
                    }
                }
                continuation.resumeWith(Result.success(products))
            }
            productReference.get().addOnFailureListener { exception ->
                continuation.resumeWith(Result.failure(exception))
            }
        }

    }

    override suspend fun uploadProductImage(imageUri: Uri): String {
        return suspendCoroutine { continuation ->
            val randomKey = UUID.randomUUID()
            val childReference = storageReference.child(
                "$STORAGE_IMAGES/${BuildConfig.FIREBASE_FLAVOR_COLLECTION}/$randomKey"
            )

            childReference.putFile(imageUri).addOnSuccessListener { taskSnapshot ->
                taskSnapshot.storage.downloadUrl.addOnSuccessListener { uri ->
                    val path = uri.toString()
                    continuation.resumeWith(Result.success(path))
                }

            }.addOnFailureListener { exception ->
                continuation.resumeWith(Result.failure(exception))
            }

        }
    }

    override suspend fun createProduct(product: Product): Product {
        return suspendCoroutine { continuation ->
            documentReference
                .collection(COLLECTION_PRODUCTS)
                .document(System.currentTimeMillis().toString())
                .set(product)
                .addOnSuccessListener {
                    continuation.resumeWith(Result.success(product))
                }
                .addOnFailureListener { exception ->
                    continuation.resumeWith(Result.failure(exception))

                }
        }
    }

}