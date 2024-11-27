package com.example.gestordeinventario.repository

import android.util.Log
import com.example.gestordeinventario.model.Student
import com.example.gestordeinventario.repository.dataclasses.PendingElementDataClass
import com.example.gestordeinventario.repository.dataclasses.StudentDataClass
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class StudentRepository: Repository<StudentDataClass>() {
    override val documentPath = "users"
    suspend fun getAll(): List<Student> {
        return ArrayList(this.internalGetAll<StudentDataClass>().mapNotNull { item -> instance(item) })
    }
    suspend fun getById(id: String): Student? {
        return instance(Firebase.firestore.document("users/$id").get().await().toObject<StudentDataClass>())
    }

    suspend fun getByAuthId(authId: String): Student? {
        val students = this.getAll()
        students.forEach { student ->
            if (student.authId == authId) {
                return student
            }
        }
        return null
    }
    fun save(student: Student) {
        Firebase.firestore.collection("users").document(student.padron).set(student)
    }

    private suspend fun instance(dataClass: StudentDataClass?): Student? {
        dataClass ?: return null
        dataClass.name ?: return null
        dataClass.padron ?: return null
        dataClass.authId ?: return null
        val pendingDevolutions = PendingElementRepository().getSome(dataClass.pendingDevolutions) ?: ArrayList()
        return Student(dataClass.name, dataClass.padron, pendingDevolutions, dataClass.authId)
    }
}