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

import org.json.JSONObject
import java.net.URL

/**
 * Interface defining the network management capabilities for the Authmatech SDK.
 */
internal interface NetworkManager {
    /**
     * Open a given URL after forcing the data connectivity on the device
     *
     * @param url The URL to be opened over a data cellular connectivity
     * @param accessToken Optional Access Token to be added in the Authorization header (Bearer)
     * @param debug A flag to include or not the URL trace in the response
     * @return JSONObject containing the response
     */
    fun openWithDataCellular(url: URL, accessToken: String?, debug: Boolean): JSONObject
}
