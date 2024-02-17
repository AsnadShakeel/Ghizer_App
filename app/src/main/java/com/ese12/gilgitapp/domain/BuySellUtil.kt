package com.ese12.gilgitapp.domain

import com.ese12.gilgitapp.domain.models.OrderModel
import com.ese12.gilgitapp.domain.models.ProductModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

object BuySellUtil {
    var database = FirebaseDatabase.getInstance().reference
    // Assume this method fetches the user's cart and processes the purchase
//    OrderModel(key,System.currentTimeMillis(),quantity*product.price ,quantity,product,buyerUid ,OrderStatus.PENDING,location,)
    suspend fun processPurchase(orderModel: OrderModel): MyResult {
        // Step 1: Decrease stock amount
        val decreaseResult = decreaseInStock(orderModel.productModel, orderModel.quantity)
        if (decreaseResult is MyResult.Error) {
            // Handle the error (e.g., insufficient stock)
            return decreaseResult
        }
var key = database.child("pendingOrders").push().key.toString()
        // Step 2: Add product to seller's pendingOrders
        val addToPendingOrdersResult = addToSellerAndBuyerPendingOrders(orderModel)
        if (addToPendingOrdersResult is MyResult.Error) {
            // Handle the error (e.g., unable to add to pendingOrders)
            return addToPendingOrdersResult
        }

        // Step 3: Notify the seller
        notifySeller(orderModel)

        // Step 4: Remove the purchased items from the user's cart
        val removeFromCartResult = removeFromCart(orderModel.buyerUid, orderModel.productModel, orderModel.quantity)
        if (removeFromCartResult is MyResult.Error) {
            // Handle the error (e.g., unable to remove from cart)
            return removeFromCartResult
        }

        return MyResult.Success("Purchase processed successfully")
    }

    private suspend fun decreaseInStock(product: ProductModel, amount: Double): MyResult {
        val reference: DatabaseReference = database.child("products/${product.modelName}/${product.key}")

        return try {
            // Fetch the current stock amount
            val currentStock = product.stockAmount.toDouble()

            // Check if there's enough stock
            if (currentStock >= amount) {
                // Calculate the new stock amount after decrease
                val newStock = currentStock - amount

                // Update the stock amount in the database
                reference.child("stockAmount").setValue(newStock)

                MyResult.Success("Stock updated successfully")
            } else {
                MyResult.Error("Not enough stock available")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            MyResult.Error("Failed to update stock: ${e.message}")
        }
    }

    private suspend fun addToSellerAndBuyerPendingOrders(orderModel: OrderModel): MyResult {

        val buyerRef =  database.child("pendingOrders/${orderModel.buyerUid}")
        val sellerRef =  database.child("pendingOrders/${orderModel.productModel.sellerUid}")

        return try {

            buyerRef.child(orderModel.key).setValue(orderModel)
            sellerRef.child(orderModel.key).setValue(orderModel)

            MyResult.Success("Product added to seller's and buyer's pending orders successfully")
        } catch (e: Exception) {
            e.printStackTrace()
            MyResult.Error("Failed to add product to seller's and buyer's pending orders: ${e.message}")
        }
    }

    private suspend fun notifySeller(orderModel: OrderModel) : MyResult {

        val buyerRef =  database.child("notifications/${orderModel.buyerUid}")
        val sellerRef =  database.child("notifications/${orderModel.productModel.sellerUid}")

        return try {

            buyerRef.child(orderModel.key).setValue(orderModel)
            sellerRef.child(orderModel.key).setValue(orderModel)

            MyResult.Success("Product added to seller's and buyer's pending orders successfully")
        } catch (e: Exception) {
            e.printStackTrace()
            MyResult.Error("Failed to add product to seller's and buyer's pending orders: ${e.message}")
        }
    }

    private suspend fun removeFromCart(userId: String, product: ProductModel, quantity: Double): MyResult {
        // Implementation of removeFromCart function
        // ...

        return try {

            MyResult.Success("Product added to seller's and buyer's pending orders successfully")
        } catch (e: Exception) {
            e.printStackTrace()
            MyResult.Error("Failed to add product to seller's and buyer's pending orders: ${e.message}")
        }
    }

}