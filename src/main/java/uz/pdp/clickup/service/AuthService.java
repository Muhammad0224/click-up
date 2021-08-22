package uz.pdp.clickup.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.clickup.domain.User;
import uz.pdp.clickup.enums.SystemRoleName;
import uz.pdp.clickup.payload.ApiResponse;
import uz.pdp.clickup.payload.RegisterDTO;
import uz.pdp.clickup.repository.UserRepository;

import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JavaMailSender javaMailSender;

    //TODO KOD YOZAMIZ
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));
    }

    public ApiResponse registerUser(RegisterDTO dto) {
        if (userRepository.existsByEmail(dto.getEmail()))
            return new ApiResponse("Bunday user mavjud", false);
        User user = new User(
                dto.getFullName(),
                dto.getEmail(),
                passwordEncoder.encode(dto.getPassword()),
                SystemRoleName.SYSTEM_USER
        );
        int code = new Random().nextInt(999999);
        user.setEmailCode(String.valueOf(code).substring(0, 4));
        userRepository.save(user);
        sendEmail(user.getEmail(), user.getEmailCode());
        return new ApiResponse("User saqlandi", true);
    }

    public void sendEmail(String sendingEmail, String emailCode) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom("Test@pdp.com");
            mailMessage.setTo(sendingEmail);
            mailMessage.setSubject("Akkountni tasdiqlash");
            mailMessage.setText(emailCode);
            javaMailSender.send(mailMessage);
        } catch (Exception ignored) {
        }
    }

    public ApiResponse verifyEmail(String email, String emailCode) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (emailCode.equals(user.getEmailCode())) {
                user.setEnabled(true);
                user.setEmailCode(null);
                userRepository.save(user);
                return new ApiResponse("Acount aktivlashtirildi", true);
            }
            return new ApiResponse("Kod xato", false);
        }
        return new ApiResponse("Bunday user mavjud emas", false);
    }
}
