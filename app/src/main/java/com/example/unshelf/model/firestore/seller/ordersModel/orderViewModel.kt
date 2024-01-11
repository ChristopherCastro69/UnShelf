package com.example.unshelf.model.firestore.seller.ordersModel


import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unshelf.model.checkout.LineItem
import com.example.unshelf.model.checkout.OrderLineItem
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.example.unshelf.model.entities.Order
import com.example.unshelf.model.entities.Product
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.Date

