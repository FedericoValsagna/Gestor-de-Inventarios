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

class PendingElementRepository: Repository<PendingElementDataClass>() {
    override val documentPath = "pendingElement"
    suspend fun getAll(): List<PendingElement> {
        return ArrayList(this.internalGetAll<PendingElementDataClass>().mapNotNull { item -> instance(item) })
    }
    private suspend fun get(documentReference: DocumentReference) : PendingElement? {
        val path = documentReference.path
        return instance(Firebase.firestore.document(path).get().await().toObject<PendingElementDataClass>())
    }
    suspend fun getSome(documentReference: List<DocumentReference>?): List<PendingElement>? {
        return documentReference?.mapNotNull { this.get(it) }?.takeIf { it.isNotEmpty() }
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