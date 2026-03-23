package com.fu.carrenting.controller;

import com.fu.carrenting.entity.Car;
import com.fu.carrenting.repository.CarProducerRepository;
import com.fu.carrenting.service.CarService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/cars")
@RequiredArgsConstructor
public class AdminCarController {

    private final CarService carService;
    private final CarProducerRepository producerRepository;

    @GetMapping
    public String list(@RequestParam(required = false) String keyword, Model model) {
        model.addAttribute("cars", carService.search(keyword));
        model.addAttribute("keyword", keyword);
        return "admin/car/list";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("car", new Car());
        model.addAttribute("producers", producerRepository.findAll());
        return "admin/car/form";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute Car car, BindingResult result,
                         Model model, RedirectAttributes ra) {
        if (result.hasErrors()) {
            model.addAttribute("producers", producerRepository.findAll());
            return "admin/car/form";
        }
        carService.save(car);
        ra.addFlashAttribute("success", "Thêm xe thành công!");
        return "redirect:/admin/cars";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Integer id, Model model) {
        model.addAttribute("car", carService.findById(id).orElseThrow());
        model.addAttribute("producers", producerRepository.findAll());
        return "admin/car/form";
    }
//a
    @PostMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, @Valid @ModelAttribute Car car,
                       BindingResult result, Model model, RedirectAttributes ra) {
        if (result.hasErrors()) {
            model.addAttribute("producers", producerRepository.findAll());
            return "admin/car/form";
        }
        car.setCarId(id);
        carService.save(car);
        ra.addFlashAttribute("success", "Cập nhật xe thành công!");
        return "redirect:/admin/cars";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes ra) {
        carService.deleteOrDeactivate(id);
        ra.addFlashAttribute("success", "Xoá/vô hiệu hoá xe thành công!");
        return "redirect:/admin/cars";
    }
}