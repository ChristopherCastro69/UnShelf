package com.example.unshelf.model.authenticationTest;

import android.content.Context;
import android.widget.Toast;

public class Utility { // This utility displays a text only, this can be moved to the view
    public static void showToast(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
