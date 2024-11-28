package com.example.gestordeinventario.repository.dataclasses

import com.google.firebase.firestore.DocumentReference

data class StudentDataClass(val name: String? = null, val padron: String? = null, val pendingDevolutions: List<DocumentReference>? = null, val authId: String? = null):RepositoryDataClass()
