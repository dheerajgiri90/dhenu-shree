import com.dhenu.app.ui.account.response.LogoutResponse
import com.dhenu.app.ui.businessman.response.AddBusinessManResponse
import com.dhenu.app.ui.businessman.response.BusinessManListResponse
import com.dhenu.app.ui.customer.response.AddCustomerResponse
import com.dhenu.app.ui.customer.response.CustomerListResponse
import com.dhenu.app.ui.exchange.response.AddExchangeResponse
import com.dhenu.app.ui.exchange.response.ExchangeListResponse
import com.dhenu.app.ui.items.response.AddItemsResponse
import com.dhenu.app.ui.items.response.ItemsListResponse
import com.dhenu.app.ui.login.response.LoginResponse
import com.dhenu.app.ui.mortgage.response.AddMortgageResponse
import com.dhenu.app.ui.mortgage.response.MortgageListResponse
import com.dhenu.app.ui.mortgage.response.UpdateMortgageResponse
import com.dhenu.app.ui.signup.response.SignUpResponse
import com.dhenu.app.ui.village.response.AddVillageResponse
import com.dhenu.app.ui.village.response.VillageListResponse
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.QueryMap

interface Api {

    @POST("signup")
    fun userSignUp(@Body map: Map<String, @JvmSuppressWildcards Any>): Observable<SignUpResponse>

    @POST("account/login")
    fun loginApi(@Body map: Map<String, @JvmSuppressWildcards Any>): Observable<LoginResponse>

    @POST("account/AddVillage")
    fun addVillage(@Body map: Map<String, @JvmSuppressWildcards Any>): Observable<AddVillageResponse>

    @POST("account/UpdateVillage")
    fun updateVillage(@Body map: Map<String, @JvmSuppressWildcards Any>): Observable<AddVillageResponse>

    @GET("account/GetVillage")
    fun villageList(@QueryMap map: HashMap<String, Any>): Observable<VillageListResponse>


    @POST("account/AddItem")
    fun addItems(@Body map: Map<String, @JvmSuppressWildcards Any>): Observable<AddItemsResponse>

    @POST("account/UpdateItem")
    fun updateItems(@Body map: Map<String, @JvmSuppressWildcards Any>): Observable<AddItemsResponse>

    @GET("account/GetItem")
    fun getItems(@QueryMap map: HashMap<String, Any>): Observable<ItemsListResponse>

    @POST("account/AddBusinessMan")
    fun addBusinessMan(@Body map: Map<String, @JvmSuppressWildcards Any>): Observable<AddBusinessManResponse>

    @POST("account/UpdateBusinessMan")
    fun updateBusinessMan(@Body map: Map<String, @JvmSuppressWildcards Any>): Observable<AddBusinessManResponse>

    @GET("account/GetBusinessMan")
    fun getBusinessMan(@QueryMap map: HashMap<String, Any>): Observable<BusinessManListResponse>


    @POST("account/AddCustomer")
    fun addCustomer(@Body map: Map<String, @JvmSuppressWildcards Any>): Observable<AddCustomerResponse>

    @POST("account/UpdateCustomer")
    fun updateCustomer(@Body map: Map<String, @JvmSuppressWildcards Any>): Observable<AddCustomerResponse>

    @GET("account/GetCustomer")
    fun getCustomer(@QueryMap map: HashMap<String, Any>): Observable<CustomerListResponse>

    @POST("account/AddMortgage")
    fun addMortgage(@Body map: MutableList<HashMap<String, Any>>): Observable<AddMortgageResponse>

    @GET("account/GetMortgage")
    fun getMortgage(@QueryMap map: HashMap<String, Any>): Observable<MortgageListResponse>


    @POST("account/UpdateMortgage")
    fun updateMortgage(@Body map: HashMap<String, Any>): Observable<UpdateMortgageResponse>


    @POST("account/AddExchange")
    fun addExchange(@Body map: Map<String, @JvmSuppressWildcards Any>): Observable<AddExchangeResponse>

    @POST("account/UpdateVillage")
    fun updateExchange(@Body map: Map<String, @JvmSuppressWildcards Any>): Observable<AddExchangeResponse>

    @GET("account/GetExchange")
    fun getExchange(@QueryMap map: HashMap<String, Any>): Observable<ExchangeListResponse>

    @POST("logout")
    fun logOut(): Observable<LogoutResponse>

}
