import com.dhenu.app.ui.account.response.LogoutResponse
import com.dhenu.app.ui.login.response.LoginResponse
import com.dhenu.app.ui.signup.response.SignUpResponse
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

interface Api {

    @POST("signup")
    fun userSignUp(@Body map: Map<String, @JvmSuppressWildcards Any>): Observable<SignUpResponse>

    @POST("account/login")
    fun loginApi(@Body map: Map<String, @JvmSuppressWildcards Any>): Observable<LoginResponse>

    @POST("logout")
    fun logOut(): Observable<LogoutResponse>

}
