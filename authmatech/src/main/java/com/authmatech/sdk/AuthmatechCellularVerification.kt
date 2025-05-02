/*
 * MIT License
 * Copyright (C) 2025 Authmatech Limited. All rights reserved
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.authmatech.sdk

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.NonNull
import androidx.annotation.RequiresApi
import com.authmatech.sdk.network.CellularNetworkManager
import com.authmatech.sdk.network.NetworkManager
import org.json.JSONException
import org.json.JSONObject
import java.net.URL
import java.util.regex.Pattern

/**
 * AuthmatechSDK provides cellular-data-based identity verification through telecom header enrichment
 * and MSISDN detection.
 */
@RequiresApi(Build.VERSION_CODES.O)
class AuthmatechSDK private constructor(networkManager: CellularNetworkManager) {
    private val networkManager: NetworkManager = networkManager

    /**
     * Open a given url after forcing the data connectivity on the device
     *
     * @param url The url to be open over a data cellular connectivity.
     * @param debug A flag to include or not the url trace in the response
     * @return JSONObject containing the response with renamed fields according to Authmatech standards
     */
    fun openWithDataCellular(@NonNull url: URL, debug: Boolean): JSONObject {
        Log.d(TAG, "openWithDataCellular")
        val networkManager: NetworkManager = getCellularNetworkManager()
        val response = networkManager.openWithDataCellular(url, null, debug)

        // Transform the response to Authmatech format
        return transformResponse(response, debug)
    }

    /**
     * Open a given url after forcing the data connectivity on the device
     *
     * @param url The url to be open over a data cellular connectivity.
     * @param accessToken Optional Access Token to be added in the Authorization header (Bearer).
     * @param debug A flag to include or not the url trace in the response
     * @return JSONObject containing the response with renamed fields according to Authmatech standards
     */
    fun openWithDataCellularAndAccessToken(@NonNull url: URL, accessToken: String?, debug: Boolean): JSONObject {
        Log.d(TAG, "openWithDataCellularAndAccessToken")
        val networkManager: NetworkManager = getCellularNetworkManager()
        val response = networkManager.openWithDataCellular(url, accessToken, debug)

        // Transform the response to Authmatech format
        return transformResponse(response, debug)
    }

    /**
     * Get a simplified response from the given URL using data cellular connectivity.
     *
     * @param context The application context
     * @param url The URL to be opened over data cellular connectivity
     * @return AuthmatechResult containing the simplified response data
     */
    fun getSimplifiedResponse(context: Context, url: String): AuthmatechResult {
        // Initialize SDK if not already initialized
        initializeSdk(context)

        // Call the existing method
        val response = openWithDataCellular(URL(url), true)

        // Extract values from the response
        var authmatechCode = ""
        var mnoid = ""
        var errorCode = "0"
        var heId = ""
        var errorDesc = "AUTHMATECH Code successfully fetched"

        // Check if there's an error
        if (response.has("error")) {
            errorCode = "-1"
            errorDesc = response.optString("error_description", "Unknown error")
        } else {
            // Try to get values from response_body
            val responseBody = response.optJSONObject("response_body")
            if (responseBody != null) {
                authmatechCode = responseBody.optString("authmatechCode", "")
                mnoid = responseBody.optString("MNOID", "")
                errorCode = responseBody.optString("errorCode", "0")
                heId = responseBody.optString("heId", "")
                errorDesc = responseBody.optString("errorDesc", "authmatechCode successfully fetched")
            } else {
                // Fallback parsing if body is null
                val debug = response.optJSONObject("debug")
                if (debug != null) {
                    val urlTrace = debug.optString("url_trace", "")
                    // Use regex to find JSON in the URL trace
                    val jsonPattern = Pattern.compile("\\{.*\\}")
                    val matcher = jsonPattern.matcher(urlTrace)
                    if (matcher.find()) {
                        try {
                            val extractedJson = JSONObject(matcher.group(0))
                            // Map original field names to new field names
                            if (extractedJson.has("encMSISDN")) {
                                authmatechCode = extractedJson.getString("encMSISDN")
                            }
                            if (extractedJson.has("opId")) {
                                mnoid = extractedJson.getString("opId")
                            }
                            if (extractedJson.has("errorCode")) {
                                errorCode = extractedJson.getString("errorCode")
                            }
                            if (extractedJson.has("heId")) {
                                heId = extractedJson.getString("heId")
                            }
                            if (extractedJson.has("errorDesc")) {
                                errorDesc = extractedJson.getString("errorDesc")
                            }
                        } catch (e: JSONException) {
                            Log.e(TAG, "Failed to parse JSON from URL trace: ${e.message}")
                        }
                    }
                }
            }
        }

        return AuthmatechResult(
            authmatechCode = authmatechCode,
            MNOID = mnoid,
            errorCode = errorCode,
            heId = heId,
            errorDesc = errorDesc,
        )
    }

    /**
     * Transform the original response to Authmatech format
     */
    private fun transformResponse(response: JSONObject, debug: Boolean): JSONObject {
        val transformedResponse = JSONObject()

        // Copy http_status if present
        if (response.has("http_status")) {
            transformedResponse.put("http_status", response.get("http_status"))
        }

        // Copy error fields if present
        if (response.has("error")) {
            transformedResponse.put("error", response.get("error"))
        }

        if (response.has("error_description")) {
            transformedResponse.put("error_description", response.get("error_description"))
        }

        // Transform response_body if present
        if (response.has("response_body")) {
            val originalBody = response.getJSONObject("response_body")
            val transformedBody = JSONObject()

            // Rename fields according to requirements
            if (originalBody.has("encMSISDN")) {
                transformedBody.put("authmatechCode", originalBody.get("encMSISDN"))
            }

            if (originalBody.has("opId")) {
                transformedBody.put("MNOID", originalBody.get("opId"))
            }

            // Keep these fields as is
            if (originalBody.has("errorCode")) {
                transformedBody.put("errorCode", originalBody.get("errorCode"))
            }

            if (originalBody.has("heId")) {
                transformedBody.put("heId", originalBody.get("heId"))
            }

            if (originalBody.has("errorDesc")) {
                transformedBody.put("errorDesc", originalBody.get("errorDesc"))
            }

            transformedResponse.put("response_body", transformedBody)
        }

        // Transform debug info if present
        if (response.has("debug")) {
            val originalDebug = response.getJSONObject("debug")
            val transformedDebug = JSONObject()

            // Copy existing debug fields
            if (originalDebug.has("device_info")) {
                transformedDebug.put("device_info", originalDebug.get("device_info"))
            }

            if (originalDebug.has("url_trace")) {
                transformedDebug.put("url_trace", originalDebug.get("url_trace"))
            }

            // Add SDK metadata
            transformedDebug.put("sdk_name", "authmatech-sdk")
            transformedDebug.put("sdk_version", "1.0.0")

            transformedResponse.put("debug", transformedDebug)
        }

        // If response_body is null but debug.url_trace exists, try to parse JSON from trace
        if (!transformedResponse.has("response_body") && transformedResponse.has("debug")) {
            val debugInfo = transformedResponse.getJSONObject("debug")
            if (debugInfo.has("url_trace")) {
                val urlTrace = debugInfo.getString("url_trace")
                val parsedBody = parseUrlTraceForJson(urlTrace)
                if (parsedBody != null) {
                    transformedResponse.put("response_body", parsedBody)
                }
            }
        }

        return transformedResponse
    }

    /**
     * Parse the URL trace for valid JSON when the response body is null
     */
    private fun parseUrlTraceForJson(urlTrace: String): JSONObject? {
        val jsonPattern = Pattern.compile("\\{.*\\}")
        val matcher = jsonPattern.matcher(urlTrace)
        if (matcher.find()) {
            try {
                val jsonStr = matcher.group(0)
                val extractedJson = JSONObject(jsonStr)

                // Create a new JSON object with the renamed fields
                val result = JSONObject()

                // Map original field names to new field names
                if (extractedJson.has("encMSISDN")) {
                    result.put("authmatechCode", extractedJson.getString("encMSISDN"))
                } else {
                    result.put("authmatechCode", "")
                }

                if (extractedJson.has("opId")) {
                    result.put("MNOID", extractedJson.getString("opId"))
                } else {
                    result.put("MNOID", "")
                }

                if (extractedJson.has("errorCode")) {
                    result.put("errorCode", extractedJson.getString("errorCode"))
                } else {
                    result.put("errorCode", "0")
                }

                if (extractedJson.has("heId")) {
                    result.put("heId", extractedJson.getString("heId"))
                } else {
                    result.put("heId", "")
                }

                if (extractedJson.has("errorDesc")) {
                    result.put("errorDesc", extractedJson.getString("errorDesc"))
                } else {
                    result.put("errorDesc", "AUTHMATECH Code successfully fetched")
                }

                return result
            } catch (e: JSONException) {
                Log.e(TAG, "Failed to parse JSON from URL trace: ${e.message}")
            }
        }
        return null
    }

    private fun getCellularNetworkManager(): NetworkManager {
        return networkManager
    }

    companion object {
        private const val TAG = "AUTHMATECH_TRACE"
        private var instance: AuthmatechSDK? = null
        private var currentContext: Context? = null

        /**
         * Initialize the Authmatech SDK with the application context
         *
         * @param context The application context
         * @return Instance of AuthmatechSDK
         */
        @Synchronized
        fun initializeSdk(context: Context): AuthmatechSDK {
            var currentInstance = instance
            if (null == currentInstance || currentContext != context) {
                val nm = CellularNetworkManager(context)
                currentContext = context
                currentInstance = AuthmatechSDK(nm)
            }
            instance = currentInstance
            return currentInstance
        }

        /**
         * Get the instance of AuthmatechSDK
         *
         * @return Instance of AuthmatechSDK
         * @throws IllegalStateException if SDK is not initialized
         */
        @Synchronized
        fun getInstance(): AuthmatechSDK {
            val currentInstance = instance
            checkNotNull(currentInstance) {
                AuthmatechSDK::class.java.simpleName +
                    " is not initialized, call initializeSdk(...) first"
            }
            return currentInstance
        }
    }
}

/**
 * Data class representing a simplified response from the Authmatech SDK
 */
data class AuthmatechResult(
    val authmatechCode: String,
    val MNOID: String,
    val errorCode: String,
    val heId: String,
    val errorDesc: String,
)
