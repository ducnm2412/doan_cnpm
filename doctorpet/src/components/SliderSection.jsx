import React from "react";
import { useEffect, useState } from "react";
import "../css/Slider.css";
const SliderSection = () => {
  const [currentSlide, setCurrentSlide] = useState(0);

  const slides = [
    {
      image: "../public/assets/slider1.png",
      title: "Đặt lịch khám nhanh chóng",
      text: "Chỉ với vài cú nhấp chuột, bác sĩ thú y sẽ đến tận nơi.",
    },
    {
      image: "../public/assets/slider2.png",
      title: "Dịch vụ tận tâm",
      text: "Chúng tôi luôn đặt sức khỏe thú cưng của bạn lên hàng đầu.",
    },
    {
      image: "../public/assets/slider3.png",
      title: "Đội ngũ bác sĩ chuyên nghiệp",
      text: "Giàu kinh nghiệm – Nhiệt tình – Luôn sẵn sàng hỗ trợ bạn.",
    },
  ];

  // Chuyển slide tự động sau mỗi 5 giây
  useEffect(() => {
    const timer = setInterval(() => {
      nextSlide();
    }, 5000);
    return () => clearInterval(timer);
  }, [currentSlide]);

  const nextSlide = () => {
    setCurrentSlide((prev) => (prev + 1) % slides.length);
  };

  const prevSlide = () => {
    setCurrentSlide((prev) => (prev === 0 ? slides.length - 1 : prev - 1));
  };

  const goToSlide = (index) => {
    setCurrentSlide(index);
  };

  return (
    <section className="slider-section">
      <div className="slider-container">
        {slides.map((slide, index) => (
          <div
            key={index}
            className={`slide fade ${index === currentSlide ? "active" : ""}`}
          >
            <img src={slide.image} alt={slide.title} />
            <div className="caption">
              <h2>{slide.title}</h2>
              <p>{slide.text}</p>
            </div>
          </div>
        ))}

        {/* Nút điều hướng */}
        <button className="prev" onClick={prevSlide}>
          ❮
        </button>
        <button className="next" onClick={nextSlide}>
          ❯
        </button>
      </div>

      {/* Dấu chấm */}
      <div className="dots">
        {slides.map((_, index) => (
          <span
            key={index}
            className={`dot ${index === currentSlide ? "active" : ""}`}
            onClick={() => goToSlide(index)}
          />
        ))}
      </div>
    </section>
  );
};

export default SliderSection;
