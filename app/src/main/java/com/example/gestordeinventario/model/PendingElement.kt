package com.example.gestordeinventario.model

import com.google.firebase.firestore.DocumentReference
import java.util.Date

data class PendingElement(val quantity: Int? = null, val element: DocumentReference? = null, val devolutionDate: Date? = null)
