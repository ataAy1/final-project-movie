package com.finalproject.data.repository

import android.util.Log
import com.finalproject.domain.model.payment.CouponModel
import com.finalproject.domain.model.payment.DiscountCouponModel
import com.finalproject.domain.model.payment.OrderModel
import com.finalproject.domain.repository.OrderRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.util.Date
import java.util.UUID
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) : OrderRepository {

    override suspend fun saveOrder(order: OrderModel) {
        val currentUserId = auth.currentUser?.uid
            ?: throw IllegalStateException("User not logged in")

        try {
            val randomId = UUID.randomUUID().toString()
            val path = "users/$currentUserId"

            firestore.collection("users").document(currentUserId)
                .collection("user_orders").document(randomId)
                .set(order)
                .await()

        } catch (e: Exception) {
            throw Exception("Failed to save order: ${e.message}", e)
        }
    }

    override suspend fun getOrders(): List<OrderModel> {
        val currentUserId = auth.currentUser?.uid
            ?: throw IllegalStateException("User not logged in")


        return try {
            val snapshot = firestore.collection("users")
                .document(currentUserId)
                .collection("user_orders")
                .get()
                .await()


            val orders = snapshot.documents.mapNotNull { document ->
                document.toObject(OrderModel::class.java)
            }


            orders
        } catch (e: Exception) {
            emptyList()
        }
    }


    override suspend fun saveCoupon(coupon: CouponModel) {
        val currentUserId = auth.currentUser?.uid
            ?: throw IllegalStateException("User not logged in")

        try {
            val randomId = UUID.randomUUID().toString()
            firestore.collection("users")
                .document(currentUserId)
                .collection("coupons")
                .document(randomId)
                .set(coupon)
                .await()

        } catch (e: Exception) {
            throw Exception("Failed to save coupon: ${e.message}", e)
        }
    }

    override suspend fun getCoupons(): List<CouponModel> {
        val currentUserId = auth.currentUser?.uid
            ?: throw IllegalStateException("User not logged in")

        return try {
            val snapshot = firestore.collection("users")
                .document(currentUserId)
                .collection("coupons")
                .get()
                .await()

            snapshot.documents.mapNotNull { it.toObject(CouponModel::class.java) }
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun saveDiscountCoupon(coupon: DiscountCouponModel) {
        val currentUserId = auth.currentUser?.uid
            ?: throw IllegalStateException("User not logged in")

        try {
            val randomId = UUID.randomUUID().toString()
            firestore.collection("users")
                .document(currentUserId)
                .collection("discountCoupons")
                .document(randomId)
                .set(coupon)
                .await()

        } catch (e: Exception) {
            throw Exception("Failed to save discount coupon: ${e.message}", e)
        }
    }

    override suspend fun getDiscountCoupons(): List<DiscountCouponModel> {
        val currentUserId = auth.currentUser?.uid
            ?: throw IllegalStateException("User not logged in")


        return try {
            val snapshot = firestore.collection("users")
                .document(currentUserId)
                .collection("discountCoupons")
                .get()
                .await()

            val coupons = snapshot.documents.mapNotNull { document ->
                val coupon = document.toObject(DiscountCouponModel::class.java)
                coupon?.copy(
                    expirationDate = document.getTimestamp("expirationDate")?.toDate() ?: Date()
                )
            }

            coupons.forEach { coupon ->
            }

            val activeCoupons = coupons.filter { it.active == true }

            if (activeCoupons.isEmpty()) {
            } else {
                activeCoupons.forEach { coupon ->
                }
            }

            activeCoupons
        } catch (e: Exception) {
            emptyList()
        }
    }



    override suspend fun updateDiscountCoupon(couponId: String) {
        val currentUserId = auth.currentUser?.uid
            ?: throw IllegalStateException("User not logged in")

        try {
            val querySnapshot = firestore.collection("users")
                .document(currentUserId)
                .collection("discountCoupons")
                .whereEqualTo("id", couponId)
                .get()
                .await()

            val document = querySnapshot.documents.firstOrNull()
            if (document != null) {
                document.reference.update("active", false).await()

            } else {
                throw Exception("Coupon document with id=$couponId not found")
            }        } catch (e: Exception) {
            throw Exception("Failed to update discount coupon: ${e.message}", e)
        }
    }

}