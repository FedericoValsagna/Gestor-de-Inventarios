package com.example.gestordeinventario.model

import com.google.firebase.firestore.DocumentReference
import java.util.Date

class PendingElement(val quantity: Int, val element: Element, val devolutionDate: Date, private var isCurrent: Boolean = false, var reference: DocumentReference? = null) {

    fun resolve(){
        isCurrent = true
        element.add(quantity)
    }
    fun isSolved(): Boolean{
        return isCurrent
    }
}