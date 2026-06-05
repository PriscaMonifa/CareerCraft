package com.evaluation.config;


import com.evaluation.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {

    public final JwtFilter jwtFilter;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/error").permitAll()

                        .requestMatchers(HttpMethod.POST,"/api/auth/jobSeeker/register").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/auth/employer/register").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/auth/login").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/auth/user-details").authenticated()
                        .requestMatchers(HttpMethod.POST,"/api/job/add/{employerId}").hasAuthority("EMPLOYER")
                        .requestMatchers(HttpMethod.POST,"/api/job/all").hasAnyAuthority("EMPLOYER","JOB_SEEKER")
                        .requestMatchers(HttpMethod.POST,"/api/application/apply/{jobId}/{seekerId}").hasAuthority("JOB_SEEKER")
                        .requestMatchers(HttpMethod.POST,"/api/application/job-seeker/{seekerId}").hasAuthority("JOB_SEEKER")

                        .requestMatchers(HttpMethod.GET,"/api/auth/book/all/for-author").permitAll()



                        .anyRequest().authenticated()
                );
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        http.httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserService userService){
        DaoAuthenticationProvider dao=new DaoAuthenticationProvider(userService);
        dao.setPasswordEncoder(passwordEncoder());
        return dao;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


}
