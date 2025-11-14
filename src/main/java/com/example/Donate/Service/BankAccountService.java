package com.example.Donate.Service;

import com.example.Donate.Entity.BankAccount;
import com.example.Donate.Entity.Organizations;
import com.example.Donate.Repository.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankAccountService {

    @Autowired
    private BankAccountRepository bankAccountRepository;

    // Lấy tất cả tài khoản ngân hàng
    public List<BankAccount> getAllAccounts() {
        return bankAccountRepository.findAll();
    }

    // Lấy danh sách tài khoản theo tổ chức
    public List<BankAccount> getAccountsByOrganization(Organizations organization) {
        return bankAccountRepository.findByOrganization(organization);
    }

    // Tìm tài khoản theo ID
    public BankAccount getAccountById(Integer id) {
        return bankAccountRepository.findById(id).orElse(null);
    }

    // Thêm hoặc cập nhật tài khoản
    public BankAccount saveAccount(BankAccount account) {
        return bankAccountRepository.save(account);
    }

    // Xóa tài khoản theo ID
    public void deleteAccount(Integer id) {
        bankAccountRepository.deleteById(id);
    }
}
