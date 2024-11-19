package com.example.gestordeinventario.model

import com.google.firebase.firestore.DocumentReference

class Student(val name: String = "", val padron: String? = null, val pendingDevolutions: List<DocumentReference>? = null) {

}
