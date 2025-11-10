import React, { useState } from "react";
import "../css/UserLayout.css";
import Header from "../../components/Header";
import "remixicon/fonts/remixicon.css";
import ProfilePet from "../pages/ProfilePet";
import Appointment from "../pages/Appointment";
import Schedule from "../pages/Schedule";
import Question from "../pages/Question";
const UserLayout = () => {
  // active-menu-section
  const [active, setActive] = useState("profile");

  return (
    <>
      <Header />
      <div className="dashboard-container">
        {/* Sidebar */}
        <div className="sidebar">
          <div className="profile-section">
            <img className="avatar" src="../public/assets/meme.jpg"></img>
            <input
              type="text"
              placeholder="Tên người dùng"
              className="info-input"
            />
            <input type="text" placeholder="Email" className="info-input" />
            <input
              type="text"
              placeholder="Số điện thoại"
              className="info-input"
            />
          </div>

          <div className="menu-section">
            <button
              className={`menu-btn ${active === "profile" ? "active" : ""}`}
              onClick={() => setActive("profile")}
            >
              Hồ sơ thú cưng
            </button>
            <button
              className={`menu-btn ${active === "appointment" ? "active" : ""}`}
              onClick={() => setActive("appointment")}
            >
              Đặt lịch khám
            </button>
            <button
              className={`menu-btn ${active === "schedule" ? "active" : ""}`}
              onClick={() => setActive("schedule")}
            >
              Lịch đã đặt
            </button>
            <button
              className={`menu-btn ${active === "question" ? "active" : ""}`}
              onClick={() => setActive("question")}
            >
              Đặt câu hỏi
            </button>
          </div>
        </div>

        {/* Main content */}
        <div className="main-content">
          {active === "profile" && <ProfilePet />}
          {active === "appointment" && <Appointment />}
          {active === "schedule" && <Schedule />}
          {active === "question" && <Question />}
        </div>
      </div>
    </>
  );
};

export default UserLayout;
