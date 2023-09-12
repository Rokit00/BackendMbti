package backend.mbti.service.mypage;

import backend.mbti.domain.dto.mypage.MbtiGroupRequest;
import backend.mbti.domain.dto.mypage.MemberUpdateRequest;
import backend.mbti.domain.mbti.Mbti;
import backend.mbti.domain.member.Member;
import backend.mbti.domain.post.Post;
import backend.mbti.repository.mbti.MbtiRepository;
import backend.mbti.repository.member.MemberRepository;
import backend.mbti.repository.post.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class MypageServiceImpl implements MypageService {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final MbtiRepository mbtiRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    // 파일 경로
    @Value("${file.upload-dir}")
    private String uploadDir;

    // 유저 정보 뷰
    @Override
    public Member getUserInfo(String username) {
        Member member = memberRepository.findAllByUserId(username);
        return member;
    }

    // 회원 정보 수정
    @Override
    public Member updateAllMemberInfo(MemberUpdateRequest request, String username) {
        Member member = memberRepository.findByUserId(username)
                .orElseThrow(() -> new EntityNotFoundException("회원을 찾을 수 없습니다."));

        if (!member.getUserId().equals(username)) {
            throw new AccessDeniedException("올바르지 못한 접근");
        }

        // 업데이트 로직
        member.setNickName(request.getNickName());
        member.setMbti(request.getMbti());
        member.setEmail(request.getEmail());
        member.setBirthday(request.getBirthday());

        if (request.getCurrentPassword() != null && request.getNewPassword() != null) {
            if (!bCryptPasswordEncoder.matches(request.getCurrentPassword(), member.getPassword())) {
                throw new IllegalArgumentException("비밀번호 틀림!");
            }
            member.setPassword(bCryptPasswordEncoder.encode(request.getNewPassword()));
        }

        return memberRepository.save(member);
    }

    // 프로필 저장
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

    // 프로필 불러오기
    @Override
    public String getProfile(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("Member not found"));
        String imageUrl = uploadDir + member.getProfileImage();
        return imageUrl;
    }

    // 내가 만든 케미 저장
    @Override
    public Mbti createMbtiGroup(MbtiGroupRequest request, String userId) {
        Member member = memberRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("회원을 찾을 수 없습니다."));

        Mbti mbtiGroup = new Mbti();
        mbtiGroup.setMbtiTypes(request.getMbtiTypes());
        mbtiGroup.setGroupName(request.getGroupName());
        mbtiGroup.setMember(member);

        return mbtiRepository.save(mbtiGroup);
    }

    // 내가 만든 케미 불러오기
    @Override
    public List<Mbti> viewMbtiGroup(String userId) {
        Member member = memberRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("회원을 찾을 수 없습니다"));

        List<Mbti> mbti = mbtiRepository.findByMember(member);
        return mbti;
    }

    // 내가 만든 토론
    @Override
    public List<Post> getPostsByMember(String userId) {
        Member member = memberRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("회원을 찾을 수 없습니다."));

        return postRepository.findByMemberOrderByCreatedAtDesc(member);
    }

    // 문의하기

    // 회원 탈퇴
    @Override
    public void deleteMember(String username) {
        Member member = memberRepository.findByUserId(username)
                .orElseThrow(() -> new EntityNotFoundException("회원을 찾을 수 없습니다."));

        if (!member.getUserId().equals(username)) {
            throw new AccessDeniedException("잘못된 접근! 회원 탈퇴 불가!");
        }

        memberRepository.delete(member);
    }
}
