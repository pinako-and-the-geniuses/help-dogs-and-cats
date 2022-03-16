import "../styles/userform.css";
import { FormGroup, Label, Input, Button } from "reactstrap";

export default function UserForm({
  email,
  password,
  confirmPassword,
  nickName,
  phone,
  region,
  onEmailHandler,
  onPasswordHandler,
  onConfirmPasswordHandler,
  onNickNameHandler,
  onRegionHandler,
  onPhoneHandler,
  onSubmit,
}) {
  return (
    <div>
      <form className="userform">
        <FormGroup>
          <Label for="email">아이디 [Email]</Label>
          <Input
            id="email"
            name="email"
            type="email"
            placeholder="이메일"
            value={email}
            onChange={onEmailHandler}
          />
        </FormGroup>
        <FormGroup>
          <Label for="password">비밀번호</Label>
          <Input
            id="password"
            name="password"
            type="password"
            placeholder="비밀번호"
            value={password}
            onChange={onPasswordHandler}
          />
        </FormGroup>
        <FormGroup>
          <Label for="confirmPassword">비밀번호 확인</Label>
          <Input
            id="confirmPassword"
            name="confirmPassword"
            type="password"
            placeholder="비밀번호 확인"
            value={confirmPassword}
            onChange={onConfirmPasswordHandler}
          />
        </FormGroup>
        <FormGroup>
          <Label for="nickname">닉네임</Label>
          <Input
            id="nickname"
            name="nickname"
            type="text"
            placeholder="닉네임"
            value={nickName}
            onChange={onNickNameHandler}
          />
        </FormGroup>
        <FormGroup>
          <Label for="phone">휴대폰 번호</Label>
          <Input
            id="phone"
            name="phone"
            type="text"
            placeholder="휴대폰 번호"
            value={phone}
            onChange={onPhoneHandler}
          />
        </FormGroup>
        <FormGroup>
          <Label for="region">활동 지역</Label>
          <Input
            name="region"
            type="select"
            placeholder="활동지역"
            onChange={onRegionHandler}
          >
            <option>서울</option>
            <option>광주</option>
            <option>부산</option>
            <option>울산</option>
            <option>경주</option>
          </Input>
        </FormGroup>

        <FormGroup check>
          <Label check>
            <Input type="checkbox" /> 개인정보 방침 동의
            <span className="form-check-sign">
              <span className="check"></span>
            </span>
          </Label>
        </FormGroup>
        <div className="d-flex justify-content-end">
          <Button className="mt-3 " onClick={onSubmit} color="primary">
            로그인
          </Button>
        </div>
      </form>
    </div>
  );
}
