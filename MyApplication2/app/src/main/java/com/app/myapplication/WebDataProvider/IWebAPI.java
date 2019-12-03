package com.app.myapplication.WebDataProvider;


        import com.app.myapplication.Entity.ItemJson;

        import java.util.List;

        import retrofit2.Call;
        import retrofit2.http.GET;

public interface IWebAPI {

    @GET("/test.json")
    Call<List<ItemJson>> getItems();

}

