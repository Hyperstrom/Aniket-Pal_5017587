package com.librarymanagement.library_management.aspect;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    @Before("execution(* com.librarymanagement.library_management.service.BookService.*(..))")
    public void logBefore() {
        System.out.println("Executing method in BookService...");
    }

    @After("execution(* com.librarymanagement.library_management.service.BookService.*(..))")
    public void logAfter() {
        System.out.println("Method execution completed in BookService.");
    }
}
