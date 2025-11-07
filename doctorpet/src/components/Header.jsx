import React from "react";
import "../css/Header.css";
import { Link } from "react-router-dom";

const Header = () => {
  return (
    <div className="header">
      <div className="img-header">
        <img src="/assets/logo.png" alt="logo" />
      </div>
      <div>
        <h2>Hệ thống đặt lịch khám thú cưng dành cho bạn</h2>
      </div>
      <div>
        <Link to="/login">
          <button className="header-btn-login">Đăng Nhập</button>
        </Link>
        <Link to="/register">
          <button className="header-btn-register">Đăng Ký</button>
        </Link>
      </div>
    </div>
  )
};

export default Header;
