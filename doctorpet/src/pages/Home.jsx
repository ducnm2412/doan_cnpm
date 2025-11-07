import React from "react";
import Header from "../components/Header";
import SliderSection from "../components/SliderSection";
import "../css/Home.css";
import Footer from "../components/Footer";
const Home = () => {
  return (
    <>
      <Header />
      <>
        {/*SLIDER-SECTION*/}
        <SliderSection />
        {/* SECTION GIỚI THIỆU */}
        <section className="about-section">
          <div className="about-container">
            <div className="about-text">
              <h2>Về Chúng Tôi</h2>
              <p>
                <strong>DOCPET</strong> là nền tảng đặt lịch khám và
                chăm sóc thú cưng trực tuyến, giúp chủ nuôi dễ dàng tìm và đặt
                lịch với các bác sĩ thú y, spa và trung tâm chăm sóc đáng tin
                cậy.
              </p>
              <p>
                Với mục tiêu “<em>Pet khỏe – Chủ vui</em>”, chúng tôi mang đến
                giải pháp tiện lợi, nhanh chóng và minh bạch. Chỉ với vài cú
                nhấp chuột, bạn có thể đặt lịch, theo dõi lịch sử khám, và nhận
                thông báo nhắc hẹn tự động.
              </p>
            </div>
            <div className="about-image">
              <img src="../public/assets/logo.png" alt="" />
            </div>
          </div>
        </section>
        {/* SỨ MỆNH & GIÁ TRỊ */}
        <section className="mission-section">
          <h2>Tầm nhìn &amp; Sứ mệnh</h2>
          <div className="mission-container">
            <div className="mission-card">
              <h3>Tầm nhìn</h3>
              <p>
                Trở thành nền tảng chăm sóc thú cưng hàng đầu Việt Nam – nơi kết
                nối bác sĩ, chủ nuôi và các trung tâm thú y một cách nhanh
                chóng, chuyên nghiệp.
              </p>
            </div>
            <div className="mission-card">
              <h3>Sứ mệnh</h3>
              <p>
                Đem lại sự tiện lợi, an tâm và hạnh phúc cho mỗi thú cưng và chủ
                nhân của chúng bằng công nghệ thông minh và dịch vụ tận tâm.
              </p>
            </div>
            <div className="mission-card">
              <h3>Giá trị cốt lõi</h3>
              <ul>
                <li>❤️ Tận tâm với thú cưng</li>
                <li>🤝 Đặt uy tín và minh bạch lên hàng đầu</li>
                <li>⚙️ Liên tục đổi mới và cải tiến</li>
              </ul>
            </div>
          </div>
        </section>
        {/* ĐỘI NGŨ */}
        <section className="team-section">
          <h2>Đội ngũ của chúng tôi</h2>
          <div className="team-container">
            <div className="team-member">
              <img src="../public/assets/doc1.jpg" alt="Bác sĩ 1" />
              <h3>Bs. Ngô Minh Đức</h3>
              <p>Bác sĩ trưởng - Chuyên khoa nội tổng quát</p>
            </div>
            <div className="team-member">
              <img src="../public/assets/doc3.jpg" alt="Bác sĩ 2" />
              <h3>Bs. Phạm Quốc Huy</h3>
              <p>Chuyên gia chăm sóc da và dinh dưỡng thú cưng</p>
            </div>
            <div className="team-member">
              <img src="../public/assets/doc2.jpg" alt="Bác sĩ 3" />
              <h3>Bs. Ngô Hoàng Thức</h3>
              <p>Phụ trách thú y di động - Chăm sóc tại nhà</p>
            </div>
          </div>
        </section>
      </>
      <Footer/>
    </>
  );
};

export default Home;
