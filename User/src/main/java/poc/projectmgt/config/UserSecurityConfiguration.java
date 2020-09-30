package poc.projectmgt.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import poc.projectmgt.JwtTokenFilterConfigurer;
import poc.projectmgt.JwtTokenProvider;



@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class UserSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	  private JwtTokenProvider jwtTokenProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
		http.headers().defaultsDisabled().disable();
		http.csrf().disable();
		http.authorizeRequests()//
        .antMatchers("/api/v1/signin").permitAll()
        .antMatchers("/api/v1/register").permitAll()
        .antMatchers("/api/v1/verifyemail/{token}").permitAll()
        .antMatchers("/api/v1//forgotpassword/{email}").permitAll()
        .antMatchers("/api/v1/resetpassword/{resetToken}").permitAll()
        .antMatchers("/api/v1/org/{id}/users").hasAuthority("PERMISSION_LIST_USER")
        .antMatchers("/h2-console/**/**").permitAll()
        .anyRequest().authenticated();
		http.apply(new JwtTokenFilterConfigurer(jwtTokenProvider));
    }  
    
    @Override
    public void configure(WebSecurity web) throws Exception {
      
      web.ignoring().antMatchers("/v2/api-docs")
          .antMatchers("/swagger-resources/**")
          .antMatchers("/swagger-ui.html")
          .antMatchers("/configuration/**")
          .antMatchers("/webjars/**")
          .antMatchers("/public")
          .and()
          .ignoring()
          .antMatchers("/h2-console/**/**");
    }
    
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }  
}