package spring.boot.fainalproject.Config;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import spring.boot.fainalproject.Service.MyUserDetailsService;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class ConfigSecurity {
    private final MyUserDetailsService myUserDetailsService;
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(myUserDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        return daoAuthenticationProvider;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .authenticationProvider(daoAuthenticationProvider())
                .authorizeHttpRequests()
                .requestMatchers("/api/v1/customer/register","api/v1/facility/registerFacility","/api/v1/product/get/all","/api/v1/product/getProductById/**","/api/v1/product/search-product/**","/api/v1/product/search-by-category/**","/api/v1/product/best-seller/**","/api/v1/review/get-all","/api/v1/supplier/registerSupplier","/api/v1/supplier/gold-suppliers").permitAll()
                .requestMatchers("/api/v1/auth/**","/api/v1/customer/get-all","/api/v1/facility/get-all-facility","/api/v1/offer/get-all","api/v1/order/get-all","/api/v1/order/get-order-by-id/**","/api/v1/order/get-all-by-userId/**","/api/v1/order/update-by-userId/**","/api/v1/price-offer","/api/v1/price-offer/get/**","/api/v1/recycling-request/updateRecyclingRequest/**","/api/v1/review/update/**","/api/v1/review/delete/**","/api/v1/supplier/getAllSuppliers","/api/v1/supplier/offers","/api/v1/supplier/suppliers-by-badge/**").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/customer/update").hasAuthority("CUSTOMER")
                .requestMatchers("api/v1/facility/updateFacility","/api/v1/facility/requests","/api/v1/facility-request/create","/api/v1/facility-request/update/{facilityRequestId}","/api/v1/facility-request/cancel/{facilityRequestId}","/api/v1/price-offer/approve-priceOffer/**","/api/v1/price-offer/pending-offers/facility/**","/api/v1/recycling-request/addRecyclingRequest").hasAuthority("FACILITY")
                .requestMatchers("/api/v1/order/recent-order-supplier","/api/v1/facility/accept/{facilityRequestId}/{offerId}","/api/v1/facility/reject/{facilityRequestId}/{offerId}","/api/v1/offer/create/{facilityRequestId","/api/v1/offer/update/{offerId}","/api/v1/offer/cancel/{offerId}","/api/v1/order/supplier-shipping-order/**","/api/v1/order/recent-order-supplier","/api/v1/price-offer/create","/api/v1/price-offer/update","/api/v1/price-offer/delete/**","/api/v1/price-offer/approved-offers/**","/api/v1/product/add","/api/v1/product/updateProduct/**","/api/v1/product/alertSupplier","/api/v1/supplier/updateSupplier","/api/v1/supplier/accept-request/**","/api/v1/supplier/reject-request/**").hasAuthority("SUPPLIER")
                .requestMatchers("/api/v1/customer/delete").hasAnyAuthority("ADMIN" , "CUSTOMER")
                .requestMatchers("/api/v1/facility/deleteFacility","/api/v1/recycling-request/deleteRecyclingRequest/**").hasAnyAuthority("ADMIN","FACILITY")
                .requestMatchers("/api/v1/facility-request/get-all","/api/v1/product/deleteProduct/**","/api/v1/recycling-request/getAllRecyclingRequests","/api/v1/recycling-request/getRecyclingRequestById/**","/api/v1/recycling-requests/no-price-offers","/api/v1/supplier/deleteSupplier").hasAnyAuthority("ADMIN","SUPPLIER")
                .requestMatchers("api/v1/facility/popular-recycle-products").hasAnyAuthority("ADMIN","SUPPLIER","FACILITY")
                .requestMatchers("/api/v1/order/add-order/**","/api/v1/order/delete/**","/api/v1/review/add-review/**").hasAnyAuthority("CUSTOMER","FACILITY")
                .anyRequest().authenticated()
                .and().logout().logoutUrl("/api/v1/user/logout").deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .and().httpBasic();

        return http.build();
    }
}
