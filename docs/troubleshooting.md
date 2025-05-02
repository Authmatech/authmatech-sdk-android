# Authmatech SDK - Troubleshooting Guide

This guide helps developers and QA teams resolve common issues encountered while using the Authmatech Mobile Identity SDK for Android.

---

## 🔌 Cellular Data Issues

### Problem:
**The SDK returns `sdk_no_data_connectivity` error**

### Cause:
The device is connected to Wi-Fi, and **mobile data is disabled at the system level**. The SDK forcibly uses cellular interfaces but cannot establish a connection if cellular data is completely turned off.

### Solution:
- Prompt the user with a message: _"To complete mobile identity verification, please enable mobile data (4G/5G)."_
- Provide a quick link or instruction to open mobile data settings.

> **Note:** This case affects less than **0.01%** of users. Most Android devices keep cellular enabled even when Wi-Fi is on.

---

## 🔁 Too Many Redirects

### Problem:
**The SDK throws `sdk_redirect_error`**

### Cause:
The Authmatech endpoint is misconfigured or responds with multiple HTTPS redirects (301/302) beyond the safe limit.


---

## 🌐 Connection Timeout or Server Error

### Problem:
**You receive `sdk_connection_error`**

### Cause:
- The mobile network operator is not reachable.
- There are DNS or SSL issues.

### Solution:
- Ensure the user not connected on any VPN.
- Validate the endpoint URL works on mobile networks.
- Retry the call after a few seconds.

---

## ❓ General SDK Errors

### Problem:
**You get a `sdk_error` or an empty/null response**

### Cause:
- Internal parsing or unknown issue.

### Solution:
- Turn on debug mode in SDK call to collect logs.
- Inspect the `debugInfo` and `url_trace` in the returned object.
- Contact `support@authmatech.com` with the full log trace.

---

## 🧠 Debug Tips

- Always test with a real SIM and mobile data on
- Make sure VPN, private DNS, or firewall apps are not interfering
- Monitor the `AUTHMATECH_RAW` logs for exact telecom response
- Use `getSimplifiedResponse()` to abstract error handling when integrating quickly

---

## 💬 Still Need Help?
Reach out to our support team at:
📧 support@authmatech.com

Include:
- Device model
- Android version
- SDK version
- Logs (with `debug: true` enabled)

We're here to make mobile identity **seamless and smart**.

