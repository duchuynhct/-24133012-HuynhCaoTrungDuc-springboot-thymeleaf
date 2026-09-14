package vn.trungduc.springboot_admin_crud.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import vn.trungduc.springboot_admin_crud.service.ICategoryService;

@Controller
@RequiredArgsConstructor
public class AdminHomeController {

    private final ICategoryService categoryService;

    @GetMapping({"/", "/admin", "/admin/home"})
    public String adminHome(Model model) {
        long totalCategories = categoryService.count();
        model.addAttribute("totalCategories", totalCategories);
        return "admin/home";
    }
}
