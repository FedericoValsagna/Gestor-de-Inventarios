package com.example.gestordeinventario.model

import com.google.firebase.firestore.DocumentReference

data class Element(val name: String, val totalQuantity: Int, var reference: DocumentReference? = null)
