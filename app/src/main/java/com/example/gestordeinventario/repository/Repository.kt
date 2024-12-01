package com.example.gestordeinventario.repository

import android.util.Log
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

abstract class Repository<RepositoryDataClass: Any> {
    abstract val documentPath: String

    suspend inline fun <reified RepositoryDataClass: Any> internalGetAll(): List<Pair<RepositoryDataClass, DocumentReference>> {
        return try {
            Firebase.firestore.collection(documentPath).get().await().documents.mapNotNull { snapshot ->
                snapshot.toObject(RepositoryDataClass::class.java)?.let { obj ->
                    Pair(obj, snapshot.reference)
                }
            }
        } catch(e: Exception) {
            Log.i("REPOTAG", e.toString())
            emptyList()
        }
    }
    suspend fun getReference(collection: String, path: String): DocumentReference {
        return Firebase.firestore.collection(collection).document(path).get().await().reference
    }
}