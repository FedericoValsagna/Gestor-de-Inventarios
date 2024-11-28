package com.example.gestordeinventario.repository

import android.util.Log
import com.example.gestordeinventario.model.PendingElement
import com.example.gestordeinventario.repository.dataclasses.PendingElementDataClass
import com.example.gestordeinventario.repository.dataclasses.RepositoryDataClass
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

abstract class Repository<RepositoryDataClass: Any> {
    abstract val documentPath: String

    suspend inline fun <reified RepositoryDataClass: Any> internalGetAll(): List<RepositoryDataClass> {
        return try {
            Firebase.firestore.collection(documentPath).get().await().documents.mapNotNull { snapshot ->
                val obj = snapshot.toObject(RepositoryDataClass::class.java)
                obj
            }
        } catch(e: Exception) {
            Log.i("REPOTAG", e.toString())
            emptyList()
        }
    }
}