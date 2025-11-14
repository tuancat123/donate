package com.example.Donate.Controller;

import com.example.Donate.Entity.BankAccount;
import com.example.Donate.Entity.Organizations;
import com.example.Donate.Service.BankAccountService;
import com.example.Donate.Service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//package com.example.Donate.Controller;
//
//import com.example.Donate.Entity.BankAccount;
//import com.example.Donate.Entity.Organizations;
//import com.example.Donate.Service.BankAccountService;
//import com.example.Donate.Service.OrganizationService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@Controller
//@RequestMapping("/bankaccounts")
//public class BankAccountController {
//
//    @Autowired
//    private BankAccountService bankAccountService;
//
//    @Autowired
//    private OrganizationService organizationService;
//
//    // Hiển thị danh sách tất cả tài khoản ngân hàng
//    @GetMapping
//    public String listAccounts(Model model) {
//        List<BankAccount> accounts = bankAccountService.getAllAccounts();
//        model.addAttribute("accounts", accounts);
//        return "bankaccount-list"; // => tạo file templates/bankaccount-list.html
//    }
//
//    // Hiển thị form thêm tài khoản mới
//    @GetMapping("/add")
//    public String showAddForm(Model model) {
//        model.addAttribute("account", new BankAccount());
//        model.addAttribute("organizations", organizationService.getAllOrganizations());
//        return "add-bankaccount"; // => tạo file templates/add-bankaccount.html
//    }
//
//    // Xử lý thêm tài khoản
//    @PostMapping("/add")
//    public String addAccount(@ModelAttribute("account") BankAccount account) {
//        bankAccountService.saveAccount(account);
//        return "redirect:/bankaccounts";
//    }
//
//    // Hiển thị form sửa tài khoản
//    @GetMapping("/edit/{id}")
//    public String editAccount(@PathVariable("id") Integer id, Model model) {
//        BankAccount account = bankAccountService.getAccountById(id);
//        model.addAttribute("account", account);
//        model.addAttribute("organizations", organizationService.getAllOrganizations());
//        return "edit-bankaccount"; // => tạo file templates/edit-bankaccount.html
//    }
//
//    // Xử lý cập nhật tài khoản
//    @PostMapping("/update/{id}")
//    public String updateAccount(@PathVariable("id") Integer id,
//                                @ModelAttribute("account") BankAccount account) {
//        account.setBankId(id);
//        bankAccountService.saveAccount(account);
//        return "redirect:/bankaccounts";
//    }
//
//    // Xóa tài khoản
//    @GetMapping("/delete/{id}")
//    public String deleteAccount(@PathVariable("id") Integer id) {
//        bankAccountService.deleteAccount(id);
//        return "redirect:/bankaccounts";
//    }
//
//    // API lấy danh sách tài khoản theo tổ chức (phục vụ cho form quyên góp)
//    @GetMapping("/organization/{orgId}")
//    @ResponseBody
//    public List<BankAccount> getAccountsByOrganization(@PathVariable("orgId") Integer orgId) {
//        Organizations org = organizationService.getOrganizationById(orgId);
//        return bankAccountService.getAccountsByOrganization(org);
//    }
//}
@Controller
@RequestMapping("/admin/bankaccounts")
public class BankAccountController {

    @Autowired
    private BankAccountService bankAccountService;

    @Autowired
    private OrganizationService organizationService;

    // 📘 Danh sách tài khoản ngân hàng
    @GetMapping
    public String listAccounts(Model model) {
        List<BankAccount> accounts = bankAccountService.getAllAccounts();
        model.addAttribute("accounts", accounts);
        return "bankaccount-list"; // → templates/bankaccount/list.html
    }

    // 📘 Form thêm tài khoản mới
    @GetMapping("/add")
    public String showAddForm(Model model) {
        List<Organizations> orgs = organizationService.getAllOrganizations();
        model.addAttribute("account", new BankAccount());
        model.addAttribute("organizations", orgs);
        return "bankaccount-add"; // → templates/bankaccount/add.html
    }

    // 📘 Xử lý thêm mới
    @PostMapping("/add")
    public String saveAccount(@ModelAttribute("account") BankAccount account) {
        bankAccountService.saveAccount(account);
        return "redirect:/admin/bankaccounts";
    }

    // 📘 Form chỉnh sửa
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model) {
        BankAccount account = bankAccountService.getAccountById(id);
        List<Organizations> orgs = organizationService.getAllOrganizations();
        model.addAttribute("account", account);
        model.addAttribute("organizations", orgs);
        return "bankaccount-edit"; // → templates/bankaccount/edit.html
    }

    // 📘 Cập nhật tài khoản
    @PostMapping("/update/{id}")
    public String updateAccount(@PathVariable("id") Integer id, @ModelAttribute("account") BankAccount updatedAccount) {
        BankAccount existing = bankAccountService.getAccountById(id);
        if (existing != null) {
            existing.setBankName(updatedAccount.getBankName());
            existing.setAccountNumber(updatedAccount.getAccountNumber());
            existing.setAccountHolder(updatedAccount.getAccountHolder());
            existing.setBranch(updatedAccount.getBranch());
            existing.setOrganization(updatedAccount.getOrganization());
            bankAccountService.saveAccount(existing);
        }
        return "redirect:/admin/bankaccounts";
    }

    // 📘 Xóa tài khoản
    @GetMapping("/delete/{id}")
    public String deleteAccount(@PathVariable("id") Integer id) {
        bankAccountService.deleteAccount(id);
        return "redirect:/admin/bankaccounts";
    }
}