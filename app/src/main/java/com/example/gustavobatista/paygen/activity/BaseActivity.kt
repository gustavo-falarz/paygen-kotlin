package com.example.gustavobatista.paygen.activity


import android.app.Activity
import android.app.FragmentManager
import android.app.FragmentTransaction
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.crashlytics.android.Crashlytics
import com.example.gustavobatista.paygen.R
import com.example.gustavobatista.paygen.widget.Progress
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.alert
import org.jetbrains.anko.yesButton


/**
 * Created by Gustavo on 10/18/2017.
 */

open class BaseActivity : AppCompatActivity() {

    private val tag = "BaseActivity"
    private var mProgress: Progress? = null


    fun <T> Observable<T>.applySchedulers(): Observable<T> {
        return subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread())
    }


    fun setupToolbar(title: Int){
        setupToolbar(getString(title))
    }

    fun setupToolbar(title: String) {
        val toolbar = supportActionBar
        when {
            toolbar != null -> {
                toolbar.setTitle(title)
            }
            else -> Log.d(tag, "Toolbar not found, check if you have it in your layout " +
                    "or if setContentView() was called.")
        }
    }

    fun setupActionBar() {
        val ab = supportActionBar
        ab?.setDisplayHomeAsUpEnabled(true)
        ab?.setHomeButtonEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun handleException(exception: Throwable) {
        exception.printStackTrace()
        Crashlytics.logException(exception)
        exception.message?.let {
            alert(it, getString(R.string.error_title))
            { yesButton { } }.show()
        }
    }

    fun showWarning(message: Int) {
        showWarning(getString(message))
    }

    fun showWarning(message: String) {
        alert(message, getString(R.string.error_title)) { yesButton { } }.show()
    }

    fun showMessage(message: String) {
        alert(message, getString(R.string.title_success)) { yesButton { } }.show()
    }

    private fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    protected fun showProgress() {
        if (mProgress == null) {
            mProgress = Progress(this)
        }
        mProgress!!.show()
    }

    protected fun closeProgress() {
        if (mProgress != null && mProgress!!.isShowing) {
            mProgress!!.dismiss()
        }
    }

    inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> Unit) {
        val fragmentTransaction = beginTransaction()
        fragmentTransaction.func()
        fragmentTransaction.commit()
    }
}
