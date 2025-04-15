package com.java6.asm.clothing_store.service.authentication.impl;

import com.java6.asm.clothing_store.constance.RoleEnum;
import com.java6.asm.clothing_store.constance.StatusEnum;
import com.java6.asm.clothing_store.constance.TypeAccountEnum;
import com.java6.asm.clothing_store.dto.mapper.ZaloUserMapper;
import com.java6.asm.clothing_store.dto.response.ZaloUserRegisterResponse;
import com.java6.asm.clothing_store.entity.User;
import com.java6.asm.clothing_store.repository.UserRepository;
import com.java6.asm.clothing_store.service.authentication.OAuthZaloUserService;
import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@AllArgsConstructor
public class OAuthZaloUserServiceImpl implements OAuthZaloUserService {

    private final UserRepository userRepository;
    private final ZaloUserMapper zaloUserMapper;
    private final RestTemplate restTemplate;

    private final String ZALO_TOKEN_URL = "https://oauth.zaloapp.com/v4/access_token";
    private final String ZALO_USER_INFO_URL = "https://graph.zalo.me/v2.0/me?fields=id,name,picture";

    private final String APP_ID = "3502086008475683777";
    private final String SECRET_KEY = "UEuw0kt53n3ZdNl7DVYH";
    private final String REDIRECT_URI = "http://localhost:5173/auth/zalo/callback";

    @Override
    public ZaloUserRegisterResponse findOrCreateUser(String code) {
        // 📌 Bước 1: Đổi Authorization Code lấy Access Token
        String accessToken = getAccessTokenFromZalo(code);

        // 📌 Bước 2: Lấy thông tin user từ Zalo
        ZaloUserRegisterResponse zaloUser = getUserInfoFromZalo(accessToken);

        // 📌 Bước 3: Tạo email giả từ Zalo ID
        String generatedEmail = zaloUser.getZaloId() + "@zalo.com"; // 🔥 Tạo email giả từ ID Zalo

        // 📌 Bước 4: Kiểm tra nếu user đã tồn tại trong DB
        return userRepository.findByEmailAndStatus(generatedEmail, StatusEnum.ACTIVE)  // 🔥 Dùng email làm định danh
                .map(zaloUserMapper::toResponse) // Nếu có, trả về DTO
                .orElseGet(() -> createUser(zaloUser, generatedEmail));
    }

    /**
     * 🔥 Gửi request đến Zalo API để lấy Access Token từ Authorization Code
     */
    @Override
    public String getAccessTokenFromZalo(String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("secret_key", SECRET_KEY);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("app_id", APP_ID);
        body.add("code", code);
        body.add("grant_type", "authorization_code");
        body.add("redirect_uri", REDIRECT_URI);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
        ResponseEntity<Map> response = restTemplate.exchange(ZALO_TOKEN_URL, HttpMethod.POST, request, Map.class);

        // ✅ Kiểm tra lỗi
        if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null || !response.getBody().containsKey("access_token")) {
            throw new RuntimeException("Không lấy được access token từ Zalo");
        }

        return response.getBody().get("access_token").toString();
    }

    /**
     * 🔥 Gửi request đến Zalo API để lấy thông tin người dùng
     */
    @Override
    public ZaloUserRegisterResponse getUserInfoFromZalo(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<Map> response = restTemplate.exchange(ZALO_USER_INFO_URL, HttpMethod.GET, entity, Map.class);

        // ✅ Kiểm tra lỗi
        if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null) {
            throw new RuntimeException("Không lấy được thông tin user từ Zalo");
        }

        // ✅ Parse dữ liệu từ API Zalo
        Map<String, Object> userData = response.getBody();
        String zaloId = userData.get("id").toString();
        String name = userData.get("name").toString();
        String pictureUrl = "";

        // ✅ Kiểm tra có ảnh đại diện không
        if (userData.containsKey("picture")) {
            Map<String, Object> pictureData = (Map<String, Object>) userData.get("picture");
            if (pictureData.containsKey("data")) {
                pictureUrl = pictureData.get("data").toString();
            }
        }

        return new ZaloUserRegisterResponse(zaloId, name, pictureUrl);
    }

    /**
     * ✅ Tạo user mới
     */
    @Override
    public ZaloUserRegisterResponse createUser(ZaloUserRegisterResponse zaloUser, String email) {
        User newUser = User.builder()
                .email(email) // 🔥 Email giả định từ Zalo ID
                .fullname(zaloUser.getName())
                .image(zaloUser.getPicture())
                .type(TypeAccountEnum.ZALO)
                .role(RoleEnum.CUSTOMER)
                .status(StatusEnum.ACTIVE)
                .build();

        userRepository.save(newUser);
        return zaloUserMapper.toResponse(newUser);
    }
}
