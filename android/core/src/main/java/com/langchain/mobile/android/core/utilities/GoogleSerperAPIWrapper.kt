package com.langchain.mobile.android.core.utilities

import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.json.JSONArray
import org.json.JSONObject

/**
 * Wrapper around the Serper.dev Google Search API.

You can create a free API key at https://serper.dev.

To use, you should have the environment variable ``SERPER_API_KEY``
set with your API key, or pass `serper_api_key` as a named parameter
to the constructor.
 */
class GoogleSerperAPIWrapper(
    private val apiKey: String,
    private val k: Int = 10,
    private val gl: String = "us",
    private val hl: String = "en",
) {
    operator fun invoke(searchTerm: String): String {
        return googleSerperSearchResults(searchTerm)
    }

    private fun googleSerperSearchResults(searchTerm: String): String {
        val response = OkHttpClient.Builder().build().newCall(
            okhttp3.Request.Builder()
                .url(
                    "https://google.serper.dev/search?q=$searchTerm&hl=$hl&gl=$gl"
                )
                .method(
                    "POST",
                    RequestBody.create(MediaType.parse("application/json"), "{\"q\":\"apple inc\"}")
                )
                .addHeader("X-API-KEY", apiKey)
                .build()
        ).execute()
        return parseResults(JSONObject(response.body()?.string() ?: "{}"))
    }

    private fun parseResults(results: JSONObject): String {
        val answerBox = results.getOrNull<JSONObject>("answerBox")
        if (answerBox != null) {
            kotlin.runCatching {
                return answerBox.getOrNull<String>("answer")!!
            }
            kotlin.runCatching {
                return answerBox.getOrNull<String>("snippet")?.replace("\n", " ")!!
            }
            kotlin.runCatching {
                return answerBox.getOrNull<JSONArray>("snippetHighlighted")?.join(", ")!!
            }
        }

        val snippets = arrayListOf<String>()
        val knowledgeGraph = results.getOrNull<JSONObject>("knowledgeGraph")
        if (knowledgeGraph != null) {
            val title = knowledgeGraph.getOrNull<String>("title")
            kotlin.runCatching {
                snippets.add(
                    "${title}: ${
                        knowledgeGraph.getOrNull<String>(
                            "type"
                        )
                    }."
                )
            }
            kotlin.runCatching {
                snippets.add(knowledgeGraph.getOrNull<String>("description")!!)
            }
            kotlin.runCatching {
                val attributes = knowledgeGraph.getOrNull<JSONObject>("attributes")!!
                val keys = attributes.keys()
                while (keys.hasNext()) {
                    val key = keys.next()
                    val value = attributes.getOrNull<String>(key)!!
                    snippets.add("$title ${key}: $value.")
                }
            }
        }
        val organics = results.getOrNull<JSONArray>("organic")
        if (organics != null) {
            for (i in 0..if (k < organics.length() - 1) {
                k
            } else {
                organics.length() - 1
            }) {
                kotlin.runCatching {
                    snippets.add((organics[i] as JSONObject).getOrNull<String>("snippet")!!)
                }
                kotlin.runCatching {
                    val attributes = (organics[i] as JSONObject).getOrNull<JSONObject>("attributes")!!
                    val keys = attributes.keys()
                    while (keys.hasNext()) {
                        val key = keys.next()
                        val value = attributes.getOrNull<String>(key)!!
                        snippets.add("${key}: $value.")
                    }
                }
            }
        }
        return if (snippets.isEmpty()) {
            "No good Google Search Result was found"
        } else {
            snippets.joinToString(" ")
        }
    }

    private fun <T> JSONObject.getOrNull(name: String): T? {
        return if (has(name)) {
            get(name) as T
        } else {
            null
        }
    }
}