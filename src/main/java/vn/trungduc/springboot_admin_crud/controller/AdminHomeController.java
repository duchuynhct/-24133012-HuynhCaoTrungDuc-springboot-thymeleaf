package vn.trungduc.springboot_admin_crud.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.trungduc.springboot_admin_crud.service.ICategoryService;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Controller
@RequiredArgsConstructor
public class AdminHomeController {

    private final ICategoryService categoryService;

    @Value("${app.upload.dir:uploads}")
    private String uploadDir;

    @GetMapping({"/", "/admin", "/admin/home"})
    public String adminHome(Model model) {
        long totalCategories = categoryService.count();
        model.addAttribute("totalCategories", totalCategories);
        return "admin/home";
    }

    /**
     * Endpoint tiếp nhận tải lên ảnh đại diện của sinh viên từ máy tính cá nhân
     */
    @PostMapping("/admin/upload-avatar")
    public String uploadAvatar(@RequestParam("avatarFile") MultipartFile file,
                               HttpServletRequest request,
                               RedirectAttributes redirectAttributes) {
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Vui lòng chọn một file ảnh từ máy tính!");
            return getRedirectTarget(request);
        }

        try {
            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                redirectAttributes.addFlashAttribute("errorMessage", "Chỉ chấp nhận các định dạng file ảnh (JPG, PNG, GIF, WEBP)!");
                return getRedirectTarget(request);
            }

            Path uploadPath = Paths.get(uploadDir, "avatar").toAbsolutePath();
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String originalFilename = file.getOriginalFilename();
            String ext = ".png";
            if (originalFilename != null && originalFilename.lastIndexOf(".") > 0) {
                ext = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
            }

            String fileName = "user_avatar" + ext;
            Path targetFile = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), targetFile, StandardCopyOption.REPLACE_EXISTING);

            // Lưu đường dẫn ảnh vào session để toàn bộ các view đều truy cập được
            String avatarUrl = request.getContextPath() + "/uploads/avatar/" + fileName + "?t=" + System.currentTimeMillis();
            request.getSession().setAttribute("userAvatarUrl", avatarUrl);

            redirectAttributes.addFlashAttribute("successMessage", "Cập nhật ảnh đại diện của bạn thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi lưu ảnh: " + e.getMessage());
        }

        return getRedirectTarget(request);
    }

    private String getRedirectTarget(HttpServletRequest request) {
        String referer = request.getHeader("Referer");
        if (referer != null && !referer.trim().isEmpty()) {
            return "redirect:" + referer;
        }
        return "redirect:/admin/home";
    }
}
