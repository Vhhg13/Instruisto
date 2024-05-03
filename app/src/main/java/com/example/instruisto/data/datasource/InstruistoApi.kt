package com.example.instruisto.data.datasource
import android.graphics.Bitmap
import com.example.instruisto.model.AuthRequest
import com.example.instruisto.model.Flashcard
import kotlinx.serialization.json.JsonElement
import retrofit2.Response
import retrofit2.http.*

interface InstruistoApi {
    @PATCH("/flashcards")
    suspend fun patchCard(@Body card: Flashcard): Response<Unit>

    @DELETE("/flashcards/{cardId}")
    suspend fun deleteFlashcard(@Path("cardId") cardId: Int): Response<Unit>

    /**
     * Example value:
     * ```
     * {
     *   "id": 821736,
     *   "front": "Hamstro",
     *   "back": "Hamster",
     *   "nextReview": "24-04-24",
     *   "image": "http://localhost:8080/image/image1.jpg",
     *   "deck": 821736
     * }
     * ```
     */
    @GET("/flashcards/{cardId}")
    suspend fun getFlashcardById(@Path("cardId") id: Int): Response<Flashcard>

    @PUT("/flashcards/{cardId}/uploadImage")
    suspend fun uploadCardImage(@Path("cardId") cardId: Int, @Body bm: Bitmap): Response<Unit>

    ////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Example value:
     * ```
     * [
     *   {
     *     "id": 821736,
     *     "front": "Hamstro",
     *     "back": "Hamster",
     *     "nextReview": "24-04-24",
     *     "image": "http://localhost:8080/image/image1.jpg",
     *     "deck": 821736
     *   }
     * ]
     * ```
     */
    @GET("/flashcards/today")
    suspend fun getFlashcardsForToday(@Query("deck") deckId: Int): Response<List<Flashcard>>

    @POST("/flashcards/today/card/{cardId}")
    suspend fun changeTodayCardStatus(cardId: Int, @Body op: String): Response<Unit>

    ////////////////////////////////////////////////////////////////////////////////////////

    /**
     * @return New deck Id
     */
    @POST("/decks")
    suspend fun createDeckWithName(@Body name: String): Response<Int>

    /**
     * Example value:
     * ```
     *[
     *   {
     *     "id": 68432,
     *     "name": "Deck Name",
     *     "plan": "1/2/3"
     *   }
     * ]
     * ```
     */
    @GET("/decks")
    suspend fun getAllDecks(): Response<JsonElement>

    @DELETE("/decks/{deckId}")
    suspend fun deleteDeckById(@Path("deckId") deckId: Int): Response<Unit>

    /**
     * @return The new card Id
     */
    @POST("/decks/{deckId}")
    suspend fun addFlashcardToDeck(
        @Path("deckId") deckId: Int,
        @Body fb: Map<String, String>
    ): Response<Int>

    ////////////////////////////////////////////////////////////////////////////////////////
    // TESTED
    /**
     * @return JWT
     */
    @POST("/login")
    suspend fun login(
        @Body req: AuthRequest
    ): Response<String>

    @POST("/register")
    suspend fun register(
        @Body req: AuthRequest
    ): Response<Unit>

    @POST("/changePassword")
    suspend fun changePassword(@Body password: String): Response<Unit>
    ///TESTED
    ////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Example value:
     * ```
     * [
     *   {
     *     "id": 821736,
     *     "passed": true
     *   }
     * ]
     * ```
     */
    @GET("/lessons")
    suspend fun getLessons(): Response<JsonElement>

    /**
     * Example value:
     * ```
     * {
     *   "id": 821736,
     *   "passed": true,
     *   "exercises": [
     *     {
     *       "id": 821736,
     *       "type": "aud",
     *       "question_text": "Vorto",
     *       "answer_text": "Word",
     *       "answer_audio": "string"
     *     }
     *   ],
     *   "grammarPoints": [
     *     {
     *       "id": 821736,
     *       "name": "Articles",
     *       "description": "# H1\nText under H1\n\n## H2\nText under H2"
     *     }
     *   ],
     *   "order": [
     *     821736
     *   ]
     * }
     * ```
     */
    @GET("/lessons/{lessonId}")
    suspend fun getLessonById(@Path("lessonId") lessonId: Int): Response<JsonElement>

    @PATCH("/lessons/{lessonId}")
    suspend fun changeLessonStatus(@Path("lessonId") lessonId: Int, @Body passed: String): Response<Unit>

    ////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Example output:
     * ```
     * [
     *   {
     *     "id": 821736,
     *     "name": "Articles"
     *   }
     * ]
     * ```
     */
    @GET("/grammar")
    suspend fun getGrammar(): Response<JsonElement>

    /**
     * Example output:
     * ```markdown
     * # H1
     * Text under H1
     *
     * ## H2
     * Text under H2
     * ```
     */
    @GET("/grammar/{pointId}")
    suspend fun getGrammarPoint(@Path("pointId") pointId: Int): Response<String>
}