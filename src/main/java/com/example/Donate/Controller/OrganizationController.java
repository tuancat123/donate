package com.example.Donate.Controller;

import com.example.Donate.Entity.Organizations;
import com.example.Donate.Service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/organizations")
public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;

    // 🧱 Hiển thị danh sách tổ chức
    @GetMapping
    public String listOrganizations(Model model) {
        List<Organizations> organizations = organizationService.getAllOrganizations();
        model.addAttribute("organizations", organizations);
        return "organization-list";
    }

    // ➕ Hiển thị form thêm tổ chức
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("organization", new Organizations());
        return "organization-add";
    }

    // 💾 Xử lý thêm mới
    @PostMapping("/add")
    public String addOrganization(@ModelAttribute("organization") Organizations organization) {
        organizationService.saveOrganization(organization);
        return "redirect:/admin/organizations";
    }

    // ✏️ Hiển thị form chỉnh sửa
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model) {
        Organizations org = organizationService.getOrganizationById(id);
        model.addAttribute("organization", org);
        return "organization-edit";
    }

    // ✅ Cập nhật tổ chức
    @PostMapping("/edit/{id}")
    public String updateOrganization(@PathVariable("id") Integer id, @ModelAttribute("organization") Organizations organization) {
        organization.setOrgId(id);
        organizationService.saveOrganization(organization);
        return "redirect:/admin/organizations";
    }

    // ❌ Xóa tổ chức
    @GetMapping("/delete/{id}")
    public String deleteOrganization(@PathVariable("id") Integer id) {
        organizationService.deleteOrganization(id);
        return "redirect:/admin/organizations";
    }
}
