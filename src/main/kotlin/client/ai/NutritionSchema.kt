package com.jacqulin.client.ai

import kotlinx.serialization.json.add
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import kotlinx.serialization.json.putJsonArray
import kotlinx.serialization.json.putJsonObject

object NutritionSchema {
    val schema = buildJsonObject {
        put("type", "object")
        putJsonObject("properties") {
            putJsonObject("name") {
                put("type", "string")
            }
            putJsonObject("weight") {
                put("type", "integer")
            }
            putJsonObject("calories") {
                put("type", "integer")
            }
            putJsonObject("protein") {
                put("type", "integer")
            }
            putJsonObject("fat") {
                put("type", "integer")
            }
            putJsonObject("carb") {
                put("type", "integer")
            }
            putJsonObject("ingredients") {
                put("type", "array")
                putJsonObject("items") {
                    put("type", "object")
                    putJsonObject("properties") {
                        putJsonObject("name") {
                            put("type", "string")
                        }
                        putJsonObject("weight") {
                            put("type", "integer")
                        }
                        putJsonObject("calories") {
                            put("type", "integer")
                        }
                        putJsonObject("protein") {
                            put("type", "integer")
                        }
                        putJsonObject("fat") {
                            put("type", "integer")
                        }
                        putJsonObject("carb") {
                            put("type", "integer")
                        }
                    }
                    putJsonArray("required") {
                        add("name")
                        add("weight")
                        add("calories")
                        add("protein")
                        add("fat")
                        add("carb")
                    }
                    put("additionalProperties", false)
                }
            }
        }
        putJsonArray("required") {
            add("name")
            add("weight")
            add("calories")
            add("protein")
            add("fat")
            add("carb")
            add("ingredients")
        }
        put("additionalProperties", false)
    }
}