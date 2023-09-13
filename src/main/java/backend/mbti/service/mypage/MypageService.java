package backend.mbti.service.mypage;

import backend.mbti.domain.dto.mypage.MbtiGroupRequest;
import backend.mbti.domain.dto.mypage.MemberUpdateRequest;
import backend.mbti.domain.mbti.MbtiGroup;
import backend.mbti.domain.member.Member;
import backend.mbti.domain.post.Post;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MypageService {

    // 유저 정보 뷰
    Member getUserInfo(String username);

    // 회원 정보 수정
    Member updateAllMemberInfo(MemberUpdateRequest request, String username);

    // 프로필 저장
    void updateProfilePicture(String username, MultipartFile file);

    // 프로필 불러오기
    String getProfile(Long memberId);

    // 내가 만든 케미 저장
    MbtiGroup createMbtiGroup(MbtiGroupRequest request, String userId);

    // 내가 만든 케미 불러오기
    List<MbtiGroup> findGroupsByMember(String userId);

    // 내가 만든 토론
    List<Post> getPostsByMember(String userId);

    // 문의하기 (ADMIN 계정으로 일단 보류)

    // 회원 탈퇴
    void deleteMember(String username);
}
