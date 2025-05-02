# Authmatech Mobile Identity SDK (Android) - Integration Guide

Welcome to the step-by-step guide for integrating the **Authmatech SDK** into your Android application. This guide walks you through installation, setup, and usage of the SDK to perform cellular-data-based Authmatech Code detection using MNO.

---

## 📦 Step 1: Install the SDK

Add the following dependency to your **app-level** `build.gradle` file:

```gradle
dependencies {
    implementation 'com.authmatech.sdk:authmatech-sdk-android:1.0.0'
}
```

---

## 🏗 Step 2: Initialize the SDK

In your `Application` class or `MainActivity`, initialize the SDK:

```kotlin
import com.authmatech.sdk.AuthmatechSDK

// Inside onCreate or your initialization block
AuthmatechSDK.initializeSdk(applicationContext)
```

---

## 🌐 Step 3: Perform an MSISDN Detection Request

Here is an example of how to use the SDK to trigger Authmatech Code detection:

```kotlin
import com.authmatech.sdk.AuthmatechSDK
import org.json.JSONObject
import java.net.URL

val response: JSONObject = AuthmatechSDK.getInstance()
    .openWithDataCellular(URL("provided by authmatech co."), false)

if (response.has("response_body")) {
    val body = response.getJSONObject("response_body")
    val authmatechCode = body.getString("authmatechCode")
    val mnoid = body.getString("MNOID")
    // Use the values as needed
} else {
    val error = response.getString("error")
    val desc = response.getString("error_description")
    // Handle the error
}
```

---

## 🧩 Step 4: Use the Simplified Result Model

For easier handling, you can use the built-in helper method:

```kotlin
import com.authmatech.sdk.AuthmatechResult

val result: AuthmatechResult = AuthmatechSDK.getInstance()
    .getSimplifiedResponse(context, "provided by authmatech co.")

if (result.errorCode == "0") {
    val code = result.authmatechCode
    val mno = result.MNOID
    // Success - use data
} else {
    val desc = result.errorDesc
    // Handle error
}
```

---

## ✅ Screenshot: Success Response (Simulated)

```
✔ Authmatech SDK initialized
✔ Request made over cellular
✔ Response:
{
  "authmatechCode": "cIMGS9z/...",
  "MNOID": "1",
  "errorCode": "0",
  "errorDesc": "Authmatech Code successfully fetched"
}
```

---

## 🛠 Required Permissions

Make sure to include the following in your `AndroidManifest.xml`:

```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
```

---

## 🔁 Notes

- Works on mobile data and Wi-Fi
- Response depends on Mobile network operator MNO support
- Device must have an active SIM card

---

## 📬 Need Help?

Check the [`troubleshooting.md`](./troubleshooting.md) guide or reach out to **support@authmatech.com**.

---

Thank you for using **Authmatech SDK** — the smart way to detect and verify users via mobile identity!

