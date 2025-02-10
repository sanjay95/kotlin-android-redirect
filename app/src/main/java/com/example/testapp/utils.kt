package com.example.testapp

// import kotlinx.serialization.*
// import kotlinx.serialization.json.*
// import org.springframework.stereotype.Service
// import org.springframework.web.reactive.function.client.WebClient
import android.content.Context
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.security.Security
import okhttp3.*
// import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaType
// import okhttp3.OkHttpClient
// import okhttp3.Request
// import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
// import okhttp3.Response

// Security.addProvider(BouncyCastleProvider())

class IssuanceService() {

        fun startIssuance(userDID: String, credentialData: String, callback: (String) -> Unit) {
                val client = OkHttpClient()
                val updatedCredentialData = credentialData.replace("example", userDID)
                val mediaType = "application/json; charset=utf-8".toMediaType()
                val payload = updatedCredentialData
                val requestBody = payload.toRequestBody(mediaType)
                val request =
                        Request.Builder()
                                .url("https://aca-bank.vercel.app/api/issuance/start")
                                .post(requestBody)
                                .build()
                var responseString = ""

                client.newCall(request)
                        .enqueue(
                                object : Callback {
                                        override fun onResponse(call: Call, response: Response) {

                                                println("onResponse called")
                                                if (response.isSuccessful) {
                                                        val responseBody = response.body?.string()
                                                        println("Response: $responseBody")
                                                        responseString = responseBody ?: ""
                                                        callback(responseString)
                                                        response.body?.close()
                                                        
                                                } else {
                                                        println(
                                                                "API Call Error: ${response.code} ${response.message}"
                                                        )
                                                        callback("API Call Error: ${response.code} ${response.message}")
                                                }
                                        }

                                        override fun onFailure(call: Call, e: IOException) {
                                                println("onFailure called")
                                                println("Failure: ${e.message}")
                                                callback("Failure: ${e.message}")
                                        }
                                }
                        )

                // println("responseString: $responseString")
                //         return responseString
        }

        fun getJsonDataFromRaw(context: Context, fileName: String): String {
                val inputStream =
                        context.resources.openRawResource(
                                context.resources.getIdentifier(
                                        fileName,
                                        "raw",
                                        context.packageName
                                )
                        )
                val bufferedReader = BufferedReader(InputStreamReader(inputStream))
                return bufferedReader.use { it.readText() }
        }

        fun listSecurityProviders() {
                for (provider in Security.getProviders()) {
                        println("Provider: ${provider.name}")
                        for (service in provider.services) {
                                println("  Algorithm: ${service.algorithm}")
                        }
                }
        }

        fun requestBodyToString(requestBody: RequestBody): String {
                val buffer = okio.Buffer()
                requestBody.writeTo(buffer)
                return buffer.readUtf8()
        }
}
