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

import android.util.Log

/**
 * Utility class for collecting trace information during SDK operations.
 */
internal class TraceCollector private constructor() {
    private var traceEnabled = false
    private var traceData = TraceData()

    /**
     * Start collecting trace information.
     */
    fun startTrace() {
        traceEnabled = true
        traceData = TraceData()
    }

    /**
     * Stop collecting trace information.
     */
    fun stopTrace() {
        traceEnabled = false
    }

    /**
     * Add debug information to the trace.
     *
     * @param level Log level
     * @param tag Log tag
     * @param message Debug message
     */
    fun addDebug(level: Int, tag: String, message: String) {
        Log.println(level, tag, message)
        if (traceEnabled) {
            traceData.trace += "$tag: $message\n"
        }
    }

    /**
     * Add trace information.
     *
     * @param message Trace message
     */
    fun addTrace(message: String) {
        if (traceEnabled) {
            traceData.trace += message
        }
    }

    /**
     * Get the collected trace data.
     *
     * @return TraceData object containing the collected trace information
     */
    fun getTrace(): TraceData {
        return traceData
    }

    /**
     * Data class for storing trace information.
     */
    class TraceData {
        var trace: String = ""
    }

    companion object {
        /**
         * Singleton instance of the TraceCollector.
         */
        val instance: TraceCollector by lazy {
            TraceCollector()
        }
    }
}
