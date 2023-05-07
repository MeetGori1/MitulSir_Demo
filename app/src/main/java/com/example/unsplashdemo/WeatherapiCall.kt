package com.example.unsplashdemo

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.telephony.SmsManager
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.work.*
import com.example.unsplashdemo.databinding.ActivityWeatherapiCallBinding
import com.example.unsplashdemo.model.WeatherDataClass
import com.example.unsplashdemo.retrofit.retrofitBuilder
import com.example.unsplashdemo.worker.get.apiWorkerService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.TimeUnit
import javax.mail.*
import javax.mail.internet.AddressException
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage


class WeatherapiCall : AppCompatActivity() {
    lateinit var binding: ActivityWeatherapiCallBinding
    val key: String = "6e36bca08bf7e7708823224d3772beb7"
    val city: String = "Ahmedabad"
    var phoneNumber = ""
    var message = "Air Quality"
    lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>
    var isReadPermissionGranted: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityWeatherapiCallBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        getTemprature()
        getACallinBg()
        permissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
                isReadPermissionGranted =
                    permissions[Manifest.permission.SEND_SMS] ?: isReadPermissionGranted
            }
        reqPremission()

        binding.btnSendSms.setOnClickListener {
            if (binding.edtContactNum.text.isNullOrEmpty()) {
                Toast.makeText(this, "Please Enter Contact Number", Toast.LENGTH_SHORT).show()
            } else if (binding.edtContactNum.text!!.length < 10) {
                Toast.makeText(this, "Enter Valid Contract Number", Toast.LENGTH_SHORT).show()
            } else {
                phoneNumber = binding.edtContactNum.text.toString()
                sendSms(message)
                sendMail()
            }
        }


//        val mail = SendMail("meet.devstree@gmail.coom", "warszkyujnnnvews",
//            "jal.devstree@gmail.com",
//            "Testing Email Sending",
//            "Yes, it's working well\nI will use it always.")
//        mail.execute()

//        MaildroidX.Builder()
//            .smtp("smtp.mailtrap.io")
//            .smtpUsername("meet")
//            .smtpPassword("warszkyujnnnvews")
//            .port("465")
//            .type(MaildroidXType.PLAIN)
//            .to("jal.devstree@gmail.com")
//            .from("meet.devstree@gmail.com")
//            .subject("water")
//            .body("hello water")
//            .onCompleteCallback(object : MaildroidX.onCompleteCallback{
//                override val timeout: Long = 3000
//                override fun onSuccess() {
//                    Log.d("MaildroidX",  "SUCCESS")
//                }
//                override fun onFail(errorMessage: String) {
//                    Log.d("MaildroidX",  "FAIL")
//                }
//            })
//            .mail()

    }

    fun getTemprature() {
        retrofitBuilder.WeatherApi.getWeatherData("23.065", "24.123", key)
            .enqueue(object : Callback<WeatherDataClass?> {
                override fun onResponse(
                    call: Call<WeatherDataClass?>,
                    response: Response<WeatherDataClass?>,
                ) {
                    var responseBody = response.body()
                    if (responseBody != null) {
                        binding.txtTemprature.text =
                            "CO :${responseBody.list[0].components?.co.toString()}"
                        binding.txtTemprature1.text =
                            "NO :${responseBody.list[0].components?.no.toString()}"
                        binding.txtTemprature2.text =
                            "NH3 :${responseBody.list[0].components?.nh3.toString()}"
                        binding.txtTemprature3.text =
                            "NO2 :${responseBody.list[0].components?.no2.toString()}"
                        binding.txtTemprature4.text =
                            "NO3 :${responseBody.list[0].components?.o3.toString()}"
                        binding.txtTemprature5.text =
                            "PM2.5 :${responseBody.list[0].components?.pm25.toString()}"
                        binding.txtTemprature6.text =
                            "PM10 :${responseBody.list[0].components?.pm10.toString()}"
                        binding.aqi.text =
                            "Aqi :${responseBody.list[0].main?.aqi.toString()}"

                        message = "Here is  a air quality data of your area " +
                                "\n CO :${responseBody.list[0].components?.co.toString()}" +
                                "\n NO :${responseBody.list[0].components?.no.toString()}" +
                                "\n NH3 :${responseBody.list[0].components?.nh3.toString()}" +
                                "\n NO2 :${responseBody.list[0].components?.no2.toString()}" +
                                "\n NO3 :${responseBody.list[0].components?.o3.toString()}" +
                                "\n PM2.5 :${responseBody.list[0].components?.pm25.toString()}" +
                                "\n PM10 :${responseBody.list[0].components?.pm10.toString()}" +
                                "\n SO2 :${responseBody.list[0].components?.so2.toString()}" +
                                "\n Aqi :${responseBody.list[0].main?.aqi.toString()}"
                        Log.e("message", message)
                    }
                }

                override fun onFailure(call: Call<WeatherDataClass?>, t: Throwable) {
                    Log.e("Api call Failed", "Api call Failed")
                }
            })

    }

    fun reqPremission() {
        isReadPermissionGranted = ContextCompat.checkSelfPermission(this,
            Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED

        val permissionREq: MutableList<String> = ArrayList()
        if (!isReadPermissionGranted) {
            permissionREq.add(Manifest.permission.SEND_SMS)
        }
        if (permissionREq.isNotEmpty()) {
            permissionLauncher.launch(permissionREq.toTypedArray())
        }
    }

    fun sendMail() {
        try {
            val stringSenderEmail = "meet.devstree@gmail.com"
            val stringReceiverEmail = "jal.devstree@gmail.com"
            val stringPasswordSenderEmail = "warszkyujnnnvews"
            val stringHost = "smtp.gmail.com"
            val properties = System.getProperties()
            properties["mail.smtp.host"] = stringHost
            properties["mail.smtp.port"] = "465"
            properties["mail.smtp.ssl.enable"] = "true"
            properties["mail.smtp.auth"] = "true"
            val session = Session.getInstance(properties, object : Authenticator() {
                override fun getPasswordAuthentication(): PasswordAuthentication {
                    return PasswordAuthentication(stringSenderEmail, stringPasswordSenderEmail)
                }
            })
            val mimeMessage = MimeMessage(session)
            mimeMessage.addRecipient(Message.RecipientType.TO, InternetAddress(stringReceiverEmail))
            mimeMessage.subject = "Subject: Android App email"
            mimeMessage.setText("Hello Programmer, \n\nProgrammer World has sent you this 2nd email. \n\n Cheers!\nProgrammer World")
            val thread = Thread {
                try {
                    Transport.send(mimeMessage)
                } catch (e: MessagingException) {
                    e.printStackTrace()
                }
            }
            thread.start()
        } catch (e: AddressException) {
            e.printStackTrace()
        } catch (e: MessagingException) {
            e.printStackTrace()
        }
    }

    fun sendSms(message: String) {
        try {
            val smsManager: SmsManager
            if (Build.VERSION.SDK_INT >= 23) {
                smsManager = this.getSystemService(SmsManager::class.java)
            } else {
                smsManager = SmsManager.getDefault()
            }
            smsManager.sendTextMessage(phoneNumber, null, message, null, null)

            Toast.makeText(applicationContext, "Message Sent", Toast.LENGTH_LONG).show()

        } catch (e: Exception) {
            Log.e("gh", e.message.toString())
        }
    }

     fun getACallinBg(){
      val constraints=Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        val workRequest= PeriodicWorkRequest.Builder(apiWorkerService::class.java,15,TimeUnit.MINUTES)
            .setConstraints(constraints).build()
        WorkManager.getInstance(this).enqueue(workRequest)
    }
}