# Changelog

All notable changes to the Authmatech SDK for Android will be documented in this file.

## 1.0.0 - 2025-04-21

### Initial Release

This is the first release of the Authmatech SDK for Android.

### Added

- Added `AuthmatechResult` data class for simplified responses
- Added `getSimplifiedResponse` helper method
- Added SDK metadata fields (`sdk_name`, `sdk_version`) to debug information
- Implemented response fallback parsing for null body responses
- Added comprehensive Javadoc for all public methods
- Created detailed README.md with integration guide

### Changed
- Added response fields:
    `authmatechCode`
    `MNOID`
- Updated build configuration to use Authmatech versioning system
- Enhanced error handling and debugging capabilities
- Improved documentation with Authmatech Code detection policy explanation

### Maintained
- Core cellular data enforcement logic
- Existing method signatures for backward compatibility
- Debug tracing and response object wrapping
- Chunked response parsing fallback behavior
- Support for Android 8.0+ devices
