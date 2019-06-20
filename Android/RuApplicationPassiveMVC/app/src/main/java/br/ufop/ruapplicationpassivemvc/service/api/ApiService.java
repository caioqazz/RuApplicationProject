package br.ufop.ruapplicationpassivemvc.service.api;


import br.ufop.ruapplicationpassivemvc.model.entity.Classification;
import br.ufop.ruapplicationpassivemvc.model.entity.Day;
import br.ufop.ruapplicationpassivemvc.model.entity.Demand;
import br.ufop.ruapplicationpassivemvc.model.entity.Dish;
import br.ufop.ruapplicationpassivemvc.model.entity.Feedback;
import br.ufop.ruapplicationpassivemvc.model.entity.Meal;
import br.ufop.ruapplicationpassivemvc.model.entity.Notice;
import br.ufop.ruapplicationpassivemvc.model.entity.ReportDish;
import br.ufop.ruapplicationpassivemvc.model.entity.User;
import br.ufop.ruapplicationpassivemvc.model.entity.Week;
import br.ufop.ruapplicationpassivemvc.model.response.AccessToken;
import br.ufop.ruapplicationpassivemvc.model.response.MenuResponse;

import java.util.List;

import br.ufop.ruapplicationpassivemvc.model.response.ReportDemandResponse;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @POST("register")
    @FormUrlEncoded
    Call<AccessToken> register(@Field("name") String name, @Field("email") String email, @Field("cpf") String cpf, @Field("password") String password);

    @POST("login")
    @FormUrlEncoded
    Call<AccessToken> login(@Field("username") String username, @Field("password") String password);

    @GET("users")
    Call<User> user();

    @Multipart
    @POST("users/update")
    Call<User> updateUser(@Part MultipartBody.Part image,
                          @Part("name") String name);

    @POST("logout")
    @FormUrlEncoded
    Call<ResponseBody> logout(@Field("logout") String log);

    @GET("dishes/type")
    Call<List<Dish>> dishes(@Query(value = "type", encoded = true) int type);

    @GET("dishes")
    Call<List<Dish>> dishes();

    @Multipart
    @POST("dishes")
    Call<Dish> newPrato(
            @Part MultipartBody.Part image,
            @Part("name") String name,
            @Part("description") String description,
            @Part("type") int type


    );


    @POST("dishes/{id}")
    @Multipart
    Call<Dish> updatePrato(@Path("id") int id, @Part MultipartBody.Part image, @Part("type") int type, @Part("name") String name,
                           @Part("description") String description);

    @DELETE("dishes/{id}")
    Call<Dish> deletePrato(@Path("id") int id);

    @GET("feedbacks")
    Call<List<Feedback>> feedbacks();

    @GET("users/feedbacks")
    Call<List<Feedback>> feedbacksByUser();

    @POST("feedbacks")
    @FormUrlEncoded
    Call<Feedback> newFeedback(@Field("subject") String subject, @Field("content") String content);

    @PUT("feedbacks/{id}")
    @FormUrlEncoded
    Call<Feedback> updateFeedback(@Path("id") int id, @Field("subject") String subject, @Field("content") String content, @Field("reply") String reply);

    @POST("classification")
    @FormUrlEncoded
    Call<Classification> addClassification(@Field("dish_id") int dish, @Field("rating") float rating, @Field("option") int option, @Field("comment") String comment);


    @PUT("feedbacks/{id}")
    @FormUrlEncoded
    Call<Feedback> setViewFeedback(@Path("id") int id, @Field("manager_view") int managerView, @Field("user_view") int userView);

    @DELETE("feedbacks/{id}")
    Call<Feedback> deleteFeedback(@Path("id") int id);

    @GET("weeks")
    Call<List<Week>> weeks();

    @POST("meals/status")
    @FormUrlEncoded
    Call<Meal> updateRefeicao(@Field("date") String date, @Field("type") int type, @Field("status") int status, @Field("reason") String reason);

    @GET("meals/days/opened")
    Call<List<Day>> daysOpened(@Query(value = "week", encoded = true) int week, @Query(value = "month", encoded = true) int month, @Query(value = "year", encoded = true) int year);

    @GET("demands/days")
    Call<List<Day>> demandDays();

    @GET("meals/days/closed")
    Call<List<Meal>> daysClosed();

    @GET("notices")
    Call<List<Notice>> notices();

    @GET("classifications")
    Call<Classification> showClassification(@Query(value = "dish_id", encoded = true) int dish);

    @GET("classifications/report")
    Call<ReportDish> reportClassification(@Query(value = "dish_id", encoded = true) int dish);

    @GET("demands/report")
    Call<ReportDemandResponse> reportDemand(@Query(value = "month", encoded = true)int month,@Query(value = "year", encoded = true) int year);


    @POST("menus/dishes")
    @FormUrlEncoded
    Call<Dish> addPratoNoCardapio(@Field("date") String date, @Field("type") int type, @Field("dish_id") int id, @Field("dish_type") int dishType);

    @PUT("menus/dishes")
    @FormUrlEncoded
    Call<Dish> updatePratoNoCardapio(@Field("date") String date, @Field("type") int type, @Field("dish_id") int id, @Field("dish_type") int dishType);

    @GET("demands")
    Call<Demand> getDemand(@Query(value = "type", encoded = true) int type, @Query(value = "date", encoded = true) String date);

    @GET("users/demand")
    Call<Demand> getMineDemand(@Query(value = "type", encoded = true) int type, @Query(value = "date", encoded = true) String date);

    @POST("demands")
    @FormUrlEncoded
    Call<Demand> addDemand(@Field("type") int type, @Field("date") String date, @Field("option") int option);

    @PUT("demands")
    @FormUrlEncoded
    Call<Demand> deleteDemand(@Field("type") int type, @Field("date") String date);

    @GET("menus")
    Call<MenuResponse> menus(@Query(value = "type", encoded = true) int type, @Query(value = "date", encoded = true) String date);

    @POST("notices")
    @FormUrlEncoded
    Call<Notice> newAviso(@Field("subject") String subject, @Field("content") String content, @Field("type") int type);

    @PUT("notices/{id}")
    @FormUrlEncoded
    Call<Notice> updateAviso(@Path("id") int id, @Field("subject") String subject, @Field("type") int type, @Field("content") String content);

    @DELETE("notices/{id}")
    Call<Notice> deleteAviso(@Path("id") int id);

    @POST("notices/view")
    @FormUrlEncoded
    Call<ResponseBody> setViewAviso(@Field("notice_id") int id);

    @POST("refresh")
    @FormUrlEncoded
    Call<AccessToken> refresh(@Field("refresh_token") String refreshToken);



}
