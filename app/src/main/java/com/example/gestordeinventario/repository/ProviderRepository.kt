package com.example.gestordeinventario.repository

import com.example.gestordeinventario.model.Element
import com.example.gestordeinventario.model.Provider
import com.example.gestordeinventario.repository.dataclasses.ElementDataClass
import com.example.gestordeinventario.repository.dataclasses.ProviderDataClass
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class ProviderRepository: Repository<ProviderDataClass>() {

    override val documentPath = "provider"
    suspend fun getAll(): List<Provider> {
        return ArrayList(this.internalGetAll<ProviderDataClass>().mapNotNull { (item, _) -> instance(item) })
    }
    suspend fun get(reference: DocumentReference) : Provider? {
        val obj = Firebase.firestore.document(reference.path).get().await()
        return instance(obj.toObject<ProviderDataClass>())
    }
    suspend fun getByName(name: String): Provider? {
        return instance(Firebase.firestore.document("$documentPath/$name").get().await().toObject<ProviderDataClass>())
    }
    private suspend fun instance(dataClass: ProviderDataClass?): Provider? {
        dataClass ?: return null
        dataClass.name ?: return null
        dataClass.elements ?: return null
        val elements = ElementRepository().getSome(dataClass.elements) ?: ArrayList()
        return Provider(dataClass.name, elements)
    }
}