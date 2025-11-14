package com.example.Donate.Controller;

import com.example.Donate.Entity.Transactions;
import com.example.Donate.Service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/save")
    public String saveTransaction(@ModelAttribute Transactions transaction) {
        transactionService.saveTransaction(transaction);
        return "admin/thankyou"; // templates/admin/thankyou.html
    }

    @GetMapping({"", "/list"})
    public String listTransactions(Model model) {
        model.addAttribute("transactions", transactionService.getAllTransactions());
        return "transactions"; // templates/admin/transactions.html
    }
}

