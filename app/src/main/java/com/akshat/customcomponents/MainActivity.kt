package com.akshat.customcomponents

import android.os.Bundle
import android.os.Message
import android.view.View
import androidx.annotation.VisibleForTesting
import androidx.appcompat.app.AppCompatActivity
import com.akshat.customcomponents.databinding.ActivityMainBinding
import com.akshat.customcomponents.lifecycle.ClearOnDestroyProperty
import com.akshat.customcomponents.widgets.Alert

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpUI()
    }

    private fun setUpUI(){
        binding.button.setOnClickListener {
            setAlertMessageSuccess("Successfully clicked button", Alert.AlertType.ALERT_SUCCESS)
        }

        binding.buttonError.setOnClickListener {
            setAlertMessageError("Error Button clicked", Alert.AlertType.ALERT_ERROR)
        }

        binding.buttonInfo.setOnClickListener {
            setAlertMessageInfo("Info button clicked", Alert.AlertType.ALERT_INFO)
        }
    }

    private fun setAlertMessageInfo(s: String, alertInfo: Alert.AlertType) {
        binding.alertMessageInfo.apply {
            setAlertType(alertInfo)
            visibility = View.VISIBLE
            setText(s)
        }
    }

    private fun setAlertMessageError(s: String, alertError: Alert.AlertType) {
        binding.alertMessageError.apply {
            setAlertType(alertError)
            visibility = View.VISIBLE
            setText(s)
        }
    }

    @VisibleForTesting
    internal fun setAlertMessageSuccess(message: String, alertType: Alert.AlertType = Alert.AlertType.ALERT_SUCCESS){
        binding.alertMessage.apply {
            setAlertType(alertType)
            visibility = View.VISIBLE
            setText(message)
        }
    }


}