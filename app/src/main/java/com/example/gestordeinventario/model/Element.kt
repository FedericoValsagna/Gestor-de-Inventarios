package com.example.gestordeinventario.model

import com.google.firebase.firestore.DocumentReference

data class Element(val name: String, var totalQuantity: Int, var reference: DocumentReference? = null) {
    fun request(quantity: Int): Boolean {
        if (quantity > totalQuantity){
            return false
        }
        totalQuantity -= quantity
        return true
    }
    fun add(quantity: Int) {
        totalQuantity += quantity
    }
}
