package cms_back.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class SignupRequest {

    @NotBlank(message = "이름은 필수입니다.")
    @Size(max = 20, message = "이름은 20자 이하여야 합니다.")
    private String name;

    @NotBlank(message = "이메일은 필수입니다.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    @Size(max = 120, message = "이메일은 120자 이하여야 합니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수입니다.")
    @Size(min = 8, max = 64, message = "비밀번호는 8~64자여야 합니다.")
    private String password;

    @NotBlank(message = "비밀번호 확인은 필수입니다.")
    private String passwordConfirm;

    @AssertTrue(message = "이용약관에 동의해야 회원가입이 가능합니다.")
    private boolean agreeTerms;

    @AssertTrue(message = "개인정보 수집·이용에 동의해야 회원가입이 가능합니다.")
    private boolean agreePrivacy;

    public SignupRequest() {}

    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getPasswordConfirm() { return passwordConfirm; }
    public boolean isAgreeTerms() { return agreeTerms; }
    public boolean isAgreePrivacy() { return agreePrivacy; }

    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setPasswordConfirm(String passwordConfirm) { this.passwordConfirm = passwordConfirm; }
    public void setAgreeTerms(boolean agreeTerms) { this.agreeTerms = agreeTerms; }
    public void setAgreePrivacy(boolean agreePrivacy) { this.agreePrivacy = agreePrivacy; }
}
