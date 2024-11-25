package com.example.gestordeinventario.repository

import android.util.Log
import com.example.gestordeinventario.model.Element
import com.example.gestordeinventario.repository.dataclasses.ElementDataClass
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class ElementRepository {
    suspend fun getAll(): List<Element> {
        return try {
            Firebase.firestore.collection("element").get().await().documents.mapNotNull { snapshot ->
                val dataClass = snapshot.toObject(ElementDataClass::class.java)
                instance(dataClass)
            }
        } catch(e: Exception) {
            Log.i("REPOTAG", e.toString())
            emptyList()
        }
    }
    suspend fun get(documentReference: DocumentReference) : Element? {
        val path = documentReference.path
        return instance(Firebase.firestore.document(path).get().await().toObject<ElementDataClass>())
    }
    private fun instance(dataClass: ElementDataClass?): Element? {
        dataClass ?: return null
        dataClass.totalQuantity ?: return null
        dataClass.name ?: return null
        return Element(dataClass.name, dataClass.totalQuantity)
    }
}