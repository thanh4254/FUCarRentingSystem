package com.fu.carrenting.controller;

import com.fu.carrenting.entity.Account;
import com.fu.carrenting.entity.Customer;
import com.fu.carrenting.repository.AccountRepository;
import com.fu.carrenting.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final AccountRepository accountRepository;
    private final CustomerService customerService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String loginPage() { return "auth/login"; }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("customer", new Customer());
        return "auth/register";
    }

    @PostMapping("/register")
    public String doRegister(@Valid @ModelAttribute("customer") Customer customer,
                             BindingResult result,
                             @RequestParam String username,
                             RedirectAttributes ra, Model model) {
        if (result.hasErrors()) return "auth/register";
        if (accountRepository.existsByAccountName(username)) {
            model.addAttribute("usernameError", "Tên đăng nhập đã tồn tại");
            return "auth/register";
        }
        if (customerService.existsByEmail(customer.getEmail())) {
            model.addAttribute("emailError", "Email đã được sử dụng");
            return "auth/register";
        }
        Account account = new Account();
        account.setAccountName(username);
        account.setPassword(passwordEncoder.encode(customer.getPassword()));
        account.setRole("Customer");
        account = accountRepository.save(account);

        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        customer.setAccount(account);
        customerService.save(customer);

        ra.addFlashAttribute("success", "Đăng ký thành công! Vui lòng đăng nhập.");
        return "redirect:/login";
    }

    @GetMapping("/home")
    public String home(Authentication auth) {
        if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_Admin"))) {
            return "redirect:/admin/dashboard";
        }
        return "redirect:/customer/dashboard";
    }
}