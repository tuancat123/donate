package com.example.Donate.Controller;

import com.example.Donate.Entity.*;
import com.example.Donate.Repository.BankAccountRepository;
import com.example.Donate.Repository.BanksRepository;
import com.example.Donate.Repository.DonationCampaignRepository;
import com.example.Donate.Repository.UserRepository;
import com.example.Donate.Service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Controller
public class DonationController {

    @Autowired
    private DonationService donationsService;

    @Autowired
    private DonationCampaignService campaignService;

    @Autowired
    private UserService userService;

    @Autowired
    private BanksService banksService;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private BankAccountService bankAccountService;

    //@Autowired
    //private EmailService emailService;

    // GET: Hiển thị form quyên góp
    @GetMapping("/donate")
    public String showDonateForm(
            @RequestParam(value = "campaignId", required = false) Integer campaignId,
            Model model) {

        List<Donation_Campaigns> campaigns = campaignService.getAllCampaigns();
//        model.addAttribute("campaigns", campaigns);
//
//        Donations donation = new Donations();
//        model.addAttribute("donation", donation);
//
//        // Trường hợp chưa chọn chiến dịch → không có bank
//        if (campaignId == null) {
//            model.addAttribute("selectedBanks", List.of());
//            model.addAttribute("selectedMomos", List.of());
//            return "donate";
//        }
//
//        // Khi người dùng chọn chiến dịch
//        Donation_Campaigns campaign = campaignService.getCampaignById(campaignId);
//
//        if (campaign != null && campaign.getOrganization() != null) {
//            Organizations org = campaign.getOrganization();
//
//            List<BankAccount> bankList = bankAccountService.getAccountsByOrganization(org);
////            List<MomoAccount> momoList = momoService.getByOrganization(org);
//
//            model.addAttribute("selectedBanks", bankList);
////            model.addAttribute("selectedMomos", momoList);
//        } else {
//            model.addAttribute("selectedBanks", List.of());
//            model.addAttribute("selectedMomos", List.of());
//        }
//
//        return "donate";
        model.addAttribute("campaigns", campaigns);

        // Form Donation
        model.addAttribute("donation", new Donations());

        // Nếu chưa chọn chiến dịch → return trang
        if (campaignId == null) {
            model.addAttribute("bankAccounts", null);
            return "donate";
        }

        // Lấy campaign đã chọn
        Donation_Campaigns campaign = campaignService.getCampaignById(campaignId);
        if (campaign == null || campaign.getOrganization() == null) {
            model.addAttribute("bankAccounts", null);
            return "donate";
        }

        // Lấy danh sách bank account của tổ chức thuộc chiến dịch đó
        List<BankAccount> accounts =
                bankAccountService.getAccountsByOrganization(campaign.getOrganization());

        model.addAttribute("bankAccounts", accounts);
        model.addAttribute("selectedCampaignId", campaignId);

        return "donate";
    }


    // POST: Lưu quyên góp
    @PostMapping("/donate")
    public String processDonation(@ModelAttribute("donation") Donations donation, Model model) {

        // Lấy user hiện tại
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userService.findByUsername(username);
        if (user == null) {
            return "redirect:/login";
        }

        donation.setUser(user);
        donation.setDonatedAt(LocalDateTime.now());

        if (donation.getAmount() == null) {
            donation.setAmount(BigDecimal.ZERO);
        }
        // Lấy campaign từ ID form
        if (donation.getCampaignId() != null) {
            Donation_Campaigns campaign = campaignService.getCampaignById(donation.getCampaignId());
            donation.setCampaign(campaign);
        }

        // Lưu Donation
        donationsService.saveDonation(donation);

        // Gửi mail xác nhận
//        emailService.sendDonationEmail(
//                donation.getEmail(),
//                donation.getCampaign().getTitle(),
//                donation.getAmount()
//        );

        // Tạo Transaction
        Transactions transaction = new Transactions();
        transaction.setDonation(donation);
        transaction.setPaymentMethod(Transactions.PaymentMethod.BANK); // hoặc MOMO
        transaction.setStatus(Transactions.Status.SUCCESS);
        transaction.setCreatedAt(LocalDateTime.now());

        transactionService.saveTransaction(transaction);

        model.addAttribute("donation", donation);
        model.addAttribute("campaign", donation.getCampaign());

        return "thankyou";
    }


    @PostMapping("/donation/delete/{id}")
    public String deleteDonation(@PathVariable("id") Integer id) {
        donationsService.deleteDonation(id);
        return "redirect:/campaigns"; // hoặc quay lại trang chi tiết chiến dịch
    }


    @GetMapping("/my-donations")
    public String myDonations(Model model) {

        // Lấy username từ Spring Security
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        // Lấy userId
        User user = userService.findByUsername(username);

        // Lấy danh sách donate
        List<Donations> history = donationsService.getDonationHistoryByUser(user.getUserId());

        model.addAttribute("history", history);

        return "donation-history"; // → templates/donation-history.html
    }

}

