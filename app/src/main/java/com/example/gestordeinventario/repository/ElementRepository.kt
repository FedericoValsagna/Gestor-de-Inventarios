package com.example.gestordeinventario.repository

import android.util.Log
import com.example.gestordeinventario.model.Element
import com.example.gestordeinventario.repository.dataclasses.ElementDataClass
import com.example.gestordeinventario.repository.dataclasses.StudentDataClass
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class ElementRepository: Repository<ElementDataClass>() {
    override val documentPath = "element"
    suspend fun getAll(): List<Element> {
        return ArrayList(this.internalGetAll<ElementDataClass>().mapNotNull { (item, _) -> instance(item) })
    }
    suspend fun get(documentReference: DocumentReference) : Element? {
        return instance(Firebase.firestore.document(documentReference.path).get().await().toObject<ElementDataClass>())
    }
    private fun instance(dataClass: ElementDataClass?): Element? {
        dataClass ?: return null
        dataClass.totalQuantity ?: return null
        dataClass.name ?: return null
        return Element(dataClass.name, dataClass.totalQuantity)
    }
}