package com.rs.todolist.data

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.ApolloMutationCall
import com.apollographql.apollo.ApolloQueryCall
import com.apollographql.apollo.api.Input
import com.rs.todolist.*
import com.rs.todolist.data.commun.API_KEY
import com.rs.todolist.data.commun.BASE_URL
import com.rs.todolist.data.commun.USER_NAME_KEY
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor

private const val AUTH_HEADER = "Authorization"

object GraphQlApolloClient {

    private var instance: ApolloClient? = null

    private fun apolloClient(accessToken: String? = null): ApolloClient {
        if (instance != null &&
            (accessToken == null || instance?.defaultCacheHeaders?.hasHeader(AUTH_HEADER) == true)
        ) {

            return instance!!
        }

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor(AuthorizationInterceptor(accessToken))
            .build()

        instance = ApolloClient.builder()
            .serverUrl(BASE_URL)
            .okHttpClient(okHttpClient)
            .build()

        return instance!!
    }

    fun getAccessToken(): ApolloMutationCall<GenerateAccessTokenMutation.Data> =
        apolloClient().mutate(GenerateAccessTokenMutation(API_KEY, USER_NAME_KEY))

    fun getAllTasks(accessToken: String?): ApolloQueryCall<AllTasksQuery.Data> =
        apolloClient(accessToken).query(AllTasksQuery())

    fun addTask(accessToken: String?, name: String, note: String?, isDone: Boolean?): ApolloMutationCall<CreateTaskMutation.Data> =
        apolloClient(accessToken).mutate(CreateTaskMutation(name, Input.optional(note), Input.optional(isDone)))

    fun updateTaskStatus(accessToken: String?, id: String, isDone: Boolean): ApolloMutationCall<UpdateTaskStatusMutation.Data> =
        apolloClient(accessToken).mutate(UpdateTaskStatusMutation(id, isDone))

    fun deleteTask(accessToken: String?, id: String): ApolloMutationCall<DeleteTaskMutation.Data> =
        apolloClient(accessToken).mutate(DeleteTaskMutation(id))
}

class AuthorizationInterceptor(private val accessToken: String?) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader(AUTH_HEADER, accessToken ?: "")
            .build()

        return chain.proceed(request)
    }
}
