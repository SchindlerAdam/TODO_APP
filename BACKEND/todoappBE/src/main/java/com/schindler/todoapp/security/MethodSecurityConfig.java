package com.schindler.todoapp.security;

import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;


// A jsr250 azért kell, mert a @Secured az Spring specifikus
// de van egy JavaEE-s párja, a @RoleAllowed, és így az is használható
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true
)
public class MethodSecurityConfig extends GlobalMethodSecurityConfiguration {
}
