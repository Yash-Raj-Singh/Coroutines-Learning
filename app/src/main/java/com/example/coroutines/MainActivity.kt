package com.example.coroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    val TAG = "MainAct"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


//        // LEARNED HOW THIS WILL MAKE A NEW THREAD WHOSE LIFE WILL BE
//        // AS LONG AS THE EXISTENCE OF MAIN THREAD
//        //WE CAN USE DELAY TO PAUSE OUR THREAD AND RESUME AFTER ASSIGNED TIME
//        GlobalScope.launch {
//            delay(5000L)
//            Log.d(TAG, "SAY HELLO TO COROUTINES! ${Thread.currentThread().name}")
//        }
//
//        Log.d(TAG, "Hi From Main Thread! ${Thread.currentThread().name}")
//
//        // A FUNCTION THAT CAN BE STARTED, RESUMED OR PAUSED IS CALLED SUSPEND FUNCTION
//        //DELAY IS ALSO A SUSPEND FUNCTION
//        // WE CAN'T CALL A SUSPEND FUNCTION OUT OF ANOTHER SUSPEND FUNCTION
//
//        GlobalScope.launch {
//            delay(5000L)
//            Log.d(TAG, "SAY A new HELLO TO COROUTINES! ${Thread.currentThread().name}")
//            // DELAY TIME OF BOTH FUNCTION CALLED BELOW WILL ADD UP
//            val x = callingSuspend()
//            val y = callingSuspend2()
//            Log.d(TAG, x)
//            Log.d(TAG, y)
//        }
//
//        // Sending Coroutines Context from One thread to other
//        GlobalScope.launch(Dispatchers.IO) {
//            Log.d(TAG, "DispatcherIO Thread ${Thread.currentThread().name}")
//            val v = callingSuspend()
//            withContext(Dispatchers.Main){
//                Log.d(TAG, "DispatcherMAIN Thread ${Thread.currentThread().name}")
//                tvText.text = v
//            }
//        }

        // runBlocking is used when we need to block our Main thread
        // works similar as GlobalScope.launch(Dispatchers.Main)
        // difference is runBlock blocks the main thread and
        // GlobalScope don't block it
        // Mainly used when we want to call a suspend function but also don't want
        // to use Coroutines in our application
        // Also used in unit testing
        // Not recommended for production applications
        // The below given run block code works same as Thread.sleep(1000L)
        // which also blocks our main thread
        // We can directly write a new launch{} inside runBlock without GlobalScope

        Log.d(TAG, "BEFORE RUNBLOCK")
        runBlocking {

            launch {
                delay(3000L)
                Log.d(TAG, "IO Coroutine 1")
            }
            launch {
                delay(3000L)
                Log.d(TAG, "IO Coroutine 2")
            }

            Log.d(TAG, "Started RUNBLOCK")
            delay(7000L)
            Log.d(TAG, "Ending RUNBLOCK")
        }
        Log.d(TAG, "AFTER RUNBLOCK")
    }
    suspend fun callingSuspend(): String{
        delay(2000L)
        return "Returned from a suspend function!"
    }
    suspend fun callingSuspend2(): String{
        delay(7000L)
        return "Returned from a suspend 2 function!"
    }
}