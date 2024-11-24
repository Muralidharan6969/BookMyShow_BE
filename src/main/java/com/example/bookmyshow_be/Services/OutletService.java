package com.example.bookmyshow_be.Services;

import com.example.bookmyshow_be.Exceptions.UserNotFoundException;
import com.example.bookmyshow_be.Models.Outlet;
import com.example.bookmyshow_be.Models.OutletTokenPayload;
import com.example.bookmyshow_be.Models.User;
import com.example.bookmyshow_be.Models.UserTokenPayload;
import com.example.bookmyshow_be.Repositories.OutletRepository;
import com.example.bookmyshow_be.Repositories.UserRepository;
import com.example.bookmyshow_be.Utils.ENUMS.RoleEnums;
import com.example.bookmyshow_be.Utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OutletService {
    private OutletRepository outletRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private JWTUtils jwtUtils;
    @Autowired
    public OutletService(OutletRepository outletRepository, BCryptPasswordEncoder bCryptPasswordEncoder,
                         JWTUtils jwtUtils){
        this.outletRepository = outletRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtUtils = jwtUtils;
    }

    public Outlet registerOutlet(Outlet outlet){
        outlet.setPassword(bCryptPasswordEncoder.encode(outlet.getPassword()));
        return outletRepository.save(outlet);
    }

    public String outletLogin(String email, String password) throws UserNotFoundException {
        Optional<Outlet> outletOptional = outletRepository.findByEmail(email);
        if(outletOptional.isEmpty()){
            throw new UserNotFoundException("Outlet Details not found");
        }

        if(!bCryptPasswordEncoder.matches(password, outletOptional.get().getPassword())){
            throw new RuntimeException("Password is incorrect");
        }

        Outlet outlet = outletOptional.get();

        return jwtUtils.generateOutletToken(new OutletTokenPayload(outlet.getOutletId(),
                outlet.getOutletOwnershipName(), RoleEnums.OUTLET.toString()));
    }

    public Outlet getOutletByOutletId(Long outletId){
        Optional<Outlet> outletOptional = outletRepository.findById(outletId);
        if(outletOptional.isEmpty()){
            return null;
        }
        return outletOptional.get();
    }
}
