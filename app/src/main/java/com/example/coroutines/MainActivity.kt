package com.example.coroutines

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

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

//        Log.d(TAG, "BEFORE RUNBLOCK")
//        runBlocking {
//
//            launch {
//                delay(3000L)
//                Log.d(TAG, "IO Coroutine 1")
//            }
//            launch {
//                delay(3000L)
//                Log.d(TAG, "IO Coroutine 2")
//            }
//
//            Log.d(TAG, "Started RUNBLOCK")
//            delay(7000L)
//            Log.d(TAG, "Ending RUNBLOCK")
//        }
//        Log.d(TAG, "AFTER RUNBLOCK")


        // The GlobalScope.launch returns a job, So we can store them in a variable
        // Then we can wait for that job to finish by writing JOBNAME.join()
        // But this is a suspend function so either call it in a
        // GlobalScope.launch or in a runBlock

        //val job = GlobalScope.launch(Dispatchers.Default) {
//            repeat(3)
//            {
//                Log.d(TAG, "Coroutine is still working!")
//                delay(3000L)
//            }
        //NOW THIS WILL NOT GET CANCEL BY CANCEL FUNCTION AUTOMATICALLY
        //BECAUSE IT WAS BUSY IN CALCULATIONS AND LOOP THAT IT DIDN'T NOTICED IT
        //SO WE NEED TO MANUALLY CHECK IT BY USING isActive FUNCTION
        //withTimeout becomes useful if we need to cancel a function/program
        //if it take more time then assigned in withTimeout function

//            Log.d(TAG, "Long Calculations Starting!")
//            withTimeout(1500L)
//            {
//                for(i in 30..40)
//                {
//                    if(isActive) {
//                        Log.d(TAG, "For $i the value is : ${fibo(i)}")
//                    }
//                }
//            }
//
//            Log.d(TAG, "Long Calculations Ended!")
//        }

//        runBlocking {
//            // here we waited for our coroutine to get finished
////            job.join()
//            delay(5000L)
//            job.cancel()
//            Log.d(TAG,"Canceled The Job!")
//        }
        //}

        // If we have more than one suspend function in our coroutine,
        //they will be executed sequentially in order

//        GlobalScope.launch(Dispatchers.IO) {
//            //Showing how time delay of both functions would add up
//            // Cool function to measure time
//            val time = measureTimeMillis {
//
//                // To escape from the execution hell of one after another and
//                // adding up the time, we have Async Function
//                // It is similar to launch function, means it create a new thread
//                // to perform tasks parallel to each other, but it is very fast
//                // So we use .await() to let the task finish up completely.
//
//                val q1 = async { callingSuspend() }
//                val q2 = async { callingSuspend2() }
//                Log.d(TAG, "${q1.await()}")
//                Log.d(TAG, "${q2.await()}")
//            }
//            Log.d(TAG, "Time in total would be : $time ms")
//        }

        // Sometimes we don't want to use GlobalScope as we don't want
        // our coroutine to have a long life till the existence of application

        btnSecond.setOnClickListener{
            // Here even after the destruction of our main activity
            // the while loop will keep running because it is in Global Scope
            // and due to that, it is running in a new thread on parallel
            // it may cause memory leaks, So to stop that we can replace
            // GlobalScope with lifecycle Scope, whose scope it defined
            // till the life of that particular activity

//            GlobalScope.launch {
//                while(true)
//                {
//                    delay(1000L)
//                    Log.d(TAG, "Yeah, it's still up!")
//                }
//            }

            lifecycleScope.launch {
                while(true)
                {
                    delay(1000L)
                    Log.d(TAG, "Yeah, it's still up!")
                }
            }

            GlobalScope.launch {
                delay(5000L)
                Log.d(TAG, " Starting 2nd Activity ")
                Intent(this@MainActivity, Second_Activity::class.java).also {
                    startActivity(it)
                    finish()
                }
            }
        }


    }
    private fun fibo(n: Int): Long{
        return if(n == 0) 0
        else if(n == 1) 1
        else fibo(n-1) + fibo(n-2)
    }
    suspend fun callingSuspend(): String{
        delay(2000L)
        return "Returned from a suspend function!"
    }
    suspend fun callingSuspend2(): String{
        delay(2000L)
        return "Returned from a suspend 2 function!"
    }
}