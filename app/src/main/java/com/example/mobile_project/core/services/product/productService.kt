
import com.example.mobile_project.core.models.ProductsData
import com.example.mobile_project.core.services.RetrofitHelper
import com.example.mobile_project.core.services.product.ProductAPI
import retrofit2.Call

public class ProductService: ProductAPI {
    override fun getProducts(): Call<ProductsData> {
        return RetrofitHelper.productRetrofitService.getProducts();
    }

}