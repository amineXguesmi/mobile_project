
import com.example.mobile_project.core.models.Product
import com.example.mobile_project.core.models.ProductByCategory
import com.example.mobile_project.core.models.ProductsData
import com.example.mobile_project.core.services.RetrofitHelper
import com.example.mobile_project.core.services.product.ProductAPI
import retrofit2.Call

public class ProductService: ProductAPI {
    override fun getProducts(): Call<ProductsData> {
        return RetrofitHelper.productRetrofitService.getProducts();
    }

    override fun getProductById(id: String): Call<Product> {
        return RetrofitHelper.productRetrofitService.getProductById(id);
    }

    override fun getProductByCategory(id: String): Call<ProductByCategory> {
        return RetrofitHelper.productRetrofitService.getProductByCategory(id);

    }


}