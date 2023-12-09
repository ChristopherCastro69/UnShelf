package com.example.unshelf.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.unshelf.R
import com.example.unshelf.controller.DataFetch.DataFetchController
import com.example.unshelf.helper.UI_Tester_Helper
import com.example.unshelf.view.AddressManager.AddressManager
import com.example.unshelf.view.BuyerBottomNav.main.BuyerAppNavigation
import com.example.unshelf.view.BuyerBottomNav.ui.MainNavigationActivityBuyer
import com.example.unshelf.view.authentication.Customer_Login
import com.example.unshelf.view.authentication.CustomerRegister
import com.example.unshelf.view.RestaurantNearMe.RestaurantsNearMe

import com.example.unshelf.view.product.product_main
import com.example.unshelf.view.Wallet.Balance
import com.example.unshelf.view.Wallet.CheckoutUI
import com.example.unshelf.controller.seller.ui.MainNavigationActivitySeller
import com.example.unshelf.view.marketplaceMain.marketplaceMain
import com.example.unshelf.view.StartUI.MainUI
import com.example.unshelf.view.productView.CartActivity
import com.example.unshelf.view.productView.ProductMainView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ui_tester)





        // BUTTONS
        // define the buttons here
        // IMPORTANT: always prefix your buttons with a test, e.g., testBtnName to distinguish that it is a test button. DO THIS in the ID definition as well.
        val testBtnMain = findViewById<Button>(R.id.testBtnMain)
        val testBtnNearMe = findViewById<Button>(R.id.testBtnNearMe)
        val testBtnLogin = findViewById<Button>(R.id.testBtnLogin);
        val testBtnRegister = findViewById<Button>(R.id.testBtnRegister);
        val testBtnPmain = findViewById<Button>(R.id.testBtnPMain);
        val testBtnCart = findViewById<Button>(R.id.testBtnCart);
        val testBtnWallet = findViewById<Button>(R.id.testBtnWallet);
        val testSellerScreen = findViewById<Button>(R.id.testSellerScreen);
        val testCheckoutScreen = findViewById<Button>(R.id.testCheckoutScreen);
        val testBtnMenu = findViewById<Button>(R.id.testBtnPMain2)
        val testBtnSetUserLoc = findViewById<Button>(R.id.testBtnSetUserLoc); // user address
        val testBtnBuyer = findViewById<Button>(R.id.testBtnBuyer)


        // HELPER FUNCTION: UI_Tester_Helper
        //! IMPORTANT: Just call the UI_Tester_Helper.UI_Test(intent, button, java.class) to
        UI_Tester_Helper.UI_Test(this, testBtnMain, MainUI::class.java);
        UI_Tester_Helper.UI_Test(this, testBtnNearMe, RestaurantsNearMe::class.java);
        UI_Tester_Helper.UI_Test(this, testBtnLogin, Customer_Login::class.java);
        UI_Tester_Helper.UI_Test(this, testBtnRegister, CustomerRegister::class.java);
        UI_Tester_Helper.UI_Test(this, testBtnPmain, product_main::class.java);
//        UI_Tester_Helper.UI_Test(this, testBtnPmain, cart::class.java);
        UI_Tester_Helper.UI_Test(this, testBtnCart, ProductMainView::class.java);
        UI_Tester_Helper.UI_Test(this, testBtnWallet, Balance::class.java);
        UI_Tester_Helper.UI_Test(this, testSellerScreen, MainNavigationActivitySeller::class.java);
        UI_Tester_Helper.UI_Test(this, testCheckoutScreen, CheckoutUI::class.java);
        UI_Tester_Helper.UI_Test(this, testBtnSetUserLoc, AddressManager::class.java);
        UI_Tester_Helper.UI_Test(this, testBtnMenu, marketplaceMain::class.java);
        UI_Tester_Helper.UI_Test(this, testBtnBuyer, MainNavigationActivityBuyer::class.java);

    }


}