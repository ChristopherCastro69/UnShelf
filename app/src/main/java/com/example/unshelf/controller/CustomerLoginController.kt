// CustomerLoginController.kt
import android.content.Context
import android.content.Intent
import com.example.unshelf.MainActivity
import com.example.unshelf.model.authentication.LoginAuthenticationManager
import com.example.unshelf.view.authentication.CustomerLoginView
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

class CustomerLoginController(private val context: Context, private val view: CustomerLoginView, private val model: LoginAuthenticationManager) :
    CustomerLoginView.LoginListener {

    fun init() {
        view.setLoginListener(this)
        view.initViews()
    }

    override fun onLoginClicked(email: String, password: String) {
        view.changeInProgress(true)
        model.loginUser(email, password, OnCompleteListener { task: Task<AuthResult?> ->
            if (task.isSuccessful) {
                // Login is successful
                val firebaseAuth = model.firebaseAuth
                if (firebaseAuth.currentUser!!.isEmailVerified) {
                    // go to main activity
                    val intent = Intent(context, MainActivity::class.java)
                    context.startActivity(intent)
                } else {
                    view.showToast("Email not verified, Please verify your email.")
                    view.changeInProgress(false)
                }
            } else {
                view.showToast(task.exception!!.localizedMessage)
                view.changeInProgress(false)
            }
        })
    }
}
