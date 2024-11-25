package com.example.gestordeinventario.repository

import android.util.Log
import com.example.gestordeinventario.model.Professor
import com.example.gestordeinventario.model.Student
import com.example.gestordeinventario.repository.dataclasses.ProfessorDataClass
import com.example.gestordeinventario.repository.dataclasses.StudentDataClass
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class ProfessorRepository {
    suspend fun getAll(): List<Professor> {
        return try {
            Firebase.firestore.collection("professors").get().await().documents.mapNotNull { snapshot ->
                val dataClass = snapshot.toObject(ProfessorDataClass::class.java)
                instance(dataClass)
            }
        } catch(e: Exception) {
            Log.i("REPOTAG", e.toString())
            emptyList()
        }
    }
    fun save(professor: Professor) {
        Firebase.firestore.collection("professors").document(professor.padron).set(professor)
    }
    suspend fun getByAuthId(authId: String): Professor? {
        val professors = this.getAll()
        professors.forEach { professor ->
            if (professor.authId == authId) {
                return professor
            }
        }
        return null
    }
    private fun instance(dataClass: ProfessorDataClass?): Professor? {
        dataClass ?: return null
        dataClass.name ?: return null
        dataClass.padron ?: return null
        dataClass.authId ?: return null
        return Professor(dataClass.name, dataClass.padron, dataClass.authId)
    }

}