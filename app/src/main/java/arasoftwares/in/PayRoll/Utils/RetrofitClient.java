package arasoftwares.in.PayRoll.Utils;

import java.util.List;

import arasoftwares.in.PayRoll.model.AssignPerson;
import arasoftwares.in.PayRoll.model.BreakInOut;
import arasoftwares.in.PayRoll.model.Delivery;
import arasoftwares.in.PayRoll.model.Employees;
import arasoftwares.in.PayRoll.model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RetrofitClient {
    @POST(Constants.END_POINT_LOGIN)
    Call<User> login(@Body User user);

    @POST(Constants.END_POINT_BREAKINOUT)
    Call<BreakInOut> breakInOut(@Body BreakInOut breakInOut);

    @POST(Constants.END_POINT_ASSIGNTO)
    Call<AssignPerson> assignPerson(@Body AssignPerson assignPerson);

    @POST(Constants.END_POINT_DELIVERY)
    Call<Delivery> deliverySignature(@Body Delivery delivery);

    @POST(Constants.END_POINT_EMPLOYEELIST)
    Call<Employees> getEmployee(@Body Employees employees);
}
