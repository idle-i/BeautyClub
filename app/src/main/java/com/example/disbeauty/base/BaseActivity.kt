package com.example.disbeauty.base

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.example.disbeauty.R
import com.google.android.material.snackbar.Snackbar

open class BaseActivity : AppCompatActivity() {

    fun startActivity(activityClass: Class<*>) {
        startActivity(
            Intent(this, activityClass)
        )

        overridePendingTransition(
            R.anim.fade_in,
            R.anim.fade_out
        )
    }

    fun startActivityWithFinish(activityClass: Class<*>, affinity: Boolean = false) {
        startActivity(activityClass)

        if (affinity) finishAffinity()
        else finish()

        overridePendingTransition(
            R.anim.fade_in,
            R.anim.fade_out
        )
    }

    override fun startActivity(intent: Intent?) {
        super.startActivity(intent)

        overridePendingTransition(
            R.anim.fade_in,
            R.anim.fade_out
        )
    }

    override fun finish() {
        super.finish()

        overridePendingTransition(
            R.anim.fade_in,
            R.anim.fade_out
        )
    }

    override fun finishAffinity() {
        super.finishAffinity()

        overridePendingTransition(
            R.anim.fade_in,
            R.anim.fade_out
        )
    }

    fun showSnackBarMessage(message: String) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT).apply {
            setAction(getString(R.string.stringOK)) {
                dismiss()
            }

            show()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()

        overridePendingTransition(
            R.anim.fade_in,
            R.anim.fade_out
        )
    }
}