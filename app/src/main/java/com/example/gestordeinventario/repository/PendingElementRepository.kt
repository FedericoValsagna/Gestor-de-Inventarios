package com.example.gestordeinventario.repository

import android.util.Log
import com.example.gestordeinventario.model.Element
import com.example.gestordeinventario.model.PendingElement
import com.example.gestordeinventario.repository.dataclasses.ElementDataClass
import com.example.gestordeinventario.repository.dataclasses.PendingElementDataClass
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class PendingElementRepository {
    suspend fun getAll(): List<PendingElement> {
        return try {
            Firebase.firestore.collection("pendingElement").get().await().documents.mapNotNull { snapshot ->
                val dataClass = snapshot.toObject(PendingElementDataClass::class.java)
                instance(dataClass)
            }
        } catch(e: Exception) {
            Log.i("REPOTAG", e.toString())
            emptyList()
        }
    }
    private suspend fun get(documentReference: DocumentReference) : PendingElement? {
        val path = documentReference.path
        return instance(Firebase.firestore.document(path).get().await().toObject<PendingElementDataClass>())
    }
    suspend fun getSome(documentReference: List<DocumentReference>?): List<PendingElement>? {
        documentReference ?: return null
        val result = ArrayList<PendingElement>()
        for (document in documentReference) {
            val x = this.get(document) ?: continue
            result.add(x)
        }
        if (result.isEmpty()) {
            return null
        }
        return result
    }
    private suspend fun instance(dataClass: PendingElementDataClass?): PendingElement? {
        dataClass ?: return null
        dataClass.element ?: return null
        dataClass.quantity ?: return null
        dataClass.devolutionDate ?: return null
        val element = ElementRepository().get(dataClass.element) ?: return null

        return PendingElement(dataClass.quantity, element, dataClass.devolutionDate)
    }
}