package com.ese12.gilgitapp.repo

import android.graphics.Bitmap
import android.net.Uri
import com.ese12.gilgitapp.domain.FilterOptions
import com.ese12.gilgitapp.domain.MyCategory
import com.ese12.gilgitapp.domain.MyResult
import com.ese12.gilgitapp.domain.SortByOption
import com.ese12.gilgitapp.domain.models.*
import com.ese12.gilgitapp.domain.Util.Companion.bitmapToByteArray
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.database.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class MainRepositoryImpl : MainRepository {

    private val storage = FirebaseStorage.getInstance()
    private val storageReference: StorageReference = storage.reference
    private val database = FirebaseDatabase.getInstance()
    override suspend fun uploadImageToFirebaseStorage(uri: String): MyResult {
        return try {
            // Upload an image from a file URI to Firebase Storage

            val imageRef = storageReference.child("images/${System.currentTimeMillis()}.jpg")
            val uploadTask = imageRef.putFile(Uri.parse(uri))

            // Wait for the upload to complete
            val result: UploadTask.TaskSnapshot = uploadTask.await()

            // Get the download URL of the uploaded image
            val downloadUrl = result.storage.downloadUrl.await()

            // Return a Success with the download URL as a String
            MyResult.Success(downloadUrl.toString())
        } catch (e: Exception) {
            // In case of an error, return an Error instance with the error message
            MyResult.Error(e.message ?: "Unknown error occurred")
        }
    }

    override suspend fun uploadImageToFirebaseStorage(bitmap: Bitmap): MyResult {
        return try {
            // Convert the Bitmap to a ByteArray
            val byteArray = bitmapToByteArray(bitmap)

            // Upload the ByteArray to Firebase Storage
            val imageRef = storageReference.child("images/${System.currentTimeMillis()}.jpg")
            val uploadTask = imageRef.putBytes(byteArray)

            // Wait for the upload to complete
            val result: UploadTask.TaskSnapshot = uploadTask.await()

            // Get the download URL of the uploaded image
            val downloadUrl = result.storage.downloadUrl.await()

            // Return a Success with the download URL as a String
            MyResult.Success(downloadUrl.toString())
        } catch (e: Exception) {
            // In case of an error, return an Error instance with the error message
            MyResult.Error(e.message ?: "Unknown error occurred")
        }
    }

    override suspend fun deleteImageToFirebaseStorage(url: String): MyResult {
        return try {
            val storage = FirebaseStorage.getInstance()
            val storageRef = storage.getReferenceFromUrl(url)

            val deleteTask: Task<Void> = storageRef.delete()
            Tasks.await(deleteTask)

            // If the task completes without an exception, consider it a success
            MyResult.Success("Image deleted successfully")
        } catch (e: Exception) {
            // Handle the exception or error here and return an Error result
            e.printStackTrace() // Replace this with proper error handling
            MyResult.Error("Failed to delete image: ${e.message}")
        }
    }

    override suspend fun uploadProduct(productModel: ProductModel): MyResult {
        val reference: DatabaseReference =
            database.getReference("products").child(productModel.modelName)
        return try {
            val k = reference.push().key
            // Set the value of the product using await to wait for the operation to complete
            reference.child(k!!).setValue(productModel).await()
            // Return a success MyResult
            MyResult.Success("Product uploaded successfully")
        } catch (e: Exception) {
            // Handle any errors and return an Error MyResult
            e.printStackTrace() // Replace this with proper error handling
            MyResult.Error("Failed to upload product: ${e.message}")
        }
    }

    override suspend fun updateProduct(productModel: ProductModel): MyResult {
        val reference: DatabaseReference =
            database.getReference("products").child(productModel.modelName)
        return try {
            // Set the value of the product using await to wait for the operation to complete
            reference.child(productModel.key).setValue(productModel).await()
            // Return a success MyResult
            MyResult.Success("Product uploaded successfully")
        } catch (e: Exception) {
            // Handle any errors and return an Error MyResult
            e.printStackTrace() // Replace this with proper error handling
            MyResult.Error("Failed to upload product: ${e.message}")
        }
    }

    override suspend fun deleteProduct(productModel: ProductModel): MyResult {
        // Assuming you have a reference to the database
        val reference: DatabaseReference = database.getReference("products")

        return try {
            val key =
                productModel.key // Assuming 'key' is the property within ProductModel containing the unique key

            if (key != null) {
                reference.child(key).removeValue().await()
                MyResult.Success("Product deleted successfully")
            } else {
                MyResult.Error("Invalid key for product")
            }
        } catch (e: Exception) {
            e.printStackTrace() // Replace this with proper error handling
            MyResult.Error("Failed to delete product: ${e.message}")
        }
    }

    override suspend fun getProductDetailsByKey(key: String): ProductModel {
        TODO("Not yet implemented")
    }

    override suspend fun getUserDetailsByUid(key: String): UserModel {
        TODO("Not yet implemented")
    }

    override suspend fun getAllProductsList(): List<ProductModel> {
        return try {
            val reference: DatabaseReference = database.getReference("products")

            val productList = mutableListOf<ProductModel>()

            val snapshot = reference.get().await()
            for (categorySnapshot in snapshot.children) {
                for (modelSnapshot in categorySnapshot.children) {
                    val product = modelSnapshot.getValue(ProductModel::class.java)
                    product?.let {
                        productList.add(it)
                    }
                }
            }

            productList
        } catch (e: Exception) {
            // Handle the exception or log the error
            emptyList() // Return an empty list or handle the error according to your use case
        }
    }

    override fun collectAllProductsList(): Flow<List<ProductModel>> = callbackFlow {
        val reference: DatabaseReference = database.getReference("products")

        val eventListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val productList = mutableListOf<ProductModel>()

                for (categorySnapshot in snapshot.children) {
                    for (modelSnapshot in categorySnapshot.children) {
                        val product = modelSnapshot.getValue(ProductModel::class.java)
                        product?.let {
                            productList.add(it)
                        }
                    }
                }
                try {
                    trySend(productList).isSuccess // Emit the list of ProductModel objects to the flow
                    close() // Close the flow when data emission is completed
                } catch (e: Exception) {
                    close(e) // Close the flow with an exception if emission fails
                }
            }

            override fun onCancelled(error: DatabaseError) {
                close(error.toException()) // Close the flow with an exception in case of cancellation
            }
        }

        reference.addListenerForSingleValueEvent(eventListener)

        awaitClose { reference.removeEventListener(eventListener) }
    }

    override fun collectProductsByModel(myCategory: MyCategory): Flow<List<ProductModel>> =
        callbackFlow {
            val reference: DatabaseReference =
                database.getReference("products").child(myCategory.name)

            val eventListener = object : ValueEventListener {
                override fun onDataChange(categorySnapshot: DataSnapshot) {
                    val productList = mutableListOf<ProductModel>()

                    for (modelSnapshot in categorySnapshot.children) {
                        val product = modelSnapshot.getValue(ProductModel::class.java)
                        product?.let {
                            productList.add(it)
                        }
                    }

                    try {
                        trySend(productList).isSuccess // Emit the list of ProductModel objects to the flow
                        close() // Close the flow when data emission is completed
                    } catch (e: Exception) {
                        close(e) // Close the flow with an exception if emission fails
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    close(error.toException()) // Close the flow with an exception in case of cancellation
                }
            }

            reference.addListenerForSingleValueEvent(eventListener)

            awaitClose { reference.removeEventListener(eventListener) }
        }

    override suspend fun getProductsOfCategory(category: MyCategory): List<ProductModel> {
        return try {
            val reference: DatabaseReference =
                database.getReference("products").child(category.name)

            val productList = mutableListOf<ProductModel>()
            val categorySnapshot = reference.get().await()
            for (modelSnapshot in categorySnapshot.children) {
                val product = modelSnapshot.getValue(ProductModel::class.java)
                product?.let {
                    productList.add(it)
                }
            }

            productList
        } catch (e: Exception) {
            // Handle the exception or log the error
            emptyList() // Return an empty list or handle the error according to your use case
        }
    }

    override suspend fun filterProducts(filters: FilterOptions): List<ProductModel> {
        val allProducts = getAllProductsList()

        return allProducts.filter { product ->
            // Apply filters based on FilterOptions
            val categoryMatch =
                filters.modelName.isNullOrBlank() || product.modelName == filters.modelName
            val minPriceMatch = filters.minPrice == null || product.price >= filters.minPrice
            val maxPriceMatch = filters.maxPrice == null || product.price <= filters.maxPrice

            categoryMatch && minPriceMatch && maxPriceMatch
        }.sortedWith(getProductComparator(filters.sortBy) ?: compareBy { it.timeStamp })
    }

    private fun getProductComparator(sortBy: SortByOption?): Comparator<ProductModel>? {
        return when (sortBy) {
            SortByOption.PRICE_LOW_TO_HIGH -> compareBy { it.price }
            SortByOption.PRICE_HIGH_TO_LOW -> compareByDescending { it.price }
            SortByOption.NAME_A_TO_Z -> compareBy { it.title }
            SortByOption.NAME_Z_TO_A -> compareByDescending { it.title }
            SortByOption.OLDER -> compareBy { it.timeStamp }
            SortByOption.NEWER -> compareByDescending { it.timeStamp }
            else -> null
        }
    }

    override fun collectCartItems(userUid: String): Flow<List<ProductModel>> = callbackFlow {
        val reference: DatabaseReference = database.getReference("cart").child(userUid)

        val eventListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val productList = mutableListOf<ProductModel>()

                for (categorySnapshot in snapshot.children) {
                    for (modelSnapshot in categorySnapshot.children) {
                        val product = modelSnapshot.getValue(ProductModel::class.java)
                        product?.let {
                            productList.add(it)
                        }
                    }
                }
                try {
                    trySend(productList).isSuccess // Emit the list of ProductModel objects to the flow
                    close() // Close the flow when data emission is completed
                } catch (e: Exception) {
                    close(e) // Close the flow with an exception if emission fails
                }
            }

            override fun onCancelled(error: DatabaseError) {
                close(error.toException()) // Close the flow with an exception in case of cancellation
            }
        }

        reference.addListenerForSingleValueEvent(eventListener)

        awaitClose { reference.removeEventListener(eventListener) }
    }

    override suspend fun decreaseInStock(product: ProductModel, amount: Int): MyResult {
        val reference: DatabaseReference =
            database.getReference("products/${product.modelName}/${product.key}")

        try {
            // Fetch the current stock amount
            val currentStock = product.stockAmount

            if (currentStock >= amount) {
                // Calculate the new stock amount after decrease
                val newStock = currentStock - amount

                // Update the stock amount in the database
                reference.child("stockAmount").setValue(newStock)

                return MyResult.Success("Stock updated successfully")
            } else {
                return MyResult.Error("Not enough stock available")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return MyResult.Error("Failed to update stock: ${e.message}")
        }
    }


    override suspend fun increaseInStock(product: ProductModel, amount: Int): MyResult {
        val database = FirebaseDatabase.getInstance()
        val reference: DatabaseReference =
            database.getReference("products/${product.modelName}/${product.key}")

        return try {
            // Fetch the current stock amount
            val currentStock = product.stockAmount

            // Calculate the new stock amount after increase
            val newStock = currentStock + amount

            // Update the stock amount in the database
            reference.child("stockAmount").setValue(newStock)

            MyResult.Success("Stock updated successfully")
        } catch (e: Exception) {
            e.printStackTrace()
            MyResult.Error("Failed to update stock: ${e.message}")
        }
    }


    override suspend fun addToCart(cartModel: CartModel): MyResult {
        cartModel.key = cartModel.productModel.key
        val reference: DatabaseReference =
            database.getReference("userCarts/${cartModel.buyerUid}/${cartModel.key}")

        return try {
            // Update the cart entry in the user's cart in the database
            reference.setValue(cartModel)

            MyResult.Success("Product added to cart successfully")
        } catch (e: Exception) {
            e.printStackTrace()
            MyResult.Error("Failed to add product to cart: ${e.message}")
        }
    }

    override suspend fun updateToCart(cartModel: CartModel): MyResult {
        TODO("Not yet implemented")
    }

    override suspend fun deleteFromCart(cartModel: CartModel): MyResult {
        TODO("Not yet implemented")
    }

    override suspend fun getCartDetailsByKey(userUid: String, key: String): CartModel {
        TODO("Not yet implemented")
    }


    override suspend fun buy(orderModel: OrderModel): MyResult {
        val userId = orderModel.buyerUid
        val cartReference: DatabaseReference =
            database.getReference("userCarts/$userId/${orderModel.productModel.key}")

        return try {
            // Fetch the current quantity in the cart
//            var cart
//            val currentQuantity = orderModel.productModel.cartQuantity.toInt()
//
//            // Check if there's enough quantity in the cart
//            if (currentQuantity >= orderModel.quantity) {
//                // Calculate the new quantity in the cart after purchase
//                val newQuantity = currentQuantity - orderModel.quantity.toInt()
//
//                // Update the quantity in the user's cart
//                cartReference.child("cartQuantity").setValue(newQuantity)
//
//                // Create an order
//                val orderKey = generateOrderKey()
//                val orderReference: DatabaseReference = database.getReference("orders/$orderKey")
//
//                // Set the order key before saving to Firebase
//                orderModel.key = orderKey
//
//                orderReference.setValue(orderModel)
//
            MyResult.Success("Order placed successfully")
//            } else {
//                MyResult.Error("Not enough quantity available in the cart")
//            }
        } catch (e: Exception) {
            e.printStackTrace()
            MyResult.Error("Failed to place order: ${e.message}")
        }
    }

    override suspend fun deliver(orderModel: OrderModel): MyResult {
        return try {
            // Fetch the current quantity in the cart
//            var cart
//            val currentQuantity = orderModel.productModel.cartQuantity.toInt()
//
//            // Check if there's enough quantity in the cart
//            if (currentQuantity >= orderModel.quantity) {
//                // Calculate the new quantity in the cart after purchase
//                val newQuantity = currentQuantity - orderModel.quantity.toInt()
//
//                // Update the quantity in the user's cart
//                cartReference.child("cartQuantity").setValue(newQuantity)
//
//                // Create an order
//                val orderKey = generateOrderKey()
//                val orderReference: DatabaseReference = database.getReference("orders/$orderKey")
//
//                // Set the order key before saving to Firebase
//                orderModel.key = orderKey
//
//                orderReference.setValue(orderModel)

            MyResult.Success("Order placed successfully")
//            } else {
//                MyResult.Error("Not enough quantity available in the cart")
//            }
        } catch (e: Exception) {
            e.printStackTrace()
            MyResult.Error("Failed to place order: ${e.message}")
        }
    }

    override suspend fun removeFromCart(userId: String, productId: String): MyResult {
        val reference: DatabaseReference = database.getReference("userCarts/$userId/$productId")

        return try {
            // Remove the product from the user's cart in the database
            reference.removeValue()

            MyResult.Success("Product removed from cart successfully")
        } catch (e: Exception) {
            e.printStackTrace()
            MyResult.Error("Failed to remove product from cart: ${e.message}")
        }
    }

    override suspend fun placeOrder(
        userId: String,
        cartItems: List<ProductModel>
    ): Flow<OrderModel> {
        TODO("Not yet implemented")
    }

    override suspend fun getOrderHistory(userId: String): Flow<List<OrderModel>> {
        TODO("Not yet implemented")
    }

    override suspend fun addToOrderHistory(orderModel: OrderModel): Flow<List<OrderModel>> {
        TODO("Not yet implemented")
    }

    override suspend fun addToFavorites(userId: String, productKey: String): MyResult {
        TODO("Not yet implemented")
    }

    override suspend fun removeFromFavorites(userId: String, productId: String): MyResult {
        TODO("Not yet implemented")
    }

    override fun collectProductReviews(productId: String): Flow<List<ReviewModel>> {
        TODO("Not yet implemented")
    }

    override suspend fun submitProductReview(productId: String, review: ReviewModel): MyResult {
        TODO("Not yet implemented")
    }

    override fun getUserInfoByKey(key: String): Flow<UserModel> {
        TODO("Not yet implemented")
    }

    override fun getProductByKey(key: String): Flow<UserModel> {
        TODO("Not yet implemented")
    }

}
