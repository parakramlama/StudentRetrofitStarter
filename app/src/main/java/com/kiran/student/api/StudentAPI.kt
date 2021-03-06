package com.kiran.student.api

import com.kiran.student.entity.Student
import com.kiran.student.response.AddStudentResponse
import com.kiran.student.response.DeleteStudentResponse
import com.kiran.student.response.GetAllStudentResponse
import com.kiran.student.response.ImageResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface StudentAPI {
    @POST("student/")
    suspend fun addStudent(
        @Header("Authorization") token : String,
        @Body student : Student
    ) : Response<AddStudentResponse>

    @GET ("student/")
    suspend fun getAllStudents(
        @Header("Authorization") token : String,
    ) : Response<GetAllStudentResponse>

    @DELETE("student/{id}")
    suspend fun deleteStudents(
        @Header ("Authorization") token : String,
        @Path("id") id: String
    ): Response<DeleteStudentResponse>

    @Multipart
    @PUT("student/{id}/photo")
    suspend fun uploadImage(
        @Header("Autjorozation") token: String,
        @Path("id") id:String,
        @Part file: MultipartBody.Part
    ) : Response<ImageResponse>
}