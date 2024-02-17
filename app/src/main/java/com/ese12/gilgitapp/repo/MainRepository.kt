package com.ese12.gilgitapp.repo

import android.graphics.Bitmap
import com.ese12.gilgitapp.domain.models.*
import com.ese12.gilgitapp.domain.FilterOptions
import com.ese12.gilgitapp.domain.MyResult
import com.ese12.gilgitapp.domain.MyCategory
import kotlinx.coroutines.flow.Flow

interface MainRepository {
    suspend fun uploadImageToFirebaseStorage(uri:String): MyResult
    suspend fun uploadImageToFirebaseStorage(bitmap: Bitmap): MyResult
    suspend fun deleteImageToFirebaseStorage(url: String): MyResult
    suspend fun uploadProduct(productModel: ProductModel): MyResult
    suspend fun updateProduct(productModel: ProductModel): MyResult
    suspend fun deleteProduct(productModel: ProductModel): MyResult
    suspend fun getProductDetailsByKey(key: String): ProductModel
    suspend fun getUserDetailsByUid(uid: String): UserModel
    suspend fun getAllProductsList(): List<ProductModel>
    fun collectAllProductsList(): Flow<List<ProductModel>>
    fun collectProductsByModel(myCategory: MyCategory): Flow<List<ProductModel>>
    suspend fun getProductsOfCategory(myCategory: MyCategory): List<ProductModel>
    suspend fun filterProducts(filters: FilterOptions): List<ProductModel>
    fun collectCartItems(userId: String): Flow<List<ProductModel>>
    suspend fun decreaseInStock(product: ProductModel, amount:Int): MyResult
    suspend fun increaseInStock(product: ProductModel, amount:Int): MyResult
    suspend fun addToCart(cartModel: CartModel): MyResult // also seller to see who added in is cart his products
    suspend fun updateToCart(cartModel: CartModel): MyResult // change the quantity etc
    suspend fun deleteFromCart(cartModel: CartModel): MyResult // when order is completed or canceled
    suspend fun getCartDetailsByKey(userUid: String , key: String): CartModel // when order is completed or canceled
    suspend fun buy( orderModel: OrderModel): MyResult
    suspend fun deliver( orderModel: OrderModel): MyResult // handle his cart and change status
    suspend fun removeFromCart(userId: String, productId: String): MyResult
    suspend fun placeOrder(userId: String, cartItems: List<ProductModel>): Flow<OrderModel>
    suspend fun getOrderHistory(userId: String): Flow<List<OrderModel>>
    suspend fun addToOrderHistory(orderModel: OrderModel): Flow<List<OrderModel>>
    suspend fun addToFavorites(userId: String, productKey: String): MyResult
    suspend fun removeFromFavorites(userId: String, productId: String): MyResult
    fun collectProductReviews(productId: String): Flow<List<ReviewModel>>
    suspend fun submitProductReview(productId: String, review: ReviewModel): MyResult
    fun getUserInfoByKey(key: String): Flow<UserModel>
    fun getProductByKey(key: String): Flow<UserModel>
}

