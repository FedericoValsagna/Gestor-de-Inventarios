package com.example.gestordeinventario.model

import com.google.firebase.firestore.DocumentReference
import java.util.Date

class PendingElement(var quantity: Int, val element: Element, var devolutionDate: Date)