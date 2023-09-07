package backend.mbti.service.member;

import backend.mbti.domain.dto.member.MemberLoginRequest;
import backend.mbti.domain.dto.member.MemberSignUpRequest;
import backend.mbti.domain.member.Member;
import backend.mbti.exception.AppException;
import backend.mbti.exception.ErrorCode;
import backend.mbti.repository.member.MemberRepository;
import backend.mbti.configuration.jwt.JwtProvider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    // JWT
    @Value("${jwt.secret}")
    private String key;
    private Long expireTimeMs = 1000 * 60 * 60L; // 1시간

    // 파일 경로
    @Value("${file.upload-dir}")
    private String uploadDir;


    // 회원가입
    @Override
    public String signup(MemberSignUpRequest memberSignUpRequest) {

        String encPwd = bCryptPasswordEncoder.encode(memberSignUpRequest.getPassword());

        Member member = memberRepository.save(memberSignUpRequest.toEntity(encPwd));

        // 에러 처리 해야함

        return member.getUserId();
    }


    // 로그인
    @Override
    public String login(MemberLoginRequest memberLoginRequest) {
        // 아이디 틀림
        Member selectedUser = memberRepository.findByUserId(memberLoginRequest.getUserId())
                .orElseThrow(() ->new AppException(ErrorCode.USERNAME_NOT_FOUND, memberLoginRequest.getUserId() + "없습니다"));
        // 패스워드 틀림
        if (!bCryptPasswordEncoder.matches(memberLoginRequest.getPassword(), selectedUser.getPassword())) {
            throw new AppException(ErrorCode.INVALID_PASSWORD, "패스워드 틀림");
        }
        String token = JwtProvider.createToken(selectedUser.getUserId(), key, expireTimeMs);
        // 앞에서 Exception(예외) 안났으면 토큰 반환
        return token;
    }

    // 아이디 중복
    public boolean isUserIdDuplicate(String userId) {
        return memberRepository.findByUserId(userId).isPresent();
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

    // 비밀번호 생성
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

    // 프로필
    @Override
    public void updateProfilePicture(String username, MultipartFile file) {
        Member member = memberRepository.findByUserId(username)
                .orElseThrow(() -> new EntityNotFoundException("Member not found"));

        String fileName = storeFile(file);
        member.setProfileImage(fileName);
        memberRepository.save(member);
    }

    // 파일 중복 검증 등...
    public String storeFile(MultipartFile file) {

        String originalFilename = file.getOriginalFilename();
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String newFileName = UUID.randomUUID() + fileExtension;

        try {
            Path targetLocation = Paths.get(uploadDir).resolve(newFileName);

            Files.copy(file.getInputStream(), targetLocation);

            return newFileName;

        } catch (IOException ex) {
            throw new RuntimeException("프로필 저장 실패", ex);
        }
    }

    // 프로필 보내기
    @Override
    public String getProfile(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("Member not found"));
        String imageUrl = uploadDir + member.getProfileImage();
        return imageUrl;
    }
}
