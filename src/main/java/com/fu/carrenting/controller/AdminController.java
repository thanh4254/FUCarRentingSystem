package com.fu.carrenting.controller;

import com.fu.carrenting.service.CarRentalService;
import com.fu.carrenting.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.time.LocalDate;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final CustomerService customerService;
    private final CarRentalService carRentalService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("totalCustomers", customerService.findAll().size());
        model.addAttribute("totalRentals", carRentalService.findAll().size());
        return "admin/dashboard";
    }

    @GetMapping("/customers")
    public String customers(@RequestParam(required = false) String keyword, Model model) {
        model.addAttribute("customers", customerService.search(keyword));
        model.addAttribute("keyword", keyword);
        return "admin/customer/list";
    }

    @GetMapping("/customers/delete/{id}")
    public String deleteCustomer(@PathVariable Integer id, RedirectAttributes ra) {
        customerService.deleteById(id);
        ra.addFlashAttribute("success", "Xoá khách hàng thành công!");
        return "redirect:/admin/customers";
    }

    @GetMapping("/rentals")
    public String rentals(Model model) {
        model.addAttribute("rentals", carRentalService.findAll());
        return "admin/rental/list";
    }

    @GetMapping("/rentals/report")
    public String report(@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                         @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
                         Model model) {
        if (startDate != null && endDate != null) {
            model.addAttribute("rentals", carRentalService.findByPeriod(startDate, endDate));
        }
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        return "admin/rental/report";
    }
}