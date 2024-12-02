package com.example.gestordeinventario.repository.dataclasses

import com.google.firebase.firestore.DocumentReference
import java.util.Date

data class PendingElementDataClass(val quantity: Int? = null, val element: DocumentReference? = null, val devolutionDate: Date? = null, val solved: Boolean? = null): RepositoryDataClass()
