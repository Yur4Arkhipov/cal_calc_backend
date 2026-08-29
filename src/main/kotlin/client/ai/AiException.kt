package com.jacqulin.client.ai

sealed class AppException(
    message: String,
    cause: Throwable? = null
) : RuntimeException(message, cause)

class AiUnavailableException(
    cause: Throwable? = null
) : AppException(
    message = "AI service unavailable",
    cause = cause
)

class InvalidAiResponseException(
    cause: Throwable? = null
) : AppException(
    message = "AI returned invalid response",
    cause = cause
)

class DailyLimitExceededException : AppException("Daily limit exceeded")

class MissingDeviceIdException : AppException(message = "Device ID header is missing")