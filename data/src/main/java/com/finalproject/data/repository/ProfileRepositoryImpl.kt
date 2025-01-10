    package com.finalproject.data.repository


    import android.net.Uri
    import android.util.Log
    import com.finalproject.domain.model.user.AddressModel
    import com.finalproject.domain.model.user.CardsModel
    import com.finalproject.domain.model.user.UserSettings
    import com.finalproject.domain.repository.ProfileRepository
    import com.google.firebase.auth.FirebaseAuth
    import com.google.firebase.firestore.FirebaseFirestore
    import com.google.firebase.storage.FirebaseStorage
    import kotlinx.coroutines.tasks.await
    import java.util.UUID

    class ProfileRepositoryImpl(
        private val firestore: FirebaseFirestore,
        private val firebaseAuth: FirebaseAuth,
        private val firebaseStorage: FirebaseStorage
    ) : ProfileRepository {

        private val usersCollection = "users"

        private fun currentUserId(): String {
            return firebaseAuth.currentUser?.uid ?: throw IllegalStateException("User is not logged in")
        }

        override suspend fun saveCard(card: CardsModel) {
            val userId = currentUserId()
            val cardId = UUID.randomUUID().toString()
            val cardData = mapOf(
                "cardId" to cardId,
                "cardName" to card.cardName,
                "cardHolderName" to card.cardHolderName,
                "cardNumber" to card.cardNumber,
                "cardExpiryDate" to card.cardExpiryDate,
                "cardCVV" to card.cardCVV
            )

            try {
                firestore.collection("users")
                    .document(userId)
                    .collection("credit_cards")
                    .document(cardId)
                    .set(cardData)
                    .await()

            } catch (e: Exception) {
                throw e
            }
        }



        override suspend fun getCards(): List<CardsModel> {
            val userId = currentUserId()
            return try {
                val snapshot = firestore.collection("users")
                    .document(userId)
                    .collection("credit_cards")
                    .get()
                    .await()

                snapshot.documents.map { doc ->
                    val data = doc.data ?: emptyMap<String, Any>()
                    CardsModel(
                        cardId = doc.id,
                        cardName = data["cardName"] as? String ?: "",
                        cardHolderName = data["cardHolderName"] as? String ?: "",
                        cardNumber = data["cardNumber"] as? String ?: "",
                        cardExpiryDate = data["cardExpiryDate"] as? String ?: "",
                        cardCVV = data["cardCVV"] as? String ?: ""
                    )
                }
            } catch (e: Exception) {
                emptyList()
            }
        }

        override suspend fun saveAddress(address: AddressModel) {
            val userId = currentUserId()
            val addressData = mapOf(
                "id" to address.id,
                "userNameSurname" to address.userNameSurname,
                "userTelephoneNumber" to address.userTelephoneNumber,
                "apartmentNo" to address.apartmentNo,
                "floor" to address.floor,
                "userAddress"  to address.userAddress,
                "homeNo" to address.homeNo,
                "addressType" to address.addressType,
                "addressDescription" to address.addressDescription,
                "latitude" to address.latitude,
                "longitude" to address.longitude
            )
            firestore.collection("users")
                .document(userId)
                .collection("addresses")
                .add(addressData)
                .addOnSuccessListener {
                }
                .addOnFailureListener { e ->
                }
        }


        override suspend fun getAddresses(): List<AddressModel> {
            val userId = currentUserId()
            return try {
                val snapshot = firestore.collection("users")
                    .document(userId)
                    .collection("addresses")
                    .get()
                    .await()


                snapshot.documents.forEach { doc ->
                }

                snapshot.documents.map { doc ->
                    val data = doc.data ?: emptyMap<String, Any>()
                    AddressModel(
                        id = doc.id,
                        userNameSurname = data["userNameSurname"] as? String ?: "",
                        userTelephoneNumber = data["userTelephoneNumber"] as? String ?: "",
                        apartmentNo = data["apartmentNo"] as? String ?: "",
                        floor = (data["floor"] as? Number)?.toInt() ?: 0,
                        userAddress = data["userAddress"] as? String ?: "",
                        homeNo = data["homeNo"] as? String ?: "",
                        addressType = (data["addressType"] as? Number)?.toInt() ?: 0,
                        addressDescription = data["addressDescription"] as? String ?: "",
                        latitude = data["latitude"] as? Double ?: 0.0,
                        longitude = data["longitude"] as? Double ?: 0.0
                    )
                }
            } catch (e: Exception) {
                emptyList()
            }
        }

        override suspend fun saveUserSettings(userSettings: UserSettings) {
            try {

                val currentUser = FirebaseAuth.getInstance().currentUser
                    ?: throw IllegalStateException("Current user not authenticated")

                val userUuid = currentUser.uid
                val email = currentUser.email

                val profilePhotoUrl = userSettings.profilePhotoUrl?.let { uri ->
                    if (uri.startsWith("http")) {
                        return@let uri
                    } else {
                        try {
                            val parsedUri = Uri.parse(uri)
                            val photoRef = firebaseStorage.reference.child("profile_photos/$userUuid.jpg")
                            photoRef.putFile(parsedUri).await()
                            val downloadUrl = photoRef.downloadUrl.await().toString()
                            downloadUrl
                        } catch (e: Exception) {
                            throw IllegalStateException("Error uploading profile photo: ${e.message}", e)
                        }
                    }
                }

                val updatedSettings = userSettings.copy(
                    userMail = email ?: userSettings.userMail,
                    profilePhotoUrl = profilePhotoUrl
                )

                firestore.collection("users")
                    .document(userUuid)
                    .collection("user_settings")
                    .document("userSettingsModel")
                    .set(updatedSettings)
                    .await()

            } catch (e: Exception) {
                throw e
            }
        }

        override suspend fun getUserSettings(): UserSettings? {
            return try {
                val currentUserId = FirebaseAuth.getInstance().currentUser?.uid

                if (currentUserId != null) {
                    val document = firestore.collection("users")
                        .document(currentUserId)
                        .collection("user_settings")
                        .document("userSettingsModel")
                        .get()
                        .await()

                    if (document.exists()) {
                        document.toObject(UserSettings::class.java)
                    } else {
                        null
                    }
                } else {
                    null
                }
            } catch (e: Exception) {
                null
            }
        }
    }