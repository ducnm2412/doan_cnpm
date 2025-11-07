import React from "react";
import "../css/Login.css";

const Login = () => {
  return (
    <div className="login-body">
      <div className="login-container">
        <h2>Đăng Nhập</h2>
        <form className="login-form">
          <div className="form-group">
            <label>Email</label>
            <input type="email" placeholder="Nhập email của bạn" required />
          </div>

          <div className="form-group">
            <label>Mật khẩu</label>
            <input type="password" placeholder="Nhập mật khẩu" required />
          </div>

          <button type="submit" className="btn-login">
            Đăng Nhập
          </button>

          <p className="register-link">
            Chưa có tài khoản? <a href="/register">Đăng ký ngay</a>
          </p>
          <a href="/">Trở về trang chủ</a>
        </form>
      </div>
    </div>
  );
};

export default Login;
