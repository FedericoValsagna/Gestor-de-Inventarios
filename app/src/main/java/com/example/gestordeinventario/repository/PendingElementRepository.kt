package com.example.gestordeinventario.repository

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
        val x = documentReference?.mapNotNull{ ref ->
            this.get(ref)
        }
        return x?.let { ArrayList(it) }
    }
    suspend fun save(pendingElement: PendingElement, student: Student) {
        val ref = this.referenceFrom(pendingElement, student)
        val dataClass = PendingElementDataClass(pendingElement.quantity, pendingElement.element.reference, pendingElement.devolutionDate)

        Firebase.firestore.collection("pendingElement").document(ref).set(dataClass).await()

        val reference = this.getReference(documentPath, ref)
        pendingElement.reference = reference
        student.addPendingElement(pendingElement)
        StudentRepository().save(student)
    }
    private suspend fun instance(dataClass: PendingElementDataClass?, reference: DocumentReference): PendingElement? {
        dataClass ?: return null
        dataClass.element ?: return null
        dataClass.quantity ?: return null
        dataClass.devolutionDate ?: return null
        val element = ElementRepository().get(dataClass.element) ?: return null

        return PendingElement(dataClass.quantity, element, dataClass.devolutionDate, reference)
    }
    private fun referenceFrom(pendingElement: PendingElement, student: Student): String{
        return "${student.name}_${pendingElement.element.name}_${pendingElement.devolutionDate}"
    }
}