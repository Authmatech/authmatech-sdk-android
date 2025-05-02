# Authmatech SDK for Android - API Reference

This document outlines all public methods and data models available in the Authmatech Mobile Identity SDK for Android.

## Overview
Package: `com.authmatech.sdk`

Primary class: `AuthmatechSDK`

## Public Methods

### 1. `initializeSdk(context: Context)`
Initializes the Authmatech SDK. Must be called before any other SDK method.

**Parameters:**
- `context`: Application or Activity context

---

### 2. `getInstance(): AuthmatechSDK`
Returns the singleton instance of the SDK.

**Returns:** `AuthmatechSDK`

---

### 3. `openWithDataCellular(url: URL, debug: Boolean = false): JSONObject`
Performs a network request to the provided URL using cellular data only. Returns the raw JSON response.

**Parameters:**
- `url`: The endpoint URL to contact
- `debug`: Optional, include debug metadata (default: false)

**Returns:** `JSONObject`

---

### 4. `getSimplifiedResponse(context: Context, url: String): AuthmatechResult`
A helper method that abstracts parsing the raw response and returns a data class object.

**Parameters:**
- `context`: Activity or Application context
- `url`: Target URL as a string

**Returns:** `AuthmatechResult`


## Data Models

### AuthmatechResult
A data class that simplifies the API response for client use.

```kotlin
data class AuthmatechResult(
    val authmatechCode: String = "",
    val MNOID: String = "",
    val errorCode: String = "",
    val errorDesc: String = "",
    val httpStatus: Int = 0,
    val debugInfo: String = ""
)
```

### Error Codes
| Code                | Description                           |
|---------------------|---------------------------------------|
| `sdk_no_data_connectivity` | No cellular data available          |
| `sdk_connection_error`     | Failed to establish connection     |
| `sdk_redirect_error`       | Too many redirects encountered     |
| `sdk_error`                | General internal SDK error         |

---

## Logging Tags
- `AUTHMATECH_SDK`: General SDK logs
- `AUTHMATECH_NET`: Network-specific logs
- `AUTHMATECH_RAW`: Full raw responses

---

## Notes
- Minimum SDK: 26 (Android 8.0)
- Required permissions: Internet, Network State
- Ensure the app is connected to mobile data for accurate MSISDN detection

