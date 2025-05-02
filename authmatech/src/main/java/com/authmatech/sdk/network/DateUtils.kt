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
package com.authmatech.sdk.network

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Utility class for date operations in the Authmatech SDK.
 */
internal class DateUtils {
    companion object {
        /**
         * Get the current date and time formatted as a string.
         *
         * @return Current date and time as a formatted string
         */
        fun now(): String {
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault())
            return sdf.format(Date())
        }
    }
}
