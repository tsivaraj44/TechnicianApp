package com.bpositive.technician.utils

import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction


/**
 * The
 * @param fragment
 * is replaced with container id
 * @param fragmentId
 */
fun AppCompatActivity.replaceFragmentInActivity(fragment: Fragment, fragmentId: Int, tag: String) {
    supportFragmentManager.transact {
        if (TextUtils.isEmpty(tag))
            replace(fragmentId, fragment)
        else
            replace(fragmentId, fragment, tag)
    }
}


/**
 * Run the FragmentTransaction and then call commit
 */
inline fun FragmentManager.transact(action: FragmentTransaction.() -> Unit) {
    beginTransaction().apply {
        action()
    }.commit()
}
