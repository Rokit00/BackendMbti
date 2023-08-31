package backend.mbti.service.member;

import backend.mbti.domain.dto.member.MemberSignUpRequest;
import backend.mbti.domain.member.Member;
import backend.mbti.repository.member.MemberRepository;
import backend.mbti.utils.Jwt;
import backend.mbti.utils.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
<<<<<<< HEAD
=======
import org.springframework.web.server.ResponseStatusException;

>>>>>>> 6ef9c21f95cdd89e3b0633e0f2ceaa7d6599c0fb
import java.util.Map;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtProvider jwtProvider;


<<<<<<< HEAD
    // Jwt 키, 만료 시간
    @Value("${jwt.secret}")
    private String key;
    private Long expireTimeMs = 1000 * 60 * 60L; // 1시간


=======
>>>>>>> 6ef9c21f95cdd89e3b0633e0f2ceaa7d6599c0fb
    // 회원가입
    @Override
    public Long signup(MemberSignUpRequest memberSignUpRequest) {
        boolean check = checkExists(memberSignUpRequest.getUserId());

        if (check) {
            throw new IllegalArgumentException("이미 존재하는 유저입니다.");
        }

        String encPwd = bCryptPasswordEncoder.encode(memberSignUpRequest.getPassword());

        Member member = memberRepository.save(memberSignUpRequest.toEntity(encPwd));

        if (member != null) {
            return member.getId();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    public boolean checkExists(String userId) {
        return memberRepository.existsUsersById(userId);
    }

    // 로그인
    @Override
    public Jwt login(String userId, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userId, password);
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        Jwt token = jwtProvider.generateToken(authentication);
        return token;
    }

    public boolean isUserIdDuplicate(String userId) {
        return memberRepository.findByUserId(userId).isPresent();
    }

    // 회원 탈퇴
    @Override
    public boolean deleteMember(Long memberId) {
        Optional<Member> memberOptional = memberRepository.findById(memberId);
        if (memberOptional.isPresent()) {
            Member member = memberOptional.get();
            memberRepository.deleteById(memberId);
            return true;
        }
        return false;
    }

    // 아이디 찾기
    public String findUsernameByEmailAndPhoneNumber(String birthday, String email) {
        Member member = memberRepository.findByBirthdayAndEmail(birthday, email);
        if (member != null) {
            return member.getUserId();
        }
        return null;
    }

    // 비밀번호 찾기
    public String requestPasswordReset(String userId, String email) {
        Member member = memberRepository.findByUserIdAndEmail(userId, email);

        if (member != null) {
            String temporaryPassword = generateTemporaryPassword();
            member.setPassword(temporaryPassword);
            memberRepository.save(member);

            return temporaryPassword;
        }
        return null;
    }

    public String generateTemporaryPassword() {
        int length = 8;
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder temporaryPassword = new StringBuilder();

        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            temporaryPassword.append(characters.charAt(index));
        }

        return temporaryPassword.toString();
    }

    // 회원 정보 수정
    public boolean updateMember(Long id, Map<String, String> updates) {
        Optional<Member> optionalMember = memberRepository.findById(id);

        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();

            if (updates.containsKey("userId")) {
                member.setUserId(updates.get("userId"));
            }

            if (updates.containsKey("password")) {
                member.setPassword(bCryptPasswordEncoder.encode(updates.get("password")));
            }

            if (updates.containsKey("nickName")) {
                member.setNickName(updates.get("nickName"));
            }

            if (updates.containsKey("email")) {
                member.setEmail(updates.get("email"));
            }

            if (updates.containsKey("birthday")) {
                member.setBirthday(updates.get("birthday"));
            }

            memberRepository.save(member);
            return true;
        }
        return false;
    }
}
