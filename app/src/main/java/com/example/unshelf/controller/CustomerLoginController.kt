// CustomerLoginController.kt
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.unshelf.R
import com.example.unshelf.view.MainActivity
import com.example.unshelf.model.authentication.LoginAuthenticationManager
import com.example.unshelf.model.entities.Customer
import com.example.unshelf.view.BuyerBottomNav.ui.MainNavigationActivityBuyer
import com.example.unshelf.view.authentication.CustomerLoginView
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore

class

CustomerLoginController(private val context: Context, private val view: CustomerLoginView, private val model: LoginAuthenticationManager) :
    CustomerLoginView.LoginListener {

    companion object {
        const val GOOGLE_SIGN_IN_REQUEST = 1001 // Arbitrary request code
    }
    fun init() {
        view.setLoginListener(this)
        view.changeInProgress(inProgress = false)
        view.initViews()
    }

    override fun onLoginClicked(email: String, password: String) {
        view.changeInProgress(true)
        model.loginUser(email, password, OnCompleteListener { task: Task<AuthResult?> ->
            if (task.isSuccessful) {
                // Login is successful
                val firebaseAuth = model.firebaseAuth
                if (firebaseAuth.currentUser!!.isEmailVerified) {
                    // Check if the user is registered as a customer in Firestore
                    checkIfRegisteredAsCustomer(firebaseAuth.currentUser?.uid ?: "")
                } else {
                    view.showToast("Email not verified, Please verify your email.")
                    view.changeInProgress(false)
                }
            } else {
                view.showToast("Invalid email or password")
                view.changeInProgress(false)
            }
        })
    }

    private fun checkIfRegisteredAsCustomer(userId: String) {
        val firestore = FirebaseFirestore.getInstance()
        firestore.collection("customers").document(userId).get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    // User is registered as a customer, proceed to MainNavigationActivityBuyer
                    val intent = Intent(context, MainNavigationActivityBuyer::class.java)
                    context.startActivity(intent)
                } else {
                    // User is not registered as a customer
                    view.showToast("You are not registered as a customer. Please register.")
                    // Redirect to registration screen (Customer_Register class)
                    // Replace the next line with the code to navigate to the registration screen
                    // val intent = Intent(context, Customer_Register::class.java)
                    // context.startActivity(intent)
                    view.changeInProgress(false)
                }
            }
            .addOnFailureListener { e ->
                view.showToast("Failed to check customer registration: ${e.localizedMessage}")
                view.changeInProgress(false)
            }
    }
    // In CustomerLoginController
    override fun onGoogleLoginClicked() {
        initiateGoogleSignIn()
    }


    // Add a method to initiate Google Sign-In
    fun initiateGoogleSignIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        val googleSignInClient = GoogleSignIn.getClient(context, gso)
        val signInIntent = googleSignInClient.signInIntent
        // Start activity for result - handle this in the activity and call back to controller
        // Assuming you're in an Activity, otherwise handle accordingly
        (context as Activity).startActivityForResult(signInIntent, GOOGLE_SIGN_IN_REQUEST)
    }


    // Handle the Google Sign-In result
    fun handleGoogleSignInResult(data: Intent?) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        try {
            val account = task.getResult(ApiException::class.java)
            model.firebaseAuthWithGoogle(account.idToken!!, this::onGoogleSignInComplete)
        } catch (e: ApiException) {
            Log.e("Google Sign-In Error", "signInResult: failed code=" + e.statusCode)
            view.showToast("Google Sign-In failed: ${e.localizedMessage}")
        }
    }

    private fun onGoogleSignInComplete(success: Boolean, message: String) {
        if (success) {
            // Navigate to main activity or buyer's dashboard
            val intent = Intent(context, MainNavigationActivityBuyer::class.java)
            context.startActivity(intent)
        } else {
            view.showToast(message)
        }
    }
}
