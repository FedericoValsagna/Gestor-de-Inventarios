package com.example.gestordeinventario.repository.dataclasses

import com.google.firebase.firestore.DocumentReference
import org.w3c.dom.Document

data class ProviderDataClass(val name: String? = null, val elements: List<DocumentReference>? = null): RepositoryDataClass()
