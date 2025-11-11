import React from "react";

const Sidebar = () => {
    return (
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
                <button className="menu-btn">Hồ sơ thú cưng</button>
                <button className="menu-btn">Đặt lịch khám</button>
                <button className="menu-btn">Lịch đã đặt</button>
                <button className="menu-btn">Đặt câu hỏi</button>
            </div>
        </div>
    );
};

export default Sidebar;
