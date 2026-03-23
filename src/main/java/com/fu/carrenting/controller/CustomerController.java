package com.fu.carrenting.controller;
import com.fu.carrenting.entity.*;
import com.fu.carrenting.repository.AccountRepository;
import com.fu.carrenting.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;

@Controller
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final CarService carService;
    private final CarRentalService carRentalService;
    private final AccountRepository accountRepository;

    private Customer getCurrentCustomer(Authentication auth) {
        Account account = accountRepository.findByAccountName(auth.getName())
                .orElseThrow(() -> new RuntimeException("Account not found"));
        return customerService.findByAccountId(account.getAccountId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));
    }

    @GetMapping("/dashboard")
    public String dashboard(Authentication auth, Model model) {
        Customer customer = getCurrentCustomer(auth);
        model.addAttribute("customer", customer);
        model.addAttribute("rentals", carRentalService.findByCustomerId(customer.getCustomerId()));
        return "customer/dashboard";
    }

    @GetMapping("/profile")
    public String profile(Authentication auth, Model model) {
        model.addAttribute("customer", getCurrentCustomer(auth));
        return "customer/profile";
    }

    @PostMapping("/profile")
    public String updateProfile(@ModelAttribute Customer updated,
                                Authentication auth, RedirectAttributes ra) {
        Customer current = getCurrentCustomer(auth);
        current.setCustomerName(updated.getCustomerName());
        current.setMobile(updated.getMobile());
        current.setBirthday(updated.getBirthday());
        customerService.save(current);
        ra.addFlashAttribute("success", "Cập nhật thành công!");
        return "redirect:/customer/profile";
    }

    @GetMapping("/cars")
    public String availableCars(Model model) {
        model.addAttribute("cars", carService.findAvailable());
        return "customer/car/list";
    }

    @GetMapping("/rent/{carId}")
    public String rentForm(@PathVariable Integer carId, Model model) {
        model.addAttribute("car", carService.findById(carId).orElseThrow());
        return "customer/rental/form";
    }

    @PostMapping("/rent/{carId}")
    public String createRental(@PathVariable Integer carId,
                               @ModelAttribute CarRental rental,
                               Authentication auth, RedirectAttributes ra) {
        Customer customer = getCurrentCustomer(auth);
        Car car = carService.findById(carId).orElseThrow();

        if (!rental.getPickupDate().isBefore(rental.getReturnDate())) {
            ra.addFlashAttribute("error", "Ngày nhận phải trước ngày trả xe!");
            return "redirect:/customer/rent/" + carId;
        }

        long days = ChronoUnit.DAYS.between(rental.getPickupDate(), rental.getReturnDate());
        BigDecimal totalPrice = car.getRentPrice().multiply(BigDecimal.valueOf(days));

        rental.setId(new CarRentalId(customer.getCustomerId(), carId));
        rental.setCustomer(customer);
        rental.setCar(car);
        rental.setRentPrice(totalPrice);
        rental.setStatus("Pending");

        carRentalService.save(rental);
        ra.addFlashAttribute("success", "Đặt xe thành công! Tổng: " + totalPrice + " VND");
        return "redirect:/customer/history";
    }

    @GetMapping("/history")
    public String history(Authentication auth, Model model) {
        Customer customer = getCurrentCustomer(auth);
        model.addAttribute("rentals", carRentalService.findByCustomerId(customer.getCustomerId()));
        return "customer/rental/history";
    }
}