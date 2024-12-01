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
        return ArrayList(this.internalGetAll<ElementDataClass>().mapNotNull { (item, reference) -> instance(item, reference) })
    }
    suspend fun get(reference: DocumentReference) : Element? {
        val obj = Firebase.firestore.document(reference.path).get().await()
        Log.d("REPOTAG", "DATACLASS: ${obj.toObject<ElementDataClass>()} ||| REF: ${obj.reference}")
        return instance(obj.toObject<ElementDataClass>(), obj.reference)
    }
    private fun instance(dataClass: ElementDataClass?, reference: DocumentReference): Element? {
        dataClass ?: return null
        dataClass.totalQuantity ?: return null
        dataClass.name ?: return null
        return Element(dataClass.name, dataClass.totalQuantity,reference)
    }
}