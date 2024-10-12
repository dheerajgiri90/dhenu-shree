import com.dhenu.app.ui.account.response.LogoutResponse
import com.dhenu.app.ui.login.response.LoginResponse
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

    @POST("logout")
    fun logOut(): Observable<LogoutResponse>

}
