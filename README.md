# Authmatech Mobile Identity SDK (Android)

A professional cellular-data-based identity verification SDK tailored for mobile network operator (MNO) detection over mobile data.

## Overview

Authmatech SDK for Android enables mobile detection over mobile data through mobile network operator (MNO), designed for seamless integration by developers and clients.

## Features

- Force cellular data connectivity for network requests
- Detect Authmatech Code through mobile network operator (MNO)
- Comprehensive error handling and debugging
- Support for Android 8.0+ devices

## Installation

Add the Authmatech SDK to your project by including it in your app's build.gradle file:

```gradle
dependencies {
    implementation 'com.authmatech.sdk:authmatech-sdk-android:1.0.0'
}
```

## Usage

### Initialization

Initialize the SDK in your Application class or main activity:

```kotlin
import com.authmatech.sdk.AuthmatechSDK

// Initialize the SDK
AuthmatechSDK.initializeSdk(applicationContext)
```

### Basic Usage

To make a request using cellular data:

```kotlin
import com.authmatech.sdk.AuthmatechSDK
import org.json.JSONObject
import java.net.URL

// Get the SDK instance
val sdk = AuthmatechSDK.getInstance()

// Make a request with cellular data
val response: JSONObject = sdk.openWithDataCellular(URL("https://api.example.com/verify"), false)

// Check for errors
if (response.has("error")) {
    val errorCode = response.getString("error")
    val errorDesc = response.getString("error_description")
    // Handle error
} else {
    // Process successful response
    val httpStatus = response.getInt("http_status")
    if (httpStatus == 200) {
        val responseBody = response.getJSONObject("response_body")
        val authmatechCode = responseBody.getString("authmatechCode")
        val mnoid = responseBody.getString("MNOID")
        // Process the data
    }
}
```

### Using the Simplified Helper Method

For easier integration, use the simplified helper method:

```kotlin
import com.authmatech.sdk.AuthmatechSDK
import com.authmatech.sdk.AuthmatechResult

// Get a simplified response
val result: AuthmatechResult = AuthmatechSDK.getInstance()
    .getSimplifiedResponse(context, "https://api.example.com/verify")

// Use the result
if (result.errorCode == "0") {
    // Success
    val authmatechCode = result.authmatechCode
    val mnoid = result.MNOID
    // Process the data
} else {
    // Error
    val errorDesc = result.errorDesc
    // Handle error
}
```

## Response Format

The SDK returns responses in the following format:

### Success Response

```json
{
  "http_status": "200",
  "response_body": {
    "authmatechCode": "base64",
    "MNOID": "1",
    "errorCode": "0",
    "heId": "string",
    "errorDesc": "AUTHMATECH Code successfully fetched"
  },
  "debug": {
    "device_info": "string",
    "url_trace": "string",
    "sdk_name": "authmatech-sdk",
    "sdk_version": "1.0.0"
  }
}
```

### Error Response

```json
{
  "error": "error_code",
  "error_description": "Human readable error description",
  "debug": {
    "device_info": "string",
    "url_trace": "string",
    "sdk_name": "authmatech-sdk",
    "sdk_version": "1.0.0"
  }
}
```

## Error Codes

| Error Code | Description |
|------------|-------------|
| sdk_no_data_connectivity | Data connectivity is not available |
| sdk_connection_error | Connection error occurred |
| sdk_redirect_error | Too many redirects |
| sdk_error | Internal SDK error |

## Authmatech Code Detection Policy

The SDK enforces the use of cellular data connectivity to enable mobile detection over mobile network operator (MNO) for Authmatech detection. This process works as follows:

1. The SDK forces the device to use cellular data connectivity
2. A request is made to the specified URL
3. The response contains the encrypted authmatechCode if available

This process requires:
- An active cellular data connection
- A supported mobile network operator
- Proper permissions in the app

## Supported Android Versions

- Minimum Android SDK: API level 26 (Android 8.0)
- Recommended: API level 30+ (Android 11+)

## Mobile Network Operator Conditions

The SDK works with most major mobile network operators, but functionality may vary based on:
- Operator support for MNO
- Network conditions

## Security & Privacy

- All Authmatech data is encrypted
- HTTPS is enforced for all requests
- Debug logs are disabled by default
- No personal data is stored by the SDK

## License

This SDK is distributed under the MIT License. See the LICENSE file for more information.
