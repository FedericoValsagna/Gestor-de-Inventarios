package com.example.gestordeinventario.model

import com.google.firebase.firestore.DocumentReference
import java.util.Date

class PendingElement(val quantity: Int, val element: Element, val devolutionDate: Date, var reference: DocumentReference? = null) {

    fun resolve(){
        element.add(quantity)
    }
}