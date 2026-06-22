package com.csb.config;

import com.csb.service.UserService;
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
                            // Preflight request enabled
                            .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                            // Auth APIs
                            .requestMatchers(HttpMethod.GET,"/api/auth/login").authenticated()
                            .requestMatchers(HttpMethod.GET, "api/auth/user-details").authenticated()
                            // Employer SignUp By Admin
                            .requestMatchers(HttpMethod.POST,"/api/employer/register").hasAuthority("ADMIN")
                            .requestMatchers(HttpMethod.POST,"/api/jobseeker/register").permitAll()

                            .requestMatchers(HttpMethod.GET,"/api/jobs/get-one/{jobId}").permitAll()
                            .requestMatchers(HttpMethod.GET,"/api/jobs/jobTitle").permitAll()
                            .requestMatchers(HttpMethod.GET,"/api/jobs/all").permitAll()
                            .requestMatchers(HttpMethod.GET,"/api/jobs/search").permitAll()
                            .requestMatchers(HttpMethod.GET,"/api/jobs/employerJobs").hasAuthority("EMPLOYER")
                            .requestMatchers(HttpMethod.GET,"/api/jobs/recommend/{seekerId}").hasAuthority("JOB_SEEKER")
                            .requestMatchers(HttpMethod.POST,"/api/jobs/add").hasAuthority("EMPLOYER")
                            .requestMatchers(HttpMethod.POST,"/api/jobs/add/{employerId}").hasAuthority("EMPLOYER")
                            .requestMatchers(HttpMethod.DELETE,"/api/jobs/delete/{jobId}").hasAuthority("EMPLOYER")
                            .requestMatchers(HttpMethod.PUT,"/api/jobs/update/{jobId}").hasAuthority("EMPLOYER")
                            .requestMatchers(HttpMethod.PUT,"/api/jobs/toggle-active/{jobId}").hasAuthority("EMPLOYER")

                            .requestMatchers(HttpMethod.GET,"/api/jobseeker/{seekerId}").hasAnyAuthority("JOB_SEEKER","EMPLOYER")
                            .requestMatchers(HttpMethod.GET,"/api/jobseeker/profile").hasAuthority("JOB_SEEKER")
                            .requestMatchers(HttpMethod.GET,"/api/jobseeker/jobseeker-stats").hasAuthority("JOB_SEEKER")
                            .requestMatchers(HttpMethod.POST,"/api/jobseeker/resume/upload").hasAuthority("JOB_SEEKER")
                            .requestMatchers(HttpMethod.DELETE,"/api/jobseeker/delete/{seekerId}").hasAuthority("JOB_SEEKER")
                            .requestMatchers(HttpMethod.PUT,"/api/jobseeker/update/{seekerId}").hasAuthority("JOB_SEEKER")

                            .requestMatchers(HttpMethod.GET,"/api/employer/{employerId}").hasAuthority("EMPLOYER")
                            .requestMatchers(HttpMethod.GET,"/api/employer/employer-stats").hasAuthority("EMPLOYER")
                            .requestMatchers(HttpMethod.DELETE,"/api/employer/delete/{employerId}").hasAuthority("EMPLOYER")
                            .requestMatchers(HttpMethod.PUT,"/api/employer/update/{employerId}").hasAuthority("EMPLOYER")

                            .requestMatchers(HttpMethod.GET,"/api/application/job/{jobId}").hasAuthority("EMPLOYER")
                            .requestMatchers(HttpMethod.GET,"/api/application/job-seeker").hasAuthority("JOB_SEEKER")
                            .requestMatchers(HttpMethod.GET,"/api/application/status-chart").hasAuthority("JOB_SEEKER")
                            .requestMatchers(HttpMethod.POST,"/api/application/apply/{jobId}").hasAuthority("JOB_SEEKER")
                            .requestMatchers(HttpMethod.PUT,"/api/application/status/{applicationId}").hasAuthority("EMPLOYER")
                            .requestMatchers(HttpMethod.PUT,"/api/application/recent/{employerId}").hasAuthority("EMPLOYER")
                            .requestMatchers(HttpMethod.PUT,"/api/application/chart/category/{employerId}").hasAuthority("EMPLOYER")

                            .requestMatchers(HttpMethod.GET,"/api/notification/receive").hasAnyAuthority("JOB_SEEKER","EMPLOYER")
                            .requestMatchers(HttpMethod.GET,"/api/unread-count").hasAnyAuthority("JOB_SEEKER","EMPLOYER")
                            .requestMatchers(HttpMethod.GET,"/api/read/{notificationId}").hasAnyAuthority("JOB_SEEKER","EMPLOYER")
                            .requestMatchers("/uploads/**").permitAll()

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

