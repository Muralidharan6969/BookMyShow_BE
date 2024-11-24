package com.example.bookmyshow_be.Services;

import com.example.bookmyshow_be.Exceptions.UserNotFoundException;
import com.example.bookmyshow_be.Models.Admin;
import com.example.bookmyshow_be.Models.AdminTokenPayload;
import com.example.bookmyshow_be.Models.Outlet;
import com.example.bookmyshow_be.Models.OutletTokenPayload;
import com.example.bookmyshow_be.Repositories.AdminRepository;
import com.example.bookmyshow_be.Utils.ENUMS.RoleEnums;
import com.example.bookmyshow_be.Utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminService {
    private AdminRepository adminRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private JWTUtils jwtUtils;

    @Autowired
    public AdminService(AdminRepository adminRepository, BCryptPasswordEncoder bCryptPasswordEncoder,
                        JWTUtils jwtUtils){
        this.adminRepository = adminRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtUtils = jwtUtils;
    }

    public Admin registerAdmin(Admin admin){
        admin.setPassword(bCryptPasswordEncoder.encode(admin.getPassword()));
        return adminRepository.save(admin);
    }

    public String adminLogin(Long mobileNumber, String password) throws UserNotFoundException {
        Optional<Admin> adminOptional = adminRepository.findByMobileNumber(mobileNumber);
        if(adminOptional.isEmpty()){
            throw new UserNotFoundException("Admin Details not found");
        }

        if(!bCryptPasswordEncoder.matches(password, adminOptional.get().getPassword())){
            throw new RuntimeException("Password is incorrect");
        }

        Admin admin = adminOptional.get();

        return jwtUtils.generateAdminToken(new AdminTokenPayload(admin.getAdminId(),
                admin.getRegistrationId(), RoleEnums.ADMIN.toString()));
    }

    public Admin getAdminByAdminId(Long adminId){
        Optional<Admin> adminOptional = adminRepository.findById(adminId);
        if(adminOptional.isEmpty()){
            return null;
        }
        return adminOptional.get();
    }
}
