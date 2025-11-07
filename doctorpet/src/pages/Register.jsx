import React from "react";
import "../css/Register.css";

const Register = () => {
  return (
    <div className="register-body">
      <div className="register-container">
        <h2>Đăng Ký Tài Khoản</h2>
        <form className="register-form">
          <div className="form-group">
            <label>Họ và tên</label>
            <input type="text" placeholder="Nhập họ và tên" required />
          </div>

          <div className="form-group">
            <label>Tên đang nhập</label>
            <input type="text" placeholder="Nhập tên đăng nhập" required />
          </div>
          <div className="form-group">
            <label>Email</label>
            <input type="email" placeholder="Nhập email" required />
          </div>
          <div className="form-group">
            <label>Mật khẩu</label>
            <input type="password" placeholder="Nhập mật khẩu" required />
          </div>
          <button type="submit" className="btn-register">
            Đăng Ký
          </button>

          <p className="login-link">
            Đã có tài khoản? <a href="/login">Đăng nhập</a>
          </p>
          <a href="/">Trở về trang chủ</a>
        </form>
      </div>
    </div>
  );
};

export default Register;
