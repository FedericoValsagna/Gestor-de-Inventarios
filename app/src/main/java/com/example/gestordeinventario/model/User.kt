package com.example.gestordeinventario.model

data class User(val name: String, val padron: String, val pendingDevolutions: List<PendingElement>)
