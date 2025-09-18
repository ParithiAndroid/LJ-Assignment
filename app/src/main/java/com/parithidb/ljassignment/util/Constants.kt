package com.parithidb.ljassignment.util

/**
 * Holds application-wide constants.
 */

class Constants {
    companion object {
        // Toggle development mode (enable detailed logging)
        var DEVELOPMENT_MODE: Boolean = true

        // GitHub API base URL
        val GITHUB_SERVER: String = "https://api.github.com/"

        // API Response Codes
        val STATUS_CODE_SUCCESS: Int = 200
        val STATUS_CODE_SERVER_RESPONSE_MISSING_DATA: Int = 400
        val STATUS_CODE_CONNECTIVITY_ISSUE: Int = 504
        val STATUS_CODE_TIMEOUT: Int = 408
        val DATA_NOT_FOUND: Int = 403
        val STATUS_CODE_RESPONSE_NOT_FOUND: Int = 404
    }
}