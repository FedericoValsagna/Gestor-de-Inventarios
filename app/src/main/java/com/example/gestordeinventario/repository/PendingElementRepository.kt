package com.example.gestordeinventario.repository

import android.util.Log
import com.example.gestordeinventario.model.Element
import com.example.gestordeinventario.model.PendingElement
import com.example.gestordeinventario.model.Student
import com.example.gestordeinventario.repository.dataclasses.PendingElementDataClass
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class PendingElementRepository: Repository<PendingElementDataClass>() {
    override val documentPath = "pendingElement"
    suspend fun getAll(): List<PendingElement> {
        return ArrayList(this.internalGetAll<PendingElementDataClass>().mapNotNull { (item, reference) -> instance(item, reference) })
    }
    private suspend fun get(documentReference: DocumentReference) : PendingElement? {
        val path = documentReference.path
        val obj = Firebase.firestore.document(path).get().await()
        return instance(obj.toObject<PendingElementDataClass>(), obj.reference)
    }
    suspend fun getSome(documentReference: List<DocumentReference>?): ArrayList<PendingElement>? {
        Log.d("REPOTAG", "REFS: $documentReference")
        val x = documentReference?.mapNotNull{ ref ->
            this.get(ref)
        }
        Log.d("REPOTAG", "RESULT FROM REFS: $x")
        return x?.let { ArrayList(it) }
//        return documentReference?.mapNotNull { this.get(it) }?.takeIf { it.isNotEmpty() }
//            ?.let { ArrayList(it) }
    }
    suspend fun save(pendingElement: PendingElement, student: Student) {
        val ref = "${student.name}_${pendingElement.element.name}_${pendingElement.devolutionDate}"
        val dataClass = PendingElementDataClass(pendingElement.quantity, null, pendingElement.devolutionDate)
        Firebase.firestore.collection("pendingElement").document(ref).set(dataClass).await()
        val reference = Firebase.firestore.collection("pendingElement").document(ref).get().await().reference
        pendingElement.reference = reference
        student.addPendingElement(pendingElement)
        StudentRepository().save(student)
    }
    private suspend fun instance(dataClass: PendingElementDataClass?, reference: DocumentReference): PendingElement? {
        Log.d("REPOTAG", "Dataclass: $dataClass |||     Reference: $reference")
        dataClass ?: return null
        // dataClass.element ?: return null
        dataClass.quantity ?: return null
        dataClass.devolutionDate ?: return null
        // val element = ElementRepository().get(dataClass.element) ?: return null

        return PendingElement(dataClass.quantity, Element("Cable", 3), dataClass.devolutionDate, reference)
    }
}