package com.example.gestordeinventario.model

class Student(val name: String, val padron: String, val pendingDevolutions: ArrayList<PendingElement>, val authId: String) {
    fun addPendingElement(pendingElement: PendingElement) {
        pendingDevolutions.add(pendingElement)
    }
}
