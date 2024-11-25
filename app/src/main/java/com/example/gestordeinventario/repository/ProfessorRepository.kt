package com.example.gestordeinventario.repository

import com.example.gestordeinventario.model.Professor
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ProfessorRepository {
    fun save(professor: Professor) {
        Firebase.firestore.collection("professors").document(professor.padron).set(professor)
    }
}