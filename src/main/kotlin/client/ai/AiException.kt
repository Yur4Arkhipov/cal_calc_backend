package com.jacqulin.client.ai

sealed class AiException(
    message: String
) : Exception(message)

class InvalidApiKeyException : AiException("Invalid API key")
class RateLimitException : AiException("Rate limit exceeded")
class AiServerException : AiException("AI server error")
class InvalidAiResponseException : AiException("AI returned invalid response")